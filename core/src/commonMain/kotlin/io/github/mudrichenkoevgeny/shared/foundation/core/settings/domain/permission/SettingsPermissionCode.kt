package io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.permission

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode

/**
 * Feature-level permission codes for global (platform-wide) settings.
 *
 * Values are the raw strings stored in [PermissionCode]; servers enforce them on the matching operations.
 */
public object SettingsPermissionCode {
    /** Allows updating platform-wide global settings. */
    public val GLOBAL_SETTINGS_UPDATE: PermissionCode = PermissionCode("global.settings.update")
}
