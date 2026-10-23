package io.github.mudrichenkoevgeny.shared.foundation.generator.schema

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventId
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventSummary
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditSortValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceId
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.CommonSortValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.crypt.DecryptedString
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.crypt.EncryptedString
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordhash.PasswordHash
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicyFailReason
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileId
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFileInternal
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFilePrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFileSummary
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status.StorageFileStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.StorageFilePrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.StorageFileSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.ExternalAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.AccessToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.RefreshToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.RefreshTokenHash
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.totp.TotpStep
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.totp.LoginByTotpMode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.totp.LoginByTotpParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.UnlockMethod
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.dtoClasses
import io.github.mudrichenkoevgeny.shared.foundation.generator.decapitalizeFirstLetter
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonElement
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.primaryConstructor

fun generateBrandedTypes(): String {
    val types = listOf(
        "PermissionCode",
        "UserId",
        "UserIdentifierId",
        "UserSessionId",
        "AuditEventId",
        "FileId",
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

fun generateDtoSchemasAndTypes(): String {
    val pagedResultCode = """
    export function pagedResultSchema(itemSchema) {
      return z.object({
        items: z.array(itemSchema),
        total_count: z.number(),
        page_number: z.number(),
        page_size: z.number(),
        total_pages: z.number()
      }).transform(data => ({
        items: data.items,
        totalCount: data.total_count,
        pageNumber: data.page_number,
        pageSize: data.page_size,
        totalPages: data.total_pages
      }));
    }
    """.trimIndent()

    val generatedDtos = dtoClasses.joinToString("\n\n") { kClass ->
        generateSingleDtoSchema(kClass)
    }

    return "$pagedResultCode\n\n$generatedDtos"
}

fun generateSingleDtoSchema(kClass: KClass<*>): String {
    val name = kClass.simpleName ?: ""
    val schemaName = name.decapitalizeFirstLetter() + "Schema"

    val primaryConstructor = kClass.primaryConstructor ?: return ""

    val fields = primaryConstructor.parameters.map { param ->
        val paramName = param.name ?: ""

        val serialNameAnnotation = param.annotations.filterIsInstance<SerialName>().firstOrNull()
            ?: kClass.declaredMemberProperties.firstOrNull { it.name == paramName }?.findAnnotation<SerialName>()

        val wireKey = serialNameAnnotation?.value ?: paramName

        val type = param.type
        var zodType = resolveZodType(paramName, type, kClass)

        if (param.isOptional) {
            zodType = when {
                zodType.startsWith("z.array(") -> "$zodType.default([])"
                zodType == "z.number()" && paramName == "pageNumber" -> "$zodType.default(ListingDefaultValues.PAGE_NUMBER)"
                zodType == "z.number()" && paramName == "pageSize" -> "$zodType.default(ListingDefaultValues.PAGE_SIZE)"
                zodType.contains(".nullable()") -> "$zodType.default(null)"
                else -> "$zodType.optional()"
            }
        }

        "    $wireKey: $zodType"
    }

    return """
    export const $schemaName = z.object({
    ${fields.joinToString(",\n")}
    });
    """.trimIndent()
}

fun resolveZodType(paramName: String, type: KType, parentClass: KClass<*>): String {
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
        classifier == TotpStep::class -> "totpStepSchema"
        classifier == LoginByTotpMode::class -> "loginByTotpModeSchema"
        classifier == UnlockMethod::class -> "unlockMethodSchema"
        classifier == UserSortValues.UserSortBy::class -> "userSortBySchema"
        classifier == UserSortValues.UserIdentifierSortBy::class -> "userIdentifierSortBySchema"
        classifier == UserSortValues.UserSessionSortBy::class -> "userSessionSortBySchema"
        classifier == AuditSortValues.AuditEventSortBy::class -> "auditEventSortBySchema"
        classifier == CommonSortValues.TimestampSortBy::class -> "timestampSortBySchema"
        classifier == JsonElement::class -> "z.unknown()"

        paramName == "totpStep" -> "totpStepSchema"
        paramName == "loginByTotpMode" || (paramName == "mode" && parentClass == LoginByTotpParams::class) -> "loginByTotpModeSchema"
        classifier == StorageCategory::class -> "storageCategorySchema"
        classifier == StorageFileStatus::class -> "storageFileStatusSchema"
        classifier == StorageFileVisibility::class -> "storageFileVisibilitySchema"
        paramName == "unlockMethod" -> "unlockMethodSchema"
        paramName == "clientType" -> "clientTypeSchema"
        paramName == "deviceId" || paramName == "clientDeviceId" -> "clientDeviceIdSchema"
        paramName == "role" || paramName == "userRole" || paramName == "actorUserRole" -> "userRoleSchema"
        paramName == "accountStatus" || paramName == "accountStatusOnRestore" -> "userAccountStatusSchema"
        paramName == "lockoutType" -> "accountLockoutTypeSchema"
        paramName == "actorType" -> "auditActorTypeSchema"
        paramName == "status" && (parentClass == AuditEventSummaryPayload::class || parentClass == AuditEventPrivatePayload::class) -> "auditStatusSchema"
        paramName == "identifierAuthProvider" || paramName == "userAuthProvider" -> "userAuthProviderSchema"
        paramName == "sensitivity" || paramName == "valueSensitivity" -> "auditValueSensitivitySchema"
        paramName == "permissionCodes" -> "z.array(permissionCodeSchema)"

        classifier == String::class -> {
            when (paramName) {
                "id" -> when (parentClass) {
                    UserSummaryPayload::class, UserPrivatePayload::class, UserSummary::class, UserPrivate::class -> "userIdSchema"
                    UserSessionSummaryPayload::class, UserSessionPrivatePayload::class, UserSessionSummary::class, UserSessionPrivate::class -> "userSessionIdSchema"
                    AuditEventSummaryPayload::class, AuditEventPrivatePayload::class, AuditEventSummary::class, AuditEventPrivate::class -> "auditEventIdSchema"
                    UserIdentifierSummaryPayload::class, UserIdentifierPrivatePayload::class, UserIdentifierSummary::class, UserIdentifierPrivate::class -> "userIdentifierIdSchema"
                    StorageFileSummaryPayload::class, StorageFilePrivatePayload::class, StorageFileSummary::class, StorageFilePrivate::class, StorageFileInternal::class -> "fileIdSchema"
                    else -> "z.string()"
                }
                "fileId" -> "fileIdSchema"
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
        classifier == FileId::class -> "fileIdSchema"
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

fun resolveTsType(paramName: String, type: KType, parentClass: KClass<*>): String {
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
        classifier == TotpStep::class -> "TotpStep"
        classifier == LoginByTotpMode::class -> "LoginByTotpMode"
        classifier == UnlockMethod::class -> "UnlockMethod"
        classifier == UserSortValues.UserSortBy::class -> "UserSortBy"
        classifier == UserSortValues.UserIdentifierSortBy::class -> "UserIdentifierSortBy"
        classifier == UserSortValues.UserSessionSortBy::class -> "UserSessionSortBy"
        classifier == AuditSortValues.AuditEventSortBy::class -> "AuditEventSortBy"
        classifier == CommonSortValues.TimestampSortBy::class -> "TimestampSortBy"
        classifier == JsonElement::class -> "unknown"
        classifier.simpleName == "Instant" -> "Instant"

        paramName == "totpStep" -> "TotpStep"
        paramName == "loginByTotpMode" || (paramName == "mode" && parentClass == LoginByTotpParams::class) -> "LoginByTotpMode"
        paramName == "unlockMethod" -> "UnlockMethod"
        paramName == "clientType" -> "ClientType"
        paramName == "deviceId" || paramName == "clientDeviceId" -> "ClientDeviceId"
        paramName == "role" || paramName == "userRole" || paramName == "actorUserRole" -> "UserRole"
        paramName == "accountStatus" || paramName == "accountStatusOnRestore" -> "UserAccountStatus"
        paramName == "lockoutType" -> "AccountLockoutType"
        paramName == "actorType" -> "AuditActorType"
        paramName == "status" && (parentClass == AuditEventSummaryPayload::class || parentClass == AuditEventPrivatePayload::class) -> "AuditStatus"
        paramName == "identifierAuthProvider" || paramName == "userAuthProvider" -> "UserAuthProvider"
        paramName == "sensitivity" || paramName == "valueSensitivity" -> "AuditValueSensitivity"
        paramName == "permissionCodes" -> "PermissionCode[]"

        classifier == String::class -> {
            when (paramName) {
                "id" -> when (parentClass) {
                    UserSummaryPayload::class, UserPrivatePayload::class, UserSummary::class, UserPrivate::class -> "UserId"
                    UserSessionSummaryPayload::class, UserSessionPrivatePayload::class, UserSessionSummary::class, UserSessionPrivate::class -> "UserSessionId"
                    AuditEventSummaryPayload::class, AuditEventPrivatePayload::class, AuditEventSummary::class, AuditEventPrivate::class -> "AuditEventId"
                    UserIdentifierSummaryPayload::class, UserIdentifierPrivatePayload::class, UserIdentifierSummary::class, UserIdentifierPrivate::class -> "UserIdentifierId"
                    else -> "string"
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
