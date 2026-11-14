package io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin

import com.vanniktech.maven.publish.GradlePlugin
import com.vanniktech.maven.publish.JavaPlatform
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SourcesJar
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.dokka.gradle.DokkaExtension

/**
 * Gradle plugin configuring Vanniktech Maven Publishing, Dokka, and POM metadata
 * for JVM, KMP, BOM (java-platform), and Gradle Plugin (java-gradle-plugin) modules.
 */
public class PublishConventionPlugin : Plugin<Project> {
    /**
     * Applies Maven Publishing, Dokka, and POM metadata configuration to the target Gradle project.
     *
     * @param target The target Gradle project to configure.
     */
    override fun apply(target: Project) {
        wireIncludedGradlePluginTasks(target.rootProject)

        val explicitPublishFlag = (target.findProperty(PROP_PUBLISH_TO_MAVEN) as? Boolean)
            ?: (target.findProperty(PROP_PUBLISH_TO_MAVEN)?.toString()?.toBoolean())
        val isTool = target.path.startsWith(PATH_PREFIX_TOOLS)
        val shouldPublish = !isTool || explicitPublishFlag == true

        if (!shouldPublish) return

        val isBom = target.name == MODULE_BOM

        target.pluginManager.apply(PLUGIN_MAVEN_PUBLISH)

        if (!isBom) {
            target.pluginManager.apply(PLUGIN_DOKKA)

            if (target != target.rootProject) {
                target.rootProject.dependencies.add(DEPENDENCY_CONFIGURATION_DOKKA, target)
            }

            target.pluginManager.withPlugin(PLUGIN_KOTLIN_MULTIPLATFORM) {
                target.extensions.configure(DokkaExtension::class.java) { dokka ->
                    dokka.dokkaPublications.configureEach { publication ->
                        publication.failOnWarning.set(false)
                    }
                }
            }

            target.pluginManager.withPlugin(PLUGIN_KOTLIN_JVM) {
                target.extensions.configure(DokkaExtension::class.java) { dokka ->
                    dokka.dokkaPublications.configureEach { publication ->
                        publication.failOnWarning.set(true)
                    }
                }
            }

            target.pluginManager.withPlugin(PLUGIN_JAVA_GRADLE_PLUGIN) {
                target.extensions.configure(DokkaExtension::class.java) { dokka ->
                    dokka.dokkaPublications.configureEach { publication ->
                        publication.failOnWarning.set(true)
                    }
                }
            }
        }

        target.extensions.configure(MavenPublishBaseExtension::class.java) { mavenPublish ->
            val customArtifactId = target.findProperty(PROP_ARTIFACT_ID) as? String
            val projectPathName = target.path.removePrefix(":").replace(":", "-").replace(".", "-")
            val rootProjectName = target.rootProject.name

            val newArtifactId = customArtifactId ?: when {
                projectPathName.isEmpty() -> rootProjectName
                projectPathName.startsWith(rootProjectName) -> projectPathName
                else -> "$rootProjectName-$projectPathName"
            }

            val defaultRepoName = rootProjectName.removeSuffix(SUFFIX_GRADLE_PLUGIN)
            val repoUrl = (target.findProperty(PROP_POM_REPO_URL) as? String)
                ?: "$BASE_GITHUB_URL$defaultRepoName"
            val gitConnection = (target.findProperty(PROP_POM_GIT_CONNECTION) as? String)
                ?: "$BASE_GIT_CONNECTION$defaultRepoName.git"
            val sshConnection = (target.findProperty(PROP_POM_SSH_CONNECTION) as? String)
                ?: "$BASE_SSH_CONNECTION$defaultRepoName.git"

            mavenPublish.coordinates(
                target.group.toString(),
                newArtifactId,
                target.version.toString()
            )

            mavenPublish.publishToMavenCentral()
            mavenPublish.signAllPublications()

            if (isBom || target.pluginManager.hasPlugin(PLUGIN_JAVA_PLATFORM)) {
                mavenPublish.configure(JavaPlatform())
            } else if (target.pluginManager.hasPlugin(PLUGIN_JAVA_GRADLE_PLUGIN)) {
                mavenPublish.configure(
                    GradlePlugin(
                        javadocJar = JavadocJar.Javadoc(),
                        sourcesJar = SourcesJar.Sources()
                    )
                )
            } else {
                target.plugins.withId(PLUGIN_KOTLIN_MULTIPLATFORM) {
                    mavenPublish.configure(KotlinMultiplatform(javadocJar = JavadocJar.Empty()))
                }
                target.plugins.withId(PLUGIN_KOTLIN_JVM) {
                    mavenPublish.configure(KotlinJvm(javadocJar = JavadocJar.Javadoc()))
                }
            }

            mavenPublish.pom { pom ->
                pom.name.set("$rootProjectName - ${target.name}")
                pom.description.set("Module ${target.name} of $defaultRepoName")
                pom.url.set(repoUrl)
                pom.licenses { licenses ->
                    licenses.license { license ->
                        license.name.set(LICENSE_NAME)
                        license.url.set(LICENSE_URL)
                    }
                }
                pom.developers { developers ->
                    developers.developer { developer ->
                        developer.id.set(DEVELOPER_ID)
                        developer.name.set(DEVELOPER_NAME)
                        developer.email.set(DEVELOPER_EMAIL)
                    }
                }
                pom.scm { scm ->
                    scm.connection.set(gitConnection)
                    scm.developerConnection.set(sshConnection)
                    scm.url.set(repoUrl)
                }
            }
        }
    }

    private fun wireIncludedGradlePluginTasks(rootProject: Project) {
        if (rootProject.extensions.extraProperties.has(EXTRA_INCLUDED_GRADLE_PLUGIN_WIRED)) return
        rootProject.extensions.extraProperties.set(EXTRA_INCLUDED_GRADLE_PLUGIN_WIRED, true)

        val pluginBuild = rootProject.gradle.includedBuilds.find {
            it.name.endsWith(INCLUDED_BUILD_GRADLE_PLUGIN_SUFFIX)
        } ?: return

        rootProject.tasks.matching { it.name == TASK_CHECK }.configureEach {
            it.dependsOn(pluginBuild.task(":$TASK_CHECK"))
        }
        rootProject.tasks.matching { it.name == TASK_PUBLISH_TO_MAVEN_LOCAL }.configureEach {
            it.dependsOn(pluginBuild.task(":$TASK_PUBLISH_TO_MAVEN_LOCAL"))
        }
    }

    private companion object {
        private const val PROP_PUBLISH_TO_MAVEN = "publishToMaven"
        private const val PROP_ARTIFACT_ID = "artifactId"
        private const val PROP_POM_REPO_URL = "pom.repoUrl"
        private const val PROP_POM_GIT_CONNECTION = "pom.gitConnection"
        private const val PROP_POM_SSH_CONNECTION = "pom.sshConnection"

        private const val PATH_PREFIX_TOOLS = ":tools"
        private const val MODULE_BOM = "bom"
        private const val SUFFIX_GRADLE_PLUGIN = "-gradle-plugin"

        private const val PLUGIN_MAVEN_PUBLISH = "com.vanniktech.maven.publish"
        private const val PLUGIN_DOKKA = "org.jetbrains.dokka"
        private const val PLUGIN_KOTLIN_MULTIPLATFORM = "org.jetbrains.kotlin.multiplatform"
        private const val PLUGIN_KOTLIN_JVM = "org.jetbrains.kotlin.jvm"
        private const val PLUGIN_JAVA_GRADLE_PLUGIN = "java-gradle-plugin"
        private const val PLUGIN_JAVA_PLATFORM = "java-platform"

        private const val DEPENDENCY_CONFIGURATION_DOKKA = "dokka"

        private const val BASE_GITHUB_URL = "https://github.com/mudrichenkoevgeny/"
        private const val BASE_GIT_CONNECTION = "scm:git:git://github.com/mudrichenkoevgeny/"
        private const val BASE_SSH_CONNECTION = "scm:git:ssh://github.com/mudrichenkoevgeny/"

        private const val LICENSE_NAME = "The Apache License, Version 2.0"
        private const val LICENSE_URL = "http://www.apache.org/licenses/LICENSE-2.0.txt"

        private const val DEVELOPER_ID = "mudrichenkoevgeny"
        private const val DEVELOPER_NAME = "Evgeny Mudrichenko"
        private const val DEVELOPER_EMAIL = "evgeny.mudrichenko@gmail.com"

        private const val EXTRA_INCLUDED_GRADLE_PLUGIN_WIRED = "includedGradlePluginWired"
        private const val INCLUDED_BUILD_GRADLE_PLUGIN_SUFFIX = "gradle-plugin"

        private const val TASK_CHECK = "check"
        private const val TASK_PUBLISH_TO_MAVEN_LOCAL = "publishToMavenLocal"
    }
}
