package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ClientTypeTest {

    @Test
    fun `serialName returns wire string for each variant`() {
        assertEquals(ClientType.CLIENT_WEB, ClientType.WEB.serialName)
        assertEquals(ClientType.CLIENT_ANDROID, ClientType.ANDROID.serialName)
        assertEquals(ClientType.CLIENT_IOS, ClientType.IOS.serialName)
        assertEquals(ClientType.CLIENT_DESKTOP, ClientType.DESKTOP.serialName)
    }

    @Test
    fun `fromValue accepts case-insensitive enum names and wire strings`() {
        assertEquals(ClientType.WEB, ClientType.fromValueOrNull("WEB"))
        assertEquals(ClientType.WEB, ClientType.fromValueOrNull("web"))
        assertEquals(ClientType.ANDROID, ClientType.fromValueOrNull("ANDROID"))
        assertEquals(ClientType.ANDROID, ClientType.fromValueOrNull("android"))
        assertEquals(ClientType.IOS, ClientType.fromValueOrNull("Ios"))
    }

    @Test
    fun `fromValue returns null for unknown string`() {
        assertNull(ClientType.fromValueOrNull("unknown"))
        assertNull(ClientType.fromValueOrNull(""))
    }
}
