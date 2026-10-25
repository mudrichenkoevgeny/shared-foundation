package io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract

/**
 * Storage specific naming conventions for API request parameters and response fields.
 */
object StorageApiFields {
    /** JSON field name for unique file identifier. */
    const val FILE_ID = "file_id"

    /** JSON field name for file name. */
    const val FILE_NAME = "file_name"

    /** JSON field name for file size in bytes. */
    const val FILE_SIZE_BYTES = "file_size_bytes"

    /** JSON field name for MIME type. */
    const val MIME_TYPE = "mime_type"

    /** JSON field name for storage category. */
    const val CATEGORY = "category"

    /** JSON field name for target scope or feature context. */
    const val TARGET_SCOPE = "target_scope"

    /** JSON field name for storage visibility. */
    const val VISIBILITY = "visibility"

    /** JSON field name for file status. */
    const val STATUS = "status"

    /** JSON field name for S3 presigned upload URL. */
    const val UPLOAD_URL = "upload_url"

    /** JSON field name for public or presigned download/view file URL. */
    const val FILE_URL = "file_url"

    /** JSON field name for owner user ID. */
    const val OWNER_USER_ID = "owner_user_id"

    /** JSON field name for expiration timestamp in Unix epoch milliseconds. */
    const val EXPIRES_AT = "expires_at"

    /** JSON field name for minimum file upload size in bytes. */
    const val MIN_UPLOAD_SIZE_BYTES = "min_upload_size_bytes"

    /** JSON field name for maximum file upload size in bytes. */
    const val MAX_UPLOAD_SIZE_BYTES = "max_upload_size_bytes"

    /** JSON field name for total storage quota in bytes for open users. */
    const val TOTAL_STORAGE_QUOTA_FOR_OPEN_USER_BYTES = "total_storage_quota_for_open_user_bytes"

    /** JSON field name for total storage quota in bytes for management users. */
    const val TOTAL_STORAGE_QUOTA_FOR_MANAGEMENT_USER_BYTES = "total_storage_quota_for_management_user_bytes"

    /** JSON field name for presigned URL expiration time in seconds. */
    const val PRESIGNED_URL_EXPIRATION_SECONDS = "presigned_url_expiration_seconds"

    /** JSON field name for the maximum number of pending uploads allowed for open users. */
    const val MAX_PENDING_UPLOADS_FOR_OPEN_USER = "max_pending_uploads_for_open_user"

    /** JSON field name for the maximum number of pending uploads allowed for management users. */
    const val MAX_PENDING_UPLOADS_FOR_MANAGEMENT_USER = "max_pending_uploads_for_management_user"

    /** JSON field name for enabling file content deduplication. */
    const val IS_DEDUPLICATION_ENABLED = "is_deduplication_enabled"

    /** JSON field name for background worker cleanup execution interval in seconds for deleted files. */
    const val DELETED_FILES_CLEANUP_INTERVAL_SECONDS = "deleted_files_cleanup_interval_seconds"
}
