package io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicychecker

import io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicychecker.result.PasswordPolicyCheckResult

interface PasswordPolicyChecker {
    fun check(password: String): PasswordPolicyCheckResult
}