package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.RefreshTokenHash
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import kotlin.time.Instant

/**
 * Internal session row for server-side storage and business logic.
 *
 * Adds [refreshTokenHash] and database row modification timestamp [updatedAt] to [UserSessionPrivate]-equivalent fields;
 * these internal fields must not be exposed directly via public API payloads.
 *
 * @property id Session row id.
 * @property userId Owning user id.
 * @property userRole User role during the session.
 * @property identifier Raw or masked login identifier string.
 * @property identifierId Id of the identifier used for login.
 * @property identifierDisplayName Display name of the identifier used for login.
 * @property identifierAuthProvider Authentication provider category.
 * @property refreshTokenHash Hash of the refresh token.
 * @property clientDeviceInfo Hardware and environment device info.
 * @property userAgent HTTP `User-Agent` header value, or `null`.
 * @property ipAddress Raw or masked client IP address, or `null`.
 * @property expiresAt Session expiration timestamp.
 * @property lastAccessedAt Timestamp of last session activity.
 * @property lastReauthenticatedAt Timestamp of last re-authentication.
 * @property createdAt Creation timestamp.
 * @property updatedAt Last database row update timestamp, or `null`.
 */
public data class UserSessionInternal(
    public val id: UserSessionId = UserSessionId.generate(),
    public val userId: UserId,
    public val userRole: UserRole,
    public val identifier: String,
    public val identifierId: UserIdentifierId,
    public val identifierDisplayName: String,
    public val identifierAuthProvider: UserAuthProvider,
    public val refreshTokenHash: RefreshTokenHash,
    public val clientDeviceInfo: ClientDeviceInfo,
    public val userAgent: String?,
    public val ipAddress: String?,
    public val expiresAt: Instant,
    public val lastAccessedAt: Instant,
    public val lastReauthenticatedAt: Instant,
    public val createdAt: Instant,
    public val updatedAt: Instant?
)
