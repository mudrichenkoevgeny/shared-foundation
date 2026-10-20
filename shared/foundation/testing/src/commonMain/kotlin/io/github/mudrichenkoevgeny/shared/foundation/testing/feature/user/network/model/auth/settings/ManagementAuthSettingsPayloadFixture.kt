package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.AvailableAuthProvidersPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.ManagementAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.emailrestriction.EmailRestrictionPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.emailrestriction.createTestEmailRestrictionPolicyPayload

/**
 * Creates a test [ManagementAuthSettingsPayload] wire object instance.
 *
 * @param availableAuthProviders List of supported authentication providers payload ([AvailableAuthProvidersPayload]).
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
 * @param openEmailRestrictionPolicy Email restriction policy payload for open APIs ([EmailRestrictionPolicyPayload]).
 * @param managementEmailRestrictionPolicy Email restriction policy payload for management APIs
 * ([EmailRestrictionPolicyPayload]).
 */
fun createTestManagementAuthSettingsPayload(
    availableAuthProviders: AvailableAuthProvidersPayload = createTestAvailableAuthProvidersPayload(),
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
    openEmailRestrictionPolicy: EmailRestrictionPolicyPayload = createTestEmailRestrictionPolicyPayload(),
    managementEmailRestrictionPolicy: EmailRestrictionPolicyPayload = createTestEmailRestrictionPolicyPayload()
) = ManagementAuthSettingsPayload(
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
