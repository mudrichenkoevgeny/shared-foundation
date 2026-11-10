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

        /**
         * Universal module for synchronizing agent rules into INIT_CWD of consumer projects.
         *
         * @param {Object} options
         * @param {string} options.sourceAgentDir - Absolute path to rules directory inside installed package
         * @param {string} options.relativeTargetSubdir - Relative target path in project
         * @param {string} options.packageName - Package name for logging
         */
        export function syncAgentRules({ sourceAgentDir, relativeTargetSubdir, packageName }) {
          const projectRoot = process.env.INIT_CWD || process.cwd();
          const packageDir = path.resolve(sourceAgentDir, '../../');

          if (projectRoot && path.resolve(projectRoot) !== packageDir && fs.existsSync(sourceAgentDir)) {
            const targetDir = path.join(projectRoot, relativeTargetSubdir);
            try {
              fs.mkdirSync(targetDir, { recursive: true });
            } catch (_) {}

            let attempts = 0;
            const maxAttempts = 3;
            while (attempts < maxAttempts) {
              try {
                fs.cpSync(sourceAgentDir, targetDir, { recursive: true, force: true });
                console.log(`✅ [${dollar}{packageName}] Agent rules synced -> ${dollar}{targetDir}`);
                break;
              } catch (err) {
                attempts++;
                if (attempts >= maxAttempts) {
                  console.warn(`⚠️ [${dollar}{packageName}] Could not sync agent rules to ${dollar}{targetDir}: ${dollar}{err.message}`);
                } else {
                  try {
                    Atomics.wait(new Int32Array(new SharedArrayBuffer(4)), 0, 0, 100);
                  } catch (_) {}
                }
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

        val agentOutputDir = File(outputDir, ".agent")
        agentOutputDir.mkdirs()

        val globalCommonDir = File(rootDir, ".agent/global/common")
        if (globalCommonDir.exists()) {
            globalCommonDir.copyRecursively(File(agentOutputDir, "common"), overwrite = true)
        }
    }
}
