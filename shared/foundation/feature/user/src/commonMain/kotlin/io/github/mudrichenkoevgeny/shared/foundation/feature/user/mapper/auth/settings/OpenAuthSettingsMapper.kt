package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.OpenAuthSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.OpenAuthSettingsPayload

/**
 * Maps between [OpenAuthSettingsPayload] and domain [OpenAuthSettings].
 *
 * Nested [OpenAuthSettings.availableAuthProviders] uses [toAvailableAuthProviders] and [toAvailableAuthProvidersPayload].
 */

/**
 * Builds domain [OpenAuthSettings] from [OpenAuthSettingsPayload].
 */
fun OpenAuthSettingsPayload.toOpenAuthSettings(): OpenAuthSettings =
    OpenAuthSettings(
        availableAuthProviders = availableAuthProviders.toAvailableAuthProviders(),
        maxTotalIdentifiers = maxTotalIdentifiers,
        maxEmailIdentifiers = maxEmailIdentifiers,
        maxPhoneIdentifiers = maxPhoneIdentifiers,
        maxIdentifiersPerExternalProvider = maxIdentifiersPerExternalProvider,
        isRegistrationEnabled = isRegistrationEnabled
    )

/**
 * Builds network [OpenAuthSettingsPayload] from domain [OpenAuthSettings].
 */
fun OpenAuthSettings.toOpenAuthSettingsPayload(): OpenAuthSettingsPayload =
    OpenAuthSettingsPayload(
        availableAuthProviders = availableAuthProviders.toAvailableAuthProvidersPayload(),
        maxTotalIdentifiers = maxTotalIdentifiers,
        maxEmailIdentifiers = maxEmailIdentifiers,
        maxPhoneIdentifiers = maxPhoneIdentifiers,
        maxIdentifiersPerExternalProvider = maxIdentifiersPerExternalProvider,
        isRegistrationEnabled = isRegistrationEnabled
    )
