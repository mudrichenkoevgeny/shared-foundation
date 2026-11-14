package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token

/**
 * Opaque JWT or bearer token string issued for API access.
 *
 * @property value Raw token string; must not be blank.
 */
@JvmInline
public value class AccessToken(
    public val value: String
) {
    init {
        require(value.isNotBlank()) { "AccessToken value must not be blank." }
    }

    override fun toString(): String = value
}

/**
 * Attempts to create an [AccessToken] from this string.
 * Returns `null` if the string is blank.
 */
public fun String.toAccessTokenOrNull(): AccessToken? = if (this.isNotBlank()) AccessToken(this) else null

/**
 * Creates an [AccessToken] from this string or throws an exception if the string is blank.
 */
public fun String.toAccessTokenOrThrow(): AccessToken = AccessToken(this)
