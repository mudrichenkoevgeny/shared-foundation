package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.configuration

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.ManagementSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.ManagementGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.ManagementAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration.ManagementUserConfigurationPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.network.model.securitysettings.createTestManagementSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.settings.network.model.globalsettings.createTestManagementGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.auth.settings.createTestManagementAuthSettingsPayload

/**
 * Creates a test [ManagementUserConfigurationPayload] wire object instance.
 *
 * @param managementGlobalSettings Global settings payload for management ([ManagementGlobalSettingsPayload]).
 * @param managementSecuritySettings Security settings payload for management ([ManagementSecuritySettingsPayload]).
 * @param managementAuthSettings Auth settings payload for management ([ManagementAuthSettingsPayload]).
 */
fun createTestManagementUserConfigurationPayload(
    managementGlobalSettings: ManagementGlobalSettingsPayload = createTestManagementGlobalSettingsPayload(),
    managementSecuritySettings: ManagementSecuritySettingsPayload = createTestManagementSecuritySettingsPayload(),
    managementAuthSettings: ManagementAuthSettingsPayload = createTestManagementAuthSettingsPayload()
) = ManagementUserConfigurationPayload(
    managementGlobalSettings = managementGlobalSettings,
    managementSecuritySettings = managementSecuritySettings,
    managementAuthSettings = managementAuthSettings
)
