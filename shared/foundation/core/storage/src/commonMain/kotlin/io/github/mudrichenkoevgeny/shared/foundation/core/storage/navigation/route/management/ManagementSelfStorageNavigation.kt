package io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.NavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.ParameterizedNavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.RedirectNavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.management.ManagementMainNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileId
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.BaseStorageNavigationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.base.detail.StorageFileDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.base.upload.StorageFileUploadParams
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.open.list.SelfStorageFileListParams
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.management.self.SelfManagementStorageRoutes
import kotlinx.serialization.Serializable

/**
 * Management navigation screen routes for staff member self-service storage operations.
 */
@Serializable
sealed interface ManagementSelfStorageNavigation : NavigationRoute {

    /**
     * Virtual entry redirect route forwarding navigation from the storage root to the storage files list.
     *
     * - **Screen Parameters:** None.
     * - **Primary API:** None.
     * - **Secondary Action APIs:** None.
     */
    @Serializable
    data object Main : ManagementSelfStorageNavigation, RedirectNavigationRoute {

        override val route: String
            get() = BaseStorageNavigationRoutes.BASE_SELF_STORAGE_NAVIGATION_ROUTE

        override val targetRoute: String
            get() = FileList.ROUTE

        override val parentRoute: String
            get() = ManagementMainNavigation.Main.route
    }

    /**
     * Staff self-service storage file list screen.
     *
     * - **Screen Parameters:** [SelfStorageFileListParams]
     * - **Primary API:** [SelfManagementStorageRoutes.GET_FILES]
     * - **Secondary Action APIs:**
     *   - [SelfManagementStorageRoutes.DELETE_FILES_BATCH]
     */
    @Serializable
    data class FileList(
        override val params: SelfStorageFileListParams = SelfStorageFileListParams()
    ) : ManagementSelfStorageNavigation, ParameterizedNavigationRoute<SelfStorageFileListParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = ManagementMainNavigation.Main.route

        companion object {
            const val ROUTE = "${BaseStorageNavigationRoutes.BASE_SELF_STORAGE_NAVIGATION_ROUTE}/files"
        }
    }

    /**
     * Staff self-service storage file detail screen.
     *
     * - **Screen Parameters:** [StorageFileDetailParams]
     * - **Primary API:** [SelfManagementStorageRoutes.GET_FILE]
     * - **Secondary Action APIs:**
     *   - [SelfManagementStorageRoutes.DELETE_FILE]
     */
    @Serializable
    data class FileDetail(
        override val params: StorageFileDetailParams
    ) : ManagementSelfStorageNavigation, ParameterizedNavigationRoute<StorageFileDetailParams> {

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
     * Staff self-service storage file upload utility screen.
     *
     * - **Screen Parameters:** [StorageFileUploadParams]
     * - **Primary API:** [SelfManagementStorageRoutes.POST_PRESIGNED_UPLOAD_URL]
     * - **Secondary Action APIs:**
     *   - [SelfManagementStorageRoutes.CONFIRM_UPLOAD]
     */
    @Serializable
    data class FileUpload(
        override val params: StorageFileUploadParams = StorageFileUploadParams()
    ) : ManagementSelfStorageNavigation, ParameterizedNavigationRoute<StorageFileUploadParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = FileList.ROUTE

        companion object {
            const val ROUTE = "${BaseStorageNavigationRoutes.BASE_SELF_STORAGE_NAVIGATION_ROUTE}/upload"
        }
    }
}
