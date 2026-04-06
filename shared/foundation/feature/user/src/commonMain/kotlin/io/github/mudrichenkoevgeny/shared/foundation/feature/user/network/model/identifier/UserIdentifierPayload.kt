package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for a user identifier record (management and open list/detail responses).
 *
 * @property identifier Login value or server-defined masked form; meaning depends on [isSensitiveValuesMasked].
 * @property isSensitiveValuesMasked `true` when [identifier] must be treated as non-raw for the caller.
 * @property id Identifier row id.
 * @property userId Owning user id.
 * @property userAuthProvider [UserAuthProvider] serial name.
 * @property createdAt Epoch millis when linked.
 * @property updatedAt Epoch millis of last change, if any.
 */
@Serializable
data class UserIdentifierPayload(
    @SerialName(CommonApiFields.ID)
    val id: String,

    @SerialName(UserApiFields.USER_ID)
    val userId: String,

    @SerialName(UserApiFields.USER_AUTH_PROVIDER)
    val userAuthProvider: String,

    @SerialName(UserApiFields.IDENTIFIER)
    val identifier: String,

    @SerialName(CommonApiFields.IS_SENSITIVE_VALUES_MASKED)
    val isSensitiveValuesMasked: Boolean,

    @SerialName(CommonApiFields.CREATED_AT)
    val createdAt: Long,

    @SerialName(CommonApiFields.UPDATED_AT)
    val updatedAt: Long? = null
)
