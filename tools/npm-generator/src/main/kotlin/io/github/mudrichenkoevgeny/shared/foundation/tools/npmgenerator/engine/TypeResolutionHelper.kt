package io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.engine

import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.util.decapitalizeFirstLetter
import kotlinx.serialization.json.JsonElement
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.time.Instant

internal fun resolveEnumZod(classifier: KClass<*>, config: NpmGeneratorConfig): String? {
    if (classifier in config.enumClasses || classifier.java.isEnum) {
        return classifier.simpleName?.decapitalizeFirstLetter() + "Schema"
    }
    return null
}

internal fun resolveEnumTs(classifier: KClass<*>, config: NpmGeneratorConfig): String? {
    if (classifier in config.enumClasses || classifier.java.isEnum) {
        return classifier.simpleName
    }
    return null
}

internal fun resolveBrandedZod(
    classifier: KClass<*>,
    paramName: String,
    parentClass: KClass<*>,
    config: NpmGeneratorConfig,
    onTrackImport: (KClass<*>) -> Unit
): String? {
    if (classifier in config.brandedTypes) {
        return classifier.simpleName?.decapitalizeFirstLetter() + "Schema"
    }

    if (classifier == String::class) {
        return resolveStringFieldOverrideZod(paramName, parentClass, config, onTrackImport)
    }

    return resolvePrimitiveZod(classifier)
}

private fun resolveStringFieldOverrideZod(
    paramName: String,
    parentClass: KClass<*>,
    config: NpmGeneratorConfig,
    onTrackImport: (KClass<*>) -> Unit
): String {
    val override = config.stringFieldTypeOverrides["${parentClass.simpleName}.$paramName"]
        ?: config.stringFieldTypeOverrides[paramName]
    if (override != null) {
        onTrackImport(override)
        return override.simpleName?.decapitalizeFirstLetter() + "Schema"
    }

    return "z.string()"
}

private fun resolvePrimitiveZod(classifier: KClass<*>): String? = when (classifier) {
    Int::class, Long::class, Float::class, Double::class, Short::class, Byte::class -> "z.number()"
    Boolean::class -> "z.boolean()"
    Instant::class -> "z.number()"
    JsonElement::class -> "z.unknown()"
    else -> null
}

internal fun resolveBrandedTs(
    classifier: KClass<*>,
    paramName: String,
    parentClass: KClass<*>,
    config: NpmGeneratorConfig,
    importedFoundationDts: MutableSet<String>,
    onTrackImport: (KClass<*>) -> Unit
): String? {
    if (classifier in config.brandedTypes) {
        return classifier.simpleName
    }

    if (classifier == String::class) {
        return resolveStringFieldOverrideTs(paramName, parentClass, config, onTrackImport)
    }

    if (classifier == Instant::class) {
        if (config.basePackagePrefix.isNotEmpty() && config.packageName != config.foundationImportPackage) {
            importedFoundationDts.add("Instant")
        }
        return "Instant"
    }

    return resolvePrimitiveTs(classifier)
}

private fun resolveStringFieldOverrideTs(
    paramName: String,
    parentClass: KClass<*>,
    config: NpmGeneratorConfig,
    onTrackImport: (KClass<*>) -> Unit
): String {
    val override = config.stringFieldTypeOverrides["${parentClass.simpleName}.$paramName"]
        ?: config.stringFieldTypeOverrides[paramName]
    if (override != null) {
        onTrackImport(override)
        return override.simpleName ?: "string"
    }

    return "string"
}

private fun resolvePrimitiveTs(classifier: KClass<*>): String? = when (classifier) {
    Int::class, Long::class, Float::class, Double::class, Short::class, Byte::class -> "number"
    Boolean::class -> "boolean"
    JsonElement::class -> "unknown"
    else -> null
}

internal fun resolveContainerZod(
    type: KType,
    paramName: String,
    parentClass: KClass<*>,
    onResolveZodType: (String, KType, KClass<*>) -> String
): String? {
    val classifier = type.classifier as? KClass<*> ?: return null
    return when {
        classifier == List::class || classifier == Set::class || classifier == Collection::class -> {
            val elementType = type.arguments.firstOrNull()?.type
            val elementZod = if (elementType != null) onResolveZodType(paramName, elementType, parentClass) else "z.unknown()"
            "z.array($elementZod)"
        }
        classifier == Map::class -> {
            val valType = type.arguments.getOrNull(1)?.type
            val valZod = if (valType != null) onResolveZodType(paramName, valType, parentClass) else "z.unknown()"
            "z.record(z.string(), $valZod)"
        }
        else -> null
    }
}

internal fun resolveContainerTs(
    type: KType,
    paramName: String,
    parentClass: KClass<*>,
    onResolveTsType: (String, KType, KClass<*>) -> String
): String? {
    val classifier = type.classifier as? KClass<*> ?: return null
    return when {
        classifier == List::class || classifier == Set::class || classifier == Collection::class -> {
            val elementType = type.arguments.firstOrNull()?.type
            val elementTs = if (elementType != null) onResolveTsType(paramName, elementType, parentClass) else "unknown"
            "$elementTs[]"
        }
        classifier == Map::class -> {
            val valType = type.arguments.getOrNull(1)?.type
            val valTs = if (valType != null) onResolveTsType(paramName, valType, parentClass) else "unknown"
            "Record<string, $valTs>"
        }
        else -> null
    }
}
