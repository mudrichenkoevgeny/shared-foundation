package io.github.mudrichenkoevgeny.shared.foundation.core.storage.ui.component.fileupload

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility

/**
 * Configuration options and constraints for the embedded storage file upload UI component.
 *
 * @property maxFiles Maximum number of files permitted to be selected or uploaded in a single batch. If null, no limit is enforced.
 * @property targetScope Target scope or feature context string (e.g., 'avatar', 'ticket_attachment').
 * @property category Target storage category to restrict upload selection (e.g., IMAGE, DOCUMENT). If null, the user selects the category manually or the UI infers it automatically.
 * @property visibility Target storage file visibility (e.g., PUBLIC, PRIVATE). If null, the user selects the visibility manually in the UI for each file.
 * @property acceptedFileTypes List of accepted file extensions or MIME types (e.g., [".jpg", ".png", "image/png"]). An empty list indicates that all file types are permitted.
 * @property minFileSizeBytes Minimum file size limit in bytes for this upload context. If null, no minimum size limit is enforced.
 * @property maxFileSizeBytes Maximum file size limit in bytes for this upload context. If null, no maximum size limit is enforced.
 * @property autoSubmit Whether to automatically trigger upload submission upon file selection.
 */
data class StorageFileUploadUiConfig(
    val maxFiles: Int? = null,
    val targetScope: String? = null,
    val category: StorageCategory? = null,
    val visibility: StorageFileVisibility? = null,
    val acceptedFileTypes: List<String> = emptyList(),
    val minFileSizeBytes: Long? = null,
    val maxFileSizeBytes: Long? = null,
    val autoSubmit: Boolean = false
)
