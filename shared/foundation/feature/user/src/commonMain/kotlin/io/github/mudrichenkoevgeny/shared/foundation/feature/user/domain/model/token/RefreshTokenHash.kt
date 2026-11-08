package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token

import kotlin.jvm.JvmInline

/**
 * Hash of a RefreshToken for secure storage and comparison.
 *
 * @property value Hash value; must not be blank.
 */
@JvmInline
public value class RefreshTokenHash(
    public val value: String
) {
    init {
        require(value.isNotBlank()) { "RefreshTokenHash value must not be blank." }
    }

    override fun toString(): String = value
}

/**
 * Attempts to create a [RefreshTokenHash] from this string.
 * Returns `null` if the string is blank.
 */
public fun String.toRefreshTokenHashOrNull(): RefreshTokenHash? =
    if (this.isNotBlank()) RefreshTokenHash(this) else null

/**
 * Creates a [RefreshTokenHash] from this string or throws an exception if the string is blank.
 */
public fun String.toRefreshTokenHashOrThrow(): RefreshTokenHash = RefreshTokenHash(this)
