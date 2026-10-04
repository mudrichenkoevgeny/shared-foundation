package io.github.mudrichenkoevgeny.shared.foundation.generator

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.contract.AuditEventFields
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.audit.resource.CommonAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventId
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditSortValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.permissions.AuditPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventMetadataPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.route.management.ManagementAuditRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceId
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
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.websocket.SocketFrame
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.websocket.WebSocketInitializePayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.audit.action.SecurityAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.audit.resource.SecurityAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicyFailReason
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
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.permission.SettingsPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.contract.GlobalSettingsApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.contract.SettingsWebSocketEventTypes
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.ManagementGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.OpenGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.management.globalsettings.ManagementGlobalSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.open.globalsettings.OpenGlobalSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.ExternalAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserDetails
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserPublic
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifier
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.data.AuthData
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.SessionToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSession
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.AuthSettingsPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.IdentifierPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.SessionPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.UserPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming.UserErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming.UserErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
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
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.resetpassword.SelfManagementResetPasswordRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.settings.ManagementAuthSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.unlock.SelfManagementUnlockRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.identifier.ManagementIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.identifier.SelfManagementIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.session.ManagementSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.session.SelfManagementSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.user.ManagementUserRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.user.SelfManagementUserRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.user.security.ManagementUserSecurityRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.user.security.SelfManagementUserSecurityRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.login.OpenLoginRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.register.OpenRegisterRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.resetpassword.OpenResetPasswordRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.unlock.OpenUnlockRoutes
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
          "version": "0.0.49",
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
        "ClientDeviceId"
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
    ManagementAuthSettingsRoutes::class
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
    SettingsWebSocketEventTypes::class
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

private val dtoClasses = listOf(
    ApiErrorResponse::class,
    ClientInfoPayload::class,
    ClientDeviceInfoPayload::class,
    AccountLockoutPolicyPayload::class,
    IpRestrictionPolicyPayload::class,
    OtpConfirmationPayload::class,
    ManagementPasswordPolicyPayload::class,
    OpenPasswordPolicyPayload::class,
    ManagementSecuritySettingsPayload::class,
    OpenSecuritySettingsPayload::class,
    TotpRecoveryCodesPayload::class,
    TotpSetupPayload::class,
    VerifyTotpPayload::class,
    ManagementGlobalSettingsPayload::class,
    OpenGlobalSettingsPayload::class,
    AuditEventPayload::class,
    AuditEventMetadataPayload::class,
    AuthDataPayload::class,
    AvailableAuthProvidersPayload::class,
    ManagementAuthSettingsPayload::class,
    OpenAuthSettingsPayload::class,
    ManagementUserConfigurationPayload::class,
    OpenUserConfigurationPayload::class,
    EmailRestrictionPolicyPayload::class,
    UserIdentifierPayload::class,
    DeletedSessionsPayload::class,
    UserSessionPayload::class,
    RefreshTokenPayload::class,
    SessionTokenPayload::class,
    UserDetailsPayload::class,
    UserPublicPayload::class,
    SocketFrame::class,
    WebSocketInitializePayload::class,
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
        paramName == "sensitivity" -> "auditValueSensitivitySchema"
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
        classifier == List::class -> {
            val elementType = type.arguments.firstOrNull()?.type
            val elementZod = if (elementType != null) resolveZodType(paramName, elementType, parentClass) else "z.unknown()"
            "z.array($elementZod)"
        }
        classifier == Set::class -> {
            val elementType = type.arguments.firstOrNull()?.type
            val elementZod = if (elementType != null) resolveZodType(paramName, elementType, parentClass) else "z.unknown()"
            "z.array($elementZod)"
        }
        classifier == Map::class -> {
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
        classifier == ClientType::class -> "ClientType"
        classifier == SortOrder::class -> "SortOrder"
        classifier == PasswordPolicyFailReason::class -> "PasswordPolicyFailReason"
        classifier == UserSortValues.UserSortBy::class -> "UserSortBy"
        classifier == UserSortValues.UserIdentifierSortBy::class -> "UserIdentifierSortBy"
        classifier == UserSortValues.UserSessionSortBy::class -> "UserSessionSortBy"
        classifier == AuditSortValues.AuditEventSortBy::class -> "AuditEventSortBy"
        classifier == CommonSortValues.TimestampSortBy::class -> "TimestampSortBy"
        classifier == JsonElement::class -> "unknown"

        paramName == "clientType" -> "ClientType"
        paramName == "deviceId" || paramName == "clientDeviceId" -> "ClientDeviceId"
        paramName == "role" || paramName == "userRole" || paramName == "actorUserRole" -> "UserRole"
        paramName == "accountStatus" || paramName == "accountStatusOnRestore" -> "UserAccountStatus"
        paramName == "lockoutType" -> "AccountLockoutType"
        paramName == "actorType" -> "AuditActorType"
        paramName == "status" && parentClass == AuditEventPayload::class -> "AuditStatus"
        paramName == "identifierAuthProvider" || paramName == "userAuthProvider" -> "UserAuthProvider"
        paramName == "sensitivity" -> "AuditValueSensitivity"
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
        classifier == List::class || classifier == Set::class -> {
            val elementType = type.arguments.firstOrNull()?.type
            val elementTs = if (elementType != null) resolveTsType(paramName, elementType, parentClass) else "unknown"
            "$elementTs[]"
        }
        classifier == Map::class -> {
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

private val domainToDtoClasses = listOf(
    ClientInfo::class to ClientInfoPayload::class,
    ClientDeviceInfo::class to ClientDeviceInfoPayload::class,
    UserSession::class to UserSessionPayload::class,
    UserDetails::class to UserDetailsPayload::class,
    UserIdentifier::class to UserIdentifierPayload::class,
    UserPublic::class to UserPublicPayload::class,
    AuthData::class to AuthDataPayload::class,
    SessionToken::class to SessionTokenPayload::class
)

private fun getWireKey(payloadClass: KClass<*>, paramName: String): String {
    val param = payloadClass.primaryConstructor?.parameters?.firstOrNull { it.name == paramName }
    val serialNameAnnotation = param?.annotations?.filterIsInstance<SerialName>()?.firstOrNull()
        ?: payloadClass.declaredMemberProperties.firstOrNull { it.name == paramName }?.findAnnotation<SerialName>()
    return serialNameAnnotation?.value ?: paramName
}

private fun buildToDomainFields(domainClass: KClass<*>, payloadClass: KClass<*>): String {
    val fields = domainClass.primaryConstructor?.parameters?.mapNotNull { domainParam ->
        val domainParamName = domainParam.name ?: return@mapNotNull null
        val payloadParamName = if (domainParamName == "deviceInfo") "clientDeviceInfo" else domainParamName
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
        val domainParamName = if (payloadParamName == "clientDeviceInfo") "deviceInfo" else payloadParamName
        val wireKey = getWireKey(payloadClass, payloadParamName)
        
        val domainRef = "domain.$domainParamName"
        val payloadParamType = payloadParam.type
        val isNullable = payloadParamType.isMarkedNullable
        
        val domainParam = domainClass.primaryConstructor?.parameters?.firstOrNull { it.name == domainParamName }
        val domainClassifier = domainParam?.type?.classifier as? KClass<*>
        
        val mappedValue = when {
            domainClassifier?.simpleName?.endsWith("Id") == true -> domainRef
            domainClassifier == PermissionCode::class -> domainRef
            domainClassifier == Set::class || domainClassifier == List::class -> domainRef
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
