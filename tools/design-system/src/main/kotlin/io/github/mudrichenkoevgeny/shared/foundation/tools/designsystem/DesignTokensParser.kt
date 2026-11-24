package io.github.mudrichenkoevgeny.shared.foundation.tools.designsystem

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import java.io.File

/**
 * Parser and resolver for design tokens JSON specifications.
 */
public class DesignTokensParser(
    public val rootTokens: JsonObject
) {

    /**
     * Resolves a token path to its ultimate string representation.
     *
     * @param path Dot-separated token path (e.g., "color.semantic.light.primary").
     * @return Resolved primitive value string, or null if token was not found.
     */
    public fun resolveTokenValueByPath(path: String): String? {
        val node = lookupToken(path) ?: return null
        return resolveValue(node, path)
    }

    /**
     * Look up a JSON element by dot-separated path.
     *
     * @param path Dot-separated path.
     * @return Matching [JsonElement] or null.
     */
    public fun lookupToken(path: String): JsonElement? {
        val parts = path.split('.')
        var current: JsonElement = rootTokens
        for (part in parts) {
            if (current !is JsonObject) return null
            current = current[part] ?: return null
        }
        return current
    }

    /**
     * Resolves a token node to its scalar value string with cycle detection.
     *
     * @param element The token node or primitive.
     * @param currentPath Context path or initial reference for cycle tracking.
     * @param visitedPaths Set of reference paths visited during resolution.
     * @return Resolved string value.
     */
    public fun resolveValue(
        element: JsonElement,
        currentPath: String = "",
        visitedPaths: Set<String> = emptySet()
    ): String {
        return when (element) {
            is JsonObject -> {
                val valueElem = element["\$value"] ?: element
                if (valueElem is JsonObject || valueElem is JsonArray) {
                    resolveValue(valueElem, currentPath, visitedPaths)
                } else if (valueElem is JsonPrimitive) {
                    resolvePrimitiveString(valueElem.content, currentPath, visitedPaths)
                } else {
                    element.toString()
                }
            }
            is JsonPrimitive -> {
                resolvePrimitiveString(element.content, currentPath, visitedPaths)
            }
            else -> element.toString()
        }
    }

    private fun resolvePrimitiveString(
        rawContent: String,
        currentPath: String,
        visitedPaths: Set<String>
    ): String {
        if (rawContent.startsWith('{') && rawContent.endsWith('}')) {
            val refPath = rawContent.substring(1, rawContent.length - 1)
            check(refPath !in visitedPaths) {
                "Circular token reference detected at '$refPath'"
            }
            val targetNode = lookupToken(refPath)
                ?: error("Missing referenced design token path: '$refPath'")
            val nextVisited = if (currentPath.isNotBlank()) {
                visitedPaths + currentPath + refPath
            } else {
                visitedPaths + refPath
            }
            return resolveValue(targetNode, refPath, nextVisited)
        }
        return rawContent
    }

    public companion object {
        private val jsonParser: Json = Json { ignoreUnknownKeys = true }

        /**
         * Loads tokens from classpath resource folder using the generated tokens index.
         *
         * @param baseResourceDir Base classpath directory containing `tokens-index.txt` and `tokens/`.
         * @return Initialized [DesignTokensParser].
         */
        public fun loadFromClasspath(baseResourceDir: String = "/design-system"): DesignTokensParser {
            val cleanBase = baseResourceDir.trimEnd('/')
            val indexPath = "$cleanBase/tokens-index.txt"
            val indexStream = DesignTokensParser::class.java.getResourceAsStream(indexPath)
                ?: error("Required design tokens index resource not found at $indexPath")

            val tokenFileNames = indexStream.bufferedReader().useLines { lines ->
                lines.map { it.trim() }.filter { it.isNotBlank() }.toList()
            }
            check(tokenFileNames.isNotEmpty()) { "Tokens index at $indexPath is empty" }

            val jsonContents = tokenFileNames.map { fileName ->
                val fullPath = "$cleanBase/tokens/$fileName"
                val stream = DesignTokensParser::class.java.getResourceAsStream(fullPath)
                    ?: error("Required design token resource not found at $fullPath")
                stream.use { it.bufferedReader().readText() }
            }
            return parseRawJsonContents(jsonContents)
        }

        /**
         * Loads tokens from a directory on disk.
         *
         * @param directory Directory containing token JSON files.
         * @return Initialized [DesignTokensParser].
         */
        public fun loadFromDirectory(directory: File): DesignTokensParser {
            check(directory.exists() && directory.isDirectory) {
                "Token directory does not exist or is not a directory: ${directory.absolutePath}"
            }
            val jsonFiles = directory.listFiles { file -> file.extension == "json" }?.sortedBy { it.name }
                ?: emptyList()
            check(jsonFiles.isNotEmpty()) {
                "No token JSON files found in directory: ${directory.absolutePath}"
            }
            val jsonContents = jsonFiles.map { it.readText() }
            return parseRawJsonContents(jsonContents)
        }

        /**
         * Parses and deep merges raw JSON string contents.
         *
         * @param contents List of JSON content strings.
         * @return Initialized [DesignTokensParser].
         */
        public fun parseRawJsonContents(contents: List<String>): DesignTokensParser {
            var combined = JsonObject(emptyMap())
            contents.forEach { content ->
                val parsed = jsonParser.parseToJsonElement(content).jsonObject
                val cleaned = removeSchemaKey(parsed)
                combined = mergeDeep(combined, cleaned)
            }
            return DesignTokensParser(combined)
        }

        private fun removeSchemaKey(obj: JsonObject): JsonObject {
            val map = obj.toMutableMap()
            map.remove("\$schema")
            return JsonObject(map)
        }

        private fun mergeDeep(target: JsonObject, source: JsonObject): JsonObject {
            val resultMap = target.toMutableMap()
            for ((key, sourceValue) in source) {
                val targetValue = resultMap[key]
                if (targetValue is JsonObject && sourceValue is JsonObject) {
                    resultMap[key] = mergeDeep(targetValue, sourceValue)
                } else {
                    resultMap[key] = sourceValue
                }
            }
            return JsonObject(resultMap)
        }
    }
}
