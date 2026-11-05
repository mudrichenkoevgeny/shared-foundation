plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
    application
}

dependencies {
    implementation(libs.kotlinpoet)
}

application {
    mainClass.set("io.github.mudrichenkoevgeny.shared.foundation.scaffolder.ModuleScaffolderKt")
}

tasks.register<JavaExec>("scaffoldModule") {
    group = "generation"
    description = "Scaffolds a new KMP feature module with domain models, payload DTOs, route constants, and fixtures following strict project standards."
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("io.github.mudrichenkoevgeny.shared.foundation.scaffolder.ModuleScaffolderKt")
    val feature = project.findProperty("feature")?.toString() ?: "sample"
    args = listOf(feature, rootProject.rootDir.absolutePath)
}
