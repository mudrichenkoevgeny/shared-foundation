package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordhash.PasswordHash
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
 *
 * @property id Unique identifier row id ([UserIdentifierId]); defaults to [UserIdentifierId.generate] when creating new instances.
 * @property userId Owning user id ([UserId]).
 * @property userAuthProvider Authentication provider category ([UserAuthProvider]).
 * @property identifier Raw login identifier (email address, phone number, or external provider subject ID).
 * @property externalProviderEmail Verified email address associated with external provider, if applicable.
 * @property passwordHash Password hash for password-based credentials ([PasswordHash]); `null` for external auth providers.
 * @property createdAt Timestamp when the identifier was created ([Instant]).
 * @property updatedAt Timestamp when the identifier was last modified ([Instant]), or `null` if unchanged.
 */
data class UserIdentifierInternal(
    val id: UserIdentifierId = UserIdentifierId.generate(),
    val userId: UserId,
    val userAuthProvider: UserAuthProvider,
    val identifier: String,
    val displayName: String,
    val externalProviderEmail: String?,
    val passwordHash: PasswordHash?,
    val createdAt: Instant,
    val updatedAt: Instant?
)