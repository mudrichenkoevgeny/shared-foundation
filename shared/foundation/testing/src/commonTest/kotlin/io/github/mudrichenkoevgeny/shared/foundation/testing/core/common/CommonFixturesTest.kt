package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.uuid.Uuid

class CommonFixturesTest {

    @Test
    fun generatesDeterministicValidUuidV4Strings() {
        val uuid0 = generateTestUuidString(0)
        val uuid9 = generateTestUuidString(9)
        val uuid99 = generateTestUuidString(99)

        assertEquals("00000000-0000-4000-8000-000000000001", uuid0)
        assertEquals("00000000-0000-4000-8000-00000000000a", uuid9)
        assertEquals("00000000-0000-4000-8000-000000000064", uuid99)

        Uuid.parse(uuid0)
        Uuid.parse(uuid9)
        Uuid.parse(uuid99)
    }

    @Test
    fun generatesDeterministicValidUuidWithGroup() {
        val uuidGroup1Index0 = generateTestUuidString(index = 0, group = 1)
        val uuidGroup2Index9 = generateTestUuidString(index = 9, group = 2)

        assertEquals("00000001-0000-4000-8000-000000000001", uuidGroup1Index0)
        assertEquals("00000002-0000-4000-8000-00000000000a", uuidGroup2Index9)

        Uuid.parse(uuidGroup1Index0)
        Uuid.parse(uuidGroup2Index9)
    }

    @Test
    fun throwsExceptionForNegativeIndexOrGroup() {
        assertFailsWith<IllegalArgumentException> {
            generateTestUuidString(-1)
        }
        assertFailsWith<IllegalArgumentException> {
            generateTestUuidString(0, -1)
        }
    }
}
