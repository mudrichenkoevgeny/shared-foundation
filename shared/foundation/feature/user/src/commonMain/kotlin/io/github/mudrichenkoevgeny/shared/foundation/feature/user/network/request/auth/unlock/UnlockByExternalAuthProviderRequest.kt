package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for unlocking a temporarily locked account via an external authentication provider.
 *
 * @property authProvider The identifier of the external provider (e.g., `google`, `apple`).
 * @property externalProviderToken The opaque token provided by the external identity provider.
 */
@Serializable
data class UnlockByExternalAuthProviderRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.AUTH_PROVIDER)
    val authProvider: String,

    @NotBlankStringField
    @SerialName(UserApiFields.EXTERNAL_PROVIDER_TOKEN)
    val externalProviderToken: String
)
