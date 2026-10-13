package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.success

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.unlock.UnlockMethod
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UnlockSuccessParamsParserTest {

    @Test
    fun `parse extracts unlockMethod correctly`() {
        val params = mapOf(UnlockMethod.PARAM_NAME to "phone")
        val parsed = UnlockSuccessParams.parse(params)

        assertEquals(UnlockMethod.PHONE, parsed.unlockMethod)
    }

    @Test
    fun `parse returns null for invalid or missing unlockMethod`() {
        val emptyParsed = UnlockSuccessParams.parse(emptyMap())
        assertNull(emptyParsed.unlockMethod)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = UnlockSuccessParams(unlockMethod = UnlockMethod.EMAIL)
        val serialized = model.toQueryMap()

        assertEquals(mapOf(UnlockMethod.PARAM_NAME to listOf("email")), serialized)
        assertEquals(serialized, UnlockSuccessParamsParser.serialize(model))
    }
}
