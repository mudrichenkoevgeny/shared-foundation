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

dependencyResolutionManagement {
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
    }
}

rootProject.name = "shared-foundation"

fun registerModules(group: String, modules: List<String>) {
    modules.forEach { name ->
        val gradleName = ":${group.replace(".", ":")}:$name"
        val folderPath = group.replace(".", "/") + "/$name"
        include(gradleName)
        project(gradleName).projectDir = file(folderPath)
    }
}

val groupBase = "shared.foundation"

val coreModules = listOf(
    "common",
    "security"
)
registerModules("$groupBase.core", coreModules)

val featureModules = listOf(
    "user"
)
registerModules("$groupBase.feature", featureModules)
