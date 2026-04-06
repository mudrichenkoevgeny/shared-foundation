package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request body for setting a user's account status (management API).
 *
 * @property accountStatus Target status as a [UserAccountStatus] serial name on the wire (for example
 * [UserAccountStatus.STATUS_ACTIVE], [UserAccountStatus.STATUS_READ_ONLY]).
 */
@Serializable
data class UpdateUserAccountStatusRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.ACCOUNT_STATUS)
    val accountStatus: String
)
