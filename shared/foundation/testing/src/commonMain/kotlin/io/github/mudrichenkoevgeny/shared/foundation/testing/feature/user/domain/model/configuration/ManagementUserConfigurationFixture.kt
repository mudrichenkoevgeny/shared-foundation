package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.configuration

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings.ManagementSecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings.ManagementGlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.ManagementAuthSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.configuration.ManagementUserConfiguration
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.securitysettings.createTestManagementSecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.settings.domain.model.globalsettings.createTestManagementGlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.auth.settings.createTestManagementAuthSettings

/**
 * Creates a test [ManagementUserConfiguration] domain instance.
 *
 * @param managementGlobalSettings Global application settings for management ([ManagementGlobalSettings]).
 * @param managementSecuritySettings Security settings for management ([ManagementSecuritySettings]).
 * @param managementAuthSettings Authentication settings for management ([ManagementAuthSettings]).
 */
fun createTestManagementUserConfiguration(
    managementGlobalSettings: ManagementGlobalSettings = createTestManagementGlobalSettings(),
    managementSecuritySettings: ManagementSecuritySettings = createTestManagementSecuritySettings(),
    managementAuthSettings: ManagementAuthSettings = createTestManagementAuthSettings()
) = ManagementUserConfiguration(
    managementGlobalSettings = managementGlobalSettings,
    managementSecuritySettings = managementSecuritySettings,
    managementAuthSettings = managementAuthSettings
)
