package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.success

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.UnlockMethod
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.contract.UserNavigationPaths
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UnlockSuccessParamsTest {

    @Test
    fun `parse extracts unlockMethod correctly`() {
        val params = mapOf(UserNavigationPaths.UNLOCK_METHOD to "phone")
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

        assertEquals(mapOf(UserNavigationPaths.UNLOCK_METHOD to listOf("email")), serialized)
        assertEquals(serialized, UnlockSuccessParams.serialize(model))
    }
}
