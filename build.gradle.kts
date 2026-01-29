import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.MavenPublishBaseExtension

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.maven.publish) apply false
}

allprojects {
    group = "io.github.mudrichenkoevgeny"
    version = "0.0.1"
}

subprojects {
    val isModule = file("src").exists()
    if (!isModule) return@subprojects

    apply(plugin = "com.vanniktech.maven.publish")

    extensions.configure<MavenPublishBaseExtension> {
        publishToMavenCentral()
        signAllPublications()

        plugins.withId("org.jetbrains.kotlin.multiplatform") {
            configure(KotlinMultiplatform(
                javadocJar = JavadocJar.Empty()
            ))
        }

        pom {
            name.set("Shared Foundation - ${project.name}")
            description.set("KMP Shared Logic")
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
}
