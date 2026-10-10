# feature/user

**Comprehensive IAM (Identity and Access Management) subsystem.** This module provides a complete set of HTTP and WebSocket contracts, domain models, and mappers for managing users, authentication flows, sessions, and identifiers across Backend and KMP clients.

The module is built on a hierarchical routing system (Open, Self-Management, and Management) and is deeply integrated with [core/security](../../core/security/README.md) and [core/audit](../../core/audit/README.md).

## Core Capabilities

### 1. Multi-Channel Authentication
Supports diverse authentication strategies across all lifecycle stages (registration, login, recovery, unlock):
- **Email & Password:** Standard registration and login flows with email restriction policy enforcement ([EmailRestrictionPolicy]).
- **Phone:** SMS-based or phone-linked authentication.
- **External Auth Providers:** Integration with OAuth/Social providers (Google, Apple, etc.).
- **Self-Service Account Unlock:** Email/SMS OTP and OAuth token-based unlock flows for temporarily locked accounts.
- **Token Management:** Handling of Access, Refresh, and Session tokens, including secure hashing and refresh cycles.

### 2. User & Profile Management
Entities follow the standardized 4-tier projection model (`Summary`, `Private`, `Internal`):
- **User Summary:** [UserSummary] / [UserSummaryPayload] for compact list views, mentions, and cards.
- **User Private:** [UserPrivate] / [UserPrivatePayload] for authenticated self-service ("My Account") and management CRUD.
- **User Internal:** [UserInternal] for server/database storage (includes `updatedAt`). **No wire Payload!**
- **User Configuration:** Open ([OpenUserConfiguration]) and Management ([ManagementUserConfiguration]) aggregated configuration slices.

### 3. Identity & Session Control
- **Identifiers:** Linking and unlinking multiple identities ([UserIdentifierSummary], [UserIdentifierPrivate], [UserIdentifierInternal]).
- **Session Management:** Full visibility into active sessions ([UserSessionSummary], [UserSessionPrivate], [UserSessionInternal]) with separate session limit policies for open users vs. management users, and ability to terminate individual or global sessions ([DeletedSessions]).
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
| **Identifiers** | [IdentifierPermissionCode] — View (masked/unmasked), manage linked IDs, and revoke passwords. |
| **Auth Settings** | [AuthSettingsPermissionCode] — Manage global auth provider availability and email restrictions. |

## Network & Data Contracts

### Complete Request & Payload Ecosystem
The module provides DTOs and Request models for every domain entity to ensure type safety across the wire:

- **Auth Data & Settings:** [AuthDataPayload], [AvailableAuthProvidersPayload], [OpenAuthSettingsPayload], [ManagementAuthSettingsPayload], [EmailRestrictionPolicyPayload].
- **Configuration:** [OpenUserConfigurationPayload], [ManagementUserConfigurationPayload].
- **Tokens:** [RefreshTokenPayload], [SessionTokenPayload].
- **Sessions & Identifiers:** [UserSessionSummaryPayload], [UserSessionPrivatePayload], [DeletedSessionsPayload], [UserIdentifierSummaryPayload], [UserIdentifierPrivatePayload].
- **User Profile:** [UserSummaryPayload], [UserPrivatePayload].
- **Requests:** [RegisterByEmailRequest], [LoginByEmailRequest], [LoginByPhoneRequest], [LoginByExternalAuthProviderRequest], [CreateByEmailRequest], [ResetPasswordRequest], [SendResetPasswordConfirmationRequest], [SendConfirmationToEmailRequest], [SendConfirmationToPhoneRequest], [UnlockByEmailConfirmationRequest], [UnlockByPhoneConfirmationRequest], [UnlockByExternalAuthProviderRequest], [EmailPasswordChangeRequest], [AddUserIdentifierEmailRequest], [AddUserIdentifierPhoneRequest], [AddUserIdentifierExternalAuthProviderRequest], [UpdateUserRequest].

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
| **Auth** | [OpenLoginRoutes]<br>[OpenRegisterRoutes]<br>[OpenResetPasswordRoutes]<br>[OpenUnlockRoutes]<br>[OpenRefreshTokenRoutes]<br>[OpenAuthSettingsRoutes] | [SelfManagementLoginRoutes]<br>[SelfManagementRefreshTokenRoutes]<br>[SelfManagementResetPasswordRoutes]<br>[SelfManagementUnlockRoutes] | [ManagementAuthSettingsRoutes] |
| **User Profile** | [OpenUserRoutes] | [SelfManagementUserRoutes] | [ManagementUserRoutes] |
| **User Security** | [OpenUserSecurityRoutes] | [SelfManagementUserSecurityRoutes] | [ManagementUserSecurityRoutes] |
| **Identifiers** | [OpenIdentifierRoutes] | [SelfManagementIdentifierRoutes] | [ManagementIdentifierRoutes] |
| **Sessions** | [OpenSessionRoutes] | [SelfManagementSessionRoutes] | [ManagementSessionRoutes] |
| **Configuration** | [OpenUserConfigurationRoutes] | — | [ManagementUserConfigurationRoutes] |

### Mappers

| Category | Mapper Classes |
| :--- | :--- |
| **Auth & Settings** | [AuthDataMapper]<br>[AvailableAuthProvidersMapper]<br>[OpenAuthSettingsMapper]<br>[ManagementAuthSettingsMapper]<br>[EmailRestrictionPolicyMapper] |
| **Configuration** | [OpenUserConfigurationMapper]<br>[ManagementUserConfigurationMapper] |
| **User Profile** | [UserSummaryMapper]<br>[UserPrivateMapper]<br>[UserDomainMapper] |
| **Identifiers & Sessions** | [UserIdentifierSummaryMapper]<br>[UserIdentifierPrivateMapper]<br>[UserIdentifierDomainMapper]<br>[UserSessionSummaryMapper]<br>[UserSessionPrivateMapper]<br>[UserSessionDomainMapper]<br>[DeletedSessionsMapper]<br>[SessionTokenMapper] |

### Domain Models

| Category | Domain Classes |
| :--- | :--- |
| **Auth & Settings** | [AuthData]<br>[AvailableAuthProviders]<br>[OpenAuthSettings]<br>[ManagementAuthSettings]<br>[EmailRestrictionPolicy]<br>[ExternalAuthProvider]<br>[UserAuthProvider] |
| **Configuration** | [OpenUserConfiguration]<br>[ManagementUserConfiguration] |
| **User Profile** | [UserSummary]<br>[UserPrivate]<br>[UserInternal]<br>[UserId]<br>[UserRole]<br>[UserAccountStatus]<br>[UserRoleDefaultAuthorityLevel]<br>[AccountLockoutType] |
| **Identifiers** | [UserIdentifierSummary]<br>[UserIdentifierPrivate]<br>[UserIdentifierInternal]<br>[UserIdentifierId] |
| **Sessions** | [UserSessionSummary]<br>[UserSessionPrivate]<br>[UserSessionInternal]<br>[UserSessionId]<br>[DeletedSessions] |
| **Tokens** | [AccessToken]<br>[RefreshToken]<br>[RefreshTokenHash]<br>[SessionToken] |
| **Listings & Filters** | [UserFilterValues]<br>[UserSortValues]<br>[UserSortBy]<br>[UserIdentifierSortBy]<br>[UserSessionSortBy] |

---

[OpenLoginRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/auth/login/OpenLoginRoutes.kt
[OpenRegisterRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/auth/register/OpenRegisterRoutes.kt
[OpenResetPasswordRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/auth/resetpassword/OpenResetPasswordRoutes.kt
[OpenUnlockRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/open/auth/unlock/OpenUnlockRoutes.kt
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
[SelfManagementUnlockRoutes]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/route/management/auth/unlock/SelfManagementUnlockRoutes.kt
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
[EmailRestrictionPolicyMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/emailrestriction/EmailRestrictionPolicyMapper.kt
[OpenUserConfigurationMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/configuration/OpenUserConfigurationMapper.kt
[ManagementUserConfigurationMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/configuration/ManagementUserConfigurationMapper.kt
[UserIdentifierSummaryMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/identifier/UserIdentifierSummaryMapper.kt
[UserIdentifierPrivateMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/identifier/UserIdentifierPrivateMapper.kt
[UserIdentifierDomainMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/identifier/UserIdentifierDomainMapper.kt
[UserSessionSummaryMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/session/UserSessionSummaryMapper.kt
[UserSessionPrivateMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/session/UserSessionPrivateMapper.kt
[UserSessionDomainMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/session/UserSessionDomainMapper.kt
[DeletedSessionsMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/session/DeletedSessionsMapper.kt
[SessionTokenMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/token/SessionTokenMapper.kt
[UserSummaryMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/user/UserSummaryMapper.kt
[UserPrivateMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/user/UserPrivateMapper.kt
[UserDomainMapper]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/mapper/user/UserDomainMapper.kt

[AuthData]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/auth/data/AuthData.kt
[AvailableAuthProviders]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/auth/settings/AvailableAuthProviders.kt
[OpenAuthSettings]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/auth/settings/OpenAuthSettings.kt
[ManagementAuthSettings]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/auth/settings/ManagementAuthSettings.kt
[EmailRestrictionPolicy]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/emailrestriction/EmailRestrictionPolicy.kt
[ExternalAuthProvider]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/authprovider/ExternalAuthProvider.kt
[UserAuthProvider]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/authprovider/UserAuthProvider.kt
[OpenUserConfiguration]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/configuration/OpenUserConfiguration.kt
[ManagementUserConfiguration]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/configuration/ManagementUserConfiguration.kt
[UserSummary]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/user/UserSummary.kt
[UserPrivate]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/user/UserPrivate.kt
[UserInternal]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/user/UserInternal.kt
[UserId]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/user/UserId.kt
[UserRole]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/role/UserRole.kt
[UserAccountStatus]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/accountstatus/UserAccountStatus.kt
[UserRoleDefaultAuthorityLevel]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/authoritylevel/UserRoleDefaultAuthorityLevel.kt
[AccountLockoutType]: ../core/security/src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/core/security/domain/model/accountlockout/AccountLockoutType.kt
[UserIdentifierSummary]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/identifier/UserIdentifierSummary.kt
[UserIdentifierPrivate]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/identifier/UserIdentifierPrivate.kt
[UserIdentifierInternal]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/identifier/UserIdentifierInternal.kt
[UserIdentifierId]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/identifier/UserIdentifierId.kt
[UserSessionSummary]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/session/UserSessionSummary.kt
[UserSessionPrivate]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/session/UserSessionPrivate.kt
[UserSessionInternal]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/session/UserSessionInternal.kt
[UserSessionId]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/domain/model/session/UserSessionId.kt
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
[EmailRestrictionPolicyPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/emailrestriction/EmailRestrictionPolicyPayload.kt
[OpenUserConfigurationPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/configuration/OpenUserConfigurationPayload.kt
[ManagementUserConfigurationPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/configuration/ManagementUserConfigurationPayload.kt
[UserIdentifierSummaryPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/identifier/UserIdentifierSummaryPayload.kt
[UserIdentifierPrivatePayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/identifier/UserIdentifierPrivatePayload.kt
[UserSessionSummaryPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/session/UserSessionSummaryPayload.kt
[UserSessionPrivatePayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/session/UserSessionPrivatePayload.kt
[DeletedSessionsPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/session/DeletedSessionsPayload.kt
[UserSummaryPayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/user/UserSummaryPayload.kt
[UserPrivatePayload]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/model/user/UserPrivatePayload.kt
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
[UnlockByEmailConfirmationRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/request/auth/unlock/UnlockByEmailConfirmationRequest.kt
[UnlockByPhoneConfirmationRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/request/auth/unlock/UnlockByPhoneConfirmationRequest.kt
[UnlockByExternalAuthProviderRequest]: src/commonMain/kotlin/io/github/mudrichenkoevgeny/shared/foundation/feature/user/network/request/auth/unlock/UnlockByExternalAuthProviderRequest.kt
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
