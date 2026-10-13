# shared-foundation Architecture

This document provides a visual representation of the static structure, taxonomy of contracts, and module dependencies within the `shared-foundation` project. As a KMP library containing shared wire types (DTOs, constants, error codes, and routes), it does not include execution flows or engine wiring.

## 1. Module Dependency Graph

The following graph illustrates the static dependencies between the library modules, demonstrating the direction from `feature/*` to `core/*`.

```mermaid
graph TD
    core_common["core/common"]
    core_audit["core/audit"]
    core_security["core/security"]
    core_settings["core/settings"]
    
    feature_user["feature/user"]
    
    core_audit --> core_common
    core_security --> core_audit
    core_security --> core_common
    core_settings --> core_audit
    core_settings --> core_common
    
    feature_user --> core_settings
    feature_user --> core_security
    feature_user --> core_audit
    feature_user --> core_common
```

## 2. Surface Separation: Open, Self-Management, and Management

This flowchart visualizes the architectural separation between unprivileged (Open), authenticated self-service (Self-Management), and protected admin (Management) API surfaces. It maps management routes to their corresponding, strongly-typed permission codes.

```mermaid
flowchart TB
    subgraph APISurfaces ["API Surfaces"]
        OpenAPI["Open API (Public / Unprivileged)"]
        SelfManagementAPI["Self-Management API (Authenticated Self-Service)"]
        ManagementAPI["Management API (Protected / Admin)"]
    end

    subgraph OpenRoutes ["Open Routes"]
        OpenGlobalSettingsRoutes["OpenGlobalSettingsRoutes"]
        OpenSecuritySettingsRoutes["OpenSecuritySettingsRoutes"]
        OpenUserRoutes["OpenUserRoutes"]
        OpenSessionRoutes["OpenSessionRoutes (open.user.session)"]
        OpenUnlockRoutes["OpenUnlockRoutes"]
        OpenIdentifierRoutes["OpenIdentifierRoutes (open.user.identifier)"]
        OpenAuthSettingsRoutes["OpenAuthSettingsRoutes"]
    end

    subgraph SelfManagementRoutes ["Self-Management Routes"]
        SelfManagementUserRoutes["SelfManagementUserRoutes"]
        SelfManagementSessionRoutes["SelfManagementSessionRoutes"]
        SelfManagementIdentifierRoutes["SelfManagementIdentifierRoutes"]
        SelfManagementUserSecurityRoutes["SelfManagementUserSecurityRoutes"]
        SelfManagementLoginRoutes["SelfManagementLoginRoutes"]
        SelfManagementUnlockRoutes["SelfManagementUnlockRoutes"]
        SelfManagementResetPasswordRoutes["SelfManagementResetPasswordRoutes"]
    end

    subgraph ManagementRoutes ["Management Routes"]
        ManagementGlobalSettingsRoutes["ManagementGlobalSettingsRoutes"]
        ManagementSecuritySettingsRoutes["ManagementSecuritySettingsRoutes"]
        ManagementAuditRoutes["ManagementAuditRoutes"]
        ManagementUsersRoutes["ManagementUsersRoutes"]
        ManagementSessionsRoutes["ManagementSessionsRoutes"]
        ManagementIdentifiersRoutes["ManagementIdentifiersRoutes"]
        ManagementUsersSecurityRoutes["ManagementUsersSecurityRoutes"]
    end

    subgraph PermissionCodes ["Permission Codes"]
        SettingsPermissionCode["SettingsPermissionCode"]
        SecurityPermissionCode["SecurityPermissionCode"]
        AuditPermissionCode["AuditPermissionCode"]
        UserPermissionCode["UserPermissionCode"]
        SessionPermissionCode["SessionPermissionCode"]
        IdentifierPermissionCode["IdentifierPermissionCode"]
        PermissionCode["PermissionCode (Base Inline Class)"]
    end

    OpenAPI --> OpenRoutes
    SelfManagementAPI --> SelfManagementRoutes
    ManagementAPI --> ManagementRoutes

    OpenRoutes -.-> OpenGlobalSettingsRoutes
    OpenRoutes -.-> OpenSecuritySettingsRoutes
    OpenRoutes -.-> OpenUserRoutes
    OpenRoutes -.-> OpenSessionRoutes
    OpenRoutes -.-> OpenUnlockRoutes
    OpenRoutes -.-> OpenIdentifierRoutes
    OpenRoutes -.-> OpenAuthSettingsRoutes

    SelfManagementRoutes -.-> SelfManagementUserRoutes
    SelfManagementRoutes -.-> SelfManagementSessionRoutes
    SelfManagementRoutes -.-> SelfManagementIdentifierRoutes
    SelfManagementRoutes -.-> SelfManagementUserSecurityRoutes
    SelfManagementRoutes -.-> SelfManagementLoginRoutes
    SelfManagementRoutes -.-> SelfManagementUnlockRoutes
    SelfManagementRoutes -.-> SelfManagementResetPasswordRoutes

    ManagementRoutes -.-> ManagementGlobalSettingsRoutes
    ManagementRoutes -.-> ManagementSecuritySettingsRoutes
    ManagementRoutes -.-> ManagementAuditRoutes
    ManagementRoutes -.-> ManagementUsersRoutes
    ManagementRoutes -.-> ManagementSessionsRoutes
    ManagementRoutes -.-> ManagementIdentifiersRoutes
    ManagementRoutes -.-> ManagementUsersSecurityRoutes

    ManagementGlobalSettingsRoutes -->|Enforced By| SettingsPermissionCode
    ManagementSecuritySettingsRoutes -->|Enforced By| SecurityPermissionCode
    ManagementAuditRoutes -->|Enforced By| AuditPermissionCode
    ManagementUsersRoutes -->|Enforced By| UserPermissionCode
    ManagementSessionsRoutes -->|Enforced By| SessionPermissionCode
    ManagementIdentifiersRoutes -->|Enforced By| IdentifierPermissionCode

    SettingsPermissionCode -->|Wraps| PermissionCode
    SecurityPermissionCode -->|Wraps| PermissionCode
    AuditPermissionCode -->|Wraps| PermissionCode
    UserPermissionCode -->|Wraps| PermissionCode
    SessionPermissionCode -->|Wraps| PermissionCode
    IdentifierPermissionCode -->|Wraps| PermissionCode
```

## 3. WebSocket Wire Contract

This diagram details the real-time communication envelope (`SocketFrame`) and categorizes the different feature event types flowing through the connection (`WebSocketContract`).

```mermaid
classDiagram
    class WebSocketContract {
        +String WS_OPEN_REALTIME_PATH
        +String WS_MANAGEMENT_REALTIME_PATH
    }

    class SocketFrame {
        +String id
        +String type
        +JsonElement payload
        +Map~String, String~ metadata
        +Long timestamp
    }
    
    class CommonWebSocketEventTypes {
        +String PING
        +String PONG
        +String INITIALIZE
        +String INITIALIZED_SUCCESS
    }
    
    class SettingsWebSocketEventTypes {
        +String OPEN_GLOBAL_SETTINGS_UPDATED
        +String MANAGEMENT_GLOBAL_SETTINGS_UPDATED
    }
    
    class SecurityWebSocketEventTypes {
        +String OPEN_SECURITY_SETTINGS_UPDATED
        +String MANAGEMENT_SECURITY_SETTINGS_UPDATED
    }
    
    class UserWebSocketEventTypes {
        +String UNAUTHORIZED
        +String OPEN_AUTH_SETTINGS_UPDATED
        +String MANAGEMENT_AUTH_SETTINGS_UPDATED
        +String USER_UPDATED
    }
    
    SocketFrame --> CommonWebSocketEventTypes : "Mapped via type"
    SocketFrame --> SettingsWebSocketEventTypes : "Mapped via type"
    SocketFrame --> SecurityWebSocketEventTypes : "Mapped via type"
    SocketFrame --> UserWebSocketEventTypes : "Mapped via type"
```

## 4. Taxonomy of Error Handling

This class diagram illustrates the unified shape of HTTP error bodies (`ApiErrorResponse`) alongside the categorized registry of standard and module-specific error codes.

```mermaid
classDiagram
    class ApiErrorResponse {
        +String id
        +String code
        +String message
        +Map~String, String~ args
    }

    class CommonErrorCodes {
        +String UNKNOWN
        +String INTERNAL
        +String NOT_FOUND
    }

    class SecurityErrorCodes {
        +String PASSWORD_TOO_WEAK
        +String OTP_RETRY_TOO_SOON
        +String TOTP_ALREADY_ENABLED
    }

    class UserErrorCodes {
        +String INVALID_ACCESS_TOKEN
        +String ACCESS_TOKEN_EXPIRED
        +String INVALID_REFRESH_TOKEN
        +String INVALID_SESSION
        +String USER_ROLE_NOT_ALLOWED
    }

    ApiErrorResponse --> CommonErrorCodes : "Supplies code"
    ApiErrorResponse --> SecurityErrorCodes : "Supplies code"
    ApiErrorResponse --> UserErrorCodes : "Supplies code"
```

## 5. Navigation Architecture & UI Presentation Traceability

This diagram illustrates how Network Routes, Navigation Screen Routes, and Navigation Screen Parameter Parsers link together to form a type-safe bridge between network endpoints and client UI screens.

```mermaid
flowchart LR
    subgraph NetworkLayer ["Network Layer"]
        NetworkRoute["Network Route Constant\n(e.g., ManagementUsersRoutes.GET_USERS)"]
    end

    subgraph NavigationLayer ["Navigation Layer"]
        ScreenRoute["Navigation Screen Route\n(e.g., ManagementUsersNavigation.UserList)"]
        ScreenParams["Screen Parameters\n(e.g., ManagementUserListParams)"]
        ScreenParser["Params Parser\n(e.g., ManagementUserListParamsParser)"]
    end

    subgraph UILayer ["Client UI Layer"]
        UIScreen["UI Screen Composable / Component"]
    end

    NetworkRoute -->|Documented via KDoc\nUI Presentation| ScreenRoute
    ScreenRoute -->|Declares Params Type| ScreenParams
    ScreenParser -->|Parses Query/Path to| ScreenParams
    ScreenRoute -->|Mounts & Triggers Primary API| NetworkRoute
    UIScreen -->|Binds to Route & Params| ScreenRoute
```

### Complete Navigation Classes Index

The library provides 11 strongly-typed navigation objects across two main hierarchies (Client and Management):

#### 1. Core Navigation Roots (`core/common`)
- **`ClientMainNavigation`**: Root navigation entry point for Client applications (`ROUTE = "/"`).
- **`ManagementMainNavigation`**: Root navigation entry point for Management/Admin applications (`ROUTE = "/management"`).

#### 2. Client Application Navigation (`feature/user`)
- **`ClientAuthNavigation`**: Unauthenticated authentication and onboarding screens (Welcome, Login by Email/Phone/TOTP, Registration, Reset Password, Account Pending Deletion, and Unlock flows).
- **`ClientUserNavigation`**: Authenticated client profile screens (Main Profile, Self Session List & Details, Self Identifier List & Details, TOTP Setup & Management).

#### 3. Management Application Navigation (`core/*` & `feature/user`)
- **`ManagementAuthNavigation`**: Admin authentication screens (Login by Email/TOTP, Reset Password, Unlock flows).
- **`ManagementAuthSettingsNavigation`**: Management authentication provider and email restriction configuration screen.
- **`ManagementSelfUserNavigation`**: Authenticated admin profile self-service screens (Profile Main, Self Sessions, Self Identifiers, TOTP).
- **`ManagementUsersNavigation`**: Admin user management screens (User List & Detail, Global & User-Specific Session Lists, Global & User-Specific Identifier Lists).
- **`ManagementAuditNavigation`** (`core/audit`): System audit event listing (`AuditEventList`) and detailed view (`AuditEventDetail`).
- **`ManagementSecurityNavigation`** (`core/security`): Password policies, lockout rules, rate limits, and IP restriction settings (`SecuritySettings`).
- **`ManagementSettingsNavigation`** (`core/settings`): System-wide global settings and version requirements (`GlobalSettings`).


