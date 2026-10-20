package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.AvailableAuthProvidersPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.OpenAuthSettingsPayload

/**
 * Creates a test [OpenAuthSettingsPayload] wire object instance.
 *
 * @param availableAuthProviders List of supported auth providers payload ([AvailableAuthProvidersPayload]).
 * @param maxTotalIdentifiers Maximum allowed identifiers per account.
 * @param maxEmailIdentifiers Maximum allowed email identifiers per account.
 * @param maxPhoneIdentifiers Maximum allowed phone identifiers per account.
 * @param maxIdentifiersPerExternalProvider Maximum identifiers per OAuth provider.
 * @param isRegistrationEnabled Flag indicating whether public user registration is open.
 */
fun createTestOpenAuthSettingsPayload(
    availableAuthProviders: AvailableAuthProvidersPayload = createTestAvailableAuthProvidersPayload(),
    maxTotalIdentifiers: Int = 10,
    maxEmailIdentifiers: Int = 5,
    maxPhoneIdentifiers: Int = 5,
    maxIdentifiersPerExternalProvider: Int = 1,
    isRegistrationEnabled: Boolean = true
) = OpenAuthSettingsPayload(
    availableAuthProviders = availableAuthProviders,
    maxTotalIdentifiers = maxTotalIdentifiers,
    maxEmailIdentifiers = maxEmailIdentifiers,
    maxPhoneIdentifiers = maxPhoneIdentifiers,
    maxIdentifiersPerExternalProvider = maxIdentifiersPerExternalProvider,
    isRegistrationEnabled = isRegistrationEnabled
)
