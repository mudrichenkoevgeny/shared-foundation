package io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin

import io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.designtokens.DesignTokensExtension
import io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.designtokens.ExtractDesignFontsTask
import io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.designtokens.GenerateDesignTokensTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

/**
 * Gradle plugin generating Compose Design Tokens and extracting design font assets.
 */
public class DesignTokensPlugin : Plugin<Project> {

    /**
     * Applies design token generation tasks and Kotlin Multiplatform sourceSet wiring to [target].
     *
     * @param target The target Gradle project to configure.
     */
    override fun apply(target: Project) {
        val extension = target.extensions.create(EXTENSION_NAME, DesignTokensExtension::class.java)
        extension.packageName.convention(DEFAULT_PACKAGE_NAME)
        extension.outputDir.convention(target.layout.buildDirectory.dir(DEFAULT_OUTPUT_DIR))
        extension.fontsOutputDir.convention(target.layout.projectDirectory.dir(DEFAULT_FONTS_OUTPUT_DIR))

        val generateTokensTask = target.tasks.register(
            TASK_GENERATE_DESIGN_TOKENS,
            GenerateDesignTokensTask::class.java
        ) { task ->
            task.group = TASK_GROUP_BUILD
            task.description = "Generates Compose Design Tokens Kotlin class from JSON resources."
            task.packageName.set(extension.packageName)
            task.outputDir.set(extension.outputDir)
        }

        val extractFontsTask = target.tasks.register(
            TASK_EXTRACT_DESIGN_FONTS,
            ExtractDesignFontsTask::class.java
        ) { task ->
            task.group = TASK_GROUP_BUILD
            task.description = "Extracts embedded design TTF fonts to Compose Multiplatform resources."
            task.fontsOutputDir.set(extension.fontsOutputDir)
        }

        target.pluginManager.withPlugin(PLUGIN_KOTLIN_MULTIPLATFORM) {
            target.extensions.configure(KotlinMultiplatformExtension::class.java) { kmp ->
                kmp.sourceSets.named(SOURCE_SET_COMMON_MAIN) { commonMain ->
                    commonMain.kotlin.srcDir(generateTokensTask.map { it.outputDir })
                }
            }
        }

        target.tasks.withType(KotlinCompilationTask::class.java).configureEach { compilationTask ->
            compilationTask.dependsOn(generateTokensTask)
            compilationTask.dependsOn(extractFontsTask)
        }

        target.tasks.matching { it.name == TASK_PREPARE_IDEA_IMPORT }.configureEach { ideaTask ->
            ideaTask.dependsOn(generateTokensTask)
            ideaTask.dependsOn(extractFontsTask)
        }

        target.tasks.matching { task ->
            val name = task.name
            name.contains("ComposeResources") ||
                name.contains("NonXmlValueResources") ||
                name.contains("ResourceAccessors")
        }.configureEach { composeResourceTask ->
            composeResourceTask.dependsOn(extractFontsTask)
            composeResourceTask.dependsOn(generateTokensTask)
        }
    }

    private companion object {
        private const val EXTENSION_NAME = "sharedDesignTokens"
        private const val DEFAULT_PACKAGE_NAME = "io.github.mudrichenkoevgeny.kmp.core.common.ui.theme.tokens"

        private const val DEFAULT_OUTPUT_DIR = "generated/sources/designTokens/kotlin/commonMain"
        private const val DEFAULT_FONTS_OUTPUT_DIR = "src/commonMain/composeResources/font"

        private const val TASK_GENERATE_DESIGN_TOKENS = "generateDesignTokens"
        private const val TASK_EXTRACT_DESIGN_FONTS = "extractDesignFonts"
        private const val TASK_GROUP_BUILD = "build"

        private const val PLUGIN_KOTLIN_MULTIPLATFORM = "org.jetbrains.kotlin.multiplatform"
        private const val SOURCE_SET_COMMON_MAIN = "commonMain"
        private const val TASK_PREPARE_IDEA_IMPORT = "prepareKotlinIdeaImport"
    }
}
