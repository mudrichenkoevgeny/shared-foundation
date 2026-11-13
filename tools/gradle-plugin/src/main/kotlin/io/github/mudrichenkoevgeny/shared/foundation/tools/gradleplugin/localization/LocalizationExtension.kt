package io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.localization

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property

/**
 * Configuration extension for the Shared Foundation Localization Gradle plugin.
 */
public abstract class LocalizationExtension {
    /**
     * Base package prefix used to construct target localization package names.
     * Defaults to `"io.github.mudrichenkoevgeny.shared.foundation"`.
     */
    public abstract val basePackageName: Property<String>

    /**
     * Input resources directory containing the `localization` module hierarchy.
     * Defaults to `src/commonMain/resources`.
     */
    public abstract val resourcesDir: DirectoryProperty

    /**
     * Output directory for generated Kotlin localization source files.
     * Defaults to `build/generated/sources/localization/kotlin/commonMain`.
     */
    public abstract val outputDir: DirectoryProperty
}
