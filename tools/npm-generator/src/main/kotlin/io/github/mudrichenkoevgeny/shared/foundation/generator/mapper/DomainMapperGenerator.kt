package io.github.mudrichenkoevgeny.shared.foundation.generator.mapper

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.domainToDtoClasses
import io.github.mudrichenkoevgeny.shared.foundation.generator.collector.rawDtoClasses
import io.github.mudrichenkoevgeny.shared.foundation.generator.decapitalizeFirstLetter
import io.github.mudrichenkoevgeny.shared.foundation.generator.doc.extractKDoc
import io.github.mudrichenkoevgeny.shared.foundation.generator.doc.formatAsJsDoc
import io.github.mudrichenkoevgeny.shared.foundation.generator.schema.resolveTsType
import kotlinx.serialization.SerialName
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.primaryConstructor

fun generateCompositeParsersJs(): String {
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

fun generateCompositeParsersDts(): String {
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

fun getWireKey(payloadClass: KClass<*>, paramName: String): String {
    val param = payloadClass.primaryConstructor?.parameters?.firstOrNull { it.name == paramName }
    val serialNameAnnotation = param?.annotations?.filterIsInstance<SerialName>()?.firstOrNull()
        ?: payloadClass.declaredMemberProperties.firstOrNull { it.name == paramName }?.findAnnotation<SerialName>()
    return serialNameAnnotation?.value ?: paramName
}

fun findMatchingPayloadParam(
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
        "userPrivate" -> "userPrivatePayload"
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

fun findMatchingDomainParam(
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
        "userPrivatePayload" -> "userPrivate"
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

fun buildToDomainFields(domainClass: KClass<*>, payloadClass: KClass<*>): String {
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

fun buildToPayloadFields(domainClass: KClass<*>, payloadClass: KClass<*>): String {
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

fun generateDomainModelsAndMappersJs(): String {
    return domainToDtoClasses.joinToString("\n\n") { (domainClass, payloadClass) ->
        val domainName = domainClass.simpleName ?: ""
        val payloadName = payloadClass.simpleName ?: ""

        if (domainName == "AuditEventSummary") {
            return@joinToString """
            export const toAuditEventSummary = (payload, actionTypeParser, resourceTypeParser) => ({
              id: toAuditEventIdOrThrow(payload.id),
              actorType: payload.actor_type,
              action: actionTypeParser.fromValueOrThrow(payload.action),
              resource: resourceTypeParser.fromValueOrThrow(payload.resource),
              status: payload.status,
              createdAt: payload.created_at
            });

            export const toAuditEventSummaryPayload = (domain) => ({
              id: domain.id,
              actor_type: domain.actorType,
              action: domain.action,
              resource: domain.resource,
              status: domain.status,
              created_at: domain.createdAt
            });
            """.trimIndent()
        }

        if (domainName == "AuditEventPrivate") {
            return@joinToString """
            export const toAuditEventPrivate = (payload, actionTypeParser, resourceTypeParser, metadataKeyParser) => ({
              id: toAuditEventIdOrThrow(payload.id),
              actorId: payload.actor_id,
              actorType: payload.actor_type,
              actorUserRole: payload.actor_user_role,
              action: actionTypeParser.fromValueOrThrow(payload.action),
              resource: resourceTypeParser.fromValueOrThrow(payload.resource),
              resourceId: payload.resource_id,
              status: payload.status,
              metadata: payload.metadata ? payload.metadata.map(val => toAuditEventMetadata(val, metadataKeyParser)) : [],
              message: payload.message,
              createdAt: payload.created_at
            });

            export const toAuditEventPrivatePayload = (domain) => ({
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

fun generateDomainModelsAndMappersDts(): String {
    return domainToDtoClasses.joinToString("\n\n") { (domainClass, payloadClass) ->
        val domainName = domainClass.simpleName ?: ""
        val payloadName = payloadClass.simpleName ?: ""
        val domainDoc = extractKDoc(domainClass)?.let { "$it\n" } ?: ""

        val fields = domainClass.primaryConstructor?.parameters?.mapNotNull { param ->
            val paramName = param.name ?: return@mapNotNull null
            val tsType = resolveTsType(paramName, param.type, domainClass)
            val optionalSign = if (param.isOptional) "?" else ""
            val fieldDoc = extractKDoc(domainClass, paramName)?.formatAsJsDoc("  ")?.let { "$it\n" } ?: ""
            "${fieldDoc}  readonly $paramName$optionalSign: $tsType;"
        }?.joinToString("\n") ?: ""

        val toDomainMapperDoc = "/** Converts $payloadName payload DTO to $domainName domain model. */"
        val toPayloadMapperDoc = "/** Converts $domainName domain model to $payloadName payload DTO. */"

        if (domainName == "AuditEventSummary") {
            return@joinToString """
            $domainDoc export interface $domainName {
            $fields
            }

            $toDomainMapperDoc
            export declare const toAuditEventSummary: (payload: AuditEventSummaryPayload, actionTypeParser: CompositeAuditActionTypeParser, resourceTypeParser: CompositeAuditResourceTypeParser) => AuditEventSummary;
            $toPayloadMapperDoc
            export declare const toAuditEventSummaryPayload: (domain: AuditEventSummary) => AuditEventSummaryPayload;
            """.trimIndent()
        }

        if (domainName == "AuditEventPrivate") {
            return@joinToString """
            $domainDoc export interface $domainName {
            $fields
            }

            $toDomainMapperDoc
            export declare const toAuditEventPrivate: (payload: AuditEventPrivatePayload, actionTypeParser: CompositeAuditActionTypeParser, resourceTypeParser: CompositeAuditResourceTypeParser, metadataKeyParser: CompositeAuditMetadataKeyParser) => AuditEventPrivate;
            $toPayloadMapperDoc
            export declare const toAuditEventPrivatePayload: (domain: AuditEventPrivate) => AuditEventPrivatePayload;
            """.trimIndent()
        }

        if (domainName == "AuditEventMetadata") {
            return@joinToString """
            $domainDoc export interface $domainName {
            $fields
            }

            $toDomainMapperDoc
            export declare const toAuditEventMetadata: (payload: AuditEventMetadataPayload, metadataKeyParser: CompositeAuditMetadataKeyParser) => AuditEventMetadata;
            $toPayloadMapperDoc
            export declare const toAuditEventMetadataPayload: (domain: AuditEventMetadata) => AuditEventMetadataPayload;
            """.trimIndent()
        }

        """
        $domainDoc export interface $domainName {
        $fields
        }

        $toDomainMapperDoc
        export declare const to$domainName: (payload: $payloadName) => $domainName;
        $toPayloadMapperDoc
        export declare const to$payloadName: (domain: $domainName) => $payloadName;
        """.trimIndent()
    }
}

fun generateParamParsersAndSerializersJs(): String {
    val paramClasses = rawDtoClasses.filter { kClass ->
        kClass.simpleName?.endsWith("Params") == true
    }

    val globalQueryMapCode = """
    /**
     * Generic parameter serializer converting a JS/TS parameter object into a URL search query map.
     * Omits default values (pageNumber=1, pageSize=20, default enums, empty arrays, nulls, undefined).
     */
    export function toQueryMap(params) {
      if (!params || typeof params !== 'object') return {};
      const res = {};

      for (const [key, val] of Object.entries(params)) {
        if (val === undefined || val === null) continue;

        let queryKey = key;
        if (key === 'pageNumber') {
          if (val === 1 || val === '1') continue;
          queryKey = 'page_number';
        } else if (key === 'pageSize') {
          if (val === 20 || val === '20') continue;
          queryKey = 'page_size';
        } else if (key === 'sortBy') {
          const sVal = String(val).toLowerCase();
          if (sVal === 'default' || sVal === 'none' || sVal === '') continue;
          queryKey = 'sort_by';
        } else if (key === 'sortOrder') {
          const sVal = String(val).toLowerCase();
          if (sVal === 'default' || sVal === 'none' || sVal === '') continue;
          queryKey = 'sort_order';
        } else if (key === 'userRoles') {
          queryKey = 'role';
        } else if (key === 'actorUserRoles') {
          queryKey = 'actor_user_role';
        } else if (key === 'actorIds') {
          queryKey = 'actor_id';
        } else if (key === 'actorTypes') {
          queryKey = 'actor_type';
        } else if (key === 'resourceIds') {
          queryKey = 'resource_id';
        } else if (key === 'userAuthProviders') {
          queryKey = 'user_auth_provider';
        } else if (key === 'identifiers') {
          queryKey = 'identifier';
        } else if (key === 'identifierIds') {
          queryKey = 'identifier_id';
        } else if (key === 'clientTypes') {
          queryKey = 'client_type';
        } else if (key === 'userAgents') {
          queryKey = 'user_agent';
        } else if (key === 'ipAddresses') {
          queryKey = 'ip_address';
        } else if (key === 'languages') {
          queryKey = 'language';
        } else if (key === 'deviceIds') {
          queryKey = 'device_id';
        } else if (key === 'deviceNames') {
          queryKey = 'device_name';
        } else if (key === 'appVersions') {
          queryKey = 'app_version';
        } else if (key === 'osVersions') {
          queryKey = 'operation_system_version';
        } else if (key === 'userIds') {
          queryKey = 'user_id';
        } else if (key === 'redirectUrl') {
          queryKey = 'redirect_url';
        } else if (key === 'mfaToken') {
          queryKey = 'mfa_token';
        } else if (key === 'loginByTotpMode') {
          queryKey = 'login_by_totp_mode';
        } else if (key === 'totpStep') {
          queryKey = 'totp_step';
        } else if (key === 'unlockMethod') {
          queryKey = 'unlock_method';
        } else if (key === 'targetInput') {
          queryKey = 'target_input';
        } else if (key === 'accountStatus') {
          queryKey = 'account_status';
        } else if (key === 'accountStatuses') {
          queryKey = 'account_status';
        } else if (key === 'accountStatusesOnRestore') {
          queryKey = 'account_status_on_restore';
        } else if (key === 'accountLockoutTypes') {
          queryKey = 'account_lockout_type';
        } else if (key === 'authorityLevelFrom') {
          queryKey = 'authority_level_from';
        } else if (key === 'authorityLevelTo') {
          queryKey = 'authority_level_to';
        } else if (key === 'isTotpEnabled') {
          queryKey = 'is_totp_enabled';
        } else if (key === 'permissionCodes') {
          queryKey = 'permission_codes';
        } else if (key === 'phone') {
          queryKey = 'phone_number';
        } else if (key === 'actions') {
          queryKey = 'action';
        } else if (key === 'resources') {
          queryKey = 'resource';
        } else if (key === 'statuses') {
          queryKey = 'status';
        } else if (key === 'messages') {
          queryKey = 'message';
        } else if (key === 'userId' || key === 'identifierId' || key === 'sessionId' || key === 'eventId') {
          continue;
        } else {
          queryKey = key.replace(/([A-Z])/g, '_$1').toLowerCase();
        }

        if (Array.isArray(val)) {
          if (val.length === 0) continue;
          const strVals = val
            .map(v => (v !== undefined && v !== null ? String(v).trim() : ''))
            .filter(v => v.length > 0);
          if (strVals.length > 0) {
            res[queryKey] = strVals;
          }
        } else {
          const strVal = String(val).trim();
          if (strVal.length > 0) {
            res[queryKey] = [strVal];
          }
        }
      }

      return res;
    }

    /**
     * Converts parameter object into a URL search query string.
     */
    export function toQuerySearchString(params) {
      const map = toQueryMap(params);
      const sp = new URLSearchParams();
      for (const [k, vals] of Object.entries(map)) {
        for (const v of vals) {
          if (v !== undefined && v !== null && v !== '') {
            sp.append(k, v);
          }
        }
      }
      return sp.toString();
    }
    """.trimIndent()

    val individualParsersCode = paramClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val schemaName = name.decapitalizeFirstLetter() + "Schema"
        """
        export function serialize$name(params) {
          return toQueryMap(params);
        }

        export function toQueryMap$name(params) {
          return toQueryMap(params);
        }

        export const ${name}Parser = {
          parse(params) {
            return $schemaName.parse(params);
          },
          serialize: serialize$name,
          toQueryMap: serialize$name,
          toSearchString(params) {
            return toQuerySearchString(params);
          }
        };
        """.trimIndent()
    }

    return "$globalQueryMapCode\n\n$individualParsersCode"
}

fun generateParamParsersAndSerializersDts(): String {
    val paramClasses = rawDtoClasses.filter { kClass ->
        kClass.simpleName?.endsWith("Params") == true
    }

    val globalDts = """
    export declare function toQueryMap(params?: Record<string, unknown> | null): Record<string, string[]>;
    export declare function toQuerySearchString(params?: Record<string, unknown> | null): string;
    """.trimIndent()

    val individualDts = paramClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        """
        export declare function serialize$name(params?: Partial<$name> | null): Record<string, string[]>;
        export declare function toQueryMap$name(params?: Partial<$name> | null): Record<string, string[]>;

        export declare const ${name}Parser: {
          parse(params: Record<string, unknown>): $name;
          serialize(params?: Partial<$name> | null): Record<string, string[]>;
          toQueryMap(params?: Partial<$name> | null): Record<string, string[]>;
          toSearchString(params?: Partial<$name> | null): string;
        };
        """.trimIndent()
    }

    return "$globalDts\n\n$individualDts"
}
