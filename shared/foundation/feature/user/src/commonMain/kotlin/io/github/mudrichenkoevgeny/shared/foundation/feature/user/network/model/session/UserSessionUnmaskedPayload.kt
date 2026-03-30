package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.clienttype.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.SessionPermissionCodes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO representing user session metadata **with unmasked IP address** when policy allows exposing it.
 *
 * Used for the **account owner** viewing their own sessions in the open API, and for management flows where
 * the caller holds a permission that allows unmasked session lists (see [SessionPermissionCodes]).
 *
 * @property id The unique identifier of the session.
 * @property userId The ID of the user who owns this session.
 * @property identifier The raw login value for the credential used to create this session (e.g. email, phone, external id).
 * @property identifierId The ID of the specific identifier record used to create this session.
 * @property identifierAuthProvider The authentication method used for this session (e.g. [UserAuthProvider]).
 * @property revoked Whether the session is revoked and must not be used for authentication.
 * @property clientType The type of client application associated with this session (e.g. [ClientType]).
 * @property userAgent The raw User-Agent string of the client used for this session.
 * @property ipAddress The IP address from which the session was initiated or last accessed.
 * @property language The preferred language of the user during the session (e.g., from Accept-Language header).
 * @property deviceId Opaque stable device identifier for this session, if available.
 * @property deviceName The human-readable name of the device used for the session, if available.
 * @property appVersion The version of the client application used for this session.
 * @property operationSystemVersion The version of the operating system running on the client device.
 * @property expiresAt The timestamp when the session is scheduled to expire, in milliseconds.
 * @property lastAccessedAt The timestamp of the last recorded activity for this session, in milliseconds.
 * @property lastReauthenticatedAt The timestamp of the last step-up / re-authentication for this session, in milliseconds.
 * @property createdAt The timestamp when the session was created, in milliseconds.
 * @property updatedAt The timestamp when the session metadata was last updated, in milliseconds.
 */
@Serializable
data class UserSessionUnmaskedPayload(
    @SerialName(CommonApiFields.ID)
    val id: String,

    @SerialName(UserApiFields.USER_ID)
    val userId: String,

    @SerialName(UserApiFields.IDENTIFIER)
    val identifier: String,

    @SerialName(UserApiFields.IDENTIFIER_ID)
    val identifierId: String,

    @SerialName(UserApiFields.IDENTIFIER_AUTH_PROVIDER)
    val identifierAuthProvider: String,

    @SerialName(UserApiFields.REVOKED)
    val revoked: Boolean = false,

    @SerialName(CommonApiFields.CLIENT_TYPE)
    val clientType: String? = null,

    @SerialName(CommonApiFields.USER_AGENT)
    val userAgent: String? = null,

    @SerialName(CommonApiFields.IP_ADDRESS)
    val ipAddress: String? = null,

    @SerialName(CommonApiFields.LANGUAGE)
    val language: String? = null,

    @SerialName(CommonApiFields.DEVICE_ID)
    val deviceId: String? = null,

    @SerialName(CommonApiFields.DEVICE_NAME)
    val deviceName: String? = null,

    @SerialName(CommonApiFields.APP_VERSION)
    val appVersion: String? = null,

    @SerialName(CommonApiFields.OPERATION_SYSTEM_VERSION)
    val operationSystemVersion: String? = null,

    @SerialName(UserApiFields.EXPIRES_AT)
    val expiresAt: Long? = null,

    @SerialName(UserApiFields.LAST_ACCESSED_AT)
    val lastAccessedAt: Long? = null,

    @SerialName(UserApiFields.LAST_REAUTHENTICATED_AT)
    val lastReauthenticatedAt: Long? = null,

    @SerialName(CommonApiFields.CREATED_AT)
    val createdAt: Long,

    @SerialName(CommonApiFields.UPDATED_AT)
    val updatedAt: Long? = null
)
