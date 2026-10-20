package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.identifier

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordhash.PasswordHash
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierInternal
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.passwordhash.createTestPasswordHash
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.createTestUserId
import kotlin.time.Instant

/**
 * Creates a test [UserIdentifierInternal] domain instance.
 *
 * @param id Unique identifier ID ([UserIdentifierId]).
 * @param userId Owning user ID ([UserId]).
 * @param userAuthProvider Provider category ([UserAuthProvider]).
 * @param identifier Raw credential identifier string.
 * @param displayName Non-null display name.
 * @param externalProviderEmail External email address if available.
 * @param passwordHash Password hash if applicable ([PasswordHash]).
 * @param createdAt Creation timestamp.
 * @param updatedAt Modification timestamp.
 */
fun createTestUserIdentifierInternal(
    id: UserIdentifierId = createTestUserIdentifierId(),
    userId: UserId = createTestUserId(),
    userAuthProvider: UserAuthProvider = UserAuthProvider.EMAIL,
    identifier: String = TEST_EMAIL,
    displayName: String = "Test User",
    externalProviderEmail: String? = null,
    passwordHash: PasswordHash? = createTestPasswordHash(),
    createdAt: Instant = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS),
    updatedAt: Instant? = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS)
) = UserIdentifierInternal(
    id = id,
    userId = userId,
    userAuthProvider = userAuthProvider,
    identifier = identifier,
    displayName = displayName,
    externalProviderEmail = externalProviderEmail,
    passwordHash = passwordHash,
    createdAt = createdAt,
    updatedAt = updatedAt
)
