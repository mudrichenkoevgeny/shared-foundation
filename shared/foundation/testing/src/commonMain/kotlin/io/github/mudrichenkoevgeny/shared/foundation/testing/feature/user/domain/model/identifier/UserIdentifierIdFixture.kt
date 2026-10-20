package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.toUserIdentifierIdOrThrow

/**
 * Default user identifier ID string for unit testing.
 */
const val TEST_IDENTIFIER_ID = "00000000-0000-0000-0000-000000000020"

/**
 * Creates a test [UserIdentifierId] domain instance.
 *
 * @param value Raw identifier ID string.
 */
fun createTestUserIdentifierId(
    value: String = TEST_IDENTIFIER_ID
): UserIdentifierId = value.toUserIdentifierIdOrThrow()
