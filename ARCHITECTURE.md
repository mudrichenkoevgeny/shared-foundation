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

## 2. Surface Separation: Open vs Management

This flowchart visualizes the architectural separation between unprivileged (Open) and protected (Management) API surfaces. It maps management routes to their corresponding, strongly-typed permission codes.

```mermaid
flowchart TB
    subgraph APISurfaces ["API Surfaces"]
        OpenAPI["Open API (Public / Unprivileged)"]
        ManagementAPI["Management API (Protected / Admin)"]
    end

    subgraph OpenRoutes ["Open Routes"]
        OpenGlobalSettingsRoutes["OpenGlobalSettingsRoutes"]
        OpenSecuritySettingsRoutes["OpenSecuritySettingsRoutes"]
        OpenUserRoutes["OpenUserRoutes"]
        OpenSessionRoutes["OpenSessionRoutes"]
        OpenUnlockRoutes["OpenUnlockRoutes"]
        OpenIdentifierRoutes["OpenIdentifierRoutes"]
        OpenAuthSettingsRoutes["OpenAuthSettingsRoutes"]
    end

    subgraph ManagementRoutes ["Management Routes"]
        ManagementGlobalSettingsRoutes["ManagementGlobalSettingsRoutes"]
        ManagementSecuritySettingsRoutes["ManagementSecuritySettingsRoutes"]
        ManagementAuditRoutes["ManagementAuditRoutes"]
        ManagementUserRoutes["ManagementUserRoutes"]
        ManagementSessionRoutes["ManagementSessionRoutes"]
        SelfManagementRoutes["SelfManagement Routes"]
    end

    subgraph PermissionCodes ["Permission Codes"]
        SettingsPermissionCode["SettingsPermissionCode"]
        SecurityPermissionCode["SecurityPermissionCode"]
        AuditPermissionCode["AuditPermissionCode"]
        PermissionCode["PermissionCode (Base Inline Class)"]
    end

    OpenAPI --> OpenRoutes
    ManagementAPI --> ManagementRoutes

    OpenRoutes -.-> OpenGlobalSettingsRoutes
    OpenRoutes -.-> OpenSecuritySettingsRoutes
    OpenRoutes -.-> OpenUserRoutes
    OpenRoutes -.-> OpenSessionRoutes
    OpenRoutes -.-> OpenUnlockRoutes
    OpenRoutes -.-> OpenIdentifierRoutes
    OpenRoutes -.-> OpenAuthSettingsRoutes

    ManagementRoutes -.-> ManagementGlobalSettingsRoutes
    ManagementRoutes -.-> ManagementSecuritySettingsRoutes
    ManagementRoutes -.-> ManagementAuditRoutes
    ManagementRoutes -.-> ManagementUserRoutes
    ManagementRoutes -.-> ManagementSessionRoutes
    ManagementRoutes -.-> SelfManagementRoutes

    ManagementGlobalSettingsRoutes -->|Enforced By| SettingsPermissionCode
    ManagementSecuritySettingsRoutes -->|Enforced By| SecurityPermissionCode
    ManagementAuditRoutes -->|Enforced By| AuditPermissionCode
    
    SettingsPermissionCode -->|Wraps| PermissionCode
    SecurityPermissionCode -->|Wraps| PermissionCode
    AuditPermissionCode -->|Wraps| PermissionCode
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
