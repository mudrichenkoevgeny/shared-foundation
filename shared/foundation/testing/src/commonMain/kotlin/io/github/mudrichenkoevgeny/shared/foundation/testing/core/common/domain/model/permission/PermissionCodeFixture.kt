package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.domain.model.permission

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode

const val TEST_PERMISSION_CODE = "test.permission.read"

fun createTestPermissionCode(
    value: String = TEST_PERMISSION_CODE
): PermissionCode = PermissionCode(value)
