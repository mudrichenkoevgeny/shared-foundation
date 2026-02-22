package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.configuration

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.response.settings.SecuritySettingsResponse
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.response.GlobalSettingsResponse
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.auth.settings.AuthSettingsResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO representing the comprehensive set of user-related configuration.
 *
 * @property globalSettings Global settings.
 * @property securitySettings Security-related settings.
 * @property authSettings Authentication settings.
 */
@Serializable
data class UserConfigurationResponse(
    @SerialName(UserApiFields.GLOBAL_SETTINGS)
    val globalSettings: GlobalSettingsResponse,

    @SerialName(UserApiFields.SECURITY_SETTINGS)
    val securitySettings: SecuritySettingsResponse,

    @SerialName(UserApiFields.AUTH_SETTINGS)
    val authSettings: AuthSettingsResponse
)