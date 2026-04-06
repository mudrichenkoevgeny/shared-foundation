package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionPayload
import kotlin.time.Instant

/**
 * Session row; wire counterpart [UserSessionPayload]. [identifier] and [ipAddress] follow [isSensitiveValuesMasked].
 */
data class UserSession(
    val id: UserSessionId = UserSessionId.generate(),
    val userId: UserId,
    val identifier: String,
    val identifierId: UserIdentifierId,
    val identifierAuthProvider: UserAuthProvider,
    val revoked: Boolean,
    val clientType: ClientType?,
    val userAgent: String?,
    val ipAddress: String?,
    val language: String?,
    val deviceId: String?,
    val deviceName: String?,
    val appVersion: String?,
    val operationSystemVersion: String?,
    val expiresAt: Instant?,
    val lastAccessedAt: Instant?,
    val lastReauthenticatedAt: Instant?,
    val isSensitiveValuesMasked: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant?
)
