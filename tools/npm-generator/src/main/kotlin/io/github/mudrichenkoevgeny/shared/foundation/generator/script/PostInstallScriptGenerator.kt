package io.github.mudrichenkoevgeny.shared.foundation.generator.script

import java.io.File

/**
 * Generates reusable postinstall Node.js scripts and synchronizes agent rules into the NPM package.
 */
public object PostInstallScriptGenerator {

    private const val DEFAULT_FOUNDATION_PACKAGE_NAME = "Shared Foundation"

    private const val SUBDIR_KOTLIN = "kotlin"
    private const val SUBDIR_SHARED = "shared"

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
     * @param packageName Package name for logging (defaults to "Shared Foundation").
     * @return The complete JS code for `postinstall-sync-rules.mjs`.
     */
    public fun generatePostInstallScriptText(packageName: String = DEFAULT_FOUNDATION_PACKAGE_NAME): String {
        val dollar = '$'
        return """
        #!/usr/bin/env node
        import fs from 'node:fs';
        import path from 'node:path';
        import { fileURLToPath } from 'node:url';
        import { syncAgentRules } from './agent-rules-syncer.mjs';

        const __dirname = path.dirname(fileURLToPath(import.meta.url));
        const agentOutputDir = path.join(__dirname, '../.agent');

        if (fs.existsSync(agentOutputDir)) {
          const entries = fs.readdirSync(agentOutputDir, { withFileTypes: true });
          for (const entry of entries) {
            if (entry.isDirectory()) {
              syncAgentRules({
                sourceAgentDir: path.join(agentOutputDir, entry.name),
                relativeTargetSubdir: `.agent/global/${dollar}{entry.name}`,
                packageName: '$packageName'
              });
            }
          }
        }
        """.trimIndent()
    }

    /**
     * Copies agent rules from the repository root into the NPM package build output directory
     * and writes the postinstall scripts.
     *
     * @param rootDir The root directory of the project.
     * @param outputDir The build output directory for the NPM package.
     * @param packageName The package name for logging in postinstall script.
     * @param excludedDirectoryNames Set of rule directory names to exclude.
     * @param includedDirectoryNames Optional set of rule directory names to include.
     */
    public fun generateScriptAndSyncAgentRules(
        rootDir: File,
        outputDir: File,
        packageName: String = DEFAULT_FOUNDATION_PACKAGE_NAME,
        excludedDirectoryNames: Set<String> = setOf(SUBDIR_KOTLIN, SUBDIR_SHARED),
        includedDirectoryNames: Set<String>? = null
    ) {
        val scriptsDir = File(outputDir, "scripts")
        scriptsDir.mkdirs()
        File(scriptsDir, "agent-rules-syncer.mjs").writeText(generateAgentRulesSyncerScriptText())
        File(scriptsDir, "postinstall-sync-rules.mjs").writeText(generatePostInstallScriptText(packageName))

        val agentOutputDir = File(outputDir, ".agent")
        if (agentOutputDir.exists()) {
            agentOutputDir.deleteRecursively()
        }
        agentOutputDir.mkdirs()

        val globalAgentDir = File(rootDir, ".agent/global")
        if (globalAgentDir.exists()) {
            val subdirs = globalAgentDir.listFiles { file -> file.isDirectory }
            if (subdirs != null) {
                for (subdir in subdirs) {
                    val name = subdir.name
                    val isIncluded = includedDirectoryNames == null || name in includedDirectoryNames
                    val isExcluded = name in excludedDirectoryNames

                    if (isIncluded && !isExcluded) {
                        val targetSubdir = File(agentOutputDir, name)
                        copyDirectoryFilteringExclusions(subdir, targetSubdir, excludedDirectoryNames)
                    }
                }
            }
        }
    }

    private fun copyDirectoryFilteringExclusions(
        source: File,
        target: File,
        excludedDirectoryNames: Set<String>
    ) {
        if (!source.exists() || !source.isDirectory) return
        target.mkdirs()

        val files = source.listFiles() ?: return
        for (file in files) {
            val destFile = File(target, file.name)
            if (file.isDirectory) {
                if (file.name in excludedDirectoryNames) {
                    continue
                }
                copyDirectoryFilteringExclusions(file, destFile, excludedDirectoryNames)
            } else {
                file.copyTo(destFile, overwrite = true)
            }
        }
    }
}
