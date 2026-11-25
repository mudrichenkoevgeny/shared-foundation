# core

Consolidated **core shared contracts** for backend and client: **kotlinx.serialization** defaults, **HTTP/WebSocket JSON field names and headers**, **API error wire shapes**, **core user primitives**, **audit logs**, **security & MFA policies**, **global system settings**, **direct-to-S3 storage contracts**, **navigation contracts**, and **embedded localization infrastructure**.

This module unifies five internal sub-packages (`common`, `audit`, `security`, `settings`, `storage`) into a single KMP artifact (`shared-foundation-core`) without pulling in `feature/*` modules, Ktor engines, or UI frameworks. Consumers ([Backend Platform SDK](https://github.com/mudrichenkoevgeny/backend-platform-sdk), [Web Platform SDK](https://github.com/mudrichenkoevgeny/web-platform-sdk), [KMP Platform SDK](https://github.com/mudrichenkoevgeny/kmp-platform-sdk)) integrate runtime behavior on top of these types.

## Installation

Add `shared-foundation-core` via the BOM (see repository [README.md](../README.md)):

```kotlin
dependencies {
    implementation(platform("io.github.mudrichenkoevgeny:shared-foundation-bom:<version>"))
    implementation("io.github.mudrichenkoevgeny:shared-foundation-core")
}
```

---

## Sub-Packages

### 1. `core.common`

- **Serialization:** [FoundationJson] — shared `Json` configuration (`ignoreUnknownKeys`, `encodeDefaults`, `snake_case`) for encoding/decoding DTOs across the ecosystem.
- **API Errors (Wire):** [ApiErrorResponse] with `snake_case` JSON keys via [CommonApiFields]; machine-readable codes and argument keys in [CommonErrorCodes] and [CommonErrorArgs].
- **HTTP Naming & Routes:** [CommonApiFields], [CommonHttpHeaders] (trace, client type, device, API version headers), and [CommonNetworkRoutes] (`/management`, `/management/self`, `/settings`).
- **WebSocket:** [WebSocketContract], [SocketFrame], [WebSocketInitializePayload], [CommonWebSocketEventTypes] (`PING`, `INITIALIZE`), and [CommonWebSocketCloseReasons].
- **Domain — User Primitives:** [UserId], [UserSessionId], [UserIdentifierId], [UserRole], and [UserAccountStatus].
- **Domain — App, Client & Permissions:** [AppType] (`CLIENT` / `MANAGEMENT`), [ClientType], [ClientInfo], [ClientDeviceInfo], and [PermissionCode].
- **Domain — Listing:** [PagedResult], [ListingParamNames], [SortOrder], and [CommonSortValues].
- **UI — Static Non-Localized Values:** [UiValues] for static placeholders, sample values, and formatting constants.
- **Navigation — Core Contracts:**
  - Interfaces: [NavigationRoute], [ParameterizedNavigationRoute], [RedirectNavigationRoute].
  - Base & root routes: [CommonNavigationRoutes] (`/settings`), [ClientMainNavigation] (`/`), [ManagementMainNavigation] (`/management`).
  - Screen params & parsers: [NavigationScreenParams], [NavigationScreenParamsParser], [NavigationRouteUrlMapper], [QueryParamNormalizer], [PhoneParamNormalizer].
- **Localization Infrastructure:** [LocalizedStringResolver], [LocalizationBundleProvider], [LocalizationRegistry], [LocalizationFormatter], and `DefaultLocalizedStringResolver` with compile-time embedded resources.
- **Validation Hints:** [RequiredField], [NotBlankStringField], [NotEmptyCollectionField] in [NetworkFieldAnnotations].

### 2. `core.audit`

- **Domain Models:**
  - [AuditEventSummary]: Condensed record for list views and tables.
  - [AuditEventPrivate]: Detailed record with actor/resource identifiers and diagnostic metadata.
  - [AuditEventInternal]: Server-side storage entity with redaction policy ([AuditValueSensitivity]) and `updatedAt` timestamp (no wire payload).
  - [AuditEventMetadata], [AuditActorType], [AuditStatus], [AuditValueSensitivity].
- **Common Metadata:** [CommonAuditMetadataKey] for standardized client diagnostics (Device Info, User-Agent, IP, Trace ID) and error tracking (`error_id`, `denied_reason`).
- **Wire Payloads & Mappers:** [AuditEventSummaryPayload], [AuditEventPrivatePayload], [AuditEventMetadataPayload]; [AuditEventSummaryMapper], [AuditEventPrivateMapper], [AuditEventDomainMapper], [AuditEventMetadataMapper], and [ClientInfoAuditMapper] (`ClientInfo.toAuditMetadata()`).
- **List & Search:** [AuditEventFilterValues], [AuditEventSortBy], [AuditApiPaths].
- **Extensibility:** [AuditActionType], [AuditResourceType], [CommonAuditResourceType], and composite parsers ([CompositeAuditActionTypeParser], [CompositeAuditResourceTypeParser], [CompositeAuditMetadataKeyParser]).
- **Routes, Permissions & Navigation:** [ManagementAuditRoutes], [AuditPermissionCode], [ManagementAuditNavigation] (`AuditEventList`, `AuditEventDetail`), [ManagementAuditEventListParams], [ManagementAuditEventDetailParams].

### 3. `core.security`

- **MFA / TOTP:**
  - Setup: [TotpSetup], [TotpSetupPayload] (`secret_key`, `otpauth_url`).
  - Verification: [VerifyTotp], [VerifyTotpPayload] (`mfa_token`).
  - Recovery: [TotpRecoveryCodes], [TotpRecoveryCodesPayload].
- **Security Settings & Policies:**
  - [OpenSecuritySettings] / [OpenSecuritySettingsPayload], [ManagementSecuritySettings] / [ManagementSecuritySettingsPayload].
  - [ManagementPasswordPolicy] / [ManagementPasswordPolicyPayload], [OpenPasswordPolicy] / [OpenPasswordPolicyPayload], [PasswordPolicyValidator].
  - [OtpConfirmation] / [OtpConfirmationPayload], [AccountLockoutPolicy] / [AccountLockoutPolicyPayload], [AccountLockoutType] (`NONE`, `INDEFINITE`, `TEMPORARY`).
  - [IpRestrictionPolicy] / [IpRestrictionPolicyPayload] and [IpRestrictionPolicyMapper].
- **Cryptographic Value Objects:** [DecryptedString], [EncryptedString], [PasswordHash].
- **Errors & Audit:** [SecurityErrorCodes], [SecurityErrorArgs], [SecurityAuditMetadataDeniedReasonValues].
- **Routes, WebSocket, Permissions & Navigation:** [OpenSecuritySettingsRoutes], [ManagementSecuritySettingsRoutes], [SecurityApiFields], [SecurityWebSocketEventTypes] (`OPEN_SECURITY_SETTINGS_UPDATED`, `MANAGEMENT_SECURITY_SETTINGS_UPDATED`), [SecurityPermissionCode], [ManagementSecurityNavigation] (`SecuritySettings`).

### 4. `core.settings`

- **Domain Models & Payloads:** [OpenGlobalSettings] / [OpenGlobalSettingsPayload], [ManagementGlobalSettings] / [ManagementGlobalSettingsPayload], [GlobalSettingsApiFields].
- **Mappers:** [OpenGlobalSettingsMapper], [ManagementGlobalSettingsMapper].
- **Routes, Permissions & Navigation:** [OpenGlobalSettingsRoutes] (`/settings/global`), [ManagementGlobalSettingsRoutes] (`/management/settings/global`), [BaseSettingsNetworkRoutes], [BaseSettingsNavigationRoutes], [SettingsPermissionCode], [ManagementSettingsNavigation] (`GlobalSettings`).
- **WebSocket & Audit:** [SettingsWebSocketEventTypes] (`OPEN_GLOBAL_SETTINGS_UPDATED`, `MANAGEMENT_GLOBAL_SETTINGS_UPDATED`), [SettingsAuditActionType], [SettingsAuditResourceType].

### 5. `core.storage`

- **Domain Models & Projections:** [FileId], [StorageCategory], [StorageFileStatus], [StorageFileVisibility], [StorageFileSummary], [StorageFilePrivate], [StorageFileInternal], [FileUrls], [PresignedUrl], [PresignedReadUrlBatch].
- **Settings & Validation:** [OpenStorageSettings], [ManagementStorageSettings], [StorageUploadValidator], [StorageErrorCodes], [StorageErrorArgs].
- **Wire Payloads & Requests:** [StorageFileSummaryPayload], [StorageFilePrivatePayload], [PresignedUrlPayload], [PresignedReadUrlBatchPayload], [PresignedUrlRequest], [PresignedReadUrlBatchRequest], [DeleteFilesBatchRequest].
- **Routes, Permissions & Navigation:** [OpenStorageRoutes], [SelfManagementStorageRoutes], [ManagementStorageRoutes], [OpenStorageSettingsRoutes], [ManagementStorageSettingsRoutes], [StoragePermissionCode], [OpenStorageNavigation], [ManagementSelfStorageNavigation], [ManagementStorageNavigation], [ManagementStorageSettingsNavigation], [StorageFileDetailParams], [StorageFileUploadParams], [ManagementStorageFileListParams], [SelfStorageFileListParams], [StorageFileUploadUiConfig].

---

## Localization Integration Guidelines

Localization strings across all modules (`core`, `feature/user`) are embedded directly into compiled Kotlin library binaries (`JAR` and `KLIB`) at compile-time.

### 1. Out-of-the-Box Usage (Zero Config)

All module facades (`CommonModuleStrings`, `SecurityModuleStrings`, `UserModuleStrings`) default to `DefaultLocalizedStringResolver()` and `CommonLocales.DEFAULT`:

```kotlin
val commonStrings = CommonModuleStrings()
val userStrings = UserModuleStrings(locale = CommonLocales.RUSSIAN)

val retryText = commonStrings.ui.retry
```

### 2. Optional Custom Resource Overriding

Supply a custom `resourceLoader` lambda to `DefaultLocalizedStringResolver` to partially override string keys from external assets, classpath files, or remote JSON:

```kotlin
val customResolver = DefaultLocalizedStringResolver(
    resourceLoader = { path ->
        loadCustomOverrideJsonOrNull(path)
    }
)

val userStrings = UserModuleStrings(resolver = customResolver, locale = "ru")
```

### 3. iOS (KMP Native)

```kotlin
class IosBundleLocalizedStringResolver : LocalizedStringResolver {
    override fun resolve(modulePath: String, bundleName: String, key: String, locale: String): String? {
        val path = NSBundle.mainBundle.pathForResource("$bundleName", "json", "localization/$modulePath/$locale") ?: return null
        val jsonContent = NSString.stringWithContentsOfFile(path, NSUTF8StringEncoding, null) as? String ?: return null
        return parseJsonFlatMap(jsonContent)[key]
    }
}
```

### 4. Web / Wasm / JS

Use the pre-built `localizationResources` or `SharedFoundationLocalization` from `@mudrichenkoevgeny/shared-foundation` in npm.

---

[FoundationJson]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/serialization/FoundationJson.kt
[ApiErrorResponse]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/error/model/ApiErrorResponse.kt
[CommonErrorCodes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/error/naming/CommonErrorCodes.kt
[CommonErrorArgs]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/error/naming/CommonErrorArgs.kt
[CommonApiFields]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/contract/CommonApiFields.kt
[CommonHttpHeaders]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/contract/CommonHttpHeaders.kt
[CommonNetworkRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/route/CommonNetworkRoutes.kt
[WebSocketContract]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/contract/WebSocketContract.kt
[SocketFrame]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/model/websocket/SocketFrame.kt
[WebSocketInitializePayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/model/websocket/WebSocketInitializePayload.kt
[CommonWebSocketEventTypes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/contract/CommonWebSocketEventTypes.kt
[CommonWebSocketCloseReasons]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/contract/CommonWebSocketCloseReasons.kt
[UserId]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/user/UserId.kt
[UserSessionId]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/user/UserSessionId.kt
[UserIdentifierId]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/user/UserIdentifierId.kt
[UserRole]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/user/UserRole.kt
[UserAccountStatus]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/user/UserAccountStatus.kt
[AppType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/apptype/AppType.kt
[ClientType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/client/ClientType.kt
[ClientInfo]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/client/ClientInfo.kt
[ClientDeviceInfo]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/client/ClientDeviceInfo.kt
[PermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/permission/PermissionCode.kt
[PagedResult]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/listing/PagedResult.kt
[ListingParamNames]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/listing/ListingParamNames.kt
[SortOrder]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/listing/SortOrder.kt
[CommonSortValues]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/listing/CommonSortValues.kt
[UiValues]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/ui/UiValues.kt
[RequiredField]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/validation/NetworkFieldAnnotations.kt
[NotBlankStringField]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/validation/NetworkFieldAnnotations.kt
[NotEmptyCollectionField]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/validation/NetworkFieldAnnotations.kt
[NetworkFieldAnnotations]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/validation/NetworkFieldAnnotations.kt
[NavigationRoute]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/navigation/route/NavigationRoute.kt
[ParameterizedNavigationRoute]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/navigation/route/ParameterizedNavigationRoute.kt
[RedirectNavigationRoute]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/navigation/route/RedirectNavigationRoute.kt
[CommonNavigationRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/navigation/route/CommonNavigationRoutes.kt
[ClientMainNavigation]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/navigation/route/client/ClientMainNavigation.kt
[ManagementMainNavigation]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/navigation/route/management/ManagementMainNavigation.kt
[NavigationScreenParams]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/navigation/params/NavigationScreenParams.kt
[NavigationScreenParamsParser]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/navigation/parser/NavigationScreenParamsParser.kt
[NavigationRouteUrlMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/navigation/route/NavigationRouteUrlMapper.kt
[QueryParamNormalizer]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/navigation/parser/QueryParamNormalizer.kt
[PhoneParamNormalizer]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/navigation/parser/PhoneParamNormalizer.kt
[LocalizedStringResolver]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/localization/LocalizedStringResolver.kt
[LocalizationBundleProvider]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/localization/LocalizationBundleProvider.kt
[LocalizationRegistry]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/localization/LocalizationRegistry.kt
[LocalizationFormatter]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/localization/LocalizationFormatter.kt
[AuditStatus]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/status/AuditStatus.kt
[AuditEventSummary]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/event/AuditEventSummary.kt
[AuditEventPrivate]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/event/AuditEventPrivate.kt
[AuditEventInternal]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/event/AuditEventInternal.kt
[AuditActorType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/actor/AuditActorType.kt
[AuditActionType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/action/AuditActionType.kt
[AuditResourceType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/resource/AuditResourceType.kt
[CommonAuditResourceType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/audit/resource/CommonAuditResourceType.kt
[AuditValueSensitivity]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/event/AuditValueSensitivity.kt
[AuditEventMetadata]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/metadata/AuditEventMetadata.kt
[CommonAuditMetadataKey]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/metadata/CommonAuditMetadataKey.kt
[AuditEventSummaryPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/network/model/event/AuditEventSummaryPayload.kt
[AuditEventPrivatePayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/network/model/event/AuditEventPrivatePayload.kt
[AuditEventMetadataPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/network/model/event/AuditEventMetadataPayload.kt
[AuditApiPaths]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/network/contract/AuditApiPaths.kt
[AuditEventFilterValues]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/listing/AuditEventFilterValues.kt
[AuditEventSortBy]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/listing/AuditEventSortBy.kt
[AuditEventSummaryMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/mapper/audit/AuditEventSummaryMapper.kt
[AuditEventPrivateMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/mapper/audit/AuditEventPrivateMapper.kt
[AuditEventDomainMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/mapper/audit/AuditEventDomainMapper.kt
[AuditEventMetadataMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/mapper/audit/AuditEventMetadataMapper.kt
[ClientInfoAuditMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/mapper/audit/ClientInfoAuditMapper.kt
[CompositeAuditActionTypeParser]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/action/CompositeAuditActionTypeParser.kt
[CompositeAuditResourceTypeParser]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/resource/CompositeAuditResourceTypeParser.kt
[CompositeAuditMetadataKeyParser]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/model/metadata/CompositeAuditMetadataKeyParser.kt
[ManagementAuditRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/network/route/management/ManagementAuditRoutes.kt
[AuditPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/domain/permissions/AuditPermissionCode.kt
[ManagementAuditNavigation]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/navigation/route/management/ManagementAuditNavigation.kt
[ManagementAuditEventListParams]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/navigation/screen/management/audit/list/ManagementAuditEventListParams.kt
[ManagementAuditEventDetailParams]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/audit/navigation/screen/management/audit/detail/ManagementAuditEventDetailParams.kt
[SecurityErrorCodes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/error/naming/SecurityErrorCodes.kt
[SecurityErrorArgs]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/error/naming/SecurityErrorArgs.kt
[SecurityApiFields]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/contract/SecurityApiFields.kt
[ManagementPasswordPolicy]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/passwordpolicy/ManagementPasswordPolicy.kt
[ManagementPasswordPolicyPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/model/passwordpolicy/ManagementPasswordPolicyPayload.kt
[OpenPasswordPolicy]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/passwordpolicy/OpenPasswordPolicy.kt
[OpenPasswordPolicyPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/model/passwordpolicy/OpenPasswordPolicyPayload.kt
[PasswordPolicyValidator]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/passwordpolicy/validator/PasswordPolicyValidator.kt
[OpenSecuritySettings]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/securitysettings/OpenSecuritySettings.kt
[OpenSecuritySettingsPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/model/securitysettings/OpenSecuritySettingsPayload.kt
[ManagementSecuritySettings]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/securitysettings/ManagementSecuritySettings.kt
[ManagementSecuritySettingsPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/model/securitysettings/ManagementSecuritySettingsPayload.kt
[AccountLockoutPolicy]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/accountlockout/AccountLockoutPolicy.kt
[AccountLockoutPolicyPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/model/accountlockout/AccountLockoutPolicyPayload.kt
[AccountLockoutType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/accountlockout/AccountLockoutType.kt
[IpRestrictionPolicy]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/iprestriction/IpRestrictionPolicy.kt
[IpRestrictionPolicyPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/model/iprestriction/IpRestrictionPolicyPayload.kt
[IpRestrictionPolicyMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/mapper/iprestriction/IpRestrictionPolicyMapper.kt
[TotpSetup]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/totpsetup/TotpSetup.kt
[TotpSetupPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/model/totpsetup/TotpSetupPayload.kt
[VerifyTotp]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/verifytotp/VerifyTotp.kt
[VerifyTotpPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/model/verifytotp/VerifyTotpPayload.kt
[TotpRecoveryCodes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/totprecoverycodes/TotpRecoveryCodes.kt
[TotpRecoveryCodesPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/model/totprecoverycodes/TotpRecoveryCodesPayload.kt
[OtpConfirmation]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/otpconfirmation/OtpConfirmation.kt
[OtpConfirmationPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/model/otpconfirmation/OtpConfirmationPayload.kt
[DecryptedString]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/crypt/DecryptedString.kt
[EncryptedString]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/crypt/EncryptedString.kt
[PasswordHash]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/passwordhash/PasswordHash.kt
[SecurityAuditMetadataDeniedReasonValues]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/audit/metadata/SecurityAuditMetadataDeniedReasonValues.kt
[SecurityWebSocketEventTypes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/contract/SecurityWebSocketEventTypes.kt
[SecurityPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/permission/SecurityPermissionCode.kt
[OpenSecuritySettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/route/open/security/settings/OpenSecuritySettingsRoutes.kt
[ManagementSecuritySettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/network/route/management/security/settings/ManagementSecuritySettingsRoutes.kt
[ManagementSecurityNavigation]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/navigation/route/management/ManagementSecurityNavigation.kt
[GlobalSettingsApiFields]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/contract/GlobalSettingsApiFields.kt
[OpenGlobalSettings]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/domain/model/globalsettings/OpenGlobalSettings.kt
[ManagementGlobalSettings]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/domain/model/globalsettings/ManagementGlobalSettings.kt
[OpenGlobalSettingsPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/model/globalsettings/OpenGlobalSettingsPayload.kt
[ManagementGlobalSettingsPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/model/globalsettings/ManagementGlobalSettingsPayload.kt
[OpenGlobalSettingsMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/mapper/globalsettings/OpenGlobalSettingsMapper.kt
[ManagementGlobalSettingsMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/mapper/globalsettings/ManagementGlobalSettingsMapper.kt
[SettingsWebSocketEventTypes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/contract/SettingsWebSocketEventTypes.kt
[SettingsPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/domain/permission/SettingsPermissionCode.kt
[OpenGlobalSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/route/open/globalsettings/OpenGlobalSettingsRoutes.kt
[ManagementGlobalSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/route/management/globalsettings/ManagementGlobalSettingsRoutes.kt
[BaseSettingsNetworkRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/network/route/BaseSettingsNetworkRoutes.kt
[BaseSettingsNavigationRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/navigation/route/BaseSettingsNavigationRoutes.kt
[ManagementSettingsNavigation]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/navigation/route/management/ManagementSettingsNavigation.kt
[SettingsAuditActionType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/domain/audit/action/SettingsAuditActionType.kt
[SettingsAuditResourceType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/settings/domain/audit/resource/SettingsAuditResourceType.kt
[FileId]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/model/file/FileId.kt
[StorageCategory]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/model/category/StorageCategory.kt
[StorageFileStatus]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/model/status/StorageFileStatus.kt
[StorageFileVisibility]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/model/visibility/StorageFileVisibility.kt
[StorageFileSummary]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/model/file/StorageFileSummary.kt
[StorageFilePrivate]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/model/file/StorageFilePrivate.kt
[StorageFileInternal]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/model/file/StorageFileInternal.kt
[FileUrls]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/model/file/FileUrls.kt
[PresignedUrl]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/model/presigned/PresignedUrl.kt
[PresignedReadUrlBatch]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/model/presigned/PresignedReadUrlBatch.kt
[OpenStorageSettings]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/model/storagesettings/OpenStorageSettings.kt
[ManagementStorageSettings]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/model/storagesettings/ManagementStorageSettings.kt
[StorageUploadValidator]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/validation/StorageUploadValidator.kt
[StorageErrorCodes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/error/naming/StorageErrorCodes.kt
[StorageErrorArgs]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/error/naming/StorageErrorArgs.kt
[StorageFileSummaryPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/model/file/StorageFileSummaryPayload.kt
[StorageFilePrivatePayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/model/file/StorageFilePrivatePayload.kt
[PresignedUrlPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/model/presigned/PresignedUrlPayload.kt
[PresignedReadUrlBatchPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/model/presigned/PresignedReadUrlBatchPayload.kt
[PresignedUrlRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/request/presigned/PresignedUrlRequest.kt
[PresignedReadUrlBatchRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/request/presigned/PresignedReadUrlBatchRequest.kt
[DeleteFilesBatchRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/request/file/DeleteFilesBatchRequest.kt
[OpenStorageRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/route/open/OpenStorageRoutes.kt
[SelfManagementStorageRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/route/management/self/SelfManagementStorageRoutes.kt
[ManagementStorageRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/route/management/ManagementStorageRoutes.kt
[OpenStorageSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/route/open/OpenStorageSettingsRoutes.kt
[ManagementStorageSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/network/route/management/ManagementStorageSettingsRoutes.kt
[StoragePermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/domain/permission/StoragePermissionCode.kt
[OpenStorageNavigation]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/navigation/route/open/OpenStorageNavigation.kt
[ManagementSelfStorageNavigation]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/navigation/route/management/ManagementSelfStorageNavigation.kt
[ManagementStorageNavigation]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/navigation/route/management/ManagementStorageNavigation.kt
[ManagementStorageSettingsNavigation]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/navigation/route/management/ManagementStorageSettingsNavigation.kt
[StorageFileDetailParams]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/navigation/screen/base/detail/StorageFileDetailParams.kt
[StorageFileUploadParams]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/navigation/screen/base/upload/StorageFileUploadParams.kt
[ManagementStorageFileListParams]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/navigation/screen/management/list/ManagementStorageFileListParams.kt
[SelfStorageFileListParams]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/navigation/screen/open/list/SelfStorageFileListParams.kt
[StorageFileUploadUiConfig]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/storage/ui/component/fileupload/StorageFileUploadUiConfig.kt
