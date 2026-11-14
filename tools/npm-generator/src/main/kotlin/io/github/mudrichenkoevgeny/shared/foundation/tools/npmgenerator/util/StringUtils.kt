package io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.util

/**
 * Decapitalizes the first letter of a string.
 */
public fun String.decapitalizeFirstLetter(): String =
    if (isNotEmpty() && this[0].isUpperCase()) this[0].lowercaseChar() + substring(1) else this
