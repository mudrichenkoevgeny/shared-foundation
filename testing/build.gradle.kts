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
    explicitApi()

    extensions.configure<KotlinMultiplatformAndroidLibraryExtension>("androidLibrary") {
        namespace = "io.github.mudrichenkoevgeny.shared.foundation.testing"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    jvm()

//    iosArm64()
//    iosSimulatorArm64()
//
//    wasmJs {
//        browser()
//    }

    sourceSets {
        commonMain {
            dependencies {
                api(project(":core:common"))
                api(project(":core:audit"))
                api(project(":core:security"))
                api(project(":core:settings"))
                api(project(":core:storage"))
                api(project(":feature:user"))

                api(libs.kotlinx.serialization.json)
                api(libs.kotlinx.serialization.core)
            }
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        jvmTest.dependencies {
            implementation(libs.konsist)
        }
    }
}
