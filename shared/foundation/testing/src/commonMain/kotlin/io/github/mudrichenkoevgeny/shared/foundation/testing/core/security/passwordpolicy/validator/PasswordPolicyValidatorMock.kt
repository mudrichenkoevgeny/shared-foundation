package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.passwordpolicy.validator

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.ManagementPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.OpenPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicyFailReason
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicyValidatorResult
import io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.passwordpolicy.toManagementPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicy.validator.PasswordPolicyValidator

/**
 * Configurable mock implementation of [PasswordPolicyValidator] for testing.
 *
 * @param failReasons List of fail reasons to return when validation fails.
 */
public class PasswordPolicyValidatorMock(
    public var failReasons: List<PasswordPolicyFailReason> = emptyList()
) : PasswordPolicyValidator {
    public override fun validate(
        managementPasswordPolicy: ManagementPasswordPolicy,
        password: String
    ): PasswordPolicyValidatorResult =
        if (failReasons.isEmpty()) {
            PasswordPolicyValidatorResult.Success
        } else {
            PasswordPolicyValidatorResult.Fail(
                reasons = failReasons,
                passwordPolicy = managementPasswordPolicy
            )
        }

    public override fun validate(
        openPasswordPolicy: OpenPasswordPolicy,
        password: String
    ): PasswordPolicyValidatorResult =
        validate(
            openPasswordPolicy.toManagementPasswordPolicy(),
            password
        )
}
