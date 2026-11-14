package io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.mapper

import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.doc.formatAsJsDoc
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.engine.NpmGeneratorConfig
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.engine.NpmGeneratorEngine
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.util.decapitalizeFirstLetter
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.util.sortDomainToDtoTopologically
import kotlinx.serialization.SerialName
import java.io.File
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.primaryConstructor

/**
 * Generates custom composite parser JavaScript code from configuration.
 */
public fun generateCompositeParsersJs(config: NpmGeneratorConfig? = null): String {
    return config?.customMatchersAndParsersJs ?: ""
}

/**
 * Generates custom composite parser TypeScript declaration code from configuration.
 */
public fun generateCompositeParsersDts(config: NpmGeneratorConfig? = null): String {
    return config?.customMatchersAndParsersDts ?: ""
}

/**
 * Returns wire key for a parameter based on `@SerialName` annotation or fallback parameter name.
 */
public fun getWireKey(
    payloadClass: KClass<*>,
    paramName: String
): String {
    val param = payloadClass.primaryConstructor?.parameters?.firstOrNull { it.name == paramName }
    val serialNameAnnotation =
        param?.annotations?.filterIsInstance<SerialName>()?.firstOrNull()
            ?: payloadClass.declaredMemberProperties.firstOrNull { it.name == paramName }?.findAnnotation<SerialName>()
    return serialNameAnnotation?.value ?: paramName
}

/**
 * Finds matching payload parameter for a given domain parameter name and type.
 */
public fun findMatchingPayloadParam(
    domainParamName: String,
    domainParamType: KType,
    payloadClass: KClass<*>,
    domainToDtoPairs: List<Pair<KClass<*>, KClass<*>?>> = emptyList(),
    fieldAliases: Map<String, String> = emptyMap()
): Pair<String, KType>? {
    val primaryParams = payloadClass.primaryConstructor?.parameters ?: return null
    val domainClassifier = domainParamType.classifier as? KClass<*>

    val exact =
        primaryParams.firstOrNull {
            it.name == domainParamName ||
                it.annotations
                    .filterIsInstance<SerialName>()
                    .firstOrNull()
                    ?.value == domainParamName
        }
    if (exact != null) return (exact.name ?: domainParamName) to exact.type

    val aliasName = fieldAliases[domainParamName]
    if (aliasName != null) {
        val aliasMatch =
            primaryParams.firstOrNull {
                it.name == aliasName ||
                    it.annotations
                        .filterIsInstance<SerialName>()
                        .firstOrNull()
                        ?.value == aliasName
            }
        if (aliasMatch != null) return (aliasMatch.name ?: aliasName) to aliasMatch.type
    }

    val targetPayloadClass = domainToDtoPairs.firstOrNull { it.first == domainClassifier }?.second
    if (targetPayloadClass != null) {
        val typeMatch = primaryParams.firstOrNull { (it.type.classifier as? KClass<*>) == targetPayloadClass }
        if (typeMatch != null) return (typeMatch.name ?: "") to typeMatch.type
    }

    return null
}

/**
 * Finds matching domain parameter for a given payload parameter name and type.
 */
public fun findMatchingDomainParam(
    payloadParamName: String,
    payloadParamType: KType,
    domainClass: KClass<*>,
    domainToDtoPairs: List<Pair<KClass<*>, KClass<*>?>> = emptyList(),
    fieldAliases: Map<String, String> = emptyMap()
): Pair<String, KType>? {
    val primaryParams = domainClass.primaryConstructor?.parameters ?: return null
    val payloadClassifier = payloadParamType.classifier as? KClass<*>

    val exact = primaryParams.firstOrNull { it.name == payloadParamName }
    if (exact != null) return (exact.name ?: payloadParamName) to exact.type

    val aliasName = fieldAliases.entries.firstOrNull { it.value == payloadParamName }?.key
    if (aliasName != null) {
        val aliasMatch = primaryParams.firstOrNull { it.name == aliasName }
        if (aliasMatch != null) return (aliasMatch.name ?: aliasName) to aliasMatch.type
    }

    val targetDomainClass = domainToDtoPairs.firstOrNull { it.second == payloadClassifier }?.first
    if (targetDomainClass != null) {
        val typeMatch = primaryParams.firstOrNull { (it.type.classifier as? KClass<*>) == targetDomainClass }
        if (typeMatch != null) return (typeMatch.name ?: "") to typeMatch.type
    }

    return null
}

/**
 * Builds field mapping expressions for converting payload DTO object to domain model.
 */
public fun buildToDomainFields(
    domainClass: KClass<*>,
    payloadClass: KClass<*>,
    domainToDtoPairs: List<Pair<KClass<*>, KClass<*>?>> = emptyList(),
    config: NpmGeneratorConfig? = null
): String {
    val aliases = config?.fieldAliases ?: emptyMap()
    val fields = domainClass.primaryConstructor?.parameters?.mapNotNull { domainParam ->
        val domainParamName = domainParam.name ?: return@mapNotNull null
        val match = findMatchingPayloadParam(domainParamName, domainParam.type, payloadClass, domainToDtoPairs, aliases)

        if (match == null) {
            return@mapNotNull "  $domainParamName: null"
        }

        val (payloadParamName, _) = match
        val wireKey = getWireKey(payloadClass, payloadParamName)
        val payloadRef = "payload.$wireKey"
        val mappedValue = resolveDomainFieldValue(payloadRef, domainParam.type, domainToDtoPairs, config)

        "  $domainParamName: $mappedValue"
    }

    return fields?.joinToString(",\n") ?: ""
}

private fun resolveDomainFieldValue(
    payloadRef: String,
    type: KType,
    domainToDtoPairs: List<Pair<KClass<*>, KClass<*>?>>,
    config: NpmGeneratorConfig?
): String {
    val classifier = type.classifier as? KClass<*>
    val isNullable = type.isMarkedNullable
    val typeName = classifier?.simpleName ?: ""

    val nestedDomainMatch = domainToDtoPairs.firstOrNull {
        it.first == classifier || it.second == classifier
    }
    val isBranded = config?.brandedTypes?.contains(classifier) == true ||
        typeName in (config?.brandedTypesNeedingHelpers ?: emptySet()) ||
        typeName.endsWith("Id")

    return when {
        nestedDomainMatch != null -> {
            val nestedDomainName = nestedDomainMatch.first.simpleName
            if (isNullable) {
                "$payloadRef ? to$nestedDomainName($payloadRef) : null"
            } else {
                "to$nestedDomainName($payloadRef)"
            }
        }
        isBranded -> {
            val brandName = classifier?.simpleName ?: ""
            if (isNullable) {
                "$payloadRef ? to${brandName}OrThrow($payloadRef) : null"
            } else {
                "to${brandName}OrThrow($payloadRef)"
            }
        }
        classifier == Set::class || classifier == List::class -> {
            resolveCollectionDomainFieldValue(payloadRef, type, domainToDtoPairs, config)
        }
        else -> payloadRef
    }
}

private fun resolveCollectionDomainFieldValue(
    payloadRef: String,
    type: KType,
    domainToDtoPairs: List<Pair<KClass<*>, KClass<*>?>>,
    config: NpmGeneratorConfig?
): String {
    val elemType = type.arguments.firstOrNull()?.type
    val elemClassifier = elemType?.classifier as? KClass<*>
    val elemTypeName = elemClassifier?.simpleName ?: ""
    val elemNestedDomainMatch = domainToDtoPairs.firstOrNull {
        it.first == elemClassifier || it.second == elemClassifier
    }
    val elemIsBranded = config?.brandedTypes?.contains(elemClassifier) == true ||
        elemTypeName in (config?.brandedTypesNeedingHelpers ?: emptySet()) ||
        elemTypeName.endsWith("Id")

    return when {
        elemNestedDomainMatch != null -> {
            val nestedDomainName = elemNestedDomainMatch.first.simpleName
            "Array.isArray($payloadRef) ? $payloadRef.map(to$nestedDomainName) : []"
        }
        elemIsBranded -> {
            val brandName = elemClassifier?.simpleName ?: ""
            "Array.isArray($payloadRef) ? $payloadRef.map(to${brandName}OrThrow) : []"
        }
        else -> "$payloadRef ?? []"
    }
}

/**
 * Builds field mapping expressions for converting domain model object to payload DTO.
 */
public fun buildToPayloadFields(
    domainClass: KClass<*>,
    payloadClass: KClass<*>,
    domainToDtoPairs: List<Pair<KClass<*>, KClass<*>?>> = emptyList(),
    config: NpmGeneratorConfig? = null
): String {
    val aliases = config?.fieldAliases ?: emptyMap()
    val fields =
        payloadClass.primaryConstructor?.parameters?.mapNotNull { payloadParam ->
            val payloadParamName = payloadParam.name ?: return@mapNotNull null
            val wireKey = getWireKey(payloadClass, payloadParamName)
            val match = findMatchingDomainParam(payloadParamName, payloadParam.type, domainClass, domainToDtoPairs, aliases)

            if (match == null) {
                return@mapNotNull "  $wireKey: null"
            }

            val (domainParamName, _) = match
            val domainRef = "domain.$domainParamName"
            val mappedValue = resolvePayloadFieldValue(domainRef, payloadParam.type, domainToDtoPairs)

            "  $wireKey: $mappedValue"
        }

    return fields?.joinToString(",\n") ?: ""
}

private fun resolvePayloadFieldValue(
    domainRef: String,
    type: KType,
    domainToDtoPairs: List<Pair<KClass<*>, KClass<*>?>>
): String {
    val classifier = type.classifier as? KClass<*>
    val isNullable = type.isMarkedNullable

    val nestedPayloadMatch = domainToDtoPairs.firstOrNull {
        it.first == classifier || it.second == classifier
    }
    return when {
        nestedPayloadMatch != null -> {
            val nestedPayloadName = nestedPayloadMatch.second?.simpleName
            if (nestedPayloadName != null) {
                if (isNullable) {
                    "$domainRef ? to$nestedPayloadName($domainRef) : null"
                } else {
                    "to$nestedPayloadName($domainRef)"
                }
            } else {
                if (isNullable) {
                    "$domainRef ?? null"
                } else {
                    domainRef
                }
            }
        }
        classifier == Set::class || classifier == List::class -> {
            val elemType = type.arguments.firstOrNull()?.type
            val elemClassifier = elemType?.classifier as? KClass<*>
            val elemNestedPayloadMatch = domainToDtoPairs.firstOrNull {
                it.first == elemClassifier || it.second == elemClassifier
            }
            val nestedPayloadName = elemNestedPayloadMatch?.second?.simpleName
            if (nestedPayloadName != null) {
                "Array.isArray($domainRef) ? $domainRef.map(to$nestedPayloadName) : []"
            } else {
                if (isNullable) {
                    "$domainRef ?? null"
                } else {
                    domainRef
                }
            }
        }
        else -> {
            if (isNullable) {
                "$domainRef ?? null"
            } else {
                domainRef
            }
        }
    }
}

/**
 * Generates JavaScript domain model mappers code for all registered pairs.
 */
public fun generateDomainModelsAndMappersJs(config: NpmGeneratorConfig? = null): String {
    val pairs = config?.domainToDtoMappers?.let { sortDomainToDtoTopologically(it) } ?: emptyList()
    val hasAuditMappers = pairs.any { it.first.simpleName in setOf("AuditEventMetadata", "AuditEventSummary", "AuditEventPrivate") }

    val parseValueHelper = if (hasAuditMappers) {
        """
const parseValueWithParser = (val, parser) => {
  if (val === undefined || val === null) return val;
  if (typeof parser === 'function') {
    const res = parser(val);
    return res !== undefined && res !== null ? res : val;
  }
  if (parser && typeof parser === 'object') {
    if (typeof parser.fromValueOrThrow === 'function') {
      try { return parser.fromValueOrThrow(val); } catch (e) { return val; }
    }
    if (typeof parser.parseOrNull === 'function') {
      const res = parser.parseOrNull(val);
      return res !== undefined && res !== null ? res : val;
    }
  }
  return val;
};
        """.trimIndent() + "\n\n"
    } else {
        ""
    }

    val mappersCode = pairs.joinToString("\n\n") { (domainClass, payloadClass) ->
        val domainName = domainClass.simpleName ?: ""
        val payloadName = payloadClass?.simpleName

        when (domainName) {
            "AuditEventMetadata" -> {
                """
export const toAuditEventMetadata = (payload, metadataKeyParser) => ({
  key: parseValueWithParser(payload?.key, metadataKeyParser),
  value: payload?.value ?? "",
  sensitivity: payload?.sensitivity
});

export const toAuditEventMetadataPayload = (domain) => ({
  key: domain?.key ?? "",
  value: domain?.value ?? "",
  sensitivity: domain?.sensitivity
});
                """.trimIndent()
            }
            "AuditEventSummary" -> {
                """
export const toAuditEventSummary = (payload, actionTypeParser, resourceTypeParser) => ({
  eventId: payload?.event_id ? toAuditEventIdOrThrow(payload.event_id) : payload?.event_id,
  createdAt: payload?.created_at,
  actorId: payload?.actor_id ? toUserIdOrThrow(payload.actor_id) : null,
  actorType: payload?.actor_type,
  actorUserRole: payload?.actor_user_role ?? null,
  action: parseValueWithParser(payload?.action, actionTypeParser),
  resourceId: payload?.resource_id ?? null,
  resourceType: parseValueWithParser(payload?.resource_type, resourceTypeParser),
  status: payload?.status
});

export const toAuditEventSummaryPayload = (domain) => ({
  event_id: domain?.eventId ?? "",
  created_at: domain?.createdAt ?? "",
  actor_id: domain?.actorId ?? null,
  actor_type: domain?.actorType,
  actor_user_role: domain?.actorUserRole ?? null,
  action: domain?.action,
  resource_id: domain?.resourceId ?? null,
  resource_type: domain?.resourceType,
  status: domain?.status
});
                """.trimIndent()
            }
            "AuditEventPrivate" -> {
                """
export const toAuditEventPrivate = (payload, actionTypeParser, resourceTypeParser, metadataKeyParser) => ({
  eventId: payload?.event_id ? toAuditEventIdOrThrow(payload.event_id) : payload?.event_id,
  createdAt: payload?.created_at,
  actorId: payload?.actor_id ? toUserIdOrThrow(payload.actor_id) : null,
  actorType: payload?.actor_type,
  actorUserRole: payload?.actor_user_role ?? null,
  action: parseValueWithParser(payload?.action, actionTypeParser),
  resourceId: payload?.resource_id ?? null,
  resourceType: parseValueWithParser(payload?.resource_type, resourceTypeParser),
  status: payload?.status,
  metadata: Array.isArray(payload?.metadata) ? payload.metadata.map(m => toAuditEventMetadata(m, metadataKeyParser)) : []
});

export const toAuditEventPrivatePayload = (domain) => ({
  event_id: domain?.eventId ?? "",
  created_at: domain?.createdAt ?? "",
  actor_id: domain?.actorId ?? null,
  actor_type: domain?.actorType,
  actor_user_role: domain?.actorUserRole ?? null,
  action: domain?.action,
  resource_id: domain?.resourceId ?? null,
  resource_type: domain?.resourceType,
  status: domain?.status,
  metadata: Array.isArray(domain?.metadata) ? domain.metadata.map(toAuditEventMetadataPayload) : []
});
                """.trimIndent()
            }
            else -> {
                if (payloadName == null) {
                    val toDomainMapperName = "to$domainName"
                    """
export const $toDomainMapperName = (payload) => ({
  ...payload
});
                    """.trimIndent()
                } else {
                    val toDomainMapperName = "to$domainName"
                    val toPayloadMapperName = "to${payloadName}"
                    val toDomainFields = buildToDomainFields(domainClass, payloadClass, pairs, config)
                    val toPayloadFields = buildToPayloadFields(domainClass, payloadClass, pairs, config)

                    """
export const $toDomainMapperName = (payload) => ({
$toDomainFields
});

export const $toPayloadMapperName = (domain) => ({
$toPayloadFields
});
                    """.trimIndent()
                }
            }
        }
    }

    return parseValueHelper + mappersCode
}

/**
 * Generates TypeScript domain interface declarations and mapper type signatures.
 */
public fun generateDomainModelsAndMappersDts(config: NpmGeneratorConfig? = null, engine: NpmGeneratorEngine? = null): String {
    val pairs = config?.domainToDtoMappers?.let { sortDomainToDtoTopologically(it) } ?: emptyList()
    val docExtractor = engine?.kDocExtractor

    return pairs.joinToString("\n\n") { (domainClass, payloadClass) ->
        val domainName = domainClass.simpleName ?: ""
        val classDoc = docExtractor?.extractKDoc(domainClass)?.let { "$it\n" } ?: ""
        val primaryConstructor = domainClass.primaryConstructor

        val fields =
            if (primaryConstructor != null) {
                primaryConstructor.parameters.map { param ->
                    val paramName = param.name ?: ""
                    val tsType = engine?.resolveTsType(paramName, param.type, domainClass)
                        ?: "unknown"
                    val optionalSign = if (param.isOptional) "?" else ""
                    val fieldDoc = docExtractor?.extractKDoc(domainClass, paramName)?.formatAsJsDoc("    ")?.let { "$it\n" } ?: ""
                    "$fieldDoc    readonly $paramName$optionalSign: $tsType;"
                }
            } else {
                emptyList()
            }

        val interfaceDecl =
            """
${classDoc}export interface $domainName {
${fields.joinToString("\n")}
}
            """.trimIndent()

        when (domainName) {
            "AuditEventMetadata" -> {
                """
$interfaceDecl

export declare function toAuditEventMetadata(payload: AuditEventMetadataPayload, metadataKeyParser?: AuditMetadataKeyParser | CompositeAuditMetadataKeyParser | ((value: string) => AuditMetadataKey | null)): AuditEventMetadata;
export declare const toAuditEventMetadataPayload: (domain: AuditEventMetadata) => AuditEventMetadataPayload;
                """.trimIndent()
            }
            "AuditEventSummary" -> {
                """
$interfaceDecl

export declare function toAuditEventSummary(payload: AuditEventSummaryPayload, actionTypeParser?: AuditActionTypeParser | CompositeAuditActionTypeParser | ((value: string) => AuditActionType | null), resourceTypeParser?: AuditResourceTypeParser | CompositeAuditResourceTypeParser | ((value: string) => AuditResourceType | null)): AuditEventSummary;
export declare const toAuditEventSummaryPayload: (domain: AuditEventSummary) => AuditEventSummaryPayload;
                """.trimIndent()
            }
            "AuditEventPrivate" -> {
                """
$interfaceDecl

export declare function toAuditEventPrivate(payload: AuditEventPrivatePayload, actionTypeParser?: AuditActionTypeParser | CompositeAuditActionTypeParser | ((value: string) => AuditActionType | null), resourceTypeParser?: AuditResourceTypeParser | CompositeAuditResourceTypeParser | ((value: string) => AuditResourceType | null), metadataKeyParser?: AuditMetadataKeyParser | CompositeAuditMetadataKeyParser | ((value: string) => AuditMetadataKey | null)): AuditEventPrivate;
export declare const toAuditEventPrivatePayload: (domain: AuditEventPrivate) => AuditEventPrivatePayload;
                """.trimIndent()
            }
            else -> {
                if (payloadClass == null) {
                    val toDomainMapperName = "to$domainName"
                    """
$interfaceDecl

export declare const $toDomainMapperName: (payload: Record<string, unknown>) => $domainName;
                    """.trimIndent()
                } else {
                    val payloadName = payloadClass.simpleName ?: ""
                    val toDomainMapperName = "to$domainName"
                    val toPayloadMapperName = "to${payloadName}"

                    """
$interfaceDecl

export declare const $toDomainMapperName: (payload: $payloadName) => $domainName;
export declare const $toPayloadMapperName: (domain: $domainName) => $payloadName;
                    """.trimIndent()
                }
            }
        }
    }
}

/**
 * Generates JavaScript parameter parser and serializer helper objects.
 */
public fun generateParamParsersAndSerializersJs(config: NpmGeneratorConfig? = null): String {
    val targetClasses = config?.dtoClasses ?: emptyList()
    val paramClasses = targetClasses.filter { kClass ->
        kClass.simpleName?.endsWith("Params") == true
    }

    val isFoundation = config == null || config.basePackagePrefix.isEmpty() || (config.foundationImportPackage.isNotEmpty() && config.packageName == config.foundationImportPackage)
    val hasCustomQueryMap = config?.extraJsCode?.contains("function toQueryMap") == true

    val queryUtilsJs = if (isFoundation && paramClasses.isNotEmpty() && !hasCustomQueryMap) {
        """
export function toQueryMap(params) {
  if (!params || typeof params !== 'object') return {};
  const res = {};
  for (const [key, val] of Object.entries(params)) {
    if (val === undefined || val === null) continue;
    let queryKey = key.replace(/([A-Z])/g, '_$1').toLowerCase();
    if (Array.isArray(val)) {
      if (val.length === 0) continue;
      const strVals = val.map(v => (v !== undefined && v !== null ? String(v).trim() : '')).filter(v => v.length > 0);
      if (strVals.length > 0) res[queryKey] = strVals;
    } else {
      const strVal = String(val).trim();
      if (strVal.length > 0) res[queryKey] = [strVal];
    }
  }
  return res;
}

export function toQuerySearchString(params) {
  const map = toQueryMap(params);
  const sp = new URLSearchParams();
  for (const [k, vals] of Object.entries(map)) {
    for (const v of vals) {
      if (v !== undefined && v !== null && v !== '') sp.append(k, v);
    }
  }
  return sp.toString();
}
        """.trimIndent() + "\n\n"
    } else {
        ""
    }

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

    return queryUtilsJs + individualParsersCode
}

/**
 * Generates TypeScript parameter parser and serializer type declarations.
 */
public fun generateParamParsersAndSerializersDts(config: NpmGeneratorConfig? = null): String {
    val targetClasses = config?.dtoClasses ?: emptyList()
    val paramClasses = targetClasses.filter { kClass ->
        kClass.simpleName?.endsWith("Params") == true
    }

    val isFoundation = config == null || config.basePackagePrefix.isEmpty() || (config.foundationImportPackage.isNotEmpty() && config.packageName == config.foundationImportPackage)
    val hasCustomQueryMapDts = config?.extraDtsCode?.contains("function toQueryMap") == true

    val queryUtilsDts = if (isFoundation && paramClasses.isNotEmpty() && !hasCustomQueryMapDts) {
        """
export declare function toQueryMap(params?: Record<string, unknown> | null): Record<string, string[]>;
export declare function toQuerySearchString(params?: Record<string, unknown> | null): string;

        """.trimIndent()
    } else {
        ""
    }

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

    return queryUtilsDts + individualDts
}
