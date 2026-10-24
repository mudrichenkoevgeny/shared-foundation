# core/storage

**Direct-to-S3 Presigned Upload & Storage Management shared contracts:** domain models, HTTP request/response payloads, value objects, permissions, API route constants, navigation screen routes, error codes, and storage settings (open and management). Depends on **core/common** and **core/audit**.

This module defines pure KMP contracts and route specifications for direct client-to-S3 binary file uploads and metadata management without handling Ktor engine wiring or S3 client SDK execution.

## What it provides

- **Domain Models & Entity Projections:**
  - [FileId]: Strongly typed `inline value` class wrapping `Uuid` for type-safe file identification.
  - [StorageCategory]: Enum classifying files for server dynamic limits, MIME rules, and processing (`IMAGE`, `VIDEO`, `AUDIO`, `DOCUMENT`, `OTHER`).
  - [StorageFileStatus]: Enum representing file lifecycle states (`PENDING_UPLOAD`, `READY`, `PROCESSING`, `DELETED`).
  - [StorageFileVisibility]: Enum controlling access level (`PUBLIC`, `PRIVATE`).
  - **Entity Projections:**
    - **Storage File Summary:** [StorageFileSummary] / [StorageFileSummaryPayload] for compact list views and embedding cards.
    - **Storage File Private:** [StorageFilePrivate] / [StorageFilePrivatePayload] for authenticated self-service ("My Files") and management inspection.
    - **Storage File Internal:** [StorageFileInternal] for server/database storage (includes S3 bucket, S3 key, and content SHA-256 hash). **No wire Payload!**
- **Presigned Upload & Batch Contracts:**
  - [PresignedUrlRequest] & [PresignedUrlPayload]: Request/response DTOs for requesting presigned S3 upload URLs with feature scope (`targetScope`).
  - [PresignedReadUrlBatchRequest] & [PresignedReadUrlBatchPayload]: Request/response DTOs for batch requesting temporary presigned read URLs for multiple files.
  - [DeleteFilesBatchRequest]: Request DTO for batch deleting multiple files in a single operation.
- **Storage Settings:**
  - [OpenStorageSettings] & [OpenStorageSettingsPayload]: Open configuration exposing system-wide upload size limits (`minUploadSizeBytes`, `maxUploadSizeBytes`) for client-side UX validation.
  - [ManagementStorageSettings] & [ManagementStorageSettingsPayload]: Complete configuration including global size limits, active user quotas (`totalStorageQuotaForOpenUserBytes`, `totalStorageQuotaForManagementUserBytes`), URL expiration TTL (`presignedUrlExpirationSeconds`), active session rate limits (`maxPendingUploadsForOpenUser`, `maxPendingUploadsForManagementUser`), and deduplication toggle (`isDeduplicationEnabled`).
- **HTTP Routes & Permissions:**
  - [OpenStorageRoutes]: Open API routes (`GET_FILES`, `GET_FILE`, `POST_PRESIGNED_UPLOAD_URL`, `CONFIRM_UPLOAD`, `DELETE_FILE`, `DELETE_FILES_BATCH`).
  - [SelfManagementStorageRoutes]: Self-service staff management API routes (`GET_FILES`, `GET_FILE`, `POST_PRESIGNED_UPLOAD_URL`, `CONFIRM_UPLOAD`, `DELETE_FILE`, `DELETE_FILES_BATCH`).
  - [ManagementStorageRoutes]: Administrative API routes (`GET_FILES`, `GET_FILE`, `DELETE_FILES_BATCH`, `DELETE_FILE`).
  - [OpenStorageSettingsRoutes] & [ManagementStorageSettingsRoutes]: Routes for reading and updating global storage configuration.
  - [StoragePermissionCode]: Permissions (`STORAGE_DELETE_ANY_FILE`, `STORAGE_VIEW_METADATA`, `STORAGE_SETTINGS_UPDATE`).
- **Navigation & Screen Params:**
  - [OpenStorageNavigation] & [ManagementStorageNavigation]: Screen navigation routes (`FileList`, `FileDetail`, `FileUpload`).
  - [ManagementStorageSettingsNavigation]: Management navigation for storage settings (`StorageSettings`).
  - Screen parameter classes with deep-link query/path parsers: [StorageFileDetailParams], [ManagementStorageFileListParams], [SelfStorageFileListParams].
- **Error Codes & Audit:**
  - [StorageErrorCodes] & [StorageErrorArgs]: Specific codes for file size limits, MIME restrictions, quota limits, presigned URL expiration, and file deletion failures.
  - [StorageAuditActionType] & [StorageAuditResourceType]: Audit action and resource types for administrative storage management and file deletions.
- **Mappers & Domain Converters:**
  - [StorageFileDomainMapper]: Converts internal DB models ([StorageFileInternal]) into public domain projections ([StorageFilePrivate], [StorageFileSummary]) with dynamically resolved `fileUrl`.
  - [StorageFilePrivateMapper] & [StorageFileSummaryMapper]: Bidirectional conversion between Domain entities and Network DTO payloads ([StorageFilePrivatePayload], [StorageFileSummaryPayload]).
  - [ManagementStorageSettingsMapper] & [OpenStorageSettingsMapper]: Conversion between Storage Settings domain models and network payloads.

## Usage

- **Dependency:** Add `shared-foundation-core-storage` via BOM.
- **Presigned Upload Flow:**
  1. Client sends [PresignedUrlRequest] with file size, MIME type, category, and target scope (`"avatar"`, `"attachment"`).
  2. Server returns [PresignedUrlPayload] containing `uploadUrl` and `fileId`.
  3. Client uploads binary file directly to S3 via HTTP `PUT` to `uploadUrl`.
  4. Client calls [OpenStorageRoutes.CONFIRM_UPLOAD] to finalize file state to `READY`.
- **Serialization:** All payloads use `snake_case` naming via [StorageApiFields] and are compatible with [FoundationJson].

[FileId]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/model/file/FileId.kt
[StorageCategory]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/model/category/StorageCategory.kt
[StorageFileStatus]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/model/status/StorageFileStatus.kt
[StorageFileVisibility]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/model/visibility/StorageFileVisibility.kt
[StorageFileSummary]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/model/file/StorageFileSummary.kt
[StorageFilePrivate]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/model/file/StorageFilePrivate.kt
[StorageFileInternal]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/model/file/StorageFileInternal.kt
[StorageFileDomainMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/mapper/file/StorageFileDomainMapper.kt
[StorageFilePrivateMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/mapper/file/StorageFilePrivateMapper.kt
[StorageFileSummaryMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/mapper/file/StorageFileSummaryMapper.kt
[ManagementStorageSettingsMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/mapper/storagesettings/ManagementStorageSettingsMapper.kt
[OpenStorageSettingsMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/mapper/storagesettings/OpenStorageSettingsMapper.kt
[PresignedUrlRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/request/presigned/PresignedUrlRequest.kt
[PresignedUrlPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/model/presigned/PresignedUrlPayload.kt
[PresignedReadUrlBatchRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/request/presigned/PresignedReadUrlBatchRequest.kt
[PresignedReadUrlBatchPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/model/presigned/PresignedReadUrlBatchPayload.kt
[DeleteFilesBatchRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/request/file/DeleteFilesBatchRequest.kt
[OpenStorageSettings]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/model/storagesettings/OpenStorageSettings.kt
[OpenStorageSettingsPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/model/storagesettings/OpenStorageSettingsPayload.kt
[ManagementStorageSettings]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/model/storagesettings/ManagementStorageSettings.kt
[ManagementStorageSettingsPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/model/storagesettings/ManagementStorageSettingsPayload.kt
[OpenStorageRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/route/open/OpenStorageRoutes.kt
[SelfManagementStorageRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/route/management/self/SelfManagementStorageRoutes.kt
[ManagementStorageRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/route/management/ManagementStorageRoutes.kt
[OpenStorageSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/route/open/OpenStorageSettingsRoutes.kt
[ManagementStorageSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/route/management/ManagementStorageSettingsRoutes.kt
[StoragePermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/permission/StoragePermissionCode.kt
[OpenStorageNavigation]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/navigation/route/open/OpenStorageNavigation.kt
[ManagementStorageNavigation]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/navigation/route/management/ManagementStorageNavigation.kt
[ManagementStorageSettingsNavigation]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/navigation/route/management/ManagementStorageSettingsNavigation.kt
[StorageFileDetailParams]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/navigation/screen/base/detail/StorageFileDetailParams.kt
[ManagementStorageFileListParams]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/navigation/screen/management/list/ManagementStorageFileListParams.kt
[SelfStorageFileListParams]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/navigation/screen/open/list/SelfStorageFileListParams.kt
[StorageErrorCodes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/error/naming/StorageErrorCodes.kt
[StorageErrorArgs]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/error/naming/StorageErrorArgs.kt
[StorageAuditActionType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/audit/action/StorageAuditActionType.kt
[StorageAuditResourceType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/audit/resource/StorageAuditResourceType.kt
[StorageApiFields]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/contract/StorageApiFields.kt
[FoundationJson]: ../common/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/serialization/FoundationJson.kt
