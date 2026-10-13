package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ClientDeviceInfoTest {

    @Test
    fun `getPrimaryLanguage returns null when language is null`() {
        val deviceInfo = ClientDeviceInfo(
            deviceId = null,
            deviceName = null,
            clientType = null,
            language = null,
            appVersion = null,
            operationSystemVersion = null
        )
        assertNull(deviceInfo.getPrimaryLanguage())
    }

    @Test
    fun `getPrimaryLanguage extracts primary language from Accept-Language string`() {
        val deviceInfo1 = createClientDeviceInfo("ru-RU,ru;q=0.9,en-US;q=0.8")
        assertEquals("ru-RU", deviceInfo1.getPrimaryLanguage())

        val deviceInfo2 = createClientDeviceInfo("en-US;q=0.8, en;q=0.7")
        assertEquals("en-US", deviceInfo2.getPrimaryLanguage())

        val deviceInfo3 = createClientDeviceInfo("ru-RU")
        assertEquals("ru-RU", deviceInfo3.getPrimaryLanguage())

        val deviceInfo4 = createClientDeviceInfo("  ru-RU  , ru;q=0.9 ")
        assertEquals("ru-RU", deviceInfo4.getPrimaryLanguage())
    }

    private fun createClientDeviceInfo(language: String?): ClientDeviceInfo {
        return ClientDeviceInfo(
            deviceId = null,
            deviceName = null,
            clientType = null,
            language = language,
            appVersion = null,
            operationSystemVersion = null
        )
    }
}
