package io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin

import io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.agent.AgentSyncConfig
import io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.agent.AgentSyncTask
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Gradle plugin for synchronizing shared AI agent rules into consumer projects.
 */
public class AgentSyncPlugin : Plugin<Project> {
    /**
     * Applies the agent sync task configuration to the target Gradle project.
     *
     * @param target The target Gradle project to configure.
     */
    override fun apply(target: Project) {
        if (target != target.rootProject) {
            target.logger.warn("AgentSyncPlugin should only be applied to the root project")
            return
        }

        val config = target.extensions.create(EXTENSION_NAME, AgentSyncConfig::class.java)
        config.includeShared.convention(target.name == PROJECT_SHARED_FOUNDATION)

        val syncTask = target.tasks.register(TASK_SYNC_AGENT_RULES, AgentSyncTask::class.java) { task ->
            task.group = TASK_GROUP_DOCUMENTATION
            task.description = "Synchronizes shared AI agent rules into .agent/global/ directory"
            task.outputDirectory.convention(target.layout.projectDirectory.dir(DEFAULT_OUTPUT_DIR))
            task.includeShared.convention(config.includeShared)
        }

        target.tasks.matching {
            it.name == TASK_PREPARE_BUILD_SCRIPT_MODEL || it.name == TASK_PREPARE_IDEA_IMPORT
        }.configureEach { task ->
            task.dependsOn(syncTask)
        }
    }

    private companion object {
        private const val EXTENSION_NAME = "agentSync"
        private const val PROJECT_SHARED_FOUNDATION = "shared-foundation"

        private const val TASK_SYNC_AGENT_RULES = "syncAgentRules"
        private const val TASK_GROUP_DOCUMENTATION = "documentation"
        private const val DEFAULT_OUTPUT_DIR = ".agent/global"

        private const val TASK_PREPARE_BUILD_SCRIPT_MODEL = "prepareKotlinBuildScriptModel"
        private const val TASK_PREPARE_IDEA_IMPORT = "prepareKotlinIdeaImport"
    }
}
