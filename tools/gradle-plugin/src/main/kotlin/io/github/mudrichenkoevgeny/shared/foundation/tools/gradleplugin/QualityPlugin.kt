package io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin

import dev.detekt.gradle.extensions.DetektExtension
import io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.utils.QualityUtils
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
            resourcePath = RESOURCE_EDITORCONFIG_BASE
        )
    }

    private fun Project.configureKtlint() {
        pluginManager.apply(PLUGIN_KTLINT)

        val isTool = path.startsWith(PATH_PREFIX_TOOLS)
        extensions.configure(KtlintExtension::class.java) { ktlint ->
            ktlint.android.set(true)
            ktlint.ignoreFailures.set(isTool)
            ktlint.filter { filter ->
                filter.exclude { file ->
                    val absolutePath = file.file.absolutePath
                    absolutePath.contains(PATH_DIR_BUILD) || absolutePath.contains(PATH_DIR_GENERATED)
                }
            }
        }

        tasks.configureEach { task ->
            if (task.name.contains(KTLINT_TASK_KEYWORD, ignoreCase = true) && task is SourceTask) {
                task.exclude { file ->
                    val path = file.file.absolutePath
                    path.contains(PATH_DIR_BUILD) || path.contains(PATH_DIR_GENERATED)
                }
            }
        }
    }

    private fun Project.configureDetekt() {
        pluginManager.apply(PLUGIN_DETEKT)

        extensions.configure(DetektExtension::class.java) { detekt ->
            detekt.buildUponDefaultConfig.set(true)
            detekt.autoCorrect.set(true)
            detekt.allRules.set(false)

            val srcDir = file(SRC_DIR_NAME)
            if (srcDir.exists()) {
                detekt.source.setFrom(files(srcDir))
            }
        }

        QualityUtils.registerDetektConfigExtraction(
            target = this,
            taskName = TASK_EXTRACT_DETEKT_BASE_CONFIG,
            classLoader = javaClass.classLoader,
            resourcePath = RESOURCE_DETEKT_BASE,
            outputFileName = FILE_DETEKT_BASE
        )

        val pluginVersion = QualityUtils.resolvePluginVersion(
            classLoader = javaClass.classLoader,
            versionResourceName = VERSION_RESOURCE_NAME,
            manifestPackage = javaClass.`package`
        )

        QualityUtils.applyDetektRulesDependency(
            target = this,
            expectedRootProjectName = PROJECT_SHARED_FOUNDATION,
            rulesProjectPath = RULES_PROJECT_PATH,
            mavenCoordinates = RULES_MAVEN_COORDINATES,
            pluginVersion = pluginVersion
        )
    }

    private companion object {
        private const val PLUGIN_KTLINT = "org.jlleitschuh.gradle.ktlint"
        private const val PLUGIN_DETEKT = "dev.detekt"

        private const val PATH_PREFIX_TOOLS = ":tools"
        private const val PATH_DIR_BUILD = "build"
        private const val PATH_DIR_GENERATED = "generated"
        private const val SRC_DIR_NAME = "src"

        private const val KTLINT_TASK_KEYWORD = "ktlint"

        private const val TASK_EXTRACT_DETEKT_BASE_CONFIG = "extractDetektBaseConfig"
        private const val FILE_DETEKT_BASE = "detekt-base.yml"
        private const val RESOURCE_DETEKT_BASE = "config/detekt/detekt-base.yml"
        private const val RESOURCE_EDITORCONFIG_BASE = "config/editorconfig/editorconfig-base.txt"

        private const val VERSION_RESOURCE_NAME = "shared-foundation-plugin-version.txt"
        private const val PROJECT_SHARED_FOUNDATION = "shared-foundation"

        private const val RULES_PROJECT_PATH = ":tools:detekt-rules"
        private const val RULES_MAVEN_COORDINATES = "io.github.mudrichenkoevgeny:shared-foundation-detekt-rules"
    }
}
