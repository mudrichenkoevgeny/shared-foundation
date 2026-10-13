package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.totp

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.totp.LoginByTotpMode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LoginByTotpParamsParserTest {

    @Test
    fun `parse extracts mfaToken and loginByTotpMode correctly`() {
        val params = mapOf(
            SecurityApiFields.MFA_TOKEN to "mfa_123",
            LoginByTotpMode.PARAM_NAME to "recovery_code"
        )
        val parsed = LoginByTotpParams.parse(params)

        assertEquals("mfa_123", parsed.mfaToken)
        assertEquals(LoginByTotpMode.RECOVERY_CODE, parsed.loginByTotpMode)
    }

    @Test
    fun `parse returns null for missing or invalid values`() {
        val emptyParsed = LoginByTotpParams.parse(emptyMap())
        assertNull(emptyParsed.mfaToken)
        assertNull(emptyParsed.loginByTotpMode)

        val invalidMode = LoginByTotpParams.parse(mapOf(LoginByTotpMode.PARAM_NAME to "invalid_mode"))
        assertNull(invalidMode.loginByTotpMode)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = LoginByTotpParams(mfaToken = "mfa_123", loginByTotpMode = LoginByTotpMode.TOTP)
        val serialized = model.toQueryMap()

        assertEquals(listOf("mfa_123"), serialized[SecurityApiFields.MFA_TOKEN])
        assertEquals(listOf("totp"), serialized[LoginByTotpMode.PARAM_NAME])
        assertEquals(serialized, LoginByTotpParamsParser.serialize(model))
    }
}
