package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.ExternalAuthProvider

/**
 * **Public** authentication settings: what sign-in methods the deployment exposes to clients.
 *
 * @property availableAuthProviders Enabled providers grouped for display and policy;
 * see [AvailableAuthProviders].
 * @property maxTotalIdentifiers Maximum number of identifiers of any type allowed per account.
 * @property maxEmailIdentifiers Maximum number of email-based identifiers allowed per account.
 * @property maxPhoneIdentifiers Maximum number of phone-based identifiers allowed per account.
 * @property maxIdentifiersPerExternalProvider Maximum number of identifiers allowed for each
 * unique external provider per account; see [ExternalAuthProvider].
 */
data class PublicAuthSettings(
    val availableAuthProviders: AvailableAuthProviders,
    val maxTotalIdentifiers: Int,
    val maxEmailIdentifiers: Int,
    val maxPhoneIdentifiers: Int,
    val maxIdentifiersPerExternalProvider: Int
)
