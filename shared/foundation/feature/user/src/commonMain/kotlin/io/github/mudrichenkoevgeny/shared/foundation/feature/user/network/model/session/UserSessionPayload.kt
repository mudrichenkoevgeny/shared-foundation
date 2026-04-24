package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.SessionPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for a user session (management and open). [identifier] and [ipAddress] are raw or masked per
 * [isSensitiveValuesMasked] and [SessionPermissionCode].
 */
@Serializable
data class UserSessionPayload(
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

    @SerialName(CommonApiFields.CLIENT_DEVICE_INFO)
    val clientDeviceInfo: ClientDeviceInfoPayload = ClientDeviceInfoPayload(),

    @SerialName(CommonApiFields.USER_AGENT)
    val userAgent: String? = null,

    @SerialName(CommonApiFields.IP_ADDRESS)
    val ipAddress: String? = null,

    @SerialName(UserApiFields.EXPIRES_AT)
    val expiresAt: Long? = null,

    @SerialName(UserApiFields.LAST_ACCESSED_AT)
    val lastAccessedAt: Long? = null,

    @SerialName(UserApiFields.LAST_REAUTHENTICATED_AT)
    val lastReauthenticatedAt: Long? = null,

    @SerialName(CommonApiFields.IS_SENSITIVE_VALUES_MASKED)
    val isSensitiveValuesMasked: Boolean,

    @SerialName(CommonApiFields.CREATED_AT)
    val createdAt: Long,

    @SerialName(CommonApiFields.UPDATED_AT)
    val updatedAt: Long? = null
)
