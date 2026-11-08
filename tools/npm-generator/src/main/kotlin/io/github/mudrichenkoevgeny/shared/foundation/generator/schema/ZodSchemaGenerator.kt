package io.github.mudrichenkoevgeny.shared.foundation.generator.schema

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventId
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventSummary
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditEventSortBy
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceId
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.CommonSortValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.patch.PatchField
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
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.upload.UploadQueueItemId
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
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserIdentifierSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSessionSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortBy
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
import kotlin.time.Instant

public fun generateBrandedTypes(): String {
    val types =
        listOf(
            "PermissionCode",
            "UserId",
            "UserIdentifierId",
            "UserSessionId",
            "AuditEventId",
            "FileId",
            "UploadQueueItemId",
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
    throw new Error('$name value must not be blank.');
  }
  return result;
}

export const $schemaName = z.string().transform((val, ctx) => {
  const result = to${name}OrNull(val);
  if (!result) {
    ctx.addIssue({ code: z.ZodIssueCode.custom, message: 'Invalid $name' });
    return z.NEVER;
  }
  return result;
});
        """.trimIndent()
    }
}

public fun generateDtoSchemasAndTypes(): String {
    val pagedResultCode =
        """
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

    val generatedDtos =
        dtoClasses.joinToString("\n\n") { kClass ->
            generateSingleDtoSchema(kClass)
        }

    return "$pagedResultCode\n\n$generatedDtos"
}

public fun generateSingleDtoSchema(kClass: KClass<*>): String {
    val name = kClass.simpleName ?: ""
    val schemaName = name.decapitalizeFirstLetter() + "Schema"

    val primaryConstructor = kClass.primaryConstructor ?: return ""

    val fields =
        primaryConstructor.parameters.map { param ->
            val paramName = param.name ?: ""

            val serialNameAnnotation =
                param.annotations.filterIsInstance<SerialName>().firstOrNull()
                    ?: kClass.declaredMemberProperties
                        .firstOrNull {
                            it.name == paramName
                        }?.findAnnotation<SerialName>()

            val wireKey = serialNameAnnotation?.value ?: paramName

            val type = param.type
            var zodType = resolveZodType(paramName, type, kClass)

            if (param.isOptional) {
                zodType =
                    when {
                        zodType.startsWith("z.array(") -> "$zodType.default([])"
                        zodType == "z.number()" && paramName == "pageNumber" ->
                            "$zodType.default(ListingDefaultValues.INITIAL_PAGE_NUMBER)"
                        zodType == "z.number()" && paramName == "pageSize" ->
                            "$zodType.default(ListingDefaultValues.DEFAULT_PAGE_SIZE)"
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

public fun resolveZodType(
    paramName: String,
    type: KType,
    parentClass: KClass<*>
): String {
    val classifier = type.classifier as? KClass<*> ?: return "z.unknown()"
    val isMarkedNullable = type.isMarkedNullable

    val baseZod = resolveDomainEnumZod(classifier, paramName, parentClass)
        ?: resolveBrandedOrSpecialZod(classifier, paramName, parentClass)
        ?: resolveContainerZod(type, paramName, parentClass)
        ?: (classifier.simpleName?.decapitalizeFirstLetter() + "Schema")

    return if (isMarkedNullable) "$baseZod.nullable()" else baseZod
}

private fun resolveDomainEnumZod(
    classifier: KClass<*>,
    paramName: String,
    parentClass: KClass<*>
): String? = when {
    classifier == UserAuthProvider::class ||
        paramName == "identifierAuthProvider" ||
        paramName == "userAuthProvider" -> "userAuthProviderSchema"
    classifier == ExternalAuthProvider::class -> "externalAuthProviderSchema"
    classifier == UserRole::class ||
        paramName == "role" ||
        paramName == "userRole" ||
        paramName == "actorUserRole" -> "userRoleSchema"
    classifier == UserAccountStatus::class ||
        paramName == "accountStatus" ||
        paramName == "accountStatusOnRestore" -> "userAccountStatusSchema"
    classifier == AccountLockoutType::class ||
        paramName == "lockoutType" -> "accountLockoutTypeSchema"
    classifier == AuditActorType::class ||
        paramName == "actorType" -> "auditActorTypeSchema"
    classifier == AuditValueSensitivity::class ||
        paramName == "sensitivity" ||
        paramName == "valueSensitivity" -> "auditValueSensitivitySchema"
    classifier == AuditStatus::class ||
        (
            paramName == "status" &&
                (parentClass == AuditEventSummaryPayload::class || parentClass == AuditEventPrivatePayload::class)
        ) -> "auditStatusSchema"
    classifier == CommonAuditMetadataKey::class -> "commonAuditMetadataKeySchema"
    classifier == UserAuditMetadataKey::class -> "userAuditMetadataKeySchema"
    classifier == ClientType::class || paramName == "clientType" -> "clientTypeSchema"
    classifier == SortOrder::class -> "sortOrderSchema"
    classifier == PasswordPolicyFailReason::class -> "passwordPolicyFailReasonSchema"
    classifier == TotpStep::class || paramName == "totpStep" -> "totpStepSchema"
    classifier.simpleName == "LoginByTotpMode" ||
        paramName == "loginByTotpMode" ||
        (paramName == "mode" && parentClass.simpleName == "LoginByTotpParams") -> "loginByTotpModeSchema"
    classifier == UnlockMethod::class || paramName == "unlockMethod" -> "unlockMethodSchema"
    classifier == UserSortBy::class -> "userSortBySchema"
    classifier == UserIdentifierSortBy::class -> "userIdentifierSortBySchema"
    classifier == UserSessionSortBy::class -> "userSessionSortBySchema"
    classifier == AuditEventSortBy::class -> "auditEventSortBySchema"
    classifier == CommonSortValues::class -> "CommonSortValuesSchema"
    classifier == StorageCategory::class -> "storageCategorySchema"
    classifier == StorageFileStatus::class -> "storageFileStatusSchema"
    classifier == StorageFileVisibility::class -> "storageFileVisibilitySchema"
    else -> null
}

private fun resolveBrandedOrSpecialZod(
    classifier: KClass<*>,
    paramName: String,
    parentClass: KClass<*>
): String? = when {
    paramName == "deviceId" ||
        paramName == "clientDeviceId" ||
        classifier == ClientDeviceId::class -> "clientDeviceIdSchema"
    paramName == "permissionCodes" -> "z.array(permissionCodeSchema)"
    classifier == String::class -> resolveStringIdZod(paramName, parentClass)
    classifier == Int::class ||
        classifier == Long::class ||
        classifier == Float::class ||
        classifier == Double::class ||
        classifier == Short::class ||
        classifier == Byte::class -> "z.number()"
    classifier == Boolean::class -> "z.boolean()"
    classifier == PermissionCode::class -> "permissionCodeSchema"
    classifier == UserId::class -> "userIdSchema"
    classifier == UserIdentifierId::class -> "userIdentifierIdSchema"
    classifier == UserSessionId::class -> "userSessionIdSchema"
    classifier == AuditEventId::class -> "auditEventIdSchema"
    classifier == FileId::class -> "fileIdSchema"
    classifier == UploadQueueItemId::class -> "uploadQueueItemIdSchema"
    classifier == DecryptedString::class -> "decryptedStringSchema"
    classifier == EncryptedString::class -> "encryptedStringSchema"
    classifier == PasswordHash::class -> "passwordHashSchema"
    classifier == AccessToken::class -> "accessTokenSchema"
    classifier == RefreshToken::class -> "refreshTokenSchema"
    classifier == RefreshTokenHash::class -> "refreshTokenHashSchema"
    classifier == Instant::class -> "z.number()"
    classifier == JsonElement::class -> "z.unknown()"
    else -> null
}

private fun resolveStringIdZod(
    paramName: String,
    parentClass: KClass<*>
): String = when (paramName) {
    "id" -> when (parentClass) {
        UserSummaryPayload::class, UserPrivatePayload::class,
        UserSummary::class, UserPrivate::class -> "userIdSchema"
        UserSessionSummaryPayload::class, UserSessionPrivatePayload::class,
        UserSessionSummary::class, UserSessionPrivate::class -> "userSessionIdSchema"
        AuditEventSummaryPayload::class, AuditEventPrivatePayload::class,
        AuditEventSummary::class, AuditEventPrivate::class -> "auditEventIdSchema"
        UserIdentifierSummaryPayload::class, UserIdentifierPrivatePayload::class,
        UserIdentifierSummary::class, UserIdentifierPrivate::class -> "userIdentifierIdSchema"
        StorageFileSummaryPayload::class, StorageFilePrivatePayload::class,
        StorageFileSummary::class, StorageFilePrivate::class,
        StorageFileInternal::class -> "fileIdSchema"
        else -> "z.string()"
    }
    "fileId" -> "fileIdSchema"
    "userId", "actorId" -> "userIdSchema"
    "identifierId" -> "userIdentifierIdSchema"
    "sessionId" -> "userSessionIdSchema"
    "permissionCode" -> "permissionCodeSchema"
    else -> "z.string()"
}

private fun resolveContainerZod(
    type: KType,
    paramName: String,
    parentClass: KClass<*>
): String? {
    val classifier = type.classifier as? KClass<*> ?: return null
    return when {
        classifier == List::class ||
            classifier == Set::class ||
            classifier == Collection::class ||
            classifier.simpleName == "Set" ||
            classifier.simpleName == "List" ||
            classifier.simpleName == "Collection" -> {
            val elementType = type.arguments.firstOrNull()?.type
            val elementZod = if (elementType !=
                null
            ) {
                resolveZodType(paramName, elementType, parentClass)
            } else {
                "z.unknown()"
            }
            "z.array($elementZod)"
        }
        classifier == PatchField::class -> {
            val innerType = type.arguments.firstOrNull()?.type
            val innerZod = if (innerType != null) resolveZodType(paramName, innerType, parentClass) else "z.unknown()"
            "$innerZod.nullable().optional()"
        }
        classifier == Map::class || classifier.simpleName == "Map" -> {
            val valType = type.arguments.getOrNull(1)?.type
            val valZod = if (valType != null) resolveZodType(paramName, valType, parentClass) else "z.unknown()"
            "z.record(z.string(), $valZod)"
        }
        else -> null
    }
}

public fun resolveTsType(
    paramName: String,
    type: KType,
    parentClass: KClass<*>
): String {
    val classifier = type.classifier as? KClass<*> ?: return "unknown"
    val isMarkedNullable = type.isMarkedNullable

    val baseTs = resolveDomainEnumTs(classifier, paramName, parentClass)
        ?: resolveBrandedOrPrimitiveTs(classifier, paramName, parentClass)
        ?: resolveContainerTs(type, paramName, parentClass)
        ?: (classifier.simpleName ?: "unknown")

    return if (isMarkedNullable) "$baseTs | null" else baseTs
}

private fun resolveDomainEnumTs(
    classifier: KClass<*>,
    paramName: String,
    parentClass: KClass<*>
): String? = when {
    classifier == UserAuthProvider::class ||
        paramName == "identifierAuthProvider" ||
        paramName == "userAuthProvider" -> "UserAuthProvider"
    classifier == ExternalAuthProvider::class -> "ExternalAuthProvider"
    classifier == UserRole::class ||
        paramName == "role" ||
        paramName == "userRole" ||
        paramName == "actorUserRole" -> "UserRole"
    classifier == UserAccountStatus::class ||
        paramName == "accountStatus" ||
        paramName == "accountStatusOnRestore" -> "UserAccountStatus"
    classifier == AccountLockoutType::class ||
        paramName == "lockoutType" -> "AccountLockoutType"
    classifier == AuditActorType::class ||
        paramName == "actorType" -> "AuditActorType"
    classifier == AuditValueSensitivity::class ||
        paramName == "sensitivity" ||
        paramName == "valueSensitivity" -> "AuditValueSensitivity"
    classifier == AuditStatus::class ||
        (
            paramName == "status" &&
                (parentClass == AuditEventSummaryPayload::class || parentClass == AuditEventPrivatePayload::class)
        ) -> "AuditStatus"
    classifier == CommonAuditMetadataKey::class -> "CommonAuditMetadataKey"
    classifier == UserAuditMetadataKey::class -> "UserAuditMetadataKey"
    classifier == ClientType::class || paramName == "clientType" -> "ClientType"
    classifier == SortOrder::class -> "SortOrder"
    classifier == PasswordPolicyFailReason::class -> "PasswordPolicyFailReason"
    classifier == TotpStep::class || paramName == "totpStep" -> "TotpStep"
    classifier.simpleName == "LoginByTotpMode" ||
        paramName == "loginByTotpMode" ||
        (paramName == "mode" && parentClass.simpleName == "LoginByTotpParams") -> "LoginByTotpMode"
    classifier == UnlockMethod::class || paramName == "unlockMethod" -> "UnlockMethod"
    classifier == UserSortBy::class -> "UserSortBy"
    classifier == UserIdentifierSortBy::class -> "UserIdentifierSortBy"
    classifier == UserSessionSortBy::class -> "UserSessionSortBy"
    classifier == AuditEventSortBy::class -> "AuditEventSortBy"
    classifier == CommonSortValues::class -> "CommonSortValues"
    else -> null
}

private fun resolveBrandedOrPrimitiveTs(
    classifier: KClass<*>,
    paramName: String,
    parentClass: KClass<*>
): String? = when {
    paramName == "deviceId" ||
        paramName == "clientDeviceId" ||
        classifier == ClientDeviceId::class -> "ClientDeviceId"
    paramName == "permissionCodes" -> "PermissionCode[]"
    classifier == String::class -> resolveStringIdTs(paramName, parentClass)
    classifier == Int::class ||
        classifier == Long::class ||
        classifier == Float::class ||
        classifier == Double::class ||
        classifier == Short::class ||
        classifier == Byte::class -> "number"
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
    classifier == Instant::class -> "Instant"
    classifier == JsonElement::class -> "unknown"
    else -> null
}

private fun resolveStringIdTs(
    paramName: String,
    parentClass: KClass<*>
): String = when (paramName) {
    "id" -> when (parentClass) {
        UserSummaryPayload::class, UserPrivatePayload::class,
        UserSummary::class, UserPrivate::class -> "UserId"
        UserSessionSummaryPayload::class, UserSessionPrivatePayload::class,
        UserSessionSummary::class, UserSessionPrivate::class -> "UserSessionId"
        AuditEventSummaryPayload::class, AuditEventPrivatePayload::class,
        AuditEventSummary::class, AuditEventPrivate::class -> "AuditEventId"
        UserIdentifierSummaryPayload::class, UserIdentifierPrivatePayload::class,
        UserIdentifierSummary::class, UserIdentifierPrivate::class -> "UserIdentifierId"
        else -> "string"
    }
    "userId", "actorId" -> "UserId"
    "identifierId" -> "UserIdentifierId"
    "sessionId" -> "UserSessionId"
    "permissionCode" -> "PermissionCode"
    else -> "string"
}

private fun resolveContainerTs(
    type: KType,
    paramName: String,
    parentClass: KClass<*>
): String? {
    val classifier = type.classifier as? KClass<*> ?: return null
    return when {
        classifier == List::class ||
            classifier == Set::class ||
            classifier == Collection::class ||
            classifier.simpleName == "Set" ||
            classifier.simpleName == "List" ||
            classifier.simpleName == "Collection" -> {
            val elementType = type.arguments.firstOrNull()?.type
            val elementTs = if (elementType != null) resolveTsType(paramName, elementType, parentClass) else "unknown"
            "$elementTs[]"
        }
        classifier == PatchField::class -> {
            val innerType = type.arguments.firstOrNull()?.type
            val innerTs = if (innerType != null) resolveTsType(paramName, innerType, parentClass) else "unknown"
            "$innerTs | null"
        }
        classifier == Map::class || classifier.simpleName == "Map" -> {
            val valType = type.arguments.getOrNull(1)?.type
            val valTs = if (valType != null) resolveTsType(paramName, valType, parentClass) else "unknown"
            "Record<string, $valTs>"
        }
        else -> null
    }
}
