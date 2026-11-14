package io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.localization

/**
 * Entry representation of a single localization key and its parameters.
 */
public data class LocalizationKeyEntry(
    public val rawKey: String,
    public val value: String,
    public val cleanKey: String,
    public val propertyOrMethodName: String,
    public val placeholders: List<String>,
    public val isMethod: Boolean
)
