package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Condensed credential wire payload for list views and login method badges.
 *
 * Wire keys use `snake_case` via [CommonApiFields] and [UserApiFields]. Aligns with domain [UserIdentifierSummary].
 *
 * @property id [UserIdentifierSummary.id] on the wire: [UserIdentifierId] as hex-dash string.
 * @property userAuthProvider [UserIdentifierSummary.userAuthProvider]; wire values match [UserAuthProvider.serialName].
 * @property identifier [UserIdentifierSummary.identifier].
 * @property displayName [UserIdentifierSummary.displayName].
 */
@Serializable
data class UserIdentifierSummaryPayload(
    @SerialName(CommonApiFields.ID)
    val id: String,

    @SerialName(UserApiFields.USER_AUTH_PROVIDER)
    val userAuthProvider: String,

    @SerialName(UserApiFields.IDENTIFIER)
    val identifier: String,

    @SerialName(UserApiFields.DISPLAY_NAME)
    val displayName: String
)
