package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.toUserIdentifierIdOrThrow

/** Default deterministic user identifier ID string for unit tests. */
public const val TEST_IDENTIFIER_ID: String = "00000000-0000-4000-8000-000000000020"

/**
 * Creates a test [UserIdentifierId] instance for unit testing.
 *
 * @param value String representation of the UUID (defaults to [TEST_IDENTIFIER_ID]).
 */
public fun createTestUserIdentifierId(value: String = TEST_IDENTIFIER_ID): UserIdentifierId =
    value.toUserIdentifierIdOrThrow()
