package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierPayload
import kotlin.time.Instant

/**
 * Internal identifier row for server-side storage and business logic.
 *
 * Adds [passwordHash] to [UserIdentifier]-equivalent fields. [passwordHash] is used for password-based providers
 * and must be `null` for external providers; this value must not be exposed via API payloads.
 * Public wire representation is [UserIdentifierPayload].
 */
data class UserIdentifierInternal(
    val id: UserIdentifierId = UserIdentifierId.generate(),
    val userId: UserId,
    val userAuthProvider: UserAuthProvider,
    val identifier: String,
    val passwordHash: String? = null,
    val isSensitiveValuesMasked: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant?
)
