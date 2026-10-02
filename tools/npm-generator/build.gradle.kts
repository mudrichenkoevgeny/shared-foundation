plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
    application
}

dependencies {
    implementation(project(":shared:foundation:core:common"))
    implementation(project(":shared:foundation:core:audit"))
    implementation(project(":shared:foundation:core:security"))
    implementation(project(":shared:foundation:core:settings"))
    implementation(project(":shared:foundation:feature:user"))

    implementation(libs.kotlinx.serialization.json)
    implementation("org.jetbrains.kotlin:kotlin-reflect:${libs.versions.kotlin.get()}")
}

application {
    mainClass.set("io.github.mudrichenkoevgeny.shared.foundation.generator.NpmGeneratorKt")
}
