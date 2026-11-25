plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.maven.publish) apply false
    alias(libs.plugins.dependency.analysis)
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.binary.compatibility.validator)
    alias(libs.plugins.dokka)
    alias(libs.plugins.shared.foundation.agent.sync)
}

apiValidation {
    // Disabled until binary-compatibility-validator supports
    // Java 25 bytecode (class file major version 69)
    validationDisabled = true
    ignoredProjects += listOf(
        "bom",
        "npm-generator",
        "npm-generator-runner",
        "project-generator",
        "detekt-rules",
        "design-system",
        "testing"
    )
}

allprojects {
    group = rootProject.group
    version = rootProject.version
}

subprojects {
    val isBom = project.name == "bom"
    val isModule = project.projectDir.resolve("src").exists() || isBom

    if (!isModule) {
        return@subprojects
    }

    pluginManager.apply("io.github.mudrichenkoevgeny.shared-foundation-quality")

    if (isBom) {
        pluginManager.apply("java-platform")
    } else {
        pluginManager.apply("io.github.mudrichenkoevgeny.shared-foundation-kotlin")
    }

    pluginManager.apply("io.github.mudrichenkoevgeny.shared-foundation-publish")
}

tasks.register("generateNpmPackage") {
    group = "publishing"
    description = "Generates the @mudrichenkoevgeny/shared-foundation NPM package with TypeScript types," +
        " Zod schemas, and route constants."
    dependsOn(":tools:npm-generator-runner:generateNpmPackage")
}

tasks.register("initProject") {
    group = "scaffolding"
    description = "Scaffolds a new downstream contract repository."
    dependsOn(":tools:project-generator:initProject")
}

tasks.register("scaffoldProject") {
    group = "scaffolding"
    description = "Alias for initProject."
    dependsOn(tasks.named("initProject"))
}

tasks.register("verifyProjectGenerator") {
    group = "verification"
    description = "E2E verification of project skeleton scaffolding."
    dependsOn(":tools:project-generator:verifyProjectGenerator")
}

val verifyRepositoryVersions = tasks.register("verifyRepositoryVersions") {
    group = "verification"
    description = "Verifies that tool package manifests stay synchronized with root project version."

    val expectedVersion = rootProject.version.toString()
    val rootDirectory = rootDir
    val toolManifests = listOf(file("tools/code-review/package.json"))

    doLast {
        val versionRegex = Regex(""""version"\s*:\s*"([^"]+)"""")
        toolManifests.forEach { manifest ->
            val relPath = manifest.relativeTo(rootDirectory).path
            if (!manifest.exists()) throw GradleException("Manifest not found: $relPath")

            val actual = versionRegex.find(manifest.readText())?.groupValues?.get(1)
                ?: throw GradleException("Could not parse 'version' from $relPath")

            if (actual != expectedVersion) {
                throw GradleException("Version mismatch in $relPath: found '$actual', expected '$expectedVersion'.")
            }
        }
    }
}

tasks.named("check") {
    dependsOn(verifyRepositoryVersions)
}

