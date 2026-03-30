package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierUnmaskedPayload
import kotlin.time.Instant

/**
 * User credential row as exposed when the raw identifier value is allowed (**unmasked**).
 *
 * Wire counterpart: [UserIdentifierUnmaskedPayload]. [id] is the identifier record id; timestamps are
 * [Instant] (epoch millis on the wire).
 */
data class UserIdentifierUnmasked(
    val id: UserIdentifierId = UserIdentifierId.generate(),
    val userId: UserId,
    val userAuthProvider: UserAuthProvider,
    val identifier: String,
    val createdAt: Instant,
    val updatedAt: Instant?
)
