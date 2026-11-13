pluginManagement {
    includeBuild("tools/gradle-plugin")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("android.*")
            }
        }
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.PREFER_SETTINGS
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("android.*")
            }
        }
        gradlePluginPortal()
        mavenCentral()
        ivy {
            name = "Node Distributions at https://nodejs.org/dist"
            url = uri("https://nodejs.org/dist")
            patternLayout {
                artifact("v[revision]/[artifact]-v[revision]-[classifier].[ext]")
            }
            metadataSources {
                artifact()
            }
            content {
                includeGroup("org.nodejs")
            }
        }
        ivy {
            name = "Yarn Distributions at https://github.com/yarnpkg/yarn/releases/download"
            url = uri("https://github.com/yarnpkg/yarn/releases/download")
            patternLayout {
                artifact("v[revision]/[artifact]-v[revision].[ext]")
            }
            metadataSources {
                artifact()
            }
            content {
                includeGroup("com.yarnpkg")
            }
        }
    }
}

rootProject.name = "shared-foundation"

fun registerModules(
    group: String,
    modules: List<String>
) {
    modules.forEach { name ->
        val gradlePath = ":$group:$name"
        val folderPath = "$group/$name"
        include(gradlePath)
        project(gradlePath).projectDir = file(folderPath)
    }
}

val coreModules = listOf(
    "common",
    "security",
    "settings",
    "audit",
    "storage"
)
registerModules(
    "core",
    coreModules
)

val featureModules = listOf(
    "user"
)
registerModules(
    "feature",
    featureModules
)

val toolModules = listOf(
    "npm-generator",
    "npm-generator-runner",
    "detekt-rules"
)
registerModules(
    "tools",
    toolModules
)

include(":bom")
include(":testing")
