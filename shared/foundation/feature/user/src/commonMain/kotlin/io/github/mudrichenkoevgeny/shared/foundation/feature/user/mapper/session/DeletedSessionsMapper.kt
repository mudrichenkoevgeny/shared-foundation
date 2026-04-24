package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.session

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.DeletedSessions
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.toUserSessionIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.DeletedSessionsPayload

/**
 * Maps between [DeletedSessionsPayload] and domain [DeletedSessions].
 */

/**
 * Builds domain [DeletedSessions] from [DeletedSessionsPayload].
 * * @throws IllegalArgumentException when any of the session IDs is not a valid UUID string.
 */
fun DeletedSessionsPayload.toDeletedSessions(): DeletedSessions = DeletedSessions(
    deletedSessionIds = deletedSessionIds.map { it.toUserSessionIdOrThrow() }
)

/**
 * Builds network [DeletedSessionsPayload] from domain [DeletedSessions].
 */
fun DeletedSessions.toDeletedSessionsPayload(): DeletedSessionsPayload = DeletedSessionsPayload(
    deletedSessionIds = deletedSessionIds.map { it.asHexDashString() }
)