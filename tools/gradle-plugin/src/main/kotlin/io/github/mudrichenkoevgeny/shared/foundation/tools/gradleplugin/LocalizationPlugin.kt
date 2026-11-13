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
        val extension = target.extensions.create("sharedLocalization", LocalizationExtension::class.java).apply {
            basePackageName.convention("io.github.mudrichenkoevgeny.shared.foundation")
            resourcesDir.convention(target.layout.projectDirectory.dir("src/commonMain/resources"))
            outputDir.convention(target.layout.buildDirectory.dir("generated/sources/localization/kotlin/commonMain"))
        }

        val generateTask = target.tasks.register("generateLocalization", GenerateLocalizationTask::class.java) { task ->
            task.group = "build"
            task.description = "Generates type-safe Kotlin localization strings from JSON resources."
            task.basePackageName.set(extension.basePackageName)
            task.resourcesDir.set(extension.resourcesDir)
            task.outputDir.set(extension.outputDir)
        }

        target.pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
            target.extensions.configure(KotlinMultiplatformExtension::class.java) { kmp ->
                kmp.sourceSets.named("commonMain") { commonMain ->
                    commonMain.kotlin.srcDir(generateTask.map { it.outputDir })
                }
            }
        }

        target.tasks.withType(KotlinCompilationTask::class.java).configureEach { compilationTask ->
            compilationTask.dependsOn(generateTask)
        }

        target.tasks.matching { it.name == "prepareKotlinIdeaImport" }.configureEach { ideaTask ->
            ideaTask.dependsOn(generateTask)
        }
    }
}
