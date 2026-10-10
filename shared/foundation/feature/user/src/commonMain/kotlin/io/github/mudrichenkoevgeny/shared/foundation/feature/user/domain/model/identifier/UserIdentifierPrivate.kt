package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierPrivatePayload
import kotlin.time.Instant

/**
 * Detailed credential row for authenticated self-service and management inspection.
 *
 * Wire counterpart is [UserIdentifierPrivatePayload].
 *
 * @property id Identifier row id.
 * @property userId Owning user id.
 * @property userAuthProvider Authentication provider category.
 * @property identifier Raw or masked login identifier string.
 * @property displayName Non-null display name for the identifier.
 * @property externalProviderEmail Verified email address associated with external provider, or `null`.
 * @property isSensitiveValuesMasked `true` when [identifier] is masked.
 * @property createdAt Creation timestamp.
 * @property updatedAt Last credential record modification timestamp, or `null`.
 */
data class UserIdentifierPrivate(
    val id: UserIdentifierId = UserIdentifierId.generate(),
    val userId: UserId,
    val userAuthProvider: UserAuthProvider,
    val identifier: String,
    val displayName: String,
    val externalProviderEmail: String?,
    val isSensitiveValuesMasked: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant?
)
