package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.patch.PatchField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request body for updating a user's own account details.
 *
 * Excludes administrative fields (`accountStatus`, `authorityLevel`, `permissionCodes`, etc.)
 * to prevent privilege escalation on self-service endpoints.
 *
 * Uses 3-state [PatchField] properties:
 * - [PatchField.Unchanged]: Field omitted in request; leave unchanged in persistence.
 * - [PatchField.Value] with non-null value: Set field to new value in persistence.
 * - [PatchField.Value] with `null` value: Explicitly clear/nullify field in persistence.
 *
 * @property nickname Target nickname update ([PatchField]).
 * @property displayName Target display name update ([PatchField]).
 * @property avatarFileId Target avatar file identifier update ([PatchField]).
 * @property birthdate Target birthdate timestamp update ([PatchField]).
 */
@Serializable
public data class SelfUpdateUserRequest(
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    @SerialName(UserApiFields.NICKNAME)
    public val nickname: PatchField<String> = PatchField.Unchanged,
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    @SerialName(UserApiFields.DISPLAY_NAME)
    public val displayName: PatchField<String> = PatchField.Unchanged,
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    @SerialName(UserApiFields.AVATAR_FILE_ID)
    public val avatarFileId: PatchField<String> = PatchField.Unchanged,
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    @SerialName(UserApiFields.BIRTHDATE)
    public val birthdate: PatchField<Long> = PatchField.Unchanged
)
