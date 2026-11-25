package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.crypt

/**
 * Represents a string that has been encrypted.
 *
 * @property value Encrypted string (usually Base64 encoded); must not be blank.
 */
@JvmInline
public value class EncryptedString(
    public val value: String
) {
    init {
        require(value.isNotBlank()) { "EncryptedString value must not be blank." }
    }

    override fun toString(): String = value
}

/**
 * Attempts to create an [EncryptedString] from this string.
 * Returns `null` if the string is blank.
 */
public fun String.toEncryptedStringOrNull(): EncryptedString? = if (this.isNotBlank()) EncryptedString(this) else null

/**
 * Creates an [EncryptedString] from this string or throws an exception if the string is blank.
 */
public fun String.toEncryptedStringOrThrow(): EncryptedString = EncryptedString(this)
