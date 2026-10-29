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
class PasswordPolicyValidatorMock(
    var failReasons: List<PasswordPolicyFailReason> = emptyList()
) : PasswordPolicyValidator {

    override fun validate(
        managementPasswordPolicy: ManagementPasswordPolicy,
        password: String
    ): PasswordPolicyValidatorResult {
        return if (failReasons.isEmpty()) {
            PasswordPolicyValidatorResult.Success
        } else {
            PasswordPolicyValidatorResult.Fail(
                reasons = failReasons,
                passwordPolicy = managementPasswordPolicy
            )
        }
    }

    override fun validate(
        openPasswordPolicy: OpenPasswordPolicy,
        password: String
    ): PasswordPolicyValidatorResult = validate(
        openPasswordPolicy.toManagementPasswordPolicy(),
        password
    )
}
