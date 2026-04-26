package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.crypt

import kotlin.jvm.JvmInline

/**
 * Represents a decrypted or plain-text string intended for secure processing.
 *
 * @property value Raw string value; must not be blank.
 */
@JvmInline
value class DecryptedString(val value: String) {
    init {
        require(value.isNotBlank()) { "DecryptedString value must not be blank." }
    }

    override fun toString(): String = value
}

/**
 * Attempts to create a [DecryptedString] from this string.
 * Returns `null` if the string is blank.
 */
fun String.toDecryptedStringOrNull(): DecryptedString? =
    if (this.isNotBlank()) DecryptedString(this) else null

/**
 * Creates a [DecryptedString] from this string or throws an exception if the string is blank.
 */
fun String.toDecryptedStringOrThrow(): DecryptedString = DecryptedString(this)