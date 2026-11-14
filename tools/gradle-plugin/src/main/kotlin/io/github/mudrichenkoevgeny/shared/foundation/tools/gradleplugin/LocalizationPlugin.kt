package io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin

import io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.localization.GenerateLocalizationTask
import io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.localization.LocalizationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

/**
 * Gradle plugin configuring automatic type-safe Kotlin localization generation from JSON resources.
 */
public class LocalizationPlugin : Plugin<Project> {
    /**
     * Applies localization generation task and Kotlin Multiplatform sourceSet wiring to [target].
     *
     * @param target The target Gradle project to configure.
     */
    override fun apply(target: Project) {
        val extension = target.extensions.create(EXTENSION_NAME, LocalizationExtension::class.java).apply {
            basePackageName.convention(DEFAULT_BASE_PACKAGE_NAME)
            resourcesDir.convention(target.layout.projectDirectory.dir(DEFAULT_RESOURCES_DIR))
            outputDir.convention(target.layout.buildDirectory.dir(DEFAULT_OUTPUT_DIR))
        }

        val generateTask = target.tasks.register(
            TASK_GENERATE_LOCALIZATION,
            GenerateLocalizationTask::class.java
        ) { task ->
            task.group = TASK_GROUP_BUILD
            task.description = "Generates type-safe Kotlin localization strings from JSON resources."
            task.basePackageName.set(extension.basePackageName)
            task.resourcesDir.set(extension.resourcesDir)
            task.outputDir.set(extension.outputDir)
        }

        target.pluginManager.withPlugin(PLUGIN_KOTLIN_MULTIPLATFORM) {
            target.extensions.configure(KotlinMultiplatformExtension::class.java) { kmp ->
                kmp.sourceSets.named(SOURCE_SET_COMMON_MAIN) { commonMain ->
                    commonMain.kotlin.srcDir(generateTask.map { it.outputDir })
                }
            }
        }

        target.tasks.withType(KotlinCompilationTask::class.java).configureEach { compilationTask ->
            compilationTask.dependsOn(generateTask)
        }

        target.tasks.matching { it.name == TASK_PREPARE_IDEA_IMPORT }.configureEach { ideaTask ->
            ideaTask.dependsOn(generateTask)
        }
    }

    private companion object {
        private const val EXTENSION_NAME = "sharedLocalization"
        private const val DEFAULT_BASE_PACKAGE_NAME = "io.github.mudrichenkoevgeny.shared.foundation"

        private const val DEFAULT_RESOURCES_DIR = "src/commonMain/resources"
        private const val DEFAULT_OUTPUT_DIR = "generated/sources/localization/kotlin/commonMain"

        private const val TASK_GENERATE_LOCALIZATION = "generateLocalization"
        private const val TASK_GROUP_BUILD = "build"

        private const val PLUGIN_KOTLIN_MULTIPLATFORM = "org.jetbrains.kotlin.multiplatform"
        private const val SOURCE_SET_COMMON_MAIN = "commonMain"
        private const val TASK_PREPARE_IDEA_IMPORT = "prepareKotlinIdeaImport"
    }
}
