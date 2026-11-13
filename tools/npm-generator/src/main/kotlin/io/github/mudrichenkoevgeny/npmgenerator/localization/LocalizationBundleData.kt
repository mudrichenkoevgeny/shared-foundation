package io.github.mudrichenkoevgeny.npmgenerator.localization

/**
 * Bundle data grouping localization entries for a specific module bundle.
 */
public data class LocalizationBundleData(
    public val modulePath: String,
    public val modulePrefix: String,
    public val bundleName: String,
    public val className: String,
    public val entries: List<LocalizationKeyEntry>
)
