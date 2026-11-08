import com.vanniktech.maven.publish.MavenPublishBaseExtension

plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.maven.publish)
}

dependencies {
    compileOnly(libs.detekt.api)
}

extensions.configure<MavenPublishBaseExtension> {
    coordinates(
        groupId = rootProject.group.toString(),
        artifactId = "shared-foundation-detekt-rules",
        version = rootProject.version.toString()
    )
}
