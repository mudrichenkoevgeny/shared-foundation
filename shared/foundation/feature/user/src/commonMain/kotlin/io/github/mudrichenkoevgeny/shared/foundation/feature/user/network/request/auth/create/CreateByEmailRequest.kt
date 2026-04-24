package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.create

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.RequiredField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request body for creating a user account with email and password (e.g. management create-user flow).
 *
 * @property email The email address for the new account.
 * @property password The initial password for the new account.
 * @property role The role to assign to the new account.
 * @property status The initial account status.
 * @property permissionCodes Explicit [PermissionCode] values to assign.
 */
@Serializable
data class CreateByEmailRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.EMAIL)
    val email: String,

    @NotBlankStringField
    @SerialName(UserApiFields.PASSWORD)
    val password: String,

    @NotBlankStringField
    @SerialName(UserApiFields.ROLE)
    val role: String,

    @NotBlankStringField
    @SerialName(UserApiFields.ACCOUNT_STATUS)
    val status: String,

    @RequiredField
    @SerialName(UserApiFields.AUTHORITY_LEVEL)
    val authorityLevel: Int,

    @RequiredField
    @SerialName(UserApiFields.PERMISSION_CODES)
    val permissionCodes: Set<String>
)