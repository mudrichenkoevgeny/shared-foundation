package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wire body for updating authentication settings via the **management** API.
 *
 * @property availableAuthProviders Provider lists; wire values follow [UserAuthProvider.serialName].
 * @property maxTotalIdentifiers Maximum number of identifiers of any type allowed per account.
 * @property maxEmailIdentifiers Maximum number of email-based identifiers allowed per account.
 * @property maxPhoneIdentifiers Maximum number of phone-based identifiers allowed per account.
 * @property maxIdentifiersPerExternalProvider Maximum number of identifiers allowed for each
 * unique external provider per account.
 * @property maxActiveSessions Maximum number of active sessions allowed per account.
 * @property accessTokenValidityHours Whole hours for **new** access token validity.
 * @property refreshTokenValidityDays Whole days for **new** refresh token validity.
 */
@Serializable
data class ManagementAuthSettingsPayload(
    @SerialName(UserApiFields.AVAILABLE_AUTH_PROVIDERS)
    val availableAuthProviders: AvailableAuthProvidersPayload,

    @SerialName(UserApiFields.MAX_TOTAL_IDENTIFIERS)
    val maxTotalIdentifiers: Int,

    @SerialName(UserApiFields.MAX_EMAIL_IDENTIFIERS)
    val maxEmailIdentifiers: Int,

    @SerialName(UserApiFields.MAX_PHONE_IDENTIFIERS)
    val maxPhoneIdentifiers: Int,

    @SerialName(UserApiFields.MAX_IDENTIFIERS_PER_EXTERNAL_PROVIDER)
    val maxIdentifiersPerExternalProvider: Int,

    @SerialName(UserApiFields.MAX_ACTIVE_SESSIONS)
    val maxActiveSessions: Int,

    @SerialName(UserApiFields.ACCESS_TOKEN_VALIDITY_HOURS)
    val accessTokenValidityHours: Long,

    @SerialName(UserApiFields.REFRESH_TOKEN_VALIDITY_DAYS)
    val refreshTokenValidityDays: Long
)