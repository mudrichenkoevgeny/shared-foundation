package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.ExternalAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.emailrestriction.EmailRestrictionPolicy

/**
 * Full authentication configuration edited through the **management** API.
 *
 * Superset of [OpenAuthSettings]: includes the same provider lists plus token lifetime policy
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
 * @property userNicknameMinLength Minimum character length for user nicknames.
 * @property userNicknameMaxLength Maximum character length for user nicknames.
 * @property userDisplayNameMinLength Minimum character length for user display names.
 * @property userDisplayNameMaxLength Maximum character length for user display names.
 * @property isUserNicknameChangeAllowed Flag indicating whether users are permitted to modify their nickname.
 * @property maxActiveSessionsForOpenUser Maximum number of active sessions allowed per open user account.
 * @property maxActiveSessionsForManagementUser Maximum number of active sessions allowed per management user account.
 * @property accessTokenExpirationSeconds Validity window for **new** access tokens, in seconds.
 * @property refreshTokenExpirationSeconds Validity window for **new** refresh tokens, in seconds.
 * @property accountDeletionGracePeriodSeconds The grace period in seconds between scheduling an account
 * for deletion and its permanent removal from the system.
 * @property accountDeletionCheckIntervalSeconds The background worker check interval in seconds for processing
 * accounts pending permanent deletion.
 * @property isRegistrationEnabled Global flag indicating whether new user registrations are permitted.
 * @property openEmailRestrictionPolicy Email domain blacklist and whitelist restriction policy for open API endpoints; see [EmailRestrictionPolicy].
 * @property managementEmailRestrictionPolicy Email domain blacklist and whitelist restriction policy for management API endpoints; see [EmailRestrictionPolicy].
 */
public data class ManagementAuthSettings(
    public val availableAuthProviders: AvailableAuthProviders,
    public val maxTotalIdentifiers: Int,
    public val maxEmailIdentifiers: Int,
    public val maxPhoneIdentifiers: Int,
    public val maxIdentifiersPerExternalProvider: Int,
    public val userNicknameMinLength: Int,
    public val userNicknameMaxLength: Int,
    public val userDisplayNameMinLength: Int,
    public val userDisplayNameMaxLength: Int,
    public val isUserNicknameChangeAllowed: Boolean,
    public val maxActiveSessionsForOpenUser: Int,
    public val maxActiveSessionsForManagementUser: Int,
    public val accessTokenExpirationSeconds: Int,
    public val refreshTokenExpirationSeconds: Int,
    public val accountDeletionGracePeriodSeconds: Int,
    public val accountDeletionCheckIntervalSeconds: Int,
    public val isRegistrationEnabled: Boolean,
    public val openEmailRestrictionPolicy: EmailRestrictionPolicy,
    public val managementEmailRestrictionPolicy: EmailRestrictionPolicy
)
