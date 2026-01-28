package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.constants.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.constants.UserApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.useridentifier.UserIdentifierResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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