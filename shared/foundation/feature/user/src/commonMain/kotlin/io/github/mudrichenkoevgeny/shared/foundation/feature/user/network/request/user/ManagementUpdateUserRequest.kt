package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.patch.PatchField
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserStorageConfigurationPayload
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request body for administrative updates to a target user's account, status, permissions, and details (management API).
 *
 * Uses 3-state [PatchField] properties:
 * - [PatchField.Unchanged]: Field omitted in request; leave unchanged in persistence.
 * - [PatchField.Value] with non-null value: Set field to new value in persistence.
 * - [PatchField.Value] with `null` value: Explicitly clear/nullify field in persistence.
 *
 * @property accountStatus Target status as a [UserAccountStatus] serial name update ([PatchField]).
 * @property authorityLevel Target authority level update ([PatchField]).
 * @property permissionCodes Explicit set of permission codes update ([PatchField]). Pass empty set to revoke all explicit grants.
 * @property lockoutType Target account lockout type as an [AccountLockoutType] serial name update ([PatchField]). Pass `"NONE"` to unlock.
 * @property temporaryLockoutUntil Target temporary lockout expiration timestamp update ([PatchField]).
 * @property storageConfiguration Target storage configuration overrides update ([PatchField]). Pass `null` to reset to system defaults.
 * @property nickname Target nickname update ([PatchField]).
 * @property displayName Target display name update ([PatchField]).
 * @property avatarFileId Target avatar file identifier update ([PatchField]).
 * @property birthdate Target birthdate timestamp update ([PatchField]).
 */
@Serializable
data class ManagementUpdateUserRequest(
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    @SerialName(UserApiFields.ACCOUNT_STATUS)
    val accountStatus: PatchField<String> = PatchField.Unchanged,

    @EncodeDefault(EncodeDefault.Mode.NEVER)
    @SerialName(UserApiFields.AUTHORITY_LEVEL)
    val authorityLevel: PatchField<Int> = PatchField.Unchanged,

    @EncodeDefault(EncodeDefault.Mode.NEVER)
    @SerialName(UserApiFields.PERMISSION_CODES)
    val permissionCodes: PatchField<Set<String>> = PatchField.Unchanged,

    @EncodeDefault(EncodeDefault.Mode.NEVER)
    @SerialName(UserApiFields.ACCOUNT_LOCKOUT_TYPE)
    val lockoutType: PatchField<String> = PatchField.Unchanged,

    @EncodeDefault(EncodeDefault.Mode.NEVER)
    @SerialName(UserApiFields.TEMPORARY_LOCKOUT_UNTIL)
    val temporaryLockoutUntil: PatchField<Long> = PatchField.Unchanged,

    @EncodeDefault(EncodeDefault.Mode.NEVER)
    @SerialName(UserApiFields.STORAGE_CONFIGURATION)
    val storageConfiguration: PatchField<UserStorageConfigurationPayload> = PatchField.Unchanged,

    @EncodeDefault(EncodeDefault.Mode.NEVER)
    @SerialName(UserApiFields.NICKNAME)
    val nickname: PatchField<String> = PatchField.Unchanged,

    @EncodeDefault(EncodeDefault.Mode.NEVER)
    @SerialName(UserApiFields.DISPLAY_NAME)
    val displayName: PatchField<String> = PatchField.Unchanged,

    @EncodeDefault(EncodeDefault.Mode.NEVER)
    @SerialName(UserApiFields.AVATAR_FILE_ID)
    val avatarFileId: PatchField<String> = PatchField.Unchanged,

    @EncodeDefault(EncodeDefault.Mode.NEVER)
    @SerialName(UserApiFields.BIRTHDATE)
    val birthdate: PatchField<Long> = PatchField.Unchanged
)
