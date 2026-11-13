package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode

/**
 * Feature-level permission codes for authentication settings management.
 *
 * Values are the raw strings stored in [PermissionCode]; servers enforce them on the matching operations.
 */
public object AuthSettingsPermissionCode {
    /** Allows updating global authentication settings. */
    public val AUTH_SETTINGS_UPDATE: PermissionCode = PermissionCode("auth.settings.update")
}
