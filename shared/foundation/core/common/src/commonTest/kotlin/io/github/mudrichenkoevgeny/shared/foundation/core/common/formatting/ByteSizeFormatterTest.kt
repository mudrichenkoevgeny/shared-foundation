package io.github.mudrichenkoevgeny.shared.foundation.core.common.formatting

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ByteSizeFormatterTest {

    @Test
    fun `format converts bytes to human-readable strings in English`() {
        assertEquals("500 B", ByteSizeFormatter.format(500L, locale = "en"))
        assertEquals("2 KB", ByteSizeFormatter.format(2048L, locale = "en"))
        assertEquals("50 MB", ByteSizeFormatter.format(52_428_800L, locale = "en"))
        assertEquals("1 GB", ByteSizeFormatter.format(1_073_741_824L, locale = "en"))
        assertEquals("2 TB", ByteSizeFormatter.format(2_199_023_255_552L, locale = "en"))
    }

    @Test
    fun `format converts bytes to human-readable strings in Russian`() {
        assertEquals("500 Б", ByteSizeFormatter.format(500L, locale = "ru"))
        assertEquals("2 КБ", ByteSizeFormatter.format(2048L, locale = "ru"))
        assertEquals("50 МБ", ByteSizeFormatter.format(52_428_800L, locale = "ru"))
        assertEquals("1 ГБ", ByteSizeFormatter.format(1_073_741_824L, locale = "ru"))
        assertEquals("2 ТБ", ByteSizeFormatter.format(2_199_023_255_552L, locale = "ru"))
    }

    @Test
    fun `format handles string numbers and null inputs gracefully`() {
        assertEquals("50 MB", ByteSizeFormatter.format("52428800", locale = "en"))
        assertEquals("invalid", ByteSizeFormatter.format("invalid", locale = "en"))
        assertNull(ByteSizeFormatter.format(null, locale = "en"))
    }
}
