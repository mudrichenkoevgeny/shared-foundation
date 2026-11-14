package io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.utils

import io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.agent.AgentSyncTask
import org.gradle.api.Project

/**
 * Utility object for registering extra AI agent rule layers across Gradle plugins.
 */
public object AgentSyncUtils {

    /**
     * Ensures agent sync plugin is applied to the root project and registers an additional rules layer.
     *
     * @param target Target project.
     * @param classLoader ClassLoader containing agent rules resources.
     * @param resourcePath Path to agent rules resource directory inside ClassLoader.
     * @param targetSubdir Target subdirectory inside .agent/global/.
     */
    public fun registerAgentRulesLayer(
        target: Project,
        classLoader: ClassLoader,
        resourcePath: String,
        targetSubdir: String
    ) {
        val root = target.rootProject

        if (!root.pluginManager.hasPlugin(PLUGIN_ID_AGENT_SYNC)) {
            root.pluginManager.apply(PLUGIN_ID_AGENT_SYNC)
        }

        root.tasks.named(TASK_SYNC_AGENT_RULES, AgentSyncTask::class.java).configure { task ->
            task.addLayer(classLoader, resourcePath, targetSubdir)
        }
    }

    private const val PLUGIN_ID_AGENT_SYNC = "io.github.mudrichenkoevgeny.shared-foundation-agent-sync"
    private const val TASK_SYNC_AGENT_RULES = "syncAgentRules"
}
