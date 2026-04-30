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

## 2. Serialization & Wire Format

- **Implementation:** Strictly `kotlinx.serialization`.
- **Global Config:** Always use `FoundationJson` (from `core/common`) for encoding/decoding to ensure `snake_case` naming, `ignoreUnknownKeys`, and `encodeDefaults` are consistent.
- **Naming:** Use `@SerialName` for all properties. Values must come from `CommonApiFields`, `SecurityApiFields`, or other module-specific contract objects.
- **Validation Hints:** Use JVM-retention annotations from `core/common` (`@RequiredField`, `@NotBlankStringField`) to mark DTO requirements for consumers.

## 3. HTTP Route Documentation (The "Gold Standard")

Every route constant in `feature/*-api` must be documented using a strict KDoc hierarchy. This is the **primary source of truth** for both backend and client developers.

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
    - **Public Access:** State `None (Public API)` if applicable.
6. **Security:** Notes on **MFA Step-up** requirements (e.g., `REAUTHENTICATE_SESSION`) for sensitive actions.
7. **Observability (Audit):** Define the `AuditActionType` and `AuditResourceType` triggered by this route.

## 4. Audit & Extensibility Patterns

- **Sensitivity:** When defining metadata or resource IDs, always consider `AuditValueSensitivity` (Full mask, Partial mask, or IP-address) to ensure PII compliance.
- **Composite Parsers:** For extensible domain types (Actions, Resources), use `CompositeAuditActionTypeParser` and similar tools to resolve types from wire strings.
- **Metadata:** Use `CommonAuditMetadataKey` for technical context (IP, OS, Trace ID) to ensure logs are searchable across the platform.

## 5. Error Handling & Machine-Readable Codes

- **Wire Shape:** All errors must use `ApiErrorResponse`.
- **Contract Only:** This library defines **only the code strings** (e.g., `SecurityErrorCodes.INVALID_MFA_CODE`) and **argument keys**.
- **No Localization:** Do not include human-readable text. Consumers map `code` to localized strings in the UI.

## 6. WebSocket Communication

- **Envelopes:** Use `SocketFrame` for all messages. The `payload` field must be a `JsonElement?`, allowing for type-safe polymorphic serialization on the consumer side.
- **Event Types:** Use `CommonWebSocketEventTypes` and feature-specific strings (e.g., `UserWebSocketEventTypes`) to signal state changes.

## 7. Logic vs. Frameworks

- **No Framework Imports:** Do not add dependencies on Ktor Server, Ktor Client, or Database drivers.
- **Pure Logic:** Logic like `PasswordPolicyValidatorImpl` must be pure Kotlin. If a feature requires platform-specific behavior, define an `interface` in `core` and let consumers provide the implementation.
- **No Environment Data:** Base URLs and secrets are forbidden. Use relative path segments only.

---
*Refer to `AGENTS.md` for coding style rules (No FQN, No Comments).*```