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
    id("io.github.mudrichenkoevgeny.shared-foundation-agent-sync")
}

apiValidation {
    validationDisabled = true
    ignoredProjects += listOf(
        "bom",
        "npm-generator",
        "testing"
    )
}

allprojects {
    group = rootProject.group
    version = rootProject.version
}

subprojects {
    val isBom = project.name == "bom"
    val isTool = project.path.startsWith(":tools")
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

    afterEvaluate {
        val explicitPublishFlag = (findProperty("publishToMaven") as? Boolean)
            ?: (findProperty("publishToMaven")?.toString()?.toBoolean())
        if (!isTool || explicitPublishFlag == true) {
            pluginManager.apply("io.github.mudrichenkoevgeny.shared-foundation-publish")
        }
    }
}

tasks.register("generateNpmPackage") {
    group = "publishing"
    description = "Generates the @mudrichenkoevgeny/shared-foundation NPM package with TypeScript types," +
        " Zod schemas, and route constants."
    dependsOn(":tools:npm-generator:run")
    finalizedBy(":tools:npm-generator:auditNpmExports")
}
