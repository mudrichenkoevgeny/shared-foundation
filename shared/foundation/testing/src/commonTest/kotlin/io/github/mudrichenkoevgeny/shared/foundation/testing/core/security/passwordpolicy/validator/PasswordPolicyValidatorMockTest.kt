package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.passwordpolicy.validator

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.ManagementPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.OpenPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicyFailReason
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicyValidatorResult
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class PasswordPolicyValidatorMockTest {

    private val managementPolicy = ManagementPasswordPolicy(
        minLength = 8,
        requireLetter = true,
        requireUpperCase = true,
        requireLowerCase = true,
        requireDigit = true,
        requireSpecialChar = false,
        commonPasswords = emptySet()
    )

    private val openPolicy = OpenPasswordPolicy(
        minLength = 8,
        requireLetter = true,
        requireUpperCase = true,
        requireLowerCase = true,
        requireDigit = true,
        requireSpecialChar = false
    )

    @Test
    fun returnsSuccessByDefaultWhenNoFailReasonsConfigured() {
        val validator = PasswordPolicyValidatorMock()

        val managementResult = validator.validate(managementPolicy, "any_password")
        val openResult = validator.validate(openPolicy, "any_password")

        assertIs<PasswordPolicyValidatorResult.Success>(managementResult)
        assertIs<PasswordPolicyValidatorResult.Success>(openResult)
    }

    @Test
    fun returnsFailWithProvidedReasonsWhenConfigured() {
        val expectedReasons = listOf(PasswordPolicyFailReason.TOO_SHORT, PasswordPolicyFailReason.NO_DIGIT)
        val validator = PasswordPolicyValidatorMock(failReasons = expectedReasons)

        val result = validator.validate(managementPolicy, "pass")

        assertIs<PasswordPolicyValidatorResult.Fail>(result)
        assertEquals(expectedReasons, result.reasons)
        assertEquals(managementPolicy, result.passwordPolicy)
    }
}
