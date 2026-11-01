package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.ExternalAuthProvider

/**
 * **Open** authentication settings: what sign-in methods and profile constraints the deployment exposes to clients.
 *
 * @property availableAuthProviders Enabled providers grouped for display and policy;
 * see [AvailableAuthProviders].
 * @property maxTotalIdentifiers Maximum number of identifiers of any type allowed per account.
 * @property maxEmailIdentifiers Maximum number of email-based identifiers allowed per account.
 * @property maxPhoneIdentifiers Maximum number of phone-based identifiers allowed per account.
 * @property maxIdentifiersPerExternalProvider Maximum number of identifiers allowed for each
 * unique external provider per account; see [ExternalAuthProvider].
 * @property userNicknameMinLength Minimum character length for user nicknames.
 * @property userNicknameMaxLength Maximum character length for user nicknames.
 * @property userDisplayNameMinLength Minimum character length for user display names.
 * @property userDisplayNameMaxLength Maximum character length for user display names.
 * @property isUserNicknameChangeAllowed Flag indicating whether users are permitted to modify their nickname.
 * @property isRegistrationEnabled Global flag indicating whether new user registrations are permitted.
 */
data class OpenAuthSettings(
    val availableAuthProviders: AvailableAuthProviders,
    val maxTotalIdentifiers: Int,
    val maxEmailIdentifiers: Int,
    val maxPhoneIdentifiers: Int,
    val maxIdentifiersPerExternalProvider: Int,
    val userNicknameMinLength: Int,
    val userNicknameMaxLength: Int,
    val userDisplayNameMinLength: Int,
    val userDisplayNameMaxLength: Int,
    val isUserNicknameChangeAllowed: Boolean,
    val isRegistrationEnabled: Boolean
)
