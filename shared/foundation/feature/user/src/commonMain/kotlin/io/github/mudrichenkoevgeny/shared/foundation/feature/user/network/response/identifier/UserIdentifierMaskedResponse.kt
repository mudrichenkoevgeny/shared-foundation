package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.identifier

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO representing a user identifier visible in staff endpoints with masked sensitive value.
 *
 * @property id The unique identifier of this specific credential record.
 * @property userId The unique identifier of the user who owns this credential.
 * @property userAuthProvider The authentication method used (e.g. [UserAuthProvider]).
 * @property maskedIdentifier The masked login value (e.g., masked email or phone).
 * @property createdAt The timestamp when this identifier was linked, in milliseconds.
 * @property updatedAt The timestamp of the last modification in milliseconds, or null if never updated.
 */
@Serializable
data class UserIdentifierMaskedResponse(
    @SerialName(CommonApiFields.ID)
    val id: String,

    @SerialName(UserApiFields.USER_ID)
    val userId: String,

    @SerialName(UserApiFields.USER_AUTH_PROVIDER)
    val userAuthProvider: String,

    @SerialName(UserApiFields.MASKED_IDENTIFIER)
    val maskedIdentifier: String,

    @SerialName(CommonApiFields.CREATED_AT)
    val createdAt: Long,

    @SerialName(CommonApiFields.UPDATED_AT)
    val updatedAt: Long? = null
)
