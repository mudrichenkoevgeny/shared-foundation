package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.uuid.Uuid

class UserIdTest {

    @Test
    fun `parsing roundtrips from dash-hex string`() {
        val uuidString = "00000000-0000-4000-8000-000000000001"
        val userId = UserId(Uuid.parse(uuidString))
        assertEquals(userId, userId.asHexDashString().toUserIdOrNull())
    }

    @Test
    fun `toUserIdOrNull returns null for invalid UUID`() {
        assertNull("invalid".toUserIdOrNull())
    }

    @Test
    fun `toUserIdOrThrow throws for invalid UUID`() {
        assertFailsWith<Throwable> {
            "invalid".toUserIdOrThrow()
        }
    }
}
