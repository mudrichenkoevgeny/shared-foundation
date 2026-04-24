package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierPayload
import kotlin.time.Instant

/**
 * User credential row; wire counterpart [UserIdentifierPayload]. [identifier] is raw or masked per
 * [isSensitiveValuesMasked].
 * Use [UserIdentifierInternal] for server-side storage fields that must not be exposed via API payloads.
 */
data class UserIdentifier(
    val id: UserIdentifierId = UserIdentifierId.generate(),
    val userId: UserId,
    val userAuthProvider: UserAuthProvider,
    val identifier: String,
    val externalProviderEmail: String?,
    val isSensitiveValuesMasked: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant?
)
