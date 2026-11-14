package io.github.mudrichenkoevgeny.shared.foundation.generator.script

import java.io.File

/**
 * Generates reusable postinstall Node.js scripts and synchronizes agent rules into the NPM package.
 */
public object PostInstallScriptGenerator {

    /**
     * Generates the reusable JS module code for syncing agent rules.
     *
     * @return The complete JS code for `agent-rules-syncer.mjs`.
     */
    public fun generateAgentRulesSyncerScriptText(): String {
        val dollar = '$'
        return """
        import fs from 'node:fs';
        import path from 'node:path';

        function findProjectRoot(startDir) {
          let current = path.resolve(startDir);
          let fallbackPkgRoot = current;
          while (true) {
            if (!current.includes('node_modules')) {
              if (fs.existsSync(path.join(current, 'pnpm-workspace.yaml')) || fs.existsSync(path.join(current, '.git'))) {
                return current;
              }
              if (fs.existsSync(path.join(current, 'package.json'))) {
                fallbackPkgRoot = current;
              }
            }
            const parent = path.dirname(current);
            if (parent === current) return fallbackPkgRoot;
            current = parent;
          }
        }

        /**
         * Universal module for synchronizing agent rules into consumer projects.
         *
         * @param {Object} options
         * @param {string} options.sourceAgentDir - Absolute path to rules directory inside installed package
         * @param {string} options.relativeTargetSubdir - Relative target path in project
         * @param {string} options.packageName - Package name for logging
         * @param {string} [options.targetRootDir] - Explicit project root directory (optional)
         */
        export function syncAgentRules({ sourceAgentDir, relativeTargetSubdir, packageName, targetRootDir }) {
          if (!sourceAgentDir || !fs.existsSync(sourceAgentDir)) return;

          const projectRoot = targetRootDir
            ? path.resolve(targetRootDir)
            : findProjectRoot(process.env.INIT_CWD || process.cwd());

          if (!projectRoot || projectRoot.includes('node_modules')) return;

          const resolvedSource = path.resolve(sourceAgentDir);
          const targetDir = path.resolve(projectRoot, relativeTargetSubdir);

          if (resolvedSource === targetDir) return;

          let attempts = 0;
          const maxAttempts = 3;
          while (attempts < maxAttempts) {
            try {
              fs.rmSync(targetDir, { recursive: true, force: true });
              fs.mkdirSync(targetDir, { recursive: true });
              fs.cpSync(resolvedSource, targetDir, { recursive: true, force: true });
              console.log(`[${dollar}{packageName}] Agent rules synced -> ${dollar}{targetDir}`);
              break;
            } catch (err) {
              attempts++;
              if (attempts >= maxAttempts) {
                console.warn(`[${dollar}{packageName}] Could not sync agent rules to ${dollar}{targetDir}: ${dollar}{err.message}`);
              } else {
                try {
                  Atomics.wait(new Int32Array(new SharedArrayBuffer(4)), 0, 0, 100);
                } catch (_) {}
              }
            }
          }
        }
        """.trimIndent()
    }

    /**
     * Generates the JS code for the postinstall entrypoint script.
     *
     * @return The complete JS code for `postinstall-sync-rules.mjs`.
     */
    public fun generatePostInstallScriptText(): String =
        """
        #!/usr/bin/env node
        import path from 'node:path';
        import { fileURLToPath } from 'node:url';
        import { syncAgentRules } from './agent-rules-syncer.mjs';

        const __dirname = path.dirname(fileURLToPath(import.meta.url));

        syncAgentRules({
          sourceAgentDir: path.join(__dirname, '../.agent/common'),
          relativeTargetSubdir: '.agent/global/common',
          packageName: 'Shared Foundation'
        });
        """.trimIndent()

    /**
     * Copies agent rules from the repository root into the NPM package build output directory
     * and writes the postinstall scripts.
     *
     * @param rootDir The root directory of the shared-foundation project.
     * @param outputDir The build output directory for the NPM package.
     */
    public fun generateScriptAndSyncAgentRules(rootDir: File, outputDir: File) {
        val scriptsDir = File(outputDir, "scripts")
        scriptsDir.mkdirs()
        File(scriptsDir, "agent-rules-syncer.mjs").writeText(generateAgentRulesSyncerScriptText())
        File(scriptsDir, "postinstall-sync-rules.mjs").writeText(generatePostInstallScriptText())

        val agentCommonOutputDir = File(outputDir, ".agent/common")
        if (agentCommonOutputDir.exists()) {
            agentCommonOutputDir.deleteRecursively()
        }
        agentCommonOutputDir.mkdirs()

        var resolvedRootDir: File? = rootDir
        while (resolvedRootDir != null && !File(resolvedRootDir, ".agent/global/common").exists()) {
            resolvedRootDir = resolvedRootDir.parentFile
        }

        val globalCommonDir = File(resolvedRootDir ?: rootDir, ".agent/global/common")
        if (globalCommonDir.exists()) {
            globalCommonDir.copyRecursively(agentCommonOutputDir, overwrite = true)
        }
    }
}
