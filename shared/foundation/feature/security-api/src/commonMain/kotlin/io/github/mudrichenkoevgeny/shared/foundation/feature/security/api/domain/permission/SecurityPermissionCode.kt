package io.github.mudrichenkoevgeny.shared.foundation.feature.security.api.domain.permission

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode

/**
 * Feature-level permission codes for security management.
 *
 * Values are the raw strings stored in [PermissionCode]; servers enforce them on the matching operations.
 */
object SecurityPermissionCode {
    /** Allows updating platform-wide security configuration (e.g. password policy and related limits). */
    val SECURITY_SETTINGS_UPDATE = PermissionCode("security.settings.update")
}