package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.OpenSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.OpenGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.OpenAuthSettingsPayload
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO representing the comprehensive set of user-related configuration.
 *
 * @property openGlobalSettings Global settings.
 * @property openSecuritySettings Security-related settings.
 * @property openAuthSettings Authentication settings.
 */
@Serializable
data class OpenUserConfigurationPayload(
    @SerialName(UserApiFields.OPEN_GLOBAL_SETTINGS)
    val openGlobalSettings: OpenGlobalSettingsPayload,

    @SerialName(UserApiFields.OPEN_SECURITY_SETTINGS)
    val openSecuritySettings: OpenSecuritySettingsPayload,

    @SerialName(UserApiFields.OPEN_AUTH_SETTINGS)
    val openAuthSettings: OpenAuthSettingsPayload
)
