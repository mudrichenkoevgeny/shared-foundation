package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.OpenSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.OpenGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings.OpenStorageSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.OpenAuthSettingsPayload
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO representing the global system configuration for the user domain.
 *
 * @property openGlobalSettings Global settings.
 * @property openSecuritySettings Security-related settings.
 * @property openStorageSettings Storage-related upload settings.
 * @property openAuthSettings Authentication settings.
 */
@Serializable
public data class OpenUserConfigurationPayload(
    @SerialName(UserApiFields.OPEN_GLOBAL_SETTINGS)
    public val openGlobalSettings: OpenGlobalSettingsPayload,
    @SerialName(UserApiFields.OPEN_SECURITY_SETTINGS)
    public val openSecuritySettings: OpenSecuritySettingsPayload,
    @SerialName(UserApiFields.OPEN_STORAGE_SETTINGS)
    public val openStorageSettings: OpenStorageSettingsPayload,
    @SerialName(UserApiFields.OPEN_AUTH_SETTINGS)
    public val openAuthSettings: OpenAuthSettingsPayload
)
