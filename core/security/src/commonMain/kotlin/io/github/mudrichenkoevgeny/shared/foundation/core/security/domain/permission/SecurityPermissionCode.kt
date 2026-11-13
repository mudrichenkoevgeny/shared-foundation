package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.permission

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode

/**
 * Feature-level permission codes for security management.
 *
 * Values are the raw strings stored in [PermissionCode]; servers enforce them on the matching operations.
 */
public object SecurityPermissionCode {
    /** Allows updating platform-wide security configuration (e.g. password policy and related limits). */
    public val SECURITY_SETTINGS_UPDATE: PermissionCode = PermissionCode("security.settings.update")
}
