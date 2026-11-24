package io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin

import io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.designsystem.DesignSystemExtension
import io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.designsystem.ExtractDesignFontsTask
import io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.designsystem.ExtractDesignIconsTask
import io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.designsystem.GenerateDesignTokensTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

/**
 * Gradle plugin generating Compose Design Tokens, extracting fonts, and extracting Vector Drawable icons.
 */
public class DesignSystemPlugin : Plugin<Project> {

    /**
     * Applies design system generation tasks and Kotlin Multiplatform sourceSet wiring to [target].
     *
     * @param target The target Gradle project to configure.
     */
    override fun apply(target: Project) {
        val extension = target.extensions.create(EXTENSION_NAME, DesignSystemExtension::class.java)
        extension.packageName.convention(DEFAULT_PACKAGE_NAME)
        extension.outputDir.convention(target.layout.buildDirectory.dir(DEFAULT_OUTPUT_DIR))
        extension.fontsOutputDir.convention(target.layout.projectDirectory.dir(DEFAULT_FONTS_OUTPUT_DIR))
        extension.generateTokens.convention(true)
        extension.extractFonts.convention(true)
        extension.iconModules.convention(emptyList())
        extension.iconsOutputDir.convention(target.layout.projectDirectory.dir(DEFAULT_ICONS_OUTPUT_DIR))

        val generateTokensTask = target.tasks.register(
            TASK_GENERATE_DESIGN_TOKENS,
            GenerateDesignTokensTask::class.java
        ) { task ->
            task.group = TASK_GROUP_BUILD
            task.description = "Generates Compose Design Tokens Kotlin class from JSON resources."
            task.onlyIf { extension.generateTokens.get() }
            task.packageName.set(extension.packageName)
            task.outputDir.set(extension.outputDir)
        }

        val extractFontsTask = target.tasks.register(
            TASK_EXTRACT_DESIGN_FONTS,
            ExtractDesignFontsTask::class.java
        ) { task ->
            task.group = TASK_GROUP_BUILD
            task.description = "Extracts embedded design TTF fonts to Compose Multiplatform resources."
            task.onlyIf { extension.extractFonts.get() }
            task.fontsOutputDir.set(extension.fontsOutputDir)
        }

        val extractIconsTask = target.tasks.register(
            TASK_EXTRACT_DESIGN_ICONS,
            ExtractDesignIconsTask::class.java
        ) { task ->
            task.group = TASK_GROUP_BUILD
            task.description = "Extracts Vector Drawable XML icons to Compose Multiplatform resources."
            task.onlyIf { extension.iconModules.get().isNotEmpty() }
            task.iconModules.set(extension.iconModules)
            task.iconsOutputDir.set(extension.iconsOutputDir)
        }

        target.pluginManager.withPlugin(PLUGIN_KOTLIN_MULTIPLATFORM) {
            target.extensions.configure(KotlinMultiplatformExtension::class.java) { kmp ->
                kmp.sourceSets.named(SOURCE_SET_COMMON_MAIN) { commonMain ->
                    commonMain.kotlin.srcDir(generateTokensTask.map { it.outputDir })
                }
            }
        }

        target.tasks.withType(KotlinCompilationTask::class.java).configureEach { compilationTask ->
            compilationTask.dependsOn(generateTokensTask, extractFontsTask, extractIconsTask)
        }

        target.tasks.matching { it.name == TASK_PREPARE_IDEA_IMPORT }.configureEach { ideaTask ->
            ideaTask.dependsOn(generateTokensTask, extractFontsTask, extractIconsTask)
        }

        target.tasks.matching { task ->
            val name = task.name
            name.contains("ComposeResources") ||
                name.contains("NonXmlValueResources") ||
                name.contains("ResourceAccessors")
        }.configureEach { composeResourceTask ->
            composeResourceTask.dependsOn(generateTokensTask, extractFontsTask, extractIconsTask)
        }

        target.tasks.matching { task ->
            val name = task.name
            name.startsWith("detekt") || name.startsWith("ktlint")
        }.configureEach { qualityTask ->
            qualityTask.mustRunAfter(extractFontsTask, generateTokensTask, extractIconsTask)
        }
    }

    private companion object {
        private const val EXTENSION_NAME = "sharedDesignSystem"
        private const val DEFAULT_PACKAGE_NAME = "io.github.mudrichenkoevgeny.kmp.core.common.ui.theme.tokens"

        private const val DEFAULT_OUTPUT_DIR = "generated/sources/designTokens/kotlin/commonMain"
        private const val DEFAULT_FONTS_OUTPUT_DIR = "src/commonMain/composeResources/font"
        private const val DEFAULT_ICONS_OUTPUT_DIR = "src/commonMain/composeResources/drawable"

        private const val TASK_GENERATE_DESIGN_TOKENS = "generateDesignTokens"
        private const val TASK_EXTRACT_DESIGN_FONTS = "extractDesignFonts"
        private const val TASK_EXTRACT_DESIGN_ICONS = "extractDesignIcons"
        private const val TASK_GROUP_BUILD = "build"

        private const val PLUGIN_KOTLIN_MULTIPLATFORM = "org.jetbrains.kotlin.multiplatform"
        private const val SOURCE_SET_COMMON_MAIN = "commonMain"
        private const val TASK_PREPARE_IDEA_IMPORT = "prepareKotlinIdeaImport"
    }
}
