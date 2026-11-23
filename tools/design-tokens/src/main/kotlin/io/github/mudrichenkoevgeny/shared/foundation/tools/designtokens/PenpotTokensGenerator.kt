package io.github.mudrichenkoevgeny.shared.foundation.tools.designtokens

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

/**
 * Generator for Penpot JSON and TypeScript design token artifacts.
 */
public object PenpotTokensGenerator {

    private val GROUP_TYPES: Map<String, String> = mapOf(
        "color" to "color",
        "elevation" to "dimension",
        "radius" to "dimension",
        "sizing" to "dimension",
        "spacing" to "dimension"
    )

    private val TYPOGRAPHY_SUBTYPES: Map<String, String> = mapOf(
        "fontFamily" to "fontFamily",
        "fontWeight" to "fontWeight",
        "fontStyle" to "fontStyle",
        "fontSize" to "dimension",
        "lineHeight" to "dimension"
    )

    /**
     * Generates Penpot `tokens.json` content.
     *
     * @param parser Design tokens parser containing loaded definitions.
     * @return Formatted Penpot JSON string.
     */
    public fun generateJson(parser: DesignTokensParser): String {
        val processed = processNode(parser, parser.rootTokens, emptyList())
        return formatJsonTwoSpaces(processed) + "\n"
    }

    /**
     * Generates Penpot `tokens.ts` content.
     *
     * @param parser Design tokens parser containing loaded definitions.
     * @return Formatted Penpot TypeScript source code string.
     */
    public fun generateTs(parser: DesignTokensParser): String {
        val root = parser.rootTokens
        val colorNode = root["color"]
        val spacingNode = root["spacing"]
        val radiusNode = root["radius"]
        val sizingNode = root["sizing"]
        val elevationNode = root["elevation"]
        val typographyNode = root["typography"]

        val colors = extractResolvedValues(parser, colorNode)
        val spacing = extractResolvedValues(parser, spacingNode)
        val radius = extractResolvedValues(parser, radiusNode)
        val sizing = extractResolvedValues(parser, sizingNode)
        val elevation = extractResolvedValues(parser, elevationNode)
        val typography = extractResolvedValues(parser, typographyNode)

        val dimensionsMap = JsonObject(
            mapOf(
                "spacing" to spacing,
                "radius" to radius,
                "sizing" to sizing,
                "elevation" to elevation
            )
        )

        val penpotTokensObj = JsonObject(
            mapOf(
                "colors" to colors,
                "dimensions" to dimensionsMap,
                "typography" to typography
            )
        )

        val jsonStr = formatJsonTwoSpaces(penpotTokensObj)
        return "export const PenpotTokens = $jsonStr as const\n"
    }

    private fun processNode(
        parser: DesignTokensParser,
        node: JsonElement,
        currentPath: List<String>
    ): JsonElement {
        if (node !is JsonObject) return node

        if (node.containsKey("\$value")) {
            return processValueNode(parser, node, currentPath)
        }

        return processContainerNode(parser, node, currentPath)
    }

    private fun processValueNode(
        parser: DesignTokensParser,
        node: JsonObject,
        currentPath: List<String>
    ): JsonObject {
        val resolvedValStr = parser.resolveValue(node)
        val resultMap = LinkedHashMap<String, JsonElement>()

        for ((k, v) in node) {
            if (k == "\$value") {
                val num = resolvedValStr.toDoubleOrNull()
                val elem = if (num != null && !resolvedValStr.startsWith('#')) {
                    if (num % 1.0 == 0.0) JsonPrimitive(num.toLong()) else JsonPrimitive(num)
                } else {
                    JsonPrimitive(resolvedValStr)
                }
                resultMap["\$value"] = elem
            } else {
                resultMap[k] = v
            }
        }

        if (!resultMap.containsKey("\$type") && currentPath.isNotEmpty()) {
            val groupType = GROUP_TYPES[currentPath.first()]
            if (groupType != null) {
                resultMap["\$type"] = JsonPrimitive(groupType)
            }
        }

        return JsonObject(resultMap)
    }

    private fun processContainerNode(
        parser: DesignTokensParser,
        node: JsonObject,
        currentPath: List<String>
    ): JsonObject {
        val resultMap = LinkedHashMap<String, JsonElement>()

        if (currentPath.size == 1 && GROUP_TYPES.containsKey(currentPath.first())) {
            resultMap["\$type"] = JsonPrimitive(GROUP_TYPES.getValue(currentPath.first()))
        } else if (currentPath.size == 2 && currentPath.first() == "typography") {
            val subType = TYPOGRAPHY_SUBTYPES[currentPath[1]]
            if (subType != null) {
                resultMap["\$type"] = JsonPrimitive(subType)
            }
        }

        val keys = sortedJsKeys(node.keys)
        for (key in keys) {
            val value = node[key] ?: continue
            if (key == "\$schema") continue
            resultMap[key] = processNode(parser, value, currentPath + key)
        }

        return JsonObject(resultMap)
    }

    private fun extractResolvedValues(
        parser: DesignTokensParser,
        node: JsonElement?
    ): JsonElement {
        if (node == null) return JsonObject(emptyMap())
        if (node !is JsonObject) return node

        if (node.containsKey("\$value")) {
            val resolvedValStr = parser.resolveValue(node)
            val num = resolvedValStr.toDoubleOrNull()
            return if (num != null && !resolvedValStr.startsWith('#')) {
                if (num % 1.0 == 0.0) JsonPrimitive(num.toLong()) else JsonPrimitive(num)
            } else {
                JsonPrimitive(resolvedValStr)
            }
        }

        val resultMap = LinkedHashMap<String, JsonElement>()
        val keys = sortedJsKeys(node.keys)
        for (key in keys) {
            val value = node[key] ?: continue
            if (key == "\$schema" || key.startsWith('\$')) continue
            resultMap[key] = extractResolvedValues(parser, value)
        }
        return JsonObject(resultMap)
    }

    private fun formatJsonTwoSpaces(element: JsonElement, indentLevel: Int = 0): String {
        val indent = "  ".repeat(indentLevel)
        val nextIndent = "  ".repeat(indentLevel + 1)
        return when (element) {
            is JsonObject -> {
                if (element.isEmpty()) return "{}"
                val entries = element.entries.joinToString(",\n") { (k, v) ->
                    "$nextIndent\"$k\": ${formatJsonTwoSpaces(v, indentLevel + 1)}"
                }
                "{\n$entries\n$indent}"
            }
            is JsonArray -> {
                if (element.isEmpty()) return "[]"
                val items = element.joinToString(",\n") { item ->
                    "$nextIndent${formatJsonTwoSpaces(item, indentLevel + 1)}"
                }
                "[\n$items\n$indent]"
            }
            is JsonPrimitive -> {
                if (element.isString) {
                    "\"${escapeJsonString(element.content)}\""
                } else {
                    element.content
                }
            }
        }
    }

    private fun escapeJsonString(str: String): String = buildString(str.length) {
        for (c in str) {
            when (c) {
                '\\' -> append("\\\\")
                '"' -> append("\\\"")
                '\n' -> append("\\n")
                '\r' -> append("\\r")
                '\t' -> append("\\t")
                else -> append(c)
            }
        }
    }

    private fun sortedJsKeys(keys: Set<String>): List<String> {
        val numericKeys = keys.filter { isJsIndexKey(it) }.sortedBy { it.toInt() }
        val otherKeys = keys.filterNot { isJsIndexKey(it) }
        return numericKeys + otherKeys
    }

    private fun isJsIndexKey(key: String): Boolean {
        val num = key.toIntOrNull() ?: return false
        return num >= 0 && num.toString() == key
    }
}
