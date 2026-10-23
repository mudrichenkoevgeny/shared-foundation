package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.configuration

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings.OpenSecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings.OpenGlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.storagesettings.OpenStorageSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.OpenAuthSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.configuration.OpenUserConfiguration
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.securitysettings.createTestOpenSecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.settings.domain.model.globalsettings.createTestOpenGlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.storage.domain.model.storagesettings.createTestOpenStorageSettings
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.auth.settings.createTestOpenAuthSettings

/**
 * Creates a test [OpenUserConfiguration] domain instance.
 *
 * @param openGlobalSettings Global application settings for open clients ([OpenGlobalSettings]).
 * @param openSecuritySettings Security settings for open clients ([OpenSecuritySettings]).
 * @param openStorageSettings Storage settings for open clients ([OpenStorageSettings]).
 * @param openAuthSettings Authentication settings for open clients ([OpenAuthSettings]).
 */
fun createTestOpenUserConfiguration(
    openGlobalSettings: OpenGlobalSettings = createTestOpenGlobalSettings(),
    openSecuritySettings: OpenSecuritySettings = createTestOpenSecuritySettings(),
    openStorageSettings: OpenStorageSettings = createTestOpenStorageSettings(),
    openAuthSettings: OpenAuthSettings = createTestOpenAuthSettings()
) = OpenUserConfiguration(
    openGlobalSettings = openGlobalSettings,
    openSecuritySettings = openSecuritySettings,
    openStorageSettings = openStorageSettings,
    openAuthSettings = openAuthSettings
)
