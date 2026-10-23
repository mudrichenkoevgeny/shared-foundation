package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.audit.metadata

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.error.naming.StorageErrorCodes

/**
 * Suggested wire values for [AuditEventMetadata.value] when [AuditEventMetadata.key] resolves to
 * [CommonAuditMetadataKey.DENIED_REASON] in storage management audit flows.
 */
object StorageAuditMetadataDeniedReasonValues {

    /**
     * File management operation failed because the requested file was not found.
     * Associated with [StorageErrorCodes.FILE_NOT_FOUND].
     */
    const val FILE_NOT_FOUND = "file_not_found"

    /**
     * File deletion failed during physical S3 object cleanup or database record modification.
     * Associated with [StorageErrorCodes.FILE_DELETE_FAILED].
     */
    const val FILE_DELETE_FAILED = "file_delete_failed"
}
