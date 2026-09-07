package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.configuration

import io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.securitysettings.toManagementSecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.securitysettings.toManagementSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.mapper.globalsettings.toManagementGlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.mapper.globalsettings.toManagementGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.configuration.ManagementUserConfiguration
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.auth.settings.toManagementAuthSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.auth.settings.toManagementAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration.ManagementUserConfigurationPayload

/**
 * Maps between [ManagementUserConfigurationPayload] and domain [ManagementUserConfiguration].
 */

/**
 * Builds domain [ManagementUserConfiguration] from [ManagementUserConfigurationPayload].
 */
fun ManagementUserConfigurationPayload.toManagementUserConfiguration(): ManagementUserConfiguration =
    ManagementUserConfiguration(
        managementGlobalSettings = managementGlobalSettings.toManagementGlobalSettings(),
        managementSecuritySettings = managementSecuritySettings.toManagementSecuritySettings(),
        managementAuthSettings = managementAuthSettings.toManagementAuthSettings()
    )

/**
 * Builds network [ManagementUserConfigurationPayload] from domain [ManagementUserConfiguration].
 */
fun ManagementUserConfiguration.toManagementUserConfigurationPayload(): ManagementUserConfigurationPayload =
    ManagementUserConfigurationPayload(
        managementGlobalSettings = managementGlobalSettings.toManagementGlobalSettingsPayload(),
        managementSecuritySettings = managementSecuritySettings.toManagementSecuritySettingsPayload(),
        managementAuthSettings = managementAuthSettings.toManagementAuthSettingsPayload()
    )
