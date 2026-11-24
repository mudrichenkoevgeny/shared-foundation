package io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.designsystem

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property

/**
 * Configuration extension for the Shared Foundation Design System Gradle plugin.
 */
public abstract class DesignSystemExtension {
    /**
     * Package name for generated Compose design tokens class.
     * Defaults to `"io.github.mudrichenkoevgeny.kmp.core.common.ui.theme.tokens"`.
     */
    public abstract val packageName: Property<String>

    /**
     * Output directory for generated Kotlin source files.
     * Defaults to `build/generated/sources/designTokens/kotlin/commonMain`.
     */
    public abstract val outputDir: DirectoryProperty

    /**
     * Output directory for extracted design font assets.
     * Defaults to `src/commonMain/composeResources/font`.
     */
    public abstract val fontsOutputDir: DirectoryProperty

    /**
     * Flag indicating whether to generate Compose Design Tokens Kotlin class.
     * Defaults to `true`.
     */
    public abstract val generateTokens: Property<Boolean>

    /**
     * Flag indicating whether to extract embedded design TTF fonts to Compose resources.
     * Defaults to `true`.
     */
    public abstract val extractFonts: Property<Boolean>

    /**
     * List of module names whose Vector Drawable XML icons should be extracted.
     * Defaults to empty list.
     */
    public abstract val iconModules: ListProperty<String>

    /**
     * Output directory for extracted Vector Drawable XML icon assets.
     * Defaults to `src/commonMain/composeResources/drawable`.
     */
    public abstract val iconsOutputDir: DirectoryProperty
}
