---
description: Serialization standards, WebSocket contracts, and API error DTO structures
glob: "**/*.kt"
alwaysApply: true
---

# Architecture and Design Patterns

This document defines the structural standards for the `shared-foundation` library. All contributions must adhere to these patterns to ensure a consistent contract between server and client applications.

## 1. Architectural Split: Core vs. Feature-API

To maintain a clean dependency graph, the library follows a strict separation:

- **Core Modules (`core/*`):** Contain the "meat" — domain models, `@Serializable` payloads (DTOs), mappers, and pure business logic (e.g., `PasswordPolicyValidator`). They focus on **what** data is being moved.
- **Feature-API Modules (`feature/*-api`):** Contain the "wiring" — `const val` HTTP routes, `PermissionCode` constants, and audit taxonomy (`AuditActionType`). They focus on **how** and **where** data is accessed via HTTP.

## 2. Entity Projection Tiers (Summary, Details, Private, Internal)

Domain models and API wire payloads follow a standardized **up to 4-tier** projection taxonomy based on scope, payload weight, and security boundaries.

**Selective Implementation Rule:** Do NOT blindly generate all 4 tiers for every entity. Implement ONLY the tiers necessitated by distinct data shapes or access control boundaries (e.g., `User` and `UserSession` only implement `Summary`, `Private`, and `Internal`, omitting `Details`). Never invent ad-hoc suffixes like `*Full`, `*View`, `*Admin`, or `*Extended`.

### The 4 Projection Tiers:

1. **`[Entity]Summary`** (Client & Server — Wire & Domain):
    - **Scope:** Public or list-level condensed snippet for feeds, catalogs, cards, tables, and badges.
    - **Payload:** `[Entity]SummaryPayload`.
    - **Excludes:** Heavy lists, nested editable sub-models, unredacted sensitive fields, and diagnostic metadata.

2. **`[Entity]Details`** (Client & Server — Wire & Domain, **when applicable**):
    - **Scope:** Expanded public/unprivileged representation for viewing a single entity when `Summary` is insufficient (e.g., extra public metadata or non-spoiler item views).
    - **Payload:** `[Entity]DetailsPayload`.
    - **Omission Rule:** Omit this tier whenever the public single-item view is identical to `Summary` or when detailed data is restricted strictly to the owner/admin (`Private`) or sequential session flows.

3. **`[Entity]Private`** (Client & Server — Wire & Domain):
    - **Scope:** Privileged representation for resource owners (self-service, account settings, creator studio/editor with full localization maps and nested items) and administrators/moderators (management scope).
    - **Payload:** `[Entity]PrivatePayload`.
    - **Excludes:** Pure server-side/database internal fields (hashes, vector embeddings, internal DB timestamps).

4. **`[Entity]Internal`** (Server/Database Usage — Domain ONLY):
    - **Scope:** Complete, unredacted domain model and database row representation for backend business logic and persistence (includes `updatedAt`, password/token hashes, vector embeddings, and system flags).
    - **STRICT PROHIBITION:** **NEVER** create an `[Entity]InternalPayload` DTO! Even if the `Internal` domain class resides in the shared module for domain mapping convenience, it must **NEVER** be serialized or exposed over HTTP/WebSocket API contracts.

### Shared Domain Logic & Mappers:
- **Base Entity Interface (Optional):** When multiple projections of an entity share domain validation or helper methods (e.g., `isValid`, `getInfo`), extract a common base interface `[Entity]` (without a tier suffix) implemented by each projection to avoid duplicating logic across data classes.
- **`[Entity]SummaryMapper.kt`**: `[Entity]SummaryPayload <-> [Entity]Summary`
- **`[Entity]DetailsMapper.kt`**: `[Entity]DetailsPayload <-> [Entity]Details` (only when `Details` tier exists)
- **`[Entity]PrivateMapper.kt`**: `[Entity]PrivatePayload <-> [Entity]Private`
- **`[Entity]DomainMapper.kt`**: Direct 1-step domain-to-domain conversions (`Internal -> Private`, `Internal -> Details`, `Private -> Summary`, `Internal -> Summary`) without allocating temporary intermediate objects.

### Mapper Structure:
- **`[Entity]SummaryMapper.kt`**: `[Entity]SummaryPayload <-> [Entity]Summary`
- **`[Entity]PrivateMapper.kt`**: `[Entity]PrivatePayload <-> [Entity]Private`
- **`[Entity]DetailsMapper.kt`**: `[Entity]DetailsPayload <-> [Entity]Details` (if applicable)
- **`[Entity]DomainMapper.kt`**: Direct 1-step domain-to-domain conversions (`Internal -> Private`, `Private -> Summary`, `Internal -> Summary`) without allocating temporary intermediate objects.

## 3. Serialization & Wire Format

- **Implementation:** Strictly `kotlinx.serialization`.
- **Global Config:** Always use `FoundationJson` (from `core/common`) for encoding/decoding to ensure `snake_case` naming, `ignoreUnknownKeys`, and `encodeDefaults` are consistent.
- **Naming:** Use `@SerialName` for all properties. Values must come from `CommonApiFields`, `SecurityApiFields`, `UserApiFields`, or other module-specific contract objects.
- **Validation Hints:** Use JVM-retention annotations from `core/common` (`@RequiredField`, `@NotBlankStringField`) to mark DTO requirements for consumers.

## 4. HTTP Route Documentation & Navigation Traceability

Every route constant in `feature/*` and `core/*` network route objects must be documented using a strict KDoc hierarchy. This is the **primary source of truth** for backend, client, and UI developers.

### Required KDoc Structure:
1. **HTTP Method:** `**HTTP method:** METHOD` (e.g., `GET`, `POST`).
2. **Summary:** Concise description of the operation.
3. **Request Contract:**
    - **Path parameters:** Reference constants like `[UserApiPaths.USER_ID]`.
    - **Pagination:** Link `[ListingParamNames]`. **Note:** `page` is 1-based.
    - **Filters:** Link domain-specific query params (e.g., `[AuditFilterValues.AuditEventFilterValues]`).
    - **Request body:** Link the DTO.
4. **Response Contract:** Link the response DTO.
5. **Access Control:**
   - **Authorization:** List **Allowed Roles** (OR) and **Required Permissions** (AND).
   - **Public Access:** State `Allowed` or `Denied`.
6. **Security:** Notes on **MFA Step-up** requirements (e.g., `REAUTHENTICATE_SESSION`) for sensitive actions.
7. **Observability (Audit):** Define the `AuditActionType` and `AuditResourceType` triggered by this route.
8. **UI Presentation:** Reference the associated navigation screen routes (`Screens:`) and/or UI flow (`Flow:`).

## 5. Audit & Extensibility Patterns

- **Sensitivity:** When defining metadata or resource IDs, always consider `AuditValueSensitivity` (Full mask, Partial mask, or IP-address) to ensure PII compliance.
- **Composite Parsers:** For extensible domain types (Actions, Resources), use `CompositeAuditActionTypeParser` and similar tools to resolve types from wire strings.
- **Metadata:** Use `CommonAuditMetadataKey` or domain-specific keys like `UserAuditMetadataKey` for technical context (IP, OS, Trace ID) to ensure logs are searchable across the platform.

## 6. Error Handling & Machine-Readable Codes

- **Wire Shape:** All errors must use `ApiErrorResponse`.
- **Contract Only:** This library defines **only the code strings** (e.g., `SecurityErrorCodes.PASSWORD_TOO_WEAK`, `UserErrorCodes.USER_LOCKED`) and **argument keys** (e.g., `UserErrorArgs.TEMPORARY_LOCKOUT_UNTIL`, `UserErrorArgs.ACCOUNT_LOCKOUT_TYPE`).
- **No Localization:** Do not include human-readable text. Consumers map `code` to localized strings in the UI.

## 7. WebSocket Communication

- **Envelopes:** Use `SocketFrame` for all messages. The `payload` field must be a `JsonElement?`, allowing for type-safe polymorphic serialization on the consumer side.
- **Event Types:** Use `CommonWebSocketEventTypes` and feature-specific strings (e.g., `UserWebSocketEventTypes`) to signal state changes.

## 8. Logic vs. Frameworks

- **No Framework Imports:** Do not add dependencies on Ktor Server, Ktor Client, or Database drivers.
- **Pure Logic:** Logic like `PasswordPolicyValidatorImpl` must be pure Kotlin. If a feature requires platform-specific behavior, define an `interface` in `core` and let consumers provide the implementation.
- **No Environment Data:** Base URLs and secrets are forbidden. Use relative path segments only.

## 9. Navigation Screen Routes & Parameters Architecture

To provide full type safety and declarative navigation for client platforms, each module defines a structured navigation layer (`*.navigation.*`):

- **Core Navigation Contracts (`core/common`):**
  - **`NavigationRoute`**: Base interface for all typed navigation destinations (`route: String`, `parentRoute: String?`).
  - **`ParameterizedNavigationRoute<P>`**: Contract for navigation destinations carrying strongly typed parameters (`params: P`).
  - **`RedirectNavigationRoute`**: Contract for virtual/alias redirect routes delegating to `targetRoute: String` without rendering a standalone UI screen.
- **Route Declarations (`*Navigation.kt`):**
  - Concrete screen objects or sealed hierarchies implementing `NavigationRoute`, `ParameterizedNavigationRoute<P>`, or `RedirectNavigationRoute`.
  - Defines screen route paths (`ROUTE`) and parent route hierarchies (`PARENT_ROUTE`).
  - Documents `Screen Parameters`, `Primary API` (auto-fetch or main submit endpoint), and `Secondary Action APIs` (user action buttons).
- **Screen Parameter Types (`*Params.kt`):**
  - Strongly typed parameter data classes implementing `NavigationScreenParams`.
- **Parameter Parsers (`*ParamsParser.kt`):**
  - Standalone parser components implementing `NavigationScreenParamsParser<T>` responsible for bidirectional serialization (`parse` raw query/path maps into typed `*Params` objects, and `serialize` `*Params` back to string query maps), utilizing `QueryParamNormalizer` and `PhoneParamNormalizer`.
- **Route Segment Constants & Helpers:**
  - Common base navigation routes in `CommonNavigationRoutes` (e.g., `BASE_SETTINGS_ROUTE`), feature navigation routes in `UserNavigationRoutes` (e.g., `BASE_AUTH_ROUTE`), parameter name constants in `UserNavigationPaths` (`ACTION`, `REDIRECT_URL`, `TOTP_STEP`, `UNLOCK_METHOD`, `LOGIN_BY_TOTP_MODE`), and base network routes in `BaseManagementRoutes` and `BaseSettingsRoutes`.

## 10. Localization Architecture & Resource Standards

To ensure type-safe, multiplatform string localization across Backend (JVM), Web (TypeScript), Mobile (Android/iOS), and Desktop, the library enforces a strict localization resource structure and code generation pipeline:

### 10.1. File & Directory Layout
- **Resource Root:** All localization files must be flat JSON files located inside `src/commonMain/resources/localization/<group>/<module>/<locale>/<bundleName>.json`.
- **Module Path Resolution:** The relative path from `resources/localization/` up to the locale directory forms the module identifier (e.g., `core/common`, `core/security`, `feature/user`).
- **Locale Directories:** Standard ISO language tags (e.g., `en`, `ru`). English (`en`) serves as the Single Source of Truth (SSOT) fallback for extracting keys and parameter signatures.
- **Excluded Files:** Email body templates (`email_templates.json`) are excluded from string generator tasks.

### 10.2. Key & Parameter Naming Conventions
- **Flat JSON Format:** Key-value pairs of string templates (`"key": "template"`).
- **Snake_case Keys:** JSON keys must use `snake_case` (e.g., `not_found`, `service_unavailable`, `max_failed_attempts`).
- **Explicit Suffix Rules:**
  - `_without_args`: Used when a key has a static version without placeholders alongside a parameterized version (e.g., `not_found_without_args` generates property `notFoundWithoutArgs`).
  - `_with_args`: Used when a key has placeholders alongside a static version (e.g., `not_found_with_args` with value `"Resource {resource} not found"` generates function `notFound(resource: Any)`).
- **Placeholder Syntax:** Placeholders use single curly braces `{paramName}` (e.g., `"Page {page} of {totalPages}"`).
  - Keys without placeholders generate Kotlin `val` properties (and TS `get` accessors).
  - Keys with placeholders generate Kotlin `fun` functions (and TS methods accepting named object or positional arguments).

### 10.3. Generated Kotlin Code (`build/generated/sources/localization`)
- **Bundle Classes (`*Strings.kt`):** Strongly typed bundle wrappers (e.g., `ErrorsStrings`, `UiStrings`) accepting `LocalizedStringResolver` and `locale: String`. Automatically registers its module embedded resources with `LocalizationRegistry` in `init`.
- **Module Facade Classes (`*ModuleStrings.kt`):** Module-level facades aggregating all bundle classes for that module (e.g., `CommonModuleStrings`, `SecurityModuleStrings`, `UserModuleStrings`). Accepts default parameters (`resolver = DefaultLocalizedStringResolver()`, `locale = CommonLocales.DEFAULT`) and auto-registers module resources in `init`.
- **Embedded Localization Objects (`*EmbeddedLocalizationResources.kt`):** Generated `internal object <ModulePrefix>EmbeddedLocalizationResources : LocalizationBundleProvider` embedding all string maps directly into compiled Kotlin bytecode. Uses separate private functions per bundle/locale for lazy memory allocation.
- **Core Abstractions (`core/common`):**
  - `LocalizedStringResolver`: Interface `resolve(modulePath: String, bundleName: String, key: String, locale: String): String?`.
  - `LocalizationBundleProvider`: Interface `get(module: String, file: String, locale: String): Map<String, String>?`.
  - `LocalizationRegistry`: Thread-safe singleton `LocalizationRegistry.register(module, provider)` maintaining module bundle providers with O(1) direct map lookup.
  - `DefaultLocalizedStringResolver`: Out-of-the-box thread-safe resolver with in-memory caching and BCP 47 fallback chain (`locale -> language -> CommonLocales.DEFAULT`). Resolves from `LocalizationRegistry` and supports partial key overrides via optional `resourceLoader`.
  - `CommonLocales`: Standard BCP 47 language code constants (`ENGLISH = "en"`, `RUSSIAN = "ru"`, `DEFAULT = "en"`).
  - `LocalizationFormatter`: Public object providing `{placeholder}` string interpolation via `format(template: String, params: Map<String, Any>): String`.

### 10.4. Generated Web / TypeScript Code (`tools/npm-generator`)
- **NPM Artifact Exports (`@mudrichenkoevgeny/shared-foundation`):**
  - `localizationResources`: Raw JSON resource dictionary constant containing templates for all locales.
  - `formatLocalizedString`: Pure JavaScript function replacing `{placeholder}` tokens in string templates.
  - `defaultLocalizedStringResolver`: Built-in string resolver fetching from `localizationResources`.
  - **Bundle Classes:** `CommonErrorsStrings`, `CommonUiStrings`, `SecurityErrorsStrings`, `UserErrorsStrings`, `UserUiStrings`.
  - **Module Facades:** `CommonModuleStrings`, `SecurityModuleStrings`, `UserModuleStrings`.
  - **Root Facade:** `SharedFoundationLocalization`.

---
*Refer to `AGENTS.md` for coding style rules (No FQN, No Comments).*
