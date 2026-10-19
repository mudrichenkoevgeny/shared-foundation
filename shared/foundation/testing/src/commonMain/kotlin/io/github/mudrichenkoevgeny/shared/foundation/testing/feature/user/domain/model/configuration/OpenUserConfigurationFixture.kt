package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.configuration

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings.OpenSecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings.OpenGlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.OpenAuthSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.configuration.OpenUserConfiguration
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.securitysettings.createTestOpenSecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.settings.domain.model.globalsettings.createTestOpenGlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.auth.settings.createTestOpenAuthSettings

fun createTestOpenUserConfiguration(
    openGlobalSettings: OpenGlobalSettings = createTestOpenGlobalSettings(),
    openSecuritySettings: OpenSecuritySettings = createTestOpenSecuritySettings(),
    openAuthSettings: OpenAuthSettings = createTestOpenAuthSettings()
) = OpenUserConfiguration(
    openGlobalSettings = openGlobalSettings,
    openSecuritySettings = openSecuritySettings,
    openAuthSettings = openAuthSettings
)
