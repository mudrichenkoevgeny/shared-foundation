package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.configuration

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.OpenSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.OpenGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings.OpenStorageSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.OpenAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration.OpenUserConfigurationPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.network.model.securitysettings.createTestOpenSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.settings.network.model.globalsettings.createTestOpenGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.storage.network.model.storagesettings.createTestOpenStorageSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.auth.settings.createTestOpenAuthSettingsPayload

/**
 * Creates a test [OpenUserConfigurationPayload] wire object instance.
 *
 * @param openGlobalSettings Global settings payload for open clients ([OpenGlobalSettingsPayload]).
 * @param openSecuritySettings Security settings payload for open clients ([OpenSecuritySettingsPayload]).
 * @param openStorageSettings Storage settings payload for open clients ([OpenStorageSettingsPayload]).
 * @param openAuthSettings Auth settings payload for open clients ([OpenAuthSettingsPayload]).
 */
fun createTestOpenUserConfigurationPayload(
    openGlobalSettings: OpenGlobalSettingsPayload = createTestOpenGlobalSettingsPayload(),
    openSecuritySettings: OpenSecuritySettingsPayload = createTestOpenSecuritySettingsPayload(),
    openStorageSettings: OpenStorageSettingsPayload = createTestOpenStorageSettingsPayload(),
    openAuthSettings: OpenAuthSettingsPayload = createTestOpenAuthSettingsPayload()
) = OpenUserConfigurationPayload(
    openGlobalSettings = openGlobalSettings,
    openSecuritySettings = openSecuritySettings,
    openStorageSettings = openStorageSettings,
    openAuthSettings = openAuthSettings
)
