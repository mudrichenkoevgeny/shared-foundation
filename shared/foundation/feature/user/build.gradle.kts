@file:OptIn(ExperimentalWasmDsl::class)

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.serialization)
}

configure<KotlinMultiplatformExtension> {
    extensions.configure<KotlinMultiplatformAndroidLibraryExtension>("androidLibrary") {
        namespace = "io.github.mudrichenkoevgeny.shared.foundation.feature.user"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    jvm()

    iosArm64()
    iosX64()
    iosSimulatorArm64()

    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":shared:foundation:core:common"))

            implementation(libs.kotlinx.serialization.json)
        }
    }
}