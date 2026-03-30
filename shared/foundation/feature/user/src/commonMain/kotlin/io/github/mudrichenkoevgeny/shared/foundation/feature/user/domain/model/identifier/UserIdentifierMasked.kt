package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierMaskedPayload
import kotlin.time.Instant

/**
 * User credential row as exposed when the identifier value must be **masked** (e.g. staff list).
 *
 * Wire counterpart: [UserIdentifierMaskedPayload]. [id] is the identifier record id; timestamps are
 * [Instant] (epoch millis on the wire).
 */
data class UserIdentifierMasked(
    val id: UserIdentifierId = UserIdentifierId.generate(),
    val userId: UserId,
    val userAuthProvider: UserAuthProvider,
    val maskedIdentifier: String,
    val createdAt: Instant,
    val updatedAt: Instant?
)
