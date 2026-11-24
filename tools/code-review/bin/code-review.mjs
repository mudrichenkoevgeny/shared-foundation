#!/usr/bin/env node
import { execSync, spawnSync } from 'node:child_process';
import { createHash } from 'node:crypto';
import { existsSync, mkdirSync, readFileSync, readdirSync, statSync, writeFileSync } from 'node:fs';
import { join, relative } from 'node:path';

function getRepoRoot() {
  try {
    return execSync('git rev-parse --show-toplevel', { encoding: 'utf8' }).trim();
  } catch (_) {
    return process.cwd();
  }
}

const REPO_ROOT = getRepoRoot();
const STATE_FILE = join(REPO_ROOT, '.code-review/state.json');
const PAYLOAD_FILE = join(REPO_ROOT, '.code-review/payload.md');
const PROMPT_FILE = join(REPO_ROOT, '.code-review/REVIEW_PROMPT.md');
const WORKFLOW_FILE = join(REPO_ROOT, '.code-review/CODE_REVIEW_WORKFLOW.md');
const ROOT_AGENTS_FILE = join(REPO_ROOT, 'AGENTS.md');
const AGENT_DIR = join(REPO_ROOT, '.agent');

const IGNORED_PATTERNS = [
  /^\.code-review\//,
  /pnpm-lock\.yaml$/,
  /routeTree\.gen\.ts$/,
  /gradle-wrapper\.jar$/,
  /\.(png|jpg|jpeg|webp|woff2|ttf|otf|ico|svg|jar|pdf|zip)$/i
];

function copyToClipboard(text) {
  const platform = process.platform;
  let result = false;

  if (platform === 'win32') {
    const res = spawnSync(
      'powershell',
      [
        '-NoProfile',
        '-Command',
        '[Console]::InputEncoding = [System.Text.Encoding]::UTF8; Set-Clipboard -Value ([Console]::In.ReadToEnd())'
      ],
      { input: text, encoding: 'utf8' }
    );
    result = res.status === 0;
  } else if (platform === 'darwin') {
    const res = spawnSync('pbcopy', [], { input: text, encoding: 'utf8' });
    result = res.status === 0;
  } else if (platform === 'linux') {
    const tryWl = spawnSync('wl-copy', [], { input: text, encoding: 'utf8' });
    if (tryWl.status === 0) {
      result = true;
    } else {
      const tryXclip = spawnSync('xclip', ['-selection', 'clipboard'], { input: text, encoding: 'utf8' });
      if (tryXclip.status === 0) {
        result = true;
      } else {
        const tryXsel = spawnSync('xsel', ['--clipboard', '--input'], { input: text, encoding: 'utf8' });
        result = tryXsel.status === 0;
      }
    }
  }

  if (!result) {
    console.warn('Warning: Could not copy payload to system clipboard in this environment.');
  }
  return result;
}

function runInitCommand() {
  const codeReviewDir = join(REPO_ROOT, '.code-review');
  mkdirSync(codeReviewDir, { recursive: true });

  writeFileSync(WORKFLOW_FILE, getWorkflowDocText());
  console.log(`Updated workflow guide -> ${relative(process.cwd(), WORKFLOW_FILE)}`);

  if (!existsSync(PROMPT_FILE)) {
    writeFileSync(PROMPT_FILE, getStarterPromptText());
    console.log(`Created starter prompt -> ${relative(process.cwd(), PROMPT_FILE)}`);
    console.log("Next step: Run 'code-review update-prompt' to generate repository prompt.");
  } else {
    console.log(`Preserved existing prompt -> ${relative(process.cwd(), PROMPT_FILE)}`);
  }
}

function runPromptCommand() {
  if (!existsSync(PROMPT_FILE)) {
    console.error(`Error: Review prompt file not found at ${PROMPT_FILE}.`);
    console.error("Run 'code-review init' and 'code-review update-prompt' to set up your repository.");
    process.exit(1);
  }

  const promptText = readFileSync(PROMPT_FILE, 'utf8');
  if (promptText.includes('This is a starter placeholder.')) {
    console.warn('Warning: .code-review/REVIEW_PROMPT.md is still a starter placeholder.');
    console.warn("Run 'code-review update-prompt' and follow instructions to generate repository prompt.");
  }

  mkdirSync(join(REPO_ROOT, '.code-review'), { recursive: true });
  writeFileSync(PAYLOAD_FILE, promptText);
  copyToClipboard(promptText);
  console.log(`Copied review prompt to clipboard and saved to ${PAYLOAD_FILE}.`);
}

function runPackCommand(commandArgs) {
  const forceAll = commandArgs.includes('--all');
  const markOnly = commandArgs.includes('--mark');
  const pathFilters = commandArgs.filter(arg => !arg.startsWith('--'));

  const prevState = existsSync(STATE_FILE)
    ? JSON.parse(readFileSync(STATE_FILE, 'utf8'))
    : {};

  let statusOutput = '';
  try {
    statusOutput = execSync('git status --porcelain -uall', { encoding: 'utf8', cwd: REPO_ROOT });
  } catch (err) {
    console.error('Error executing git status: ' + err.message);
    process.exit(1);
  }

  const candidateFiles = statusOutput
    .split('\n')
    .map(line => line.trimEnd())
    .filter(Boolean)
    .filter(line => line[1] !== ' ' && line[1] !== 'D')
    .map(line => {
      const rawPath = line.slice(3).replace(/^"|"$/g, '');
      return rawPath.includes(' -> ') ? rawPath.split(' -> ').pop() : rawPath;
    })
    .filter(relPath => {
      const absPath = join(REPO_ROOT, relPath);
      return existsSync(absPath) && !IGNORED_PATTERNS.some(re => re.test(relPath));
    })
    .filter(relPath => pathFilters.length === 0 || pathFilters.some(p => relPath.startsWith(p)));

  const currentHashes = {};
  const changedFiles = [];

  for (const relPath of candidateFiles) {
    const absPath = join(REPO_ROOT, relPath);
    const content = readFileSync(absPath, 'utf8');
    const hash = createHash('sha256').update(content).digest('hex');
    currentHashes[relPath] = hash;

    if (forceAll || prevState[relPath] !== hash) {
      changedFiles.push({ filePath: relPath, content });
    }
  }

  mkdirSync(join(REPO_ROOT, '.code-review'), { recursive: true });

  if (markOnly) {
    writeFileSync(STATE_FILE, JSON.stringify({ ...prevState, ...currentHashes }, null, 2));
    console.log(`Marked ${candidateFiles.length} file(s) as reviewed.`);
    process.exit(0);
  }

  if (changedFiles.length === 0) {
    console.log('No changes since last review.');
    process.exit(0);
  }

  const payload = changedFiles
    .map(({ filePath, content }) => `${filePath}\n\`\`\`\n${content}\n\`\`\``)
    .join('\n\n');

  writeFileSync(PAYLOAD_FILE, payload);
  copyToClipboard(payload);
  writeFileSync(STATE_FILE, JSON.stringify({ ...prevState, ...currentHashes }, null, 2));
  const fileList = changedFiles.map(f => f.filePath).join('\n- ');
  console.log(`Copied ${changedFiles.length} changed file(s) to clipboard:\n- ${fileList}`);
}

function collectMarkdownFiles(dirPath) {
  if (!existsSync(dirPath)) return [];
  const results = [];
  for (const entry of readdirSync(dirPath)) {
    const fullPath = join(dirPath, entry);
    const stat = statSync(fullPath);
    if (stat.isDirectory()) {
      results.push(...collectMarkdownFiles(fullPath));
    } else if (entry.toLowerCase().endsWith('.md')) {
      results.push(relative(REPO_ROOT, fullPath).replace(/\\/g, '/'));
    }
  }
  return results.sort();
}

function runUpdatePromptCommand() {
  const ruleFiles = [
    ...(existsSync(ROOT_AGENTS_FILE) ? [relative(REPO_ROOT, ROOT_AGENTS_FILE).replace(/\\/g, '/')] : []),
    ...collectMarkdownFiles(AGENT_DIR)
  ];

  if (ruleFiles.length === 0) {
    console.error('No agent rule files found (.agent/**/*.md or AGENTS.md).');
    process.exit(1);
  }

  const packedRules = ruleFiles
    .map(relPath => {
      const absPath = join(REPO_ROOT, relPath);
      return `${relPath}\n\`\`\`markdown\n${readFileSync(absPath, 'utf8')}\n\`\`\``;
    })
    .join('\n\n');

  let metaPrompt = '';
  const isPlaceholder = existsSync(PROMPT_FILE) &&
    readFileSync(PROMPT_FILE, 'utf8').includes('This is a starter placeholder.');

  if (existsSync(PROMPT_FILE) && !isPlaceholder) {
    const currentReviewPrompt = readFileSync(PROMPT_FILE, 'utf8');
    metaPrompt = `Update \`.code-review/REVIEW_PROMPT.md\` based on the latest project agent rule files provided below.

CRITICAL REQUIREMENTS:
1. Keep exact structure, tone, handshake protocol (\`Ready to review.\`), output format (\`Passed review:\`, \`Failed review:\`), and reviewer meta-rules (English-only fix prompt for coding agent, never rewrite full files yourself) from current \`.code-review/REVIEW_PROMPT.md\`.
2. Update, add, or remove code-review checklist items so they 100% reflect updated agent rule files below.
3. Output complete \`.code-review/REVIEW_PROMPT.md\` inside single \`markdown\` code block.

---

### CURRENT \`.code-review/REVIEW_PROMPT.md\`
\`\`\`markdown
${currentReviewPrompt}
\`\`\`

---

### UPDATED AGENT RULE FILES
${packedRules}
`;
  } else {
    metaPrompt = `Create \`.code-review/REVIEW_PROMPT.md\` from scratch based strictly on the project agent rule files provided below.

CRITICAL REQUIREMENTS:
1. Output ONLY the complete \`.code-review/REVIEW_PROMPT.md\` inside a single \`markdown\` code block (use 4-backtick fences so the inner \`text\` block renders intact). Do not add any commentary before or after the code block.
2. Follow the exact document structure, heading names, and section order specified below. Do not add extra top-level sections, introductory titles, or conversational filler.
3. Synthesize every concrete rule, prohibition, architectural boundary, naming convention, documentation standard, error-handling constraint, and testing requirement from the provided files. Do not invent rules not present in the source files, and do not replace specific project rules with generic advice.
4. Organize \`## MANDATORY REVIEW CHECKLIST\` into concise numbered subsections (\`### 1.\`, \`### 2.\`, etc.) grouped logically by domain, using dense, scannable bullet points.
5. Keep the \`## OUTPUT FORMAT & LANGUAGE (STRICT)\` section and the final handshake line verbatim (only adapt placeholder file extensions to match the repository).

REQUIRED STRUCTURE OF \`.code-review/REVIEW_PROMPT.md\`:

\`\`\`
You are a Principal <repository domain/stack> Architect and strict Code Reviewer for the \`<repository-name>\` repository.
I will send you code generated by a coding agent (diffs, new files, or batches). Audit it ruthlessly for architectural violations, rule breaches, bugs, edge cases, <key audit areas derived from the rule files>, documentation completeness, and test coverage.

---

## OUTPUT FORMAT & LANGUAGE (STRICT)

- **Always respond in English** (including the fix prompt for the coding agent).
- No greetings, praise, explanations, or code summaries. Use strictly one of two templates:

**Case A — All files passed:**
Passed review:
- \`path/to/File1.<ext>\`
- \`path/to/File2.<ext>\`

No issues found.

**Case B — Some or all files failed:**
Passed review:
- \`path/to/CleanFile.<ext>\` (or \`None\` if all failed)

Failed review:
- \`path/to/BrokenFile1.<ext>\`
- \`path/to/BrokenFile2.<ext>\`

\`\`\`text
<Ready-to-paste English fix prompt for the coding agent. Group by broken file (\`path/to/File.<ext>\`). For every issue, list the exact line number(s), symbol, or pattern and unambiguous fix instructions. Zero fluff.>
\`\`\`

If an imported file, contract, or referenced type is missing for verification, do not guess—list the exact files needed under \`Missing files:\`.

---

## ARCHITECTURE & MODULE BOUNDARIES (\`<repository-name>\`)

- <Synthesized rules, strict target platforms/stack, prohibitions, module/package nature, dependencies, dependency graph, management boundaries, and approved/forbidden backward-compatibility expectations from the provided repository files.>

---

## MANDATORY REVIEW CHECKLIST

### 1. <Domain Topic>
- <Concrete rules synthesized from the provided files.>

Confirm briefly with "Ready to review." and wait for code.
\`\`\`

---

### PROJECT AGENT RULE FILES

${packedRules}
`;
  }

  mkdirSync(join(REPO_ROOT, '.code-review'), { recursive: true });
  writeFileSync(PAYLOAD_FILE, metaPrompt);
  copyToClipboard(metaPrompt);
  const fileList = ruleFiles.join('\n- ');
  console.log(`Copied review prompt update payload (${ruleFiles.length} rule file(s)) to clipboard:\n- ${fileList}`);
}

function getWorkflowDocText() {
  return `# Code Review Workflow Guide

Universal cross-platform code review workflow for agent code generation.

## Directory Structure

- \`.code-review/REVIEW_PROMPT.md\` — Repository code-review prompt and checklist (versioned in Git).
- \`.code-review/CODE_REVIEW_WORKFLOW.md\` — Workflow guide and CLI documentation (versioned in Git).
- \`.code-review/state.json\` — Local SHA-256 hash cache for incremental review (ignored in Git).
- \`.code-review/payload.md\` — Temporary packed payload generated for reviewer chat (ignored in Git).

## Setup & Prerequisites

### 1. Installation

- **Web / Node.js Projects:** Install as devDependency:
  \`\`\`bash
  pnpm add -D @mudrichenkoevgeny/code-review
  \`\`\`
  Run via \`pnpm code-review <command>\` or \`npx code-review <command>\`.

- **Kotlin / KMP / Backend / Multi-repo Projects:**
  Run directly via \`dlx\` or \`npx\` without adding NPM dependencies:
  \`\`\`bash
  pnpm dlx @mudrichenkoevgeny/code-review <command>
  # or
  npx @mudrichenkoevgeny/code-review <command>
  \`\`\`
  Or install globally: \`npm install -g @mudrichenkoevgeny/code-review\`.

### 2. \`.gitignore\` Configuration

Ensure local state and payload files are ignored:
\`\`\`gitignore
.code-review/state.json
.code-review/payload.md
\`\`\`

## CLI Commands

- \`code-review init\`
  Initializes \`.code-review/\` directory, writes \`.code-review/CODE_REVIEW_WORKFLOW.md\`, and creates starter \`REVIEW_PROMPT.md\` if missing.
- \`code-review prompt\`
  Copies \`.code-review/REVIEW_PROMPT.md\` to system clipboard and writes \`.code-review/payload.md\`.
- \`code-review pack [paths...] [--all] [--mark]\`
  Packs unstaged and untracked git changes into \`.code-review/payload.md\` and copies to system clipboard.
  - Optional \`[paths...]\`: Filters changes by directory or file path prefixes.
  - \`--all\`: Forces packing all candidate files, ignoring cache.
  - \`--mark\`: Marks candidate files as reviewed without copying or writing payload.
- \`code-review update-prompt\`
  Scans \`AGENTS.md\` and \`.agent/**/*.md\` rule files, builds meta-prompt in \`.code-review/payload.md\`, and copies to clipboard.

Note: In headless or SSH environments where clipboard access is unavailable, payload text is safely saved to \`.code-review/payload.md\` for manual file attachment.

## Iterative Review Workflow

### Step 1: Initialize Reviewer Chat
Run \`code-review prompt\`. Paste clipboard (or attach \`.code-review/payload.md\`) into a fresh Reviewer agent chat window.
Wait for the handshake response: \`Ready to review.\`.

### Step 2: Pack Changes After Coding Agent Finishes
After Coding agent completes code modifications, run \`code-review pack\`.
Paste clipboard (or attach \`.code-review/payload.md\`) into the Reviewer agent chat.

### Step 3: Handle Review Verdict
- **Passed Files:** Run \`git add <file>\` to stage verified clean files. Staged files are automatically excluded from subsequent \`code-review pack\` runs.
- **Failed Files:** Copy English fix prompt (\`text\` code block) from Reviewer agent response into Coding agent chat.
- **Re-pack:** After Coding agent finishes fixes, run \`code-review pack\` again. Only newly modified unstaged files are packed.

### Step 4: Finalize
Repeat Step 3 until Reviewer agent responds with \`No issues found.\`.

## Keeping \`REVIEW_PROMPT.md\` in Sync

Whenever project standards, architecture, or \`.agent/**/*.md\` rules change:
1. Run \`code-review update-prompt\`.
2. Paste clipboard (or attach \`.code-review/payload.md\`) into an agent chat.
3. Save generated Markdown response to \`.code-review/REVIEW_PROMPT.md\`.

## Cache & Exclusions

- **Excluded from Pack:** Staged files, deleted files, \`.code-review/\` state, lockfiles (\`pnpm-lock.yaml\`),
  generated files (\`routeTree.gen.ts\`, \`gradle-wrapper.jar\`), and binary assets (\`png\`, \`jpg\`, \`webp\`, \`woff2\`, \`ttf\`, \`otf\`, \`ico\`, \`svg\`, \`jar\`, \`pdf\`, \`zip\`).
- **Resetting Cache:** Delete \`.code-review/state.json\` or pass \`--all\` to force packing all modified files.
`;
}

function getStarterPromptText() {
  return `# Code Review Prompt

> **NOTE:** This is a starter placeholder. Generate the full repository prompt by following these steps:
> 1. Run \`code-review update-prompt\`
> 2. Paste clipboard or attach \`.code-review/payload.md\` into an agent chat
> 3. Replace this file (\`.code-review/REVIEW_PROMPT.md\`) with the generated output

---

You are a Senior Code Reviewer.
Audit code batches for architectural violations, style compliance, type safety, error handling, and edge cases.

Confirm briefly with "Ready to review." and wait for code batches.
`;
}

function showHelp() {
  console.log(`
code-review — Universal Cross-Platform Code Review CLI

Usage:
  code-review init                     Initialize .code-review/ directory and documentation
  code-review prompt                     Copy current .code-review/REVIEW_PROMPT.md to clipboard
  code-review pack [paths...] [--all] [--mark]  Pack unstaged/untracked git changes for Code Review
  code-review update-prompt              Generate update meta-prompt for .code-review/REVIEW_PROMPT.md
  code-review --help                     Show this help message
`);
}

const args = process.argv.slice(2);
const subcommand = args[0];

if (!subcommand || subcommand === '--help' || subcommand === '-h') {
  showHelp();
  process.exit(0);
}

if (subcommand === 'init') {
  runInitCommand();
} else if (subcommand === 'prompt') {
  runPromptCommand();
} else if (subcommand === 'pack') {
  runPackCommand(args.slice(1));
} else if (subcommand === 'update-prompt') {
  runUpdatePromptCommand();
} else {
  console.error(`Unknown subcommand: ${subcommand}`);
  showHelp();
  process.exit(1);
}
