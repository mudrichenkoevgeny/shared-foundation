package io.github.mudrichenkoevgeny.shared.foundation.generator

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.audit.resource.CommonAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventId
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.permissions.AuditPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventMetadataPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.route.management.ManagementAuditRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceId
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.CommonSortValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.model.ApiErrorResponse
import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming.CommonErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming.CommonErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.audit.action.SecurityAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.audit.resource.SecurityAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicyFailReason
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.permission.SecurityPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorCodes
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
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.AuthSettingsPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.IdentifierPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.SessionPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.UserPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming.UserErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming.UserErrorCodes
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
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.primaryConstructor

fun main() {
    try {
        val currentDir = File(System.getProperty("user.dir"))
        val rootDir = if (currentDir.name == "npm-generator") currentDir.parentFile.parentFile else currentDir
        val outputDir = File(rootDir, "build/npm-package")
        outputDir.mkdirs()

        val packageJson = """
        {
          "name": "@mudrichenkoevgeny/shared-foundation",
          "version": "0.0.47",
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

        TypeScript & Zod contracts, API routes, error codes, permission models, and branded types generated directly from the [shared-foundation](https://github.com/mudrichenkoevgeny/shared-foundation) Kotlin Multiplatform (KMP) repository.

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
        - **Route Constants**: Strongly typed API endpoint routes (`ManagementSecuritySettingsRoutes`, `OpenLoginRoutes`, `SelfManagementUserRoutes`).
        - **Error & Permission Codes**: Literal string definitions for all application error codes (`SecurityErrorCodes`, `CommonErrorCodes`) and permission strings (`SecurityPermissionCode`, `UserPermissionCode`).
        - **Branded Types & Helpers**: Type-safe domain identifiers (`UserId`, `PermissionCode`, `UserSessionId`, `UserIdentifierId`, `AuditEventId`, `ClientDeviceId`) with converter helpers (`toUserIdOrThrow`, `toPermissionCodeOrNull`).

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
        val errorCodeCode = generateErrorCodeObjects()
        val permissionCodeCode = generatePermissionCodeObjects()
        val enumCode = generateEnums()
        val dtoCode = generateDtoSchemasAndTypes()

        val fullJsContent = """
        import { z } from 'zod';

        $brandedCode

        $routeCode

        $errorCodeCode

        $permissionCodeCode

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
    ExternalAuthProvider::class
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

private fun generateEnums(): String {
    return enumClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val schemaName = name.decapitalizeFirstLetter() + "Schema"

        val enumConstants = kClass.java.enumConstants
        if (enumConstants != null && enumConstants.isNotEmpty()) {
            val values = enumConstants.mapNotNull { enumConst ->
                val serialNameProp = try {
                    enumConst.javaClass.getMethod("getSerialName").invoke(enumConst) as? String
                } catch (e: Exception) {
                    null
                }
                val value = serialNameProp ?: (enumConst as Enum<*>).name.lowercase()
                "\"$value\""
            }
            """
            export const $schemaName = z.enum([${values.joinToString(", ")}]);
            """.trimIndent()
        } else if (kClass == ExternalAuthProvider::class) {
            val values = ExternalAuthProvider.all.map { "\"" + it.userAuthProvider.serialName + "\"" }
            """
            export const $schemaName = z.enum([${values.joinToString(", ")}]);
            """.trimIndent()
        } else {
            """
            export const $schemaName = z.string();
            """.trimIndent()
        }
    }
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
        export declare const $name: {
        ${fields.joinToString(",\n")}
        };
        """.trimIndent()
    }

    val enumDeclarations = enumClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val schemaName = name.decapitalizeFirstLetter() + "Schema"
        """
        export const $schemaName: z.ZodType<string>;
        export type $name = z.infer<typeof $schemaName>;
        """.trimIndent()
    }

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

    $errorDeclarations

    $permDeclarations

    $enumDeclarations

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

private fun String.decapitalizeFirstLetter(): String {
    if (isEmpty()) return this
    return replaceFirstChar { it.lowercase() }
}
