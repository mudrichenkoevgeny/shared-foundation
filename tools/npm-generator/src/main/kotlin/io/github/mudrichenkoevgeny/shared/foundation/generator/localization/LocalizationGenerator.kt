package io.github.mudrichenkoevgeny.shared.foundation.generator.localization

import io.github.mudrichenkoevgeny.shared.foundation.generator.decapitalizeFirstLetter
import java.io.File

data class LocalizationKeyEntry(
    val rawKey: String,
    val value: String,
    val cleanKey: String,
    val propertyOrMethodName: String,
    val placeholders: List<String>,
    val isMethod: Boolean
)

data class LocalizationBundleData(
    val modulePath: String,
    val modulePrefix: String,
    val bundleName: String,
    val className: String,
    val entries: List<LocalizationKeyEntry>
)

data class ModuleLocalizationData(
    val modulePath: String,
    val modulePrefix: String,
    val facadeClassName: String,
    val bundles: List<LocalizationBundleData>
)

fun generateLocalizationCode(rootDir: File): Pair<String, String> {
    val localizationDirs = rootDir.walkTopDown()
        .filter { it.isDirectory && it.name == "localization" && it.parentFile?.name == "resources" }
        .toList()

    val allResources = mutableMapOf<String, MutableMap<String, MutableMap<String, Map<String, String>>>>()

    localizationDirs.forEach { locDir ->
        locDir.walkTopDown()
            .filter { it.isFile && it.extension == "json" }
            .forEach { jsonFile ->
                val relativePath = jsonFile.relativeTo(locDir).path.replace(File.separatorChar, '/')
                val parts = relativePath.split('/')
                if (parts.size >= 3) {
                    val bundleNameWithExt = parts.last()
                    val bundleName = bundleNameWithExt.substringBeforeLast('.')
                    if (bundleName != "email_templates") {
                        val locale = parts[parts.size - 2]
                        val modulePath = parts.subList(0, parts.size - 2).joinToString("/")
                        val entries = parseFlatJson(jsonFile.readText())

                        allResources.getOrPut(locale) { mutableMapOf() }
                            .getOrPut(modulePath) { mutableMapOf() }[bundleName] = entries
                    }
                }
            }
    }

    if (allResources.isEmpty()) {
        return Pair("// No localization resources found", "// No localization resources found")
    }

    val fallbackLocale = if (allResources.containsKey("en")) "en" else allResources.keys.first()
    val fallbackData = allResources[fallbackLocale] ?: emptyMap()

    val placeholderRegex = Regex("""\{([a-zA-Z0-9_]+)\}""")

    val modulesList = fallbackData.map { (modulePath, bundleMap) ->
        val modulePrefix = modulePath.split('/').last().split('_')
            .joinToString("") { part -> part.replaceFirstChar { char -> char.uppercase() } }
        val facadeClassName = "${modulePrefix}ModuleStrings"

        val bundlesList = bundleMap.map { (bundleName, jsonMap) ->
            val bundleClassName = "${modulePrefix}${bundleName.split('_').joinToString("") { part -> part.replaceFirstChar { char -> char.uppercase() } }}Strings"
            val entriesList = jsonMap.map { (rawKey, value) ->
                val placeholders = placeholderRegex.findAll(value).map { it.groupValues[1] }.distinct().toList()
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

    val jsSb = StringBuilder()

    val jsonResourcesCode = formatAsJsObject(allResources)
    jsSb.append("""
        /**
         * Raw localization resource dictionaries by locale, module path, and bundle name.
         */
        export const localizationResources = $jsonResourcesCode;

        /**
         * Replaces `{placeholder}` tokens in string templates.
         */
        export function formatLocalizedString(template, params) {
          if (!template || !params) return template || '';
          return template.replace(/\{([a-zA-Z0-9_]+)\}/g, (match, key) => {
            return params[key] !== undefined && params[key] !== null ? String(params[key]) : match;
          });
        }

        /**
         * Default localized string resolver fetching strings from `localizationResources`.
         */
        export const defaultLocalizedStringResolver = (modulePath, bundleName, key, locale = 'en') => {
          const localeData = localizationResources[locale] || localizationResources['en'];
          return localeData?.[modulePath]?.[bundleName]?.[key];
        };

    """.trimIndent()).append("\n\n")

    modulesList.forEach { moduleData ->
        moduleData.bundles.forEach { bundleData ->
            jsSb.append("""
                export class ${bundleData.className} {
                  constructor(resolver = defaultLocalizedStringResolver, locale = 'en') {
                    this._resolver = resolver;
                    this._locale = locale;
                  }

                  _get(key) {
                    return this._resolver("${bundleData.modulePath}", "${bundleData.bundleName}", key, this._locale) ?? key;
                  }

            """.trimIndent()).append("\n")

            bundleData.entries.forEach { entry ->
                if (!entry.isMethod) {
                    jsSb.append("""
                          get ${entry.propertyOrMethodName}() {
                            return this._get("${entry.rawKey}");
                          }

                    """.trimIndent()).append("\n")
                } else {
                    val argList = entry.placeholders.indices.map { "arg${it + 1}" }.joinToString(", ")
                    val objProps = entry.placeholders.mapIndexed { idx, p -> "$p: arg${idx + 1}" }.joinToString(", ")
                    jsSb.append("""
                          ${entry.propertyOrMethodName}($argList) {
                            const template = this._get("${entry.rawKey}");
                            const params = (typeof arg1 === 'object' && arg1 !== null) ? arg1 : { $objProps };
                            return formatLocalizedString(template, params);
                          }

                    """.trimIndent()).append("\n")
                }
            }

            jsSb.append("}\n\n")
        }

        jsSb.append("""
            export class ${moduleData.facadeClassName} {
              constructor(resolver = defaultLocalizedStringResolver, locale = 'en') {

        """.trimIndent()).append("\n")
        moduleData.bundles.forEach { bundleData ->
            val fieldName = bundleData.bundleName.split('_').joinToString("") { part -> part.replaceFirstChar { char -> char.uppercase() } }.decapitalizeFirstLetter()
            jsSb.append("    this.$fieldName = new ${bundleData.className}(resolver, locale);\n")
        }
        jsSb.append("  }\n}\n\n")
    }

    jsSb.append("""
        export class SharedFoundationLocalization {
          constructor(resolver = defaultLocalizedStringResolver, locale = 'en') {

    """.trimIndent()).append("\n")
    modulesList.forEach { moduleData ->
        val fieldName = moduleData.modulePrefix.decapitalizeFirstLetter()
        jsSb.append("    this.$fieldName = new ${moduleData.facadeClassName}(resolver, locale);\n")
    }
    jsSb.append("  }\n}\n\n")

    jsSb.append("""
        let _globalLocale = 'en';
        let _globalResolver = defaultLocalizedStringResolver;

        export const SharedLocalization = {
          setLocale(locale) {
            if (locale) _globalLocale = locale;
          },
          getLocale() {
            return _globalLocale;
          },
          setResolver(resolver) {
            if (resolver) _globalResolver = resolver;
          },
          getResolver() {
            return _globalResolver;
          }
        };

        export const sharedLocalization = new Proxy({}, {
          get(_, prop) {
            return new SharedFoundationLocalization(_globalResolver, _globalLocale)[prop];
          }
        });

    """.trimIndent()).append("\n")

    modulesList.forEach { moduleData ->
        val singletonName = "${moduleData.modulePrefix.decapitalizeFirstLetter()}Strings"
        jsSb.append("""
            export const $singletonName = new Proxy({}, {
              get(_, prop) {
                return new ${moduleData.facadeClassName}(_globalResolver, _globalLocale)[prop];
              }
            });

        """.trimIndent()).append("\n")
    }

    val dtsSb = StringBuilder()

    dtsSb.append("""
        export type LocalizedStringResolver = (modulePath: string, bundleName: string, key: string, locale?: string) => string | undefined;

        export declare function formatLocalizedString(template: string, params?: Record<string, unknown>): string;

        export declare const defaultLocalizedStringResolver: LocalizedStringResolver;

        export declare const localizationResources: Record<string, Record<string, Record<string, Record<string, string>>>>;

    """.trimIndent()).append("\n\n")

    modulesList.forEach { moduleData ->
        moduleData.bundles.forEach { bundleData ->
            val rawKeysUnion = bundleData.entries.joinToString(" | ") { "'${it.rawKey}'" }.ifEmpty { "string" }
            val propsUnion = bundleData.entries.joinToString(" | ") { "'${it.propertyOrMethodName}'" }.ifEmpty { "string" }

            dtsSb.append("""
                export type ${bundleData.className}RawKey = $rawKeysUnion;
                export type ${bundleData.className}Property = $propsUnion;

                export class ${bundleData.className} {
                  constructor(resolver?: LocalizedStringResolver, locale?: string);

            """.trimIndent()).append("\n")

            bundleData.entries.forEach { entry ->
                val escapedVal = entry.value.replace("\n", " ").replace("*/", "* /")
                if (!entry.isMethod) {
                    dtsSb.append("""
                          /**
                           * Template: "$escapedVal"
                           */
                          get ${entry.propertyOrMethodName}(): string;

                    """.trimIndent()).append("\n")
                } else {
                    val paramsObjType = entry.placeholders.joinToString("; ") { "$it: string | number" }
                    val paramsPositional = entry.placeholders.joinToString(", ") { "$it: string | number" }
                    dtsSb.append("""
                          /**
                           * Template: "$escapedVal"
                           */
                          ${entry.propertyOrMethodName}(params: { $paramsObjType }): string;
                          ${entry.propertyOrMethodName}($paramsPositional): string;

                    """.trimIndent()).append("\n")
                }
            }

            dtsSb.append("}\n\n")
        }

        dtsSb.append("""
            export class ${moduleData.facadeClassName} {
              constructor(resolver?: LocalizedStringResolver, locale?: string);

        """.trimIndent()).append("\n")
        moduleData.bundles.forEach { bundleData ->
            val fieldName = bundleData.bundleName.split('_').joinToString("") { part -> part.replaceFirstChar { char -> char.uppercase() } }.decapitalizeFirstLetter()
            dtsSb.append("  readonly $fieldName: ${bundleData.className};\n")
        }
        dtsSb.append("}\n\n")
    }

    dtsSb.append("""
        export class SharedFoundationLocalization {
          constructor(resolver?: LocalizedStringResolver, locale?: string);

    """.trimIndent()).append("\n")
    modulesList.forEach { moduleData ->
        val fieldName = moduleData.modulePrefix.decapitalizeFirstLetter()
        dtsSb.append("  readonly $fieldName: ${moduleData.facadeClassName};\n")
    }
    dtsSb.append("}\n\n")

    dtsSb.append("""
        export declare const SharedLocalization: {
          setLocale(locale: string): void;
          getLocale(): string;
          setResolver(resolver: LocalizedStringResolver): void;
          getResolver(): LocalizedStringResolver;
        };

        export declare const sharedLocalization: SharedFoundationLocalization;

    """.trimIndent()).append("\n")

    modulesList.forEach { moduleData ->
        val singletonName = "${moduleData.modulePrefix.decapitalizeFirstLetter()}Strings"
        dtsSb.append("export declare const $singletonName: ${moduleData.facadeClassName};\n")
    }

    return Pair(jsSb.toString(), dtsSb.toString())
}

fun parseFlatJson(content: String): Map<String, String> {
    val result = mutableMapOf<String, String>()
    val lineRegex = Regex(""""([^"]+)"\s*:\s*"((?:\\.|[^"\\])*)"""")
    lineRegex.findAll(content).forEach { match ->
        val key = match.groupValues[1]
        val value = match.groupValues[2].replace("\\\"", "\"").replace("\\\\", "\\")
        result[key] = value
    }
    return result
}

fun toCamelCase(snakeCase: String): String {
    val parts = snakeCase.split('_')
    return parts.first() + parts.drop(1).joinToString("") { part ->
        part.replaceFirstChar { char -> char.uppercase() }
    }
}

fun formatAsJsObject(obj: Any?): String {
    return when (obj) {
        is Map<*, *> -> {
            val entries = obj.entries.joinToString(",\n") { (k, v) ->
                val keyStr = if (k.toString().matches(Regex("^[a-zA-Z_$][a-zA-Z0-9_$]*$"))) k.toString() else "\"$k\""
                "$keyStr: ${formatAsJsObject(v)}"
            }
            "{\n${entries.indent(2)}\n}"
        }
        is String -> "\"${obj.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n")}\""
        else -> obj.toString()
    }
}

fun String.indent(spaces: Int): String {
    val prefix = " ".repeat(spaces)
    return this.lines().joinToString("\n") { "$prefix$it" }
}
