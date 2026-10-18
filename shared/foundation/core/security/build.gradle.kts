@file:OptIn(ExperimentalWasmDsl::class)

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import io.github.mudrichenkoevgeny.build.tasks.GenerateLocalizationTask
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.serialization)
}

val generateLocalization = tasks.register<GenerateLocalizationTask>("generateLocalization") {
    group = "build"
    description = "Generates type-safe Kotlin localization strings from JSON resources."
    resourcesDir.set(file("src/commonMain/resources"))
    outputDir.set(layout.buildDirectory.dir("generated/sources/localization/kotlin/commonMain"))
}

configure<KotlinMultiplatformExtension> {
    extensions.configure<KotlinMultiplatformAndroidLibraryExtension>("androidLibrary") {
        namespace = "io.github.mudrichenkoevgeny.shared.foundation.core.security"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    jvm()

    iosArm64()
    iosSimulatorArm64()

    wasmJs {
        browser()
    }

    sourceSets {
        commonMain {
            kotlin.srcDir(generateLocalization.map { it.outputDir })
            dependencies {
                api(project(":shared:foundation:core:common"))
                api(project(":shared:foundation:core:audit"))

                api(libs.kotlinx.serialization.json)
                api(libs.kotlinx.serialization.core) // Transitive for kotlinx-serialization
            }
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<*>>().configureEach {
    dependsOn(generateLocalization)
}

tasks.matching { it.name == "prepareKotlinIdeaImport" }.configureEach {
    dependsOn(generateLocalization)
}