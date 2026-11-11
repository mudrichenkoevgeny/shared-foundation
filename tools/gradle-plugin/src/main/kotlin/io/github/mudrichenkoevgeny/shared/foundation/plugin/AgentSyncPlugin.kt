package io.github.mudrichenkoevgeny.shared.foundation.plugin

import io.github.mudrichenkoevgeny.shared.foundation.plugin.agent.AgentSyncConfig
import io.github.mudrichenkoevgeny.shared.foundation.plugin.agent.AgentSyncTask
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

        val config = target.extensions.create("agentSync", AgentSyncConfig::class.java)
        config.includeShared.convention(target.name == "shared-foundation")

        val syncTask = target.tasks.register("syncAgentRules", AgentSyncTask::class.java) { task ->
            task.group = "documentation"
            task.description = "Synchronizes shared AI agent rules into .agent/global/ directory"
            task.outputDirectory.convention(target.layout.projectDirectory.dir(".agent/global"))
            task.includeShared.convention(config.includeShared)
        }

        target.tasks.matching {
            it.name == "prepareKotlinBuildScriptModel" || it.name == "prepareKotlinIdeaImport"
        }.configureEach { task ->
            task.dependsOn(syncTask)
        }
    }
}
