package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for user authentication via external providers (e.g. Google or Apple).
 *
 * @property authProvider The identifier of the external service (e.g. [UserAuthProvider]).
 * @property externalProviderToken The authentication token provided by the external service.
 * @property displayName Optional display name captured during initial provider sign-in.
 */
@Serializable
data class LoginByExternalAuthProviderRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.AUTH_PROVIDER)
    val authProvider: String,

    @NotBlankStringField
    @SerialName(UserApiFields.EXTERNAL_PROVIDER_TOKEN)
    val externalProviderToken: String,

    @SerialName(UserApiFields.DISPLAY_NAME)
    val displayName: String? = null
)
