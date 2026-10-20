package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.domain.model.permission

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode

/**
 * Default permission code string for unit testing.
 */
const val TEST_PERMISSION_CODE = "test.permission.read"

/**
 * Creates a test [PermissionCode] domain instance.
 *
 * @param value Permission code string.
 */
fun createTestPermissionCode(
    value: String = TEST_PERMISSION_CODE
): PermissionCode = PermissionCode(value)
