package io.github.mudrichenkoevgeny.npmgenerator.localization

import io.github.mudrichenkoevgeny.npmgenerator.engine.NpmGeneratorConfig
import io.github.mudrichenkoevgeny.npmgenerator.util.decapitalizeFirstLetter
import java.io.File

/**
 * Generates JS and DTS localization code from JSON resource files.
 */
public fun generateLocalizationCode(
    config: NpmGeneratorConfig? = null,
    rootDir: File? = null,
    importedFoundationJs: MutableSet<String>? = null,
    importedFoundationDts: MutableSet<String>? = null
): Pair<String, String> {
    val targetDir = config?.localizationJsonDirectory
        ?: config?.sourceCodeDirectory
        ?: (rootDir?.let { File(it, "src") } ?: File("src"))

    val excludedBundles = config?.excludedLocalizationBundles ?: emptySet()
    val allResources = collectLocalizationResources(targetDir, excludedBundles)
    if (allResources.isEmpty()) {
        return Pair("", "")
    }

    val isFoundation = config == null || config.basePackagePrefix.isEmpty() || (config.foundationImportPackage.isNotEmpty() && config.packageName == config.foundationImportPackage)
    val fallbackLocale = if (allResources.containsKey("en")) "en" else allResources.keys.first()
    val fallbackData = allResources[fallbackLocale] ?: emptyMap()
    val modulesList = buildModuleLocalizationData(fallbackData)

    if (!isFoundation) {
        importedFoundationJs?.add("formatLocalizedString")
        importedFoundationJs?.add("defaultLocalizedStringResolver")
        importedFoundationDts?.add("LocalizedStringResolver")
    }

    val rootClassName = config?.localizationRootClassName ?: resolveRootClassName(config?.packageName)
    val rawManagerName = config?.localizationManagerName ?: "${rootClassName.removeSuffix("Localization")}LocalizationManager"
    val managerName = if (rawManagerName == rootClassName) "${rootClassName}Manager" else rawManagerName
    val rootVarName = rootClassName.decapitalizeFirstLetter()

    val jsCode = generateLocalizationJs(allResources, modulesList, rootClassName, rootVarName, managerName, isFoundation)
    val dtsCode = generateLocalizationDts(modulesList, rootClassName, rootVarName, managerName, isFoundation)

    return Pair(jsCode, dtsCode)
}

private fun resolveRootClassName(packageName: String?): String {
    val pkgName = packageName ?: ""
    val simpleName = pkgName.removePrefix("@").split("/", "-").joinToString("") { part ->
        part.replaceFirstChar { char -> char.uppercase() }
    }.takeIf { it.isNotBlank() } ?: "App"
    return "${simpleName}Localization"
}

/**
 * Generates JS and DTS localization code given a root directory.
 */
public fun generateLocalizationCode(rootDir: File): Pair<String, String> =
    generateLocalizationCode(config = null, rootDir = rootDir)

private fun collectLocalizationResources(
    targetDir: File,
    excludedBundles: Set<String>
): MutableMap<String, MutableMap<String, MutableMap<String, Map<String, String>>>> {
    val searchRoot = if (targetDir.name == "localization") targetDir.parentFile ?: targetDir else targetDir
    val localizationDirs = searchRoot
        .walkTopDown()
        .onEnter { !it.name.startsWith(".") && it.name != "build" && it.name != "node_modules" }
        .filter { it.isDirectory && it.name == "localization" }
        .toList()

    val dirsToScan = if (targetDir.name == "localization") listOf(targetDir) else localizationDirs.ifEmpty { listOf(targetDir) }
    val allResources = mutableMapOf<String, MutableMap<String, MutableMap<String, Map<String, String>>>>()

    dirsToScan.forEach { locDir ->
        val fallbackModulePath = extractModulePath(locDir, searchRoot)
        locDir
            .walkTopDown()
            .onEnter { !it.name.startsWith(".") && it.name != "build" && it.name != "node_modules" }
            .filter { it.isFile && it.extension == "json" }
            .forEach { jsonFile ->
                val relativePath = jsonFile.relativeTo(locDir).path.replace(File.separatorChar, '/')
                val parts = relativePath.split('/')
                if (parts.size >= 2) {
                    val bundleName = parts.last().substringBeforeLast('.')
                    if (bundleName !in excludedBundles) {
                        val locale = parts[parts.size - 2]
                        val modulePath = if (parts.size >= 3) {
                            parts.subList(0, parts.size - 2).joinToString("/")
                        } else {
                            fallbackModulePath
                        }
                        val entries = parseFlatJson(jsonFile.readText())
                        allResources
                            .getOrPut(locale) { mutableMapOf() }
                            .getOrPut(modulePath) { mutableMapOf() }[bundleName] = entries
                    }
                }
            }
    }
    return allResources
}

private fun extractModulePath(locDir: File, searchRoot: File): String {
    if (locDir == searchRoot) return "common"
    val relPath = locDir.relativeTo(searchRoot).path.replace(File.separatorChar, '/')

    val regex = Regex("""^([^/]+(?:/[^/]+)?)/src/""")
    val match = regex.find(relPath)
    if (match != null) {
        val moduleName = match.groupValues[1]
        if (moduleName.startsWith("core/")) return moduleName
        if (moduleName.startsWith("feature/")) return moduleName.replace("feature/", "")
        return moduleName
    }

    val moduleSegment = relPath.substringBefore("/src/").substringBefore("/resources/").substringBefore("/localization")
    return moduleSegment.ifEmpty { "common" }
}

private fun buildModuleLocalizationData(
    fallbackData: Map<String, Map<String, Map<String, String>>>
): List<ModuleLocalizationData> {
    val placeholderRegex = Regex("""\{([a-zA-Z0-9_]+)\}""")

    return fallbackData.map { (modulePath, bundleMap) ->
        val modulePrefix = modulePath
            .split('/')
            .last()
            .split('_')
            .joinToString("") { part -> part.replaceFirstChar { char -> char.uppercase() } }
        val facadeClassName = "${modulePrefix}ModuleStrings"

        val bundlesList = bundleMap.map { (bundleName, jsonMap) ->
            val bundleClassName = "${modulePrefix}${bundleName.split('_').joinToString("") { part ->
                part.replaceFirstChar { char -> char.uppercase() }
            }}Strings"
            val entriesList = jsonMap.map { (rawKey, value) ->
                val placeholders = placeholderRegex
                    .findAll(value)
                    .map { it.groupValues[1] }
                    .distinct()
                    .toList()
                val cleanKey = rawKey.removeSuffix("_without_args").removeSuffix("_with_args")
                val propertyName = toCamelCase(cleanKey)
                val isMethod = placeholders.isNotEmpty()
                val propOrMethodName = if (!isMethod && rawKey.endsWith("_without_args")) {
                    "${propertyName}WithoutArgs"
                } else {
                    propertyName
                }
                LocalizationKeyEntry(
                    rawKey = rawKey,
                    value = value,
                    cleanKey = cleanKey,
                    propertyOrMethodName = propOrMethodName,
                    placeholders = placeholders,
                    isMethod = isMethod
                )
            }
            LocalizationBundleData(
                modulePath = modulePath,
                modulePrefix = modulePrefix,
                bundleName = bundleName,
                className = bundleClassName,
                entries = entriesList
            )
        }

        ModuleLocalizationData(
            modulePath = modulePath,
            modulePrefix = modulePrefix,
            facadeClassName = facadeClassName,
            bundles = bundlesList
        )
    }
}

private fun generateLocalizationJs(
    allResources: Map<String, Map<String, Map<String, Map<String, String>>>>,
    modulesList: List<ModuleLocalizationData>,
    rootClassName: String,
    rootVarName: String,
    managerName: String,
    isFoundation: Boolean
): String {
    val jsSb = StringBuilder()

    jsSb.append(
        """
export const localizationResources = ${formatAsJsObject(allResources)};
""".trimIndent()
    ).append("\n\n")

    if (isFoundation) {
        appendFoundationLocalizationJs(jsSb)
    }

    modulesList.forEach { moduleData ->
        appendModuleBundleClassesJs(jsSb, moduleData)
        appendModuleFacadeClassJs(jsSb, moduleData)
    }

    appendGlobalLocalizationJs(jsSb, modulesList, rootClassName, rootVarName, managerName)

    return jsSb.toString()
}

private fun appendFoundationLocalizationJs(jsSb: StringBuilder) {
    jsSb.append(
        """
export const defaultLocalizedStringResolver = (modulePath, bundleName, key, locale = 'en') => {
  return localizationResources[locale]?.[modulePath]?.[bundleName]?.[key]
    ?? localizationResources['en']?.[modulePath]?.[bundleName]?.[key];
};

export function formatLocalizedString(template, params = {}) {
  if (!template || typeof template !== 'string') return '';
  return template.replace(/\{([a-zA-Z0-9_]+)\}/g, (match, key) => {
    return params[key] !== undefined && params[key] !== null ? String(params[key]) : match;
  });
}
""".trimIndent()
    ).append("\n\n")
}

private fun appendModuleBundleClassesJs(
    jsSb: StringBuilder,
    moduleData: ModuleLocalizationData
) {
    moduleData.bundles.forEach { bundleData ->
        jsSb.append(
            """
export class ${bundleData.className} {
  constructor(resolver = defaultLocalizedStringResolver, locale = 'en') {
    this._resolver = resolver;
    this._locale = locale;
  }

  _get(key) {
    return this._resolver("${bundleData.modulePath}", "${bundleData.bundleName}", key, this._locale) ?? key;
  }

""".trimIndent()
        ).append("\n")

        bundleData.entries.forEach { entry ->
            if (!entry.isMethod) {
                jsSb.append(
                    """
  get ${entry.propertyOrMethodName}() {
    return this._get("${entry.rawKey}");
  }
                    """.trimIndent()
                ).append("\n\n")
            } else {
                val argList = entry.placeholders.indices.joinToString(", ") { "arg${it + 1}" }
                val objProps = entry.placeholders.mapIndexed { idx, p -> "$p: arg${idx + 1}" }.joinToString(", ")
                jsSb.append(
                    """
  ${entry.propertyOrMethodName}($argList) {
    const template = this._get("${entry.rawKey}");
    const params = (typeof arg1 === 'object' && arg1 !== null) ? arg1 : { $objProps };
    return formatLocalizedString(template, params);
  }
                    """.trimIndent()
                ).append("\n\n")
            }
        }
        jsSb.append("}\n\n")
    }
}

private fun appendModuleFacadeClassJs(
    jsSb: StringBuilder,
    moduleData: ModuleLocalizationData
) {
    jsSb.append(
        """
export class ${moduleData.facadeClassName} {
  constructor(resolver = defaultLocalizedStringResolver, locale = 'en') {

""".trimIndent()
    ).append("\n")

    moduleData.bundles.forEach { bundleData ->
        val fieldName = bundleData.bundleName
            .split('_')
            .joinToString("") { part ->
                part.replaceFirstChar { char -> char.uppercase() }
            }.decapitalizeFirstLetter()
        jsSb.append("    this.$fieldName = new ${bundleData.className}(resolver, locale);\n")
    }

    jsSb.append("  }\n}\n\n")
}

private fun appendGlobalLocalizationJs(
    jsSb: StringBuilder,
    modulesList: List<ModuleLocalizationData>,
    rootClassName: String,
    rootVarName: String,
    managerName: String
) {
    jsSb.append(
        """
let _globalLocale = 'en';
let _globalResolver = defaultLocalizedStringResolver;

export class $rootClassName {
  constructor(resolver = defaultLocalizedStringResolver, locale = 'en') {
""".trimIndent()
    ).append("\n")

    modulesList.forEach { moduleData ->
        val fieldName = moduleData.modulePrefix.decapitalizeFirstLetter()
        jsSb.append("    this.$fieldName = new ${moduleData.facadeClassName}(resolver, locale);\n")
    }
    jsSb.append(
        """  }

  static setLocale(locale) {
    if (locale) _globalLocale = locale;
  }

  static getLocale() {
    return _globalLocale;
  }

  static setResolver(resolver) {
    if (resolver) _globalResolver = resolver;
  }

  static getResolver() {
    return _globalResolver;
  }
}

export const $managerName = {
  setLocale(locale) {
    $rootClassName.setLocale(locale);
  },
  getLocale() {
    return $rootClassName.getLocale();
  },
  setResolver(resolver) {
    $rootClassName.setResolver(resolver);
  },
  getResolver() {
    return $rootClassName.getResolver();
  }
};

export const $rootVarName = new Proxy({}, {
  get(_, prop) {
    return new $rootClassName(_globalResolver, _globalLocale)[prop];
  }
});

""".trimIndent()
    ).append("\n")

    if (rootVarName != "sharedLocalization" && rootClassName == "SharedFoundationLocalization") {
        jsSb.append("export const sharedLocalization = $rootVarName;\n\n")
    }

    modulesList.forEach { moduleData ->
        val singletonName = "${moduleData.modulePrefix.decapitalizeFirstLetter()}Strings"
        jsSb.append(
            """
export const $singletonName = new Proxy({}, {
  get(_, prop) {
    return new ${moduleData.facadeClassName}(_globalResolver, _globalLocale)[prop];
  }
});

""".trimIndent()
        ).append("\n")
    }
}

private fun generateLocalizationDts(
    modulesList: List<ModuleLocalizationData>,
    rootClassName: String,
    rootVarName: String,
    managerName: String,
    isFoundation: Boolean
): String {
    val dtsSb = StringBuilder()

    if (isFoundation) {
        dtsSb.append(
            """
export type LocalizedStringResolver = (modulePath: string, bundleName: string, key: string, locale?: string) => string | undefined;

export declare function formatLocalizedString(template: string, params?: Record<string, unknown>): string;

export declare const defaultLocalizedStringResolver: LocalizedStringResolver;
""".trimIndent()
        ).append("\n\n")
    }

    dtsSb.append(
        """
export declare const localizationResources: Record<string, Record<string, Record<string, Record<string, string>>>>;

""".trimIndent()
    ).append("\n\n")

    modulesList.forEach { moduleData ->
        appendModuleBundleClassesDts(dtsSb, moduleData)
        appendModuleFacadeClassDts(dtsSb, moduleData)
    }

    appendGlobalLocalizationDts(dtsSb, modulesList, rootClassName, rootVarName, managerName)

    return dtsSb.toString()
}

private fun appendModuleBundleClassesDts(
    dtsSb: StringBuilder,
    moduleData: ModuleLocalizationData
) {
    moduleData.bundles.forEach { bundleData ->
        val rawKeysUnion = bundleData.entries.joinToString(" | ") { "'${it.rawKey}'" }.ifEmpty { "string" }
        val propsUnion = bundleData.entries.joinToString(" | ") { "'${it.propertyOrMethodName}'" }.ifEmpty { "string" }

        dtsSb.append(
            """
export type ${bundleData.className}RawKey = $rawKeysUnion;
export type ${bundleData.className}Property = $propsUnion;

export class ${bundleData.className} {
  constructor(resolver?: LocalizedStringResolver, locale?: string);

""".trimIndent()
        ).append("\n")

        bundleData.entries.forEach { entry ->
            val escapedVal = entry.value.replace("\n", " ").replace("*/", "* /")
            if (!entry.isMethod) {
                dtsSb.append(
                    """
  /**
   * Template: "$escapedVal"
   */
  readonly ${entry.propertyOrMethodName}: string;

""".trimIndent()
                ).append("\n")
            } else {
                val paramsObjType = entry.placeholders.joinToString("; ") { "$it: string | number" }
                val paramsPositional = entry.placeholders.joinToString(", ") { "$it: string | number" }
                dtsSb.append(
                    """
  /**
   * Template: "$escapedVal"
   */
  ${entry.propertyOrMethodName}(params: { $paramsObjType }): string;
  ${entry.propertyOrMethodName}($paramsPositional): string;

""".trimIndent()
                ).append("\n")
            }
        }

        dtsSb.append("}\n\n")
    }
}

private fun appendModuleFacadeClassDts(
    dtsSb: StringBuilder,
    moduleData: ModuleLocalizationData
) {
    dtsSb.append(
        """
export class ${moduleData.facadeClassName} {
  constructor(resolver?: LocalizedStringResolver, locale?: string);

""".trimIndent()
    ).append("\n")

    moduleData.bundles.forEach { bundleData ->
        val fieldName = bundleData.bundleName
            .split('_')
            .joinToString("") { part ->
                part.replaceFirstChar { char -> char.uppercase() }
            }.decapitalizeFirstLetter()
        dtsSb.append("  readonly $fieldName: ${bundleData.className};\n")
    }

    dtsSb.append("}\n\n")
}

private fun appendGlobalLocalizationDts(
    dtsSb: StringBuilder,
    modulesList: List<ModuleLocalizationData>,
    rootClassName: String,
    rootVarName: String,
    managerName: String
) {
    dtsSb.append(
        """
export class $rootClassName {
  constructor(resolver?: LocalizedStringResolver, locale?: string);
""".trimIndent()
    ).append("\n")

    modulesList.forEach { moduleData ->
        val fieldName = moduleData.modulePrefix.decapitalizeFirstLetter()
        dtsSb.append("  readonly $fieldName: ${moduleData.facadeClassName};\n")
    }
    dtsSb.append(
        """  static setLocale(locale: string): void;
  static getLocale(): string;
  static setResolver(resolver: LocalizedStringResolver): void;
  static getResolver(): LocalizedStringResolver;
}

export declare const $managerName: {
  setLocale(locale: string): void;
  getLocale(): string;
  setResolver(resolver: LocalizedStringResolver): void;
  getResolver(): LocalizedStringResolver;
};

export declare const $rootVarName: $rootClassName;

""".trimIndent()
    ).append("\n")

    if (rootVarName != "sharedLocalization" && rootClassName == "SharedFoundationLocalization") {
        dtsSb.append("export declare const sharedLocalization: $rootClassName;\n\n")
    }

    modulesList.forEach { moduleData ->
        val singletonName = "${moduleData.modulePrefix.decapitalizeFirstLetter()}Strings"
        dtsSb.append("export declare const $singletonName: ${moduleData.facadeClassName};\n")
    }
}

/**
 * Parses flat key-value pairs from a JSON string.
 */
public fun parseFlatJson(content: String): Map<String, String> {
    val result = mutableMapOf<String, String>()
    val lineRegex = Regex(""""([^"]+)"\s*:\s*"((?:\\.|[^"\\])*)"""")
    lineRegex.findAll(content).forEach { match ->
        val key = match.groupValues[1]
        val value = match.groupValues[2].replace("\\\"", "\"").replace("\\\\", "\\")
        result[key] = value
    }
    return result
}

/**
 * Converts snake_case string to camelCase.
 */
public fun toCamelCase(snakeCase: String): String {
    val parts = snakeCase.split('_')
    return parts.first() +
        parts.drop(1).joinToString("") { part ->
            part.replaceFirstChar { char -> char.uppercase() }
        }
}

/**
 * Formats a Kotlin map or primitive object as a JavaScript object literal string.
 */
public fun formatAsJsObject(obj: Any?): String = when (obj) {
    is Map<*, *> -> {
        val entries = obj.entries.joinToString(",\n") { (k, v) ->
            val keyStr = if (k.toString().matches(Regex("^[a-zA-Z_$][a-zA-Z0-9_$]*$"))) {
                k.toString()
            } else {
                "\"$k\""
            }
            "$keyStr: ${formatAsJsObject(v)}"
        }
        "{\n${entries.indent(2)}\n}"
    }
    is String -> "\"${obj.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n")}\""
    else -> obj.toString()
}

/**
 * Indents all lines in a string by the specified number of spaces.
 */
public fun String.indent(spaces: Int): String {
    val prefix = " ".repeat(spaces)
    return this.lines().joinToString("\n") { "$prefix$it" }
}
