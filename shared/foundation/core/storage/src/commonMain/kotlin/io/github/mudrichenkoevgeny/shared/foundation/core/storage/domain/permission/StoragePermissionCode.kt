package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.permission

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode

/**
 * Feature-level permission codes for storage management.
 *
 * Values are the raw strings stored in [PermissionCode]; servers enforce them on matching operations.
 */
object StoragePermissionCode {
    /** Allows deleting any file in storage regardless of ownership. */
    val STORAGE_DELETE_ANY_FILE = PermissionCode("storage.delete.any.file")

    /** Allows inspecting metadata for any file in storage. */
    val STORAGE_VIEW_METADATA = PermissionCode("storage.view.metadata")

    /** Allows updating platform-wide storage configuration and limits. */
    val STORAGE_SETTINGS_UPDATE = PermissionCode("storage.settings.update")

    /**
     * All storage permissions.
     */
    val ALL: Set<PermissionCode> = setOf(
        STORAGE_DELETE_ANY_FILE,
        STORAGE_VIEW_METADATA,
        STORAGE_SETTINGS_UPDATE
    )
}
