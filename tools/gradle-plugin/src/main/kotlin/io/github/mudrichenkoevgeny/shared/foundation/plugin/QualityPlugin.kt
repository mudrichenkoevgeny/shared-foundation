package io.github.mudrichenkoevgeny.shared.foundation.plugin

import dev.detekt.gradle.Detekt
import dev.detekt.gradle.extensions.DetektExtension
import io.github.mudrichenkoevgeny.shared.foundation.plugin.utils.QualityUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceTask
import org.jlleitschuh.gradle.ktlint.KtlintExtension

/**
 * Gradle plugin configuring Ktlint and Detekt with shared base detekt-base.yml config.
 */
public class QualityPlugin : Plugin<Project> {
    /**
     * Applies Ktlint and Detekt configurations to the target Gradle project.
     *
     * @param target The target Gradle project to configure.
     */
    override fun apply(target: Project) {
        target.configureKtlint()
        target.configureDetekt()
        QualityUtils.registerEditorConfigLayer(
            target = target,
            classLoader = javaClass.classLoader,
            resourcePath = "config/editorconfig/editorconfig-base.txt"
        )
    }

    private fun Project.configureKtlint() {
        pluginManager.apply("org.jlleitschuh.gradle.ktlint")

        val isTool = path.startsWith(":tools")
        extensions.configure(KtlintExtension::class.java) { ktlint ->
            ktlint.android.set(true)
            ktlint.ignoreFailures.set(isTool)
            ktlint.filter { filter ->
                filter.exclude { file ->
                    val absolutePath = file.file.absolutePath
                    absolutePath.contains("build") || absolutePath.contains("generated")
                }
            }
        }

        tasks.configureEach { task ->
            if (task.name.contains("ktlint", ignoreCase = true) && task is SourceTask) {
                task.exclude { file ->
                    val path = file.file.absolutePath
                    path.contains("build") || path.contains("generated")
                }
            }
        }
    }

    private fun Project.configureDetekt() {
        pluginManager.apply("dev.detekt")

        val isIgnoredTool = path == ":tools:npm-generator"

        extensions.configure(DetektExtension::class.java) { detekt ->
            detekt.buildUponDefaultConfig.set(true)
            detekt.autoCorrect.set(true)
            detekt.allRules.set(false)
            detekt.ignoreFailures.set(isIgnoredTool)

            val srcDir = file("src")
            if (srcDir.exists()) {
                detekt.source.setFrom(files(srcDir))
            }
        }

        tasks.withType(Detekt::class.java).configureEach { detektTask ->
            detektTask.ignoreFailures.set(isIgnoredTool)
            detektTask.enabled = !isIgnoredTool
        }

        QualityUtils.registerDetektConfigExtraction(
            target = this,
            taskName = "extractDetektBaseConfig",
            classLoader = javaClass.classLoader,
            resourcePath = "config/detekt/detekt-base.yml",
            outputFileName = "detekt-base.yml"
        )

        val pluginVersion = QualityUtils.resolvePluginVersion(
            classLoader = javaClass.classLoader,
            versionResourceName = "shared-foundation-plugin-version.txt",
            manifestPackage = javaClass.`package`
        )

        QualityUtils.applyDetektRulesDependency(
            target = this,
            expectedRootProjectName = "shared-foundation",
            rulesProjectPath = ":tools:detekt-rules",
            mavenCoordinates = "io.github.mudrichenkoevgeny:shared-foundation-detekt-rules",
            pluginVersion = pluginVersion
        )
    }
}
