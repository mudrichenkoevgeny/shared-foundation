package io.github.mudrichenkoevgeny.shared.foundation.tools.npmgeneratorrunner

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventId
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceId
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.crypt.DecryptedString
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.crypt.EncryptedString
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordhash.PasswordHash
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileId
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status.StorageFileStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.upload.UploadQueueItemId
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.upload.UploadQueueItemStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.ExternalAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.AccessToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.RefreshToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.RefreshTokenHash
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.totp.TotpStep
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.UnlockMethod
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.engine.NpmGeneratorConfig
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.engine.NpmTypeResolver
import java.io.File
import kotlin.reflect.KClass
import kotlin.reflect.KType

internal fun buildFoundationNpmConfig(
    rootDir: File,
    outputDir: File,
    packageVersion: String,
    zodVersion: String,
    typescriptVersion: String,
    repositoryUrl: String = ""
): NpmGeneratorConfig {
    return NpmGeneratorConfig(
        packageName = "@mudrichenkoevgeny/shared-foundation",
        packageVersion = packageVersion,
        packageDescription = "Shared Foundation - TypeScript & Zod contracts and routes",
        repositoryUrl = repositoryUrl,
        peerDependencies = emptyMap(),
        outputDirectory = outputDir,
        sourceCodeDirectory = rootDir,
        localizationJsonDirectory = rootDir,
        testingSourceDirectory = File(rootDir, "testing/src/commonMain/kotlin"),
        dtoClasses = dtoClasses,
        enumClasses = enumClasses.filter { it.simpleName != "CommonLocales" },
        brandedTypes = buildBrandedTypesList(),
        errorClasses = errorClasses,
        permClasses = permClasses,
        routeObjects = routeClasses,
        contractClasses = contractClasses,
        domainToDtoMappers = domainToDtoClasses,
        testFixtures = dtoClasses + domainToDtoClasses.map { it.first },
        stringFieldTypeOverrides = buildStringFieldTypeOverrides(),
        extraConstantsCode = buildExtraConstantsCode(),
        extraConstantsDts = buildExtraConstantsDts(),
        extraJsCode = buildExtraJsCode(),
        extraDtsCode = buildExtraDtsCode(),
        enumEnhancers = buildEnumEnhancersMap(),
        fieldAliases = buildFieldAliasesMap(),
        idMockMappings = buildIdMockMappings(),
        ownerIdMockMappings = buildOwnerIdMockMappings(),
        defaultIdMockMapping = "TEST_USER_ID",
        stringMockMappings = buildStringMockMappings(),
        numberMockMappings = buildNumberMockMappings(),
        brandedTypeMockValues = buildBrandedTypeMockValues(),
        basePackagePrefix = "io.github.mudrichenkoevgeny.shared.foundation",
        foundationImportPackage = "@mudrichenkoevgeny/shared-foundation",
        localizationRootClassName = "SharedFoundationLocalization",
        localizationManagerName = "SharedLocalization",
        excludedLocalizationBundles = setOf("email_templates"),
        excludedTestFixtures = setOf("List", "PagedResult", "PagedResultPayload"),
        preferredEnumDefaultNames = listOf("DEFAULT", "NONE", "ACTIVE", "USER", "SUCCESS"),
        autoImportSymbols = buildAutoImportSymbolsSet(),
        brandedTypesNeedingHelpers = buildBrandedTypesNeedingHelpersSet(),
        customTypeResolver = object : NpmTypeResolver {
            override fun resolveZodType(
                paramName: String,
                type: KType,
                parentClass: KClass<*>,
                resolver: (String, KType, KClass<*>) -> String
            ): String? {
                val classifier = type.classifier as? KClass<*> ?: return null
                if (classifier.simpleName == "PatchField") {
                    val innerType = type.arguments.firstOrNull()?.type ?: return "z.unknown().nullable().optional()"
                    val innerZod = resolver(paramName, innerType, parentClass)
                    return "$innerZod.nullable().optional()"
                }
                return null
            }

            override fun resolveTsType(
                paramName: String,
                type: KType,
                parentClass: KClass<*>,
                resolver: (String, KType, KClass<*>) -> String
            ): String? {
                val classifier = type.classifier as? KClass<*> ?: return null
                if (classifier.simpleName == "PatchField") {
                    val innerType = type.arguments.firstOrNull()?.type ?: return "unknown | null"
                    val innerTs = resolver(paramName, innerType, parentClass)
                    return "$innerTs | null"
                }
                return null
            }

            override fun isSyntheticWrapper(classifier: KClass<*>): Boolean = classifier.simpleName == "PatchField"
        },
        customEnumExtractor = { kClass ->
            if (kClass == ExternalAuthProvider::class) {
                ExternalAuthProvider.all.map { it.userAuthProvider.value.uppercase() to it.userAuthProvider.value }
            } else {
                emptyList()
            }
        },
        customMatchersAndParsersJs = buildPostLocalizationJsCode(),
        customDtsSmokeTestCode = buildCustomDtsSmokeTestCode(),
        customRuntimeSmokeTestCode = buildCustomRuntimeSmokeTestCode(),
        zodVersion = zodVersion,
        typescriptVersion = typescriptVersion
    )
}

private fun buildBrandedTypesList(): List<KClass<*>> = listOf(
    PermissionCode::class,
    UserId::class,
    UserIdentifierId::class,
    UserSessionId::class,
    AuditEventId::class,
    FileId::class,
    UploadQueueItemId::class,
    ClientDeviceId::class,
    DecryptedString::class,
    EncryptedString::class,
    PasswordHash::class,
    AccessToken::class,
    RefreshToken::class,
    RefreshTokenHash::class
)

private fun buildStringFieldTypeOverrides(): Map<String, KClass<*>> = mapOf(
    "actorId" to UserId::class,
    "userId" to UserId::class,
    "identifierId" to UserIdentifierId::class,
    "sessionId" to UserSessionId::class,
    "fileId" to FileId::class,
    "permissionCode" to PermissionCode::class,
    "permissionCodes" to PermissionCode::class,
    "role" to UserRole::class,
    "userRole" to UserRole::class,
    "actorUserRole" to UserRole::class,
    "accountStatus" to UserAccountStatus::class,
    "accountStatusOnRestore" to UserAccountStatus::class,
    "lockoutType" to AccountLockoutType::class,
    "actorType" to AuditActorType::class,
    "sensitivity" to AuditValueSensitivity::class,
    "valueSensitivity" to AuditValueSensitivity::class,
    "AuditEventSummaryPayload.status" to AuditStatus::class,
    "AuditEventPrivatePayload.status" to AuditStatus::class,
    "AuditEventInternal.status" to AuditStatus::class,
    "ManagementAuditEventListParams.status" to AuditStatus::class,
    "StorageFileSummaryPayload.status" to StorageFileStatus::class,
    "StorageFilePrivatePayload.status" to StorageFileStatus::class,
    "StorageFileInternal.status" to StorageFileStatus::class,
    "SelfStorageFileListParams.status" to StorageFileStatus::class,
    "ManagementStorageFileListParams.status" to StorageFileStatus::class,
    "UploadQueueItem.status" to UploadQueueItemStatus::class,
    "clientType" to ClientType::class,
    "totpStep" to TotpStep::class,
    "unlockMethod" to UnlockMethod::class
)

private fun buildEnumEnhancersMap(): Map<String, List<Pair<String, String>>> = mapOf(
    "UserRole" to listOf(
        "USER" to "user",
        "CLIENT_USER" to "user",
        "MANAGEMENT_USER" to "admin"
    ),
    "AccountLockoutType" to listOf(
        "PERMANENT" to "indefinite"
    )
)

private fun buildFieldAliasesMap(): Map<String, String> = mapOf(
    "deviceInfo" to "clientDeviceInfo",
    "userPrivate" to "userPrivatePayload",
    "sessionToken" to "sessionTokenPayload"
)

private fun buildIdMockMappings(): Map<String, String> = mapOf(
    "userId" to "TEST_USER_ID",
    "actorId" to "TEST_USER_ID",
    "sessionId" to "TEST_SESSION_ID",
    "identifierId" to "TEST_IDENTIFIER_ID",
    "auditEventId" to "TEST_AUDIT_EVENT_ID",
    "fileId" to "TEST_FILE_ID",
    "clientDeviceId" to "TEST_CLIENT_DEVICE_ID"
)

private fun buildOwnerIdMockMappings(): Map<String, String> = mapOf(
    "UserIdentifier" to "TEST_IDENTIFIER_ID",
    "UserSession" to "TEST_SESSION_ID",
    "DeletedSession" to "TEST_SESSION_ID",
    "ActiveSession" to "TEST_SESSION_ID",
    "AuditEvent" to "TEST_AUDIT_EVENT_ID",
    "StorageFile" to "TEST_FILE_ID",
    "File" to "TEST_FILE_ID",
    "User" to "TEST_USER_ID"
)

private fun buildStringMockMappings(): Map<String, String> = mapOf(
    "email" to "TEST_EMAIL",
    "phone" to "TEST_PHONE",
    "phoneNumber" to "TEST_PHONE",
    "ipAddress" to "TEST_IP_ADDRESS",
    "userAgent" to "TEST_USER_AGENT",
    "code" to "\"123456\"",
    "totpCode" to "\"123456\"",
    "identifier" to "TEST_EMAIL",
    "title" to "\"Test Title\"",
    "description" to "\"Test Description\"",
    "reason" to "\"Test Reason\"",
    "url" to "\"https://example.com\"",
    "avatarUrl" to "\"https://example.com/avatar.png\""
)

private fun buildNumberMockMappings(): Map<String, String> = mapOf(
    "createdAt" to "TEST_TIME_MILLIS",
    "updatedAt" to "TEST_TIME_MILLIS",
    "lastActiveAt" to "TEST_TIME_MILLIS",
    "lastLoginAt" to "TEST_TIME_MILLIS",
    "expiresAt" to "TEST_TIME_MILLIS",
    "temporaryLockoutUntil" to "TEST_TIME_MILLIS"
)

private fun buildBrandedTypeMockValues(): Map<String, String> = mapOf(
    "PermissionCode" to "TEST_PERMISSION_CODE",
    "DecryptedString" to "TEST_DECRYPTED_STRING",
    "EncryptedString" to "TEST_ENCRYPTED_STRING",
    "PasswordHash" to "TEST_PASSWORD_HASH",
    "AccessToken" to "TEST_ACCESS_TOKEN",
    "RefreshToken" to "TEST_REFRESH_TOKEN",
    "RefreshTokenHash" to "TEST_REFRESH_TOKEN_HASH"
)

private fun buildAutoImportSymbolsSet(): Set<String> = setOf(
    "UserId",
    "toUserIdOrThrow",
    "toUserIdOrNull",
    "UserIdentifierId",
    "toUserIdentifierIdOrThrow",
    "UserSessionId",
    "toUserSessionIdOrThrow",
    "AuditEventId",
    "toAuditEventIdOrThrow",
    "FileId",
    "toFileIdOrThrow",
    "PermissionCode",
    "toPermissionCodeOrThrow",
    "ClientDeviceId",
    "toClientDeviceIdOrThrow",
    "DecryptedString",
    "toDecryptedStringOrThrow",
    "EncryptedString",
    "toEncryptedStringOrThrow",
    "PasswordHash",
    "toPasswordHashOrThrow",
    "AccessToken",
    "toAccessTokenOrThrow",
    "RefreshToken",
    "toRefreshTokenOrThrow",
    "RefreshTokenHash",
    "toRefreshTokenHashOrThrow",
    "pagedResultSchema",
    "toQueryMap",
    "toQuerySearchString",
    "formatLocalizedString",
    "defaultLocalizedStringResolver",
    "createTestList",
    "TEST_USER_ID",
    "TEST_IDENTIFIER_ID",
    "TEST_SESSION_ID",
    "TEST_AUDIT_EVENT_ID",
    "TEST_FILE_ID",
    "TEST_CLIENT_DEVICE_ID",
    "TEST_EMAIL",
    "TEST_PHONE",
    "TEST_IP_ADDRESS",
    "TEST_USER_AGENT",
    "TEST_TIME_MILLIS"
)

private fun buildBrandedTypesNeedingHelpersSet(): Set<String> = setOf(
    "UserId",
    "UserIdentifierId",
    "UserSessionId",
    "AuditEventId",
    "FileId",
    "ClientDeviceId",
    "PermissionCode",
    "DecryptedString",
    "EncryptedString",
    "PasswordHash",
    "AccessToken",
    "RefreshToken",
    "RefreshTokenHash"
)
