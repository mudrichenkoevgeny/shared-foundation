import { execSync } from 'node:child_process';
import { existsSync, mkdirSync, readFileSync, rmSync, writeFileSync } from 'node:fs';
import { join } from 'node:path';
import { tmpdir } from 'node:os';
import { fileURLToPath } from 'node:url';
import assert from 'node:assert/strict';
import test from 'node:test';

const BIN_PATH = fileURLToPath(new URL('../bin/code-review.mjs', import.meta.url));

test('CLI script format and line endings', () => {
  const content = readFileSync(BIN_PATH, 'utf8');
  assert.ok(content.startsWith('#!/usr/bin/env node\n'), 'Must start with LF shebang');
  assert.ok(!content.includes('\r\n'), 'Must not contain CRLF');
});

test('code-review init in fresh repository', () => {
  const testDir = join(tmpdir(), `code-review-test-init-${Date.now()}`);
  mkdirSync(testDir, { recursive: true });
  try {
    execSync('git init', { cwd: testDir });
    execSync(`node "${BIN_PATH}" init`, { cwd: testDir });

    const workflowPath = join(testDir, '.code-review/CODE_REVIEW_WORKFLOW.md');
    const promptPath = join(testDir, '.code-review/REVIEW_PROMPT.md');

    assert.ok(existsSync(workflowPath), 'CODE_REVIEW_WORKFLOW.md should be created');
    assert.ok(existsSync(promptPath), 'REVIEW_PROMPT.md starter should be created');

    const promptText = readFileSync(promptPath, 'utf8');
    assert.ok(promptText.includes('This is a starter placeholder.'), 'Should contain starter note');

    writeFileSync(promptPath, '# Custom Review Prompt');
    execSync(`node "${BIN_PATH}" init`, { cwd: testDir });
    assert.equal(readFileSync(promptPath, 'utf8'), '# Custom Review Prompt', 'Should preserve existing prompt');
  } finally {
    rmSync(testDir, { recursive: true, force: true });
  }
});

test('code-review update-prompt', () => {
  const testDir = join(tmpdir(), `code-review-test-update-${Date.now()}`);
  mkdirSync(testDir, { recursive: true });
  try {
    execSync('git init', { cwd: testDir });
    mkdirSync(join(testDir, '.agent'), { recursive: true });
    writeFileSync(join(testDir, 'AGENTS.md'), '# Main Agents Rule');
    writeFileSync(join(testDir, '.agent/style.md'), '# Style Rule');

    execSync(`node "${BIN_PATH}" init`, { cwd: testDir });
    execSync(`node "${BIN_PATH}" update-prompt`, { cwd: testDir });

    const payloadPath = join(testDir, '.code-review/payload.md');
    assert.ok(existsSync(payloadPath), 'Payload file should exist');
    let payloadText = readFileSync(payloadPath, 'utf8');
    assert.ok(payloadText.includes('Create `.code-review/REVIEW_PROMPT.md` from scratch'), 'Should generate bootstrap meta-prompt');

    writeFileSync(join(testDir, '.code-review/REVIEW_PROMPT.md'), '# Real Prompt\nPassed review:');
    execSync(`node "${BIN_PATH}" update-prompt`, { cwd: testDir });
    payloadText = readFileSync(payloadPath, 'utf8');
    assert.ok(payloadText.includes('Update `.code-review/REVIEW_PROMPT.md` based on'), 'Should generate update meta-prompt');
  } finally {
    rmSync(testDir, { recursive: true, force: true });
  }
});

test('code-review pack', () => {
  const testDir = join(tmpdir(), `code-review-test-pack-${Date.now()}`);
  mkdirSync(testDir, { recursive: true });
  try {
    execSync('git init', { cwd: testDir });
    writeFileSync(join(testDir, 'test1.txt'), 'console.log("hello");');
    execSync(`node "${BIN_PATH}" pack`, { cwd: testDir });

    const payloadPath = join(testDir, '.code-review/payload.md');
    const statePath = join(testDir, '.code-review/state.json');

    assert.ok(existsSync(payloadPath), 'Payload file should be created');
    assert.ok(existsSync(statePath), 'State file should be created');

    const payloadText = readFileSync(payloadPath, 'utf8');
    assert.ok(payloadText.includes('test1.txt'), 'Payload should include test1.txt');
    assert.ok(payloadText.includes('console.log("hello");'), 'Payload should include file content');
  } finally {
    rmSync(testDir, { recursive: true, force: true });
  }
});
