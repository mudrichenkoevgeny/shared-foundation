package io.github.mudrichenkoevgeny.shared.foundation.tools.designsystem

import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class DesignSystemGeneratorTest {

    @Test
    fun testDesignTokensParserClasspathLoadingAndResolution() {
        val parser = DesignTokensParser.loadFromClasspath()

        val primaryColor = parser.resolveTokenValueByPath("color.semantic.light.primary")
        assertEquals("#2C3137", primaryColor, "Expected resolved primary color hex")

        val spacingSmall = parser.resolveTokenValueByPath("spacing.small")
        assertEquals("8", spacingSmall, "Expected resolved spacing value")

        val fontFamily = parser.resolveTokenValueByPath("typography.fontFamily.primary")
        assertEquals("PT Sans", fontFamily, "Expected resolved font family name")
    }

    @Test
    fun testDesignTokensParserCycleDetection() {
        val rawJson = """
            {
              "color": {
                "a": { "${'$'}value": "{color.b}" },
                "b": { "${'$'}value": "{color.a}" }
              }
            }
        """.trimIndent()

        val parser = DesignTokensParser.parseRawJsonContents(listOf(rawJson))

        val exception = assertFailsWith<IllegalStateException> {
            parser.resolveTokenValueByPath("color.a")
        }
        assertEquals(
            true,
            exception.message?.contains("Circular token reference"),
            "Expected circular token reference error message"
        )
    }

    @Test
    fun testDesignTokensParserMissingResourceFailure() {
        assertFailsWith<IllegalStateException> {
            DesignTokensParser.loadFromClasspath("/invalid/resource/path")
        }
    }

    @Test
    fun testComposeTokensGenerator() {
        val parser = DesignTokensParser.loadFromClasspath()
        val generatedCode = ComposeTokensGenerator.generate(
            parser = parser,
            packageName = "com.example.custom.theme"
        )

        assertTrue(generatedCode.contains("package com.example.custom.theme"))
        assertTrue(generatedCode.contains("public object PlatformDesignTokens"))
        assertTrue(generatedCode.contains("public val primary: Color = Color(0xFF2C3137)"))
        assertTrue(generatedCode.contains("public val small: Dp = 8.dp"))
        assertTrue(generatedCode.contains("public val fontFamilyPrimary: String = \"PT Sans\""))
    }

    @Test
    fun testWebTokensGeneratorCss() {
        val parser = DesignTokensParser.loadFromClasspath()
        val css = WebTokensGenerator.generateCss(parser, "./fonts")

        assertTrue(css.contains("src: url('./fonts/pt-sans-regular.woff2') format('woff2');"))
        assertTrue(css.contains("--color-primary: 213 11% 19%;"))
        assertTrue(css.contains("--color-primary-hex: #2C3137;"))
        assertTrue(css.contains("--spacing-sm: 0.5rem;"))
    }

    @Test
    fun testWebTokensGeneratorJsDtsTs() {
        val parser = DesignTokensParser.loadFromClasspath()

        val js = WebTokensGenerator.generateJs(parser)
        assertTrue(js.contains("export const PlatformDesignTokens = {"))
        assertTrue(js.contains("primary: '#2C3137'"))

        val dts = WebTokensGenerator.generateDts(parser)
        assertTrue(dts.contains("export declare const PlatformDesignTokens: {"))
        assertTrue(dts.contains("readonly primary: '#2C3137';"))

        val ts = WebTokensGenerator.generateTs(parser)
        assertTrue(ts.contains("export const PlatformDesignTokens = {"))
        assertTrue(ts.contains("} as const"))
    }

    @Test
    fun testPenpotTokensGenerator() {
        val parser = DesignTokensParser.loadFromClasspath()

        val json = PenpotTokensGenerator.generateJson(parser)
        assertTrue(json.contains("\"\$type\": \"color\""))
        assertTrue(json.contains("\"charcoal\""))

        val ts = PenpotTokensGenerator.generateTs(parser)
        assertTrue(ts.contains("export const PenpotTokens = {"))
        assertTrue(ts.contains("} as const"))
    }

    @Test
    fun testFontExtractor() {
        val tempDir = File.createTempFile("font_extract_test", "").apply {
            delete()
            mkdirs()
        }
        try {
            DesignTokensFontExtractor.extractAllFonts(tempDir)
            val ttfFiles = File(tempDir, "ttf").listFiles() ?: emptyArray()
            val woff2Files = File(tempDir, "woff2").listFiles() ?: emptyArray()

            assertEquals(4, ttfFiles.size, "Expected 4 TTF font files extracted")
            assertEquals(4, woff2Files.size, "Expected 4 WOFF2 font files extracted")
        } finally {
            tempDir.deleteRecursively()
        }
    }

    @Test
    fun testIconParityBetweenSvgAndDrawable() {
        var rootDir = File(System.getProperty("user.dir")).canonicalFile
        while (rootDir.parentFile != null && !File(rootDir, "settings.gradle.kts").exists()) {
            rootDir = rootDir.parentFile
        }
        val iconsRootDir = File(rootDir, "tools/design-system/src/main/resources/design-system/icons")
        assertTrue(iconsRootDir.exists() && iconsRootDir.isDirectory, "Icons directory must exist")

        val expectedModules = listOf("core-common", "core-storage", "feature-user")
        expectedModules.forEach { moduleName ->
            val moduleDir = File(iconsRootDir, moduleName)
            assertTrue(moduleDir.exists() && moduleDir.isDirectory, "Module directory $moduleName must exist")

            val svgDir = File(moduleDir, "svg")
            val drawableDir = File(moduleDir, "drawable")

            assertTrue(svgDir.exists() && svgDir.isDirectory, "svg directory for $moduleName must exist")
            assertTrue(
                drawableDir.exists() && drawableDir.isDirectory,
                "drawable directory for $moduleName must exist"
            )

            val svgBaseNames = (svgDir.listFiles { _, name -> name.endsWith(".svg") } ?: emptyArray())
                .map { it.nameWithoutExtension }
                .toSet()

            val drawableBaseNames = (drawableDir.listFiles { _, name -> name.endsWith(".xml") } ?: emptyArray())
                .map { it.nameWithoutExtension }
                .toSet()

            assertTrue(svgBaseNames.isNotEmpty(), "svg directory for $moduleName must not be empty")
            assertTrue(drawableBaseNames.isNotEmpty(), "drawable directory for $moduleName must not be empty")

            assertEquals(
                svgBaseNames,
                drawableBaseNames,
                "SVG and Drawable icon sets for module $moduleName must match 1-to-1"
            )
        }
    }

    @Test
    fun testIconExtractorExtraction() {
        val tempDir = File.createTempFile("icon_extract_test", "").apply {
            delete()
            mkdirs()
        }
        try {
            val drawableTarget = File(tempDir, "drawable")
            DesignSystemIconExtractor.extractDrawableIcons(
                listOf("core-common", "core-storage"),
                drawableTarget
            )
            val extractedXmlFiles = drawableTarget.listFiles { _, name -> name.endsWith(".xml") } ?: emptyArray()
            assertTrue(extractedXmlFiles.size >= 20, "Expected at least 20 extracted XML icon drawables")

            val svgTarget = File(tempDir, "svg")
            DesignSystemIconExtractor.extractSvgIconsForWeb(svgTarget)
            val commonSvgDir = File(svgTarget, "core-common")
            val storageSvgDir = File(svgTarget, "core-storage")
            val userSvgDir = File(svgTarget, "feature-user")

            assertTrue(commonSvgDir.exists() && (commonSvgDir.listFiles()?.size ?: 0) >= 13)
            assertTrue(storageSvgDir.exists() && (storageSvgDir.listFiles()?.size ?: 0) >= 9)
            assertTrue(userSvgDir.exists() && (userSvgDir.listFiles()?.size ?: 0) >= 4)
        } finally {
            tempDir.deleteRecursively()
        }
    }

    @Test
    fun testDataDrivenExtensibility() {
        val customJson = """
            {
              "color": {
                "semantic": {
                  "light": {
                    "primary": { "${'$'}value": "#112233" },
                    "customAccent": { "${'$'}value": "#FF0055" }
                  },
                  "dark": {
                    "primary": { "${'$'}value": "#AABBCC" }
                  }
                }
              },
              "spacing": {
                "small": { "${'$'}value": 8 },
                "huge": { "${'$'}value": 64 }
              },
              "radius": {
                "small": { "${'$'}value": 8 }
              },
              "sizing": {
                "customWidget": { "${'$'}value": 120 }
              },
              "elevation": {
                "header": { "${'$'}value": 2 }
              },
              "typography": {
                "fontFamily": { "primary": { "${'$'}value": "PT Sans" } },
                "fontWeight": { "regular": { "${'$'}value": 400 }, "bold": { "${'$'}value": 700 } },
                "fontStyle": { "normal": { "${'$'}value": "normal" } }
              }
            }
        """.trimIndent()

        val parser = DesignTokensParser.parseRawJsonContents(listOf(customJson))

        val composeCode = ComposeTokensGenerator.generate(parser, "com.example.test")
        assertTrue(composeCode.contains("public val customAccent: Color = Color(0xFFFF0055)"))
        assertTrue(composeCode.contains("public val huge: Dp = 64.dp"))
        assertTrue(composeCode.contains("public val customWidget: Dp = 120.dp"))

        val webCss = WebTokensGenerator.generateCss(parser)
        assertTrue(webCss.contains("--color-custom-accent: 340 100% 50%;"))
        assertTrue(webCss.contains("--spacing-huge: 4rem;"))
        assertTrue(webCss.contains("--dimen-custom-widget: 7.5rem;"))

        val webJs = WebTokensGenerator.generateJs(parser)
        assertTrue(webJs.contains("customAccent: '#FF0055'"), "webJs did not contain customAccent: $webJs")
        assertTrue(webJs.contains("huge: 64"), "webJs did not contain huge: $webJs")
        assertTrue(webJs.contains("customWidget: 120"), "webJs did not contain customWidget: $webJs")

        val webDts = WebTokensGenerator.generateDts(parser)
        assertTrue(webDts.contains("readonly customAccent: '#FF0055';"), "webDts did not contain customAccent: $webDts")
        assertTrue(webDts.contains("readonly huge: 64;"), "webDts did not contain huge: $webDts")
        assertTrue(webDts.contains("readonly customWidget: 120;"), "webDts did not contain customWidget: $webDts")
    }
}
