import com.vanniktech.maven.publish.GradlePlugin
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.SourcesJar
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

fun loadPropertiesFile(file: File): Properties = Properties().apply {
    check(file.isFile) { "Required properties file not found at: ${file.absolutePath}" }
    file.inputStream().use { load(it) }
}

fun Properties.requireProperty(key: String): String =
    checkNotNull(getProperty(key)?.takeIf { it.isNotBlank() }) {
        "Missing required property '$key'"
    }

val repoRootDir: File = checkNotNull(rootDir.parentFile?.parentFile) {
    "Unable to resolve repository root directory from: ${rootDir.absolutePath}"
}

val rootProps = loadPropertiesFile(repoRootDir.resolve("gradle.properties"))
val daemonProps = loadPropertiesFile(repoRootDir.resolve("gradle/gradle-daemon-jvm.properties"))

group = rootProps.requireProperty("group")
version = rootProps.requireProperty("version")

val daemonJvmVersion = daemonProps.requireProperty("toolchainVersion").toInt()
val targetJvmVersion = (rootProps.getProperty("targetJvmVersion")?.takeIf { it.isNotBlank() }
    ?: daemonJvmVersion.toString()).toInt()

plugins {
    alias(libs.plugins.kotlin.jvm)
    `java-gradle-plugin`
    alias(libs.plugins.maven.publish)
}

kotlin {
    explicitApi()
    jvmToolchain(daemonJvmVersion)
    compilerOptions {
        jvmTarget.set(JvmTarget.fromTarget(targetJvmVersion.toString()))
        allWarningsAsErrors.set(true)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(targetJvmVersion)
}

gradlePlugin {
    plugins {
        create("agentSync") {
            id = "io.github.mudrichenkoevgeny.shared-foundation-agent-sync"
            implementationClass = "io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.AgentSyncPlugin"
            displayName = "Shared Foundation Agent Sync Gradle Plugin"
            description = "Gradle plugin synchronizing AI agent rules from shared-foundation into consumer projects"
        }
        create("quality") {
            id = "io.github.mudrichenkoevgeny.shared-foundation-quality"
            implementationClass = "io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.QualityPlugin"
            displayName = "Shared Foundation Quality Gradle Plugin"
            description = "Gradle plugin configuring Detekt with base detekt.yml and Ktlint"
        }
        create("kotlinConvention") {
            id = "io.github.mudrichenkoevgeny.shared-foundation-kotlin"
            implementationClass =
                "io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.KotlinConventionPlugin"
            displayName = "Shared Foundation Kotlin Convention Gradle Plugin"
            description = "Gradle plugin configuring Kotlin JVM compiler options, explicitApi, and opt-ins"
        }
        create("localization") {
            id = "io.github.mudrichenkoevgeny.shared-foundation-localization"
            implementationClass = "io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.LocalizationPlugin"
            displayName = "Shared Foundation Localization Gradle Plugin"
            description =
                "Gradle plugin generating type-safe Kotlin localization accessors and embedded bundles from JSON resources"
        }
        create("publishConvention") {
            id = "io.github.mudrichenkoevgeny.shared-foundation-publish"
            implementationClass =
                "io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.PublishConventionPlugin"
            displayName = "Shared Foundation Maven Publishing Convention Gradle Plugin"
            description = "Gradle plugin configuring Vanniktech Maven Publishing, Dokka, and POM metadata"
        }
    }
}

mavenPublishing {
    coordinates(
        group.toString(),
        "shared-foundation-gradle-plugin",
        version.toString()
    )

    publishToMavenCentral()
    signAllPublications()

    configure(
        GradlePlugin(
            javadocJar = JavadocJar.Javadoc(),
            sourcesJar = SourcesJar.Sources()
        )
    )

    pom {
        name.set("shared-foundation - gradle-plugin")
        description.set("Gradle convention plugins for shared-foundation and consumer projects")
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

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.detekt.gradle.plugin)
    implementation(libs.ktlint.gradle.plugin)
    implementation(libs.maven.publish.gradle.plugin)
    implementation(libs.dokka.gradle.plugin)
}

val generatePluginVersionResource = tasks.register("generatePluginVersionResource") {
    val currentVersion = project.version.toString()
    inputs.property("version", currentVersion)
    val versionFile = layout.buildDirectory.file("generated/resources/shared-foundation-plugin-version.txt")
    outputs.file(versionFile)
    doLast {
        val file = versionFile.get().asFile
        file.parentFile.mkdirs()
        file.writeText(currentVersion)
    }
}

tasks.named<ProcessResources>("processResources") {
    from(generatePluginVersionResource)
    from(repoRootDir.resolve(".agent/global/common")) {
        into("agent-rules/common")
    }
    from(repoRootDir.resolve(".agent/global/kotlin")) {
        into("agent-rules/kotlin")
    }
    from(repoRootDir.resolve(".agent/global/shared")) {
        into("agent-rules/shared")
    }
}
