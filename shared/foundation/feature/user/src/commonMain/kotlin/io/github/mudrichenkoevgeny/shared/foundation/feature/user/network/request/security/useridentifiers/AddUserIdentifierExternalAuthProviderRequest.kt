package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.enums.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.constants.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for linking an external authentication provider to a user account.
 *
 * @property authProvider The identifier of the external service (e.g. [UserAuthProvider]).
 * @property token The authentication token provided by the external service to verify the identity.
 */
@Serializable
data class AddUserIdentifierExternalAuthProviderRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.AUTH_PROVIDER)
    val authProvider: String,

    @NotBlankStringField
    @SerialName(UserApiFields.TOKEN)
    val token: String
)