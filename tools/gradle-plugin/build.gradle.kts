import org.gradle.language.jvm.tasks.ProcessResources
import java.util.Properties

val rootProps = Properties().apply {
    val rootPropsFile = rootDir.parentFile.parentFile.resolve("gradle.properties")
    check(rootPropsFile.exists()) {
        "Root gradle.properties not found at: ${rootPropsFile.absolutePath}"
    }
    rootPropsFile.inputStream().use { load(it) }
}

group = checkNotNull(rootProps.getProperty("group")) { "Missing 'group' property in root gradle.properties" }
version = checkNotNull(rootProps.getProperty("version")) { "Missing 'version' property in root gradle.properties" }

extra["publishToMaven"] = true
extra["artifactId"] = "shared-foundation-gradle-plugin"

plugins {
    alias(libs.plugins.kotlin.jvm)
    `java-gradle-plugin`
    alias(libs.plugins.maven.publish)
}

kotlin {
    explicitApi()
}

gradlePlugin {
    plugins {
        create("agentSync") {
            id = "io.github.mudrichenkoevgeny.shared-foundation-agent-sync"
            implementationClass = "io.github.mudrichenkoevgeny.shared.foundation.plugin.AgentSyncPlugin"
            displayName = "Shared Foundation Agent Sync Gradle Plugin"
            description = "Gradle plugin synchronizing AI agent rules from shared-foundation into consumer projects"
        }
        create("quality") {
            id = "io.github.mudrichenkoevgeny.shared-foundation-quality"
            implementationClass = "io.github.mudrichenkoevgeny.shared.foundation.plugin.QualityPlugin"
            displayName = "Shared Foundation Quality Gradle Plugin"
            description = "Gradle plugin configuring Detekt with base detekt.yml and Ktlint"
        }
        create("kotlinConvention") {
            id = "io.github.mudrichenkoevgeny.shared-foundation-kotlin"
            implementationClass = "io.github.mudrichenkoevgeny.shared.foundation.plugin.KotlinConventionPlugin"
            displayName = "Shared Foundation Kotlin Convention Gradle Plugin"
            description = "Gradle plugin configuring Kotlin JVM compiler options, explicitApi, and opt-ins"
        }
        create("publishConvention") {
            id = "io.github.mudrichenkoevgeny.shared-foundation-publish"
            implementationClass = "io.github.mudrichenkoevgeny.shared.foundation.plugin.PublishConventionPlugin"
            displayName = "Shared Foundation Maven Publishing Convention Gradle Plugin"
            description = "Gradle plugin configuring Vanniktech Maven Publishing, Dokka, and POM metadata"
        }
    }
}

dependencies {
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.detekt.gradle.plugin)
    compileOnly(libs.ktlint.gradle.plugin)
    compileOnly(libs.maven.publish.gradle.plugin)
    compileOnly(libs.dokka.gradle.plugin)
}

val repoRootDir = rootDir.parentFile.parentFile

val generatePluginVersionResource = tasks.register("generatePluginVersionResource") {
    val versionFile = layout.buildDirectory.file("generated/resources/plugin-version.txt")
    outputs.file(versionFile)
    doLast {
        val file = versionFile.get().asFile
        file.parentFile.mkdirs()
        file.writeText(project.version.toString())
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
    from(repoRootDir.resolve("tools/detekt-rules/src/main/resources/config/detekt/detekt-base.yml")) {
        into("config/detekt")
    }
    from(repoRootDir.resolve(".editorconfig")) {
        into("config/editorconfig")
    }
}
