package io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.NavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.ParameterizedNavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.RedirectNavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.management.ManagementMainNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileId
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.BaseStorageNavigationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.base.detail.StorageFileDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.management.list.ManagementStorageFileListParams
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.management.ManagementStorageRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.open.OpenStorageRoutes
import kotlinx.serialization.Serializable

/**
 * Management navigation screen routes for global storage files operations.
 */
@Serializable
sealed interface ManagementStorageNavigation : NavigationRoute {

    /**
     * Virtual entry redirect route forwarding navigation from the storage root to the storage files list.
     *
     * - **Screen Parameters:** None.
     * - **Primary API:** None.
     * - **Secondary Action APIs:** None.
     */
    @Serializable
    data object Main : ManagementStorageNavigation, RedirectNavigationRoute {

        override val route: String
            get() = BaseStorageNavigationRoutes.BASE_STORAGE_NAVIGATION_ROUTE

        override val targetRoute: String
            get() = FileList.ROUTE

        override val parentRoute: String
            get() = ManagementMainNavigation.Main.route
    }

    /**
     * Global storage file list management screen.
     *
     * - **Screen Parameters:** [ManagementStorageFileListParams]
     * - **Primary API:** [ManagementStorageRoutes.GET_FILES]
     * - **Secondary Action APIs:** None.
     */
    @Serializable
    data class FileList(
        override val params: ManagementStorageFileListParams = ManagementStorageFileListParams()
    ) : ManagementStorageNavigation, ParameterizedNavigationRoute<ManagementStorageFileListParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = ManagementMainNavigation.Main.route

        companion object {
            const val ROUTE = "$${BaseStorageNavigationRoutes.BASE_STORAGE_NAVIGATION_ROUTE}/files"
        }
    }

    /**
     * Single storage file detail screen.
     *
     * - **Screen Parameters:** [StorageFileDetailParams]
     * - **Primary API:** [ManagementStorageRoutes.GET_FILE]
     * - **Secondary Action APIs:**
     *   - [ManagementStorageRoutes.DELETE_FILE]
     */
    @Serializable
    data class FileDetail(
        override val params: StorageFileDetailParams
    ) : ManagementStorageNavigation, ParameterizedNavigationRoute<StorageFileDetailParams> {

        override val route: String
            get() = getPath(params.fileId)

        override val parentRoute: String
            get() = FileList.ROUTE

        companion object {
            const val ROUTE = "${FileList.ROUTE}/{${StorageApiPaths.FILE_ID}}"

            fun getPath(fileId: FileId): String =
                ROUTE.replace("{${StorageApiPaths.FILE_ID}}", fileId.asHexDashString())

            fun getPath(fileId: String): String =
                ROUTE.replace("{${StorageApiPaths.FILE_ID}}", fileId)
        }
    }

    /**
     * Storage file upload utility screen (e.g. for testing uploads directly from the management panel).
     *
     * - **Screen Parameters:** None.
     * - **Primary API:** None.
     * - **Secondary Action APIs:**
     *   - [OpenStorageRoutes.POST_PRESIGNED_UPLOAD_URL]
     *   - [OpenStorageRoutes.CONFIRM_UPLOAD]
     */
    @Serializable
    data object FileUpload : ManagementStorageNavigation {

        override val route: String
            get() = "${BaseStorageNavigationRoutes.BASE_STORAGE_NAVIGATION_ROUTE}/upload"

        override val parentRoute: String
            get() = FileList.ROUTE
    }
}
