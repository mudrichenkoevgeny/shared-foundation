package io.github.mudrichenkoevgeny.shared.foundation.core.audit.mapper.audit

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventInternal
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventSummary

/**
 * Converts an [AuditEventInternal] into a client-facing [AuditEventPrivate].
 *
 * Excludes server-side redaction policy (`resourceValueSensitivity`).
 */
public fun AuditEventInternal.toAuditEventPrivate(): AuditEventPrivate = AuditEventPrivate(
    id = id,
    actorId = actorId,
    actorType = actorType,
    actorUserRole = actorUserRole,
    action = action,
    resource = resource,
    resourceId = resourceId,
    status = status,
    metadata = metadata,
    message = message,
    createdAt = createdAt
)

/**
 * Converts an [AuditEventPrivate] into a condensed [AuditEventSummary].
 *
 * Excludes detailed actor/resource IDs, diagnostic messages, and metadata entries.
 */
public fun AuditEventPrivate.toAuditEventSummary(): AuditEventSummary = AuditEventSummary(
    id = id,
    actorType = actorType,
    action = action,
    resource = resource,
    status = status,
    createdAt = createdAt
)

/**
 * Converts an [AuditEventInternal] directly into a condensed [AuditEventSummary].
 *
 * Maps fields directly without allocating an intermediate [AuditEventPrivate] instance.
 */
public fun AuditEventInternal.toAuditEventSummary(): AuditEventSummary = AuditEventSummary(
    id = id,
    actorType = actorType,
    action = action,
    resource = resource,
    status = status,
    createdAt = createdAt
)
