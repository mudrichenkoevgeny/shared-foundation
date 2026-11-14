package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.password

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for changing an existing user password.
 *
 * @property oldPassword The current password for identity verification.
 * @property newPassword The new password to be set for the account.
 */
@Serializable
public data class EmailPasswordChangeRequest(
    @NotBlankStringField
    @SerialName(UserApiFields.OLD_PASSWORD)
    public val oldPassword: String,
    @NotBlankStringField
    @SerialName(UserApiFields.NEW_PASSWORD)
    public val newPassword: String
)
