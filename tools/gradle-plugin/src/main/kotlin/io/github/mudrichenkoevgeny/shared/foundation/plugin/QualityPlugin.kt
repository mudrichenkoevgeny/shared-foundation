package io.github.mudrichenkoevgeny.shared.foundation.plugin

import dev.detekt.gradle.Detekt
import dev.detekt.gradle.extensions.DetektExtension
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceTask
import java.io.File

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
            if (task.name.startsWith("ktlint") && task is SourceTask) {
                task.exclude { file ->
                    val path = file.file.absolutePath
                    path.contains("build") || path.contains("generated")
                }
            }
        }
    }

    private fun Project.configureDetekt() {
        pluginManager.apply("dev.detekt")

        val extractConfigTask = tasks.register("extractDetektBaseConfig") { task ->
            val outputFile = layout.buildDirectory.file("generated/detekt/detekt-base.yml")
            task.outputs.file(outputFile)
            task.doLast {
                val file = outputFile.get().asFile
                file.parentFile.mkdirs()
                extractDetektBaseConfig(file)
            }
        }

        val localConfigFile = rootProject.file("config/detekt/detekt.yml")

        extensions.configure(DetektExtension::class.java) { detekt ->
            detekt.buildUponDefaultConfig.set(true)
            detekt.autoCorrect.set(true)
            detekt.allRules.set(false)

            val baseConfigProvider = extractConfigTask.map { it.outputs.files.singleFile }

            if (localConfigFile.exists()) {
                detekt.config.setFrom(files(baseConfigProvider, localConfigFile))
            } else {
                detekt.config.setFrom(files(baseConfigProvider))
            }

            val srcDir = file("src")
            if (srcDir.exists()) {
                detekt.source.setFrom(files(srcDir))
            }
        }

        val pluginVersion = resolvePluginVersion()
        val isSharedFoundationRepo = rootProject.name == "shared-foundation"

        if (!isSharedFoundationRepo) {
            dependencies.add(
                "detektPlugins",
                "io.github.mudrichenkoevgeny:shared-foundation-detekt-rules:$pluginVersion"
            )
        }

        if (path != ":tools:detekt-rules") {
            val detektRulesProject = rootProject.findProject(":tools:detekt-rules")
            if (detektRulesProject != null) {
                rootProject.evaluationDependsOn(":tools:detekt-rules")
                val jarTask = detektRulesProject.tasks.named("jar")
                val jarFiles = detektRulesProject.files(jarTask)

                dependencies.add("detektPlugins", jarFiles)

                tasks.withType(Detekt::class.java).configureEach { detektTask ->
                    detektTask.dependsOn(extractConfigTask)
                    detektTask.dependsOn(jarTask)
                }
            } else {
                tasks.withType(Detekt::class.java).configureEach { detektTask ->
                    detektTask.dependsOn(extractConfigTask)
                }
            }
        } else {
            tasks.withType(Detekt::class.java).configureEach { detektTask ->
                detektTask.dependsOn(extractConfigTask)
                detektTask.dependsOn("jar")
                detektTask.pluginClasspath.from(tasks.named("jar"))
            }
        }
    }

    private fun resolvePluginVersion(): String {
        val resourceStream = javaClass.classLoader.getResourceAsStream("plugin-version.txt")
        if (resourceStream != null) {
            val versionFromResource = resourceStream.bufferedReader().use { it.readText().trim() }
            if (versionFromResource.isNotBlank()) {
                return versionFromResource
            }
        }
        return javaClass.`package`?.implementationVersion ?: "0.1.8"
    }

    private fun extractDetektBaseConfig(targetFile: File) {
        val classLoader = javaClass.classLoader
        val resourceStream = classLoader.getResourceAsStream("config/detekt/detekt-base.yml")
            ?: classLoader.getResourceAsStream("detekt-base.yml")

        if (resourceStream != null) {
            resourceStream.use { input ->
                targetFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }
    }
}
