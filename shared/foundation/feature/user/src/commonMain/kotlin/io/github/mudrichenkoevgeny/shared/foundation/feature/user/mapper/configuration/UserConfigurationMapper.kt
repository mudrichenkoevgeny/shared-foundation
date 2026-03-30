package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.configuration

import io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.securitysettings.toSecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.securitysettings.toSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.mapper.globalsettings.toGlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.mapper.globalsettings.toGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.configuration.UserConfiguration
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.auth.settings.toAuthSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.auth.settings.toAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration.UserConfigurationPayload

/**
 * Maps between [UserConfigurationPayload] and domain [UserConfiguration].
 *
 * Nested sections use [toGlobalSettings] / [toGlobalSettingsPayload], [toSecuritySettings] / [toSecuritySettingsPayload],
 * and [toAuthSettings] / [toAuthSettingsPayload].
 */

/**
 * Builds domain [UserConfiguration] from [UserConfigurationPayload].
 */
fun UserConfigurationPayload.toUserConfiguration(): UserConfiguration =
    UserConfiguration(
        globalSettings = globalSettings.toGlobalSettings(),
        securitySettings = securitySettings.toSecuritySettings(),
        authSettings = authSettings.toAuthSettings()
    )

/**
 * Builds network [UserConfigurationPayload] from domain [UserConfiguration].
 */
fun UserConfiguration.toUserConfigurationPayload(): UserConfigurationPayload =
    UserConfigurationPayload(
        globalSettings = globalSettings.toGlobalSettingsPayload(),
        securitySettings = securitySettings.toSecuritySettingsPayload(),
        authSettings = authSettings.toAuthSettingsPayload()
    )
