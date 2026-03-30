package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.configuration

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings.SecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings.GlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.PublicAuthSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration.UserConfigurationPayload

/**
 * Aggregated **public** configuration surfaced to ordinary clients in one response.
 *
 * Combines legal/support URLs and related global options ([GlobalSettings]), password and
 * step-up policy from [SecuritySettings], and the public auth surface ([PublicAuthSettings] —
 * enabled sign-in providers only, not management-only fields such as token lifetimes).
 *
 * Wire shape for typical open configuration fetches: [UserConfigurationPayload].
 *
 * @property globalSettings Legal links, support contact, and other deployment-wide options.
 * @property securitySettings Password policy and recent re-authentication window for sensitive flows.
 * @property authSettings Which authentication providers are enabled for sign-in.
 */
data class UserConfiguration(
    val globalSettings: GlobalSettings,
    val securitySettings: SecuritySettings,
    val authSettings: PublicAuthSettings
)
