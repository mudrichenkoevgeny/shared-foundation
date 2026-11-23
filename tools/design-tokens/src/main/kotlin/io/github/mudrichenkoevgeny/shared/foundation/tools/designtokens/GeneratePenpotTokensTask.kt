package io.github.mudrichenkoevgeny.shared.foundation.tools.designtokens

import java.io.File

/**
 * Main entry point for generating Penpot tokens via Gradle execution.
 */
public fun main() {
    val outDirPath = System.getProperty("outputDir") ?: "build/penpot"
    val outDir = File(outDirPath)
    outDir.mkdirs()

    val parser = DesignTokensParser.loadFromClasspath()

    val jsonContent = PenpotTokensGenerator.generateJson(parser)
    File(outDir, "tokens.json").writeText(jsonContent)

    val tsContent = PenpotTokensGenerator.generateTs(parser)
    File(outDir, "tokens.ts").writeText(tsContent)
}
