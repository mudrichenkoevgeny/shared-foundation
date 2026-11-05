package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ClientDeviceInfoTest {
    @Test
    fun `parsePrimaryLanguage returns null when header is null or blank`() {
        assertNull(ClientDeviceInfo.parsePrimaryLanguage(null))
        assertNull(ClientDeviceInfo.parsePrimaryLanguage(""))
        assertNull(ClientDeviceInfo.parsePrimaryLanguage("   "))
    }

    @Test
    fun `parsePrimaryLanguage extracts primary language from Accept-Language string`() {
        assertEquals("en-US", ClientDeviceInfo.parsePrimaryLanguage("en-US,en;q=0.9,ru;q=0.8"))
        assertEquals("ru-RU", ClientDeviceInfo.parsePrimaryLanguage("ru-RU,ru;q=0.9,en-US;q=0.8"))
        assertEquals("en-US", ClientDeviceInfo.parsePrimaryLanguage("en-US;q=0.8, en;q=0.7"))
        assertEquals("ru-RU", ClientDeviceInfo.parsePrimaryLanguage("ru-RU"))
        assertEquals("en-US", ClientDeviceInfo.parsePrimaryLanguage("  en-US  , en;q=0.9 "))
    }
}
