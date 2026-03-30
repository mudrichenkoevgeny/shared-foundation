package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserPublic
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO representing publicly accessible information for any user.
 *
 * @property id The unique identifier of the user.
 * @property role The access level assigned to the user (e.g. [UserRole]).
 * @property accountStatus The current state of the account (e.g. [UserAccountStatus]).
 */
@Serializable
data class UserPublicPayload(
    @SerialName(CommonApiFields.ID)
    val id: String,

    @SerialName(UserApiFields.ROLE)
    val role: String,

    @SerialName(UserApiFields.ACCOUNT_STATUS)
    val accountStatus: String
)