package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session

import kotlin.jvm.JvmInline
import kotlin.uuid.Uuid

/**
 * Identifier of an authenticated user session.
 *
 * Each login typically receives a dedicated [UserSessionId] so that sessions
 * can be deleted, audited and correlated in logs independently.
 */
@JvmInline
public value class UserSessionId(
    public val value: Uuid
) {
    /**
     * Returns the underlying [Uuid] as a canonical hex string with dashes.
     */
    public fun asHexDashString(): String = value.toHexDashString()

    public companion object {
        /**
         * Generates a new random [UserSessionId].
         */
        public fun generate(): UserSessionId = UserSessionId(Uuid.random())
    }
}

/**
 * Attempts to parse this string as a [UserSessionId].
 *
 * Returns `null` if the value is not a valid UUID in hex-with-dashes form.
 */
public fun String.toUserSessionIdOrNull(): UserSessionId? = Uuid.parseOrNull(this)?.let { UserSessionId(it) }

/**
 * Parses this string into a [UserSessionId] or throws if the value is not a valid UUID.
 */
public fun String.toUserSessionIdOrThrow(): UserSessionId = UserSessionId(Uuid.parse(this))
