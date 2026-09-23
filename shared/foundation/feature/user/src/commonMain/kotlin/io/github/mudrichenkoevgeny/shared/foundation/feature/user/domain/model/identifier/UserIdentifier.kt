package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierPayload
import kotlin.time.Instant

/**
 * User credential row; wire counterpart [UserIdentifierPayload]. [identifier] is raw or masked per
 * [isSensitiveValuesMasked].
 * Use [UserIdentifierInternal] for server-side storage fields that must not be exposed via API payloads.
 *
 * @property id Identifier row id.
 * @property userId Owning user id.
 * @property userAuthProvider Authentication provider category.
 * @property identifier Raw or masked login identifier string.
 * @property displayName Non-null display name for the identifier.
 * @property externalProviderEmail Verified email address associated with external provider.
 * @property isSensitiveValuesMasked `true` when [identifier] is masked.
 * @property createdAt Creation timestamp.
 * @property updatedAt Last update timestamp, or `null`.
 */
data class UserIdentifier(
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
