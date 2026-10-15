package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.totp.main

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.totp.TotpStep
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.contract.UserNavigationPaths
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class TotpMainParamsTest {

    @Test
    fun `parse extracts totpStep correctly`() {
        val params = mapOf(UserNavigationPaths.TOTP_STEP to "setup")
        val parsed = TotpMainParams.parse(params)

        assertEquals(TotpStep.SETUP, parsed.totpStep)
    }

    @Test
    fun `parse returns null for missing or invalid totpStep`() {
        val emptyParsed = TotpMainParams.parse(emptyMap())
        assertNull(emptyParsed.totpStep)

        val invalidParsed = TotpMainParams.parse(mapOf(UserNavigationPaths.TOTP_STEP to "invalid"))
        assertNull(invalidParsed.totpStep)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = TotpMainParams(totpStep = TotpStep.VERIFY)
        val serialized = model.toQueryMap()

        assertEquals(mapOf(UserNavigationPaths.TOTP_STEP to listOf("verify")), serialized)
        assertEquals(serialized, TotpMainParams.serialize(model))
    }
}
