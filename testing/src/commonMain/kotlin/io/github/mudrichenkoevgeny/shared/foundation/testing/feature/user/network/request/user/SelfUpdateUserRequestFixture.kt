package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.patch.PatchField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.user.SelfUpdateUserRequest

/**
 * Creates a test [SelfUpdateUserRequest] request object instance.
 *
 * @param nickname Target nickname update ([PatchField]).
 * @param displayName Target display name update ([PatchField]).
 * @param avatarFileId Target avatar file identifier update ([PatchField]).
 * @param birthdate Target birthdate timestamp update ([PatchField]).
 */
public fun createTestSelfUpdateUserRequest(
    nickname: PatchField<String> = PatchField.Value("testuser"),
    displayName: PatchField<String> = PatchField.Value("Test User"),
    avatarFileId: PatchField<String> = PatchField.Unchanged,
    birthdate: PatchField<Long> = PatchField.Unchanged
): SelfUpdateUserRequest = SelfUpdateUserRequest(
    nickname = nickname,
    displayName = displayName,
    avatarFileId = avatarFileId,
    birthdate = birthdate
)
