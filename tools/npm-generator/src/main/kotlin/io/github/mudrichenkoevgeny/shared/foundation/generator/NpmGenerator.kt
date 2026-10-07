package io.github.mudrichenkoevgeny.shared.foundation.generator

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.contract.AuditEventFields
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.audit.resource.CommonAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventId
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditSortValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.permissions.AuditPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.contract.AuditApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventMetadataPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.route.management.ManagementAuditRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceId
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.CommonSortValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.model.ApiErrorResponse
import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming.CommonErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming.CommonErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonHttpHeaders
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonWebSocketCloseReasons
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonWebSocketEventTypes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.WebSocketContract
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.websocket.SocketFrame
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.websocket.WebSocketInitializePayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.audit.action.SecurityAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.audit.metadata.SecurityAuditMetadataDeniedReasonValues
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.audit.resource.SecurityAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.crypt.DecryptedString
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.crypt.EncryptedString
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.iprestriction.IpRestrictionPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.otpconfirmation.OtpConfirmation
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordhash.PasswordHash
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.ManagementPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.OpenPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicyFailReason
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings.ManagementSecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings.OpenSecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.totprecoverycodes.TotpRecoveryCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.totpsetup.TotpSetup
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.verifytotp.VerifyTotp
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.permission.SecurityPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityWebSocketEventTypes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.accountlockout.AccountLockoutPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.iprestriction.IpRestrictionPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation.OtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.passwordpolicy.ManagementPasswordPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.passwordpolicy.OpenPasswordPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.ManagementSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.OpenSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.totprecoverycodes.TotpRecoveryCodesPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.totpsetup.TotpSetupPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.verifytotp.VerifyTotpPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.management.security.settings.ManagementSecuritySettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.open.security.settings.OpenSecuritySettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.audit.action.SettingsAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.audit.resource.SettingsAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings.ManagementGlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings.OpenGlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.permission.SettingsPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.contract.GlobalSettingsApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.contract.SettingsWebSocketEventTypes
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.ManagementGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.OpenGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.management.globalsettings.ManagementGlobalSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.open.globalsettings.OpenGlobalSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataDeniedReasonValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.authoritylevel.UserRoleDefaultAuthorityLevel
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.data.AuthData
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.AvailableAuthProviders
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.ManagementAuthSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.OpenAuthSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.ExternalAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.configuration.ManagementUserConfiguration
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.configuration.OpenUserConfiguration
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.emailrestriction.EmailRestrictionPolicy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifier
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.DeletedSessions
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSession
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.AccessToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.RefreshToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.RefreshTokenHash
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.SessionToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserDetails
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserPublic
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.AuthSettingsPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.IdentifierPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.SessionPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.UserPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming.UserErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming.UserErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserAuthSpec
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserWebSocketCloseReasons
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserWebSocketEventTypes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.data.AuthDataPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.AvailableAuthProvidersPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.ManagementAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.OpenAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration.ManagementUserConfigurationPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration.OpenUserConfigurationPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.emailrestriction.EmailRestrictionPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.DeletedSessionsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.token.RefreshTokenPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.token.SessionTokenPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserDetailsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserPublicPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.create.CreateByEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByExternalAuthProviderRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.password.ResetPasswordRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.password.SendResetPasswordConfirmationRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.register.RegisterByEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock.UnlockByEmailConfirmationRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock.UnlockByExternalAuthProviderRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock.UnlockByPhoneConfirmationRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.password.EmailPasswordChangeRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers.AddUserIdentifierEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers.AddUserIdentifierExternalAuthProviderRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers.AddUserIdentifierPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.user.UpdateUserRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.login.SelfManagementLoginRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.refreshtoken.SelfManagementRefreshTokenRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.resetpassword.SelfManagementResetPasswordRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.settings.ManagementAuthSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.unlock.SelfManagementUnlockRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.configuration.ManagementUserConfigurationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.identifier.ManagementIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.identifier.SelfManagementIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.session.ManagementSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.session.SelfManagementSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.user.ManagementUserRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.user.SelfManagementUserRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.user.security.ManagementUserSecurityRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.user.security.SelfManagementUserSecurityRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.login.OpenLoginRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.refreshtoken.OpenRefreshTokenRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.register.OpenRegisterRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.resetpassword.OpenResetPasswordRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.settings.OpenAuthSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.unlock.OpenUnlockRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.configuration.OpenUserConfigurationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.identifier.OpenIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.session.OpenSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.OpenUserRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.security.OpenUserSecurityRoutes
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonElement
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.primaryConstructor

data class EnumInfo(
    val name: String,
    val schemaName: String,
    val entries: List<Pair<String, String>>
)

fun main() {
    try {
        val currentDir = File(System.getProperty("user.dir"))
        val rootDir = if (currentDir.name == "npm-generator") currentDir.parentFile.parentFile else currentDir
        val outputDir = File(rootDir, "build/npm-package")
        outputDir.mkdirs()

        val packageJson = """
        {
          "name": "@mudrichenkoevgeny/shared-foundation",
          "version": "0.0.54",
          "description": "Shared Foundation - TypeScript & Zod contracts and routes",
          "main": "index.js",
          "module": "index.mjs",
          "types": "index.d.ts",
          "exports": {
            ".": {
              "types": "./index.d.ts",
              "import": "./index.mjs",
              "require": "./index.js"
            }
          },
          "dependencies": {
            "zod": "^3.23.0"
          }
        }
        """.trimIndent()

        File(outputDir, "package.json").writeText(packageJson)

        val npmReadme = """
        # @mudrichenkoevgeny/shared-foundation

        TypeScript & Zod contracts, API routes, error codes, permission models, runtime enums, and branded types generated directly from the [shared-foundation](https://github.com/mudrichenkoevgeny/shared-foundation) Kotlin Multiplatform (KMP) repository.

        Provides a single source of truth (SSOT) between Kotlin Backend/KMP services and TypeScript/React web applications.

        ## Installation

        ```bash
        # pnpm
        pnpm add @mudrichenkoevgeny/shared-foundation zod

        # npm
        npm install @mudrichenkoevgeny/shared-foundation zod

        # yarn
        yarn add @mudrichenkoevgeny/shared-foundation zod
        ```

        ## Features

        - **Zod Schemas**: Auto-generated `z.ZodType` validation schemas for all Request payloads and Response DTOs (`loginByEmailRequestSchema`, `apiErrorResponseSchema`, `userDetailsPayloadSchema`).
        - **Runtime Enums**: TS types & runtime JS objects for all enums and string unions (`UserAuthProvider`, `UserRole`, `UserAccountStatus`, `AccountLockoutType`, `ClientType`, `SortOrder`, `UserSortValues`, etc.).
        - **Route Constants**: Strongly typed API endpoint routes (`ManagementSecuritySettingsRoutes`, `OpenLoginRoutes`, `SelfManagementUserRoutes`).
        - **Error & Permission Codes**: Literal string definitions for all application error codes (`SecurityErrorCodes`, `CommonErrorCodes`) and permission strings (`SecurityPermissionCode`, `UserPermissionCode`).
        - **Branded Types & Helpers**: Type-safe domain identifiers (`UserId`, `PermissionCode`, `UserSessionId`, `UserIdentifierId`, `AuditEventId`, `ClientDeviceId`) with converter helpers (`toUserIdOrThrow`, `toPermissionCodeOrNull`).
        - **Domain Models & Mappers**: Fully typed TS domain models (`UserSession`, `UserDetails`, `ClientDeviceInfo`) and their runtime mappers from/to API payloads (`toUserSession`, `toUserSessionPayload`).
        - **Composite Parsers**: Helper classes for runtime parsing of polymorphic values (`CompositeAuditActionTypeParser`, `CompositeAuditMetadataKeyParser`, `CompositeAuditResourceTypeParser`).

        ## Quick Usage Example

        ```typescript
        import { 
          ManagementSecuritySettingsRoutes, 
          SecurityErrorCodes, 
          apiErrorResponseSchema, 
          toUserIdOrThrow 
        } from '@mudrichenkoevgeny/shared-foundation';

        // 1. Using Route Constants
        const url = ManagementSecuritySettingsRoutes.GET_MANAGEMENT_SECURITY_SETTINGS;

        // 2. Branded Types
        const userId = toUserIdOrThrow('123e4567-e89b-12d3-a456-426614174000');

        // 3. Validating API Responses with Zod
        async function fetchUserSettings() {
          const response = await fetch(url);
          const json = await response.json();

          if (!response.ok) {
            const error = apiErrorResponseSchema.parse(json);
            if (error.code === SecurityErrorCodes.TOTP_NOT_ENABLED) {
              console.error('TOTP configuration required');
            }
            return;
          }
        }
        ```

        ## Source Code & Repository

        The source code for this library and generator is maintained in the main GitHub repository:
        **[GitHub Repository: mudrichenkoevgeny/shared-foundation](https://github.com/mudrichenkoevgeny/shared-foundation)**

        ## License

        Apache License 2.0. See [LICENSE](https://github.com/mudrichenkoevgeny/shared-foundation/blob/main/LICENSE) for details.
        """.trimIndent()

        File(outputDir, "README.md").writeText(npmReadme)

        val brandedCode = generateBrandedTypes()
        val compositeParsersJsCode = generateCompositeParsersJs()
        val routeCode = generateRouteObjects()
        val contractCode = generateContractObjects()
        val errorCodeCode = generateErrorCodeObjects()
        val permissionCodeCode = generatePermissionCodeObjects()
        val enumCode = generateEnumsJs()
        val dtoCode = generateDtoSchemasAndTypes()
        val domainModelsAndMappersCode = generateDomainModelsAndMappersJs()

        val fullJsContent = """
        import { z } from 'zod';

        $brandedCode

        $compositeParsersJsCode

        $routeCode

        $contractCode

        $errorCodeCode

        $permissionCodeCode

        $domainModelsAndMappersCode

        $enumCode

        $dtoCode
        """.trimIndent()

        File(outputDir, "index.js").writeText(fullJsContent)
        File(outputDir, "index.mjs").writeText(fullJsContent)

        val dtsContent = generateDeclarationFile()
        File(outputDir, "index.d.ts").writeText(dtsContent)

        val localWebSdkDir = File(rootDir.parentFile, "web-platform-sdk/packages/core-common/src/generated")
        if (localWebSdkDir.exists()) {
            outputDir.copyRecursively(localWebSdkDir, overwrite = true)
            println("Synced generated files to local web-platform-sdk: ${localWebSdkDir.absolutePath}")
        }

        println("Successfully generated NPM package in ${outputDir.absolutePath}")
    } catch (e: Throwable) {
        val sw = StringWriter()
        e.printStackTrace(PrintWriter(sw))
        File("build/generator-error.log").apply {
            parentFile.mkdirs()
            writeText(sw.toString())
        }
        throw e
    }
}

private fun generateBrandedTypes(): String {
    val types = listOf(
        "PermissionCode",
        "UserId",
        "UserIdentifierId",
        "UserSessionId",
        "AuditEventId",
        "ClientDeviceId",
        "DecryptedString",
        "EncryptedString",
        "PasswordHash",
        "AccessToken",
        "RefreshToken",
        "RefreshTokenHash"
    )

    return types.joinToString("\n\n") { name ->
        val schemaName = name.decapitalizeFirstLetter() + "Schema"
        """
        export function to${name}OrNull(value) {
          if (!value || typeof value !== 'string' || value.trim() === '') {
            return null;
          }
          return value;
        }

        export function to${name}OrThrow(value) {
          const result = to${name}OrNull(value);
          if (!result) {
            throw new Error('${name} value must not be blank.');
          }
          return result;
        }

        export const $schemaName = z.string().transform((val, ctx) => {
          const result = to${name}OrNull(val);
          if (!result) {
            ctx.addIssue({ code: z.ZodIssueCode.custom, message: 'Invalid ${name}' });
            return z.NEVER;
          }
          return result;
        });
        """.trimIndent()
    }
}

private fun generateCompositeParsersJs(): String {
    return """
    export class CompositeAuditActionTypeParser {
      constructor(auditActionTypes) {
        this.auditActionTypes = auditActionTypes;
      }

      fromValueOrThrow(value) {
        for (const parse of this.auditActionTypes) {
          if (typeof parse === 'function') {
            const result = parse(value);
            if (result !== null && result !== undefined) return result;
          } else if (parse && typeof parse.parseOrNull === 'function') {
            const result = parse.parseOrNull(value);
            if (result !== null && result !== undefined) return result;
          } else if (parse && typeof parse === 'object') {
            // Support passing the enum object directly (e.g. UserAuditActionType)
            const matchedValue = Object.values(parse).find(val => val === value);
            if (matchedValue !== undefined) return matchedValue;
          }
        }
        throw new Error("Unknown value of AuditActionType: '" + value + "'");
      }
    }

    export class CompositeAuditMetadataKeyParser {
      constructor(metadataKeys) {
        this.metadataKeys = metadataKeys;
      }

      fromValueOrThrow(value) {
        for (const parse of this.metadataKeys) {
          if (typeof parse === 'function') {
            const result = parse(value);
            if (result !== null && result !== undefined) return result;
          } else if (parse && typeof parse.parseOrNull === 'function') {
            const result = parse.parseOrNull(value);
            if (result !== null && result !== undefined) return result;
          } else if (parse && typeof parse === 'object') {
            const matchedValue = Object.values(parse).find(val => val === value);
            if (matchedValue !== undefined) return matchedValue;
          }
        }
        throw new Error("Unknown value of AuditMetadataKey: '" + value + "'");
      }
    }

    export class CompositeAuditResourceTypeParser {
      constructor(auditResourceTypes) {
        this.auditResourceTypes = auditResourceTypes;
      }

      fromValueOrThrow(value) {
        for (const parse of this.auditResourceTypes) {
          if (typeof parse === 'function') {
            const result = parse(value);
            if (result !== null && result !== undefined) return result;
          } else if (parse && typeof parse.parseOrNull === 'function') {
            const result = parse.parseOrNull(value);
            if (result !== null && result !== undefined) return result;
          } else if (parse && typeof parse === 'object') {
            const matchedValue = Object.values(parse).find(val => val === value);
            if (matchedValue !== undefined) return matchedValue;
          }
        }
        throw new Error("Unknown value of AuditResourceType: '" + value + "'");
      }
    }
    """.trimIndent()
}

private fun generateCompositeParsersDts(): String {
    return """
    export type AuditActionType = SecurityAuditActionType | SettingsAuditActionType | UserAuditActionType | string;
    export type AuditMetadataKey = CommonAuditMetadataKey | UserAuditMetadataKey | string;
    export type AuditResourceType = CommonAuditResourceType | SecurityAuditResourceType | SettingsAuditResourceType | UserAuditResourceType | string;

    export interface AuditActionTypeParser {
      parseOrNull(value: string): AuditActionType | null;
    }

    export interface AuditMetadataKeyParser {
      parseOrNull(value: string): AuditMetadataKey | null;
    }

    export interface AuditResourceTypeParser {
      parseOrNull(value: string): AuditResourceType | null;
    }

    export declare class CompositeAuditActionTypeParser {
      constructor(auditActionTypes: Array<((value: string) => AuditActionType | null) | AuditActionTypeParser | Record<string, string>>);
      fromValueOrThrow(value: string): AuditActionType;
    }

    export declare class CompositeAuditMetadataKeyParser {
      constructor(metadataKeys: Array<((value: string) => AuditMetadataKey | null) | AuditMetadataKeyParser | Record<string, string>>);
      fromValueOrThrow(value: string): AuditMetadataKey;
    }

    export declare class CompositeAuditResourceTypeParser {
      constructor(auditResourceTypes: Array<((value: string) => AuditResourceType | null) | AuditResourceTypeParser | Record<string, string>>);
      fromValueOrThrow(value: string): AuditResourceType;
    }
    """.trimIndent()
}

private fun extractObjectStringProperties(kClass: KClass<*>): List<Pair<String, String>> {
    val instance = kClass.objectInstance
    val staticFields = kClass.java.declaredFields
        .filter { java.lang.reflect.Modifier.isStatic(it.modifiers) && it.type == String::class.java }
        .mapNotNull { field ->
            field.isAccessible = true
            val valName = field.name
            val valValue = field.get(null) as? String
            if (valValue != null) valName to valValue else null
        }

    if (staticFields.isNotEmpty()) return staticFields

    if (instance != null) {
        val instanceFields = kClass.java.declaredFields
            .filter { it.type == String::class.java }
            .mapNotNull { field ->
                field.isAccessible = true
                val valName = field.name
                val valValue = field.get(instance) as? String
                if (valValue != null) valName to valValue else null
            }
        if (instanceFields.isNotEmpty()) return instanceFields
    }

    return emptyList()
}

private val routeClasses = listOf(
    ManagementRoutes::class,
    ManagementAuditRoutes::class,
    ManagementSecuritySettingsRoutes::class,
    OpenSecuritySettingsRoutes::class,
    ManagementGlobalSettingsRoutes::class,
    OpenGlobalSettingsRoutes::class,
    OpenLoginRoutes::class,
    SelfManagementLoginRoutes::class,
    OpenRefreshTokenRoutes::class,
    SelfManagementRefreshTokenRoutes::class,
    OpenRegisterRoutes::class,
    OpenResetPasswordRoutes::class,
    SelfManagementResetPasswordRoutes::class,
    OpenUnlockRoutes::class,
    SelfManagementUnlockRoutes::class,
    OpenUserSecurityRoutes::class,
    SelfManagementUserSecurityRoutes::class,
    ManagementUserSecurityRoutes::class,
    OpenIdentifierRoutes::class,
    SelfManagementIdentifierRoutes::class,
    ManagementIdentifierRoutes::class,
    OpenSessionRoutes::class,
    SelfManagementSessionRoutes::class,
    ManagementSessionRoutes::class,
    OpenUserRoutes::class,
    SelfManagementUserRoutes::class,
    ManagementUserRoutes::class,
    ManagementAuthSettingsRoutes::class,
    OpenAuthSettingsRoutes::class,
    OpenUserConfigurationRoutes::class,
    ManagementUserConfigurationRoutes::class,
    WebSocketContract::class
)

private val contractClasses = listOf(
    CommonApiFields::class,
    SecurityApiFields::class,
    GlobalSettingsApiFields::class,
    UserApiFields::class,
    AuditEventFields::class,
    CommonHttpHeaders::class,
    CommonWebSocketCloseReasons::class,
    CommonWebSocketEventTypes::class,
    SecurityWebSocketEventTypes::class,
    SettingsWebSocketEventTypes::class,
    UserWebSocketCloseReasons::class,
    UserWebSocketEventTypes::class,
    UserApiPaths::class,
    UserApiQueryParams::class,
    UserAuthSpec::class,
    AuditApiPaths::class,
    UserRoleDefaultAuthorityLevel::class,
    SecurityAuditMetadataDeniedReasonValues::class,
    UserAuditMetadataDeniedReasonValues::class
)

private val errorClasses = listOf(
    CommonErrorCodes::class,
    CommonErrorArgs::class,
    SecurityErrorCodes::class,
    SecurityErrorArgs::class,
    UserErrorCodes::class,
    UserErrorArgs::class
)

private val permClasses = listOf(
    AuditPermissionCode::class,
    SecurityPermissionCode::class,
    SettingsPermissionCode::class,
    AuthSettingsPermissionCode::class,
    IdentifierPermissionCode::class,
    SessionPermissionCode::class,
    UserPermissionCode::class
)

private val enumClasses = listOf(
    ClientType::class,
    SortOrder::class,
    AccountLockoutType::class,
    PasswordPolicyFailReason::class,
    AuditActorType::class,
    AuditValueSensitivity::class,
    AuditStatus::class,
    CommonAuditResourceType::class,
    SecurityAuditResourceType::class,
    SettingsAuditResourceType::class,
    UserAuditResourceType::class,
    SecurityAuditActionType::class,
    SettingsAuditActionType::class,
    UserAuditActionType::class,
    CommonAuditMetadataKey::class,
    UserAuditMetadataKey::class,
    UserAccountStatus::class,
    UserRole::class,
    UserAuthProvider::class,
    ExternalAuthProvider::class,
    UserSortValues.UserSortBy::class,
    UserSortValues.UserIdentifierSortBy::class,
    UserSortValues.UserSessionSortBy::class,
    AuditSortValues.AuditEventSortBy::class,
    CommonSortValues.TimestampSortBy::class
)

private val extraEnumInfos = listOf(
    EnumInfo(
        name = "UnlockMethod",
        schemaName = "unlockMethodSchema",
        entries = listOf(
            "EMAIL" to "EMAIL",
            "PHONE" to "PHONE"
        )
    ),
    EnumInfo(
        name = "ConfirmationType",
        schemaName = "confirmationTypeSchema",
        entries = listOf(
            "REGISTRATION_EMAIL" to "REGISTRATION_EMAIL",
            "ADD_EMAIL" to "ADD_EMAIL",
            "PASSWORD_RESET_EMAIL" to "PASSWORD_RESET_EMAIL",
            "LOGIN_PHONE" to "LOGIN_PHONE",
            "ADD_PHONE" to "ADD_PHONE",
            "UNLOCK_EMAIL" to "UNLOCK_EMAIL",
            "UNLOCK_PHONE" to "UNLOCK_PHONE"
        )
    ),
    EnumInfo(
        name = "AppType",
        schemaName = "appTypeSchema",
        entries = listOf(
            "CLIENT" to "CLIENT",
            "MANAGEMENT" to "MANAGEMENT"
        )
    )
)

private fun extractReferencedClasses(type: KType): Set<KClass<*>> {
    val result = mutableSetOf<KClass<*>>()
    val classifier = type.classifier as? KClass<*>
    if (classifier != null) {
        result.add(classifier)
    }
    for (arg in type.arguments) {
        val argType = arg.type
        if (argType != null) {
            result.addAll(extractReferencedClasses(argType))
        }
    }
    return result
}

private fun getDtoDependencies(kClass: KClass<*>, allClasses: Set<KClass<*>>): Set<KClass<*>> {
    val dependencies = mutableSetOf<KClass<*>>()
    val primaryConstructor = kClass.primaryConstructor ?: return dependencies
    for (param in primaryConstructor.parameters) {
        val refClasses = extractReferencedClasses(param.type)
        for (refClass in refClasses) {
            if (refClass != kClass && refClass in allClasses) {
                dependencies.add(refClass)
            }
        }
    }
    return dependencies
}

private fun sortDtosTopologically(classes: List<KClass<*>>): List<KClass<*>> {
    val allClasses = classes.toSet()
    val depMap = classes.associateWith { kClass ->
        getDtoDependencies(kClass, allClasses)
    }

    val sorted = mutableListOf<KClass<*>>()
    val visited = mutableSetOf<KClass<*>>()
    val visiting = mutableSetOf<KClass<*>>()

    fun visit(kClass: KClass<*>) {
        if (kClass in visited) return
        if (kClass in visiting) return
        visiting.add(kClass)
        val deps = depMap[kClass] ?: emptySet()
        for (dep in deps) {
            visit(dep)
        }
        visiting.remove(kClass)
        visited.add(kClass)
        sorted.add(kClass)
    }

    for (kClass in classes) {
        visit(kClass)
    }

    return sorted
}

private fun getDomainPairDependencies(
    pair: Pair<KClass<*>, KClass<*>>,
    allPairs: List<Pair<KClass<*>, KClass<*>>>
): Set<Pair<KClass<*>, KClass<*>>> {
    val dependencies = mutableSetOf<Pair<KClass<*>, KClass<*>>>()
    val (domainClass, payloadClass) = pair
    val domainRefClasses = (domainClass.primaryConstructor?.parameters ?: emptyList())
        .flatMap { extractReferencedClasses(it.type) }
        .toSet()
    val payloadRefClasses = (payloadClass.primaryConstructor?.parameters ?: emptyList())
        .flatMap { extractReferencedClasses(it.type) }
        .toSet()

    for (otherPair in allPairs) {
        if (otherPair == pair) continue
        val (otherDomain, otherPayload) = otherPair
        if (otherDomain in domainRefClasses || otherPayload in payloadRefClasses || otherDomain in payloadRefClasses || otherPayload in domainRefClasses) {
            dependencies.add(otherPair)
        }
    }
    return dependencies
}

private fun sortDomainToDtoTopologically(
    pairs: List<Pair<KClass<*>, KClass<*>>>
): List<Pair<KClass<*>, KClass<*>>> {
    val depMap = pairs.associateWith { pair ->
        getDomainPairDependencies(pair, pairs)
    }

    val sorted = mutableListOf<Pair<KClass<*>, KClass<*>>>()
    val visited = mutableSetOf<Pair<KClass<*>, KClass<*>>>()
    val visiting = mutableSetOf<Pair<KClass<*>, KClass<*>>>()

    fun visit(pair: Pair<KClass<*>, KClass<*>>) {
        if (pair in visited) return
        if (pair in visiting) return
        visiting.add(pair)
        val deps = depMap[pair] ?: emptySet()
        for (dep in deps) {
            visit(dep)
        }
        visiting.remove(pair)
        visited.add(pair)
        sorted.add(pair)
    }

    for (pair in pairs) {
        visit(pair)
    }

    return sorted
}

private val rawDtoClasses = listOf(
    ApiErrorResponse::class,
    ClientDeviceInfoPayload::class,
    AccountLockoutPolicyPayload::class,
    IpRestrictionPolicyPayload::class,
    OtpConfirmationPayload::class,
    ManagementPasswordPolicyPayload::class,
    OpenPasswordPolicyPayload::class,
    ManagementGlobalSettingsPayload::class,
    OpenGlobalSettingsPayload::class,
    AuditEventMetadataPayload::class,
    AvailableAuthProvidersPayload::class,
    EmailRestrictionPolicyPayload::class,
    SessionTokenPayload::class,
    UserDetailsPayload::class,
    UserPublicPayload::class,
    UserIdentifierPayload::class,
    DeletedSessionsPayload::class,
    RefreshTokenPayload::class,
    TotpRecoveryCodesPayload::class,
    TotpSetupPayload::class,
    VerifyTotpPayload::class,
    SocketFrame::class,
    WebSocketInitializePayload::class,
    ClientInfoPayload::class,
    UserSessionPayload::class,
    AuditEventPayload::class,
    AuthDataPayload::class,
    ManagementAuthSettingsPayload::class,
    OpenAuthSettingsPayload::class,
    ManagementSecuritySettingsPayload::class,
    OpenSecuritySettingsPayload::class,
    ManagementUserConfigurationPayload::class,
    OpenUserConfigurationPayload::class,
    CreateByEmailRequest::class,
    LoginByEmailRequest::class,
    LoginByExternalAuthProviderRequest::class,
    LoginByPhoneRequest::class,
    ResetPasswordRequest::class,
    SendResetPasswordConfirmationRequest::class,
    RegisterByEmailRequest::class,
    UnlockByEmailConfirmationRequest::class,
    UnlockByExternalAuthProviderRequest::class,
    UnlockByPhoneConfirmationRequest::class,
    SendConfirmationToEmailRequest::class,
    SendConfirmationToPhoneRequest::class,
    EmailPasswordChangeRequest::class,
    AddUserIdentifierEmailRequest::class,
    AddUserIdentifierExternalAuthProviderRequest::class,
    AddUserIdentifierPhoneRequest::class,
    UpdateUserRequest::class
)

private val dtoClasses = sortDtosTopologically(rawDtoClasses)

private fun getEnumInfo(kClass: KClass<*>): EnumInfo {
    val name = kClass.simpleName ?: ""
    val schemaName = name.decapitalizeFirstLetter() + "Schema"
    val entries = mutableListOf<Pair<String, String>>()

    val enumConstants = kClass.java.enumConstants
    if (enumConstants != null && enumConstants.isNotEmpty()) {
        for (enumConst in enumConstants) {
            val key = (enumConst as Enum<*>).name
            val field = try { kClass.java.getField(key) } catch (_: Exception) { null }
            val serialNameAnnot = field?.getAnnotation(SerialName::class.java)?.value
            val serialNameProp = try {
                enumConst.javaClass.getMethod("getSerialName").invoke(enumConst) as? String
            } catch (_: Exception) {
                null
            }
            val wireValue = serialNameAnnot ?: serialNameProp ?: key.lowercase()
            entries.add(key to wireValue)
        }
    } else if (kClass == ExternalAuthProvider::class) {
        for (ext in ExternalAuthProvider.all) {
            entries.add(ext.userAuthProvider.name to ext.userAuthProvider.serialName)
        }
    }

    if (kClass == UserRole::class) {
        if (entries.none { it.first == "USER" }) {
            entries.add("USER" to "user")
        }
        if (entries.none { it.first == "CLIENT_USER" }) {
            entries.add("CLIENT_USER" to "user")
        }
        if (entries.none { it.first == "MANAGEMENT_USER" }) {
            entries.add("MANAGEMENT_USER" to "admin")
        }
    }

    if (kClass == AccountLockoutType::class) {
        if (entries.none { it.first == "PERMANENT" }) {
            entries.add("PERMANENT" to "indefinite")
        }
    }

    return EnumInfo(name, schemaName, entries)
}

private fun getAllEnumInfos(): List<EnumInfo> {
    return enumClasses.map { getEnumInfo(it) } + extraEnumInfos
}

private fun generateRouteObjects(): String {
    return routeClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val props = extractObjectStringProperties(kClass)
        val fields = props.map { (valName, valValue) -> "  $valName: \"$valValue\"" }

        """
        export const $name = {
        ${fields.joinToString(",\n")}
        };
        """.trimIndent()
    }
}

private fun generateContractObjects(): String {
    val standardContracts = contractClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val props = extractObjectStringProperties(kClass)
        val fields = props.map { (valName, valValue) -> "  $valName: \"$valValue\"" }

        """
        export const $name = {
        ${fields.joinToString(",\n")}
        };
        """.trimIndent()
    }

    val listingParamNamesCode = """
    export const ListingParamNames = {
      PAGE_NUMBER: "${ListingParamNames.Pagination.PAGE_NUMBER}",
      PAGE_SIZE: "${ListingParamNames.Pagination.PAGE_SIZE}",
      SORT_BY: "${ListingParamNames.Sort.SORT_BY}",
      SORT_ORDER: "${ListingParamNames.Sort.SORT_ORDER}",
      Pagination: {
        PAGE_NUMBER: "${ListingParamNames.Pagination.PAGE_NUMBER}",
        PAGE_SIZE: "${ListingParamNames.Pagination.PAGE_SIZE}"
      },
      Sort: {
        SORT_BY: "${ListingParamNames.Sort.SORT_BY}",
        SORT_ORDER: "${ListingParamNames.Sort.SORT_ORDER}"
      }
    };

    export const AuditFilterValues = {
      AuditEventFilterValues: {
        ACTOR_ID: "${AuditFilterValues.AuditEventFilterValues.ACTOR_ID}",
        ACTOR_TYPE: "${AuditFilterValues.AuditEventFilterValues.ACTOR_TYPE}",
        ACTOR_USER_ROLE: "${AuditFilterValues.AuditEventFilterValues.ACTOR_USER_ROLE}",
        ACTION: "${AuditFilterValues.AuditEventFilterValues.ACTION}",
        RESOURCE: "${AuditFilterValues.AuditEventFilterValues.RESOURCE}",
        RESOURCE_ID: "${AuditFilterValues.AuditEventFilterValues.RESOURCE_ID}",
        STATUS: "${AuditFilterValues.AuditEventFilterValues.STATUS}",
        MESSAGE: "${AuditFilterValues.AuditEventFilterValues.MESSAGE}"
      }
    };

    export const UserFilterValues = {
      UserFilterValues: {
        ROLE: "${UserFilterValues.UserFilterValues.ROLE}",
        ACCOUNT_STATUS: "${UserFilterValues.UserFilterValues.ACCOUNT_STATUS}",
        ACCOUNT_STATUS_ON_RESTORE: "${UserFilterValues.UserFilterValues.ACCOUNT_STATUS_ON_RESTORE}",
        ACCOUNT_LOCKOUT_TYPE: "${UserFilterValues.UserFilterValues.ACCOUNT_LOCKOUT_TYPE}",
        AUTHORITY_LEVEL_FROM: "${UserFilterValues.UserFilterValues.AUTHORITY_LEVEL_FROM}",
        AUTHORITY_LEVEL_TO: "${UserFilterValues.UserFilterValues.AUTHORITY_LEVEL_TO}",
        IS_TOTP_ENABLED: "${UserFilterValues.UserFilterValues.IS_TOTP_ENABLED}",
        PERMISSION_CODES: "${UserFilterValues.UserFilterValues.PERMISSION_CODES}"
      },
      UserIdentifierFilterValues: {
        USER_ID: "${UserFilterValues.UserIdentifierFilterValues.USER_ID}",
        USER_AUTH_PROVIDER: "${UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER}",
        IDENTIFIER: "${UserFilterValues.UserIdentifierFilterValues.IDENTIFIER}"
      },
      UserSessionFilterValues: {
        USER_ID: "${UserFilterValues.UserSessionFilterValues.USER_ID}",
        USER_ROLE: "${UserFilterValues.UserSessionFilterValues.USER_ROLE}",
        IDENTIFIER_ID: "${UserFilterValues.UserSessionFilterValues.IDENTIFIER_ID}",
        USER_AUTH_PROVIDER: "${UserFilterValues.UserSessionFilterValues.USER_AUTH_PROVIDER}",
        CLIENT_TYPE: "${UserFilterValues.UserSessionFilterValues.CLIENT_TYPE}",
        USER_AGENT: "${UserFilterValues.UserSessionFilterValues.USER_AGENT}",
        IP_ADDRESS: "${UserFilterValues.UserSessionFilterValues.IP_ADDRESS}",
        LANGUAGE: "${UserFilterValues.UserSessionFilterValues.LANGUAGE}",
        DEVICE_ID: "${UserFilterValues.UserSessionFilterValues.DEVICE_ID}",
        DEVICE_NAME: "${UserFilterValues.UserSessionFilterValues.DEVICE_NAME}",
        APP_VERSION: "${UserFilterValues.UserSessionFilterValues.APP_VERSION}",
        OPERATION_SYSTEM_VERSION: "${UserFilterValues.UserSessionFilterValues.OPERATION_SYSTEM_VERSION}",
        IDENTIFIER: "${UserFilterValues.UserSessionFilterValues.IDENTIFIER}"
      }
    };
    """.trimIndent()

    return "$standardContracts\n\n$listingParamNamesCode"
}

private fun generateErrorCodeObjects(): String {
    return errorClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val props = extractObjectStringProperties(kClass)
        val fields = props.map { (valName, valValue) -> "  $valName: \"$valValue\"" }

        """
        export const $name = {
        ${fields.joinToString(",\n")}
        };
        """.trimIndent()
    }
}

private fun extractPermissionCodeProperties(kClass: KClass<*>): List<Pair<String, String>> {
    val instance = kClass.objectInstance ?: return emptyList()

    val fields = kClass.java.declaredFields
        .filter { PermissionCode::class.java.isAssignableFrom(it.type) }
        .mapNotNull { field ->
            field.isAccessible = true
            val permObj = field.get(instance) as? PermissionCode ?: field.get(null) as? PermissionCode ?: return@mapNotNull null
            field.name to permObj.value
        }

    if (fields.isNotEmpty()) return fields

    val methods = kClass.java.declaredMethods
        .filter { PermissionCode::class.java.isAssignableFrom(it.returnType) && it.parameterCount == 0 }
        .mapNotNull { method ->
            method.isAccessible = true
            val name = method.name.removePrefix("get")
            val permObj = method.invoke(instance) as? PermissionCode ?: return@mapNotNull null
            name to permObj.value
        }

    return methods
}

private fun generatePermissionCodeObjects(): String {
    return permClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val props = extractPermissionCodeProperties(kClass)
        val fields = props.map { (valName, valValue) -> "  $valName: toPermissionCodeOrThrow(\"$valValue\")" }

        """
        export const $name = {
        ${fields.joinToString(",\n")}
        };
        """.trimIndent()
    }
}

private fun generateEnumsJs(): String {
    val enumJsBlocks = getAllEnumInfos().map { info ->
        val schemaValues = info.entries.map { it.second }.distinct().joinToString(", ") { "\"$it\"" }
        val objectFields = info.entries.joinToString(",\n") { (key, wire) -> "  $key: \"$wire\"" }

        """
        export const ${info.schemaName} = z.enum([$schemaValues]);
        export const ${info.name} = {
        $objectFields
        };
        """.trimIndent()
    }

    val extraJs = """
    export const confirmationKeySchema = z.object({
      type: confirmationTypeSchema,
      identifier: z.string()
    });

    export const UserSortValues = {
      UserSortBy,
      UserIdentifierSortBy,
      UserSessionSortBy
    };
    """.trimIndent()

    return (enumJsBlocks + listOf(extraJs)).joinToString("\n\n")
}

private fun generateDtoSchemasAndTypes(): String {
    val pagedResultCode = """
    export function pagedResultSchema(itemSchema) {
      return z.object({
        items: z.array(itemSchema),
        totalCount: z.number(),
        pageNumber: z.number(),
        pageSize: z.number(),
        totalPages: z.number()
      });
    }
    """.trimIndent()

    val generatedDtos = dtoClasses.joinToString("\n\n") { kClass ->
        generateSingleDtoSchema(kClass)
    }

    return "$pagedResultCode\n\n$generatedDtos"
}

private fun generateSingleDtoSchema(kClass: KClass<*>): String {
    val name = kClass.simpleName ?: ""
    val schemaName = name.decapitalizeFirstLetter() + "Schema"

    val primaryConstructor = kClass.primaryConstructor ?: return ""

    val fields = primaryConstructor.parameters.map { param ->
        val paramName = param.name ?: ""

        val serialNameAnnotation = param.annotations.filterIsInstance<SerialName>().firstOrNull()
            ?: kClass.declaredMemberProperties.firstOrNull { it.name == paramName }?.findAnnotation<SerialName>()

        val wireKey = serialNameAnnotation?.value ?: paramName

        val type = param.type
        val zodType = resolveZodType(paramName, type, kClass)

        "    $wireKey: $zodType"
    }

    return """
    export const $schemaName = z.object({
    ${fields.joinToString(",\n")}
    });
    """.trimIndent()
}

private fun resolveZodType(paramName: String, type: KType, parentClass: KClass<*>): String {
    val classifier = type.classifier as? KClass<*> ?: return "z.unknown()"
    val isMarkedNullable = type.isMarkedNullable

    val baseZod = when {
        classifier == UserAuthProvider::class -> "userAuthProviderSchema"
        classifier == ExternalAuthProvider::class -> "externalAuthProviderSchema"
        classifier == UserRole::class -> "userRoleSchema"
        classifier == UserAccountStatus::class -> "userAccountStatusSchema"
        classifier == AccountLockoutType::class -> "accountLockoutTypeSchema"
        classifier == AuditActorType::class -> "auditActorTypeSchema"
        classifier == AuditValueSensitivity::class -> "auditValueSensitivitySchema"
        classifier == AuditStatus::class -> "auditStatusSchema"
        classifier == CommonAuditMetadataKey::class -> "commonAuditMetadataKeySchema"
        classifier == UserAuditMetadataKey::class -> "userAuditMetadataKeySchema"
        classifier == ClientType::class -> "clientTypeSchema"
        classifier == SortOrder::class -> "sortOrderSchema"
        classifier == PasswordPolicyFailReason::class -> "passwordPolicyFailReasonSchema"
        classifier == UserSortValues.UserSortBy::class -> "userSortBySchema"
        classifier == UserSortValues.UserIdentifierSortBy::class -> "userIdentifierSortBySchema"
        classifier == UserSortValues.UserSessionSortBy::class -> "userSessionSortBySchema"
        classifier == AuditSortValues.AuditEventSortBy::class -> "auditEventSortBySchema"
        classifier == CommonSortValues.TimestampSortBy::class -> "timestampSortBySchema"
        classifier == JsonElement::class -> "z.unknown()"

        paramName == "clientType" -> "clientTypeSchema"
        paramName == "deviceId" || paramName == "clientDeviceId" -> "clientDeviceIdSchema"
        paramName == "role" || paramName == "userRole" || paramName == "actorUserRole" -> "userRoleSchema"
        paramName == "accountStatus" || paramName == "accountStatusOnRestore" -> "userAccountStatusSchema"
        paramName == "lockoutType" -> "accountLockoutTypeSchema"
        paramName == "actorType" -> "auditActorTypeSchema"
        paramName == "status" && parentClass == AuditEventPayload::class -> "auditStatusSchema"
        paramName == "identifierAuthProvider" || paramName == "userAuthProvider" -> "userAuthProviderSchema"
        paramName == "sensitivity" || paramName == "valueSensitivity" -> "auditValueSensitivitySchema"
        paramName == "permissionCodes" -> "z.array(permissionCodeSchema)"

        classifier == String::class -> {
            when (paramName) {
                "id" -> when (parentClass) {
                    UserSessionPayload::class -> "userSessionIdSchema"
                    AuditEventPayload::class -> "auditEventIdSchema"
                    UserIdentifierPayload::class -> "userIdentifierIdSchema"
                    else -> "userIdSchema"
                }
                "userId" -> "userIdSchema"
                "identifierId" -> "userIdentifierIdSchema"
                "sessionId" -> "userSessionIdSchema"
                "actorId" -> "userIdSchema"
                "permissionCode" -> "permissionCodeSchema"
                else -> "z.string()"
            }
        }
        classifier == Int::class || classifier == Long::class || classifier == Float::class || classifier == Double::class || classifier == Short::class || classifier == Byte::class -> "z.number()"
        classifier == Boolean::class -> "z.boolean()"
        classifier == PermissionCode::class -> "permissionCodeSchema"
        classifier == UserId::class -> "userIdSchema"
        classifier == UserIdentifierId::class -> "userIdentifierIdSchema"
        classifier == UserSessionId::class -> "userSessionIdSchema"
        classifier == AuditEventId::class -> "auditEventIdSchema"
        classifier == ClientDeviceId::class -> "clientDeviceIdSchema"
        classifier == DecryptedString::class -> "decryptedStringSchema"
        classifier == EncryptedString::class -> "encryptedStringSchema"
        classifier == PasswordHash::class -> "passwordHashSchema"
        classifier == AccessToken::class -> "accessTokenSchema"
        classifier == RefreshToken::class -> "refreshTokenSchema"
        classifier == RefreshTokenHash::class -> "refreshTokenHashSchema"
        classifier == List::class || classifier == Set::class || classifier == Collection::class || classifier.simpleName == "Set" || classifier.simpleName == "List" || classifier.simpleName == "Collection" -> {
            val elementType = type.arguments.firstOrNull()?.type
            val elementZod = if (elementType != null) resolveZodType(paramName, elementType, parentClass) else "z.unknown()"
            "z.array($elementZod)"
        }
        classifier == Map::class || classifier.simpleName == "Map" -> {
            val valType = type.arguments.getOrNull(1)?.type
            val valZod = if (valType != null) resolveZodType(paramName, valType, parentClass) else "z.unknown()"
            "z.record(z.string(), $valZod)"
        }
        else -> {
            classifier.simpleName?.decapitalizeFirstLetter() + "Schema"
        }
    }

    return if (isMarkedNullable) "$baseZod.nullable()" else baseZod
}

private fun resolveTsType(paramName: String, type: KType, parentClass: KClass<*>): String {
    val classifier = type.classifier as? KClass<*> ?: return "unknown"
    val isMarkedNullable = type.isMarkedNullable

    val baseTs = when {
        classifier == UserAuthProvider::class -> "UserAuthProvider"
        classifier == ExternalAuthProvider::class -> "ExternalAuthProvider"
        classifier == UserRole::class -> "UserRole"
        classifier == UserAccountStatus::class -> "UserAccountStatus"
        classifier == AccountLockoutType::class -> "AccountLockoutType"
        classifier == AuditActorType::class -> "AuditActorType"
        classifier == AuditValueSensitivity::class -> "AuditValueSensitivity"
        classifier == AuditStatus::class -> "AuditStatus"
        classifier == CommonAuditMetadataKey::class -> "CommonAuditMetadataKey"
        classifier == UserAuditMetadataKey::class -> "UserAuditMetadataKey"
        classifier == ClientType::class -> "ClientType"
        classifier == SortOrder::class -> "SortOrder"
        classifier == PasswordPolicyFailReason::class -> "PasswordPolicyFailReason"
        classifier == UserSortValues.UserSortBy::class -> "UserSortBy"
        classifier == UserSortValues.UserIdentifierSortBy::class -> "UserIdentifierSortBy"
        classifier == UserSortValues.UserSessionSortBy::class -> "UserSessionSortBy"
        classifier == AuditSortValues.AuditEventSortBy::class -> "AuditEventSortBy"
        classifier == CommonSortValues.TimestampSortBy::class -> "TimestampSortBy"
        classifier == JsonElement::class -> "unknown"
        classifier.simpleName == "Instant" -> "Instant"

        paramName == "clientType" -> "ClientType"
        paramName == "deviceId" || paramName == "clientDeviceId" -> "ClientDeviceId"
        paramName == "role" || paramName == "userRole" || paramName == "actorUserRole" -> "UserRole"
        paramName == "accountStatus" || paramName == "accountStatusOnRestore" -> "UserAccountStatus"
        paramName == "lockoutType" -> "AccountLockoutType"
        paramName == "actorType" -> "AuditActorType"
        paramName == "status" && parentClass == AuditEventPayload::class -> "AuditStatus"
        paramName == "identifierAuthProvider" || paramName == "userAuthProvider" -> "UserAuthProvider"
        paramName == "sensitivity" || paramName == "valueSensitivity" -> "AuditValueSensitivity"
        paramName == "permissionCodes" -> "PermissionCode[]"

        classifier == String::class -> {
            when (paramName) {
                "id" -> when (parentClass) {
                    UserSessionPayload::class -> "UserSessionId"
                    AuditEventPayload::class -> "AuditEventId"
                    UserIdentifierPayload::class -> "UserIdentifierId"
                    else -> "UserId"
                }
                "userId" -> "UserId"
                "identifierId" -> "UserIdentifierId"
                "sessionId" -> "UserSessionId"
                "actorId" -> "UserId"
                "permissionCode" -> "PermissionCode"
                else -> "string"
            }
        }
        classifier == Int::class || classifier == Long::class || classifier == Float::class || classifier == Double::class || classifier == Short::class || classifier == Byte::class -> "number"
        classifier == Boolean::class -> "boolean"
        classifier == PermissionCode::class -> "PermissionCode"
        classifier == UserId::class -> "UserId"
        classifier == UserIdentifierId::class -> "UserIdentifierId"
        classifier == UserSessionId::class -> "UserSessionId"
        classifier == AuditEventId::class -> "AuditEventId"
        classifier == ClientDeviceId::class -> "ClientDeviceId"
        classifier == DecryptedString::class -> "DecryptedString"
        classifier == EncryptedString::class -> "EncryptedString"
        classifier == PasswordHash::class -> "PasswordHash"
        classifier == AccessToken::class -> "AccessToken"
        classifier == RefreshToken::class -> "RefreshToken"
        classifier == RefreshTokenHash::class -> "RefreshTokenHash"
        classifier == List::class || classifier == Set::class || classifier == Collection::class || classifier.simpleName == "Set" || classifier.simpleName == "List" || classifier.simpleName == "Collection" -> {
            val elementType = type.arguments.firstOrNull()?.type
            val elementTs = if (elementType != null) resolveTsType(paramName, elementType, parentClass) else "unknown"
            "$elementTs[]"
        }
        classifier == Map::class || classifier.simpleName == "Map" -> {
            val valType = type.arguments.getOrNull(1)?.type
            val valTs = if (valType != null) resolveTsType(paramName, valType, parentClass) else "unknown"
            "Record<string, $valTs>"
        }
        else -> {
            classifier.simpleName ?: "unknown"
        }
    }

    return if (isMarkedNullable) "$baseTs | null" else baseTs
}

private fun generateDeclarationFile(): String {
    val routeDeclarations = routeClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val props = extractObjectStringProperties(kClass)
        val fields = props.map { (valName, valValue) -> "    readonly $valName: \"$valValue\";" }
        """
        export declare const $name: {
        ${fields.joinToString("\n")}
        };
        """.trimIndent()
    }

    val contractDeclarations = contractClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val props = extractObjectStringProperties(kClass)
        val fields = props.map { (valName, valValue) -> "    readonly $valName: \"$valValue\";" }
        """
        export declare const $name: {
        ${fields.joinToString("\n")}
        };
        """.trimIndent()
    }

    val listingParamNamesDecl = """
    export declare const ListingParamNames: {
      readonly PAGE_NUMBER: "${ListingParamNames.Pagination.PAGE_NUMBER}";
      readonly PAGE_SIZE: "${ListingParamNames.Pagination.PAGE_SIZE}";
      readonly SORT_BY: "${ListingParamNames.Sort.SORT_BY}";
      readonly SORT_ORDER: "${ListingParamNames.Sort.SORT_ORDER}";
      readonly Pagination: {
        readonly PAGE_NUMBER: "${ListingParamNames.Pagination.PAGE_NUMBER}";
        readonly PAGE_SIZE: "${ListingParamNames.Pagination.PAGE_SIZE}";
      };
      readonly Sort: {
        readonly SORT_BY: "${ListingParamNames.Sort.SORT_BY}";
        readonly SORT_ORDER: "${ListingParamNames.Sort.SORT_ORDER}";
      };
    };

    export declare const AuditFilterValues: {
      readonly AuditEventFilterValues: {
        readonly ACTOR_ID: "${AuditFilterValues.AuditEventFilterValues.ACTOR_ID}";
        readonly ACTOR_TYPE: "${AuditFilterValues.AuditEventFilterValues.ACTOR_TYPE}";
        readonly ACTOR_USER_ROLE: "${AuditFilterValues.AuditEventFilterValues.ACTOR_USER_ROLE}";
        readonly ACTION: "${AuditFilterValues.AuditEventFilterValues.ACTION}";
        readonly RESOURCE: "${AuditFilterValues.AuditEventFilterValues.RESOURCE}";
        readonly RESOURCE_ID: "${AuditFilterValues.AuditEventFilterValues.RESOURCE_ID}";
        readonly STATUS: "${AuditFilterValues.AuditEventFilterValues.STATUS}";
        readonly MESSAGE: "${AuditFilterValues.AuditEventFilterValues.MESSAGE}";
      };
    };

    export declare const UserFilterValues: {
      readonly UserFilterValues: {
        readonly ROLE: "${UserFilterValues.UserFilterValues.ROLE}";
        readonly ACCOUNT_STATUS: "${UserFilterValues.UserFilterValues.ACCOUNT_STATUS}";
        readonly ACCOUNT_STATUS_ON_RESTORE: "${UserFilterValues.UserFilterValues.ACCOUNT_STATUS_ON_RESTORE}";
        readonly ACCOUNT_LOCKOUT_TYPE: "${UserFilterValues.UserFilterValues.ACCOUNT_LOCKOUT_TYPE}";
        readonly AUTHORITY_LEVEL_FROM: "${UserFilterValues.UserFilterValues.AUTHORITY_LEVEL_FROM}";
        readonly AUTHORITY_LEVEL_TO: "${UserFilterValues.UserFilterValues.AUTHORITY_LEVEL_TO}";
        readonly IS_TOTP_ENABLED: "${UserFilterValues.UserFilterValues.IS_TOTP_ENABLED}";
        readonly PERMISSION_CODES: "${UserFilterValues.UserFilterValues.PERMISSION_CODES}";
      };
      readonly UserIdentifierFilterValues: {
        readonly USER_ID: "${UserFilterValues.UserIdentifierFilterValues.USER_ID}";
        readonly USER_AUTH_PROVIDER: "${UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER}";
        readonly IDENTIFIER: "${UserFilterValues.UserIdentifierFilterValues.IDENTIFIER}";
      };
      readonly UserSessionFilterValues: {
        readonly USER_ID: "${UserFilterValues.UserSessionFilterValues.USER_ID}";
        readonly USER_ROLE: "${UserFilterValues.UserSessionFilterValues.USER_ROLE}";
        readonly IDENTIFIER_ID: "${UserFilterValues.UserSessionFilterValues.IDENTIFIER_ID}";
        readonly USER_AUTH_PROVIDER: "${UserFilterValues.UserSessionFilterValues.USER_AUTH_PROVIDER}";
        readonly CLIENT_TYPE: "${UserFilterValues.UserSessionFilterValues.CLIENT_TYPE}";
        readonly USER_AGENT: "${UserFilterValues.UserSessionFilterValues.USER_AGENT}";
        readonly IP_ADDRESS: "${UserFilterValues.UserSessionFilterValues.IP_ADDRESS}";
        readonly LANGUAGE: "${UserFilterValues.UserSessionFilterValues.LANGUAGE}";
        readonly DEVICE_ID: "${UserFilterValues.UserSessionFilterValues.DEVICE_ID}";
        readonly DEVICE_NAME: "${UserFilterValues.UserSessionFilterValues.DEVICE_NAME}";
        readonly APP_VERSION: "${UserFilterValues.UserSessionFilterValues.APP_VERSION}";
        readonly OPERATION_SYSTEM_VERSION: "${UserFilterValues.UserSessionFilterValues.OPERATION_SYSTEM_VERSION}";
        readonly IDENTIFIER: "${UserFilterValues.UserSessionFilterValues.IDENTIFIER}";
      };
    };
    """.trimIndent()

    val errorDeclarations = errorClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val props = extractObjectStringProperties(kClass)
        val fields = props.map { (valName, valValue) -> "    readonly $valName: \"$valValue\";" }
        """
        export declare const $name: {
        ${fields.joinToString("\n")}
        };
        """.trimIndent()
    }

    val permDeclarations = permClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val props = extractPermissionCodeProperties(kClass)
        val fields = props.map { (valName, _) -> "    readonly $valName: PermissionCode;" }
        """
        export const $name: {
        ${fields.joinToString(",\n")}
        };
        """.trimIndent()
    }

    val enumDeclarations = getAllEnumInfos().joinToString("\n\n") { info ->
        val unionType = info.entries.map { it.second }.distinct().joinToString(" | ") { "'$it'" }
        val objectFields = info.entries.joinToString("\n") { (key, wire) -> "  readonly $key: '$wire';" }

        """
        export type ${info.name} = $unionType;
        export const ${info.name}: {
        $objectFields
        };
        export const ${info.schemaName}: z.ZodType<${info.name}>;
        """.trimIndent()
    }

    val extraDeclarations = """
    export interface ConfirmationKey {
      readonly type: ConfirmationType;
      readonly identifier: string;
    }
    export const confirmationKeySchema: z.ZodType<ConfirmationKey>;

    ${generateDomainModelsAndMappersDts()}

    ${generateCompositeParsersDts()}

    export type UserSortValues = 'created_at' | 'updated_at' | 'last_accessed_at' | 'last_reauthenticated_at' | 'expires_at';

    export namespace UserSortValues {
      export type UserSortBy = 'last_login_at' | 'last_active_at' | 'scheduled_permanent_deletion_at' | 'account_lockout_type' | 'temporary_lockout_until' | 'created_at' | 'updated_at';
      export const UserSortBy: {
        readonly LAST_LOGIN_AT: 'last_login_at';
        readonly LAST_ACTIVE_AT: 'last_active_at';
        readonly SCHEDULED_PERMANENT_DELETION_AT: 'scheduled_permanent_deletion_at';
        readonly ACCOUNT_LOCKOUT_TYPE: 'account_lockout_type';
        readonly TEMPORARY_LOCKOUT_UNTIL: 'temporary_lockout_until';
        readonly CREATED_AT: 'created_at';
        readonly UPDATED_AT: 'updated_at';
      };

      export type UserIdentifierSortBy = 'created_at' | 'updated_at';
      export const UserIdentifierSortBy: {
        readonly CREATED_AT: 'created_at';
        readonly UPDATED_AT: 'updated_at';
      };

      export type UserSessionSortBy = 'expires_at' | 'last_accessed_at' | 'last_reauthenticated_at' | 'created_at' | 'updated_at';
      export const UserSessionSortBy: {
        readonly EXPIRES_AT: 'expires_at';
        readonly LAST_ACCESSED_AT: 'last_accessed_at';
        readonly LAST_REAUTHENTICATED_AT: 'last_reauthenticated_at';
        readonly CREATED_AT: 'created_at';
        readonly UPDATED_AT: 'updated_at';
      };
    }
    """.trimIndent()

    val dtoDeclarations = dtoClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val schemaName = name.decapitalizeFirstLetter() + "Schema"
        val primaryConstructor = kClass.primaryConstructor

        val fields = if (primaryConstructor != null) {
            primaryConstructor.parameters.map { param ->
                val paramName = param.name ?: ""
                val serialNameAnnotation = param.annotations.filterIsInstance<SerialName>().firstOrNull()
                    ?: kClass.declaredMemberProperties.firstOrNull { it.name == paramName }?.findAnnotation<SerialName>()

                val wireKey = serialNameAnnotation?.value ?: paramName
                val tsType = resolveTsType(paramName, param.type, kClass)
                "    $wireKey: $tsType;"
            }
        } else {
            emptyList()
        }

        """
        export interface $name {
        ${fields.joinToString("\n")}
        }
        export const $schemaName: z.ZodType<$name>;
        """.trimIndent()
    }

    return """
    import { z } from 'zod';

    export type Instant = number;

    export type PermissionCode = string & { readonly __brand: 'PermissionCode' };
    export function toPermissionCodeOrNull(value: string | null | undefined): PermissionCode | null;
    export function toPermissionCodeOrThrow(value: string): PermissionCode;
    export const permissionCodeSchema: z.ZodType<PermissionCode>;

    export type UserId = string & { readonly __brand: 'UserId' };
    export function toUserIdOrNull(value: string | null | undefined): UserId | null;
    export function toUserIdOrThrow(value: string): UserId;
    export const userIdSchema: z.ZodType<UserId>;

    export type UserIdentifierId = string & { readonly __brand: 'UserIdentifierId' };
    export function toUserIdentifierIdOrNull(value: string | null | undefined): UserIdentifierId | null;
    export function toUserIdentifierIdOrThrow(value: string): UserIdentifierId;
    export const userIdentifierIdSchema: z.ZodType<UserIdentifierId>;

    export type UserSessionId = string & { readonly __brand: 'UserSessionId' };
    export function toUserSessionIdOrNull(value: string | null | undefined): UserSessionId | null;
    export function toUserSessionIdOrThrow(value: string): UserSessionId;
    export const userSessionIdSchema: z.ZodType<UserSessionId>;

    export type AuditEventId = string & { readonly __brand: 'AuditEventId' };
    export function toAuditEventIdOrNull(value: string | null | undefined): AuditEventId | null;
    export function toAuditEventIdOrThrow(value: string): AuditEventId;
    export const auditEventIdSchema: z.ZodType<AuditEventId>;

    export type ClientDeviceId = string & { readonly __brand: 'ClientDeviceId' };
    export function toClientDeviceIdOrNull(value: string | null | undefined): ClientDeviceId | null;
    export function toClientDeviceIdOrThrow(value: string): ClientDeviceId;
    export const clientDeviceIdSchema: z.ZodType<ClientDeviceId>;

    export type DecryptedString = string & { readonly __brand: 'DecryptedString' };
    export function toDecryptedStringOrNull(value: string | null | undefined): DecryptedString | null;
    export function toDecryptedStringOrThrow(value: string): DecryptedString;
    export const decryptedStringSchema: z.ZodType<DecryptedString>;

    export type EncryptedString = string & { readonly __brand: 'EncryptedString' };
    export function toEncryptedStringOrNull(value: string | null | undefined): EncryptedString | null;
    export function toEncryptedStringOrThrow(value: string): EncryptedString;
    export const encryptedStringSchema: z.ZodType<EncryptedString>;

    export type PasswordHash = string & { readonly __brand: 'PasswordHash' };
    export function toPasswordHashOrNull(value: string | null | undefined): PasswordHash | null;
    export function toPasswordHashOrThrow(value: string): PasswordHash;
    export const passwordHashSchema: z.ZodType<PasswordHash>;

    export type AccessToken = string & { readonly __brand: 'AccessToken' };
    export function toAccessTokenOrNull(value: string | null | undefined): AccessToken | null;
    export function toAccessTokenOrThrow(value: string): AccessToken;
    export const accessTokenSchema: z.ZodType<AccessToken>;

    export type RefreshToken = string & { readonly __brand: 'RefreshToken' };
    export function toRefreshTokenOrNull(value: string | null | undefined): RefreshToken | null;
    export function toRefreshTokenOrThrow(value: string): RefreshToken;
    export const refreshTokenSchema: z.ZodType<RefreshToken>;

    export type RefreshTokenHash = string & { readonly __brand: 'RefreshTokenHash' };
    export function toRefreshTokenHashOrNull(value: string | null | undefined): RefreshTokenHash | null;
    export function toRefreshTokenHashOrThrow(value: string): RefreshTokenHash;
    export const refreshTokenHashSchema: z.ZodType<RefreshTokenHash>;

    $routeDeclarations

    $contractDeclarations

    $listingParamNamesDecl

    $errorDeclarations

    $permDeclarations

    $enumDeclarations

    $extraDeclarations

    export function pagedResultSchema<T extends z.ZodTypeAny>(itemSchema: T): z.ZodObject<{
      items: z.ZodArray<T>;
      totalCount: z.ZodNumber;
      pageNumber: z.ZodNumber;
      pageSize: z.ZodNumber;
      totalPages: z.ZodNumber;
    }>;

    export interface PagedResult<T> {
      items: T[];
      totalCount: number;
      pageNumber: number;
      pageSize: number;
      totalPages: number;
    }

    $dtoDeclarations
    """.trimIndent()
}

private val rawDomainToDtoClasses = listOf(
    ClientInfo::class to ClientInfoPayload::class,
    ClientDeviceInfo::class to ClientDeviceInfoPayload::class,
    AccountLockoutPolicy::class to AccountLockoutPolicyPayload::class,
    IpRestrictionPolicy::class to IpRestrictionPolicyPayload::class,
    OtpConfirmation::class to OtpConfirmationPayload::class,
    ManagementPasswordPolicy::class to ManagementPasswordPolicyPayload::class,
    OpenPasswordPolicy::class to OpenPasswordPolicyPayload::class,
    ManagementSecuritySettings::class to ManagementSecuritySettingsPayload::class,
    OpenSecuritySettings::class to OpenSecuritySettingsPayload::class,
    TotpRecoveryCodes::class to TotpRecoveryCodesPayload::class,
    TotpSetup::class to TotpSetupPayload::class,
    VerifyTotp::class to VerifyTotpPayload::class,
    ManagementGlobalSettings::class to ManagementGlobalSettingsPayload::class,
    OpenGlobalSettings::class to OpenGlobalSettingsPayload::class,
    AuditEvent::class to AuditEventPayload::class,
    AuditEventMetadata::class to AuditEventMetadataPayload::class,
    AuthData::class to AuthDataPayload::class,
    AvailableAuthProviders::class to AvailableAuthProvidersPayload::class,
    ManagementAuthSettings::class to ManagementAuthSettingsPayload::class,
    OpenAuthSettings::class to OpenAuthSettingsPayload::class,
    ManagementUserConfiguration::class to ManagementUserConfigurationPayload::class,
    OpenUserConfiguration::class to OpenUserConfigurationPayload::class,
    EmailRestrictionPolicy::class to EmailRestrictionPolicyPayload::class,
    UserIdentifier::class to UserIdentifierPayload::class,
    DeletedSessions::class to DeletedSessionsPayload::class,
    UserSession::class to UserSessionPayload::class,
    SessionToken::class to SessionTokenPayload::class,
    UserDetails::class to UserDetailsPayload::class,
    UserPublic::class to UserPublicPayload::class
)

private val domainToDtoClasses = sortDomainToDtoTopologically(rawDomainToDtoClasses)

private fun getWireKey(payloadClass: KClass<*>, paramName: String): String {
    val param = payloadClass.primaryConstructor?.parameters?.firstOrNull { it.name == paramName }
    val serialNameAnnotation = param?.annotations?.filterIsInstance<SerialName>()?.firstOrNull()
        ?: payloadClass.declaredMemberProperties.firstOrNull { it.name == paramName }?.findAnnotation<SerialName>()
    return serialNameAnnotation?.value ?: paramName
}

private fun findMatchingPayloadParam(
    domainParamName: String,
    domainParamType: KType,
    payloadClass: KClass<*>
): Pair<String, KType>? {
    val primaryParams = payloadClass.primaryConstructor?.parameters ?: return null
    val domainClassifier = domainParamType.classifier as? KClass<*>

    val exact = primaryParams.firstOrNull {
        it.name == domainParamName ||
        it.annotations.filterIsInstance<SerialName>().firstOrNull()?.value == domainParamName
    }
    if (exact != null) return (exact.name ?: domainParamName) to exact.type

    val aliasName = when (domainParamName) {
        "deviceInfo" -> "clientDeviceInfo"
        "userDetails" -> "userDetailsPayload"
        "sessionToken" -> "sessionTokenPayload"
        else -> null
    }
    if (aliasName != null) {
        val aliasMatch = primaryParams.firstOrNull {
            it.name == aliasName ||
            it.annotations.filterIsInstance<SerialName>().firstOrNull()?.value == aliasName
        }
        if (aliasMatch != null) return (aliasMatch.name ?: aliasName) to aliasMatch.type
    }

    val targetPayloadClass = domainToDtoClasses.firstOrNull { it.first == domainClassifier }?.second
    if (targetPayloadClass != null) {
        val typeMatch = primaryParams.firstOrNull { (it.type.classifier as? KClass<*>) == targetPayloadClass }
        if (typeMatch != null) return (typeMatch.name ?: "") to typeMatch.type
    }

    return null
}

private fun findMatchingDomainParam(
    payloadParamName: String,
    payloadParamType: KType,
    domainClass: KClass<*>
): Pair<String, KType>? {
    val primaryParams = domainClass.primaryConstructor?.parameters ?: return null
    val payloadClassifier = payloadParamType.classifier as? KClass<*>

    val exact = primaryParams.firstOrNull { it.name == payloadParamName }
    if (exact != null) return (exact.name ?: payloadParamName) to exact.type

    val aliasName = when (payloadParamName) {
        "clientDeviceInfo" -> "deviceInfo"
        "userDetailsPayload" -> "userDetails"
        "sessionTokenPayload" -> "sessionToken"
        else -> null
    }
    if (aliasName != null) {
        val aliasMatch = primaryParams.firstOrNull { it.name == aliasName }
        if (aliasMatch != null) return (aliasMatch.name ?: aliasName) to aliasMatch.type
    }

    val targetDomainClass = domainToDtoClasses.firstOrNull { it.second == payloadClassifier }?.first
    if (targetDomainClass != null) {
        val typeMatch = primaryParams.firstOrNull { (it.type.classifier as? KClass<*>) == targetDomainClass }
        if (typeMatch != null) return (typeMatch.name ?: "") to typeMatch.type
    }

    return null
}

private fun buildToDomainFields(domainClass: KClass<*>, payloadClass: KClass<*>): String {
    val fields = domainClass.primaryConstructor?.parameters?.mapNotNull { domainParam ->
        val domainParamName = domainParam.name ?: return@mapNotNull null
        val match = findMatchingPayloadParam(domainParamName, domainParam.type, payloadClass)
        
        if (match == null) {
            return@mapNotNull "  $domainParamName: null"
        }

        val (payloadParamName, _) = match
        val wireKey = getWireKey(payloadClass, payloadParamName)
        val payloadRef = "payload.$wireKey"
        val type = domainParam.type
        val classifier = type.classifier as? KClass<*>
        val isNullable = type.isMarkedNullable
        
        val mappedValue = when {
            classifier?.simpleName?.endsWith("Id") == true -> {
                val brandName = classifier.simpleName
                if (isNullable) {
                    "$payloadRef ? to${brandName}OrThrow($payloadRef) : null"
                } else {
                    "to${brandName}OrThrow($payloadRef)"
                }
            }
            classifier == PermissionCode::class -> {
                if (isNullable) "$payloadRef ? toPermissionCodeOrThrow($payloadRef) : null"
                else "toPermissionCodeOrThrow($payloadRef)"
            }
            classifier == Set::class || classifier == List::class -> {
                val typeArg = type.arguments.firstOrNull()?.type?.classifier as? KClass<*>
                if (typeArg == PermissionCode::class) {
                    "$payloadRef ? $payloadRef.map(val => toPermissionCodeOrThrow(val)) : []"
                } else if (typeArg?.simpleName?.endsWith("Id") == true) {
                    val brandName = typeArg.simpleName
                    "$payloadRef ? $payloadRef.map(val => to${brandName}OrThrow(val)) : []"
                } else if (domainToDtoClasses.any { it.first == typeArg }) {
                    val nestedDomainName = typeArg?.simpleName
                    "$payloadRef ? $payloadRef.map(val => to$nestedDomainName(val)) : []"
                } else {
                    payloadRef
                }
            }
            domainToDtoClasses.any { it.first == classifier } -> {
                val nestedDomainName = classifier?.simpleName
                if (isNullable) {
                    "$payloadRef ? to$nestedDomainName($payloadRef) : null"
                } else {
                    "to$nestedDomainName($payloadRef)"
                }
            }
            else -> payloadRef
        }
        
        "  $domainParamName: $mappedValue"
    } ?: emptyList()
    return fields.joinToString(",\n")
}

private fun buildToPayloadFields(domainClass: KClass<*>, payloadClass: KClass<*>): String {
    val fields = payloadClass.primaryConstructor?.parameters?.mapNotNull { payloadParam ->
        val payloadParamName = payloadParam.name ?: return@mapNotNull null
        val wireKey = getWireKey(payloadClass, payloadParamName)
        val match = findMatchingDomainParam(payloadParamName, payloadParam.type, domainClass)
        
        if (match == null) {
            return@mapNotNull "  $wireKey: null"
        }

        val (domainParamName, _) = match
        val domainRef = "domain.$domainParamName"
        val payloadParamType = payloadParam.type
        val isNullable = payloadParamType.isMarkedNullable
        
        val domainParam = domainClass.primaryConstructor?.parameters?.firstOrNull { it.name == domainParamName }
        val domainClassifier = domainParam?.type?.classifier as? KClass<*>
        
        val mappedValue = when {
            domainClassifier?.simpleName?.endsWith("Id") == true -> domainRef
            domainClassifier == PermissionCode::class -> domainRef
            domainClassifier == Set::class || domainClassifier == List::class -> {
                val typeArg = domainParam?.type?.arguments?.firstOrNull()?.type?.classifier as? KClass<*>
                if (domainToDtoClasses.any { it.first == typeArg }) {
                    val nestedDomainDtoPair = domainToDtoClasses.firstOrNull { it.first == typeArg }
                    val nestedPayloadName = nestedDomainDtoPair?.second?.simpleName
                    "$domainRef ? $domainRef.map(val => to$nestedPayloadName(val)) : []"
                } else {
                    domainRef
                }
            }
            domainToDtoClasses.any { it.first == domainClassifier } -> {
                val nestedDomainDtoPair = domainToDtoClasses.firstOrNull { it.first == domainClassifier }
                val nestedPayloadNameCorrect = nestedDomainDtoPair?.second?.simpleName
                if (isNullable) {
                    "$domainRef ? to$nestedPayloadNameCorrect($domainRef) : null"
                } else {
                    "to$nestedPayloadNameCorrect($domainRef)"
                }
            }
            else -> domainRef
        }
        
        "  $wireKey: $mappedValue"
    } ?: emptyList()
    return fields.joinToString(",\n")
}

private fun generateDomainModelsAndMappersJs(): String {
    return domainToDtoClasses.joinToString("\n\n") { (domainClass, payloadClass) ->
        val domainName = domainClass.simpleName ?: ""
        val payloadName = payloadClass.simpleName ?: ""
        
        if (domainName == "AuditEvent") {
            return@joinToString """
            export const toAuditEvent = (payload, actionTypeParser, resourceTypeParser, metadataKeyParser) => ({
              id: toAuditEventIdOrThrow(payload.id),
              actorId: payload.actor_id,
              actorType: payload.actor_type,
              actorUserRole: payload.actor_user_role,
              action: actionTypeParser.fromValueOrThrow(payload.action),
              resource: resourceTypeParser.fromValueOrThrow(payload.resource),
              resourceId: payload.resource_id,
              resourceValueSensitivity: 'non_sensitive',
              status: payload.status,
              metadata: payload.metadata ? payload.metadata.map(val => toAuditEventMetadata(val, metadataKeyParser)) : [],
              message: payload.message,
              createdAt: payload.created_at
            });
            
            export const toAuditEventPayload = (domain) => ({
              id: domain.id,
              actor_id: domain.actorId,
              actor_type: domain.actorType,
              actor_user_role: domain.actorUserRole,
              action: domain.action,
              resource: domain.resource,
              resource_id: domain.resourceId,
              status: domain.status,
              metadata: domain.metadata ? domain.metadata.map(val => toAuditEventMetadataPayload(val)) : [],
              message: domain.message,
              created_at: domain.createdAt
            });
            """.trimIndent()
        }
        
        if (domainName == "AuditEventMetadata") {
            return@joinToString """
            export const toAuditEventMetadata = (payload, metadataKeyParser) => ({
              key: metadataKeyParser.fromValueOrThrow(payload.key),
              value: payload.value,
              valueSensitivity: payload.value_sensitivity
            });
            
            export const toAuditEventMetadataPayload = (domain) => ({
              key: domain.key,
              value: domain.value,
              value_sensitivity: domain.valueSensitivity
            });
            """.trimIndent()
        }
        
        val toDomainFields = buildToDomainFields(domainClass, payloadClass)
        val toPayloadFields = buildToPayloadFields(domainClass, payloadClass)
        
        """
        export const to$domainName = (payload) => ({
        $toDomainFields
        });
        
        export const to$payloadName = (domain) => ({
        $toPayloadFields
        });
        """.trimIndent()
    }
}

private fun generateDomainModelsAndMappersDts(): String {
    return domainToDtoClasses.joinToString("\n\n") { (domainClass, payloadClass) ->
        val domainName = domainClass.simpleName ?: ""
        val payloadName = payloadClass.simpleName ?: ""
        
        val fields = domainClass.primaryConstructor?.parameters?.mapNotNull { param ->
            val paramName = param.name ?: return@mapNotNull null
            val tsType = resolveTsType(paramName, param.type, domainClass)
            "  readonly $paramName: $tsType;"
        }?.joinToString("\n") ?: ""
        
        if (domainName == "AuditEvent") {
            return@joinToString """
            export interface $domainName {
            $fields
            }
            
            export declare const toAuditEvent: (payload: AuditEventPayload, actionTypeParser: CompositeAuditActionTypeParser, resourceTypeParser: CompositeAuditResourceTypeParser, metadataKeyParser: CompositeAuditMetadataKeyParser) => AuditEvent;
            export declare const toAuditEventPayload: (domain: AuditEvent) => AuditEventPayload;
            """.trimIndent()
        }
        
        if (domainName == "AuditEventMetadata") {
            return@joinToString """
            export interface $domainName {
            $fields
            }
            
            export declare const toAuditEventMetadata: (payload: AuditEventMetadataPayload, metadataKeyParser: CompositeAuditMetadataKeyParser) => AuditEventMetadata;
            export declare const toAuditEventMetadataPayload: (domain: AuditEventMetadata) => AuditEventMetadataPayload;
            """.trimIndent()
        }
        
        """
        export interface $domainName {
        $fields
        }
        
        export declare const to$domainName: (payload: $payloadName) => $domainName;
        export declare const to$payloadName: (domain: $domainName) => $payloadName;
        """.trimIndent()
    }
}

private fun String.decapitalizeFirstLetter(): String {
    if (isEmpty()) return this
    return replaceFirstChar { it.lowercase() }
}
