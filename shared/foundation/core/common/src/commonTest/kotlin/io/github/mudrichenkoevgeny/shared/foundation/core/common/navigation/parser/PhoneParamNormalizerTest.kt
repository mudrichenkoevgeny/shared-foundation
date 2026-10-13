package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PhoneParamNormalizerTest {

    @Test
    fun `normalizePhoneQueryParam handles null and blank strings`() {
        assertNull(null.normalizePhoneQueryParam())
        assertNull("".normalizePhoneQueryParam())
        assertNull("   ".normalizePhoneQueryParam())
    }

    @Test
    fun `normalizePhoneQueryParam restores plus for space-prefixed decoded phone numbers`() {
        assertEquals("+1234567890", " 1234567890".normalizePhoneQueryParam())
        assertEquals("+1234567890", " +1234567890".normalizePhoneQueryParam())
        assertEquals("+1234567890", "  1234567890  ".normalizePhoneQueryParam())
    }

    @Test
    fun `normalizePhoneQueryParam keeps valid phone numbers intact`() {
        assertEquals("+1234567890", "+1234567890".normalizePhoneQueryParam())
        assertEquals("1234567890", "1234567890".normalizePhoneQueryParam())
    }

    @Test
    fun `normalizePhoneQueryParams handles lists of phone strings`() {
        val rawList = listOf(" 1234567890", "+9876543210", "", "   ")
        val normalized = rawList.normalizePhoneQueryParams()

        assertEquals(listOf("+1234567890", "+9876543210"), normalized)

        val nullList: List<String>? = null
        assertEquals(emptyList(), nullList.normalizePhoneQueryParams())
    }
}
