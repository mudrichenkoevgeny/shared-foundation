package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wire body for updating authentication settings via the **management** API.
 *
 * @property availableAuthProviders Provider lists to apply; primary/secondary entries use [UserAuthProvider.serialName] on the wire.
 * @property accessTokenValidityHours Whole hours for **new** access token validity.
 * @property refreshTokenValidityDays Whole days for **new** refresh token validity.
 */
@Serializable
data class ManagementAuthSettingsPayload(
    @SerialName(UserApiFields.AVAILABLE_AUTH_PROVIDERS)
    val availableAuthProviders: AvailableAuthProvidersPayload,

    @SerialName(UserApiFields.ACCESS_TOKEN_VALIDITY_HOURS)
    val accessTokenValidityHours: Long,

    @SerialName(UserApiFields.REFRESH_TOKEN_VALIDITY_DAYS)
    val refreshTokenValidityDays: Long
)
