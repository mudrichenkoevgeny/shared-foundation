package io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicy.validator

import io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicy.model.PasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicy.model.PasswordPolicyFailReason
import io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicy.model.PasswordPolicyValidatorResult
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
    private val validator = PasswordPolicyValidatorImpl(defaultPolicy)

    @Test
    fun `given valid password when validate then return success`() {
        // GIVEN
        val password = "Password123"
        // WHEN
        val result = validator.validate(password)
        // THEN
        assertIs<PasswordPolicyValidatorResult.Success>(result)
    }

    @Test
    fun `given short password when validate then return too short reason`() {
        // GIVEN
        val password = "abc"
        // WHEN
        val result = validator.validate(password)
        // THEN
        val fail = assertIs<PasswordPolicyValidatorResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.TOO_SHORT))
    }

    @Test
    fun `given password from common list when validate then return too common reason`() {
        // GIVEN
        val password = "qwertyui"
        // WHEN
        val result = validator.validate(password)
        // THEN
        val fail = assertIs<PasswordPolicyValidatorResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.TOO_COMMON))
    }

    @Test
    fun `given policy with digits required when validate without digit then return no digit reason`() {
        // GIVEN
        val policy = PasswordPolicy(requireDigit = true)
        val customValidator = PasswordPolicyValidatorImpl(policy)
        val password = "NoDigitsHere"
        // WHEN
        val result = customValidator.validate(password)
        // THEN
        val fail = assertIs<PasswordPolicyValidatorResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_DIGIT))
    }

    @Test
    fun `given policy with uppercase required when validate with lowercase only then return no uppercase reason`() {
        // GIVEN
        val policy = PasswordPolicy(requireUpperCase = true)
        val customValidator = PasswordPolicyValidatorImpl(policy)
        val password = "onlylowercase123"
        // WHEN
        val result = customValidator.validate(password)
        // THEN
        val fail = assertIs<PasswordPolicyValidatorResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_UPPERCASE))
    }

    @Test
    fun `given policy with special char required when validate without special char then return no special char reason`() {
        // GIVEN
        val policy = PasswordPolicy(requireSpecialChar = true)
        val customValidator = PasswordPolicyValidatorImpl(policy)
        val password = "NoSpecialChar123"
        // WHEN
        val result = customValidator.validate(password)
        // THEN
        val fail = assertIs<PasswordPolicyValidatorResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_SPECIAL_CHAR))
    }

    @Test
    fun `given password missing everything when validate then return multiple reasons`() {
        // GIVEN
        val strictPolicy = PasswordPolicy(
            minLength = 10,
            requireUpperCase = true,
            requireSpecialChar = true,
            requireDigit = true
        )
        val strictValidator = PasswordPolicyValidatorImpl(strictPolicy)
        val password = "abc"
        // WHEN
        val result = strictValidator.validate(password)
        // THEN
        val fail = assertIs<PasswordPolicyValidatorResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.TOO_SHORT))
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_UPPERCASE))
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_DIGIT))
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_SPECIAL_CHAR))
    }
}