package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user

import kotlin.uuid.Uuid

/**
 * Stable identifier of a user in the system.
 *
 * Wraps a [Uuid] to provide type safety at the call sites where user identifiers
 * are passed around, logged or serialized.
 */
@JvmInline
public value class UserId(
    public val value: Uuid
) {
    /**
     * Returns the underlying [Uuid] as a canonical hex string with dashes.
     */
    public fun asHexDashString(): String = value.toHexDashString()

    public companion object {
        /**
         * Generates a new random [UserId].
         */
        public fun generate(): UserId = UserId(Uuid.random())
    }
}

/**
 * Attempts to parse this string as a [UserId].
 *
 * Returns `null` if the value is not a valid UUID in hex-with-dashes form.
 */
public fun String.toUserIdOrNull(): UserId? = Uuid.parseOrNull(this)?.let { UserId(it) }

/**
 * Parses this string into a [UserId] or throws if the value is not a valid UUID.
 */
public fun String.toUserIdOrThrow(): UserId = UserId(Uuid.parse(this))
