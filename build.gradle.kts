import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.maven.publish) apply false
    alias(libs.plugins.dependency.analysis)
}

allprojects {
    group = "io.github.mudrichenkoevgeny"
    version = "0.0.35"
}

subprojects {
    val isBom = project.name == "bom"
    val isModule = project.projectDir.resolve("src").exists() || isBom

    if (!isModule) {
        return@subprojects
    }

    apply(plugin = "com.vanniktech.maven.publish")
    apply(plugin = "com.autonomousapps.dependency-analysis")

    val uuidExperimentalOptIn = "-opt-in=kotlin.uuid.ExperimentalUuidApi"
    val uuidExperimentalOptInFqcn = uuidExperimentalOptIn.substringAfter("=")

    plugins.withId("org.jetbrains.kotlin.multiplatform") {
        extensions.configure<KotlinMultiplatformExtension>("kotlin") {
            compilerOptions {
                optIn.add(uuidExperimentalOptInFqcn)
            }
        }
        tasks.withType<KotlinCompilationTask<*>>().configureEach {
            compilerOptions {
                freeCompilerArgs.add(uuidExperimentalOptIn)
            }
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            freeCompilerArgs.add(uuidExperimentalOptIn)
        }
    }

    extensions.configure<MavenPublishBaseExtension> {
        val projectPathName = project.path
            .removePrefix(":")
            .replace(":", "-")

        val newArtifactId = if (projectPathName.startsWith(rootProject.name)) {
            projectPathName
        } else {
            "${rootProject.name}-$projectPathName"
        }

        coordinates(
            project.group.toString(),
            newArtifactId,
            project.version.toString()
        )

        publishToMavenCentral()
        signAllPublications()

        plugins.withId("org.jetbrains.kotlin.multiplatform") {
            configure(KotlinMultiplatform(
                javadocJar = JavadocJar.Empty()
            ))
        }

        pom {
            name.set("Shared Foundation - ${project.name}")
            description.set("KMP Shared Logic")
            url.set("https://github.com/mudrichenkoevgeny/shared-foundation")
            licenses {
                license {
                    name.set("The Apache License, Version 2.0")
                    url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }
            developers {
                developer {
                    id.set("mudrichenkoevgeny")
                    name.set("Evgeny Mudrichenko")
                    email.set("evgeny.mudrichenko@gmail.com")
                }
            }
            scm {
                connection.set("scm:git:git://github.com/mudrichenkoevgeny/shared-foundation.git")
                developerConnection.set("scm:git:ssh://github.com/mudrichenkoevgeny/shared-foundation.git")
                url.set("https://github.com/mudrichenkoevgeny/shared-foundation")
            }
        }
    }
}
