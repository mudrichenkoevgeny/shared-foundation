package io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.designtokens

import io.github.mudrichenkoevgeny.shared.foundation.tools.designtokens.DesignTokensFontExtractor
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

/**
 * Gradle task extracting embedded design TTF font assets to Compose Multiplatform resources directory.
 */
@CacheableTask
public abstract class ExtractDesignFontsTask : DefaultTask() {

    /**
     * Output directory for extracted TTF font files.
     */
    @get:OutputDirectory
    public abstract val fontsOutputDir: DirectoryProperty

    /**
     * Executes the font extraction task.
     */
    @TaskAction
    public fun extract() {
        val targetDir = fontsOutputDir.get().asFile
        targetDir.mkdirs()
        DesignTokensFontExtractor.extractTtfFonts(targetDir)
    }
}
