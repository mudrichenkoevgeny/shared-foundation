package io.github.mudrichenkoevgeny.shared.foundation.core.settings.mapper.globalsettings

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings.OpenGlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.OpenGlobalSettingsPayload

/**
 * Maps [OpenGlobalSettingsPayload] DTO to [OpenGlobalSettings] domain model.
 */
public fun OpenGlobalSettingsPayload.toOpenGlobalSettings(): OpenGlobalSettings = OpenGlobalSettings(
    privacyPolicyUrl = privacyPolicyUrl,
    termsOfServiceUrl = termsOfServiceUrl,
    contactSupportEmail = contactSupportEmail,
    minSupportedAppVersions = minSupportedAppVersions.mapKeys { ClientType.fromValueOrThrow(it.key) }
)

/**
 * Maps [OpenGlobalSettings] domain model to [OpenGlobalSettingsPayload] DTO.
 */
public fun OpenGlobalSettings.toOpenGlobalSettingsPayload(): OpenGlobalSettingsPayload = OpenGlobalSettingsPayload(
    privacyPolicyUrl = privacyPolicyUrl,
    termsOfServiceUrl = termsOfServiceUrl,
    contactSupportEmail = contactSupportEmail,
    minSupportedAppVersions = minSupportedAppVersions.mapKeys { it.key.value }
)
