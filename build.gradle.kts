import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import dev.detekt.gradle.extensions.DetektExtension
import org.jetbrains.dokka.gradle.DokkaExtension
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.maven.publish) apply false
    alias(libs.plugins.dependency.analysis)
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.binary.compatibility.validator)
    alias(libs.plugins.dokka)
    alias(libs.plugins.cyclonedx) apply false
    alias(libs.plugins.pitest) apply false
}

apiValidation {
    // Disabled during pre-1.0 active development to allow rapid API evolution without breaking CI checks.
    // Re-enable (set to false or remove) once public API is stabilized for v1.0.0 release.
    validationDisabled = true
    ignoredProjects += listOf("bom", "npm-generator", "testing")
}

allprojects {
    group = "io.github.mudrichenkoevgeny"
    version = "0.1.6"
}

val daemonJvmVersion: Int = run {
    val daemonFile = rootProject.file("gradle/gradle-daemon-jvm.properties")
    if (daemonFile.exists()) {
        val properties = Properties().apply {
            daemonFile.inputStream().use { load(it) }
        }
        properties.getProperty("toolchainVersion")?.toIntOrNull()
    } else {
        null
    }
} ?: libs.versions.jvmToolchain.get().toInt()

subprojects {
    val isBom = project.name == "bom"
    val isTool = project.path.startsWith(":tools")
    val isModule = project.projectDir.resolve("src").exists() || isBom

    if (!isModule) {
        return@subprojects
    }

    pluginManager.apply("com.vanniktech.maven.publish")
    pluginManager.apply("com.autonomousapps.dependency-analysis")
    pluginManager.apply("org.jlleitschuh.gradle.ktlint")

    if (!isTool && !isBom) {
        pluginManager.apply("dev.detekt")
        pluginManager.apply("org.jetbrains.dokka")
        pluginManager.apply("org.cyclonedx.bom")

        dependencies {
            "detektPlugins"(project(":tools:detekt-rules"))
        }

        extensions.configure<DetektExtension> {
            buildUponDefaultConfig = true
            autoCorrect = true
            allRules = false
        }

        extensions.configure<DokkaExtension> {
            dokkaPublications.configureEach {
                failOnWarning.set(false)
                pluginsConfiguration.html {
                    customAssets.from(rootProject.file("docs/playground-inject.js"))
                }
            }
        }
    }

    extensions.configure<KtlintExtension> {
        android.set(true)
        ignoreFailures.set(false)
        filter {
            exclude { it.file.absolutePath.contains("build") }
        }
    }

    val uuidExperimentalOptIn = "-opt-in=kotlin.uuid.ExperimentalUuidApi"
    val uuidExperimentalOptInFqcn = uuidExperimentalOptIn.substringAfter("=")
    val targetJvmVersionString = rootProject.libs.versions.targetJvm.get()
    val targetJvm = JvmTarget.fromTarget(targetJvmVersionString)

    plugins.withId("org.jetbrains.kotlin.multiplatform") {
        extensions.configure<KotlinMultiplatformExtension>("kotlin") {
            explicitApi()
            jvmToolchain(daemonJvmVersion)
            compilerOptions {
                optIn.add(uuidExperimentalOptInFqcn)
            }
            jvm {
                compilerOptions {
                    jvmTarget.set(targetJvm)
                }
            }
        }
        tasks.withType<KotlinCompilationTask<*>>().configureEach {
            compilerOptions {
                freeCompilerArgs.add(uuidExperimentalOptIn)
            }
        }
    }

    plugins.withId("org.jetbrains.kotlin.jvm") {
        extensions.configure<KotlinJvmProjectExtension>("kotlin") {
            explicitApi()
            jvmToolchain(daemonJvmVersion)
            compilerOptions {
                jvmTarget.set(targetJvm)
            }
        }
    }

    tasks.withType<JavaCompile>().configureEach {
        options.release.set(targetJvmVersionString.toInt())
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
            configure(
                KotlinMultiplatform(
                    javadocJar = JavadocJar.Empty()
                )
            )
        }

        pom {
            name.set("Cross-platform Shared Foundation - ${project.name}")
            description.set("Shared Foundation")
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

tasks.register("generateNpmPackage") {
    group = "publishing"
    description = "Generates the @mudrichenkoevgeny/shared-foundation NPM package with TypeScript types, Zod schemas, and route constants."
    dependsOn(":tools:npm-generator:run")
    finalizedBy(":tools:npm-generator:auditNpmExports")
}

tasks.register("checkBinarySize") {
    group = "verification"
    description = "Enforces binary size budgets for compiled JAR and AAR artifacts."
    dependsOn("assemble")

    doLast {
        val maxIndividualArtifactBytes = 5_242_880L
        val violations = mutableListOf<String>()

        project.subprojects.forEach { sub ->
            val buildDir = sub.layout.buildDirectory.get().asFile
            val targetDirs = listOf(buildDir.resolve("libs"), buildDir.resolve("outputs"))
            targetDirs.filter { it.exists() }.forEach { dir ->
                dir.walkTopDown().filter { it.isFile && (it.extension == "jar" || it.extension == "aar") }.forEach { file ->
                    val size = file.length()
                    if (size > maxIndividualArtifactBytes) {
                        violations.add("${sub.path} -> ${file.name}: ${size / 1024} KB exceeds limit of ${maxIndividualArtifactBytes / 1024} KB")
                    }
                }
            }
        }

        if (violations.isNotEmpty()) {
            throw GradleException("Binary budget enforcement failed:\n" + violations.joinToString("\n"))
        } else {
            logger.lifecycle("Binary budget enforcement passed: All artifacts are within size limits.")
        }
    }
}
