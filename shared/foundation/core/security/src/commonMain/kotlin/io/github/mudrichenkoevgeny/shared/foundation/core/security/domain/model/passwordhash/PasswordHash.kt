package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordhash

import kotlin.jvm.JvmInline

/**
 * Securely hashed password string.
 *
 * @property value Raw hash value; must not be blank.
 */
@JvmInline
value class PasswordHash(val value: String) {
    init {
        require(value.isNotBlank()) { "PasswordHash value must not be blank." }
    }

    override fun toString(): String = value
}

/**
 * Attempts to create a [PasswordHash] from this string.
 * Returns `null` if the string is blank.
 */
fun String.toPasswordHashOrNull(): PasswordHash? =
    if (this.isNotBlank()) PasswordHash(this) else null

/**
 * Creates a [PasswordHash] from this string or throws an exception if the string is blank.
 */
fun String.toPasswordHashOrThrow(): PasswordHash = PasswordHash(this)