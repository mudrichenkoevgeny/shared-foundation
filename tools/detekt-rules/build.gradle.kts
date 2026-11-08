extra["publishToMaven"] = true
extra["artifactId"] = "shared-foundation-detekt-rules"

plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    compileOnly(libs.detekt.api)
}
