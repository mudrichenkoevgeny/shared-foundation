package io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.securitysettings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings.SecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.passwordpolicy.toPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.passwordpolicy.toPasswordPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.SecuritySettingsPayload

/**
 * Maps between [SecuritySettingsPayload] and domain [SecuritySettings].
 * [SecuritySettings.recentAuthenticationValidityInMinutes] is copied as-is;
 * [SecuritySettings.passwordPolicy] uses [toPasswordPolicy] and [toPasswordPolicyPayload].
 */

/**
 * Builds domain [SecuritySettings] from [SecuritySettingsPayload].
 */
fun SecuritySettingsPayload.toSecuritySettings() = SecuritySettings(
    recentAuthenticationValidityInMinutes = recentAuthenticationValidityInMinutes,
    passwordPolicy = passwordPolicy.toPasswordPolicy()
)

/**
 * Builds network [SecuritySettingsPayload] from domain [SecuritySettings].
 */
fun SecuritySettings.toSecuritySettingsPayload() = SecuritySettingsPayload(
    recentAuthenticationValidityInMinutes = recentAuthenticationValidityInMinutes,
    passwordPolicy = passwordPolicy.toPasswordPolicyPayload()
)