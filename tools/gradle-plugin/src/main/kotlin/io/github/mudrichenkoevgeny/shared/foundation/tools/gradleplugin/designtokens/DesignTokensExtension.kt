package io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.designtokens

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property

/**
 * Configuration extension for the Shared Foundation Design Tokens Gradle plugin.
 */
public abstract class DesignTokensExtension {
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
}
