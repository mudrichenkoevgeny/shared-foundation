package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.ManagementSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.ManagementGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.configuration.ManagementUserConfiguration
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.ManagementAuthSettingsPayload
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wire DTO representing aggregated management user configuration, aligned with [ManagementUserConfiguration].
 *
 * @property managementGlobalSettings Management global settings payload ([ManagementGlobalSettingsPayload]).
 * @property managementSecuritySettings Management security settings payload ([ManagementSecuritySettingsPayload]).
 * @property managementAuthSettings Management authentication settings payload ([ManagementAuthSettingsPayload]).
 */
@Serializable
data class ManagementUserConfigurationPayload(
    @SerialName(UserApiFields.MANAGEMENT_GLOBAL_SETTINGS)
    val managementGlobalSettings: ManagementGlobalSettingsPayload,

    @SerialName(UserApiFields.MANAGEMENT_SECURITY_SETTINGS)
    val managementSecuritySettings: ManagementSecuritySettingsPayload,

    @SerialName(UserApiFields.MANAGEMENT_AUTH_SETTINGS)
    val managementAuthSettings: ManagementAuthSettingsPayload
)
