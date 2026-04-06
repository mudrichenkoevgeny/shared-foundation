package io.github.mudrichenkoevgeny.shared.foundation.feature.settingsApi.domain.permission

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode

/**
 * Feature-level permission codes for global (platform-wide) settings.
 *
 * Values are the raw strings stored in [PermissionCode]; servers enforce them on the matching operations.
 */
object SettingsPermissionCode {
    /** Allows updating platform-wide global settings. */
    val GLOBAL_SETTINGS_UPDATE = PermissionCode("global.settings.update")
}