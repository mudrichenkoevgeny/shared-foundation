package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.auth.create

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.create.CreateByEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL

fun createTestCreateByEmailRequest(
    email: String = TEST_EMAIL,
    password: String? = "Secret123!",
    role: String = UserRole.USER.value,
    status: String = UserAccountStatus.ACTIVE.value,
    authorityLevel: Int = 10,
    permissionCodes: Set<String> = emptySet()
) = CreateByEmailRequest(
    email = email,
    password = password,
    role = role,
    status = status,
    authorityLevel = authorityLevel,
    permissionCodes = permissionCodes
)
