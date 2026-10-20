package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.base.user.identifier.detail

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.identifier.detail.IdentifierDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.identifier.TEST_IDENTIFIER_ID
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.TEST_USER_ID

/**
 * Creates a test [IdentifierDetailParams] navigation params instance.
 *
 * @param identifierId Unique identifier ID string.
 * @param userId Target user ID string.
 */
fun createTestIdentifierDetailParams(
    identifierId: String = TEST_IDENTIFIER_ID,
    userId: String? = TEST_USER_ID
): IdentifierDetailParams = IdentifierDetailParams(
    identifierId = identifierId,
    userId = userId
)
