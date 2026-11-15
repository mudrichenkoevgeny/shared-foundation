package io.github.mudrichenkoevgeny.shared.foundation.tools.projectgenerator

internal object TemplateReplacer {
    private const val TOKEN_PROJECT_NAME = "{{PROJECT_NAME}}"
    private const val TOKEN_PROJECT_TITLE = "{{PROJECT_TITLE}}"
    private const val TOKEN_PROJECT_REPO_NAME = "{{PROJECT_REPO_NAME}}"
    private const val TOKEN_GROUP_ID = "{{GROUP_ID}}"
    private const val TOKEN_PACKAGE_NAME = "{{PACKAGE_NAME}}"
    private const val TOKEN_PACKAGE_DIR_PATH = "{{PACKAGE_DIR_PATH}}"
    private const val TOKEN_NPM_SCOPE = "{{NPM_SCOPE}}"
    private const val TOKEN_NPM_PACKAGE_NAME = "{{NPM_PACKAGE_NAME}}"
    private const val TOKEN_SF_VERSION = "{{SF_VERSION}}"
    private const val TOKEN_KOTLIN_VERSION = "{{KOTLIN_VERSION}}"
    private const val TOKEN_TARGET_JVM = "{{TARGET_JVM}}"
    private const val TOKEN_TOOLCHAIN_JVM = "{{TOOLCHAIN_JVM}}"
    private const val TOKEN_DETEKT_VERSION = "{{DETEKT_VERSION}}"
    private const val TOKEN_KTLINT_VERSION = "{{KTLINT_VERSION}}"
    private const val TOKEN_SERIALIZATION_VERSION = "{{SERIALIZATION_VERSION}}"
    private const val TOKEN_MAVEN_LOCAL_REPOS = "{{MAVEN_LOCAL_REPOS}}"

    private const val MAVEN_LOCAL_REPOS_SNIPPET = "        mavenLocal()\n"

    fun buildReplacementMap(config: ProjectGeneratorConfig): Map<String, String> {
        val mavenLocalRepos = if (config.linkLocal) MAVEN_LOCAL_REPOS_SNIPPET else ""
        return mapOf(
            TOKEN_PROJECT_NAME to config.projectName,
            TOKEN_PROJECT_TITLE to config.projectTitle,
            TOKEN_PROJECT_REPO_NAME to config.projectRepoName,
            TOKEN_GROUP_ID to config.groupId,
            TOKEN_PACKAGE_NAME to config.packageName,
            TOKEN_PACKAGE_DIR_PATH to config.packageDirPath,
            TOKEN_NPM_SCOPE to config.npmScope,
            TOKEN_NPM_PACKAGE_NAME to config.npmPackageName,
            TOKEN_SF_VERSION to config.sfVersion,
            TOKEN_KOTLIN_VERSION to config.kotlinVersion,
            TOKEN_TARGET_JVM to config.targetJvm,
            TOKEN_TOOLCHAIN_JVM to config.toolchainJvm,
            TOKEN_DETEKT_VERSION to config.detektVersion,
            TOKEN_KTLINT_VERSION to config.ktlintVersion,
            TOKEN_SERIALIZATION_VERSION to config.serializationVersion,
            TOKEN_MAVEN_LOCAL_REPOS to mavenLocalRepos
        )
    }

    fun replaceAll(content: String, replacements: Map<String, String>): String {
        var result = content
        for ((key, value) in replacements) {
            result = result.replace(key, value)
        }
        return result
    }
}
