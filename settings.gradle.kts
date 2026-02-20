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
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
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
    "settings"
)
registerModules("$groupBase.core", coreModules)

val featureModules = listOf(
    "user"
)
registerModules("$groupBase.feature", featureModules)

registerModules(groupBase, listOf("bom"))
