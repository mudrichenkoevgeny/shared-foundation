package io.github.mudrichenkoevgeny.shared.foundation.scaffolder

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File

public fun scaffoldFeatureModule(
    featureName: String,
    rootDirPath: String
) {
    val capitalized = featureName.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    val lowercase = featureName.lowercase()
    val basePackage = "io.github.mudrichenkoevgeny.shared.foundation.feature.$lowercase"

    val featureDir = File(rootDirPath, "shared/foundation/feature/$lowercase")
    val srcDir = File(featureDir, "src/commonMain/kotlin")
    srcDir.mkdirs()

    val serializableAnnotation = AnnotationSpec.builder(
        ClassName("kotlinx.serialization", "Serializable")
    ).build()

    // 1. Domain Model (*Private.kt)
    val domainClassName = "${capitalized}Private"
    val domainType = TypeSpec.classBuilder(domainClassName)
        .addModifiers(KModifier.PUBLIC, KModifier.DATA)
        .addAnnotation(serializableAnnotation)
        .primaryConstructor(
            FunSpec.constructorBuilder()
                .addParameter("id", String::class)
                .build()
        )
        .addProperty(
            PropertySpec.builder("id", String::class)
                .initializer("id")
                .addModifiers(KModifier.PUBLIC)
                .build()
        )
        .build()

    val domainFile = FileSpec.builder("$basePackage.domain.model", domainClassName)
        .addType(domainType)
        .build()
    domainFile.writeTo(srcDir)

    // 2. Payload DTO (*PrivatePayload.kt)
    val payloadClassName = "${capitalized}PrivatePayload"
    val serialNameAnnotation = AnnotationSpec.builder(
        ClassName("kotlinx.serialization", "SerialName")
    ).addMember("%S", "id").build()

    val payloadType = TypeSpec.classBuilder(payloadClassName)
        .addModifiers(KModifier.PUBLIC, KModifier.DATA)
        .addAnnotation(serializableAnnotation)
        .primaryConstructor(
            FunSpec.constructorBuilder()
                .addParameter("id", String::class)
                .build()
        )
        .addProperty(
            PropertySpec.builder("id", String::class)
                .initializer("id")
                .addAnnotation(serialNameAnnotation)
                .addModifiers(KModifier.PUBLIC)
                .build()
        )
        .build()

    val payloadFile = FileSpec.builder("$basePackage.network.model", payloadClassName)
        .addType(payloadType)
        .build()
    payloadFile.writeTo(srcDir)

    // 3. Network Routes (*Routes.kt)
    val routesClassName = "${capitalized}Routes"
    val routeKDoc = """
        Endpoint for $capitalized operations.

        * **HTTP method:** `POST`
        * **Request body:** [${payloadClassName}]
        * **Response body:** [${payloadClassName}]
        * **UI Presentation:** Displays $capitalized status view.
        * **Audit logging:** Records $capitalized operation event.
    """.trimIndent()

    val routeProperty = PropertySpec.builder("PATH_${lowercase.uppercase()}_MAIN", String::class)
        .addModifiers(KModifier.PUBLIC, KModifier.CONST)
        .initializer("%S", "/api/v1/$lowercase/main")
        .addKdoc(routeKDoc)
        .build()

    val routesType = TypeSpec.objectBuilder(routesClassName)
        .addModifiers(KModifier.PUBLIC)
        .addProperty(routeProperty)
        .build()

    val routesFile = FileSpec.builder("$basePackage.network.route", routesClassName)
        .addType(routesType)
        .build()
    routesFile.writeTo(srcDir)

    // 4. Create build.gradle.kts
    val buildGradle = File(featureDir, "build.gradle.kts")
    if (!buildGradle.exists()) {
        buildGradle.writeText(
            """
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
                    namespace = "$basePackage"
                    compileSdk = libs.versions.android.compileSdk.get().toInt()
                    minSdk = libs.versions.android.minSdk.get().toInt()
                    optimization {
                        consumerKeepRules.file("consumer-rules.pro")
                    }
                }
                jvm()
                iosArm64()
                iosSimulatorArm64()
                wasmJs { browser() }

                sourceSets {
                    commonMain {
                        dependencies {
                            api(project(":shared:foundation:core:common"))
                            api(libs.kotlinx.serialization.json)
                            api(libs.kotlinx.serialization.core)
                        }
                    }
                }
            }
            """.trimIndent() + "\n"
        )
    }

    // 5. Create consumer-rules.pro
    val consumerRules = File(featureDir, "consumer-rules.pro")
    if (!consumerRules.exists()) {
        consumerRules.writeText(
            """
            # Consumer Proguard / R8 keep rules for shared-foundation kotlinx.serialization
            -keepclassmembers class * {
                @kotlinx.serialization.SerialName <fields>;
                @kotlinx.serialization.Serializable <fields>;
            }
            -keepclassmembers class * {
                public static final **${'$'}serializer INSTANCE;
            }
            -keepclasseswithmembers class * {
                public static final **${'$'}serializer INSTANCE;
            }
            """.trimIndent() + "\n"
        )
    }

    // 6. Update settings.gradle.kts
    val settingsFile = File(rootDirPath, "settings.gradle.kts")
    if (settingsFile.exists()) {
        val content = settingsFile.readText()
        if (!content.contains("\"$lowercase\"")) {
            val updated = content.replace(
                "val featureModules = listOf(\n",
                "val featureModules = listOf(\n    \"$lowercase\",\n"
            )
            settingsFile.writeText(updated)
            println("Module $lowercase added to settings.gradle.kts")
        }
    }
}

public fun main(args: Array<String>) {
    val feature = args.getOrNull(0) ?: "sample"
    val rootDir = args.getOrNull(1) ?: "."
    scaffoldFeatureModule(feature, rootDir)
    println("Scaffolded KMP module for feature: $feature in $rootDir")
}
