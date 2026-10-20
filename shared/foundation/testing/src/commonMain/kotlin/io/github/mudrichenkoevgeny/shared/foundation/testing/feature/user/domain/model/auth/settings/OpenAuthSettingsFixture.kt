package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.AvailableAuthProviders
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.OpenAuthSettings

/**
 * Creates a test [OpenAuthSettings] domain instance.
 *
 * @param availableAuthProviders List of supported authentication providers ([AvailableAuthProviders]).
 * @param maxTotalIdentifiers Maximum allowed identifiers per account.
 * @param maxEmailIdentifiers Maximum allowed email identifiers per account.
 * @param maxPhoneIdentifiers Maximum allowed phone identifiers per account.
 * @param maxIdentifiersPerExternalProvider Maximum identifiers per OAuth provider.
 * @param isRegistrationEnabled Flag indicating whether public user registration is open.
 */
fun createTestOpenAuthSettings(
    availableAuthProviders: AvailableAuthProviders = createTestAvailableAuthProviders(),
    maxTotalIdentifiers: Int = 10,
    maxEmailIdentifiers: Int = 5,
    maxPhoneIdentifiers: Int = 5,
    maxIdentifiersPerExternalProvider: Int = 1,
    isRegistrationEnabled: Boolean = true
) = OpenAuthSettings(
    availableAuthProviders = availableAuthProviders,
    maxTotalIdentifiers = maxTotalIdentifiers,
    maxEmailIdentifiers = maxEmailIdentifiers,
    maxPhoneIdentifiers = maxPhoneIdentifiers,
    maxIdentifiersPerExternalProvider = maxIdentifiersPerExternalProvider,
    isRegistrationEnabled = isRegistrationEnabled
)
