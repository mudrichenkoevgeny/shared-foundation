package io.github.mudrichenkoevgeny.shared.foundation.tools.designsystem

import kotlinx.serialization.json.JsonObject

/**
 * Generator for Jetpack Compose Kotlin design token declarations.
 */
public object ComposeTokensGenerator {

    /**
     * Generates `PlatformDesignTokens.kt` content for Jetpack Compose.
     *
     * @param parser Design tokens parser containing loaded definitions.
     * @param packageName Target package name for the generated Kotlin class.
     * @return Formatted Kotlin source code string.
     */
    public fun generate(
        parser: DesignTokensParser,
        packageName: String = "io.github.mudrichenkoevgeny.kmp.core.common.ui.theme.tokens"
    ): String {
        val colorsBlock = generateColorsBlock(parser)
        val spacingBlock = generateDpGroupBlock(parser, "spacing", "Spacing")
        val radiusBlock = generateDpGroupBlock(parser, "radius", "Radius")
        val sizingBlock = generateDpGroupBlock(parser, "sizing", "Sizing")
        val elevationBlock = generateDpGroupBlock(parser, "elevation", "Elevation")
        val typographyBlock = generateTypographyBlock(parser)

        return """package $packageName

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

public object PlatformDesignTokens {
$colorsBlock

$spacingBlock

$radiusBlock

$sizingBlock

$elevationBlock

$typographyBlock
}"""
    }

    private fun generateColorsBlock(parser: DesignTokensParser): String {
        val lightProperties = generateColorProperties(parser, "color.semantic.light")
        val darkProperties = generateColorProperties(parser, "color.semantic.dark")

        return """    public object Colors {
        public object Light {
$lightProperties
        }

        public object Dark {
$darkProperties
        }
    }"""
    }

    private fun generateColorProperties(
        parser: DesignTokensParser,
        basePath: String
    ): String {
        val node = parser.lookupToken(basePath) as? JsonObject
            ?: error("Missing required color token object at $basePath")

        return node.keys.sorted().joinToString("\n") { key ->
            val path = "$basePath.$key"
            val rawHex = parser.resolveTokenValueByPath(path)
                ?: error("Missing required design token: $path")
            val cleanHex = rawHex.replace("#", "").uppercase()
            check(cleanHex.length == 6) { "Invalid hex color format at $path: $rawHex" }
            "            public val $key: Color = Color(0xFF$cleanHex)"
        }
    }

    private fun generateDpGroupBlock(
        parser: DesignTokensParser,
        basePath: String,
        objectName: String
    ): String {
        val node = parser.lookupToken(basePath) as? JsonObject
            ?: return "    public object $objectName {\n    }"

        val properties = node.keys.sorted().joinToString("\n") { key ->
            val path = "$basePath.$key"
            val valStr = parser.resolveTokenValueByPath(path)
                ?: error("Missing required design token: $path")
            "        public val $key: Dp = $valStr.dp"
        }

        return """    public object $objectName {
$properties
    }"""
    }

    private fun generateTypographyBlock(parser: DesignTokensParser): String {
        val node = parser.lookupToken("typography") as? JsonObject
            ?: return "    public object Typography {\n    }"

        val propertyLines = mutableListOf<String>()

        node.keys.sorted().forEach { subCategory ->
            val subNode = node[subCategory] as? JsonObject ?: return@forEach
            subNode.keys.sorted().forEach { key ->
                val path = "typography.$subCategory.$key"
                val valueStr = parser.resolveTokenValueByPath(path)
                    ?: error("Missing required design token: $path")

                val propertyName = subCategory + key.replaceFirstChar { it.uppercase() }
                val numValue = valueStr.toIntOrNull()

                val line = if (numValue != null) {
                    "        public val $propertyName: Int = $numValue"
                } else {
                    "        public val $propertyName: String = \"$valueStr\""
                }
                propertyLines.add(line)
            }
        }

        val properties = propertyLines.joinToString("\n")

        return """    public object Typography {
$properties
    }"""
    }
}
