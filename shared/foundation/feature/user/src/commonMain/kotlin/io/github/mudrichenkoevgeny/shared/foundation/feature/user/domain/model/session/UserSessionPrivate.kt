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
public data class UserSessionPrivate(
    public val id: UserSessionId = UserSessionId.generate(),
    public val userId: UserId,
    public val userRole: UserRole,
    public val identifier: String,
    public val identifierId: UserIdentifierId,
    public val identifierDisplayName: String,
    public val identifierAuthProvider: UserAuthProvider,
    public val clientDeviceInfo: ClientDeviceInfo,
    public val userAgent: String?,
    public val ipAddress: String?,
    public val expiresAt: Instant,
    public val lastAccessedAt: Instant,
    public val lastReauthenticatedAt: Instant,
    public val isSensitiveValuesMasked: Boolean,
    public val createdAt: Instant,
    public val updatedAt: Instant?
)
