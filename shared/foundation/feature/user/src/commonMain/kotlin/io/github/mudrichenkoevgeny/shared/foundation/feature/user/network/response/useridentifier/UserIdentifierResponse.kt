package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.useridentifier

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.constants.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.constants.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserIdentifierResponse(
    @SerialName(CommonApiFields.ID)
    val id: String,

    @SerialName(UserApiFields.USER_ID)
    val userId: String,

    @SerialName(UserApiFields.USER_AUTH_PROVIDER)
    val userAuthProvider: String,

    @SerialName(UserApiFields.IDENTIFIER)
    val identifier: String,

    @SerialName(CommonApiFields.CREATED_AT)
    val createdAt: Long,

    @SerialName(CommonApiFields.UPDATED_AT)
    val updatedAt: Long? = null
)