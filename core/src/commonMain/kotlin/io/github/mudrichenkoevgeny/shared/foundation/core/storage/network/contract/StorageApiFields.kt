package io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract

/**
 * Storage specific naming conventions for API request parameters and response fields.
 */
public object StorageApiFields {
    /** JSON field name for unique file identifier. */
    public const val FILE_ID: String = "file_id"

    /** JSON field name for file name. */
    public const val FILE_NAME: String = "file_name"

    /** JSON field name for file size in bytes. */
    public const val FILE_SIZE_BYTES: String = "file_size_bytes"

    /** JSON field name for MIME type. */
    public const val MIME_TYPE: String = "mime_type"

    /** JSON field name for storage category. */
    public const val CATEGORY: String = "category"

    /** JSON field name for target scope or feature context. */
    public const val TARGET_SCOPE: String = "target_scope"

    /** JSON field name for storage visibility. */
    public const val VISIBILITY: String = "visibility"

    /** JSON field name for file status. */
    public const val STATUS: String = "status"

    /** JSON field name for S3 presigned upload URL. */
    public const val UPLOAD_URL: String = "upload_url"

    /** JSON field name for public or presigned download/view file URL. */
    public const val FILE_URL: String = "file_url"

    /** JSON field name for structured file URLs object. */
    public const val FILE_URLS: String = "file_urls"

    /** JSON field name for original file URL. */
    public const val ORIGINAL_URL: String = "original_url"

    /** JSON field name for map of URL presets. */
    public const val URL_PRESETS: String = "url_presets"

    /** JSON field name for owner user ID. */
    public const val OWNER_USER_ID: String = "owner_user_id"

    /** JSON field name for expiration timestamp in Unix epoch milliseconds. */
    public const val EXPIRES_AT: String = "expires_at"

    /** JSON field name for minimum file upload size in bytes. */
    public const val MIN_UPLOAD_SIZE_BYTES: String = "min_upload_size_bytes"

    /** JSON field name for maximum file upload size in bytes. */
    public const val MAX_UPLOAD_SIZE_BYTES: String = "max_upload_size_bytes"

    /** JSON field name for total storage quota in bytes for open users. */
    public const val TOTAL_STORAGE_QUOTA_FOR_OPEN_USER_BYTES: String = "total_storage_quota_for_open_user_bytes"

    /** JSON field name for total storage quota in bytes for management users. */
    public const val TOTAL_STORAGE_QUOTA_FOR_MANAGEMENT_USER_BYTES: String =
        "total_storage_quota_for_management_user_bytes"

    /** JSON field name for presigned URL expiration time in seconds. */
    public const val PRESIGNED_URL_EXPIRATION_SECONDS: String = "presigned_url_expiration_seconds"

    /** JSON field name for the maximum number of pending uploads allowed for open users. */
    public const val MAX_PENDING_UPLOADS_FOR_OPEN_USER: String = "max_pending_uploads_for_open_user"

    /** JSON field name for the maximum number of pending uploads allowed for management users. */
    public const val MAX_PENDING_UPLOADS_FOR_MANAGEMENT_USER: String = "max_pending_uploads_for_management_user"

    /** JSON field name for enabling file content deduplication. */
    public const val IS_DEDUPLICATION_ENABLED: String = "is_deduplication_enabled"

    /** JSON field name for background worker cleanup execution interval in seconds for deleted files. */
    public const val DELETED_FILES_CLEANUP_INTERVAL_SECONDS: String = "deleted_files_cleanup_interval_seconds"
}
