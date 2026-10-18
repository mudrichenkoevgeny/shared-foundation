package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionSummaryPayload
import kotlin.time.Instant

/**
 * Condensed session row for active device lists and session badges.
 *
 * Wire counterpart is [UserSessionSummaryPayload].
 *
 * @property id Session row id.
 * @property userId Identifier of the user account that owns this session ([UserId]).
 * @property clientDeviceInfo Hardware and environment device info.
 * @property identifierDisplayName Display name of the identifier used for login.
 * @property identifierAuthProvider Authentication provider category.
 * @property lastAccessedAt Timestamp of last session activity.
 * @property expiresAt Session expiration timestamp.
 */
data class UserSessionSummary(
    override val id: UserSessionId = UserSessionId.generate(),
    override val userId: UserId,
    override val clientDeviceInfo: ClientDeviceInfo,
    val identifierDisplayName: String,
    val identifierAuthProvider: UserAuthProvider,
    val lastAccessedAt: Instant,
    override val expiresAt: Instant
) : UserSession
