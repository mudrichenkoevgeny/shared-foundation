package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionPrivatePayload
import kotlin.time.Instant

/**
 * Detailed session row for authenticated self-service and management inspection.
 *
 * Wire counterpart is [UserSessionPrivatePayload].
 *
 * @property id Session row id.
 * @property userId Owning user id.
 * @property userRole User role during the session.
 * @property identifier Raw or masked login identifier string.
 * @property identifierId Id of the identifier used for login.
 * @property identifierDisplayName Display name of the identifier used for login.
 * @property identifierAuthProvider Authentication provider category.
 * @property clientDeviceInfo Hardware and environment device info.
 * @property userAgent HTTP `User-Agent` header value, or `null`.
 * @property ipAddress Raw or masked client IP address, or `null`.
 * @property expiresAt Session expiration timestamp.
 * @property lastAccessedAt Timestamp of last session activity.
 * @property lastReauthenticatedAt Timestamp of last re-authentication.
 * @property isSensitiveValuesMasked `true` when [identifier] and [ipAddress] are masked.
 * @property createdAt Creation timestamp.
 * @property updatedAt Last session record modification timestamp, or `null`.
 */
data class UserSessionPrivate(
    override val id: UserSessionId = UserSessionId.generate(),
    override val userId: UserId,
    val userRole: UserRole,
    val identifier: String,
    val identifierId: UserIdentifierId,
    val identifierDisplayName: String,
    val identifierAuthProvider: UserAuthProvider,
    override val clientDeviceInfo: ClientDeviceInfo,
    val userAgent: String?,
    val ipAddress: String?,
    override val expiresAt: Instant,
    val lastAccessedAt: Instant,
    val lastReauthenticatedAt: Instant,
    val isSensitiveValuesMasked: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant?
) : UserSession
