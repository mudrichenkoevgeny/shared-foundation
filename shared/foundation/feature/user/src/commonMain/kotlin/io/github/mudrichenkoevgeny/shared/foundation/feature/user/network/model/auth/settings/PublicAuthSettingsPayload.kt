package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wire DTO for the **public** auth-settings slice (enabled providers only).
 *
 * @property availableAuthProviders Provider lists; wire values follow [UserAuthProvider.serialName].
 */
@Serializable
data class PublicAuthSettingsPayload(
    @SerialName(UserApiFields.AVAILABLE_AUTH_PROVIDERS)
    val availableAuthProviders: AvailableAuthProvidersPayload
)