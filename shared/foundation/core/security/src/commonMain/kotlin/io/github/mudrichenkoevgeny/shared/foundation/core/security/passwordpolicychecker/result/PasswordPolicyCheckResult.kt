package io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicychecker.result

import io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicychecker.enums.PasswordPolicyFailReason
import io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicychecker.model.PasswordPolicy

/**
 * Represents the outcome of a password validation check.
 */
sealed class PasswordPolicyCheckResult {
    /**
     * Indicates the password meets all security requirements.
     */
    object Success : PasswordPolicyCheckResult()

    /**
     * Indicates validation failure, providing specific reasons and the policy used.
     *
     * @property reasons List of specific criteria that the password failed to meet (e.g. [PasswordPolicyFailReason]).
     * @property passwordPolicy The policy configuration (e.g. [PasswordPolicy]) against which the password was validated.
     */
    data class Fail(
        val reasons: List<PasswordPolicyFailReason>,
        val passwordPolicy: PasswordPolicy
    ) : PasswordPolicyCheckResult()
}