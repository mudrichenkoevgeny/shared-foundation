plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlin.reflect)

    testImplementation(libs.kotlin.test)
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
