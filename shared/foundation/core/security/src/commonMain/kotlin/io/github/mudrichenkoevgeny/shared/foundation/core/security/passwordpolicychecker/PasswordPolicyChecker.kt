package io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicychecker

import io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicychecker.result.PasswordPolicyCheckResult

/**
 * Component for validating passwords against security policies.
 */
interface PasswordPolicyChecker {
    /**
     * Validates the [password] and returns a [PasswordPolicyCheckResult].
     *
     * @param password The raw password string to be checked.
     * @return [PasswordPolicyCheckResult.Success] if the password is valid,
     * or [PasswordPolicyCheckResult.Fail] with a list of reasons.
     */
    fun check(password: String): PasswordPolicyCheckResult
}