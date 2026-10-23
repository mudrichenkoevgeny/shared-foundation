package io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.base.detail

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileId
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.toFileIdOrNull
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiPaths
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for a storage file detail screen.
 *
 * @property fileId Raw string representation of the target file ID extracted from path parameters;
 * wire key is [StorageApiPaths.FILE_ID]. May be empty or unvalidated until parsed via [typedFileId].
 */
@Serializable
data class StorageFileDetailParams(
    val fileId: String = ""
) : NavigationScreenParams<StorageFileDetailParams> {

    /**
     * Parsed strongly typed [FileId] representation of [fileId].
     *
     * Returns `null` if [fileId] is blank or does not conform to the canonical UUID format.
     */
    val typedFileId: FileId?
        get() = fileId.toFileIdOrNull()

    override val parser: NavigationScreenParamsParser<StorageFileDetailParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<StorageFileDetailParams> {

        /**
         * Parses raw Deep Link or Web Router query parameters into typed [StorageFileDetailParams].
         */
        override fun parse(params: Map<String, Any?>): StorageFileDetailParams {
            val normalized = params.normalizeQueryParams()
            val fileId = normalized[StorageApiPaths.FILE_ID]?.firstOrNull()?.trim() ?: ""
            return StorageFileDetailParams(fileId = fileId)
        }

        /**
         * Serializes [StorageFileDetailParams] into a map of URL query parameter strings.
         *
         * Returns an empty map because the file ID is represented as a path parameter in the route URL.
         */
        override fun serialize(params: StorageFileDetailParams): Map<String, List<String>> = emptyMap()
    }
}
