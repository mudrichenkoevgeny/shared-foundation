package io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicychecker.result

import io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicychecker.enums.PasswordPolicyFailReason
import io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicychecker.model.PasswordPolicy

sealed class PasswordPolicyCheckResult {
    object Success : PasswordPolicyCheckResult()
    data class Fail(
        val reasons: List<PasswordPolicyFailReason>,
        val passwordPolicy: PasswordPolicy
    ) : PasswordPolicyCheckResult()
}
