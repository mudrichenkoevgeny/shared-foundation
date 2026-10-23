package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.configuration

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings.OpenSecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings.OpenGlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.storagesettings.OpenStorageSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.OpenAuthSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration.OpenUserConfigurationPayload

/**
 * Aggregated open configuration surfaced to ordinary clients in one response.
 *
 * Combines legal/support URLs and related global options ([OpenGlobalSettings]), password and
 * step-up policy from [OpenSecuritySettings], file size limits from [OpenStorageSettings], and the open
 * auth surface ([OpenAuthSettings] — enabled sign-in providers only).
 *
 * Wire shape for typical open configuration fetches: [OpenUserConfigurationPayload].
 *
 * @property openGlobalSettings Legal links, support contact, and other deployment-wide options.
 * @property openSecuritySettings Password policy and recent re-authentication window for sensitive flows.
 * @property openStorageSettings File size upload limits for client-side validation.
 * @property openAuthSettings Which authentication providers are enabled for sign-in.
 */
data class OpenUserConfiguration(
    val openGlobalSettings: OpenGlobalSettings,
    val openSecuritySettings: OpenSecuritySettings,
    val openStorageSettings: OpenStorageSettings,
    val openAuthSettings: OpenAuthSettings
)
