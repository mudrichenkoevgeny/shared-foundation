package io.github.mudrichenkoevgeny.shared.foundation.tools.designtokens

import java.io.File

/**
 * Utility for extracting embedded font resources to disk.
 */
public object DesignTokensFontExtractor {

    private const val INDEX_TTF_PATH: String = "/design-tokens/fonts-ttf-index.txt"
    private const val INDEX_WOFF2_PATH: String = "/design-tokens/fonts-woff2-index.txt"

    /**
     * Lists available TTF font filenames from resource index.
     *
     * @return List of TTF filenames.
     */
    public fun listTtfFonts(): List<String> = loadIndex(INDEX_TTF_PATH)

    /**
     * Lists available WOFF2 font filenames from resource index.
     *
     * @return List of WOFF2 filenames.
     */
    public fun listWoff2Fonts(): List<String> = loadIndex(INDEX_WOFF2_PATH)

    /**
     * Extracts all TTF fonts from classpath resources to the specified target directory.
     *
     * @param targetDir Destination directory.
     */
    public fun extractTtfFonts(targetDir: File) {
        targetDir.mkdirs()
        listTtfFonts().forEach { fontName ->
            val resourcePath = "/design-tokens/fonts/ttf/$fontName"
            extractResource(resourcePath, File(targetDir, fontName))
        }
    }

    /**
     * Extracts all WOFF2 fonts from classpath resources to the specified target directory.
     *
     * @param targetDir Destination directory.
     */
    public fun extractWoff2Fonts(targetDir: File) {
        targetDir.mkdirs()
        listWoff2Fonts().forEach { fontName ->
            val resourcePath = "/design-tokens/fonts/woff2/$fontName"
            extractResource(resourcePath, File(targetDir, fontName))
        }
    }

    /**
     * Extracts all TTF and WOFF2 fonts to the specified target directory.
     *
     * @param targetDir Destination directory.
     */
    public fun extractAllFonts(targetDir: File) {
        val ttfDir = File(targetDir, "ttf")
        val woff2Dir = File(targetDir, "woff2")
        extractTtfFonts(ttfDir)
        extractWoff2Fonts(woff2Dir)
    }

    private fun loadIndex(indexPath: String): List<String> {
        val stream = DesignTokensFontExtractor::class.java.getResourceAsStream(indexPath)
            ?: error("Font index resource not found at $indexPath")
        return stream.bufferedReader().useLines { lines ->
            lines.map { it.trim() }.filter { it.isNotBlank() }.toList()
        }
    }

    private fun extractResource(resourcePath: String, targetFile: File) {
        val stream = DesignTokensFontExtractor::class.java.getResourceAsStream(resourcePath)
            ?: error("Font resource not found at $resourcePath")

        stream.use { input ->
            targetFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
    }
}
