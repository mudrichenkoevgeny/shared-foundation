package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.PublicAuthSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.PublicAuthSettingsPayload

/**
 * Maps between [PublicAuthSettingsPayload] and domain [PublicAuthSettings].
 *
 * Nested [PublicAuthSettings.availableAuthProviders] uses [toAvailableAuthProviders] and [toAvailableAuthProvidersPayload].
 */

/**
 * Builds domain [PublicAuthSettings] from [PublicAuthSettingsPayload].
 */
fun PublicAuthSettingsPayload.toAuthSettings(): PublicAuthSettings =
    PublicAuthSettings(
        availableAuthProviders = availableAuthProviders.toAvailableAuthProviders()
    )

/**
 * Builds network [PublicAuthSettingsPayload] from domain [PublicAuthSettings].
 */
fun PublicAuthSettings.toAuthSettingsPayload(): PublicAuthSettingsPayload =
    PublicAuthSettingsPayload(
        availableAuthProviders = availableAuthProviders.toAvailableAuthProvidersPayload()
    )
