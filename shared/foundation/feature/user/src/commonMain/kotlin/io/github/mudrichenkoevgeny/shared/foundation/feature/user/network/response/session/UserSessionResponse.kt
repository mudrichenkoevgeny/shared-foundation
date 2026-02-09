package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.constants.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserClientType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.constants.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO representing metadata about an active or past user session.
 *
 * @property id The unique identifier of the session.
 * @property identifierId The ID of the specific identifier used to create this session.
 * @property identifierAuthProvider The authentication method used for this session (e.g [UserAuthProvider]).
 * @property expiresAt The timestamp when the session is scheduled to expire, in milliseconds.
 * @property clientType The type of client application associated with this session (e.g. [UserClientType]).
 * @property userAgent The raw User-Agent string of the client used for this session.
 * @property ipAddress The IP address from which the session was initiated or last accessed.
 * @property deviceName The human-readable name of the device used for the session, if available.
 * @property createdAt The timestamp when the session was created, in milliseconds.
 * @property lastAccessedAt The timestamp of the last recorded activity for this session, in milliseconds.
 */
@Serializable
data class UserSessionResponse(
    @SerialName(CommonApiFields.ID)
    val id: String,

    @SerialName(UserApiFields.IDENTIFIER_ID)
    val identifierId: String,

    @SerialName(UserApiFields.IDENTIFIER_AUTH_PROVIDER)
    val identifierAuthProvider: String,

    @SerialName(UserApiFields.EXPIRES_AT)
    val expiresAt: Long? = null,

    @SerialName(UserApiFields.CLIENT_TYPE)
    val clientType: String? = null,

    @SerialName(UserApiFields.USER_AGENT)
    val userAgent: String? = null,

    @SerialName(UserApiFields.IP_ADDRESS)
    val ipAddress: String? = null,

    @SerialName(UserApiFields.DEVICE_NAME)
    val deviceName: String? = null,

    @SerialName(CommonApiFields.CREATED_AT)
    val createdAt: Long? = null,

    @SerialName(UserApiFields.LAST_ACCESSED_AT)
    val lastAccessedAt: Long? = null
)