plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

dependencies {
    implementation(project(":shared:foundation:core:common"))
    implementation(project(":shared:foundation:core:audit"))
    implementation(project(":shared:foundation:core:security"))
    implementation(project(":shared:foundation:core:settings"))
    implementation(project(":shared:foundation:core:storage"))
    implementation(project(":shared:foundation:feature:user"))

    implementation(libs.kotlinx.serialization.json)
    implementation("org.jetbrains.kotlin:kotlin-reflect:${libs.versions.kotlin.get()}")

    testImplementation(libs.kotlin.test)
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

application {
    mainClass.set("io.github.mudrichenkoevgeny.shared.foundation.generator.NpmGeneratorKt")
}

tasks.named<JavaExec>("run") {
    systemProperty(
        "PACKAGE_VERSION",
        rootProject.version.toString()
    )
}

val verifyConsumerNpmPackage = tasks.register<Exec>("verifyConsumerNpmPackage") {
    group = "verification"
    description =
        "Runs isolated NPM consumer smoke test (npm pack --dry-run, node --check, TypeScript declaration verification)."
    commandLine(
        "node",
        "scripts/consumer-smoke-test.js"
    )
    dependsOn(tasks.named("run"))
}

val auditNpmExports = tasks.register<Exec>("auditNpmExports") {
    group = "verification"
    description =
        "Audits generated NPM package to guarantee 100% parity between Kotlin classes/fixtures and JS/DTS exports."
    commandLine(
        "node",
        "scripts/audit-npm-exports.js"
    )
    dependsOn(
        tasks.named("run"),
        verifyConsumerNpmPackage
    )
}
