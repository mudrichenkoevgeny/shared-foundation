package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Condensed session wire payload for active device lists and session badges.
 *
 * Wire keys use `snake_case` via [CommonApiFields] and [UserApiFields]. Aligns with domain [UserSessionSummary].
 *
 * @property id [UserSessionSummary.id] on the wire: [UserSessionId] as hex-dash string.
 * @property deviceInfo [UserSessionSummary.deviceInfo].
 * @property identifierDisplayName [UserSessionSummary.identifierDisplayName].
 * @property identifierAuthProvider [UserSessionSummary.identifierAuthProvider]; wire values match [UserAuthProvider.serialName].
 * @property lastAccessedAt [UserSessionSummary.lastAccessedAt] as Unix epoch milliseconds ([Instant]).
 * @property expiresAt [UserSessionSummary.expiresAt] as Unix epoch milliseconds ([Instant]).
 */
@Serializable
data class UserSessionSummaryPayload(
    @SerialName(CommonApiFields.ID)
    val id: String,

    @SerialName(CommonApiFields.CLIENT_DEVICE_INFO)
    val deviceInfo: ClientDeviceInfoPayload,

    @SerialName(UserApiFields.IDENTIFIER_DISPLAY_NAME)
    val identifierDisplayName: String,

    @SerialName(UserApiFields.IDENTIFIER_AUTH_PROVIDER)
    val identifierAuthProvider: String,

    @SerialName(UserApiFields.LAST_ACCESSED_AT)
    val lastAccessedAt: Long,

    @SerialName(UserApiFields.EXPIRES_AT)
    val expiresAt: Long
)
