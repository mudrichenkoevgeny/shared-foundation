package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.domain.model.permission

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode

/** Default deterministic permission code string for unit tests. */
public const val TEST_PERMISSION_CODE: String = "test.permission.read"

/**
* Creates a test [PermissionCode] instance for unit testing.
*
* @param value Permission code string (defaults to [TEST_PERMISSION_CODE]).
*/
public fun createTestPermissionCode(value: String = TEST_PERMISSION_CODE): PermissionCode = PermissionCode(value)
