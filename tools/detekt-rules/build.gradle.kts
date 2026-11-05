plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

dependencies {
    compileOnly(libs.detekt.api)
}
