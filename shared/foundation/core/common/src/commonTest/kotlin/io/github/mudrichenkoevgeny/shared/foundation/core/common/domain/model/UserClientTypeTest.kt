package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model

import io.github.mudrichenkoevgeny.shared.foundation.core.common.serialization.FoundationJson
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UserClientTypeTest {

    @Test
    fun `serialName returns wire string for each variant`() {
        assertEquals(UserClientType.CLIENT_WEB, UserClientType.WEB.serialName)
        assertEquals(UserClientType.CLIENT_ANDROID, UserClientType.ANDROID.serialName)
        assertEquals(UserClientType.CLIENT_IOS, UserClientType.IOS.serialName)
        assertEquals(UserClientType.CLIENT_DESKTOP, UserClientType.DESKTOP.serialName)
    }

    @Test
    fun `fromValue accepts case-insensitive enum names and wire strings`() {
        assertEquals(UserClientType.WEB, UserClientType.fromValue("WEB"))
        assertEquals(UserClientType.WEB, UserClientType.fromValue("web"))
        assertEquals(UserClientType.ANDROID, UserClientType.fromValue("ANDROID"))
        assertEquals(UserClientType.ANDROID, UserClientType.fromValue("android"))
        assertEquals(UserClientType.IOS, UserClientType.fromValue("Ios"))
    }

    @Test
    fun `fromValue returns null for unknown string`() {
        assertNull(UserClientType.fromValue("unknown"))
        assertNull(UserClientType.fromValue(""))
    }

    @Test
    fun `json uses SerialName wire values`() {
        val json = FoundationJson.encodeToString(UserClientType.serializer(), UserClientType.WEB)
        assertEquals("\"${UserClientType.CLIENT_WEB}\"", json)

        val decoded = FoundationJson.decodeFromString(UserClientType.serializer(), json)
        assertEquals(UserClientType.WEB, decoded)
    }
}
