package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Public summary user wire model for list views, mentions, and cards.
 *
 * Wire keys use `snake_case` via [CommonApiFields] and [UserApiFields]. Aligns with domain [UserSummary].
 *
 * @property id [UserSummary.id] on the wire: [UserId] as hex-dash string.
 * @property role [UserSummary.role]; wire values match [UserRole.serialName].
 * @property accountStatus [UserSummary.accountStatus]; wire values match [UserAccountStatus.serialName].
 */
@Serializable
data class UserSummaryPayload(
    @SerialName(CommonApiFields.ID)
    val id: String,

    @SerialName(UserApiFields.ROLE)
    val role: String,

    @SerialName(UserApiFields.ACCOUNT_STATUS)
    val accountStatus: String
)
