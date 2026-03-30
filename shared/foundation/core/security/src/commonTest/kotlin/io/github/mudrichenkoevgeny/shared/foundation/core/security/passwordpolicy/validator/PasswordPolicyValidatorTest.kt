package io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicy.validator

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicyFailReason
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicyValidatorResult
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertTrue

class PasswordPolicyValidatorTest {

    private val defaultPolicy = PasswordPolicy(
        requireLetter = true,
        requireUpperCase = false,
        requireLowerCase = false,
        requireDigit = false,
        requireSpecialChar = false
    )
    private val validator = PasswordPolicyValidatorImpl()

    @Test
    fun `given valid password when validate then return success`() {
        val password = "Password123"
        val result = validator.validate(defaultPolicy, password)
        assertIs<PasswordPolicyValidatorResult.Success>(result)
    }

    @Test
    fun `given short password when validate then return too short reason`() {
        val password = "abc"
        val result = validator.validate(defaultPolicy, password)
        val fail = assertIs<PasswordPolicyValidatorResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.TOO_SHORT))
    }

    @Test
    fun `given password from common list when validate then return too common reason`() {
        val password = "qwertyui"
        val result = validator.validate(defaultPolicy, password)
        val fail = assertIs<PasswordPolicyValidatorResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.TOO_COMMON))
    }

    @Test
    fun `given policy with digits required when validate without digit then return no digit reason`() {
        val policy = PasswordPolicy(requireDigit = true)
        val password = "NoDigitsHere"
        val result = validator.validate(policy, password)
        val fail = assertIs<PasswordPolicyValidatorResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_DIGIT))
    }

    @Test
    fun `given policy with uppercase required when validate with lowercase only then return no uppercase reason`() {
        val policy = PasswordPolicy(requireUpperCase = true)
        val password = "onlylowercase123"
        val result = validator.validate(policy, password)
        val fail = assertIs<PasswordPolicyValidatorResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_UPPERCASE))
    }

    @Test
    fun `given policy with special char required when validate without special char then return no special char reason`() {
        val policy = PasswordPolicy(requireSpecialChar = true)
        val password = "NoSpecialChar123"
        val result = validator.validate(policy, password)
        val fail = assertIs<PasswordPolicyValidatorResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_SPECIAL_CHAR))
    }

    @Test
    fun `given password missing everything when validate then return multiple reasons`() {
        val strictPolicy = PasswordPolicy(
            minLength = 10,
            requireUpperCase = true,
            requireSpecialChar = true,
            requireDigit = true
        )
        val password = "abc"
        val result = validator.validate(strictPolicy, password)
        val fail = assertIs<PasswordPolicyValidatorResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.TOO_SHORT))
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_UPPERCASE))
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_DIGIT))
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_SPECIAL_CHAR))
    }
}
