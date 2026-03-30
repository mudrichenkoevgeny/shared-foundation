package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.uuid.Uuid

class UserIdentifierIdTest {

    @Test
    fun `parsing roundtrips from dash-hex string`() {
        val uuidString = "123e4567-e89b-12d3-a456-426614174000"
        val identifierId = UserIdentifierId(Uuid.parse(uuidString))
        assertEquals(identifierId, identifierId.asHexDashString().toUserIdentifierIdOrNull())
    }

    @Test
    fun `toUserIdentifierIdOrNull returns null for invalid UUID`() {
        assertNull("invalid".toUserIdentifierIdOrNull())
    }

    @Test
    fun `toUserIdentifierIdOrThrow throws for invalid UUID`() {
        assertFailsWith<Throwable> {
            "invalid".toUserIdentifierIdOrThrow()
        }
    }
}
