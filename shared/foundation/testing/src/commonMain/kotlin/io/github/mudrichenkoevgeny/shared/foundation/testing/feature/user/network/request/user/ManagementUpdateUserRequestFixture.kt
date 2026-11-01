package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.patch.PatchField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserStorageConfigurationPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.user.ManagementUpdateUserRequest

/**
 * Creates a test [ManagementUpdateUserRequest] request object instance.
 *
 * @param accountStatus Target account status update ([PatchField]).
 * @param authorityLevel Target authority level update ([PatchField]).
 * @param permissionCodes Target explicit permission codes update ([PatchField]).
 * @param lockoutType Target account lockout type update ([PatchField]).
 * @param temporaryLockoutUntil Target temporary lockout expiration timestamp update ([PatchField]).
 * @param storageConfiguration Target storage configuration overrides update ([PatchField]).
 * @param nickname Target nickname update ([PatchField]).
 * @param displayName Target display name update ([PatchField]).
 * @param avatarFileId Target avatar file identifier update ([PatchField]).
 * @param birthdate Target birthdate timestamp update ([PatchField]).
 */
fun createTestManagementUpdateUserRequest(
    accountStatus: PatchField<String> = PatchField.Value(UserAccountStatus.ACTIVE.value),
    authorityLevel: PatchField<Int> = PatchField.Value(10),
    permissionCodes: PatchField<Set<String>> = PatchField.Value(emptySet()),
    lockoutType: PatchField<String> = PatchField.Unchanged,
    temporaryLockoutUntil: PatchField<Long> = PatchField.Unchanged,
    storageConfiguration: PatchField<UserStorageConfigurationPayload> = PatchField.Unchanged,
    nickname: PatchField<String> = PatchField.Value("testuser"),
    displayName: PatchField<String> = PatchField.Value("Test User"),
    avatarFileId: PatchField<String> = PatchField.Unchanged,
    birthdate: PatchField<Long> = PatchField.Unchanged
) = ManagementUpdateUserRequest(
    accountStatus = accountStatus,
    authorityLevel = authorityLevel,
    permissionCodes = permissionCodes,
    lockoutType = lockoutType,
    temporaryLockoutUntil = temporaryLockoutUntil,
    storageConfiguration = storageConfiguration,
    nickname = nickname,
    displayName = displayName,
    avatarFileId = avatarFileId,
    birthdate = birthdate
)
