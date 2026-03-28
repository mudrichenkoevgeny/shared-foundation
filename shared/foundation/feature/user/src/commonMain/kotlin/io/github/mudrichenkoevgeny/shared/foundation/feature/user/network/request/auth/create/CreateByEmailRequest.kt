package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.create

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.UserPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request body for creating a user account with email and password (e.g. management create-user flow).
 *
 * @property email The email address for the new account.
 * @property password The initial password for the new account.
 * @property permissions Explicit [UserPermissionCode] values to assign.
 */
@Serializable
data class CreateByEmailRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.EMAIL)
    val email: String,

    @NotBlankStringField
    @SerialName(UserApiFields.PASSWORD)
    val password: String,

    @SerialName(UserApiFields.PERMISSIONS)
    val permissions: Set<UserPermissionCode>
)