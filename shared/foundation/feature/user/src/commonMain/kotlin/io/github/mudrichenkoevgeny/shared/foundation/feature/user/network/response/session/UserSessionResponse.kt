package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.UserClientType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
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
 * @property language The preferred language of the user during the session (e.g., from Accept-Language header).
 * @property deviceName The human-readable name of the device used for the session, if available.
 * @property appVersion The version of the client application used for this session.
 * @property operationSystemVersion The version of the operating system running on the client device.
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

    @SerialName(CommonApiFields.CLIENT_TYPE)
    val clientType: String? = null,

    @SerialName(CommonApiFields.USER_AGENT)
    val userAgent: String? = null,

    @SerialName(CommonApiFields.IP_ADDRESS)
    val ipAddress: String? = null,

    @SerialName(CommonApiFields.LANGUAGE)
    val language: String? = null,

    @SerialName(CommonApiFields.DEVICE_NAME)
    val deviceName: String? = null,

    @SerialName(CommonApiFields.APP_VERSION)
    val appVersion: String? = null,

    @SerialName(CommonApiFields.OPERATION_SYSTEM_VERSION)
    val operationSystemVersion: String? = null,

    @SerialName(CommonApiFields.CREATED_AT)
    val createdAt: Long? = null,

    @SerialName(UserApiFields.LAST_ACCESSED_AT)
    val lastAccessedAt: Long? = null
)