package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier

import kotlin.jvm.JvmInline
import kotlin.uuid.Uuid

/**
 * Identifier of a user-related external identifier (for example, email or phone).
 */
@JvmInline
public value class UserIdentifierId(
    public val value: Uuid
) {
    /**
     * Returns the underlying [Uuid] as a canonical hex string with dashes.
     */
    public fun asHexDashString(): String = value.toHexDashString()

    public companion object {
        /**
         * Generates a new random [UserIdentifierId].
         */
        public fun generate(): UserIdentifierId = UserIdentifierId(Uuid.random())
    }
}

/**
 * Attempts to parse this string as a [UserIdentifierId].
 *
 * Returns `null` if the value is not a valid UUID in hex-with-dashes form.
 */
public fun String.toUserIdentifierIdOrNull(): UserIdentifierId? = Uuid.parseOrNull(this)?.let { UserIdentifierId(it) }

/**
 * Parses this string into a [UserIdentifierId] or throws if the value is not a valid UUID.
 */
public fun String.toUserIdentifierIdOrThrow(): UserIdentifierId = UserIdentifierId(Uuid.parse(this))
