package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO representing the authentication configuration, including supported login methods.
 *
 * @property availableAuthProviders The collection of primary and secondary authentication methods available for the user.
 */
@Serializable
data class AuthSettingsResponse(
    @SerialName(UserApiFields.AVAILABLE_AUTH_PROVIDERS)
    val availableAuthProviders: AvailableAuthProvidersResponse
)