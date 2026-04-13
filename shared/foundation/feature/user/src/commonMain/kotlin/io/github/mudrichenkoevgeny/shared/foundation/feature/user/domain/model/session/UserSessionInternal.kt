package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.RefreshTokenHash
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionPayload
import kotlin.time.Instant

/**
 * Internal session row for server-side storage and business logic.
 *
 * Adds [refreshTokenHash] to [UserSession]-equivalent fields; this hash must not be exposed via API payloads.
 * Public wire representation is [UserSessionPayload].
 */
data class UserSessionInternal(
    val id: UserSessionId = UserSessionId.generate(),
    val userId: UserId,
    val identifier: String,
    val identifierId: UserIdentifierId,
    val identifierAuthProvider: UserAuthProvider,
    val refreshTokenHash: RefreshTokenHash,
    val revoked: Boolean,
    val deviceInfo: ClientDeviceInfo,
    val userAgent: String?,
    val ipAddress: String?,
    val expiresAt: Instant?,
    val lastAccessedAt: Instant?,
    val lastReauthenticatedAt: Instant?,
    val isSensitiveValuesMasked: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant?
)
