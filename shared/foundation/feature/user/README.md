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
- **Administrative CRUD:** Full management of user accounts, status ([UserAccountStatus]), and authority levels ([UserRoleDefaultAuthorityLevel]).
- **User Configuration:** Open ([OpenUserConfiguration]) and Management ([ManagementUserConfiguration]) aggregated configuration slices.

### 3. Identity & Session Control
- **Identifiers:** Linking and unlinking multiple identities (Email, Phone, External IDs) to a single account ([UserIdentifier]).
- **Session Management:** Full visibility into active sessions ([UserSession]) with the ability to terminate individual or global sessions ([DeletedSessions]).
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

### Complete Request & Payload Ecosystem
The module provides DTOs and Request models for every domain entity to ensure type safety across the wire:

- **Auth Data & Settings:** [AuthDataPayload], [AvailableAuthProvidersPayload], [OpenAuthSettingsPayload], [ManagementAuthSettingsPayload].
- **Configuration:** [OpenUserConfigurationPayload], [ManagementUserConfigurationPayload].
- **Tokens:** [RefreshTokenPayload], [SessionTokenPayload].
- **Sessions & Identifiers:** [UserSessionPayload], [DeletedSessionsPayload], [UserIdentifierPayload].
- **User Profile:** [UserDetailsPayload], [UserPublicPayload].
- **Requests:** [RegisterByEmailRequest], [LoginByEmailRequest], [LoginByPhoneRequest], [LoginByExternalAuthProviderRequest], [CreateByEmailRequest], [ResetPasswordRequest], [SendResetPasswordConfirmationRequest], [SendConfirmationToEmailRequest], [SendConfirmationToPhoneRequest], [EmailPasswordChangeRequest], [AddUserIdentifierEmailRequest], [AddUserIdentifierPhoneRequest], [AddUserIdentifierExternalAuthProviderRequest], [UpdateUserRequest].

### Real-Time Communication
Typed WebSocket contracts for live updates:
- **Events:** [UserWebSocketEventTypes] for state changes.
- **Protocol:** [UserNetworkFoundationConstants] for socket configuration.
- **Reliability:** Defined [UserWebSocketCloseReasons] for robust error handling.

## Error Handling & Audit
Standardized error management using **[UserErrorCodes]** and dynamic **[UserErrorArgs]** for context-aware error reporting.
Full audit taxonomy mapped via **[UserAuditActionType]**, **[UserAuditResourceType]**, **[UserAuditMetadataKey]**, and **[UserAuditMetadataDeniedReasonValues]**.

---

## Component Index

### HTTP Routes

| Domain | Open Routes | Self-Management Routes | Management Routes |
| :--- | :--- | :--- | :--- |
| **Auth** | [OpenLoginRoutes]<br>[OpenRegisterRoutes]<br>[OpenResetPasswordRoutes]<br>[OpenRefreshTokenRoutes]<br>[OpenAuthSettingsRoutes] | [SelfManagementLoginRoutes]<br>[SelfManagementRefreshTokenRoutes]<br>[SelfManagementResetPasswordRoutes] | [ManagementAuthSettingsRoutes] |
| **User Profile** | [OpenUserRoutes] | [SelfManagementUserRoutes] | [ManagementUserRoutes] |
| **User Security** | [OpenUserSecurityRoutes] | [SelfManagementUserSecurityRoutes] | [ManagementUserSecurityRoutes] |
| **Identifiers** | [OpenIdentifierRoutes] | [SelfManagementIdentifierRoutes] | [ManagementIdentifierRoutes] |
| **Sessions** | [OpenSessionRoutes] | [SelfManagementSessionRoutes] | [ManagementSessionRoutes] |
| **Configuration** | [OpenUserConfigurationRoutes] | — | [ManagementUserConfigurationRoutes] |

### Mappers

| Category | Mapper Classes |
| :--- | :--- |
| **Auth & Settings** | [AuthDataMapper]<br>[AvailableAuthProvidersMapper]<br>[OpenAuthSettingsMapper]<br>[ManagementAuthSettingsMapper] |
| **Configuration** | [OpenUserConfigurationMapper]<br>[ManagementUserConfigurationMapper] |
| **User Profile** | [UserDetailsMapper]<br>[UserPublicMapper] |
| **Identifiers & Sessions** | [UserIdentifierMapper]<br>[UserSessionMapper]<br>[DeletedSessionsMapper]<br>[SessionTokenMapper] |

### Domain Models

| Category | Domain Classes |
| :--- | :--- |
| **Auth & Settings** | [AuthData]<br>[AvailableAuthProviders]<br>[OpenAuthSettings]<br>[ManagementAuthSettings]<br>[ExternalAuthProvider]<br>[UserAuthProvider] |
| **Configuration** | [OpenUserConfiguration]<br>[ManagementUserConfiguration] |
| **User Profile** | [UserDetails]<br>[UserPublic]<br>[UserId]<br>[UserRole]<br>[UserAccountStatus]<br>[UserRoleDefaultAuthorityLevel] |
| **Identifiers** | [UserIdentifier]<br>[UserIdentifierId]<br>[UserIdentifierInternal] |
| **Sessions** | [UserSession]<br>[UserSessionId]<br>[UserSessionInternal]<br>[DeletedSessions] |
| **Tokens** | [AccessToken]<br>[RefreshToken]<br>[RefreshTokenHash]<br>[SessionToken] |
| **Listings & Filters** | [UserFilterValues]<br>[UserSortValues]<br>[UserSortBy]<br>[UserIdentifierSortBy]<br>[UserSessionSortBy] |

---

[OpenLoginRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/auth/login/OpenLoginRoutes.kt
[OpenRegisterRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/auth/register/OpenRegisterRoutes.kt
[OpenResetPasswordRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/auth/resetpassword/OpenResetPasswordRoutes.kt
[OpenRefreshTokenRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/auth/refreshtoken/OpenRefreshTokenRoutes.kt
[OpenAuthSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/auth/settings/OpenAuthSettingsRoutes.kt
[OpenUserConfigurationRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/configuration/OpenUserConfigurationRoutes.kt
[OpenIdentifierRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/identifier/OpenIdentifierRoutes.kt
[OpenSessionRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/session/OpenSessionRoutes.kt
[OpenUserRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/user/OpenUserRoutes.kt
[OpenUserSecurityRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/user/security/OpenUserSecurityRoutes.kt

[SelfManagementLoginRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/management/auth/login/SelfManagementLoginRoutes.kt
[SelfManagementRefreshTokenRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/management/auth/refreshtoken/SelfManagementRefreshTokenRoutes.kt
[SelfManagementResetPasswordRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/management/auth/resetpassword/SelfManagementResetPasswordRoutes.kt
[SelfManagementIdentifierRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/management/identifier/SelfManagementIdentifierRoutes.kt
[SelfManagementSessionRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/management/session/SelfManagementSessionRoutes.kt
[SelfManagementUserRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/management/user/SelfManagementUserRoutes.kt
[SelfManagementUserSecurityRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/management/user/security/SelfManagementUserSecurityRoutes.kt

[ManagementAuthSettingsRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/management/auth/settings/ManagementAuthSettingsRoutes.kt
[ManagementUserConfigurationRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/management/configuration/ManagementUserConfigurationRoutes.kt
[ManagementIdentifierRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/management/identifier/ManagementIdentifierRoutes.kt
[ManagementSessionRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/management/session/ManagementSessionRoutes.kt
[ManagementUserRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/management/user/ManagementUserRoutes.kt
[ManagementUserSecurityRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/management/user/security/ManagementUserSecurityRoutes.kt

[AuthDataMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/auth/data/AuthDataMapper.kt
[AvailableAuthProvidersMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/auth/settings/AvailableAuthProvidersMapper.kt
[OpenAuthSettingsMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/auth/settings/OpenAuthSettingsMapper.kt
[ManagementAuthSettingsMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/auth/settings/ManagementAuthSettingsMapper.kt
[OpenUserConfigurationMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/configuration/OpenUserConfigurationMapper.kt
[ManagementUserConfigurationMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/configuration/ManagementUserConfigurationMapper.kt
[UserIdentifierMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/identifier/UserIdentifierMapper.kt
[UserSessionMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/session/UserSessionMapper.kt
[DeletedSessionsMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/session/DeletedSessionsMapper.kt
[SessionTokenMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/token/SessionTokenMapper.kt
[UserDetailsMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/user/UserDetailsMapper.kt
[UserPublicMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/user/UserPublicMapper.kt

[AuthData]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/auth/data/AuthData.kt
[AvailableAuthProviders]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/auth/settings/AvailableAuthProviders.kt
[OpenAuthSettings]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/auth/settings/OpenAuthSettings.kt
[ManagementAuthSettings]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/auth/settings/ManagementAuthSettings.kt
[ExternalAuthProvider]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/authprovider/ExternalAuthProvider.kt
[UserAuthProvider]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/authprovider/UserAuthProvider.kt
[OpenUserConfiguration]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/configuration/OpenUserConfiguration.kt
[ManagementUserConfiguration]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/configuration/ManagementUserConfiguration.kt
[UserDetails]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/user/UserDetails.kt
[UserPublic]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/user/UserPublic.kt
[UserId]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/user/UserId.kt
[UserRole]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/role/UserRole.kt
[UserAccountStatus]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/accountstatus/UserAccountStatus.kt
[UserRoleDefaultAuthorityLevel]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/authoritylevel/UserRoleDefaultAuthorityLevel.kt
[UserIdentifier]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/identifier/UserIdentifier.kt
[UserIdentifierId]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/identifier/UserIdentifierId.kt
[UserIdentifierInternal]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/identifier/UserIdentifierInternal.kt
[UserSession]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/session/UserSession.kt
[UserSessionId]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/session/UserSessionId.kt
[UserSessionInternal]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/session/UserSessionInternal.kt
[DeletedSessions]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/session/DeletedSessions.kt
[AccessToken]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/token/AccessToken.kt
[RefreshToken]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/token/RefreshToken.kt
[RefreshTokenHash]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/token/RefreshTokenHash.kt
[SessionToken]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/token/SessionToken.kt
[UserFilterValues]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/listing/UserFilterValues.kt
[UserSortValues]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/listing/UserSortValues.kt
[UserSortBy]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/listing/UserSortValues.kt
[UserIdentifierSortBy]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/listing/UserSortValues.kt
[UserSessionSortBy]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/listing/UserSortValues.kt

[AuthDataPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/auth/data/AuthDataPayload.kt
[AvailableAuthProvidersPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/auth/settings/AvailableAuthProvidersPayload.kt
[OpenAuthSettingsPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/auth/settings/OpenAuthSettingsPayload.kt
[ManagementAuthSettingsPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/auth/settings/ManagementAuthSettingsPayload.kt
[OpenUserConfigurationPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/configuration/OpenUserConfigurationPayload.kt
[ManagementUserConfigurationPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/configuration/ManagementUserConfigurationPayload.kt
[UserIdentifierPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/identifier/UserIdentifierPayload.kt
[UserSessionPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/session/UserSessionPayload.kt
[DeletedSessionsPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/session/DeletedSessionsPayload.kt
[UserDetailsPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/user/UserDetailsPayload.kt
[UserPublicPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/user/UserPublicPayload.kt
[RefreshTokenPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/token/RefreshTokenPayload.kt
[SessionTokenPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/token/SessionTokenPayload.kt

[RegisterByEmailRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/request/auth/register/RegisterByEmailRequest.kt
[LoginByEmailRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/request/auth/login/LoginByEmailRequest.kt
[LoginByPhoneRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/request/auth/login/LoginByPhoneRequest.kt
[LoginByExternalAuthProviderRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/request/auth/login/LoginByExternalAuthProviderRequest.kt
[CreateByEmailRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/request/auth/create/CreateByEmailRequest.kt
[ResetPasswordRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/request/auth/password/ResetPasswordRequest.kt
[SendResetPasswordConfirmationRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/request/auth/password/SendResetPasswordConfirmationRequest.kt
[SendConfirmationToEmailRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/request/confirmation/SendConfirmationToEmailRequest.kt
[SendConfirmationToPhoneRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/request/confirmation/SendConfirmationToPhoneRequest.kt
[EmailPasswordChangeRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/request/security/password/EmailPasswordChangeRequest.kt
[AddUserIdentifierEmailRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/request/security/useridentifiers/AddUserIdentifierEmailRequest.kt
[AddUserIdentifierPhoneRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/request/security/useridentifiers/AddUserIdentifierPhoneRequest.kt
[AddUserIdentifierExternalAuthProviderRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/request/security/useridentifiers/AddUserIdentifierExternalAuthProviderRequest.kt
[UpdateUserRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/request/user/UpdateUserRequest.kt

[UserPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/permission/UserPermissionCode.kt
[SessionPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/permission/SessionPermissionCode.kt
[IdentifierPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/permission/IdentifierPermissionCode.kt
[AuthSettingsPermissionCode]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/permission/AuthSettingsPermissionCode.kt

[UserErrorCodes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/error/naming/UserErrorCodes.kt
[UserErrorArgs]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/error/naming/UserErrorArgs.kt

[UserWebSocketEventTypes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/contract/UserWebSocketEventTypes.kt
[UserWebSocketCloseReasons]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/contract/UserWebSocketCloseReasons.kt
[UserNetworkFoundationConstants]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/contract/UserNetworkFoundationConstants.kt

[UserAuditActionType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/audit/action/UserAuditActionType.kt
[UserAuditResourceType]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/audit/resource/UserAuditResourceType.kt
[UserAuditMetadataKey]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/audit/metadata/UserAuditMetadataKey.kt
[UserAuditMetadataDeniedReasonValues]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/audit/metadata/UserAuditMetadataDeniedReasonValues.kt
