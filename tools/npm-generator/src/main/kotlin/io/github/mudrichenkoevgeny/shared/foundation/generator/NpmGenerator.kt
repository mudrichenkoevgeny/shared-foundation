package io.github.mudrichenkoevgeny.shared.foundation.generator

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.contract.AuditApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.ui.UiValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.contractClasses
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.dtoClasses
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.enumClasses
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.errorClasses
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.extractClassStructure
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.extractObjectStringProperties
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.getAllEnumInfos
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.permClasses
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.routeClasses
import io.github.mudrichenkoevgeny.shared.foundation.generator.doc.extractKDoc
import io.github.mudrichenkoevgeny.shared.foundation.generator.doc.formatAsJsDoc
import io.github.mudrichenkoevgeny.shared.foundation.generator.localization.generateLocalizationCode
import io.github.mudrichenkoevgeny.shared.foundation.generator.mapper.generateCompositeParsersDts
import io.github.mudrichenkoevgeny.shared.foundation.generator.mapper.generateCompositeParsersJs
import io.github.mudrichenkoevgeny.shared.foundation.generator.mapper.generateDomainModelsAndMappersDts
import io.github.mudrichenkoevgeny.shared.foundation.generator.mapper.generateDomainModelsAndMappersJs
import io.github.mudrichenkoevgeny.shared.foundation.generator.mapper.generateParamParsersAndSerializersDts
import io.github.mudrichenkoevgeny.shared.foundation.generator.mapper.generateParamParsersAndSerializersJs
import io.github.mudrichenkoevgeny.shared.foundation.generator.route.extractPermissionCodeProperties
import io.github.mudrichenkoevgeny.shared.foundation.generator.route.generateContractObjects
import io.github.mudrichenkoevgeny.shared.foundation.generator.route.generateEnumsJs
import io.github.mudrichenkoevgeny.shared.foundation.generator.route.generateErrorCodeObjects
import io.github.mudrichenkoevgeny.shared.foundation.generator.route.generatePermissionCodeObjects
import io.github.mudrichenkoevgeny.shared.foundation.generator.route.generateRouteObjects
import io.github.mudrichenkoevgeny.shared.foundation.generator.schema.generateBrandedTypes
import io.github.mudrichenkoevgeny.shared.foundation.generator.schema.generateDtoSchemasAndTypes
import io.github.mudrichenkoevgeny.shared.foundation.generator.schema.resolveTsType
import io.github.mudrichenkoevgeny.shared.foundation.generator.testing.generateTestFixturesDts
import io.github.mudrichenkoevgeny.shared.foundation.generator.testing.generateTestFixturesJs
import kotlinx.serialization.SerialName
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.primaryConstructor

fun main() {
    try {
        val currentDir = File(System.getProperty("user.dir"))
        val rootDir = if (currentDir.name == "npm-generator") currentDir.parentFile.parentFile else currentDir
        val outputDir = File(rootDir, "build/npm-package")
        outputDir.mkdirs()

        val packageVersion = System.getProperty("PACKAGE_VERSION")
            ?.takeIf { it.isNotBlank() && it != "unspecified" }
            ?: System.getenv("PACKAGE_VERSION")
            ?: parseRootBuildGradleVersion(rootDir)
            ?: "0.0.71"

        val packageJson = """
        {
          "name": "@mudrichenkoevgeny/shared-foundation",
          "version": "$packageVersion",
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
        File(outputDir, ".npmignore").writeText("*.tgz\n")

        val npmReadme = """
        # @mudrichenkoevgeny/shared-foundation

        TypeScript & Zod contracts, REST API routes, navigation screen routes, screen parameter schemas, error codes, permission models, runtime enums, branded types, testing fixtures, and domain models generated directly from the [shared-foundation](https://github.com/mudrichenkoevgeny/shared-foundation) repository.

        Provides type-safe REST/WS API contracts, Zod validation schemas, route constants, error codes, permission models, testing fixtures/constants, and domain types for Web, Node.js, and TypeScript applications.

        ## Installation

        ```bash
        # pnpm
        pnpm add @mudrichenkoevgeny/shared-foundation zod

        # npm
        npm install @mudrichenkoevgeny/shared-foundation zod

        # yarn
        yarn add @mudrichenkoevgeny/shared-foundation zod
        ```

        ## Features & Exports

        - **Zod Validation Schemas (`*Schema`)**: Auto-generated `z.ZodType` validation schemas for all Request payloads, Response DTOs, and Screen Parameter models (`managementAuditEventListParamsSchema`, `apiErrorResponseSchema`, `userPrivatePayloadSchema`).
        - **Navigation Screen Routes (`*Navigation`)**: Strongly typed screen routes and parent fallback routes for client and management apps (`ClientMainNavigation`, `ManagementMainNavigation`, `ClientAuthNavigation`, `ClientUserNavigation`, `ManagementUsersNavigation`, `ManagementAuditNavigation`, `ManagementSecurityNavigation`, `ManagementSettingsNavigation`).
        - **REST API Routes**: Strongly typed REST API endpoint paths (`ManagementUsersRoutes`, `ManagementAuditRoutes`, `OpenLoginRoutes`, `SelfManagementUserRoutes`).
        - **Runtime Enums (`.value` representation)**: TS types & runtime JS objects for all domain enums and string unions (`UserRole`, `AuditActorType`, `AuditStatus`, `AccountLockoutType`, `ClientType`, `SortOrder`, `UserSortValues`, `TotpStep`, `UnlockMethod`, etc.).
        - **Error & Permission Codes**: Literal string definitions for all application error codes (`SecurityErrorCodes`, `UserErrorCodes`, `CommonErrorCodes`) and permission strings (`UserPermissionCode`, `SecurityPermissionCode`, `AuditPermissionCode`).
        - **Branded Types & Helpers**: Type-safe domain identifiers (`UserId`, `PermissionCode`, `UserSessionId`, `UserIdentifierId`, `AuditEventId`, `ClientDeviceId`) with converter helpers (`toUserIdOrThrow`, `toPermissionCodeOrNull`).
        - **Testing Fixtures & Collection Generators**: Ready-to-use testing constants (`TEST_*`), single-item mock factories (`createTestUserSummary`, `createTestUserPrivatePayload`), auto-calculating `createTestPagedResult<T>`, generic helper `createTestList<T>`, and deterministic collection generators (`createTest<Entity>List`, `createTest<Entity>PayloadList`) with 0-based index IDs, alternating providers/devices, and time shifting (`TEST_TIME_MILLIS + index * 60_000`). Supports static overrides or callback `(index) => Partial<T>`.
        - **Domain Models & Mappers**: Fully typed TS domain models (`UserSessionSummary`, `UserPrivate`, `AuditEventSummary`) and their runtime mappers from/to API payloads (`toUserPrivate`, `toUserPrivatePayload`, `toAuditEventSummary`).
        - **Localization (`SharedFoundationLocalization`, `*ModuleStrings`, `*Strings`)**: Type-safe localization bundle classes, string formatters (`formatLocalizedString`), and JSON resources (`localizationResources`) for `core/common`, `core/security`, and `feature/user`.

        ## Navigation Screen Routes & Parameter Schemas by Module

        All navigation classes and their Zod parameter validation schemas (`*ParamsSchema`) organized by module/subsystem:

        ### 1. `core/common` (Root Navigation)
        - **`ClientMainNavigation.Main`** (`/`) — Screen Params: None
        - **`ManagementMainNavigation.Main`** (`/`) — Screen Params: None

        ### 2. `core/audit` (Audit Navigation)
        - **`ManagementAuditNavigation`**:
          - `Main` (`/audit`) — Redirect to `/audit/events`
          - `AuditEventList` (`/audit/events`) — Zod Schema: `managementAuditEventListParamsSchema`
          - `AuditEventDetail` (`/audit/events/{event_id}`) — Zod Schema: `managementAuditEventDetailParamsSchema`

        ### 3. `core/security` (Security Settings Navigation)
        - **`ManagementSecurityNavigation`**:
          - `SecuritySettings` (`/settings/security`) — Screen Params: None

        ### 4. `core/settings` (Global Settings Navigation)
        - **`ManagementSettingsNavigation`**:
          - `Main` (`/settings`) — Screen Params: None
          - `GlobalSettings` (`/settings/global`) — Screen Params: None

        ### 5. `feature/user` (IAM / User & Auth Navigation)
        - **`ClientAuthNavigation`** (Client Authentication & Registration):
          - `Auth` (`/auth`) — Redirect to `/auth/login`
          - `Main` (`/auth/login`) — Screen Params: None
          - `LoginByEmail` (`/auth/login/email`) — `loginByEmailParamsSchema`
          - `LoginByPhone` (`/auth/login/phone`) — `loginByPhoneParamsSchema`
          - `LoginByTotp` (`/auth/login/totp`) — `loginByTotpParamsSchema`
          - `Registration` (`/auth/registration`) — `clientRegistrationByEmailParamsSchema`
          - `ResetPassword` (`/auth/reset-password`) — `resetPasswordParamsSchema`
          - `PendingDeletion` (`/auth/login/pending-deletion`) — None
          - `UnlockMethodSelection` (`/auth/unlock`) — `unlockMethodSelectionParamsSchema`
          - `UnlockTargetInput` (`/auth/unlock/input`) — `unlockTargetInputParamsSchema`
          - `UnlockOtpInput` (`/auth/unlock/otp`) — `unlockOtpInputParamsSchema`
          - `UnlockSuccess` (`/auth/unlock/success`) — `unlockSuccessParamsSchema`

        - **`ClientUserNavigation`** (Client Self-Service):
          - `Main` (`/user`) — None
          - `IdentifierList` (`/user/identifiers`) — `selfIdentifierListParamsSchema`
          - `IdentifierDetail` (`/user/identifiers/{user_identifier_id}`) — `identifierDetailParamsSchema`
          - `SessionList` (`/user/sessions`) — `selfSessionListParamsSchema`
          - `SessionDetail` (`/user/sessions/{session_id}`) — `sessionDetailParamsSchema`
          - `TotpMain` (`/user/totp`) — `totpMainParamsSchema`
          - `TotpRecoveryCodes` (`/user/totp/recovery-codes`) — None

        - **`ManagementAuthNavigation`** (Staff Authentication):
          - `Auth` (`/auth`) — Redirect to `/auth/login`
          - `Main` (`/auth/login`) — None
          - `LoginByEmail` (`/auth/login/email`) — `loginByEmailParamsSchema`
          - `LoginByTotp` (`/auth/login/totp`) — `loginByTotpParamsSchema`
          - `ResetPassword` (`/auth/reset-password`) — `resetPasswordParamsSchema`
          - `UnlockMethodSelection` (`/auth/unlock`) — `unlockMethodSelectionParamsSchema`
          - `UnlockTargetInput` (`/auth/unlock/input`) — `unlockTargetInputParamsSchema`
          - `UnlockOtpInput` (`/auth/unlock/otp`) — `unlockOtpInputParamsSchema`
          - `UnlockSuccess` (`/auth/unlock/success`) — `unlockSuccessParamsSchema`

        - **`ManagementAuthSettingsNavigation`** (Auth Policy Settings):
          - `AuthSettings` (`/settings/auth`) — None

        - **`ManagementSelfUserNavigation`** (Staff Self-Service):
          - `Main` (`/self/user`) — None
          - `IdentifierList` (`/self/user/identifiers`) — `selfIdentifierListParamsSchema`
          - `IdentifierDetail` (`/self/user/identifiers/{user_identifier_id}`) — `identifierDetailParamsSchema`
          - `SessionList` (`/self/user/sessions`) — `selfSessionListParamsSchema`
          - `SessionDetail` (`/self/user/sessions/{session_id}`) — `sessionDetailParamsSchema`
          - `TotpMain` (`/self/user/totp`) — `totpMainParamsSchema`
          - `TotpRecoveryCodes` (`/self/user/totp/recovery-codes`) — None

        - **`ManagementUsersNavigation`** (Staff User Administration):
          - `UserList` (`/users`) — `managementUserListParamsSchema`
          - `CreateUser` (`/users/create`) — None
          - `UserDetail` (`/users/{user_id}`) — `userDetailParamsSchema`
          - `UserSessionList` (`/users/{user_id}/sessions`) — `managementUserSessionListParamsSchema`
          - `UserIdentifierList` (`/users/{user_id}/identifiers`) — `managementUserIdentifierListParamsSchema`
          - `GlobalSessionList` (`/sessions`) — `managementGlobalSessionListParamsSchema`
          - `GlobalIdentifierList` (`/identifiers`) — `managementGlobalIdentifierListParamsSchema`
          - `IdentifierDetail` (`/identifiers/{user_identifier_id}`) — `identifierDetailParamsSchema`
          - `SessionDetail` (`/sessions/{session_id}`) — `sessionDetailParamsSchema`

        ## Usage Examples

        ### 1. Validating Screen Parameters (Deep Link & URL Router)

        ```typescript
        import { 
          managementAuditEventListParamsSchema, 
          ManagementAuditEventListParamsParser,
          toQueryMap,
          toQuerySearchString,
          ManagementAuditNavigation 
        } from '@mudrichenkoevgeny/shared-foundation';

        // 1. Parsing raw URL search params into typed params
        function parseSearchParams(rawParams: Record<string, unknown>) {
          const params = ManagementAuditEventListParamsParser.parse(rawParams);
          console.log(params.pageNumber); // Defaults to 1 if omitted
          return params;
        }

        // 2. Serializing typed params back to URL query map or search string
        // Automatically omits default values (pageNumber=1, pageSize=20) to keep URLs clean
        const params = { pageNumber: 1, pageSize: 20, actorIds: ['123'] };
        const queryMap = ManagementAuditEventListParamsParser.toQueryMap(params); // { actor_id: ['123'] }
        const searchString = toQuerySearchString(params); // "actor_id=123"

        // 3. Accessing Screen Route & Parent Route for Fallback Back-Navigation
        const screenRoute = ManagementAuditNavigation.AuditEventList.ROUTE; // "/management/audit/events"
        const parentRoute = ManagementAuditNavigation.AuditEventList.PARENT_ROUTE; // "/"
        ```

        ### 2. Issuing API Requests with Route Constants

        ```typescript
        import { 
          ManagementAuditRoutes, 
          AuditApiPaths 
        } from '@mudrichenkoevgeny/shared-foundation';

        // REST API Path for fetching audit event details
        const eventId = '00000000-0000-4000-8000-000000000099';
        const url = ManagementAuditRoutes.GET_AUDIT_EVENT.replace(`{${AuditApiPaths.EVENT_ID}}`, eventId);
        ```

        ### 3. Validating API Responses with Zod & Error Handling

        ```typescript
        import { 
          apiErrorResponseSchema, 
          SecurityErrorCodes, 
          toUserIdOrThrow 
        } from '@mudrichenkoevgeny/shared-foundation';

        async function fetchUserData(rawResponse: unknown) {
          if (!rawResponse) {
            const error = apiErrorResponseSchema.parse(rawResponse);
            if (error.code === SecurityErrorCodes.PASSWORD_TOO_WEAK) {
              console.error('Password does not meet requirements');
            }
            return;
          }

          const userId = toUserIdOrThrow('00000000-0000-4000-8000-000000000001');
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
        val enumCode = generateEnumsJs()
        val compositeParsersJsCode = generateCompositeParsersJs()
        val routeCode = generateRouteObjects()
        val contractCode = generateContractObjects()
        val errorCodeCode = generateErrorCodeObjects()
        val permissionCodeCode = generatePermissionCodeObjects()
        val domainModelsAndMappersCode = generateDomainModelsAndMappersJs()
        val dtoCode = generateDtoSchemasAndTypes()
        val paramParsersJsCode = generateParamParsersAndSerializersJs()
        val testingJsCode = generateTestFixturesJs()
        val testingDtsCode = generateTestFixturesDts()
        val (localizationJsCode, localizationDtsCode) = generateLocalizationCode(rootDir)

        val fullJsContent = """
        import { z } from 'zod';

        $brandedCode

        $enumCode

        $compositeParsersJsCode

        $routeCode

        $contractCode

        $errorCodeCode

        $permissionCodeCode

        $domainModelsAndMappersCode

        $dtoCode

        $paramParsersJsCode

        $testingJsCode

        $localizationJsCode
        """.trimIndent()

        File(outputDir, "index.js").writeText(fullJsContent)
        File(outputDir, "index.mjs").writeText(fullJsContent)

        val dtsContent = generateDeclarationFile(localizationDtsCode, testingDtsCode)
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

private fun generateDeclarationFile(localizationDtsCode: String, testingDtsCode: String): String {
    val routeDeclarations = routeClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val classDoc = extractKDoc(kClass)?.let { "$it\n" } ?: ""
        val structure = extractClassStructure(kClass)

        val directFields = structure.directProps.map { (valName, valValue) ->
            val fieldDoc = extractKDoc(kClass, valName)?.formatAsJsDoc("    ")?.let { "$it\n" } ?: ""
            "${fieldDoc}    readonly $valName: \"$valValue\";"
        }

        val nestedFields = structure.nestedObjects.map { (nestedName, nestedProps) ->
            val nestedDoc = extractKDoc(kClass, nestedClassName = nestedName)?.formatAsJsDoc("    ")?.let { "$it\n" } ?: ""
            val propsCode = nestedProps.joinToString("\n") { (vName, vVal) ->
                val vDoc = extractKDoc(kClass, memberName = vName, nestedClassName = nestedName)?.formatAsJsDoc("      ")?.let { "$it\n" } ?: ""
                "${vDoc}      readonly $vName: \"$vVal\";"
            }
            "${nestedDoc}    readonly $nestedName: {\n$propsCode\n    };"
        }

        val allFields = directFields + nestedFields

        """
        $classDoc export declare const $name: {
        ${allFields.joinToString("\n")}
        };
        """.trimIndent()
    }

    val contractDeclarations = contractClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val classDoc = extractKDoc(kClass)?.let { "$it\n" } ?: ""
        val props = extractObjectStringProperties(kClass)
        val fields = props.map { (valName, valValue) ->
            val fieldDoc = extractKDoc(kClass, valName)?.formatAsJsDoc("    ")?.let { "$it\n" } ?: ""
            "${fieldDoc}    readonly $valName: \"$valValue\";"
        }
        """
        $classDoc export declare const $name: {
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

    export declare const ListingDefaultValues: {
      readonly PAGE_NUMBER: ${ListingDefaultValues.Pagination.PAGE_NUMBER};
      readonly PAGE_SIZE: ${ListingDefaultValues.Pagination.PAGE_SIZE};
      readonly Pagination: {
        readonly PAGE_NUMBER: ${ListingDefaultValues.Pagination.PAGE_NUMBER};
        readonly PAGE_SIZE: ${ListingDefaultValues.Pagination.PAGE_SIZE};
      };
    };

    export declare const UiValues: {
      readonly Placeholder: {
        readonly EMAIL: "${UiValues.Placeholder.EMAIL}";
        readonly TOTP_CODE: "${UiValues.Placeholder.TOTP_CODE}";
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

    export declare const StorageFilterValues: {
      readonly StorageFileFilterValues: {
        readonly FILE_ID: "${StorageFilterValues.StorageFileFilterValues.FILE_ID}";
        readonly OWNER_USER_ID: "${StorageFilterValues.StorageFileFilterValues.OWNER_USER_ID}";
        readonly FILE_NAME: "${StorageFilterValues.StorageFileFilterValues.FILE_NAME}";
        readonly MIME_TYPE: "${StorageFilterValues.StorageFileFilterValues.MIME_TYPE}";
        readonly CATEGORY: "${StorageFilterValues.StorageFileFilterValues.CATEGORY}";
        readonly VISIBILITY: "${StorageFilterValues.StorageFileFilterValues.VISIBILITY}";
        readonly STATUS: "${StorageFilterValues.StorageFileFilterValues.STATUS}";
        readonly TARGET_SCOPE: "${StorageFilterValues.StorageFileFilterValues.TARGET_SCOPE}";
      };
    };
    """.trimIndent()

    val errorDeclarations = errorClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val classDoc = extractKDoc(kClass)?.let { "$it\n" } ?: ""
        val props = extractObjectStringProperties(kClass)
        val fields = props.map { (valName, valValue) ->
            val fieldDoc = extractKDoc(kClass, valName)?.formatAsJsDoc("    ")?.let { "$it\n" } ?: ""
            "${fieldDoc}    readonly $valName: \"$valValue\";"
        }
        """
        $classDoc export declare const $name: {
        ${fields.joinToString("\n")}
        };
        """.trimIndent()
    }

    val permDeclarations = permClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val classDoc = extractKDoc(kClass)?.let { "$it\n" } ?: ""
        val props = extractPermissionCodeProperties(kClass)
        val fields = props.map { (valName, _) ->
            val fieldDoc = extractKDoc(kClass, valName)?.formatAsJsDoc("    ")?.let { "$it\n" } ?: ""
            "${fieldDoc}    readonly $valName: PermissionCode;"
        }
        """
        $classDoc export const $name: {
        ${fields.joinToString(",\n")}
        };
        """.trimIndent()
    }

    val enumDeclarations = getAllEnumInfos().joinToString("\n\n") { info ->
        val enumClass = enumClasses.firstOrNull { (it.simpleName ?: "") == info.name }
        val classDoc = enumClass?.let { extractKDoc(it) }?.let { "$it\n" } ?: ""
        val unionType = info.entries.map { it.second }.distinct().joinToString(" | ") { "'$it'" }
        val objectFields = info.entries.joinToString("\n") { (key, wire) ->
            val entryDoc = enumClass?.let { extractKDoc(it, key) }?.formatAsJsDoc("  ")?.let { "$it\n" } ?: ""
            "${entryDoc}  readonly $key: '$wire';"
        }

        """
        $classDoc export type ${info.name} = $unionType;
        $classDoc export const ${info.name}: {
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

    export type PasswordPolicyValidatorResult =
      | { readonly success: true; readonly reasons: readonly [] }
      | { readonly success: false; readonly reasons: readonly PasswordPolicyFailReason[] };

    export class PasswordPolicyValidator {
      validate(policy: OpenPasswordPolicy | ManagementPasswordPolicy, password: string): PasswordPolicyValidatorResult;
    }

    export declare const passwordPolicyValidator: PasswordPolicyValidator;

    export declare const storageUploadValidator: {
      isFileTypeAccepted(acceptedFileTypes: string[], fileName: string, mimeType?: string | null): boolean;
      isFileSizeValid(maxFileSizeBytes?: number | null, fileSizeBytes?: number): boolean;
      isBatchSizeValid(maxFiles?: number | null, currentCount?: number): boolean;
    };

    ${generateDomainModelsAndMappersDts()}

    ${generateCompositeParsersDts()}

    export type CommonSortValues = 'created_at' | 'updated_at';

    export namespace CommonSortValues {
      export type TimestampSortBy = 'created_at' | 'updated_at';
      export const TimestampSortBy: {
        readonly CREATED_AT: 'created_at';
        readonly UPDATED_AT: 'updated_at';
      };
    }

    export type AuditSortValues = 'created_at';

    export namespace AuditSortValues {
      export type AuditEventSortBy = 'created_at';
      export const AuditEventSortBy: {
        readonly CREATED_AT: 'created_at';
      };
    }

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

    export type StorageSortValues = 'created_at' | 'updated_at' | 'file_name' | 'file_size_bytes';

    export namespace StorageSortValues {
      export type StorageFileSortBy = 'created_at' | 'updated_at' | 'file_name' | 'file_size_bytes';
      export const StorageFileSortBy: {
        readonly CREATED_AT: 'created_at';
        readonly UPDATED_AT: 'updated_at';
        readonly FILE_NAME: 'file_name';
        readonly FILE_SIZE_BYTES: 'file_size_bytes';
      };
    }
    """.trimIndent()

    val dtoDeclarations = dtoClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val classDoc = extractKDoc(kClass)?.let { "$it\n" } ?: ""
        val schemaName = name.decapitalizeFirstLetter() + "Schema"
        val primaryConstructor = kClass.primaryConstructor

        val fields = if (primaryConstructor != null) {
            primaryConstructor.parameters.map { param ->
                val paramName = param.name ?: ""
                val serialNameAnnotation = param.annotations.filterIsInstance<SerialName>().firstOrNull()
                    ?: kClass.declaredMemberProperties.firstOrNull { it.name == paramName }?.findAnnotation<SerialName>()

                val wireKey = serialNameAnnotation?.value ?: paramName
                val tsType = resolveTsType(paramName, param.type, kClass)
                val optionalSign = if (param.isOptional) "?" else ""
                val fieldDoc = extractKDoc(kClass, paramName)?.formatAsJsDoc("    ")?.let { "$it\n" } ?: ""
                "${fieldDoc}    $wireKey$optionalSign: $tsType;"
            }
        } else {
            emptyList()
        }

        """
        $classDoc export interface $name {
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

    export type FileId = string & { readonly __brand: 'FileId' };
    export function toFileIdOrNull(value: string | null | undefined): FileId | null;
    export function toFileIdOrThrow(value: string): FileId;
    export const fileIdSchema: z.ZodType<FileId>;

    export type UploadQueueItemId = string & { readonly __brand: 'UploadQueueItemId' };
    export function toUploadQueueItemIdOrNull(value: string | null | undefined): UploadQueueItemId | null;
    export function toUploadQueueItemIdOrThrow(value: string): UploadQueueItemId;
    export const uploadQueueItemIdSchema: z.ZodType<UploadQueueItemId>;

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

    export function pagedResultSchema<T extends z.ZodTypeAny>(itemSchema: T): z.ZodType<PagedResult<z.infer<T>>>;

    export interface PagedResult<T> {
      items: T[];
      totalCount: number;
      pageNumber: number;
      pageSize: number;
      totalPages: number;
    }

    $dtoDeclarations

    ${generateParamParsersAndSerializersDts()}

    $testingDtsCode

    $localizationDtsCode
    """.trimIndent()
}

private fun parseRootBuildGradleVersion(rootDir: File): String? {
    val buildFile = File(rootDir, "build.gradle.kts")
    if (!buildFile.exists()) return null
    val regex = Regex("""version\s*=\s*["']([^"']+)["']""")
    return buildFile.useLines { lines ->
        lines.mapNotNull { line -> regex.find(line)?.groupValues?.get(1) }.firstOrNull()
    }
}

fun String.decapitalizeFirstLetter(): String {
    if (isEmpty()) return this
    return replaceFirstChar { it.lowercase() }
}
