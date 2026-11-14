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
import java.io.File
import java.util.Properties

/**
 * Gradle plugin configuring Kotlin compiler infrastructure invariants (project coordinates, toolchain,
 * explicitApi, target JVM, allWarningsAsErrors) for both JVM and KMP modules.
 */
public class KotlinConventionPlugin : Plugin<Project> {
    /**
     * Applies Kotlin compiler, toolchain, and release target configurations to the target Gradle project.
     *
     * @param target The target Gradle project to configure.
     */
    override fun apply(target: Project) {
        val rootProps = resolveRootGradleProperties(target)
        configureProjectCoordinatesIfMissing(target, rootProps)

        val daemonJvmVersion = resolveDaemonJvmVersion(target)
        val targetJvmVersion = resolveTargetJvmVersion(target, rootProps, daemonJvmVersion)
        val kotlinJvmTarget = JvmTarget.fromTarget(targetJvmVersion.toString())

        target.plugins.withId(PLUGIN_KOTLIN_JVM) {
            target.extensions.configure(KotlinJvmProjectExtension::class.java) { kotlin ->
                kotlin.explicitApi()
                kotlin.jvmToolchain(daemonJvmVersion)
                kotlin.compilerOptions {
                    jvmTarget.set(kotlinJvmTarget)
                }
            }
        }

        target.plugins.withId(PLUGIN_KOTLIN_MULTIPLATFORM) {
            target.extensions.configure(KotlinMultiplatformExtension::class.java) { kotlin ->
                kotlin.explicitApi()
                kotlin.jvmToolchain(daemonJvmVersion)
                kotlin.targets.withType(KotlinJvmTarget::class.java).configureEach { jvmTarget ->
                    jvmTarget.compilerOptions {
                        this.jvmTarget.set(kotlinJvmTarget)
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
            javaCompile.options.release.set(targetJvmVersion)
        }

        target.tasks.withType(Test::class.java).configureEach { testTask ->
            testTask.jvmArgs(JVM_ARG_DYNAMIC_AGENT_LOADING)
        }
    }

    private fun configureProjectCoordinatesIfMissing(
        target: Project,
        rootProps: Properties
    ) {
        if (target.group.toString().isEmpty()) {
            val group = rootProps.getProperty(PROP_GROUP)?.takeIf { it.isNotBlank() }
                ?: error("Missing '$PROP_GROUP' property in $GRADLE_PROPERTIES_PATH for project '${target.path}'.")
            target.group = group
        }
        if (target.version.toString() == Project.DEFAULT_VERSION) {
            val version = rootProps.getProperty(PROP_VERSION)?.takeIf { it.isNotBlank() }
                ?: error("Missing '$PROP_VERSION' property in $GRADLE_PROPERTIES_PATH for project '${target.path}'.")
            target.version = version
        }
    }

    private fun resolveDaemonJvmVersion(target: Project): Int {
        val daemonFile = findFileUpwards(target.rootProject.projectDir, DAEMON_JVM_PROPERTIES_PATH)
            ?: error(
                "Missing '$DAEMON_JVM_PROPERTIES_PATH' in project '${target.rootProject.name}' " +
                    "(searched upwards from ${target.rootProject.projectDir.absolutePath})."
            )

        val properties = loadProperties(daemonFile)
        val rawVersion = properties.getProperty(PROP_TOOLCHAIN_VERSION)?.takeIf { it.isNotBlank() }
            ?: error("Missing '$PROP_TOOLCHAIN_VERSION' in ${daemonFile.absolutePath}.")

        return rawVersion.toIntOrNull()
            ?: error("Invalid '$PROP_TOOLCHAIN_VERSION' value '$rawVersion' in ${daemonFile.absolutePath}.")
    }

    private fun resolveTargetJvmVersion(
        target: Project,
        rootProps: Properties,
        defaultVersion: Int
    ): Int {
        val rawTarget = target.providers.gradleProperty(PROP_TARGET_JVM_VERSION).orNull
            ?: (target.findProperty(PROP_TARGET_JVM_VERSION) as? String)
            ?: rootProps.getProperty(PROP_TARGET_JVM_VERSION)

        if (rawTarget.isNullOrBlank()) {
            return defaultVersion
        }

        return rawTarget.toIntOrNull()
            ?: error("Invalid '$PROP_TARGET_JVM_VERSION' value '$rawTarget'. Expected an integer (e.g. 21, 25).")
    }

    private fun resolveRootGradleProperties(target: Project): Properties {
        val propsFile = generateSequence(target.rootProject.projectDir) { it.parentFile }
            .map { it.resolve(GRADLE_PROPERTIES_PATH) }
            .filter { it.isFile }
            .map { file -> file to loadProperties(file) }
            .firstOrNull { (_, props) ->
                !props.getProperty(PROP_GROUP).isNullOrBlank() &&
                    !props.getProperty(PROP_VERSION).isNullOrBlank()
            }?.first
            ?: error(
                "Missing root '$GRADLE_PROPERTIES_PATH' containing '$PROP_GROUP' and '$PROP_VERSION' " +
                    "for project '${target.rootProject.name}' " +
                    "(searched upwards from ${target.rootProject.projectDir.absolutePath})."
            )
        return loadProperties(propsFile)
    }

    private fun findFileUpwards(
        startDir: File,
        relativePath: String
    ): File? {
        return generateSequence(startDir) { it.parentFile }
            .map { it.resolve(relativePath) }
            .firstOrNull { it.isFile }
    }

    private fun loadProperties(file: File): Properties {
        return Properties().apply {
            file.inputStream().use { load(it) }
        }
    }

    private companion object {
        private const val GRADLE_PROPERTIES_PATH = "gradle.properties"
        private const val DAEMON_JVM_PROPERTIES_PATH = "gradle/gradle-daemon-jvm.properties"

        private const val PROP_GROUP = "group"
        private const val PROP_VERSION = "version"
        private const val PROP_TOOLCHAIN_VERSION = "toolchainVersion"
        private const val PROP_TARGET_JVM_VERSION = "targetJvmVersion"

        private const val PLUGIN_KOTLIN_JVM = "org.jetbrains.kotlin.jvm"
        private const val PLUGIN_KOTLIN_MULTIPLATFORM = "org.jetbrains.kotlin.multiplatform"

        private const val JVM_ARG_DYNAMIC_AGENT_LOADING = "-XX:+EnableDynamicAgentLoading"
    }
}
