package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO representing categories of available authentication providers.
 *
 * @property primary A list of main authentication methods. Values correspond to [UserAuthProvider.serialName].
 * @property secondary A list of alternative authentication methods. Values correspond to [UserAuthProvider.serialName].
 */
@Serializable
data class AvailableAuthProvidersResponse(
    @SerialName(UserApiFields.PRIMARY)
    val primary: List<String>,

    @SerialName(UserApiFields.SECONDARY)
    val secondary: List<String>
)