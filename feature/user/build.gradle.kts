@file:OptIn(ExperimentalWasmDsl::class)

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.shared.foundation.localization)
}

configure<KotlinMultiplatformExtension> {
    extensions.configure<KotlinMultiplatformAndroidLibraryExtension>("androidLibrary") {
        namespace = "io.github.mudrichenkoevgeny.shared.foundation.feature.user"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        optimization {
            consumerKeepRules.file("consumer-rules.pro")
        }
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

                api(libs.kotlinx.serialization.json)
                api(libs.kotlinx.serialization.core) // Transitive for kotlinx-serialization
            }
        }

        jvmMain.dependencies {
            api(project(":core:common")) // Transitive for core.common
            api(project(":core:security")) // Transitive for core.security
            api(project(":core:settings")) // Transitive for core.settings
            api(project(":core:storage")) // Transitive for core.storage
            api(project(":core:audit")) // Transitive for core.audit
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
