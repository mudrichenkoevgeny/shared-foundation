plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

dependencies {
    implementation(project(":tools:npm-generator"))
    implementation(project(":shared:foundation:core:common"))
    implementation(project(":shared:foundation:core:audit"))
    implementation(project(":shared:foundation:core:security"))
    implementation(project(":shared:foundation:core:settings"))
    implementation(project(":shared:foundation:core:storage"))
    implementation(project(":shared:foundation:feature:user"))

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlin.reflect)

    testImplementation(libs.kotlin.test)
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

application {
    mainClass.set("io.github.mudrichenkoevgeny.shared.foundation.generator.foundation.FoundationNpmGeneratorKt")
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
