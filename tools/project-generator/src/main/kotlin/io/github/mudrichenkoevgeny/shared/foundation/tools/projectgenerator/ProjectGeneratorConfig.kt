package io.github.mudrichenkoevgeny.shared.foundation.tools.projectgenerator

import java.io.File

internal data class ProjectGeneratorConfig(
    val projectName: String,
    val projectTitle: String,
    val projectRepoName: String,
    val groupId: String,
    val packageName: String,
    val packageDirPath: String,
    val npmScope: String,
    val npmPackageName: String,
    val targetDir: File,
    val sfVersion: String,
    val linkLocal: Boolean,
    val skipGit: Boolean,
    val foundationRootDir: File,
    val kotlinVersion: String,
    val targetJvm: String,
    val toolchainJvm: String,
    val detektVersion: String,
    val ktlintVersion: String,
    val serializationVersion: String
) {
    companion object {
        private const val PROP_PROJECT_NAME = "projectName"
        private const val PROP_GROUP_ID = "groupId"
        private const val PROP_NPM_SCOPE = "npmScope"
        private const val PROP_TARGET_DIR = "targetDir"
        private const val PROP_LINK_LOCAL = "linkLocal"
        private const val PROP_SKIP_GIT = "skipGit"
        private const val PROP_SHARED_FOUNDATION_VERSION = "sharedFoundationVersion"
        private const val PROP_FOUNDATION_ROOT_DIR = "foundationRootDir"
        private const val PROP_KOTLIN_VERSION = "kotlinVersion"
        private const val PROP_TARGET_JVM = "targetJvm"
        private const val PROP_TOOLCHAIN_JVM = "toolchainJvm"
        private const val PROP_DETEKT_VERSION = "detektVersion"
        private const val PROP_KTLINT_VERSION = "ktlintVersion"
        private const val PROP_SERIALIZATION_VERSION = "serializationVersion"

        private const val DEFAULT_GROUP_PREFIX = "com."
        private const val SHARED_SUFFIX = ".shared"
        private const val REPO_NAME_SUFFIX = "-shared"
        private const val NPM_SHARED_SUFFIX = "/shared"

        private const val NON_ALPHANUMERIC_REGEX = "[^a-zA-Z0-9_]"
        private const val WORD_SPLIT_REGEX = "[-_]+"

        fun fromSystemProperties(): ProjectGeneratorConfig {
            val rawName = System.getProperty(PROP_PROJECT_NAME).takeIf { !it.isNullOrBlank() }
                ?: error("Property 'name' is required. Example: ./gradlew initProject -Pname=sample")

            val foundationRootDir = File(
                System.getProperty(PROP_FOUNDATION_ROOT_DIR)
                    ?: error("System property '$PROP_FOUNDATION_ROOT_DIR' is missing.")
            )
            val sfVersion = System.getProperty(PROP_SHARED_FOUNDATION_VERSION)
                ?: error("System property '$PROP_SHARED_FOUNDATION_VERSION' is missing.")

            val kotlinVersion = System.getProperty(PROP_KOTLIN_VERSION)
                ?: error("System property '$PROP_KOTLIN_VERSION' is missing.")
            val targetJvm = System.getProperty(PROP_TARGET_JVM)
                ?: error("System property '$PROP_TARGET_JVM' is missing.")
            val toolchainJvm = System.getProperty(PROP_TOOLCHAIN_JVM)
                ?: error("System property '$PROP_TOOLCHAIN_JVM' is missing.")
            val detektVersion = System.getProperty(PROP_DETEKT_VERSION)
                ?: error("System property '$PROP_DETEKT_VERSION' is missing.")
            val ktlintVersion = System.getProperty(PROP_KTLINT_VERSION)
                ?: error("System property '$PROP_KTLINT_VERSION' is missing.")
            val serializationVersion = System.getProperty(PROP_SERIALIZATION_VERSION)
                ?: error("System property '$PROP_SERIALIZATION_VERSION' is missing.")

            val rawGroup = System.getProperty(PROP_GROUP_ID).takeIf { !it.isNullOrBlank() }
                ?: "$DEFAULT_GROUP_PREFIX${rawName.replace(Regex(NON_ALPHANUMERIC_REGEX), "").lowercase()}"
            val rawNpmScope = System.getProperty(PROP_NPM_SCOPE).takeIf { !it.isNullOrBlank() } ?: "@$rawName"

            val projectTitle = rawName.split(Regex(WORD_SPLIT_REGEX)).joinToString("") { word ->
                word.replaceFirstChar { char -> char.uppercase() }
            }
            val projectRepoName = "$rawName$REPO_NAME_SUFFIX"

            val sanitizedGroup = rawGroup.split(".").joinToString(".") { part ->
                part.replace(Regex(NON_ALPHANUMERIC_REGEX), "").lowercase()
            }
            val packageName = if (sanitizedGroup.endsWith(SHARED_SUFFIX)) sanitizedGroup else "$sanitizedGroup$SHARED_SUFFIX"
            val packageDirPath = packageName.replace('.', '/')
            val npmPackageName = "$rawNpmScope$NPM_SHARED_SUFFIX"

            val rawTargetDir = System.getProperty(PROP_TARGET_DIR).takeIf { !it.isNullOrBlank() }
            val targetDir = if (rawTargetDir != null) {
                File(rawTargetDir)
            } else {
                File(foundationRootDir.parentFile, projectRepoName)
            }

            val linkLocal = System.getProperty(PROP_LINK_LOCAL).toBoolean()
            val skipGit = System.getProperty(PROP_SKIP_GIT).toBoolean()

            return ProjectGeneratorConfig(
                projectName = rawName,
                projectTitle = projectTitle,
                projectRepoName = projectRepoName,
                groupId = rawGroup,
                packageName = packageName,
                packageDirPath = packageDirPath,
                npmScope = rawNpmScope,
                npmPackageName = npmPackageName,
                targetDir = targetDir,
                sfVersion = sfVersion,
                linkLocal = linkLocal,
                skipGit = skipGit,
                foundationRootDir = foundationRootDir,
                kotlinVersion = kotlinVersion,
                targetJvm = targetJvm,
                toolchainJvm = toolchainJvm,
                detektVersion = detektVersion,
                ktlintVersion = ktlintVersion,
                serializationVersion = serializationVersion
            )
        }
    }
}
