plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    testImplementation(libs.kotlin.test)
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

fun JavaExec.configureVersionProperties() {
    systemProperty("sharedFoundationVersion", project.rootProject.version.toString())
    systemProperty("foundationRootDir", project.rootProject.projectDir.absolutePath)

    systemProperty("kotlinVersion", rootProject.libs.versions.kotlin.get())
    systemProperty("targetJvm", rootProject.libs.versions.targetJvm.get())
    systemProperty("toolchainJvm", rootProject.libs.versions.jvmToolchain.get())
    systemProperty("detektVersion", rootProject.libs.versions.detekt.get())
    systemProperty("ktlintVersion", rootProject.libs.versions.ktlint.get())
    systemProperty("serializationVersion", rootProject.libs.versions.kotlinx.serialization.get())
}

tasks.register<JavaExec>("initProject") {
    group = "scaffolding"
    description = "Scaffolds a new downstream contract repository."
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("io.github.mudrichenkoevgeny.shared.foundation.tools.projectgenerator.ProjectGeneratorKt")

    val customGroup = project.providers.gradleProperty("groupId").orNull
        ?: project.providers.gradleProperty("group").orNull?.takeIf { it != project.rootProject.group.toString() }
        ?: ""

    systemProperty("projectName", project.providers.gradleProperty("name").orNull ?: "")
    systemProperty("groupId", customGroup)
    systemProperty("npmScope", project.providers.gradleProperty("npmScope").orNull ?: "")
    systemProperty("targetDir", project.providers.gradleProperty("targetDir").orNull ?: "")
    systemProperty("linkLocal", project.providers.gradleProperty("linkLocal").orNull ?: "false")
    systemProperty("skipGit", project.providers.gradleProperty("skipGit").orNull ?: "false")

    configureVersionProperties()
}

tasks.register<JavaExec>("verifyProjectGenerator") {
    group = "verification"
    description = "E2E verification of project skeleton scaffolding."
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("io.github.mudrichenkoevgeny.shared.foundation.tools.projectgenerator.ProjectGeneratorVerifierKt")

    dependsOn(rootProject.getTasksByName("publishToMavenLocal", true))
    dependsOn(rootProject.tasks.named("generateNpmPackage"))

    val pluginBuild = gradle.includedBuilds.find { it.name.endsWith("gradle-plugin") }
    if (pluginBuild != null) {
        dependsOn(pluginBuild.task(":publishToMavenLocal"))
    }

    configureVersionProperties()
}
