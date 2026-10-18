# core/common

Cross-cutting **shared contracts** for backend and client: **kotlinx.serialization** defaults, **HTTP/WebSocket JSON field names and headers**, **API error wire shape** (`code` / `args` string constants), **WebSocket frame and handshake payloads**, shared **domain models** (client info, permissions, listing), and **JVM retention annotations** for request validation hints. This module does **not** pull in other in-repo `feature/*` modules. It does **not** include Ktor client/server wiring, UI, or localized error text — consumers (e.g. [Backend Platform SDK](https://github.com/mudrichenkoevgeny/backend-platform-sdk), [Web Platform SDK](https://github.com/mudrichenkoevgeny/web-platform-sdk), [KMP Platform SDK](https://github.com/mudrichenkoevgeny/kmp-platform-sdk)) integrate runtime behavior on top of these types.

## What it provides

- **Serialization:** [FoundationJson] — shared `Json` configuration (`ignoreUnknownKeys`, `encodeDefaults`, etc.) for encoding/decoding DTOs in this library and dependent modules.
- **API errors (wire):** [ApiErrorResponse] with snake_case JSON keys via [CommonApiFields]; machine-readable **codes** and **args** keys in [CommonErrorCodes] and [CommonErrorArgs].
- **HTTP naming & routes:** [CommonApiFields] (JSON field names), [CommonHttpHeaders] (e.g. trace, client type, device, API version headers), [ManagementRoutes] for platform-wide management paths, [BaseManagementRoutes] (`/management`, `/management/self`), and [BaseSettingsRoutes] (`/settings`).
- **WebSocket — paths and envelopes:** [WebSocketContract] (realtime path string); [SocketFrame] (id, type, optional `JsonElement` payload, metadata, timestamp); [WebSocketInitializePayload] for client metadata on connect.
- **WebSocket — event and close strings:** [CommonWebSocketEventTypes] (e.g. PING, INITIALIZE), [CommonWebSocketCloseReasons].
- **Domain — Client:** [ClientType] serializable enum, [ClientInfo], and [ClientDeviceInfo] to describe connecting applications.
- **Domain — Permissions:** [PermissionCode] base type for shared permission constants.
- **Domain — Listing:** [PagedResult], [ListingParamNames], [SortOrder], and [CommonSortValues] for standardized pagination and sorting contracts.
- **Navigation — Core Contracts:**
    - Base navigation interfaces: [NavigationRoute] (`route: String`, `parentRoute: String?`), [ParameterizedNavigationRoute] (for screen destinations carrying typed parameters), and [RedirectNavigationRoute] (for virtual/alias redirect routes delegating to `targetRoute`).
    - Base navigation routes: [CommonNavigationRoutes] (`BASE_SETTINGS_ROUTE = "/settings"`).
    - Root navigation route definitions: [ClientMainNavigation] (`/`) and [ManagementMainNavigation] (`/management`).
    - Screen parameter contracts & parsers: [NavigationScreenParams] marker interface; [NavigationScreenParamsParser] contract interface for bidirectional parameter serialization; [NavigationRouteUrlMapper] utility for building URLs with parameter maps; [QueryParamNormalizer] and [PhoneParamNormalizer] for parameter normalization.
- **Localization Infrastructure:**
    - [LocalizedStringResolver]: Functional contract interface `resolve(modulePath: String, bundleName: String, key: String, locale: String): String?` implemented by client applications to load resource bundles from classpath, assets, or native bundles.
    - [LocalizationBundleProvider]: Functional contract supplying localization bundle maps (`get(module, file, locale)`).
    - [LocalizationRegistry]: Thread-safe registry (`LocalizationRegistry.register(provider)`) maintaining module localization bundle providers.
    - [LocalizationFormatter]: Core formatting object replacing `{placeholder}` tokens in string templates (`format(template, params)`).
    - Embedded localization: Each module automatically registers its embedded `<ModulePrefix>EmbeddedLocalizationResources` with `LocalizationRegistry`. Out-of-the-box `DefaultLocalizedStringResolver` resolves embedded strings without external resource loaders and supports partial key overrides via optional custom `resourceLoader`.
    - Generated module facades: [CommonModuleStrings], [ErrorsStrings], and [UiStrings] for type-safe access to `core/common` localized strings.
- **Validation hints:** [RequiredField], [NotBlankStringField], [NotEmptyCollectionField] in [NetworkFieldAnnotations] (runtime interpretation is outside this module unless you add validators).

## Localization Integration Guidelines

Localization strings across all modules (`core/common`, `core/security`, `feature/user`) are embedded directly into compiled Kotlin library binaries (`JAR` and `KLIB`) at compile-time.

### 1. Out-of-the-Box Usage (Zero Config)
All module facades (`CommonModuleStrings`, `SecurityModuleStrings`, `UserModuleStrings`) default to `DefaultLocalizedStringResolver()` and `CommonLocales.DEFAULT`:
```kotlin
// Works out of the box across all KMP targets (JVM, Android, iOS, Wasm, Desktop):
val commonStrings = CommonModuleStrings()
val userStrings = UserModuleStrings(locale = CommonLocales.RUSSIAN)

val retryText = commonStrings.ui.retry // "Повторить"
```

### 2. Optional Custom Resource Overriding
If a client application needs to partially override string keys from external assets, classpath files, or remote JSON, supply a custom `resourceLoader` lambda to `DefaultLocalizedStringResolver`:

```kotlin
val customResolver = DefaultLocalizedStringResolver(
    resourceLoader = { path ->
        // Return custom JSON content string or null to fall back to embedded library strings
        loadCustomOverrideJsonOrNull(path)
    }
)

val userStrings = UserModuleStrings(resolver = customResolver, locale = "ru")
```

### 3. iOS (KMP Native)
Resolve resource files from `NSBundle.mainBundle`:
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

## Usage

- Add a dependency on the published artifact `shared-foundation-core-common` (or align versions via [shared-foundation-bom](../../bom) / your version catalog — see repository [README.md](../../../../README.md)).
- Use [FoundationJson] (or the same settings) when serializing types from this module so wire format stays consistent.
- Compare API error `code` values to [CommonErrorCodes] (and feature-specific `*ErrorCodes` in other modules); populate `args` using keys from [CommonErrorArgs] where applicable.
- Use [CommonApiFields] / [CommonHttpHeaders] in client and server code so JSON keys and header names stay aligned.
- For WebSocket framing, send/receive [SocketFrame] and use shared event type strings from [CommonWebSocketEventTypes] (feature modules may define additional types).

[FoundationJson]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/serialization/FoundationJson.kt
[ApiErrorResponse]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/error/model/ApiErrorResponse.kt
[CommonErrorCodes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/error/naming/CommonErrorCodes.kt
[CommonErrorArgs]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/error/naming/CommonErrorArgs.kt
[CommonApiFields]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/contract/CommonApiFields.kt
[CommonHttpHeaders]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/contract/CommonHttpHeaders.kt
[ManagementRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/route/management/ManagementRoutes.kt
[BaseManagementRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/route/base/management/BaseManagementRoutes.kt
[BaseSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/route/base/settings/BaseSettingsRoutes.kt
[WebSocketContract]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/contract/WebSocketContract.kt
[SocketFrame]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/model/websocket/SocketFrame.kt
[WebSocketInitializePayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/model/websocket/WebSocketInitializePayload.kt
[CommonWebSocketEventTypes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/contract/CommonWebSocketEventTypes.kt
[CommonWebSocketCloseReasons]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/network/contract/CommonWebSocketCloseReasons.kt
[ClientType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/client/ClientType.kt
[ClientInfo]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/client/ClientInfo.kt
[ClientDeviceInfo]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/client/ClientDeviceInfo.kt
[PermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/permission/PermissionCode.kt
[PagedResult]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/listing/PagedResult.kt
[ListingParamNames]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/listing/ListingParamNames.kt
[SortOrder]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/listing/SortOrder.kt
[CommonSortValues]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/common/domain/model/listing/CommonSortValues.kt
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
