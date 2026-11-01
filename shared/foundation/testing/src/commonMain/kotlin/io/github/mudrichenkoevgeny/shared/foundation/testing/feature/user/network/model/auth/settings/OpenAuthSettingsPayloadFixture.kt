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
 * @param userNicknameMinLength Minimum character length for user nicknames.
 * @param userNicknameMaxLength Maximum character length for user nicknames.
 * @param userDisplayNameMinLength Minimum character length for user display names.
 * @param userDisplayNameMaxLength Maximum character length for user display names.
 * @param isUserNicknameChangeAllowed Flag indicating whether users are permitted to modify their nickname.
 * @param isRegistrationEnabled Flag indicating whether public user registration is open.
 */
fun createTestOpenAuthSettingsPayload(
    availableAuthProviders: AvailableAuthProvidersPayload = createTestAvailableAuthProvidersPayload(),
    maxTotalIdentifiers: Int = 10,
    maxEmailIdentifiers: Int = 5,
    maxPhoneIdentifiers: Int = 5,
    maxIdentifiersPerExternalProvider: Int = 1,
    userNicknameMinLength: Int = 3,
    userNicknameMaxLength: Int = 30,
    userDisplayNameMinLength: Int = 1,
    userDisplayNameMaxLength: Int = 50,
    isUserNicknameChangeAllowed: Boolean = true,
    isRegistrationEnabled: Boolean = true
) = OpenAuthSettingsPayload(
    availableAuthProviders = availableAuthProviders,
    maxTotalIdentifiers = maxTotalIdentifiers,
    maxEmailIdentifiers = maxEmailIdentifiers,
    maxPhoneIdentifiers = maxPhoneIdentifiers,
    maxIdentifiersPerExternalProvider = maxIdentifiersPerExternalProvider,
    userNicknameMinLength = userNicknameMinLength,
    userNicknameMaxLength = userNicknameMaxLength,
    userDisplayNameMinLength = userDisplayNameMinLength,
    userDisplayNameMaxLength = userDisplayNameMaxLength,
    isUserNicknameChangeAllowed = isUserNicknameChangeAllowed,
    isRegistrationEnabled = isRegistrationEnabled
)
