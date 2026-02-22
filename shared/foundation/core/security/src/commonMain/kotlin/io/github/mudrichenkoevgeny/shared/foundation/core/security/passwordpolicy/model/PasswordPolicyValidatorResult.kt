package io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicy.model

/**
 * Represents the outcome of a password validation check.
 */
sealed class PasswordPolicyValidatorResult {
    /**
     * Indicates the password meets all security requirements.
     */
    object Success : PasswordPolicyValidatorResult()

    /**
     * Indicates validation failure, providing specific reasons and the policy used.
     *
     * @property reasons List of specific criteria that the password failed to meet (e.g. [PasswordPolicyFailReason]).
     * @property passwordPolicy The policy configuration (e.g. [PasswordPolicy]) against which the password was validated.
     */
    data class Fail(
        val reasons: List<PasswordPolicyFailReason>,
        val passwordPolicy: PasswordPolicy
    ) : PasswordPolicyValidatorResult()
}