package io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.util

import kotlinx.serialization.SerialName
import java.lang.reflect.Modifier
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible

/**
 * Extracts constant string properties from an object or companion object.
 */
public fun extractObjectStringProperties(kClass: KClass<*>): List<Pair<String, String>> {
    return extractAllStringProperties(kClass, kClass.objectInstance)
}

/**
 * Helper extracting all string properties across fields, properties, and companion objects.
 */
public fun extractAllStringProperties(kClass: KClass<*>, instance: Any? = null): List<Pair<String, String>> {
    val propsMap = LinkedHashMap<String, String>()
    extractStaticStringFields(kClass, propsMap)

    val currentInstance = instance ?: kClass.objectInstance
    if (currentInstance != null) {
        extractMemberProperties(currentInstance, propsMap)
    }

    extractCompanionProperties(kClass, propsMap)
    return propsMap.entries.map { it.key to it.value }
}

private fun extractStaticStringFields(kClass: KClass<*>, propsMap: MutableMap<String, String>) {
    val javaClasses = generateSequence(kClass.java) { if (it.superclass != Any::class.java) it.superclass else null }.toList()
    for (clazz in javaClasses.reversed()) {
        for (field in clazz.declaredFields) {
            if (field.name == "INSTANCE" || field.isSynthetic) continue
            if (Modifier.isStatic(field.modifiers) && field.type == String::class.java) {
                tryGetStaticField(field, propsMap)
            }
        }
    }
}

private fun tryGetStaticField(field: java.lang.reflect.Field, propsMap: MutableMap<String, String>) {
    try {
        field.isAccessible = true
        val value = field.get(null) as? String
        if (value != null) {
            propsMap[field.name] = value
        }
    } catch (_: Exception) {}
}

private fun extractMemberProperties(instance: Any, propsMap: MutableMap<String, String>) {
    for (prop in instance::class.memberProperties) {
        if (prop.name in setOf("toString", "hashCode", "equals")) continue
        try {
            prop.isAccessible = true
            val value = prop.getter.call(instance)
            if (value is String) {
                propsMap[prop.name] = value
            }
        } catch (_: Exception) {}
    }
}

private fun extractCompanionProperties(kClass: KClass<*>, propsMap: MutableMap<String, String>) {
    val companion = kClass.companionObjectInstance ?: return
    extractMemberProperties(companion, propsMap)

    val companionJavaClasses = generateSequence(companion::class.java) { if (it.superclass != Any::class.java) it.superclass else null }.toList()
    for (clazz in companionJavaClasses.reversed()) {
        for (field in clazz.declaredFields) {
            if (field.name == "INSTANCE" || field.isSynthetic) continue
            if (field.type == String::class.java) {
                tryGetCompanionField(field, companion, propsMap)
            }
        }
    }
}

private fun tryGetCompanionField(field: java.lang.reflect.Field, companion: Any, propsMap: MutableMap<String, String>) {
    try {
        field.isAccessible = true
        val value = field.get(companion) as? String ?: field.get(null) as? String
        if (value != null) {
            propsMap[field.name] = value
        }
    } catch (_: Exception) {}
}

/**
 * Extracts class structure including direct string properties and nested objects.
 */
public fun extractClassStructure(kClass: KClass<*>): ClassStructure {
    val instance = kClass.objectInstance
    val directProps = extractAllStringProperties(kClass, instance).toMutableList()
    val nestedObjects = mutableListOf<Pair<String, List<Pair<String, String>>>>()

    if (instance != null) {
        extractNestedFromInstanceProps(instance, nestedObjects)
    }

    val seenNestedNames = nestedObjects.map { it.first }.toMutableSet()
    extractNestedFromClassHierarchy(kClass, seenNestedNames, nestedObjects)

    return ClassStructure(directProps.distinctBy { it.first }, nestedObjects)
}

private fun extractNestedFromInstanceProps(
    instance: Any,
    nestedObjects: MutableList<Pair<String, List<Pair<String, String>>>>
) {
    for (prop in instance::class.memberProperties) {
        if (prop.name in setOf("toString", "hashCode", "equals")) continue
        try {
            prop.isAccessible = true
            val valObj = prop.getter.call(instance) ?: continue
            val valClass = valObj::class
            if (valObj !is String && !valClass.java.isPrimitive && !valClass.java.isEnum && valObj !is Collection<*> && valObj !is Map<*, *>) {
                val nestedProps = extractAllStringProperties(valClass, valObj).toMutableList()
                if (nestedProps.isNotEmpty()) {
                    addRouteAliases(nestedProps)
                    nestedObjects.add(prop.name to nestedProps)
                }
            }
        } catch (_: Exception) {}
    }
}

private fun extractNestedFromClassHierarchy(
    kClass: KClass<*>,
    seenNestedNames: MutableSet<String>,
    nestedObjects: MutableList<Pair<String, List<Pair<String, String>>>>
) {
    val allClasses = generateSequence(kClass) {
        val superCls = it.supertypes.firstOrNull()?.classifier as? KClass<*>
        if (superCls != Any::class) superCls else null
    }.toList()

    for (cls in allClasses) {
        for (nested in cls.nestedClasses) {
            val nestedName = nested.simpleName ?: continue
            if (nestedName in seenNestedNames || nestedName == "Companion") continue
            seenNestedNames.add(nestedName)

            val nestedInstance = nested.objectInstance
            val nestedProps = extractAllStringProperties(nested, nestedInstance).toMutableList()

            if (nestedProps.isNotEmpty()) {
                addRouteAliases(nestedProps)
                nestedObjects.add(nestedName to nestedProps)
            }
        }
    }
}

private fun addRouteAliases(props: MutableList<Pair<String, String>>) {
    val hasRoute = props.any { it.first == "route" }
    val hasROUTE = props.any { it.first == "ROUTE" }
    if (hasRoute && !hasROUTE) {
        val routeVal = props.first { it.first == "route" }.second
        props.add("ROUTE" to routeVal)
    } else if (hasROUTE && !hasRoute) {
        val routeVal = props.first { it.first == "ROUTE" }.second
        props.add("route" to routeVal)
    }
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
        val valueProp = try {
            val getter = enumConst.javaClass.methods.firstOrNull { it.name == "getValue" && it.parameterCount == 0 && it.returnType == String::class.java }
            getter?.invoke(enumConst) as? String ?: run {
                val prop = enumConst.javaClass.declaredFields.firstOrNull { it.name == "value" && it.type == String::class.java }
                prop?.isAccessible = true
                prop?.get(enumConst) as? String
            }
        } catch (_: Exception) {
            null
        }
        val wireValue = serialNameAnnot ?: serialNameProp ?: valueProp ?: key
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
