package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.client.auth.register.email

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the client registration screen.
 *
 * @property email Prefilled email address for registration; wire key is [UserApiFields.EMAIL].
 */
@Serializable
data class ClientRegistrationByEmailParams(
    val email: String? = null
) : NavigationScreenParams {

    override fun toQueryMap(): Map<String, List<String>> =
        ClientRegistrationByEmailParamsParser.serialize(this)

    companion object {
        fun parse(params: Map<String, Any?>): ClientRegistrationByEmailParams =
            ClientRegistrationByEmailParamsParser.parse(params)
    }
}
