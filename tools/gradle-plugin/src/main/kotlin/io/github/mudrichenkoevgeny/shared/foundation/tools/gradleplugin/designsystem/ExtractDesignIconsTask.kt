package io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.designsystem

import io.github.mudrichenkoevgeny.shared.foundation.tools.designsystem.DesignSystemIconExtractor
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

/**
 * Gradle task extracting Vector Drawable XML icon assets for designated modules to Compose resources.
 */
@CacheableTask
public abstract class ExtractDesignIconsTask : DefaultTask() {

    /**
     * List of module names whose icon drawables should be extracted.
     */
    @get:Input
    public abstract val iconModules: ListProperty<String>

    /**
     * Output directory for extracted Vector Drawable XML files.
     */
    @get:OutputDirectory
    public abstract val iconsOutputDir: DirectoryProperty

    /**
     * Executes the icon extraction task.
     */
    @TaskAction
    public fun extract() {
        val modules = iconModules.get()
        if (modules.isEmpty()) return
        val targetDir = iconsOutputDir.get().asFile
        targetDir.mkdirs()
        DesignSystemIconExtractor.extractDrawableIcons(modules, targetDir)
    }
}
