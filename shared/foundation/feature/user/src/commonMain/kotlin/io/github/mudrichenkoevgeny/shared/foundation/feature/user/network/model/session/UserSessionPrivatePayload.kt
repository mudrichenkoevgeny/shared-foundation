package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Detailed session wire payload for authenticated self-service and management inspection.
 *
 * Wire keys use `snake_case` via [CommonApiFields] and [UserApiFields]. Aligns with domain [UserSessionPrivate].
 *
 * @property id [UserSessionPrivate.id] on the wire: [UserSessionId] as hex-dash string.
 * @property userId [UserSessionPrivate.userId] on the wire: [UserId] as hex-dash string.
 * @property userRole [UserSessionPrivate.userRole]; wire values match [UserRole.serialName].
 * @property identifier [UserSessionPrivate.identifier].
 * @property identifierId [UserSessionPrivate.identifierId] on the wire: [UserIdentifierId] as hex-dash string.
 * @property identifierDisplayName [UserSessionPrivate.identifierDisplayName].
 * @property identifierAuthProvider [UserSessionPrivate.identifierAuthProvider]; wire values match [UserAuthProvider.serialName].
 * @property deviceInfo [UserSessionPrivate.deviceInfo].
 * @property userAgent [UserSessionPrivate.userAgent].
 * @property ipAddress [UserSessionPrivate.ipAddress].
 * @property expiresAt [UserSessionPrivate.expiresAt] as Unix epoch milliseconds ([Instant]).
 * @property lastAccessedAt [UserSessionPrivate.lastAccessedAt] as Unix epoch milliseconds ([Instant]).
 * @property lastReauthenticatedAt [UserSessionPrivate.lastReauthenticatedAt] as Unix epoch milliseconds ([Instant]).
 * @property isSensitiveValuesMasked [UserSessionPrivate.isSensitiveValuesMasked].
 * @property createdAt [UserSessionPrivate.createdAt] as Unix epoch milliseconds ([Instant]).
 */
@Serializable
data class UserSessionPrivatePayload(
    @SerialName(CommonApiFields.ID)
    val id: String,

    @SerialName(UserApiFields.USER_ID)
    val userId: String,

    @SerialName(UserApiFields.USER_ROLE)
    val userRole: String,

    @SerialName(UserApiFields.IDENTIFIER)
    val identifier: String,

    @SerialName(UserApiFields.IDENTIFIER_ID)
    val identifierId: String,

    @SerialName(UserApiFields.IDENTIFIER_DISPLAY_NAME)
    val identifierDisplayName: String,

    @SerialName(UserApiFields.IDENTIFIER_AUTH_PROVIDER)
    val identifierAuthProvider: String,

    @SerialName(CommonApiFields.CLIENT_DEVICE_INFO)
    val deviceInfo: ClientDeviceInfoPayload,

    @SerialName(CommonApiFields.USER_AGENT)
    val userAgent: String? = null,

    @SerialName(CommonApiFields.IP_ADDRESS)
    val ipAddress: String? = null,

    @SerialName(UserApiFields.EXPIRES_AT)
    val expiresAt: Long,

    @SerialName(UserApiFields.LAST_ACCESSED_AT)
    val lastAccessedAt: Long,

    @SerialName(UserApiFields.LAST_REAUTHENTICATED_AT)
    val lastReauthenticatedAt: Long,

    @SerialName(CommonApiFields.IS_SENSITIVE_VALUES_MASKED)
    val isSensitiveValuesMasked: Boolean,

    @SerialName(CommonApiFields.CREATED_AT)
    val createdAt: Long
)
