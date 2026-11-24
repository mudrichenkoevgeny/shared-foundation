package io.github.mudrichenkoevgeny.shared.foundation.tools.designsystem

import kotlinx.serialization.json.JsonObject
import kotlin.math.roundToInt

/**
 * Generator for Web CSS, JavaScript, and TypeScript design token declarations.
 */
public object WebTokensGenerator {

    /**
     * Generates `tokens.css` content.
     *
     * @param parser Design tokens parser containing loaded definitions.
     * @param fontsBasePath Relative base URL path for woff2 font references. Defaults to `"./fonts"`.
     * @return Formatted CSS string.
     */
    public fun generateCss(
        parser: DesignTokensParser,
        fontsBasePath: String = "./fonts"
    ): String {
        val fontFaces = generateFontFaceCss(parser, fontsBasePath)
        val rootVars = generateRootVarsCss(parser)
        val darkVars = generateDarkVarsCss(parser)

        return "$fontFaces\n\n$rootVars\n\n$darkVars\n"
    }

    /**
     * Generates `tokens.ts` content.
     *
     * @param parser Design tokens parser containing loaded definitions.
     * @return Formatted TypeScript source code string.
     */
    public fun generateTs(parser: DesignTokensParser): String {
        val tree = buildResolvedTokenTree(parser)
        val jsObj = renderJsObject(tree, 1)
        return "export const PlatformDesignTokens = {\n$jsObj\n} as const\n"
    }

    /**
     * Generates `tokens.js` ES module content.
     *
     * @param parser Design tokens parser containing loaded definitions.
     * @return Formatted JavaScript source code string.
     */
    public fun generateJs(parser: DesignTokensParser): String {
        val tree = buildResolvedTokenTree(parser)
        val jsObj = renderJsObject(tree, 1)
        return "export const PlatformDesignTokens = {\n$jsObj\n};\n"
    }

    /**
     * Generates `tokens.d.ts` TypeScript declaration content.
     *
     * @param parser Design tokens parser containing loaded definitions.
     * @return Formatted TypeScript declaration string.
     */
    public fun generateDts(parser: DesignTokensParser): String {
        val tree = buildResolvedTokenTree(parser)
        val dtsType = renderDtsType(tree, 1)
        return "export declare const PlatformDesignTokens: {\n$dtsType\n};\n"
    }

    private fun generateFontFaceCss(parser: DesignTokensParser, fontsBasePath: String): String {
        val fontFamily = parser.resolveTokenValueByPath("typography.fontFamily.primary") ?: "sans-serif"
        val woff2Files = DesignTokensFontExtractor.listWoff2Fonts()
        val cleanBase = fontsBasePath.trimEnd('/')

        return woff2Files.joinToString("\n\n") { fontFileName ->
            val fontStyle = if (fontFileName.contains("italic")) "italic" else "normal"
            val fontWeight = if (fontFileName.contains("bold")) "700" else "400"
            """@font-face {
  font-family: '$fontFamily';
  font-style: $fontStyle;
  font-weight: $fontWeight;
  font-display: swap;
  src: url('$cleanBase/$fontFileName') format('woff2');
}"""
        }
    }

    private fun generateRootVarsCss(parser: DesignTokensParser): String {
        val colorVars = generateCssColorVars(parser, "color.semantic.light")
        val spacingVars = generateCssGroupVars(parser, "spacing", "dpToRem")
        val radiusVars = generateCssGroupVars(parser, "radius", "dpToRem")
        val sizingVars = generateCssGroupVars(parser, "sizing", "dpToRem")
        val elevationVars = generateCssGroupVars(parser, "elevation", "dpToPx")
        val typographyVars = generateCssTypographyVars(parser)

        return """:root {
$colorVars

  --background: var(--color-background);
  --foreground: var(--color-on-background);
  --primary: var(--color-primary);
  --primary-foreground: var(--color-on-primary);
  --secondary: var(--color-primary-container);
  --secondary-foreground: var(--color-on-primary-container);
  --card: 0 0% 100%;
  --card-foreground: var(--color-on-background);
  --popover: 0 0% 100%;
  --popover-foreground: var(--color-on-background);
  --muted: 0 0% 94%;
  --muted-foreground: 215 16% 47%;
  --accent: var(--color-primary-container);
  --accent-foreground: var(--color-on-primary-container);
  --destructive: var(--color-error);
  --destructive-foreground: 0 0% 100%;
  --border: 214 15% 82%;
  --input: 214 15% 82%;
  --ring: var(--color-primary);

$spacingVars

$radiusVars
  --radius: var(--radius-sm);

$sizingVars

$elevationVars

$typographyVars
}"""
    }

    private fun generateDarkVarsCss(parser: DesignTokensParser): String {
        val darkColorVars = generateCssColorVars(parser, "color.semantic.dark")

        return """.dark {
$darkColorVars

  --background: var(--color-background);
  --foreground: var(--color-on-background);
  --primary: var(--color-primary);
  --primary-foreground: var(--color-on-primary);
  --secondary: var(--color-primary-container);
  --secondary-foreground: var(--color-on-primary-container);
  --card: 120 5% 10%;
  --card-foreground: var(--color-on-background);
  --popover: 120 5% 10%;
  --popover-foreground: var(--color-on-background);
  --muted: 120 5% 15%;
  --muted-foreground: 215 20% 65%;
  --accent: var(--color-primary-container);
  --accent-foreground: var(--color-on-primary-container);
  --destructive: var(--color-error);
  --destructive-foreground: 0 0% 100%;
  --border: 213 11% 25%;
  --input: 213 11% 25%;
  --ring: var(--color-primary);
}"""
    }

    private fun generateCssColorVars(parser: DesignTokensParser, basePath: String): String {
        val node = parser.lookupToken(basePath) as? JsonObject
            ?: error("Missing color object at $basePath")

        return node.keys.sorted().joinToString("\n") { key ->
            val path = "$basePath.$key"
            val hex = resolveValue(parser, path)
            val kebabKey = camelToKebab(key)
            "  --color-$kebabKey: ${hexToHsl(hex)};\n  --color-$kebabKey-hex: $hex;"
        }
    }

    private fun generateCssGroupVars(
        parser: DesignTokensParser,
        group: String,
        unitMode: String
    ): String {
        val node = parser.lookupToken(group) as? JsonObject
            ?: return ""

        return node.keys.sorted().joinToString("\n") { key ->
            val path = "$group.$key"
            val rawVal = resolveValue(parser, path)
            val varName = resolveCssVarName(group, key)
            val formattedVal = if (unitMode == "dpToRem") dpToRem(rawVal) else dpToPx(rawVal)
            "  $varName: $formattedVal;"
        }
    }

    private fun generateCssTypographyVars(parser: DesignTokensParser): String {
        val fontFamilyPrimary = parser.resolveTokenValueByPath("typography.fontFamily.primary") ?: "sans-serif"
        val fontWeightRegular = parser.resolveTokenValueByPath("typography.fontWeight.regular") ?: "400"
        val fontWeightBold = parser.resolveTokenValueByPath("typography.fontWeight.bold") ?: "700"

        return """  --font-family-primary: '$fontFamilyPrimary', sans-serif;
  --font-weight-regular: $fontWeightRegular;
  --font-weight-bold: $fontWeightBold;"""
    }

    private fun buildResolvedTokenTree(parser: DesignTokensParser): Map<String, Any> {
        val colorsLight = resolveTokenMap(parser, "color.semantic.light")
        val colorsDark = resolveTokenMap(parser, "color.semantic.dark")
        val spacing = resolveTokenMapTyped(parser, "spacing")
        val radius = resolveTokenMapTyped(parser, "radius")
        val sizing = resolveTokenMapTyped(parser, "sizing")
        val elevation = resolveTokenMapTyped(parser, "elevation")
        val typography = resolveNestedTokenMapTyped(parser, "typography")

        return mapOf(
            "colors" to mapOf("light" to colorsLight, "dark" to colorsDark),
            "spacing" to spacing,
            "radius" to radius,
            "sizing" to sizing,
            "elevation" to elevation,
            "typography" to typography
        )
    }

    private fun resolveTokenMap(parser: DesignTokensParser, path: String): Map<String, String> {
        val node = parser.lookupToken(path) as? JsonObject ?: return emptyMap()
        return node.keys.sorted().associateWith { key ->
            resolveValue(parser, "$path.$key")
        }
    }

    private fun resolveTokenMapTyped(parser: DesignTokensParser, path: String): Map<String, Any> {
        val node = parser.lookupToken(path) as? JsonObject ?: return emptyMap()
        return node.keys.sorted().associateWith { key ->
            val valStr = resolveValue(parser, "$path.$key")
            valStr.toIntOrNull() ?: valStr.toDoubleOrNull() ?: valStr
        }
    }

    private fun resolveNestedTokenMapTyped(parser: DesignTokensParser, path: String): Map<String, Map<String, Any>> {
        val node = parser.lookupToken(path) as? JsonObject ?: return emptyMap()
        return node.keys.sorted().associateWith { subKey ->
            resolveTokenMapTyped(parser, "$path.$subKey")
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun renderJsObject(map: Map<String, Any>, indentLevel: Int): String {
        val indent = "  ".repeat(indentLevel)
        val entries = map.entries.joinToString(",\n") { (k, v) ->
            val valStr = when (v) {
                is Map<*, *> -> "{\n${renderJsObject(v as Map<String, Any>, indentLevel + 1)}\n$indent}"
                is String -> "'$v'"
                else -> v.toString()
            }
            "$indent$k: $valStr"
        }
        return entries
    }

    @Suppress("UNCHECKED_CAST")
    private fun renderDtsType(map: Map<String, Any>, indentLevel: Int): String {
        val indent = "  ".repeat(indentLevel)
        val entries = map.entries.joinToString("\n") { (k, v) ->
            val typeStr = when (v) {
                is Map<*, *> -> "{\n${renderDtsType(v as Map<String, Any>, indentLevel + 1)}\n$indent};"
                is String -> "'$v';"
                else -> "$v;"
            }
            "${indent}readonly $k: $typeStr"
        }
        return entries
    }

    private fun resolveValue(parser: DesignTokensParser, path: String): String =
        parser.resolveTokenValueByPath(path)
            ?: error("Missing required design token: $path")

    private fun resolveCssVarName(group: String, key: String): String {
        val shortAlias = when (key) {
            "extraSmall" -> "xs"
            "small" -> "sm"
            "medium" -> "md"
            "large" -> "lg"
            "extraLarge" -> "xl"
            else -> null
        }
        if (shortAlias != null && (group == "spacing" || group == "radius")) {
            return "--$group-$shortAlias"
        }
        val kebabKey = camelToKebab(key)
        val groupPrefix = if (group == "sizing") "dimen" else group
        return "--$groupPrefix-$kebabKey"
    }

    private fun camelToKebab(str: String): String =
        str.replace(Regex("([a-z0-9])([A-Z])"), "$1-$2").lowercase()

    private fun hexToHsl(hex: String): String {
        var cleanHex = hex.replace("#", "")
        check(cleanHex.isNotBlank()) { "Color hex string cannot be blank" }
        if (cleanHex.length == 3) {
            cleanHex = cleanHex.map { "$it$it" }.joinToString("")
        }
        check(cleanHex.length == 6) { "Invalid hex color format: $hex" }

        val r = cleanHex.substring(0, 2).toInt(16) / 255.0
        val g = cleanHex.substring(2, 4).toInt(16) / 255.0
        val b = cleanHex.substring(4, 6).toInt(16) / 255.0

        val max = maxOf(r, maxOf(g, b))
        val min = minOf(r, minOf(g, b))
        val delta = max - min

        var h = 0.0
        var s = 0.0
        val l = (max + min) / 2.0

        if (delta != 0.0) {
            s = if (l > 0.5) delta / (2.0 - max - min) else delta / (max + min)
            if (max == r) {
                h = ((g - b) / delta) + (if (g < b) 6.0 else 0.0)
            } else if (max == g) {
                h = ((b - r) / delta) + 2.0
            } else if (max == b) {
                h = ((r - g) / delta) + 4.0
            }
            h /= 6.0
        }

        val hDeg = (h * 360.0).roundToInt()
        val sPct = (s * 100.0).roundToInt()
        val lPct = (l * 100.0).roundToInt()

        return "$hDeg $sPct% $lPct%"
    }

    private fun dpToRem(dpValStr: String): String {
        val dpVal = dpValStr.toDouble()
        val rem = dpVal / 16.0
        val formatted = if (rem % 1.0 == 0.0) rem.toInt().toString() else rem.toString()
        return "${formatted}rem"
    }

    private fun dpToPx(dpValStr: String): String {
        val dpVal = dpValStr.toDouble()
        val formatted = if (dpVal % 1.0 == 0.0) dpVal.toInt().toString() else dpVal.toString()
        return "${formatted}px"
    }
}
