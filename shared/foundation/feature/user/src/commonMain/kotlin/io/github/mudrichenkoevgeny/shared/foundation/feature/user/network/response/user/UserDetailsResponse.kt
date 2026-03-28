package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.UserPermissionCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO representing extended user details for non-public contexts.
 *
 * Used in authenticated endpoints (self profile) and administrative user-management flows.
 *
 * @property id The unique identifier of the user.
 * @property role The access level assigned to the user (e.g. [UserRole]).
 * @property accountStatus The current state of the account (e.g. [UserAccountStatus]).
 * @property accountStatusBeforeDeletion Account status before deletion was requested; only meaningful when the account is pending deletion (e.g. [UserAccountStatus]).
 * @property permissions Explicit permissions assigned to the user (see [UserPermissionCode]).
 * @property lastLoginAt The timestamp of the most recent successful authentication, in milliseconds.
 * @property lastActiveAt The timestamp of the last recorded user activity, in milliseconds.
 * @property createdAt The timestamp of the account creation, in milliseconds.
 * @property updatedAt The timestamp of the last account profile modification, in milliseconds.
 * @property scheduledPermanentDeletionAt The timestamp when the account will be permanently removed if not restored, in milliseconds; `null` if not pending deletion.
 */
@Serializable
data class UserDetailsResponse(
    @SerialName(CommonApiFields.ID)
    val id: String,

    @SerialName(UserApiFields.ROLE)
    val role: String,

    @SerialName(UserApiFields.ACCOUNT_STATUS)
    val accountStatus: String,

    @SerialName(UserApiFields.ACCOUNT_STATUS_BEFORE_DELETION)
    val accountStatusBeforeDeletion: String,

    @SerialName(UserApiFields.PERMISSIONS)
    val permissions: Set<UserPermissionCode>,

    @SerialName(UserApiFields.LAST_LOGIN_AT)
    val lastLoginAt: Long? = null,

    @SerialName(UserApiFields.LAST_ACTIVE_AT)
    val lastActiveAt: Long? = null,

    @SerialName(CommonApiFields.CREATED_AT)
    val createdAt: Long,

    @SerialName(CommonApiFields.UPDATED_AT)
    val updatedAt: Long? = null,

    @SerialName(UserApiFields.SCHEDULED_PERMANENT_DELETION_AT)
    val scheduledPermanentDeletionAt: Long? = null
)