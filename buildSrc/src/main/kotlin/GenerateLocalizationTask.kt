package io.github.mudrichenkoevgeny.build.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class GenerateLocalizationTask : DefaultTask() {

    @get:InputDirectory
    abstract val resourcesDir: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun generate() {
        val rootResources = resourcesDir.get().asFile
        val localizationRoot = File(rootResources, "localization")
        if (!localizationRoot.exists()) return

        val outBaseDir = outputDir.get().asFile
        outBaseDir.deleteRecursively()
        outBaseDir.mkdirs()

        localizationRoot.walkTopDown()
            .filter { it.isDirectory && it.name == "en" }
            .forEach { enDir ->
                val moduleRelativePath = enDir.parentFile.relativeTo(localizationRoot).path.replace(File.separatorChar, '/')
                val packageSuffix = moduleRelativePath.replace('/', '.')
                val packageName = "io.github.mudrichenkoevgeny.shared.foundation.$packageSuffix.localization"

                val jsonFiles = enDir.listFiles { file -> file.extension == "json" } ?: return@forEach

                val generatedClassNames = mutableListOf<String>()

                jsonFiles.forEach { jsonFile ->
                    if (jsonFile.name == "email_templates.json") return@forEach

                    val bundleName = jsonFile.nameWithoutExtension
                    val className = bundleName.split('_').joinToString("") { part ->
                        part.replaceFirstChar { char -> char.uppercase() }
                    } + "Strings"

                    val classContent = generateBundleClass(
                        packageName = packageName,
                        className = className,
                        modulePath = moduleRelativePath,
                        bundleName = bundleName,
                        jsonFile = jsonFile
                    )

                    val packageFolder = File(outBaseDir, packageName.replace('.', File.separatorChar))
                    packageFolder.mkdirs()
                    File(packageFolder, "$className.kt").writeText(classContent)
                    generatedClassNames.add(className)
                }

                if (generatedClassNames.isNotEmpty()) {
                    val moduleFacadeName = moduleRelativePath.split('/').last().split('_')
                        .joinToString("") { part -> part.replaceFirstChar { char -> char.uppercase() } } + "ModuleStrings"

                    val facadeContent = generateModuleFacade(
                        packageName = packageName,
                        facadeName = moduleFacadeName,
                        classes = generatedClassNames
                    )

                    val packageFolder = File(outBaseDir, packageName.replace('.', File.separatorChar))
                    File(packageFolder, "$moduleFacadeName.kt").writeText(facadeContent)
                }
            }
    }

    private fun generateBundleClass(
        packageName: String,
        className: String,
        modulePath: String,
        bundleName: String,
        jsonFile: File
    ): String {
        val jsonContent = jsonFile.readText()
        val entries = parseFlatJson(jsonContent)
        val placeholderRegex = Regex("""\{([a-zA-Z0-9_]+)\}""")
        val commonPackage = "io.github.mudrichenkoevgeny.shared.foundation.core.common.localization"

        var hasPlaceholders = false
        val members = StringBuilder()

        entries.forEach { (rawKey, value) ->
            val placeholders = placeholderRegex.findAll(value).map { it.groupValues[1] }.distinct().toList()
            val cleanKey = rawKey.removeSuffix("_without_args").removeSuffix("_with_args")
            val propertyName = toCamelCase(cleanKey)

            if (placeholders.isEmpty()) {
                val propName = if (rawKey.endsWith("_without_args")) "${propertyName}WithoutArgs" else propertyName
                members.append(
                    """
    val $propName: String
        get() = get("$rawKey")

"""
                )
            } else {
                hasPlaceholders = true
                val fnName = if (rawKey.endsWith("_with_args")) propertyName else propertyName
                val paramsSignature = placeholders.joinToString(", ") { "$it: Any" }
                val mapEntries = placeholders.joinToString(", ") { "\"$it\" to $it" }

                members.append(
                    """
    fun $fnName($paramsSignature): String {
        val template = get("$rawKey")
        return LocalizationFormatter.format(template, mapOf($mapEntries))
    }

"""
                )
            }
        }

        val importsList = mutableListOf<String>()
        if (packageName != commonPackage) {
            if (hasPlaceholders) {
                importsList.add("import $commonPackage.LocalizationFormatter")
            }
            importsList.add("import $commonPackage.LocalizedStringResolver")
        }

        val importsBlock = if (importsList.isNotEmpty()) {
            "\n\n" + importsList.joinToString("\n") + "\n\n"
        } else {
            "\n\n"
        }

        val classDeclaration = """class $className(
    private val resolver: LocalizedStringResolver,
    private val locale: String
) {
    private fun get(key: String): String =
        resolver.resolve("$modulePath", "$bundleName", key, locale) ?: key
$members}"""

        return "package $packageName$importsBlock$classDeclaration"
    }

    private fun generateModuleFacade(
        packageName: String,
        facadeName: String,
        classes: List<String>
    ): String {
        val commonPackage = "io.github.mudrichenkoevgeny.shared.foundation.core.common.localization"
        val importsBlock = if (packageName != commonPackage) {
            "\n\nimport $commonPackage.LocalizedStringResolver\n\n"
        } else {
            "\n\n"
        }

        val declarations = classes.joinToString("\n") { cls ->
            val fieldName = cls.removeSuffix("Strings").replaceFirstChar { it.lowercase() }
            "    val $fieldName: $cls = $cls(resolver, locale)"
        }

        val classDeclaration = """class $facadeName(
    resolver: LocalizedStringResolver,
    locale: String
) {
$declarations
}"""

        return "package $packageName$importsBlock$classDeclaration"
    }

    private fun toCamelCase(snakeCase: String): String {
        val parts = snakeCase.split('_')
        return parts.first() + parts.drop(1).joinToString("") { part ->
            part.replaceFirstChar { char -> char.uppercase() }
        }
    }

    private fun parseFlatJson(content: String): Map<String, String> {
        val result = mutableMapOf<String, String>()
        val lineRegex = Regex(""""([^"]+)"\s*:\s*"((?:\\.|[^"\\])*)"""")
        lineRegex.findAll(content).forEach { match ->
            val key = match.groupValues[1]
            val value = match.groupValues[2].replace("\\\"", "\"").replace("\\\\", "\\")
            result[key] = value
        }
        return result
    }
}