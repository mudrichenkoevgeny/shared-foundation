package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.ManagementAuthSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.ManagementAuthSettingsPayload

/**
 * Maps between [ManagementAuthSettingsPayload] and domain [ManagementAuthSettings].
 *
 * Nested [ManagementAuthSettings.availableAuthProviders] uses [toAvailableAuthProviders] and [toAvailableAuthProvidersPayload].
 */

/**
 * Builds domain [ManagementAuthSettings] from [ManagementAuthSettingsPayload].
 */
fun ManagementAuthSettingsPayload.toManagementAuthSettings(): ManagementAuthSettings =
    ManagementAuthSettings(
        availableAuthProviders = availableAuthProviders.toAvailableAuthProviders(),
        maxTotalIdentifiers = maxTotalIdentifiers,
        maxEmailIdentifiers = maxEmailIdentifiers,
        maxPhoneIdentifiers = maxPhoneIdentifiers,
        maxIdentifiersPerExternalProvider = maxIdentifiersPerExternalProvider,
        maxActiveSessions = maxActiveSessions,
        accessTokenExpirationSeconds = accessTokenExpirationSeconds,
        refreshTokenExpirationSeconds = refreshTokenExpirationSeconds,
        accountDeletionDelaySeconds = accountDeletionDelaySeconds
    )

/**
 * Builds network [ManagementAuthSettingsPayload] from domain [ManagementAuthSettings].
 */
fun ManagementAuthSettings.toManagementAuthSettingsPayload(): ManagementAuthSettingsPayload =
    ManagementAuthSettingsPayload(
        availableAuthProviders = availableAuthProviders.toAvailableAuthProvidersPayload(),
        maxTotalIdentifiers = maxTotalIdentifiers,
        maxEmailIdentifiers = maxEmailIdentifiers,
        maxPhoneIdentifiers = maxPhoneIdentifiers,
        maxIdentifiersPerExternalProvider = maxIdentifiersPerExternalProvider,
        maxActiveSessions = maxActiveSessions,
        accessTokenExpirationSeconds = accessTokenExpirationSeconds,
        refreshTokenExpirationSeconds = refreshTokenExpirationSeconds,
        accountDeletionDelaySeconds = accountDeletionDelaySeconds
    )