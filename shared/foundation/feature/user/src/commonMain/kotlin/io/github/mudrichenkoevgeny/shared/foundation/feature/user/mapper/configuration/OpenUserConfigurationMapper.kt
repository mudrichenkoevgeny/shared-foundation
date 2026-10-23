package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.configuration

import io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.securitysettings.toOpenSecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.securitysettings.toOpenSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.mapper.globalsettings.toOpenGlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.mapper.globalsettings.toOpenGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.mapper.storagesettings.toOpenStorageSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.mapper.storagesettings.toOpenStorageSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.configuration.OpenUserConfiguration
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.auth.settings.toOpenAuthSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.auth.settings.toOpenAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration.OpenUserConfigurationPayload

/**
 * Maps between [OpenUserConfigurationPayload] and domain [OpenUserConfiguration].
 */

/**
 * Builds domain [OpenUserConfiguration] from [OpenUserConfigurationPayload].
 */
fun OpenUserConfigurationPayload.toOpenUserConfiguration(): OpenUserConfiguration =
    OpenUserConfiguration(
        openGlobalSettings = openGlobalSettings.toOpenGlobalSettings(),
        openSecuritySettings = openSecuritySettings.toOpenSecuritySettings(),
        openStorageSettings = openStorageSettings.toOpenStorageSettings(),
        openAuthSettings = openAuthSettings.toOpenAuthSettings()
    )

/**
 * Builds network [OpenUserConfigurationPayload] from domain [OpenUserConfiguration].
 */
fun OpenUserConfiguration.toOpenUserConfigurationPayload(): OpenUserConfigurationPayload =
    OpenUserConfigurationPayload(
        openGlobalSettings = openGlobalSettings.toOpenGlobalSettingsPayload(),
        openSecuritySettings = openSecuritySettings.toOpenSecuritySettingsPayload(),
        openStorageSettings = openStorageSettings.toOpenStorageSettingsPayload(),
        openAuthSettings = openAuthSettings.toOpenAuthSettingsPayload()
    )
