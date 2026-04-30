# feature/user

**Comprehensive IAM (Identity and Access Management) subsystem.** This module provides a complete set of HTTP and WebSocket contracts, domain models, and mappers for managing users, authentication flows, sessions, and identifiers across Backend and KMP clients.

The module is built on a hierarchical routing system (Open, Self-Management, and Management) and is deeply integrated with [core/security](../../core/security/README.md) and [core/audit](../../core/audit/README.md).

## Core Capabilities

### 1. Multi-Channel Authentication
Supports diverse authentication strategies across all lifecycle stages (registration, login, recovery):
- **Email & Password:** Standard registration and login flows.
- **Phone:** SMS-based or phone-linked authentication.
- **External Auth Providers:** Integration with OAuth/Social providers (Google, Apple, etc.).
- **Token Management:** Handling of Access, Refresh, and Session tokens, including secure hashing and refresh cycles.

### 2. User & Profile Management
- **Self-Service:** Profile retrieval, updates, and account deletion/restoration.
- **Administrative CRUD:** Full management of user accounts, status ([UserAccountStatus]), and authority levels.
- **User Configuration:** Managed key-value or structured user-specific settings ([UserConfiguration]).

### 3. Identity & Session Control
- **Identifiers:** Linking and unlinking multiple identities (Email, Phone, External IDs) to a single account.
- **Session Management:** Full visibility into active sessions ([UserSession]) with the ability to terminate individual or global sessions.
- **Data Masking:** Permissions-based masking of sensitive data in identifiers and sessions (e.g., masked vs. unmasked emails).

### 4. Multifactor Security
Built-in TOTP enrollment and verification lifecycle:
- **MFA Step-up:** Enforces re-authentication for sensitive actions like security setting changes.
- **Recovery Codes:** Generation and management of backup access secrets.

## Permission System

Permissions are strictly scoped by target roles ([UserRole]) and objects:

| Object | Permission Codes |
| :--- | :--- |
| **Users** | [UserPermissionCode] — CRUD, status, authority, and security updates. |
| **Sessions** | [SessionPermissionCode] — View (masked/unmasked) and terminate sessions. |
| **Identifiers** | [IdentifierPermissionCode] — View (masked/unmasked) and manage linked IDs. |
| **Auth Settings** | [AuthSettingsPermissionCode] — Manage global auth provider availability. |

## Network & Data Contracts

### Complete Payload Ecosystem
The module provides DTOs for every domain entity to ensure type safety across the wire:
- **Auth:** [AuthDataPayload], [AvailableAuthProvidersPayload], [PublicAuthSettingsPayload].
- **Tokens:** [RefreshTokenPayload], [SessionTokenPayload].
- **Sessions:** [UserSessionPayload], [DeletedSessionsPayload].
- **User:** [UserDetailsPayload], [UserPublicPayload], [UserConfigurationPayload].
- **Identifiers:** [UserIdentifierPayload].

### Real-Time Communication
Typed WebSocket contracts for live updates:
- **Events:** [UserWebSocketEventTypes] for state changes.
- **Protocol:** [AuthenticatedWebSocketContract] for secure connections.
- **Reliability:** Defined [UserWebSocketCloseReasons] for robust error handling.

## Error Handling
Standardized error management using **[UserErrorCodes]** and dynamic **[UserErrorArgs]** for context-aware error reporting (e.g., specific validation failures or security blocks).

---

### Key Component Links

| Category | Links |
| :--- | :--- |
| **Routes (Base)** | [BaseAuthRoutes], [BaseOpenUserRoutes], [BaseManagementUserRoutes], [BaseSelfManagementUserRoutes] |
| **Routes (Open)** | [OpenLoginRoutes], [OpenRegisterRoutes], [OpenResetPasswordRoutes], [OpenUserSecurityRoutes] |
| **Routes (Mgmt)** | [ManagementUserRoutes], [ManagementSessionRoutes], [ManagementIdentifierRoutes] |
| **Mappers** | [AuthDataMapper], [UserSessionMapper], [UserIdentifierMapper], [UserDetailsMapper] |
| **Domain** | [UserRole], [UserAccountStatus], [UserFilterValues], [UserSortValues] |

[BaseAuthRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/base/auth/BaseAuthRoutes.kt
[BaseOpenUserRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/base/user/BaseOpenUserRoutes.kt
[BaseManagementUserRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/base/user/BaseManagementUserRoutes.kt
[BaseSelfManagementUserRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/base/user/BaseSelfManagementUserRoutes.kt
[OpenLoginRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/auth/login/OpenLoginRoutes.kt
[OpenRegisterRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/auth/register/OpenRegisterRoutes.kt
[OpenUserSecurityRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/user/security/OpenUserSecurityRoutes.kt
[ManagementUserRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/management/user/ManagementUserRoutes.kt
[ManagementSessionRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/management/session/ManagementSessionRoutes.kt
[ManagementIdentifierRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/management/identifier/ManagementIdentifierRoutes.kt
[UserPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/permission/UserPermissionCode.kt
[SessionPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/permission/SessionPermissionCode.kt
[IdentifierPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/permission/IdentifierPermissionCode.kt
[AuthSettingsPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/permission/AuthSettingsPermissionCode.kt
[UserErrorCodes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/error/naming/UserErrorCodes.kt
[UserWebSocketEventTypes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/contract/UserWebSocketEventTypes.kt
[UserRole]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/role/UserRole.kt
[UserAccountStatus]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/accountstatus/UserAccountStatus.kt
[UserConfiguration]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/configuration/UserConfiguration.kt
[UserSession]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/session/UserSession.kt
[UserFilterValues]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/listing/UserFilterValues.kt
[UserSortValues]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/listing/UserSortValues.kt
[AuthDataPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/auth/data/AuthDataPayload.kt
[UserIdentifierPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/identifier/UserIdentifierPayload.kt
[UserWebSocketCloseReasons]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/contract/UserWebSocketCloseReasons.kt
[AuthenticatedWebSocketContract]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/contract/AuthenticatedWebSocketContract.kt
[UserErrorArgs]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/error/naming/UserErrorArgs.kt