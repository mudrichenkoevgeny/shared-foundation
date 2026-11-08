package io.github.mudrichenkoevgeny.shared.foundation.generator.route

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditEventFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.common.ui.UiValues
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageFileFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserIdentifierFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSessionFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.storage.UserStorageTargetScopes
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.contractClasses
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.errorClasses
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.extractClassStructure
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.extractObjectStringProperties
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.getAllEnumInfos
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.permClasses
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.routeClasses
import kotlin.reflect.KClass

public fun generateRouteObjects(): String = routeClasses.joinToString("\n\n") { kClass ->
    val name = kClass.simpleName ?: ""
    val structure = extractClassStructure(kClass)

    val directFields = structure.directProps.map { (valName, valValue) -> "  $valName: \"$valValue\"" }
    val nestedFields =
        structure.nestedObjects.map { (nestedName, nestedProps) ->
            val propsCode = nestedProps.joinToString(",\n    ") { (vName, vVal) -> "$vName: \"$vVal\"" }
            "  $nestedName: {\n    $propsCode\n  }"
        }

    val allFields = directFields + nestedFields

    if (allFields.isEmpty()) {
        """
export const $name = {};
        """.trimIndent()
    } else {
        """
export const $name = {
${allFields.joinToString(",\n")}
};
        """.trimIndent()
    }
}

public fun generateContractObjects(): String {
    val standardContracts =
        contractClasses.joinToString("\n\n") { kClass ->
            val name = kClass.simpleName ?: ""
            val props = extractObjectStringProperties(kClass)
            val fields = props.map { (valName, valValue) -> "  $valName: \"$valValue\"" }

"""
export const $name = {
${fields.joinToString(",\n")}
};
""".trimIndent()
        }

    val listingParamNamesCode =
"""
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
},
Search: {
SEARCH_QUERY: "${ListingParamNames.Search.SEARCH_QUERY}"
}
};

export const ListingDefaultValues = {
INITIAL_PAGE_NUMBER: ${ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER},
DEFAULT_PAGE_SIZE: ${ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE},
LARGE_PAGE_SIZE: ${ListingDefaultValues.Pagination.LARGE_PAGE_SIZE},
Pagination: {
INITIAL_PAGE_NUMBER: ${ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER},
DEFAULT_PAGE_SIZE: ${ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE},
LARGE_PAGE_SIZE: ${ListingDefaultValues.Pagination.LARGE_PAGE_SIZE}
}
};

export const UiValues = {
Placeholder: {
EMAIL: "${UiValues.Placeholder.EMAIL}",
TOTP_CODE: "${UiValues.Placeholder.TOTP_CODE}"
}
};

// TODO refactor
export const AuditEventFilterValues = {
EVENT_ID: "${AuditEventFilterValues.EVENT_ID}",
ACTOR_ID: "${AuditEventFilterValues.ACTOR_ID}",
ACTOR_TYPE: "${AuditEventFilterValues.ACTOR_TYPE}",
EXCLUDE_ACTOR_TYPE: "${AuditEventFilterValues.EXCLUDE_ACTOR_TYPE}",
ACTOR_USER_ROLE: "${AuditEventFilterValues.ACTOR_USER_ROLE}",
ACTION: "${AuditEventFilterValues.ACTION}",
EXCLUDE_ACTION: "${AuditEventFilterValues.EXCLUDE_ACTION}",
RESOURCE: "${AuditEventFilterValues.RESOURCE}",
RESOURCE_ID: "${AuditEventFilterValues.RESOURCE_ID}",
STATUS: "${AuditEventFilterValues.STATUS}",
EXCLUDE_STATUS: "${AuditEventFilterValues.EXCLUDE_STATUS}",
CREATED_AT_FROM: "${AuditEventFilterValues.CREATED_AT_FROM}",
CREATED_AT_TO: "${AuditEventFilterValues.CREATED_AT_TO}",
SEARCH_QUERY: "${AuditEventFilterValues.SEARCH_QUERY}"
};

export const UserFilterValues = {
USER_ID: "${UserFilterValues.USER_ID}",
ROLE: "${UserFilterValues.ROLE}",
EXCLUDE_ROLE: "${UserFilterValues.EXCLUDE_ROLE}",
ACCOUNT_STATUS: "${UserFilterValues.ACCOUNT_STATUS}",
EXCLUDE_ACCOUNT_STATUS: "${UserFilterValues.EXCLUDE_ACCOUNT_STATUS}",
ACCOUNT_STATUS_ON_RESTORE: "${UserFilterValues.ACCOUNT_STATUS_ON_RESTORE}",
EXCLUDE_ACCOUNT_STATUS_ON_RESTORE: "${UserFilterValues.EXCLUDE_ACCOUNT_STATUS_ON_RESTORE}",
IS_ACCOUNT_STATUS_ON_RESTORE_SET: "${UserFilterValues.IS_ACCOUNT_STATUS_ON_RESTORE_SET}",
AUTHORITY_LEVEL_FROM: "${UserFilterValues.AUTHORITY_LEVEL_FROM}",
AUTHORITY_LEVEL_TO: "${UserFilterValues.AUTHORITY_LEVEL_TO}",
PERMISSION_CODES: "${UserFilterValues.PERMISSION_CODES}",
EXCLUDE_PERMISSION_CODES: "${UserFilterValues.EXCLUDE_PERMISSION_CODES}",
IS_TOTP_ENABLED: "${UserFilterValues.IS_TOTP_ENABLED}",
LAST_LOGIN_AT_FROM: "${UserFilterValues.LAST_LOGIN_AT_FROM}",
LAST_LOGIN_AT_TO: "${UserFilterValues.LAST_LOGIN_AT_TO}",
IS_LAST_LOGIN_AT_SET: "${UserFilterValues.IS_LAST_LOGIN_AT_SET}",
LAST_ACTIVE_AT_FROM: "${UserFilterValues.LAST_ACTIVE_AT_FROM}",
LAST_ACTIVE_AT_TO: "${UserFilterValues.LAST_ACTIVE_AT_TO}",
IS_LAST_ACTIVE_AT_SET: "${UserFilterValues.IS_LAST_ACTIVE_AT_SET}",
CREATED_AT_FROM: "${UserFilterValues.CREATED_AT_FROM}",
CREATED_AT_TO: "${UserFilterValues.CREATED_AT_TO}",
UPDATED_AT_FROM: "${UserFilterValues.UPDATED_AT_FROM}",
UPDATED_AT_TO: "${UserFilterValues.UPDATED_AT_TO}",
IS_UPDATED_AT_SET: "${UserFilterValues.IS_UPDATED_AT_SET}",
SCHEDULED_PERMANENT_DELETION_AT_FROM: "${UserFilterValues.SCHEDULED_PERMANENT_DELETION_AT_FROM}",
SCHEDULED_PERMANENT_DELETION_AT_TO: "${UserFilterValues.SCHEDULED_PERMANENT_DELETION_AT_TO}",
IS_SCHEDULED_PERMANENT_DELETION_AT_SET: "${UserFilterValues.IS_SCHEDULED_PERMANENT_DELETION_AT_SET}",
ACCOUNT_LOCKOUT_TYPE: "${UserFilterValues.ACCOUNT_LOCKOUT_TYPE}",
EXCLUDE_ACCOUNT_LOCKOUT_TYPE: "${UserFilterValues.EXCLUDE_ACCOUNT_LOCKOUT_TYPE}",
TEMPORARY_LOCKOUT_UNTIL_FROM: "${UserFilterValues.TEMPORARY_LOCKOUT_UNTIL_FROM}",
TEMPORARY_LOCKOUT_UNTIL_TO: "${UserFilterValues.TEMPORARY_LOCKOUT_UNTIL_TO}",
IS_TEMPORARY_LOCKOUT_UNTIL_SET: "${UserFilterValues.IS_TEMPORARY_LOCKOUT_UNTIL_SET}",
STORAGE_QUOTA_BYTES_FROM: "${UserFilterValues.STORAGE_QUOTA_BYTES_FROM}",
STORAGE_QUOTA_BYTES_TO: "${UserFilterValues.STORAGE_QUOTA_BYTES_TO}",
IS_STORAGE_QUOTA_BYTES_SET: "${UserFilterValues.IS_STORAGE_QUOTA_BYTES_SET}",
STORAGE_MAX_UPLOAD_SIZE_BYTES_FROM: "${UserFilterValues.STORAGE_MAX_UPLOAD_SIZE_BYTES_FROM}",
STORAGE_MAX_UPLOAD_SIZE_BYTES_TO: "${UserFilterValues.STORAGE_MAX_UPLOAD_SIZE_BYTES_TO}",
IS_STORAGE_MAX_UPLOAD_SIZE_BYTES_SET: "${UserFilterValues.IS_STORAGE_MAX_UPLOAD_SIZE_BYTES_SET}",
STORAGE_MAX_PENDING_UPLOADS_FROM: "${UserFilterValues.STORAGE_MAX_PENDING_UPLOADS_FROM}",
STORAGE_MAX_PENDING_UPLOADS_TO: "${UserFilterValues.STORAGE_MAX_PENDING_UPLOADS_TO}",
IS_STORAGE_MAX_PENDING_UPLOADS_SET: "${UserFilterValues.IS_STORAGE_MAX_PENDING_UPLOADS_SET}",
IS_STORAGE_CONFIGURATION_SET: "${UserFilterValues.IS_STORAGE_CONFIGURATION_SET}",
NICKNAME: "${UserFilterValues.NICKNAME}",
IS_NICKNAME_SET: "${UserFilterValues.IS_NICKNAME_SET}",
DISPLAY_NAME: "${UserFilterValues.DISPLAY_NAME}",
IS_DISPLAY_NAME_SET: "${UserFilterValues.IS_DISPLAY_NAME_SET}",
IS_AVATAR_SET: "${UserFilterValues.IS_AVATAR_SET}",
BIRTHDATE_FROM: "${UserFilterValues.BIRTHDATE_FROM}",
BIRTHDATE_TO: "${UserFilterValues.BIRTHDATE_TO}",
IS_BIRTHDATE_SET: "${UserFilterValues.IS_BIRTHDATE_SET}",
SEARCH_QUERY: "${UserFilterValues.SEARCH_QUERY}"
};

export const UserIdentifierFilterValues = {
IDENTIFIER_ID: "${UserIdentifierFilterValues.IDENTIFIER_ID}",
USER_ID: "${UserIdentifierFilterValues.USER_ID}",
USER_AUTH_PROVIDER: "${UserIdentifierFilterValues.USER_AUTH_PROVIDER}",
EXCLUDE_USER_AUTH_PROVIDER: "${UserIdentifierFilterValues.EXCLUDE_USER_AUTH_PROVIDER}",
IDENTIFIER: "${UserIdentifierFilterValues.IDENTIFIER}",
DISPLAY_NAME: "${UserIdentifierFilterValues.DISPLAY_NAME}",
EXTERNAL_PROVIDER_EMAIL: "${UserIdentifierFilterValues.EXTERNAL_PROVIDER_EMAIL}",
IS_EXTERNAL_PROVIDER_EMAIL_SET: "${UserIdentifierFilterValues.IS_EXTERNAL_PROVIDER_EMAIL_SET}",
CREATED_AT_FROM: "${UserIdentifierFilterValues.CREATED_AT_FROM}",
CREATED_AT_TO: "${UserIdentifierFilterValues.CREATED_AT_TO}",
UPDATED_AT_FROM: "${UserIdentifierFilterValues.UPDATED_AT_FROM}",
UPDATED_AT_TO: "${UserIdentifierFilterValues.UPDATED_AT_TO}",
IS_UPDATED_AT_SET: "${UserIdentifierFilterValues.IS_UPDATED_AT_SET}",
SEARCH_QUERY: "${UserIdentifierFilterValues.SEARCH_QUERY}"
};

export const UserSessionFilterValues = {
SESSION_ID: "${UserSessionFilterValues.SESSION_ID}",
USER_ID: "${UserSessionFilterValues.USER_ID}",
USER_ROLE: "${UserSessionFilterValues.USER_ROLE}",
EXCLUDE_USER_ROLE: "${UserSessionFilterValues.EXCLUDE_USER_ROLE}",
IDENTIFIER: "${UserSessionFilterValues.IDENTIFIER}",
IDENTIFIER_ID: "${UserSessionFilterValues.IDENTIFIER_ID}",
IDENTIFIER_DISPLAY_NAME: "${UserSessionFilterValues.IDENTIFIER_DISPLAY_NAME}",
IDENTIFIER_AUTH_PROVIDER: "${UserSessionFilterValues.IDENTIFIER_AUTH_PROVIDER}",
EXCLUDE_IDENTIFIER_AUTH_PROVIDER: "${UserSessionFilterValues.EXCLUDE_IDENTIFIER_AUTH_PROVIDER}",
CLIENT_TYPE: "${UserSessionFilterValues.CLIENT_TYPE}",
DEVICE_ID: "${UserSessionFilterValues.DEVICE_ID}",
DEVICE_NAME: "${UserSessionFilterValues.DEVICE_NAME}",
APP_VERSION: "${UserSessionFilterValues.APP_VERSION}",
OPERATION_SYSTEM_VERSION: "${UserSessionFilterValues.OPERATION_SYSTEM_VERSION}",
USER_AGENT: "${UserSessionFilterValues.USER_AGENT}",
IS_USER_AGENT_SET: "${UserSessionFilterValues.IS_USER_AGENT_SET}",
IP_ADDRESS: "${UserSessionFilterValues.IP_ADDRESS}",
IS_IP_ADDRESS_SET: "${UserSessionFilterValues.IS_IP_ADDRESS_SET}",
EXPIRES_AT_FROM: "${UserSessionFilterValues.EXPIRES_AT_FROM}",
EXPIRES_AT_TO: "${UserSessionFilterValues.EXPIRES_AT_TO}",
LAST_ACCESSED_AT_FROM: "${UserSessionFilterValues.LAST_ACCESSED_AT_FROM}",
LAST_ACCESSED_AT_TO: "${UserSessionFilterValues.LAST_ACCESSED_AT_TO}",
LAST_REAUTHENTICATED_AT_FROM: "${UserSessionFilterValues.LAST_REAUTHENTICATED_AT_FROM}",
LAST_REAUTHENTICATED_AT_TO: "${UserSessionFilterValues.LAST_REAUTHENTICATED_AT_TO}",
CREATED_AT_FROM: "${UserSessionFilterValues.CREATED_AT_FROM}",
CREATED_AT_TO: "${UserSessionFilterValues.CREATED_AT_TO}",
UPDATED_AT_FROM: "${UserSessionFilterValues.UPDATED_AT_FROM}",
UPDATED_AT_TO: "${UserSessionFilterValues.UPDATED_AT_TO}",
IS_UPDATED_AT_SET: "${UserSessionFilterValues.IS_UPDATED_AT_SET}",
SEARCH_QUERY: "${UserSessionFilterValues.SEARCH_QUERY}"
};

export const StorageFileFilterValues = {
FILE_ID: "${StorageFileFilterValues.FILE_ID}",
OWNER_USER_ID: "${StorageFileFilterValues.OWNER_USER_ID}",
IS_OWNER_USER_ID_SET: "${StorageFileFilterValues.IS_OWNER_USER_ID_SET}",
FILE_NAME: "${StorageFileFilterValues.FILE_NAME}",
FILE_SIZE_BYTES_FROM: "${StorageFileFilterValues.FILE_SIZE_BYTES_FROM}",
FILE_SIZE_BYTES_TO: "${StorageFileFilterValues.FILE_SIZE_BYTES_TO}",
MIME_TYPE: "${StorageFileFilterValues.MIME_TYPE}",
CATEGORY: "${StorageFileFilterValues.CATEGORY}",
EXCLUDE_CATEGORY: "${StorageFileFilterValues.EXCLUDE_CATEGORY}",
VISIBILITY: "${StorageFileFilterValues.VISIBILITY}",
EXCLUDE_VISIBILITY: "${StorageFileFilterValues.EXCLUDE_VISIBILITY}",
STATUS: "${StorageFileFilterValues.STATUS}",
EXCLUDE_STATUS: "${StorageFileFilterValues.EXCLUDE_STATUS}",
TARGET_SCOPE: "${StorageFileFilterValues.TARGET_SCOPE}",
IS_TARGET_SCOPE_SET: "${StorageFileFilterValues.IS_TARGET_SCOPE_SET}",
CREATED_AT_FROM: "${StorageFileFilterValues.CREATED_AT_FROM}",
CREATED_AT_TO: "${StorageFileFilterValues.CREATED_AT_TO}",
UPDATED_AT_FROM: "${StorageFileFilterValues.UPDATED_AT_FROM}",
UPDATED_AT_TO: "${StorageFileFilterValues.UPDATED_AT_TO}",
IS_UPDATED_AT_SET: "${StorageFileFilterValues.IS_UPDATED_AT_SET}",
SEARCH_QUERY: "${StorageFileFilterValues.SEARCH_QUERY}"
};

export const UserStorageTargetScopes = {
AVATAR: "${UserStorageTargetScopes.AVATAR}"
};
""".trimIndent()

    return "$standardContracts\n\n$listingParamNamesCode"
}

public fun generateErrorCodeObjects(): String = errorClasses.joinToString("\n\n") { kClass ->
    val name = kClass.simpleName ?: ""
    val props = extractObjectStringProperties(kClass)
    val fields = props.map { (valName, valValue) -> "  $valName: \"$valValue\"" }

    """
export const $name = {
${fields.joinToString(",\n")}
};
    """.trimIndent()
}

public fun extractPermissionCodeProperties(kClass: KClass<*>): List<Pair<String, String>> {
    val instance = kClass.objectInstance ?: return emptyList()

    val fields =
        kClass.java.declaredFields
            .filter { PermissionCode::class.java.isAssignableFrom(it.type) }
            .mapNotNull { field ->
                field.isAccessible = true
                val permObj =
                    field.get(instance) as? PermissionCode ?: field.get(null) as? PermissionCode
                        ?: return@mapNotNull null
                field.name to permObj.value
            }

    if (fields.isNotEmpty()) return fields

    val methods =
        kClass.java.declaredMethods
            .filter { PermissionCode::class.java.isAssignableFrom(it.returnType) && it.parameterCount == 0 }
            .mapNotNull { method ->
                method.isAccessible = true
                val name = method.name.removePrefix("get")
                val permObj = method.invoke(instance) as? PermissionCode ?: return@mapNotNull null
                name to permObj.value
            }

    return methods
}

public fun generatePermissionCodeObjects(): String = permClasses.joinToString("\n\n") { kClass ->
    val name = kClass.simpleName ?: ""
    val props = extractPermissionCodeProperties(kClass)
    val fields = props.map { (valName, valValue) -> "  $valName: toPermissionCodeOrThrow(\"$valValue\")" }

    """
export const $name = {
${fields.joinToString(",\n")}
};
    """.trimIndent()
}

public fun generateEnumsJs(): String {
    val enumJsBlocks =
        getAllEnumInfos().map { info ->
            val schemaValues =
                info.entries
                    .map { it.second }
                    .distinct()
                    .joinToString(", ") { "\"$it\"" }
            val objectFields = info.entries.joinToString(",\n") { (key, wire) -> "  $key: \"$wire\"" }

"""
export const ${info.schemaName} = z.enum([$schemaValues]);
export const ${info.name} = {
$objectFields
};
""".trimIndent()
        }

    val extraJs =
"""
export const confirmationKeySchema = z.object({
type: confirmationTypeSchema,
identifier: z.string()
});

export class PasswordPolicyValidator {
validate(policy, password) {
if (!policy || typeof password !== 'string') {
return { success: false, reasons: [PasswordPolicyFailReason.TOO_SHORT] };
}
const reasons = [];
const minLength = policy.minLength ?? 0;
if (password.length < minLength) {
reasons.push(PasswordPolicyFailReason.TOO_SHORT);
}
if (policy.requireLetter && !/[a-zA-Z]/.test(password)) {
reasons.push(PasswordPolicyFailReason.NO_LETTER);
}
if (policy.requireUpperCase && !/[A-Z]/.test(password)) {
reasons.push(PasswordPolicyFailReason.NO_UPPERCASE);
}
if (policy.requireLowerCase && !/[a-z]/.test(password)) {
reasons.push(PasswordPolicyFailReason.NO_LOWERCASE);
}
if (policy.requireDigit && !/[0-9]/.test(password)) {
reasons.push(PasswordPolicyFailReason.NO_DIGIT);
}
if (policy.requireSpecialChar && !/[^a-zA-Z0-9]/.test(password)) {
reasons.push(PasswordPolicyFailReason.NO_SPECIAL_CHAR);
}
if (Array.isArray(policy.commonPasswords) && policy.commonPasswords.includes(password)) {
reasons.push(PasswordPolicyFailReason.TOO_COMMON);
}

if (reasons.length > 0) {
return { success: false, reasons };
}
return { success: true, reasons: [] };
}
}

export const passwordPolicyValidator = new PasswordPolicyValidator();

export const storageUploadValidator = {
isFileTypeAccepted: (acceptedFileTypes, fileName, mimeType) => {
if (!Array.isArray(acceptedFileTypes) || acceptedFileTypes.length === 0) return true;
if (!fileName) return false;

const lowerFileName = fileName.toLowerCase();
const lastDot = lowerFileName.lastIndexOf('.');
const extension = lastDot !== -1 ? lowerFileName.substring(lastDot) : '';
const normalizedMimeType = mimeType ? mimeType.toLowerCase().trim() : null;

return acceptedFileTypes.some((rawFilter) => {
if (!rawFilter) return false;
const filter = rawFilter.trim().lowercase();
if (!filter) return false;

if (filter.startsWith('.')) {
return extension === filter || lowerFileName.endsWith(filter);
}
if (filter.endsWith('/*')) {
const mimePrefix = filter.slice(0, -2);
return Boolean(normalizedMimeType && normalizedMimeType.startsWith(mimePrefix + '/'));
}
if (filter.includes('/')) {
return normalizedMimeType === filter;
}
return extension === '.' + filter;
});
},

isFileSizeValid: (maxFileSizeBytes, fileSizeBytes) => {
if (maxFileSizeBytes === null || maxFileSizeBytes === undefined || maxFileSizeBytes <= 0) return true;
if (typeof fileSizeBytes !== 'number' || fileSizeBytes < 0) return false;
return fileSizeBytes <= maxFileSizeBytes;
},

isBatchSizeValid: (maxFiles, currentCount) => {
if (maxFiles === null || maxFiles === undefined || maxFiles <= 0) return typeof currentCount === 'number' && currentCount >= 0;
if (typeof currentCount !== 'number' || currentCount < 0) return false;
return currentCount <= maxFiles;
}
};

export const nicknameValidator = {
validate: (nickname, minLength, maxLength, isNicknameChangeAllowed = true, isNicknameChanging = false) => {
if (nickname === null || nickname === undefined) return null;
if (isNicknameChanging && !isNicknameChangeAllowed) return UserErrorCodes.NICKNAME_CHANGE_DISABLED;
const valStr = String(nickname);
if (valStr.length < minLength) return UserErrorCodes.NICKNAME_TOO_SHORT;
if (valStr.length > maxLength) return UserErrorCodes.NICKNAME_TOO_LONG;
if (!/^[a-zA-Z0-9](?:[._]?[a-zA-Z0-9]+)*$/.test(valStr)) return UserErrorCodes.INVALID_NICKNAME_CHARACTERS;
return null;
}
};

export const displayNameValidator = {
validate: (displayName, minLength, maxLength) => {
if (displayName === null || displayName === undefined) return null;
const valStr = String(displayName);
if (valStr.length < minLength) return UserErrorCodes.DISPLAY_NAME_TOO_SHORT;
if (valStr.length > maxLength) return UserErrorCodes.DISPLAY_NAME_TOO_LONG;
if (/[\x00-\x1F\x7F-\x9F]/.test(valStr)) return UserErrorCodes.INVALID_DISPLAY_NAME_CHARACTERS;
return null;
}
};

export function resolveUserDisplayName(user, fallbackPrefix) {
if (!user) return fallbackPrefix;
const displayName = user.displayName || user.display_name;
if (displayName && String(displayName).trim().length > 0) {
return String(displayName).trim();
}
if (user.nickname && String(user.nickname).trim().length > 0) {
return '@' + String(user.nickname).trim();
}
const rawId = String(user.id || '');
const shortId = rawId.substring(0, 8);
return (fallbackPrefix + ' ' + shortId).trim();
}
""".trimIndent()

    return (enumJsBlocks + listOf(extraJs)).joinToString("\n\n")
}
