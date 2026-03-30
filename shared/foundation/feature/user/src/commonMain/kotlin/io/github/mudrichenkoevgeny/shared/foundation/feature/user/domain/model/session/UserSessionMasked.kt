package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.clienttype.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionMaskedPayload
import kotlin.time.Instant

/**
 * Session row as exposed when the list policy requires **masked** sensitive fields (IP).
 *
 * Wire counterpart: [UserSessionMaskedPayload]. Session row [id] is a UUID; timestamps are
 * [Instant] (epoch millis on the wire).
 */
data class UserSessionMasked(
    val id: UserSessionId = UserSessionId.generate(),
    val userId: UserId,
    val maskedIdentifier: String,
    val identifierId: UserIdentifierId,
    val identifierAuthProvider: UserAuthProvider,
    val revoked: Boolean,
    val clientType: ClientType?,
    val userAgent: String?,
    val maskedIpAddress: String?,
    val language: String?,
    val deviceId: String?,
    val deviceName: String?,
    val appVersion: String?,
    val operationSystemVersion: String?,
    val expiresAt: Instant?,
    val lastAccessedAt: Instant?,
    val lastReauthenticatedAt: Instant?,
    val createdAt: Instant,
    val updatedAt: Instant?
)
