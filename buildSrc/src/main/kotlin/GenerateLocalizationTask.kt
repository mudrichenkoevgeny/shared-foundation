package io.github.mudrichenkoevgeny.build.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import java.io.File

@CacheableTask
abstract class GenerateLocalizationTask : DefaultTask() {

    @get:Optional
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val resourcesDir: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    private data class RawEmailTemplateEntry(
        val templateKey: String,
        val subject: String,
        val body: String
    )

    @TaskAction
    fun generate() {
        val rootResources = resourcesDir.orNull?.asFile ?: return
        if (!rootResources.exists()) return
        val localizationRoot = File(rootResources, "localization")
        if (!localizationRoot.exists()) return

        val outBaseDir = outputDir.get().asFile
        outBaseDir.deleteRecursively()
        outBaseDir.mkdirs()

        val moduleDirs = localizationRoot.walkTopDown()
            .filter { it.isDirectory && it.name == "en" }
            .map { it.parentFile }
            .distinct()
            .sortedBy { it.path }
            .toList()

        moduleDirs.forEach { moduleDir ->
            val moduleRelativePath = moduleDir.relativeTo(localizationRoot).path.replace(File.separatorChar, '/')
            val packageSuffix = moduleRelativePath.replace('/', '.')
            val packageName = "io.github.mudrichenkoevgeny.shared.foundation.$packageSuffix.localization"

            val modulePrefix = moduleRelativePath.split('/').last().split('_')
                .joinToString("") { part -> part.replaceFirstChar { char -> char.uppercase() } }

            val enDir = File(moduleDir, "en")
            val jsonFiles = enDir.listFiles { file -> file.extension == "json" }?.sortedBy { it.name } ?: return@forEach

            val generatedClassNames = mutableListOf<String>()

            jsonFiles.forEach { jsonFile ->
                if (jsonFile.name == "email_templates.json") {
                    val className = "${modulePrefix}EmailTemplates"
                    val classContent = generateEmailTemplatesClass(
                        packageName = packageName,
                        className = className,
                        modulePath = moduleRelativePath,
                        jsonFile = jsonFile,
                        modulePrefix = modulePrefix
                    )

                    val packageFolder = File(outBaseDir, packageName.replace('.', File.separatorChar))
                    packageFolder.mkdirs()
                    File(packageFolder, "$className.kt").writeText(classContent)
                    generatedClassNames.add(className)
                    return@forEach
                }

                val bundleName = jsonFile.nameWithoutExtension
                val className = bundleName.split('_').joinToString("") { part ->
                    part.replaceFirstChar { char -> char.uppercase() }
                } + "Strings"

                val classContent = generateBundleClass(
                    packageName = packageName,
                    className = className,
                    modulePath = moduleRelativePath,
                    bundleName = bundleName,
                    jsonFile = jsonFile,
                    modulePrefix = modulePrefix
                )

                val packageFolder = File(outBaseDir, packageName.replace('.', File.separatorChar))
                packageFolder.mkdirs()
                File(packageFolder, "$className.kt").writeText(classContent)
                generatedClassNames.add(className)
            }

            if (generatedClassNames.isNotEmpty()) {
                val moduleFacadeName = "${modulePrefix}ModuleStrings"

                val facadeContent = generateModuleFacade(
                    packageName = packageName,
                    facadeName = moduleFacadeName,
                    classes = generatedClassNames,
                    modulePrefix = modulePrefix,
                    modulePath = moduleRelativePath
                )

                val packageFolder = File(outBaseDir, packageName.replace('.', File.separatorChar))
                packageFolder.mkdirs()
                File(packageFolder, "$moduleFacadeName.kt").writeText(facadeContent)

                val embeddedContent = generateEmbeddedResources(
                    packageName = packageName,
                    modulePrefix = modulePrefix,
                    moduleRelativePath = moduleRelativePath,
                    moduleDir = moduleDir
                )

                File(packageFolder, "${modulePrefix}EmbeddedLocalizationResources.kt").writeText(embeddedContent)
            }
        }
    }

    private fun generateEmbeddedResources(
        packageName: String,
        modulePrefix: String,
        moduleRelativePath: String,
        moduleDir: File
    ): String {
        val commonPackage = "io.github.mudrichenkoevgeny.shared.foundation.core.common.localization"
        val importsList = mutableListOf<String>()
        if (packageName != commonPackage) {
            importsList.add("import $commonPackage.LocalizationBundleProvider")
        }

        val importsBlock = if (importsList.isNotEmpty()) {
            "\n\n" + importsList.joinToString("\n") + "\n\n"
        } else {
            "\n\n"
        }

        val localeDirs = moduleDir.listFiles { file -> file.isDirectory }?.sortedBy { it.name } ?: emptyList()

        data class BundleKey(val bundleName: String, val locale: String, val file: File)

        val allBundles = mutableListOf<BundleKey>()
        localeDirs.forEach { locDir ->
            val locale = locDir.name.lowercase()
            locDir.listFiles { file -> file.extension == "json" }
                ?.sortedBy { it.name }
                ?.forEach { jsonFile ->
                    allBundles.add(BundleKey(jsonFile.nameWithoutExtension, locale, jsonFile))
                }
        }

        allBundles.sortWith(compareBy({ it.bundleName }, { it.locale }))

        val whenBranches = StringBuilder()
        val privateFunctions = StringBuilder()

        allBundles.forEachIndexed { index, bundle ->
            val fnName = "bundle$index"
            whenBranches.append("            \"${bundle.bundleName}/${bundle.locale}\" -> $fnName()\n")

            val jsonContent = bundle.file.readText()
            val entries = if (bundle.bundleName == "email_templates") {
                val parsedTemplates = parseEmailTemplatesJson(jsonContent)
                val flattened = mutableMapOf<String, String>()
                parsedTemplates.forEach { t ->
                    flattened["${t.templateKey}.subject"] = t.subject
                    flattened["${t.templateKey}.body"] = t.body
                }
                flattened.toSortedMap()
            } else {
                parseFlatJson(jsonContent).toSortedMap()
            }

            val mapContent = if (entries.isEmpty()) {
                "emptyMap()"
            } else {
                val pairs = entries.entries.joinToString(",\n") { (k, v) ->
                    "        \"${escapeKotlinString(k)}\" to \"${escapeKotlinString(v)}\""
                }
                "mapOf(\n$pairs\n    )"
            }

            privateFunctions.append("""
    private fun $fnName(): Map<String, String> = $mapContent
""")
        }

        val className = "${modulePrefix}EmbeddedLocalizationResources"

        val classDeclaration = """internal object $className : LocalizationBundleProvider {

    override fun get(module: String, file: String, locale: String): Map<String, String>? {
        if (module != "$moduleRelativePath") return null
        return when ("${'$'}file/${'$'}locale") {
$whenBranches            else -> null
        }
    }

$privateFunctions}"""

        return "package $packageName$importsBlock$classDeclaration"
    }

    private fun generateModuleFacade(
        packageName: String,
        facadeName: String,
        classes: List<String>,
        modulePrefix: String,
        modulePath: String
    ): String {
        val commonPackage = "io.github.mudrichenkoevgeny.shared.foundation.core.common.localization"
        val importsList = mutableSetOf<String>()
        if (packageName != commonPackage) {
            importsList.add("import $commonPackage.CommonLocales")
            importsList.add("import $commonPackage.DefaultLocalizedStringResolver")
            importsList.add("import $commonPackage.LocalizationRegistry")
            importsList.add("import $commonPackage.LocalizedStringResolver")
        }

        val importsBlock = if (importsList.isNotEmpty()) {
            "\n\n" + importsList.sorted().joinToString("\n") + "\n\n"
        } else {
            "\n\n"
        }

        val declarations = classes.joinToString("\n") { cls ->
            val rawName = if (cls.endsWith("EmailTemplates")) {
                cls.removeSuffix("EmailTemplates") + "EmailTemplates"
            } else {
                cls.removeSuffix("Strings")
            }
            val fieldName = if (cls.endsWith("EmailTemplates")) "emailTemplates" else rawName.replaceFirstChar { it.lowercase() }
            "    val $fieldName: $cls = $cls(resolver, locale)"
        }

        val classDeclaration = """class $facadeName(
    resolver: LocalizedStringResolver = DefaultLocalizedStringResolver(),
    locale: String = CommonLocales.DEFAULT
) {
    init {
        LocalizationRegistry.register("$modulePath", ${modulePrefix}EmbeddedLocalizationResources)
    }

$declarations
}"""

        return "package $packageName$importsBlock$classDeclaration"
    }

    private fun generateEmailTemplatesClass(
        packageName: String,
        className: String,
        modulePath: String,
        jsonFile: File,
        modulePrefix: String
    ): String {
        val commonPackage = "io.github.mudrichenkoevgeny.shared.foundation.core.common.localization"
        val jsonContent = jsonFile.readText()
        val entries = parseEmailTemplatesJson(jsonContent)
        val placeholderRegex = Regex("""\{([a-zA-Z0-9_]+)\}""")

        val members = StringBuilder()

        entries.forEach { entry ->
            val placeholders = (placeholderRegex.findAll(entry.subject) + placeholderRegex.findAll(entry.body))
                .map { it.groupValues[1] }
                .distinct()
                .toList()

            val cleanKey = entry.templateKey.removeSuffix("_without_args").removeSuffix("_with_args")
            val propertyName = toCamelCase(cleanKey)

            if (placeholders.isEmpty()) {
                val propName = if (entry.templateKey.endsWith("_without_args")) "${propertyName}WithoutArgs" else propertyName
                members.append(
                    """
    val $propName: EmailTemplate
        get() = get("${entry.templateKey}")

"""
                )
            } else {
                val fnName = if (entry.templateKey.endsWith("_with_args")) propertyName else propertyName
                val paramsSignature = placeholders.joinToString(", ") { "$it: Any" }
                val mapEntries = placeholders.joinToString(", ") { "\"$it\" to $it" }

                members.append(
                    """
    fun $fnName($paramsSignature): EmailTemplate {
        val raw = get("${entry.templateKey}")
        val args = mapOf($mapEntries)
        return EmailTemplate(
            subject = LocalizationFormatter.format(raw.subject, args),
            body = LocalizationFormatter.format(raw.body, args)
        )
    }

"""
                )
            }
        }

        val importsList = mutableSetOf<String>()
        importsList.add("import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.email.EmailTemplate")
        if (packageName != commonPackage) {
            importsList.add("import $commonPackage.CommonLocales")
            importsList.add("import $commonPackage.DefaultLocalizedStringResolver")
            importsList.add("import $commonPackage.LocalizationFormatter")
            importsList.add("import $commonPackage.LocalizationRegistry")
            importsList.add("import $commonPackage.LocalizedStringResolver")
        }

        val importsBlock = if (importsList.isNotEmpty()) {
            "\n\n" + importsList.sorted().joinToString("\n") + "\n\n"
        } else {
            "\n\n"
        }

        val classDeclaration = """class $className(
    private val resolver: LocalizedStringResolver = DefaultLocalizedStringResolver(),
    private val locale: String = CommonLocales.DEFAULT
) {
    init {
        LocalizationRegistry.register("$modulePath", ${modulePrefix}EmbeddedLocalizationResources)
    }

    private fun get(key: String): EmailTemplate {
        val subject = resolver.resolve("$modulePath", "email_templates", "${'$'}key.subject", locale) ?: ""
        val body = resolver.resolve("$modulePath", "email_templates", "${'$'}key.body", locale) ?: ""
        return EmailTemplate(subject, body)
    }
$members}"""

        return "package $packageName$importsBlock$classDeclaration"
    }

    private fun generateBundleClass(
        packageName: String,
        className: String,
        modulePath: String,
        bundleName: String,
        jsonFile: File,
        modulePrefix: String
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

        val importsList = mutableSetOf<String>()
        if (packageName != commonPackage) {
            importsList.add("import $commonPackage.LocalizationRegistry")
            if (hasPlaceholders) {
                importsList.add("import $commonPackage.LocalizationFormatter")
            }
            importsList.add("import $commonPackage.LocalizedStringResolver")
        }

        val importsBlock = if (importsList.isNotEmpty()) {
            "\n\n" + importsList.sorted().joinToString("\n") + "\n\n"
        } else {
            "\n\n"
        }

        val classDeclaration = """class $className(
    private val resolver: LocalizedStringResolver,
    private val locale: String
) {
    init {
        LocalizationRegistry.register("$modulePath", ${modulePrefix}EmbeddedLocalizationResources)
    }

    private fun get(key: String): String =
        resolver.resolve("$modulePath", "$bundleName", key, locale) ?: key
$members}"""

        return "package $packageName$importsBlock$classDeclaration"
    }

    private fun parseEmailTemplatesJson(content: String): List<RawEmailTemplateEntry> {
        val result = mutableListOf<RawEmailTemplateEntry>()
        val entryRegex = Regex(""""([a-zA-Z0-9_]+)"\s*:\s*\{""")
        val matches = entryRegex.findAll(content).toList()

        matches.forEachIndexed { index, match ->
            val key = match.groupValues[1]
            val startIndex = match.range.last + 1
            val endIndex = if (index + 1 < matches.size) matches[index + 1].range.first else content.length
            val block = content.substring(startIndex, endIndex)

            val subjectRegex = Regex(""""subject"\s*:\s*"((?:\\.|[^"\\])*)"""")
            val bodyRegex = Regex(""""body"\s*:\s*"((?:\\.|[^"\\])*)"""")

            val subject = subjectRegex.find(block)?.groupValues?.get(1)?.let { unescapeJsonString(it) } ?: ""
            val body = bodyRegex.find(block)?.groupValues?.get(1)?.let { unescapeJsonString(it) } ?: ""

            result.add(RawEmailTemplateEntry(key, subject, body))
        }
        return result
    }

    private fun escapeKotlinString(value: String): String = buildString(value.length) {
        for (c in value) {
            when (c) {
                '\\' -> append("\\\\")
                '"' -> append("\\\"")
                '$' -> append("\\$")
                '\n' -> append("\\n")
                '\r' -> append("\\r")
                '\t' -> append("\\t")
                else -> append(c)
            }
        }
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
            val value = unescapeJsonString(match.groupValues[2])
            result[key] = value
        }
        return result
    }

    private fun unescapeJsonString(raw: String): String {
        val sb = StringBuilder(raw.length)
        var i = 0
        while (i < raw.length) {
            val c = raw[i]
            if (c == '\\' && i + 1 < raw.length) {
                when (val next = raw[i + 1]) {
                    '"' -> { sb.append('"'); i += 2 }
                    '\\' -> { sb.append('\\'); i += 2 }
                    '/' -> { sb.append('/'); i += 2 }
                    'n' -> { sb.append('\n'); i += 2 }
                    'r' -> { sb.append('\r'); i += 2 }
                    't' -> { sb.append('\t'); i += 2 }
                    'b' -> { sb.append('\b'); i += 2 }
                    'f' -> { sb.append('\u000C'); i += 2 }
                    'u' -> {
                        if (i + 5 < raw.length) {
                            val hex = raw.substring(i + 2, i + 6)
                            sb.append(hex.toInt(16).toChar())
                            i += 6
                        } else {
                            sb.append(c)
                            i++
                        }
                    }
                    else -> { sb.append(next); i += 2 }
                }
            } else {
                sb.append(c)
                i++
            }
        }
        return sb.toString()
    }
}
