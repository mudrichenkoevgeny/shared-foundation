package io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import java.util.Properties

/**
 * Gradle plugin configuring Kotlin compiler infrastructure invariants (toolchain, explicitApi, target Jvm,
 * allWarningsAsErrors) for both JVM and KMP (Kotlin Multiplatform) modules using lazy Gradle Provider APIs.
 */
public class KotlinConventionPlugin : Plugin<Project> {
    /**
     * Applies Kotlin compiler, toolchain, and release target configurations to the target Gradle project.
     *
     * @param target The target Gradle project to configure.
     */
    override fun apply(target: Project) {
        val daemonJvmVersion = resolveDaemonJvmVersion(target)
        val targetJvmVersionProvider = target.providers.gradleProperty("targetJvmVersion")
            .orElse(target.providers.provider { (target.findProperty("targetJvmVersion") as? String) ?: "21" })
        val targetJvmProvider = targetJvmVersionProvider.map { JvmTarget.fromTarget(it) }

        target.plugins.withId("org.jetbrains.kotlin.jvm") {
            target.extensions.configure(KotlinJvmProjectExtension::class.java) { kotlin ->
                kotlin.explicitApi()
                kotlin.jvmToolchain(daemonJvmVersion)
                kotlin.compilerOptions {
                    jvmTarget.set(targetJvmProvider)
                }
            }
        }

        target.plugins.withId("org.jetbrains.kotlin.multiplatform") {
            target.extensions.configure(KotlinMultiplatformExtension::class.java) { kotlin ->
                kotlin.explicitApi()
                kotlin.jvmToolchain(daemonJvmVersion)
                kotlin.targets.withType(KotlinJvmTarget::class.java).configureEach { jvmTarget ->
                    jvmTarget.compilerOptions {
                        this.jvmTarget.set(targetJvmProvider)
                    }
                }
            }
        }

        target.tasks.withType(KotlinCompilationTask::class.java).configureEach { kotlinCompile ->
            kotlinCompile.compilerOptions {
                allWarningsAsErrors.set(true)
            }
        }

        target.tasks.withType(JavaCompile::class.java).configureEach { javaCompile ->
            javaCompile.options.release.set(targetJvmVersionProvider.map { it.toInt() })
        }

        target.tasks.withType(Test::class.java).configureEach { testTask ->
            testTask.jvmArgs("-XX:+EnableDynamicAgentLoading")
        }
    }

    private fun resolveDaemonJvmVersion(target: Project): Int {
        val daemonFile = target.rootProject.file("gradle/gradle-daemon-jvm.properties")
        if (daemonFile.exists()) {
            val properties = Properties().apply {
                daemonFile.inputStream().use { load(it) }
            }
            val versionFromProperties = properties.getProperty("toolchainVersion")?.toIntOrNull()
            if (versionFromProperties != null) {
                return versionFromProperties
            }
        }
        val propertyVersion = (target.findProperty("jvmToolchainVersion") as? String)?.toIntOrNull()
        return propertyVersion ?: 21
    }
}
