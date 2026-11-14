import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:audit"))
    implementation(project(":core:security"))
    implementation(project(":core:settings"))
    implementation(project(":core:storage"))
    implementation(project(":feature:user"))
    implementation(project(":tools:npm-generator"))

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlin.reflect)

    testImplementation(libs.kotlin.test)
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

application {
    mainClass.set("io.github.mudrichenkoevgeny.shared.foundation.tools.npmgeneratorrunner.NpmGeneratorRunnerKt")
}

tasks.named<JavaExec>("run") {
    systemProperty(
        "PACKAGE_VERSION",
        rootProject.version.toString()
    )
    val zodVer = libs.versions.npmZod.get()
    val tsVer = libs.versions.npmTypescript.get()
    systemProperty("ZOD_VERSION", zodVer)
    systemProperty("TYPESCRIPT_VERSION", tsVer)

    val propsFile = rootProject.file("gradle.properties")
    val props = Properties()
    if (propsFile.exists()) {
        propsFile.inputStream().use { props.load(it) }
    }
    val repoUrl = props.getProperty("POM_SCM_URL")
        ?: props.getProperty("POM_URL")
        ?: "https://github.com/mudrichenkoevgeny/shared-foundation"
    systemProperty("REPOSITORY_URL", repoUrl)
}

val auditNpmExports = tasks.register<Exec>("auditNpmExports") {
    group = "verification"
    description =
        "Audits generated NPM package to guarantee 100% parity between Kotlin classes/fixtures and JS/DTS exports."
    commandLine(
        "node",
        "scripts/audit-npm-exports.js"
    )
    dependsOn(tasks.named("run"))
}

tasks.register("generateNpmPackage") {
    group = "publishing"
    description = "Generates NPM package artifact for shared-foundation."
    dependsOn(auditNpmExports)
}
