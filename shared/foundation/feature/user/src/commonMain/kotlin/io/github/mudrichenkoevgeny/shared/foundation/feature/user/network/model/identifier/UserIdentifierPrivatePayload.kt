package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Detailed credential wire payload for authenticated self-service and management inspection.
 *
 * Wire keys use `snake_case` via [CommonApiFields] and [UserApiFields]. Aligns with domain [UserIdentifierPrivate].
 *
 * @property id [UserIdentifierPrivate.id] on the wire: [UserIdentifierId] as hex-dash string.
 * @property userId [UserIdentifierPrivate.userId] on the wire: [UserId] as hex-dash string.
 * @property userAuthProvider [UserIdentifierPrivate.userAuthProvider]; wire values match [UserAuthProvider.serialName].
 * @property identifier [UserIdentifierPrivate.identifier].
 * @property displayName [UserIdentifierPrivate.displayName].
 * @property externalProviderEmail [UserIdentifierPrivate.externalProviderEmail].
 * @property isSensitiveValuesMasked [UserIdentifierPrivate.isSensitiveValuesMasked].
 * @property createdAt [UserIdentifierPrivate.createdAt] as Unix epoch milliseconds ([Instant]).
 * @property updatedAt [UserIdentifierPrivate.updatedAt] as Unix epoch milliseconds ([Instant]), or `null`.
 */
@Serializable
data class UserIdentifierPrivatePayload(
    @SerialName(CommonApiFields.ID)
    val id: String,

    @SerialName(UserApiFields.USER_ID)
    val userId: String,

    @SerialName(UserApiFields.USER_AUTH_PROVIDER)
    val userAuthProvider: String,

    @SerialName(UserApiFields.IDENTIFIER)
    val identifier: String,

    @SerialName(UserApiFields.DISPLAY_NAME)
    val displayName: String,

    @SerialName(UserApiFields.EXTERNAL_PROVIDER_EMAIL)
    val externalProviderEmail: String? = null,

    @SerialName(CommonApiFields.IS_SENSITIVE_VALUES_MASKED)
    val isSensitiveValuesMasked: Boolean,

    @SerialName(CommonApiFields.CREATED_AT)
    val createdAt: Long,

    @SerialName(CommonApiFields.UPDATED_AT)
    val updatedAt: Long? = null
)
