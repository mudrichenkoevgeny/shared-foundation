package io.github.mudrichenkoevgeny.shared.foundation.plugin

import com.vanniktech.maven.publish.JavaPlatform
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.dokka.gradle.DokkaExtension

/**
 * Gradle plugin configuring Vanniktech Maven Publishing, Dokka, and POM metadata
 * for JVM, KMP, and BOM (java-platform) modules.
 */
public class PublishConventionPlugin : Plugin<Project> {
    /**
     * Applies Maven Publishing, Dokka, and POM metadata configuration to the target Gradle project.
     *
     * @param target The target Gradle project to configure.
     */
    override fun apply(target: Project) {
        val explicitPublishFlag = (target.findProperty("publishToMaven") as? Boolean)
            ?: (target.findProperty("publishToMaven")?.toString()?.toBoolean())
        val isTool = target.path.startsWith(":tools")
        val shouldPublish = !isTool || explicitPublishFlag == true

        if (!shouldPublish) return

        val isBom = target.name == "bom"

        target.pluginManager.apply("com.vanniktech.maven.publish")

        if (!isBom) {
            target.pluginManager.apply("org.jetbrains.dokka")

            if (target != target.rootProject) {
                target.rootProject.dependencies.add("dokka", target)
            }

            target.extensions.configure(DokkaExtension::class.java) { dokka ->
                dokka.dokkaPublications.configureEach { publication ->
                    publication.failOnWarning.set(true)
                }
            }
        }

        target.extensions.configure(MavenPublishBaseExtension::class.java) { mavenPublish ->
            val customArtifactId = target.findProperty("artifactId") as? String
            val projectPathName = target.path.removePrefix(":").replace(":", "-").replace(".", "-")
            val rootProjectName = target.rootProject.name

            val newArtifactId = customArtifactId ?: if (projectPathName.startsWith(rootProjectName)) {
                projectPathName
            } else {
                "$rootProjectName-$projectPathName"
            }

            val repoUrl = (target.findProperty("pom.repoUrl") as? String)
                ?: "https://github.com/mudrichenkoevgeny/$rootProjectName"
            val gitConnection = (target.findProperty("pom.gitConnection") as? String)
                ?: "scm:git:git://github.com/mudrichenkoevgeny/$rootProjectName.git"
            val sshConnection = (target.findProperty("pom.sshConnection") as? String)
                ?: "scm:git:ssh://github.com/mudrichenkoevgeny/$rootProjectName.git"

            mavenPublish.coordinates(
                target.group.toString(),
                newArtifactId,
                target.version.toString()
            )

            mavenPublish.publishToMavenCentral()
            mavenPublish.signAllPublications()

            if (isBom || target.pluginManager.hasPlugin("java-platform")) {
                mavenPublish.configure(JavaPlatform())
            } else {
                target.plugins.withId("org.jetbrains.kotlin.multiplatform") {
                    mavenPublish.configure(KotlinMultiplatform(javadocJar = JavadocJar.Empty()))
                }
                target.plugins.withId("org.jetbrains.kotlin.jvm") {
                    mavenPublish.configure(KotlinJvm(javadocJar = JavadocJar.Javadoc()))
                }
            }

            mavenPublish.pom { pom ->
                pom.name.set("$rootProjectName - ${target.name}")
                pom.description.set("Module ${target.name} of $rootProjectName")
                pom.url.set(repoUrl)
                pom.licenses { licenses ->
                    licenses.license { license ->
                        license.name.set("The Apache License, Version 2.0")
                        license.url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                pom.developers { developers ->
                    developers.developer { developer ->
                        developer.id.set("mudrichenkoevgeny")
                        developer.name.set("Evgeny Mudrichenko")
                        developer.email.set("evgeny.mudrichenko@gmail.com")
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
}
