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
        val pluginId = "io.github.mudrichenkoevgeny.shared-foundation-agent-sync"

        if (!root.pluginManager.hasPlugin(pluginId)) {
            root.pluginManager.apply(pluginId)
        }

        root.tasks.named("syncAgentRules", AgentSyncTask::class.java).configure { task ->
            task.addLayer(classLoader, resourcePath, targetSubdir)
        }
    }
}
