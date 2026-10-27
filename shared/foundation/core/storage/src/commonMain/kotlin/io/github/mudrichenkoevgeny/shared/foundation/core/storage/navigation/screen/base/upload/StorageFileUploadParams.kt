package io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.base.upload

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.contract.StorageNavigationPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiFields
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for a storage file upload screen.
 *
 * @property maxFiles Maximum number of files permitted to be selected or uploaded in a single batch; wire key is [StorageNavigationPaths.MAX_FILES].
 * @property targetScope Target scope or feature context string (e.g., 'avatar', 'ticket_attachment'); wire key is [StorageApiFields.TARGET_SCOPE].
 * @property category Target storage category to restrict upload selection; wire key is [StorageApiFields.CATEGORY].
 * @property acceptedFileTypes List of accepted file extensions or MIME types (e.g., [".jpg", ".png", "image/png"]); wire key is [StorageNavigationPaths.ACCEPTED_FILE_TYPES].
 * @property maxFileSizeBytes Maximum file size limit in bytes for this upload context; wire key is [StorageNavigationPaths.MAX_FILE_SIZE_BYTES].
 * @property autoSubmit Whether to automatically trigger upload submission upon file selection; wire key is [StorageNavigationPaths.AUTO_SUBMIT].
 */
@Serializable
data class StorageFileUploadParams(
    val maxFiles: Int? = null,
    val targetScope: String? = null,
    val category: StorageCategory? = null,
    val acceptedFileTypes: List<String> = emptyList(),
    val maxFileSizeBytes: Long? = null,
    val autoSubmit: Boolean = false
) : NavigationScreenParams<StorageFileUploadParams> {

    override val parser: NavigationScreenParamsParser<StorageFileUploadParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<StorageFileUploadParams> {

        override fun parse(params: Map<String, Any?>): StorageFileUploadParams {
            val normalized = params.normalizeQueryParams()

            val maxFiles = normalized[StorageNavigationPaths.MAX_FILES]
                ?.firstOrNull()
                ?.toIntOrNull()
                ?.takeIf { it > 0 }

            val targetScope = normalized[StorageApiFields.TARGET_SCOPE]
                ?.firstOrNull()
                ?.trim()
                ?.takeIf { it.isNotBlank() }

            val category = normalized[StorageApiFields.CATEGORY]
                ?.firstOrNull()
                ?.let { StorageCategory.fromValueOrNull(it) }

            val acceptedFileTypes = normalized[StorageNavigationPaths.ACCEPTED_FILE_TYPES]
                ?.flatMap { it.split(",") }
                ?.map { it.trim() }
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            val maxFileSizeBytes = normalized[StorageNavigationPaths.MAX_FILE_SIZE_BYTES]
                ?.firstOrNull()
                ?.toLongOrNull()
                ?.takeIf { it > 0 }

            val autoSubmit = normalized[StorageNavigationPaths.AUTO_SUBMIT]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()
                ?: false

            return StorageFileUploadParams(
                maxFiles = maxFiles,
                targetScope = targetScope,
                category = category,
                acceptedFileTypes = acceptedFileTypes,
                maxFileSizeBytes = maxFileSizeBytes,
                autoSubmit = autoSubmit
            )
        }

        override fun serialize(params: StorageFileUploadParams): Map<String, List<String>> {
            val result = mutableMapOf<String, List<String>>()

            if (params.maxFiles != null && params.maxFiles > 0) {
                result[StorageNavigationPaths.MAX_FILES] = listOf(params.maxFiles.toString())
            }
            if (!params.targetScope.isNullOrBlank()) {
                result[StorageApiFields.TARGET_SCOPE] = listOf(params.targetScope.trim())
            }
            if (params.category != null) {
                result[StorageApiFields.CATEGORY] = listOf(params.category.value)
            }
            if (params.acceptedFileTypes.isNotEmpty()) {
                result[StorageNavigationPaths.ACCEPTED_FILE_TYPES] = params.acceptedFileTypes
            }
            if (params.maxFileSizeBytes != null && params.maxFileSizeBytes > 0) {
                result[StorageNavigationPaths.MAX_FILE_SIZE_BYTES] = listOf(params.maxFileSizeBytes.toString())
            }
            if (params.autoSubmit) {
                result[StorageNavigationPaths.AUTO_SUBMIT] = listOf("true")
            }

            return result
        }
    }
}
