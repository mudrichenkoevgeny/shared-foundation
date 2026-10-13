package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.welcome

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.login.LoginStep
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LoginWelcomeParamsParserTest {

    @Test
    fun `parse extracts loginStep correctly`() {
        val valid = LoginWelcomeParams.parse(mapOf(LoginStep.PARAM_NAME to "email"))
        assertEquals(LoginStep.EMAIL, valid.loginStep)
    }

    @Test
    fun `parse returns null for invalid or missing loginStep`() {
        val invalid = LoginWelcomeParams.parse(mapOf(LoginStep.PARAM_NAME to "invalid_step"))
        assertNull(invalid.loginStep)

        val missing = LoginWelcomeParams.parse(emptyMap())
        assertNull(missing.loginStep)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = LoginWelcomeParams(loginStep = LoginStep.PHONE)
        val serialized = model.toQueryMap()

        assertEquals(mapOf(LoginStep.PARAM_NAME to listOf("phone")), serialized)
        assertEquals(serialized, LoginWelcomeParamsParser.serialize(model))
    }
}
