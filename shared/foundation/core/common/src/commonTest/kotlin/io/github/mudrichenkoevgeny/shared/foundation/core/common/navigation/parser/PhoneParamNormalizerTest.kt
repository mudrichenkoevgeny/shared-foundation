package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PhoneParamNormalizerTest {

    @Test
    fun `normalizePhoneQueryParam handles null receiver and blank strings`() {
        val nullReceiver: String? = null
        assertNull(nullReceiver.normalizePhoneQueryParam())
        assertNull("".normalizePhoneQueryParam())
        assertNull("   ".normalizePhoneQueryParam())
        assertNull("\t\n\r".normalizePhoneQueryParam())
    }

    @Test
    fun `normalizePhoneQueryParam restores plus for space-prefixed decoded phone numbers`() {
        assertEquals("+1234567890", " 1234567890".normalizePhoneQueryParam())
        assertEquals("+1234567890", " +1234567890".normalizePhoneQueryParam())
        assertEquals("+1234567890", "  1234567890  ".normalizePhoneQueryParam())
    }

    @Test
    fun `normalizePhoneQueryParam keeps valid phone numbers and local numbers intact`() {
        assertEquals("+1234567890", "+1234567890".normalizePhoneQueryParam())
        assertEquals("1234567890", "1234567890".normalizePhoneQueryParam())
        assertEquals("89991234567", "89991234567".normalizePhoneQueryParam())
    }

    @Test
    fun `normalizePhoneQueryParam handles tab prefixes and non-space whitespace without adding plus`() {
        assertEquals("1234567890", "\t1234567890".normalizePhoneQueryParam())
        assertEquals("abc", " abc".normalizePhoneQueryParam())
    }

    @Test
    fun `normalizePhoneQueryParam restores leading plus while preserving internal formatting spaces`() {
        assertEquals("+1 234 567 890", " 1 234 567 890".normalizePhoneQueryParam())
        assertEquals("1 234 567 890", "1 234 567 890".normalizePhoneQueryParam())
    }

    @Test
    fun `normalizePhoneQueryParams handles lists of phone strings`() {
        val rawList = listOf(" 1234567890", "+9876543210", "", "   ", " 1 234 567 890")
        val normalized = rawList.normalizePhoneQueryParams()

        assertEquals(listOf("+1234567890", "+9876543210", "+1 234 567 890"), normalized)

        val nullList: List<String>? = null
        assertEquals(emptyList(), nullList.normalizePhoneQueryParams())
    }
}
