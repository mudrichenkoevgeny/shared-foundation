package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.configuration

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings.ManagementSecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings.ManagementGlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.storagesettings.ManagementStorageSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.ManagementAuthSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration.ManagementUserConfigurationPayload

/**
 * Aggregated management configuration surfaced to staff and admin workflows.
 *
 * Wire shape for management configuration fetches: [ManagementUserConfigurationPayload].
 *
 * @property managementGlobalSettings Management global settings.
 * @property managementSecuritySettings Management security settings.
 * @property managementStorageSettings Management storage settings.
 * @property managementAuthSettings Management authentication settings.
 */
public data class ManagementUserConfiguration(
    public val managementGlobalSettings: ManagementGlobalSettings,
    public val managementSecuritySettings: ManagementSecuritySettings,
    public val managementStorageSettings: ManagementStorageSettings,
    public val managementAuthSettings: ManagementAuthSettings
)
