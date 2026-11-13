package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy

/**
 * Represents the outcome of a password validation check.
 */
public sealed class PasswordPolicyValidatorResult {
    /**
     * Indicates the password meets all security requirements.
     */
    public object Success : PasswordPolicyValidatorResult()

    /**
     * Indicates validation failure, providing specific reasons and the policy used.
     *
     * @property reasons List of specific criteria that the password failed to meet (e.g. [PasswordPolicyFailReason]).
     * @property passwordPolicy The policy configuration against which the password was validated.
     */
    public data class Fail(
        public val reasons: List<PasswordPolicyFailReason>,
        public val passwordPolicy: ManagementPasswordPolicy
    ) : PasswordPolicyValidatorResult()
}
