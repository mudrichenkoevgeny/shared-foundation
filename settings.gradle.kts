pluginManagement {
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

fun registerModules(group: String, modules: List<String>) {
    modules.forEach { name ->
        val gradlePath = ":${group.replace(".", ":")}:$name"
        val folderPath = group.replace(".", "/") + "/$name"
        include(gradlePath)
        project(gradlePath).projectDir = file(folderPath)
    }
}

val groupBase = "shared.foundation"

val coreModules = listOf(
    "common",
    "security",
    "settings",
    "audit"
)
registerModules("$groupBase.core", coreModules)

val featureModules = listOf(
    "user"
)
registerModules("$groupBase.feature", featureModules)

registerModules(groupBase, listOf("bom", "testing"))

include(":tools:npm-generator")
project(":tools:npm-generator").projectDir = file("tools/npm-generator")
