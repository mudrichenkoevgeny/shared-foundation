package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.AvailableAuthProvidersPayload
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO representing the request to update authentication settings (management).
 *
 * @property availableAuthProviders Provider lists to apply.
 */
@Serializable
data class UpdateAuthSettingsRequest(
    @SerialName(UserApiFields.AVAILABLE_AUTH_PROVIDERS)
    val availableAuthProviders: AvailableAuthProvidersPayload
)