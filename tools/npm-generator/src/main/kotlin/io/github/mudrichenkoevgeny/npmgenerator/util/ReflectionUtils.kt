package io.github.mudrichenkoevgeny.npmgenerator.util

import kotlinx.serialization.SerialName
import java.lang.reflect.Modifier
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.primaryConstructor

/**
 * Extracts constant string properties from an object or companion object.
 */
public fun extractObjectStringProperties(kClass: KClass<*>): List<Pair<String, String>> {
    val result = mutableListOf<Pair<String, String>>()

    val staticFields = kClass.java.declaredFields
        .filter { Modifier.isStatic(it.modifiers) && it.type == String::class.java }
        .mapNotNull { field ->
            try {
                field.isAccessible = true
                val valName = field.name
                val valValue = field.get(null) as? String
                if (valValue != null) valName to valValue else null
            } catch (_: Exception) {
                null
            }
        }
    result.addAll(staticFields)

    val companion = kClass.companionObjectInstance
    if (companion != null) {
        val companionProps = companion::class.java.declaredFields
            .filter { it.type == String::class.java }
            .mapNotNull { field ->
                try {
                    field.isAccessible = true
                    val valName = field.name
                    val valValue = field.get(companion) as? String
                    if (valValue != null) valName to valValue else null
                } catch (_: Exception) {
                    null
                }
            }
        result.addAll(companionProps)
    }

    return result.distinctBy { it.first }
}

/**
 * Extracts class structure including direct string properties and nested objects.
 */
public fun extractClassStructure(kClass: KClass<*>): ClassStructure {
    val instance = kClass.objectInstance ?: return ClassStructure(emptyList(), emptyList())
    val directProps = mutableListOf<Pair<String, String>>()
    val nestedObjects = mutableListOf<Pair<String, List<Pair<String, String>>>>()

    val fields = kClass.java.declaredFields
    for (field in fields) {
        if (field.name == "INSTANCE" || field.isSynthetic) continue
        field.isAccessible = true
        val value = try { field.get(instance) } catch (_: Exception) { null } ?: continue

        if (field.type == String::class.java) {
            directProps.add(field.name to (value as String))
        } else if (!field.type.isPrimitive && !field.type.isEnum) {
            val nestedProps = extractObjectStringProperties(value::class)
            if (nestedProps.isNotEmpty()) {
                nestedObjects.add(field.name to nestedProps)
            }
        }
    }

    return ClassStructure(directProps, nestedObjects)
}

/**
 * Extracts enum info and wire value entries from a Kotlin enum class or object.
 */
public fun getEnumInfo(
    kClass: KClass<*>,
    enumEnhancers: Map<String, List<Pair<String, String>>> = emptyMap(),
    customExtractor: ((KClass<*>) -> List<Pair<String, String>>)? = null
): EnumInfo {
    val name = kClass.simpleName ?: ""
    val schemaName = name.decapitalizeFirstLetter() + "Schema"
    val entries = mutableListOf<Pair<String, String>>()

    val extracted = customExtractor?.invoke(kClass)
    if (!extracted.isNullOrEmpty()) {
        entries.addAll(extracted)
    } else {
        val enumEntries = extractStandardEnumEntries(kClass)
        if (enumEntries.isNotEmpty()) {
            entries.addAll(enumEntries)
        } else {
            entries.addAll(extractSealedObjectEntries(kClass))
        }
    }

    val extraPairs = enumEnhancers[name]
    if (extraPairs != null) {
        for ((key, wire) in extraPairs) {
            if (entries.none { it.first == key }) {
                entries.add(key to wire)
            }
        }
    }

    return EnumInfo(name, schemaName, entries)
}

private fun extractStandardEnumEntries(kClass: KClass<*>): List<Pair<String, String>> {
    val enumConstants = kClass.java.enumConstants ?: return emptyList()
    val results = mutableListOf<Pair<String, String>>()
    for (enumConst in enumConstants) {
        val key = (enumConst as Enum<*>).name
        val field = try {
            kClass.java.getField(key)
        } catch (_: Exception) {
            null
        }
        val serialNameAnnot = field?.getAnnotation(SerialName::class.java)?.value
        val serialNameProp = try {
            enumConst.javaClass.getMethod("getSerialName").invoke(enumConst) as? String
        } catch (_: Exception) {
            null
        }
        val wireValue = serialNameAnnot ?: serialNameProp ?: key.lowercase()
        results.add(key to wireValue)
    }
    return results
}

private fun extractSealedObjectEntries(kClass: KClass<*>): List<Pair<String, String>> {
    val results = mutableListOf<Pair<String, String>>()
    try {
        val allProp = kClass.members.firstOrNull { it.name == "all" }
        val allList = allProp?.call(kClass.objectInstance) as? List<*>
        if (allList != null) {
            for (item in allList) {
                if (item == null) continue
                val valueProp = item.javaClass.declaredFields.firstOrNull { it.name == "value" }
                    ?: item.javaClass.declaredFields.firstOrNull { it.type == String::class.java }
                valueProp?.isAccessible = true
                val strValue = valueProp?.get(item) as? String
                if (strValue != null) {
                    results.add(strValue.uppercase() to strValue)
                }
            }
        }
    } catch (_: Exception) {}
    return results
}

/**
 * Topologically sorts DTO classes based on constructor parameters.
 */
public fun sortDtosTopologically(classes: List<KClass<*>>): List<KClass<*>> {
    val classMap = classes.associateBy { it.simpleName }
    val visited = mutableSetOf<KClass<*>>()
    val result = mutableListOf<KClass<*>>()

    fun visit(kClass: KClass<*>) {
        if (kClass in visited) return
        visited.add(kClass)

        val constructor = kClass.primaryConstructor ?: return
        for (param in constructor.parameters) {
            val classifier = param.type.classifier as? KClass<*> ?: continue
            if (classifier in classMap.values && classifier != kClass) {
                visit(classifier)
            }
            for (arg in param.type.arguments) {
                val argClassifier = arg.type?.classifier as? KClass<*> ?: continue
                if (argClassifier in classMap.values && argClassifier != kClass) {
                    visit(argClassifier)
                }
            }
        }

        result.add(kClass)
    }

    for (kClass in classes) {
        visit(kClass)
    }

    return result
}

/**
 * Topologically sorts domain to DTO mapping pairs based on constructor parameters.
 */
public fun sortDomainToDtoTopologically(pairs: List<Pair<KClass<*>, KClass<*>?>>): List<Pair<KClass<*>, KClass<*>?>> {
    val domainMap = pairs.associateBy { it.first.simpleName }
    val visited = mutableSetOf<KClass<*>>()
    val result = mutableListOf<Pair<KClass<*>, KClass<*>?>>()

    fun visit(pair: Pair<KClass<*>, KClass<*>?>) {
        val (domainClass, _) = pair
        if (domainClass in visited) return
        visited.add(domainClass)

        val constructor = domainClass.primaryConstructor ?: return
        for (param in constructor.parameters) {
            val classifier = param.type.classifier as? KClass<*> ?: continue
            val depPair = domainMap[classifier.simpleName]
            if (depPair != null && classifier != domainClass) {
                visit(depPair)
            }
            for (arg in param.type.arguments) {
                val argClassifier = arg.type?.classifier as? KClass<*> ?: continue
                val argDepPair = domainMap[argClassifier.simpleName]
                if (argDepPair != null && argClassifier != domainClass) {
                    visit(argDepPair)
                }
            }
        }

        result.add(pair)
    }

    for (pair in pairs) {
        visit(pair)
    }

    return result
}
