package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.ManagementSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.ManagementGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.ManagementAuthSettingsPayload
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ManagementUserConfigurationPayload(
    @SerialName(UserApiFields.MANAGEMENT_GLOBAL_SETTINGS)
    val managementGlobalSettings: ManagementGlobalSettingsPayload,

    @SerialName(UserApiFields.MANAGEMENT_SECURITY_SETTINGS)
    val managementSecuritySettings: ManagementSecuritySettingsPayload,

    @SerialName(UserApiFields.MANAGEMENT_AUTH_SETTINGS)
    val managementAuthSettings: ManagementAuthSettingsPayload
)
