package io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.designsystem

import io.github.mudrichenkoevgeny.shared.foundation.tools.designsystem.ComposeTokensGenerator
import io.github.mudrichenkoevgeny.shared.foundation.tools.designsystem.DesignTokensParser
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * Gradle task generating type-safe Kotlin Compose design tokens class from embedded JSON resources.
 */
@CacheableTask
public abstract class GenerateDesignTokensTask : DefaultTask() {

    /**
     * Target package name for generated class.
     */
    @get:Input
    public abstract val packageName: Property<String>

    /**
     * Output directory for generated Kotlin source files.
     */
    @get:OutputDirectory
    public abstract val outputDir: DirectoryProperty

    /**
     * Executes the Compose design tokens code generation.
     */
    @TaskAction
    public fun generate() {
        val outDir = outputDir.get().asFile
        outDir.deleteRecursively()
        outDir.mkdirs()

        val parser = DesignTokensParser.loadFromClasspath()
        val generatedCode = ComposeTokensGenerator.generate(
            parser = parser,
            packageName = packageName.get()
        )

        val pkgPath = packageName.get().replace('.', File.separatorChar)
        val targetPackageFolder = File(outDir, pkgPath)
        targetPackageFolder.mkdirs()

        File(targetPackageFolder, "PlatformDesignTokens.kt").writeText(generatedCode)
    }
}
