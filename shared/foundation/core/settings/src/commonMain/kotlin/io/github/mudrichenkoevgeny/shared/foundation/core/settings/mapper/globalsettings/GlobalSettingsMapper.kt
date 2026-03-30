package io.github.mudrichenkoevgeny.shared.foundation.core.settings.mapper.globalsettings

import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings.GlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.GlobalSettingsPayload

/**
 * Extension functions mapping global settings between domain and network models.
 */

/**
 * Builds domain [GlobalSettings] from [GlobalSettingsPayload].
 *
 * @return domain model aligned with [GlobalSettings] semantics.
 */
fun GlobalSettingsPayload.toGlobalSettings(): GlobalSettings = GlobalSettings(
    privacyPolicyUrl = privacyPolicyUrl,
    termsOfServiceUrl = termsOfServiceUrl,
    contactSupportEmail = contactSupportEmail
)

/**
 * Builds network [GlobalSettingsPayload] from domain [GlobalSettings].
 *
 * @return payload model aligned with the shared [GlobalSettingsPayload] contract.
 */
fun GlobalSettings.toGlobalSettingsPayload(): GlobalSettingsPayload = GlobalSettingsPayload(
    privacyPolicyUrl = privacyPolicyUrl,
    termsOfServiceUrl = termsOfServiceUrl,
    contactSupportEmail = contactSupportEmail
)