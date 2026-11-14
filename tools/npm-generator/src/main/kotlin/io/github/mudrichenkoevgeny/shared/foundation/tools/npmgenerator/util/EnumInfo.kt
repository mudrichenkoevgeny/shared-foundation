package io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.util

/**
 * Metadata representation for a generated TypeScript enum or const object schema.
 */
public data class EnumInfo(
    public val name: String,
    public val schemaName: String,
    public val entries: List<Pair<String, String>>
)
