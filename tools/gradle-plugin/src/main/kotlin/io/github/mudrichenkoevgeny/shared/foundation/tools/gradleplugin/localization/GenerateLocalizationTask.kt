package io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.localization

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * Gradle task generating type-safe Kotlin localization accessors and embedded bundles from JSON resources.
 */
@CacheableTask
public abstract class GenerateLocalizationTask : DefaultTask() {

    /**
     * Base package prefix used to construct target localization package names.
     */
    @get:Input
    public abstract val basePackageName: Property<String>

    /**
     * Input resources directory containing the `localization` module hierarchy.
     */
    @get:Optional
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    public abstract val resourcesDir: DirectoryProperty

    /**
     * Output directory for generated Kotlin localization source files.
     */
    @get:OutputDirectory
    public abstract val outputDir: DirectoryProperty

    private data class RawEmailTemplateEntry(
        val templateKey: String,
        val subject: String,
        val body: String
    )

    private data class BundleKey(
        val bundleName: String,
        val locale: String,
        val file: File
    )

    /**
     * Executes the localization code generation task.
     */
    @TaskAction
    public fun generate() {
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
            processModule(outBaseDir, localizationRoot, moduleDir)
        }
    }

    private fun processModule(
        outBaseDir: File,
        localizationRoot: File,
        moduleDir: File
    ) {
        val moduleRelativePath = moduleDir.relativeTo(localizationRoot).path.replace(File.separatorChar, '/')
        val packageSuffix = moduleRelativePath.replace('/', '.')
        val basePkg = basePackageName.get().trimEnd('.')
        val packageName = if (packageSuffix.isEmpty()) {
            "$basePkg.localization"
        } else {
            "$basePkg.$packageSuffix.localization"
        }

        val modulePrefix = moduleRelativePath.split('/').last().split('_')
            .joinToString("") { part -> part.replaceFirstChar { char -> char.uppercase() } }

        val enDir = File(moduleDir, "en")
        val jsonFiles = enDir.listFiles { file -> file.extension == "json" }?.sortedBy { it.name } ?: return

        val generatedClassNames = mutableListOf<String>()

        jsonFiles.forEach { jsonFile ->
            val generatedName = processJsonFile(
                outBaseDir = outBaseDir,
                packageName = packageName,
                moduleRelativePath = moduleRelativePath,
                modulePrefix = modulePrefix,
                jsonFile = jsonFile
            )
            generatedClassNames.add(generatedName)
        }

        if (generatedClassNames.isNotEmpty()) {
            generateModuleOutputs(
                outBaseDir = outBaseDir,
                packageName = packageName,
                modulePrefix = modulePrefix,
                moduleRelativePath = moduleRelativePath,
                moduleDir = moduleDir,
                generatedClassNames = generatedClassNames
            )
        }
    }

    private fun processJsonFile(
        outBaseDir: File,
        packageName: String,
        moduleRelativePath: String,
        modulePrefix: String,
        jsonFile: File
    ): String {
        if (jsonFile.name == "email_templates.json") {
            val className = "${modulePrefix}EmailTemplates"
            val classContent = generateEmailTemplatesClass(
                packageName = packageName,
                className = className,
                modulePath = moduleRelativePath,
                jsonFile = jsonFile,
                modulePrefix = modulePrefix
            )
            writeGeneratedFile(outBaseDir, packageName, className, classContent)
            return className
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
        writeGeneratedFile(outBaseDir, packageName, className, classContent)
        return className
    }

    private fun generateModuleOutputs(
        outBaseDir: File,
        packageName: String,
        modulePrefix: String,
        moduleRelativePath: String,
        moduleDir: File,
        generatedClassNames: List<String>
    ) {
        val moduleFacadeName = "${modulePrefix}ModuleStrings"
        val facadeContent = generateModuleFacade(
            packageName = packageName,
            facadeName = moduleFacadeName,
            classes = generatedClassNames,
            modulePrefix = modulePrefix,
            modulePath = moduleRelativePath
        )
        writeGeneratedFile(outBaseDir, packageName, moduleFacadeName, facadeContent)

        val embeddedContent = generateEmbeddedResources(
            packageName = packageName,
            modulePrefix = modulePrefix,
            moduleRelativePath = moduleRelativePath,
            moduleDir = moduleDir
        )
        writeGeneratedFile(outBaseDir, packageName, "${modulePrefix}EmbeddedLocalizationResources", embeddedContent)
    }

    private fun writeGeneratedFile(
        outBaseDir: File,
        packageName: String,
        className: String,
        content: String
    ) {
        val packageFolder = File(outBaseDir, packageName.replace('.', File.separatorChar))
        packageFolder.mkdirs()
        File(packageFolder, "$className.kt").writeText(content)
    }

    private fun generateEmbeddedResources(
        packageName: String,
        modulePrefix: String,
        moduleRelativePath: String,
        moduleDir: File
    ): String {
        val importsBlock = if (packageName != COMMON_PACKAGE) {
            "\n\nimport $COMMON_PACKAGE.LocalizationBundleProvider\n\n"
        } else {
            "\n\n"
        }

        val allBundles = collectAllBundles(moduleDir)
        val (whenBranches, privateFunctions) = buildEmbeddedBundleCode(allBundles)
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

    private fun collectAllBundles(moduleDir: File): List<BundleKey> {
        val localeDirs = moduleDir.listFiles { file -> file.isDirectory }?.sortedBy { it.name } ?: emptyList()
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
        return allBundles
    }

    private fun buildEmbeddedBundleCode(allBundles: List<BundleKey>): Pair<String, String> {
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

            val mapContent = formatMapContent(entries)
            privateFunctions.append("\n    private fun $fnName(): Map<String, String> = $mapContent\n")
        }
        return Pair(whenBranches.toString(), privateFunctions.toString())
    }

    private fun formatMapContent(entries: Map<String, String>): String {
        if (entries.isEmpty()) {
            return "emptyMap()"
        }
        val pairs = entries.entries.joinToString(",\n") { (k, v) ->
            "        \"${escapeKotlinString(k)}\" to \"${escapeKotlinString(v)}\""
        }
        return "mapOf(\n$pairs\n    )"
    }

    private fun generateModuleFacade(
        packageName: String,
        facadeName: String,
        classes: List<String>,
        modulePrefix: String,
        modulePath: String
    ): String {
        val importsList = mutableSetOf<String>()
        if (packageName != COMMON_PACKAGE) {
            importsList.add("import $COMMON_PACKAGE.CommonLocales")
            importsList.add("import $COMMON_PACKAGE.DefaultLocalizedStringResolver")
            importsList.add("import $COMMON_PACKAGE.LocalizationRegistry")
            importsList.add("import $COMMON_PACKAGE.LocalizedStringResolver")
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
            val fieldName = if (cls.endsWith("EmailTemplates")) {
                "emailTemplates"
            } else {
                rawName.replaceFirstChar { it.lowercase() }
            }
            "    public val $fieldName: $cls = $cls(resolver, locale)"
        }

        val classDeclaration = """public class $facadeName(
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
                val propName = if (entry.templateKey.endsWith("_without_args")) {
                    "${propertyName}WithoutArgs"
                } else {
                    propertyName
                }
                members.append(
                    """
    public val $propName: EmailTemplate
        get() = get("${entry.templateKey}")

"""
                )
            } else {
                val fnName = propertyName
                val paramsSignature = placeholders.joinToString(", ") { "$it: Any" }
                val mapEntries = placeholders.joinToString(", ") { "\"$it\" to $it" }

                members.append(
                    """
    public fun $fnName($paramsSignature): EmailTemplate {
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
        importsList.add(
            "import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.email.EmailTemplate")
        if (packageName != COMMON_PACKAGE) {
            importsList.add("import $COMMON_PACKAGE.CommonLocales")
            importsList.add("import $COMMON_PACKAGE.DefaultLocalizedStringResolver")
            importsList.add("import $COMMON_PACKAGE.LocalizationFormatter")
            importsList.add("import $COMMON_PACKAGE.LocalizationRegistry")
            importsList.add("import $COMMON_PACKAGE.LocalizedStringResolver")
        }

        val importsBlock = "\n\n" + importsList.sorted().joinToString("\n") + "\n\n"

        val classDeclaration = """public class $className(
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
    public val $propName: String
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
    public fun $fnName($paramsSignature): String {
        val template = get("$rawKey")
        return LocalizationFormatter.format(template, mapOf($mapEntries))
    }

"""
                )
            }
        }

        val importsList = mutableSetOf<String>()
        if (packageName != COMMON_PACKAGE) {
            importsList.add("import $COMMON_PACKAGE.LocalizationRegistry")
            if (hasPlaceholders) {
                importsList.add("import $COMMON_PACKAGE.LocalizationFormatter")
            }
            importsList.add("import $COMMON_PACKAGE.LocalizedStringResolver")
        }

        val importsBlock = if (importsList.isNotEmpty()) {
            "\n\n" + importsList.sorted().joinToString("\n") + "\n\n"
        } else {
            "\n\n"
        }

        val classDeclaration = """public class $className(
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
                val (unescaped, charsRead) = processEscapeSequence(raw, i)
                sb.append(unescaped)
                i += charsRead
            } else {
                sb.append(c)
                i++
            }
        }
        return sb.toString()
    }

    private fun processEscapeSequence(
        raw: String,
        index: Int
    ): Pair<String, Int> {
        return when (val next = raw[index + 1]) {
            '"' -> Pair("\"", 2)
            '\\' -> Pair("\\", 2)
            '/' -> Pair("/", 2)
            'n' -> Pair("\n", 2)
            'r' -> Pair("\r", 2)
            't' -> Pair("\t", 2)
            'b' -> Pair("\b", 2)
            'f' -> Pair("\u000C", 2)
            'u' -> {
                if (index + 5 < raw.length) {
                    val hex = raw.substring(index + 2, index + 6)
                    Pair(hex.toInt(16).toChar().toString(), 6)
                } else {
                    Pair("\\", 1)
                }
            }

            else -> Pair(next.toString(), 2)
        }
    }

    private companion object {
        private const val COMMON_PACKAGE: String =
            "io.github.mudrichenkoevgeny.shared.foundation.core.common.localization"
    }
}
