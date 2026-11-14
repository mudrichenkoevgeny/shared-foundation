package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event

import kotlin.uuid.Uuid

/**
 * Stable, unique identifier for a single audit event.
 *
 * Wraps a [Uuid] to provide type safety where audit event identifiers
 * are used (e.g. repository, API responses).
 *
 * @param value UUID backing the id; use [asHexDashString] for serialization.
 */
@JvmInline
public value class AuditEventId(
    public val value: Uuid
) {
    /**
     * Returns the id as a canonical hex string with dashes (e.g. for API or logging).
     */
    public fun asHexDashString(): String = value.toHexDashString()

    public companion object {
        /**
         * Generates a new random [AuditEventId].
         */
        public fun generate(): AuditEventId = AuditEventId(Uuid.random())
    }
}

/**
 * Attempts to parse this string as an [AuditEventId].
 *
 * Returns `null` if the value is not a valid UUID in hex-with-dashes form.
 */
public fun String.toAuditEventIdOrNull(): AuditEventId? = Uuid.parseOrNull(this)?.let { AuditEventId(it) }

/**
 * Parses this string into an [AuditEventId] or throws if the value is not a valid UUID.
 */
public fun String.toAuditEventIdOrThrow(): AuditEventId = AuditEventId(Uuid.parse(this))
