package io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicychecker

import io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicychecker.enums.PasswordPolicyFailReason
import io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicychecker.model.PasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicychecker.result.PasswordPolicyCheckResult
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertIs

class PasswordPolicyCheckerTest {

    private val defaultPolicy = PasswordPolicy(
        requireLetter = true,
        requireUpperCase = false,
        requireLowerCase = false,
        requireDigit = false,
        requireSpecialChar = false
    )
    private val checker = PasswordPolicyCheckerImpl(defaultPolicy)

    @Test
    fun `given valid password when check then return success`() {
        // GIVEN
        val password = "Password123"
        // WHEN
        val result = checker.check(password)
        // THEN
        assertIs<PasswordPolicyCheckResult.Success>(result)
    }

    @Test
    fun `given short password when check then return too short reason`() {
        // GIVEN
        val password = "abc"
        // WHEN
        val result = checker.check(password)
        // THEN
        val fail = assertIs<PasswordPolicyCheckResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.TOO_SHORT))
    }

    @Test
    fun `given password from common list when check then return too common reason`() {
        // GIVEN
        val password = "qwertyui"
        // WHEN
        val result = checker.check(password)
        // THEN
        val fail = assertIs<PasswordPolicyCheckResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.TOO_COMMON))
    }

    @Test
    fun `given policy with digits required when check without digit then return no digit reason`() {
        // GIVEN
        val policy = PasswordPolicy(requireDigit = true)
        val customChecker = PasswordPolicyCheckerImpl(policy)
        val password = "NoDigitsHere"
        // WHEN
        val result = customChecker.check(password)
        // THEN
        val fail = assertIs<PasswordPolicyCheckResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_DIGIT))
    }

    @Test
    fun `given policy with uppercase required when check with lowercase only then return no uppercase reason`() {
        // GIVEN
        val policy = PasswordPolicy(requireUpperCase = true)
        val customChecker = PasswordPolicyCheckerImpl(policy)
        val password = "onlylowercase123"
        // WHEN
        val result = customChecker.check(password)
        // THEN
        val fail = assertIs<PasswordPolicyCheckResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_UPPERCASE))
    }

    @Test
    fun `given policy with special char required when check without special char then return no special char reason`() {
        // GIVEN
        val policy = PasswordPolicy(requireSpecialChar = true)
        val customChecker = PasswordPolicyCheckerImpl(policy)
        val password = "NoSpecialChar123"
        // WHEN
        val result = customChecker.check(password)
        // THEN
        val fail = assertIs<PasswordPolicyCheckResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_SPECIAL_CHAR))
    }

    @Test
    fun `given password missing everything when check then return multiple reasons`() {
        // GIVEN
        val strictPolicy = PasswordPolicy(
            minLength = 10,
            requireUpperCase = true,
            requireSpecialChar = true,
            requireDigit = true
        )
        val strictChecker = PasswordPolicyCheckerImpl(strictPolicy)
        val password = "abc"
        // WHEN
        val result = strictChecker.check(password)
        // THEN
        val fail = assertIs<PasswordPolicyCheckResult.Fail>(result)
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.TOO_SHORT))
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_UPPERCASE))
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_DIGIT))
        assertTrue(fail.reasons.contains(PasswordPolicyFailReason.NO_SPECIAL_CHAR))
    }
}