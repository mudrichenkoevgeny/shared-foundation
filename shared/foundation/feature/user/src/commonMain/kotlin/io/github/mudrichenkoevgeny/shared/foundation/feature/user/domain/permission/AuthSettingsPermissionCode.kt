package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode

/**
 * Feature-level permission codes for authentication settings management.
 *
 * Values are the raw strings stored in [PermissionCode]; servers enforce them on the matching operations.
 */
object AuthSettingsPermissionCode {
    /** Allows updating global authentication settings. */
    val AUTH_SETTINGS_UPDATE = PermissionCode("auth.settings.update")
}