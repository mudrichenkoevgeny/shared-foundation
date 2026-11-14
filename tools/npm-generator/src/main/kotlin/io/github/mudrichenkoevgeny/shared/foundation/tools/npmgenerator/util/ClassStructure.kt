package io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.util

/**
 * Structural breakdown of top-level properties and nested objects within a Kotlin object/class.
 */
public data class ClassStructure(
    public val directProps: List<Pair<String, String>>,
    public val nestedObjects: List<Pair<String, List<Pair<String, String>>>>
)
