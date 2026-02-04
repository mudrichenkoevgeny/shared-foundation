package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.constants.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.constants.UserApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.useridentifier.UserIdentifierResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO representing a complete user profile and account state.
 *
 * @property id The unique identifier of the user.
 * @property role The access level assigned to the user (e.g. [UserRole]).
 * @property accountStatus The current state of the account (e.g. [UserAccountStatus]).
 * @property userIdentifiers The list of [UserIdentifierResponse] objects linked to the account.
 * @property lastLoginAt The timestamp of the most recent successful authentication, in milliseconds.
 * @property lastActiveAt The timestamp of the last recorded user activity, in milliseconds.
 * @property createdAt The timestamp of the account creation, in milliseconds.
 * @property updatedAt The timestamp of the last account profile modification, in milliseconds.
 */
@Serializable
data class UserResponse(
    @SerialName(CommonApiFields.ID)
    val id: String,

    @SerialName(UserApiFields.ROLE)
    val role: String,

    @SerialName(UserApiFields.ACCOUNT_STATUS)
    val accountStatus: String,

    @SerialName(UserApiFields.USER_IDENTIFIERS)
    val userIdentifiers: List<UserIdentifierResponse>,

    @SerialName(UserApiFields.LAST_LOGIN_AT)
    val lastLoginAt: Long? = null,

    @SerialName(UserApiFields.LAST_ACTIVE_AT)
    val lastActiveAt: Long? = null,

    @SerialName(CommonApiFields.CREATED_AT)
    val createdAt: Long,

    @SerialName(CommonApiFields.UPDATED_AT)
    val updatedAt: Long? = null
)