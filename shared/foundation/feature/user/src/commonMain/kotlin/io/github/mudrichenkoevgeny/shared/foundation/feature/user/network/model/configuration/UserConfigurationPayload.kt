package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.SecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.GlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.PublicAuthSettingsPayload
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
data class UserConfigurationPayload(
    @SerialName(UserApiFields.GLOBAL_SETTINGS)
    val globalSettings: GlobalSettingsPayload,

    @SerialName(UserApiFields.SECURITY_SETTINGS)
    val securitySettings: SecuritySettingsPayload,

    @SerialName(UserApiFields.AUTH_SETTINGS)
    val authSettings: PublicAuthSettingsPayload
)