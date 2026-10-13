package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.targetinput

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.unlock.UnlockMethod
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UnlockTargetInputParamsParserTest {

    @Test
    fun `parse extracts unlockMethod correctly`() {
        val params = mapOf(UnlockMethod.PARAM_NAME to "email")
        val parsed = UnlockTargetInputParams.parse(params)

        assertEquals(UnlockMethod.EMAIL, parsed.unlockMethod)
    }

    @Test
    fun `parse returns null for missing unlockMethod`() {
        val emptyParsed = UnlockTargetInputParams.parse(emptyMap())
        assertNull(emptyParsed.unlockMethod)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = UnlockTargetInputParams(unlockMethod = UnlockMethod.EMAIL)
        val serialized = model.toQueryMap()

        assertEquals(mapOf(UnlockMethod.PARAM_NAME to listOf("email")), serialized)
        assertEquals(serialized, UnlockTargetInputParamsParser.serialize(model))
    }
}
