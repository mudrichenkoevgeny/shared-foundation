package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordhash.PasswordHash
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import kotlin.time.Instant

/**
 * Internal identifier row for server-side storage and business logic.
 *
 * Adds [passwordHash] and database row modification timestamp [updatedAt] to [UserIdentifierPrivate]-equivalent fields;
 * these internal fields must not be exposed directly via public API payloads.
 *
 * @property id Unique identifier row id ([UserIdentifierId]); defaults to [UserIdentifierId.generate] when creating new instances.
 * @property userId Owning user id ([UserId]).
 * @property userAuthProvider Authentication provider category ([UserAuthProvider]).
 * @property identifier Raw login identifier (email address, phone number, or external provider subject ID).
 * @property displayName Non-null display name for the identifier.
 * @property externalProviderEmail Verified email address associated with external provider, if applicable.
 * @property passwordHash Password hash for password-based credentials ([PasswordHash]); `null` for external auth providers.
 * @property createdAt Timestamp when the identifier was created ([Instant]).
 * @property updatedAt Timestamp when the identifier was last modified ([Instant]), or `null` if unchanged.
 */
public data class UserIdentifierInternal(
    public val id: UserIdentifierId = UserIdentifierId.generate(),
    public val userId: UserId,
    public val userAuthProvider: UserAuthProvider,
    public val identifier: String,
    public val displayName: String,
    public val externalProviderEmail: String?,
    public val passwordHash: PasswordHash?,
    public val createdAt: Instant,
    public val updatedAt: Instant?
)
