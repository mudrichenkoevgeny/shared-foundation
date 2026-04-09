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
            api(project(":shared:foundation:core:common"))
            api(project(":shared:foundation:core:audit"))
            api(project(":shared:foundation:core:security"))
            api(project(":shared:foundation:core:settings"))
            api(project(":shared:foundation:feature:audit-api"))
            api(project(":shared:foundation:feature:security-api"))
            api(project(":shared:foundation:feature:settings-api"))

            api(libs.kotlinx.serialization.json)
            api(libs.kotlinx.serialization.core) // Transitive for kotlinx-serialization
        }

        jvmMain.dependencies {
            api(project(":shared:foundation:core:common")) // Transitive for core.common
            api(project(":shared:foundation:core:security")) // Transitive for core.security
            api(project(":shared:foundation:core:settings")) // Transitive for core.settings
            api(project(":shared:foundation:core:audit")) // Transitive for core.audit
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
