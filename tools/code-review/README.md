# @mudrichenkoevgeny/code-review

**Universal Cross-Platform Code Review CLI** for agent code generation.

A zero-dependency Node.js CLI tool that packs git changes into reviewer-friendly Markdown prompts, maintains hash-based review state, and generates custom repository prompt checklists.

---

## Installation & Setup

### Option 1: Web / Node.js Projects
Install as a dev dependency:

```bash
pnpm add -D @mudrichenkoevgeny/code-review
# or
npm install -D @mudrichenkoevgeny/code-review
```

Run via `pnpm code-review <command>` or `npx code-review <command>`.

### Option 2: Kotlin / KMP / Backend / Multi-repo Projects
Run directly without installing as a project dependency:

```bash
pnpm dlx @mudrichenkoevgeny/code-review <command>
# or
npx @mudrichenkoevgeny/code-review <command>
```

Or install globally: `npm install -g @mudrichenkoevgeny/code-review`.

---

## Commands

- **`code-review init`**
  Initializes `.code-review/` in the repository root, writes `.code-review/CODE_REVIEW_WORKFLOW.md`, and creates a starter `.code-review/REVIEW_PROMPT.md` if missing (preserves existing prompt files).

- **`code-review prompt`**
  Copies `.code-review/REVIEW_PROMPT.md` to the system clipboard and writes `.code-review/payload.md`.

- **`code-review pack [paths...] [--all] [--mark]`**
  Scans `git status --porcelain -uall` for unstaged/untracked changes, calculates SHA-256 hashes against `.code-review/state.json`, writes `.code-review/payload.md`, and copies the packed payload to clipboard.
  - Optional `[paths...]`: Filters changes by directory/file path prefixes.
  - `--all`: Forces packing all candidate files regardless of cache.
  - `--mark`: Updates `.code-review/state.json` without writing payload or copying.

- **`code-review update-prompt`**
  Discovers `AGENTS.md` and `.agent/**/*.md` rule files. Generates an update meta-prompt (or bootstrap meta-prompt) in `.code-review/payload.md` and copies to clipboard.

---

## Cross-Platform Clipboard Support

Native clipboard copying is supported out of the box across:
- **Windows:** PowerShell UTF-8 `Set-Clipboard`.
- **macOS:** `pbcopy`.
- **Linux:** `wl-copy` -> `xclip` -> `xsel`.

In headless or SSH environments where clipboard tools are unavailable, `code-review` outputs a non-fatal warning and saves the full payload to `.code-review/payload.md` for manual file attachment.

---

## License

Apache-2.0
