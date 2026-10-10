package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.mapper.client.toClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.mapper.client.toClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.toUserSessionIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionSummaryPayload
import kotlin.time.Instant

/**
 * Extension functions mapping summary session models between domain and network representations.
 */

/**
 * Builds domain [UserSessionSummary] from [UserSessionSummaryPayload].
 */
fun UserSessionSummaryPayload.toUserSessionSummary(): UserSessionSummary = UserSessionSummary(
    id = id.toUserSessionIdOrThrow(),
    deviceInfo = deviceInfo.toClientDeviceInfo(),
    identifierDisplayName = identifierDisplayName,
    identifierAuthProvider = UserAuthProvider.fromValueOrThrow(identifierAuthProvider),
    lastAccessedAt = Instant.fromEpochMilliseconds(lastAccessedAt),
    expiresAt = Instant.fromEpochMilliseconds(expiresAt)
)

/**
 * Builds network [UserSessionSummaryPayload] from domain [UserSessionSummary].
 */
fun UserSessionSummary.toUserSessionSummaryPayload(): UserSessionSummaryPayload = UserSessionSummaryPayload(
    id = id.asHexDashString(),
    deviceInfo = deviceInfo.toClientDeviceInfoPayload(),
    identifierDisplayName = identifierDisplayName,
    identifierAuthProvider = identifierAuthProvider.serialName,
    lastAccessedAt = lastAccessedAt.toEpochMilliseconds(),
    expiresAt = expiresAt.toEpochMilliseconds()
)
