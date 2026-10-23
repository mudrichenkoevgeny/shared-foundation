package io.github.mudrichenkoevgeny.shared.foundation.generator.route

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.contractClasses
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.errorClasses
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.extractClassStructure
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.extractObjectStringProperties
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.getAllEnumInfos
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.permClasses
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.routeClasses
import kotlin.reflect.KClass

fun generateRouteObjects(): String {
    return routeClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val structure = extractClassStructure(kClass)

        val directFields = structure.directProps.map { (valName, valValue) -> "  $valName: \"$valValue\"" }
        val nestedFields = structure.nestedObjects.map { (nestedName, nestedProps) ->
            val propsCode = nestedProps.joinToString(",\n    ") { (vName, vVal) -> "$vName: \"$vVal\"" }
            "  $nestedName: {\n    $propsCode\n  }"
        }

        val allFields = directFields + nestedFields

        """
        export const $name = {
        ${allFields.joinToString(",\n")}
        };
        """.trimIndent()
    }
}

fun generateContractObjects(): String {
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

    export const ListingDefaultValues = {
      PAGE_NUMBER: ${ListingDefaultValues.Pagination.PAGE_NUMBER},
      PAGE_SIZE: ${ListingDefaultValues.Pagination.PAGE_SIZE},
      Pagination: {
        PAGE_NUMBER: ${ListingDefaultValues.Pagination.PAGE_NUMBER},
        PAGE_SIZE: ${ListingDefaultValues.Pagination.PAGE_SIZE}
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

    export const StorageFilterValues = {
      StorageFileFilterValues: {
        FILE_ID: "${StorageFilterValues.StorageFileFilterValues.FILE_ID}",
        OWNER_USER_ID: "${StorageFilterValues.StorageFileFilterValues.OWNER_USER_ID}",
        FILE_NAME: "${StorageFilterValues.StorageFileFilterValues.FILE_NAME}",
        MIME_TYPE: "${StorageFilterValues.StorageFileFilterValues.MIME_TYPE}",
        CATEGORY: "${StorageFilterValues.StorageFileFilterValues.CATEGORY}",
        VISIBILITY: "${StorageFilterValues.StorageFileFilterValues.VISIBILITY}",
        STATUS: "${StorageFilterValues.StorageFileFilterValues.STATUS}",
        TARGET_SCOPE: "${StorageFilterValues.StorageFileFilterValues.TARGET_SCOPE}"
      }
    };
    """.trimIndent()

    return "$standardContracts\n\n$listingParamNamesCode"
}

fun generateErrorCodeObjects(): String {
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

fun extractPermissionCodeProperties(kClass: KClass<*>): List<Pair<String, String>> {
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

fun generatePermissionCodeObjects(): String {
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

fun generateEnumsJs(): String {
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

    export const CommonSortValues = {
      TimestampSortBy
    };

    export const AuditSortValues = {
      AuditEventSortBy
    };

    export const StorageSortValues = {
      StorageFileSortBy
    };

    export const UserSortValues = {
      UserSortBy,
      UserIdentifierSortBy,
      UserSessionSortBy
    };
    """.trimIndent()

    return (enumJsBlocks + listOf(extraJs)).joinToString("\n\n")
}
