package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.ManagementAuthSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.emailrestriction.toEmailRestrictionPolicy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.emailrestriction.toEmailRestrictionPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.ManagementAuthSettingsPayload

/**
 * Builds domain [ManagementAuthSettings] from [ManagementAuthSettingsPayload].
 */
public fun ManagementAuthSettingsPayload.toManagementAuthSettings(): ManagementAuthSettings = ManagementAuthSettings(
    availableAuthProviders = availableAuthProviders.toAvailableAuthProviders(),
    maxTotalIdentifiers = maxTotalIdentifiers,
    maxEmailIdentifiers = maxEmailIdentifiers,
    maxPhoneIdentifiers = maxPhoneIdentifiers,
    maxIdentifiersPerExternalProvider = maxIdentifiersPerExternalProvider,
    userNicknameMinLength = userNicknameMinLength,
    userNicknameMaxLength = userNicknameMaxLength,
    userDisplayNameMinLength = userDisplayNameMinLength,
    userDisplayNameMaxLength = userDisplayNameMaxLength,
    isUserNicknameChangeAllowed = isUserNicknameChangeAllowed,
    maxActiveSessionsForOpenUser = maxActiveSessionsForOpenUser,
    maxActiveSessionsForManagementUser = maxActiveSessionsForManagementUser,
    accessTokenExpirationSeconds = accessTokenExpirationSeconds,
    refreshTokenExpirationSeconds = refreshTokenExpirationSeconds,
    accountDeletionGracePeriodSeconds = accountDeletionGracePeriodSeconds,
    accountDeletionCheckIntervalSeconds = accountDeletionCheckIntervalSeconds,
    isRegistrationEnabled = isRegistrationEnabled,
    openEmailRestrictionPolicy = openEmailRestrictionPolicy.toEmailRestrictionPolicy(),
    managementEmailRestrictionPolicy = managementEmailRestrictionPolicy.toEmailRestrictionPolicy()
)

/**
 * Builds network [ManagementAuthSettingsPayload] from domain [ManagementAuthSettings].
 */
public fun ManagementAuthSettings.toManagementAuthSettingsPayload(): ManagementAuthSettingsPayload =
    ManagementAuthSettingsPayload(
        availableAuthProviders = availableAuthProviders.toAvailableAuthProvidersPayload(),
        maxTotalIdentifiers = maxTotalIdentifiers,
        maxEmailIdentifiers = maxEmailIdentifiers,
        maxPhoneIdentifiers = maxPhoneIdentifiers,
        maxIdentifiersPerExternalProvider = maxIdentifiersPerExternalProvider,
        userNicknameMinLength = userNicknameMinLength,
        userNicknameMaxLength = userNicknameMaxLength,
        userDisplayNameMinLength = userDisplayNameMinLength,
        userDisplayNameMaxLength = userDisplayNameMaxLength,
        isUserNicknameChangeAllowed = isUserNicknameChangeAllowed,
        maxActiveSessionsForOpenUser = maxActiveSessionsForOpenUser,
        maxActiveSessionsForManagementUser = maxActiveSessionsForManagementUser,
        accessTokenExpirationSeconds = accessTokenExpirationSeconds,
        refreshTokenExpirationSeconds = refreshTokenExpirationSeconds,
        accountDeletionGracePeriodSeconds = accountDeletionGracePeriodSeconds,
        accountDeletionCheckIntervalSeconds = accountDeletionCheckIntervalSeconds,
        isRegistrationEnabled = isRegistrationEnabled,
        openEmailRestrictionPolicy = openEmailRestrictionPolicy.toEmailRestrictionPolicyPayload(),
        managementEmailRestrictionPolicy = managementEmailRestrictionPolicy.toEmailRestrictionPolicyPayload()
    )
