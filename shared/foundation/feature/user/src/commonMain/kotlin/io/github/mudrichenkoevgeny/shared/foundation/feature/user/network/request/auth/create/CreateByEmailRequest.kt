package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.create

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.RequiredField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserStorageConfigurationPayload
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request body for creating a user account with email and password (e.g. management create-user flow).
 *
 * @property email The email address for the new account.
 * @property password Optional initial password for the new account. If null, an invitation
 * email is sent to set up the initial password.
 * @property role The role to assign to the new account.
 * @property status The initial account status.
 * @property authorityLevel Authority level assigned to the new account.
 * @property permissionCodes Explicit [PermissionCode] values to assign.
 * @property storageConfiguration Custom storage configuration overrides and limits for the new account.
 * @property nickname Optional requested nickname handle for the new account.
 * @property displayName Optional human-readable display name for the new account.
 * @property avatarFileId Optional avatar file identifier for the new account.
 */
@Serializable
data class CreateByEmailRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.EMAIL)
    val email: String,

    @SerialName(UserApiFields.PASSWORD)
    val password: String? = null,

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
    val permissionCodes: Set<String>,

    @SerialName(UserApiFields.STORAGE_CONFIGURATION)
    val storageConfiguration: UserStorageConfigurationPayload?,

    @SerialName(UserApiFields.NICKNAME)
    val nickname: String? = null,

    @SerialName(UserApiFields.DISPLAY_NAME)
    val displayName: String? = null,

    @SerialName(UserApiFields.AVATAR_FILE_ID)
    val avatarFileId: String? = null
)
