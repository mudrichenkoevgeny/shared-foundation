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
              const hasWorkspace = fs.existsSync(path.join(current, 'pnpm-workspace.yaml'));
              const hasGit = fs.existsSync(path.join(current, '.git'));
              if (hasWorkspace || hasGit) {
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
                const warnMsg = `[${dollar}{packageName}] Could not sync rules: ${dollar}{err.message}`;
                console.warn(warnMsg);
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
     * @param packageName Package name for logging (defaults to `"Shared Foundation"`).
     * @param relativeTargetSubdir Relative target directory path (defaults to `".agent/global/common"`).
     * @return The complete JS code for `postinstall-sync-rules.mjs`.
     */
    @Suppress("UNUSED_PARAMETER")
    public fun generatePostInstallScriptText(
        packageName: String = "Shared Foundation",
        relativeTargetSubdir: String = ".agent/global/common"
    ): String =
        """
        #!/usr/bin/env node
        import fs from 'node:fs';
        import path from 'node:path';
        import { fileURLToPath } from 'node:url';
        import { syncAgentRules } from './agent-rules-syncer.mjs';

        const __dirname = path.dirname(fileURLToPath(import.meta.url));
        const agentDir = path.join(__dirname, '../.agent');

        if (fs.existsSync(agentDir)) {
          const entries = fs.readdirSync(agentDir, { withFileTypes: true });
          for (const entry of entries) {
            if (entry.isDirectory()) {
              syncAgentRules({
                sourceAgentDir: path.join(agentDir, entry.name),
                relativeTargetSubdir: path.join('.agent/global', entry.name),
                packageName: '$packageName'
              });
            }
          }
        }
        """.trimIndent()

    /**
     * Copies agent rules from repository root into output directory and generates postinstall scripts.
     *
     * @param rootDir The root directory of the project.
     * @param outputDir The build output directory for the NPM package.
     * @param packageName Package display name for logging.
     * @param relativeTargetSubdir Relative target path in consumer projects.
     * @param excludedDirectoryNames Directory names to exclude when copying rules.
     * @param includedDirectoryNames Subdirectory names to explicitly include when copying rules.
     */
    public fun generateScriptAndSyncAgentRules(
        rootDir: File,
        outputDir: File,
        packageName: String = "Shared Foundation",
        relativeTargetSubdir: String = ".agent/global/common",
        excludedDirectoryNames: Set<String> = setOf("kotlin", "shared"),
        includedDirectoryNames: Set<String> = emptySet()
    ) {
        val scriptsDir = File(outputDir, "scripts")
        scriptsDir.mkdirs()
        File(scriptsDir, "agent-rules-syncer.mjs").writeText(generateAgentRulesSyncerScriptText())
        File(scriptsDir, "postinstall-sync-rules.mjs").writeText(
            generatePostInstallScriptText(packageName, relativeTargetSubdir)
        )

        val agentOutputDir = File(outputDir, ".agent")
        if (agentOutputDir.exists()) {
            agentOutputDir.deleteRecursively()
        }
        agentOutputDir.mkdirs()

        val sourceDir = resolveAgentSourceDirectory(rootDir)
        if (sourceDir.exists()) {
            copyAgentSubDirectories(sourceDir, agentOutputDir, excludedDirectoryNames, includedDirectoryNames)
        }
    }

    private fun resolveAgentSourceDirectory(rootDir: File): File {
        var resolvedRootDir: File? = rootDir
        while (resolvedRootDir != null && !File(resolvedRootDir, ".agent/global").exists()) {
            resolvedRootDir = resolvedRootDir.parentFile
        }
        val globalDir = File(resolvedRootDir ?: rootDir, ".agent/global")
        return if (globalDir.exists()) globalDir else File(rootDir, ".agent")
    }

    private fun copyAgentSubDirectories(
        sourceDir: File,
        agentOutputDir: File,
        excludedDirectoryNames: Set<String>,
        includedDirectoryNames: Set<String>
    ) {
        val topDirs = sourceDir.listFiles()?.filter { it.isDirectory } ?: return
        topDirs.forEach { topDir ->
            val topName = topDir.name
            val isTopExcluded = topName in excludedDirectoryNames
            val isTopIncluded = includedDirectoryNames.isEmpty() || topName in includedDirectoryNames
            if (!isTopExcluded && isTopIncluded) {
                val targetTopDir = File(agentOutputDir, topName)
                copySubDirFilteringExcluded(topDir, targetTopDir, excludedDirectoryNames)
            }
        }
    }

    private fun copySubDirFilteringExcluded(
        sourceDir: File,
        targetDir: File,
        excludedDirectoryNames: Set<String>
    ) {
        sourceDir.listFiles()?.forEach { child ->
            val childName = child.name
            if (child.isDirectory) {
                if (childName !in excludedDirectoryNames) {
                    val childTarget = File(targetDir, childName)
                    copySubDirFilteringExcluded(child, childTarget, excludedDirectoryNames)
                }
            } else {
                targetDir.mkdirs()
                child.copyTo(File(targetDir, childName), overwrite = true)
            }
        }
    }
}
