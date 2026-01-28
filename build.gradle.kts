plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    `maven-publish`
    signing
}

allprojects {
    group = "io.github.mudrichenkoevgeny"
    version = "0.0.1"

    repositories {
        mavenCentral()
    }
}

subprojects {
    val isModule = file("src").exists()
    if (!isModule) return@subprojects

    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    configure<PublishingExtension> {
        publications.withType<MavenPublication>().configureEach {
            pom {
                name.set("Shared Foundation - ${project.name}")
                description.set("KMP module ${project.name} for shared logic")
                url.set("https://github.com/mudrichenkoevgeny/shared-foundation")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("mudrichenkoevgeny")
                        name.set("Evgeny Mudrichenko")
                        email.set("evgeny.mudrichenko@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/mudrichenkoevgeny/shared-foundation.git")
                    developerConnection.set("scm:git:ssh://github.com/mudrichenkoevgeny/shared-foundation.git")
                    url.set("https://github.com/mudrichenkoevgeny/shared-foundation")
                }
            }
        }

        repositories {
            maven {
                name = "OSSRH"
                url = uri("https://central.sonatype.com/api/v1/publisher/upload")
                credentials {
                    username = project.findProperty("mavenCentralUsername")?.toString() ?: ""
                    password = project.findProperty("mavenCentralPassword")?.toString() ?: ""
                }
            }
        }
    }

    configure<SigningExtension> {
        if (project.hasProperty("signing.keyId")) {
            sign(extensions.getByType<PublishingExtension>().publications)
        }
    }
}