package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.configuration

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.OpenSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.OpenGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.OpenAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration.OpenUserConfigurationPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.network.model.securitysettings.createTestOpenSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.settings.network.model.globalsettings.createTestOpenGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.auth.settings.createTestOpenAuthSettingsPayload

fun createTestOpenUserConfigurationPayload(
    openGlobalSettings: OpenGlobalSettingsPayload = createTestOpenGlobalSettingsPayload(),
    openSecuritySettings: OpenSecuritySettingsPayload = createTestOpenSecuritySettingsPayload(),
    openAuthSettings: OpenAuthSettingsPayload = createTestOpenAuthSettingsPayload()
) = OpenUserConfigurationPayload(
    openGlobalSettings = openGlobalSettings,
    openSecuritySettings = openSecuritySettings,
    openAuthSettings = openAuthSettings
)
