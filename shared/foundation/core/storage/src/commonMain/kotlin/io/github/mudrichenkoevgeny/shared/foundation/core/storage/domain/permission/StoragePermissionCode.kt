package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.permission

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode

/**
 * Feature-level permission codes for storage management.
 *
 * Values are the raw strings stored in [PermissionCode]; servers enforce them on matching operations.
 */
object StoragePermissionCode {
    /** Allows deleting files uploaded by other users, subject to authority level restrictions. */
    val STORAGE_DELETE_OTHER_USERS_FILES = PermissionCode("storage.delete.other.users.files")

    /** Allows inspecting and retrieving files uploaded by other users, subject to authority level restrictions. */
    val STORAGE_VIEW_OTHER_USERS_FILES = PermissionCode("storage.view.other.users.files")

    /** Allows updating platform-wide storage configuration and limits. */
    val STORAGE_SETTINGS_UPDATE = PermissionCode("storage.settings.update")

    /**
     * All storage permissions.
     */
    val ALL: Set<PermissionCode> = setOf(
        STORAGE_DELETE_OTHER_USERS_FILES,
        STORAGE_VIEW_OTHER_USERS_FILES,
        STORAGE_SETTINGS_UPDATE
    )
}
