package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.SessionPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for a user session (management and open). [identifier] and [ipAddress] are raw or masked per
 * [isSensitiveValuesMasked] and [SessionPermissionCode].
 *
 * @property id Session id on the wire as a hex-dash string.
 * @property userId Owning user id as a hex-dash string.
 * @property userRole Wire value matching [UserRole.serialName].
 * @property identifier Raw or masked login identifier string depending on [isSensitiveValuesMasked] and [SessionPermissionCode].
 * @property identifierId Id of the identifier used for login as a hex-dash string.
 * @property identifierDisplayName Display name of the identifier used for login.
 * @property identifierAuthProvider Wire value matching [UserAuthProvider.serialName].
 * @property clientDeviceInfo Hardware and environment information payload ([ClientDeviceInfoPayload]).
 * @property userAgent HTTP `User-Agent` header value, or `null` if missing.
 * @property ipAddress Raw or masked client IP address, or `null` if unavailable.
 * @property expiresAt Session expiration timestamp in Unix epoch milliseconds.
 * @property lastAccessedAt Timestamp of last session activity in Unix epoch milliseconds.
 * @property lastReauthenticatedAt Timestamp of last re-authentication in Unix epoch milliseconds.
 * @property isSensitiveValuesMasked `true` when [identifier] and [ipAddress] are masked for the caller.
 * @property createdAt Creation timestamp in Unix epoch milliseconds.
 * @property updatedAt Last modification timestamp in Unix epoch milliseconds, or `null` if unchanged.
 */
@Serializable
data class UserSessionPayload(
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
    val clientDeviceInfo: ClientDeviceInfoPayload,

    @SerialName(CommonApiFields.USER_AGENT)
    val userAgent: String?,

    @SerialName(CommonApiFields.IP_ADDRESS)
    val ipAddress: String?,

    @SerialName(UserApiFields.EXPIRES_AT)
    val expiresAt: Long,

    @SerialName(UserApiFields.LAST_ACCESSED_AT)
    val lastAccessedAt: Long,

    @SerialName(UserApiFields.LAST_REAUTHENTICATED_AT)
    val lastReauthenticatedAt: Long,

    @SerialName(CommonApiFields.IS_SENSITIVE_VALUES_MASKED)
    val isSensitiveValuesMasked: Boolean,

    @SerialName(CommonApiFields.CREATED_AT)
    val createdAt: Long,

    @SerialName(CommonApiFields.UPDATED_AT)
    val updatedAt: Long?
)
