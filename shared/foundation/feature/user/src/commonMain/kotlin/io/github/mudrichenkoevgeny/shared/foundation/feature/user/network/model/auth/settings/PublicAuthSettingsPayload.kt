package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wire DTO for the **public** auth-settings slice.
 *
 * @property availableAuthProviders Provider lists; wire values follow [UserAuthProvider.serialName].
 * @property maxTotalIdentifiers Maximum number of identifiers of any type allowed per account.
 * @property maxEmailIdentifiers Maximum number of email-based identifiers allowed per account.
 * @property maxPhoneIdentifiers Maximum number of phone-based identifiers allowed per account.
 * @property maxIdentifiersPerExternalProvider Maximum number of identifiers allowed for each
 * unique external provider per account.
 */
@Serializable
data class PublicAuthSettingsPayload(
    @SerialName(UserApiFields.AVAILABLE_AUTH_PROVIDERS)
    val availableAuthProviders: AvailableAuthProvidersPayload,

    @SerialName(UserApiFields.MAX_TOTAL_IDENTIFIERS)
    val maxTotalIdentifiers: Int,

    @SerialName(UserApiFields.MAX_EMAIL_IDENTIFIERS)
    val maxEmailIdentifiers: Int,

    @SerialName(UserApiFields.MAX_PHONE_IDENTIFIERS)
    val maxPhoneIdentifiers: Int,

    @SerialName(UserApiFields.MAX_IDENTIFIERS_PER_EXTERNAL_PROVIDER)
    val maxIdentifiersPerExternalProvider: Int
)