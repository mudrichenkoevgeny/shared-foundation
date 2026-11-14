package io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.localization

/**
 * Module localization metadata aggregating bundles and facade class information.
 */
public data class ModuleLocalizationData(
    public val modulePath: String,
    public val modulePrefix: String,
    public val facadeClassName: String,
    public val bundles: List<LocalizationBundleData>
)
