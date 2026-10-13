package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.profile.totp.main

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.totp.TotpStep
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class TotpMainParamsParserTest {

    @Test
    fun `parse extracts totpStep correctly`() {
        val params = mapOf(TotpStep.PARAM_NAME to "setup")
        val parsed = TotpMainParams.parse(params)

        assertEquals(TotpStep.SETUP, parsed.totpStep)
    }

    @Test
    fun `parse returns null for missing or invalid totpStep`() {
        val emptyParsed = TotpMainParams.parse(emptyMap())
        assertNull(emptyParsed.totpStep)

        val invalidParsed = TotpMainParams.parse(mapOf(TotpStep.PARAM_NAME to "invalid"))
        assertNull(invalidParsed.totpStep)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = TotpMainParams(totpStep = TotpStep.VERIFY)
        val serialized = model.toQueryMap()

        assertEquals(mapOf(TotpStep.PARAM_NAME to listOf("verify")), serialized)
        assertEquals(serialized, TotpMainParamsParser.serialize(model))
    }
}
