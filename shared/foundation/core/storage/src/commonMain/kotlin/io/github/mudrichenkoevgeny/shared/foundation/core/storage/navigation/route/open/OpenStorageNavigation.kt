package io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.open

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.NavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.ParameterizedNavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.RedirectNavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.client.ClientMainNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileId
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.BaseStorageNavigationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.base.detail.StorageFileDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.base.upload.StorageFileUploadParams
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.open.list.SelfStorageFileListParams
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.open.OpenStorageRoutes
import kotlinx.serialization.Serializable

/**
 * Navigation screen routes for open/self-service storage operations.
 */
@Serializable
sealed interface OpenStorageNavigation : NavigationRoute {

    /**
     * Virtual entry redirect route forwarding navigation from the storage root to the storage files list.
     *
     * - **Screen Parameters:** None.
     * - **Primary API:** None.
     * - **Secondary Action APIs:** None.
     */
    @Serializable
    data object Main : OpenStorageNavigation, RedirectNavigationRoute {

        override val route: String
            get() = BaseStorageNavigationRoutes.BASE_STORAGE_NAVIGATION_ROUTE

        override val targetRoute: String
            get() = FileList.ROUTE

        override val parentRoute: String
            get() = ClientMainNavigation.Main.route
    }

    /**
     * Self-service storage file list screen.
     *
     * - **Screen Parameters:** [SelfStorageFileListParams]
     * - **Primary API:** [OpenStorageRoutes.GET_FILES]
     * - **Secondary Action APIs:** None.
     */
    @Serializable
    data class FileList(
        override val params: SelfStorageFileListParams = SelfStorageFileListParams()
    ) : OpenStorageNavigation, ParameterizedNavigationRoute<SelfStorageFileListParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = ClientMainNavigation.Main.route

        companion object {
            const val ROUTE = "${BaseStorageNavigationRoutes.BASE_STORAGE_NAVIGATION_ROUTE}/files"
        }
    }

    /**
     * Self-service storage file detail screen.
     *
     * - **Screen Parameters:** [StorageFileDetailParams]
     * - **Primary API:** [OpenStorageRoutes.GET_FILE]
     * - **Secondary Action APIs:**
     *   - [OpenStorageRoutes.DELETE_FILE]
     */
    @Serializable
    data class FileDetail(
        override val params: StorageFileDetailParams
    ) : OpenStorageNavigation, ParameterizedNavigationRoute<StorageFileDetailParams> {

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
     * Storage file upload utility screen.
     *
     * - **Screen Parameters:** [StorageFileUploadParams]
     * - **Primary API:** None.
     * - **Secondary Action APIs:**
     *   - [OpenStorageRoutes.POST_PRESIGNED_UPLOAD_URL]
     *   - [OpenStorageRoutes.CONFIRM_UPLOAD]
     */
    @Serializable
    data class FileUpload(
        override val params: StorageFileUploadParams = StorageFileUploadParams()
    ) : OpenStorageNavigation, ParameterizedNavigationRoute<StorageFileUploadParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = FileList.ROUTE

        companion object {
            const val ROUTE = "${BaseStorageNavigationRoutes.BASE_STORAGE_NAVIGATION_ROUTE}/upload"
        }
    }
}
