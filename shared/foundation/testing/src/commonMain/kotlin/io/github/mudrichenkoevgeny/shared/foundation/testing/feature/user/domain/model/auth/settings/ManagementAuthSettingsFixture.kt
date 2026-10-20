package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.AvailableAuthProviders
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.ManagementAuthSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.emailrestriction.EmailRestrictionPolicy
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.emailrestriction.createTestEmailRestrictionPolicy

/**
 * Creates a test [ManagementAuthSettings] domain instance.
 *
 * @param availableAuthProviders List of supported authentication providers ([AvailableAuthProviders]).
 * @param maxTotalIdentifiers Maximum allowed identifiers per account.
 * @param maxEmailIdentifiers Maximum allowed email identifiers per account.
 * @param maxPhoneIdentifiers Maximum allowed phone identifiers per account.
 * @param maxIdentifiersPerExternalProvider Maximum identifiers per OAuth provider.
 * @param maxActiveSessionsForOpenUser Maximum active sessions allowed for open users.
 * @param maxActiveSessionsForManagementUser Maximum active sessions allowed for management users.
 * @param accessTokenExpirationSeconds Access token validity period in seconds.
 * @param refreshTokenExpirationSeconds Refresh token validity period in seconds.
 * @param accountDeletionGracePeriodSeconds Grace period in seconds before account deletion.
 * @param accountDeletionCheckIntervalSeconds Check interval in seconds for soft-deleted accounts.
 * @param isRegistrationEnabled Flag indicating whether public user registration is open.
 * @param openEmailRestrictionPolicy Email domain restriction policy for open APIs.
 * @param managementEmailRestrictionPolicy Email domain restriction policy for management APIs.
 */
fun createTestManagementAuthSettings(
    availableAuthProviders: AvailableAuthProviders = createTestAvailableAuthProviders(),
    maxTotalIdentifiers: Int = 10,
    maxEmailIdentifiers: Int = 5,
    maxPhoneIdentifiers: Int = 5,
    maxIdentifiersPerExternalProvider: Int = 1,
    maxActiveSessionsForOpenUser: Int = 5,
    maxActiveSessionsForManagementUser: Int = 10,
    accessTokenExpirationSeconds: Int = 3600,
    refreshTokenExpirationSeconds: Int = 2592000,
    accountDeletionGracePeriodSeconds: Int = 2592000,
    accountDeletionCheckIntervalSeconds: Int = 86400,
    isRegistrationEnabled: Boolean = true,
    openEmailRestrictionPolicy: EmailRestrictionPolicy = createTestEmailRestrictionPolicy(),
    managementEmailRestrictionPolicy: EmailRestrictionPolicy = createTestEmailRestrictionPolicy()
) = ManagementAuthSettings(
    availableAuthProviders = availableAuthProviders,
    maxTotalIdentifiers = maxTotalIdentifiers,
    maxEmailIdentifiers = maxEmailIdentifiers,
    maxPhoneIdentifiers = maxPhoneIdentifiers,
    maxIdentifiersPerExternalProvider = maxIdentifiersPerExternalProvider,
    maxActiveSessionsForOpenUser = maxActiveSessionsForOpenUser,
    maxActiveSessionsForManagementUser = maxActiveSessionsForManagementUser,
    accessTokenExpirationSeconds = accessTokenExpirationSeconds,
    refreshTokenExpirationSeconds = refreshTokenExpirationSeconds,
    accountDeletionGracePeriodSeconds = accountDeletionGracePeriodSeconds,
    accountDeletionCheckIntervalSeconds = accountDeletionCheckIntervalSeconds,
    isRegistrationEnabled = isRegistrationEnabled,
    openEmailRestrictionPolicy = openEmailRestrictionPolicy,
    managementEmailRestrictionPolicy = managementEmailRestrictionPolicy
)
