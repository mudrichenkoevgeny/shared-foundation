package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.toUserIdOrThrow

const val TEST_USER_ID = "00000000-0000-0000-0000-000000000001"
const val TEST_USER_ID_2 = "00000000-0000-0000-0000-000000000002"

fun createTestUserId(
    value: String = TEST_USER_ID
): UserId = value.toUserIdOrThrow()
