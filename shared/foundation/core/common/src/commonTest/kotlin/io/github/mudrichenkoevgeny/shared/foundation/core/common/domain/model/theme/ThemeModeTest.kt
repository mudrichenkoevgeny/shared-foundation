package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.theme

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class ThemeModeTest {

    @Test
    fun testFromValueOrNull() {
        assertEquals(ThemeMode.LIGHT, ThemeMode.fromValueOrNull("light"))
        assertEquals(ThemeMode.LIGHT, ThemeMode.fromValueOrNull("LIGHT"))
        assertEquals(ThemeMode.DARK, ThemeMode.fromValueOrNull("dark"))
        assertEquals(ThemeMode.SYSTEM, ThemeMode.fromValueOrNull("system"))
        assertNull(ThemeMode.fromValueOrNull("invalid"))
        assertNull(ThemeMode.fromValueOrNull(null))
    }

    @Test
    fun testFromValueOrThrow() {
        assertEquals(ThemeMode.LIGHT, ThemeMode.fromValueOrThrow("light"))
        assertFailsWith<IllegalArgumentException> {
            ThemeMode.fromValueOrThrow("unknown")
        }
    }
}
