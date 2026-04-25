package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.ExternalAuthProvider

/**
 * Full authentication configuration edited through the **management** API.
 *
 * Superset of [PublicAuthSettings]: includes the same provider lists plus token lifetime policy
 * for issued credentials. Server implementations apply updates without restart; **already issued**
 * tokens may retain semantics from the previous policy until they expire or are deleted, unless
 * the server documents otherwise.
 *
 * @property availableAuthProviders Enabled providers grouped for display and policy;
 * see [AvailableAuthProviders].
 * @property maxTotalIdentifiers Maximum number of identifiers of any type allowed per account.
 * @property maxEmailIdentifiers Maximum number of email-based identifiers allowed per account.
 * @property maxPhoneIdentifiers Maximum number of phone-based identifiers allowed per account.
 * @property maxIdentifiersPerExternalProvider Maximum number of identifiers allowed for each
 * unique external provider per account; see [ExternalAuthProvider].
 * @property maxActiveSessions Maximum number of active sessions allowed per account.
 * @property accessTokenExpirationSeconds Validity window for **new** access tokens, in seconds.
 * @property refreshTokenExpirationSeconds Validity window for **new** refresh tokens, in seconds.
 * @property accountDeletionDelaySeconds The delay in seconds between scheduling an account
 * for deletion and its permanent removal from the system.
 */
data class ManagementAuthSettings(
    val availableAuthProviders: AvailableAuthProviders,
    val maxTotalIdentifiers: Int,
    val maxEmailIdentifiers: Int,
    val maxPhoneIdentifiers: Int,
    val maxIdentifiersPerExternalProvider: Int,
    val maxActiveSessions: Int,
    val accessTokenExpirationSeconds: Int,
    val refreshTokenExpirationSeconds: Int,
    val accountDeletionDelaySeconds: Int
)
