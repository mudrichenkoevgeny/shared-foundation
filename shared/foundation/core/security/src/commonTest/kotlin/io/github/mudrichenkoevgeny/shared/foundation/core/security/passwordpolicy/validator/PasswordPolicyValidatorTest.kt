package io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicy.validator

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.ManagementPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicyFailReason
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicyValidatorResult
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertTrue

class PasswordPolicyValidatorTest {
    private val defaultPolicy =
        ManagementPasswordPolicy(
            minLength = ManagementPasswordPolicy.DEFAULT_MIN_LENGTH,
            requireLetter = true,
            requireUpperCase = false,
            requireLowerCase = false,
            requireDigit = false,
            requireSpecialChar = false,
            commonPasswords = ManagementPasswordPolicy.DEFAULT_COMMON_PASSWORDS
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
        val policy = defaultPolicy.copy(requireDigit = true)
        val password = "NoDigitsHere"
        val result = validator.validate(policy, password)
        val fail = assertIs<PasswordPolicyValidatorResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_DIGIT))
    }

    @Test
    fun `given policy with uppercase required when validate with lowercase only then return no uppercase reason`() {
        val policy = defaultPolicy.copy(requireUpperCase = true)
        val password = "onlylowercase123"
        val result = validator.validate(policy, password)
        val fail = assertIs<PasswordPolicyValidatorResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_UPPERCASE))
    }

    @Test
    fun `given special char required when validate without it then return no special char reason`() {
        val policy = defaultPolicy.copy(requireSpecialChar = true)
        val password = "NoSpecialChar123"
        val result = validator.validate(policy, password)
        val fail = assertIs<PasswordPolicyValidatorResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_SPECIAL_CHAR))
    }

    @Test
    fun `given password missing everything when validate then return multiple reasons`() {
        val strictPolicy =
            ManagementPasswordPolicy(
                minLength = 10,
                requireLetter = true,
                requireUpperCase = true,
                requireLowerCase = true,
                requireDigit = true,
                requireSpecialChar = true,
                commonPasswords = ManagementPasswordPolicy.DEFAULT_COMMON_PASSWORDS
            )
        val password = "abc"
        val result = validator.validate(strictPolicy, password)
        val fail = assertIs<PasswordPolicyValidatorResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.TOO_SHORT))
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_UPPERCASE))
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_DIGIT))
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_SPECIAL_CHAR))
    }

    @Test
    fun `given empty password when validate then return multiple reasons`() {
        val strictPolicy =
            ManagementPasswordPolicy(
                minLength = 8,
                requireLetter = true,
                requireUpperCase = true,
                requireLowerCase = true,
                requireDigit = true,
                requireSpecialChar = true,
                commonPasswords = ManagementPasswordPolicy.DEFAULT_COMMON_PASSWORDS
            )
        val result = validator.validate(strictPolicy, "")
        val fail = assertIs<PasswordPolicyValidatorResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.TOO_SHORT))
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_LETTER))
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_UPPERCASE))
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_LOWERCASE))
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_DIGIT))
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_SPECIAL_CHAR))
    }

    @Test
    fun `given exact min length password when validate then return success`() {
        val policy = defaultPolicy.copy(minLength = 8)
        val password = "Password"
        val result = validator.validate(policy, password)
        assertIs<PasswordPolicyValidatorResult.Success>(result)
    }

    @Test
    fun `given extremely long password when validate then return success`() {
        val password = "P" + "a".repeat(1000) + "1"
        val result = validator.validate(defaultPolicy, password)
        assertIs<PasswordPolicyValidatorResult.Success>(result)
    }

    @Test
    fun `given policy with lowercase required when validate with uppercase only then return no lowercase reason`() {
        val policy = defaultPolicy.copy(requireLowerCase = true)
        val password = "ONLYUPPERCASE123"
        val result = validator.validate(policy, password)
        val fail = assertIs<PasswordPolicyValidatorResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_LOWERCASE))
    }

    @Test
    fun `given letter required when validate without letters then return no letter reason`() {
        val policy = defaultPolicy.copy(requireLetter = true)
        val password = "123!@#"
        val result = validator.validate(policy, password)
        val fail = assertIs<PasswordPolicyValidatorResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_LETTER))
    }

    @Test
    fun `given password with emojis when validate then evaluates characters correctly`() {
        val policy = defaultPolicy.copy(requireSpecialChar = true)
        val password = "Password123😎"
        val result = validator.validate(policy, password)
        assertIs<PasswordPolicyValidatorResult.Success>(result)
    }
}
