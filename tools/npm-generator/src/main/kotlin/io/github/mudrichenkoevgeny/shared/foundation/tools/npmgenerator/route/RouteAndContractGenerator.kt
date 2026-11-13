package io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.route

import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.engine.NpmGeneratorConfig
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.util.extractClassStructure
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.util.extractObjectStringProperties
import kotlin.reflect.KClass

/**
 * Generates JavaScript route objects from registered route Kotlin classes.
 */
public fun generateRouteObjects(config: NpmGeneratorConfig? = null): String {
    val targetClasses = config?.routeObjects ?: emptyList()
    return targetClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val structure = extractClassStructure(kClass)

        val directFields = structure.directProps.map { (valName, valValue) -> "  $valName: \"$valValue\"" }
        val nestedFields = structure.nestedObjects.map { (nestedName, nestedProps) ->
            val propsCode = nestedProps.joinToString(",\n    ") { (vName, vVal) -> "$vName: \"$vVal\"" }
            "  $nestedName: {\n    $propsCode\n  }"
        }

        val allFields = directFields + nestedFields

        if (allFields.isEmpty()) {
            "export const $name = {};"
        } else {
            """
export const $name = {
${allFields.joinToString(",\n")}
};
            """.trimIndent()
        }
    }
}

/**
 * Generates TypeScript route declarations from registered route Kotlin classes.
 */
public fun generateRouteDeclarationsDts(config: NpmGeneratorConfig? = null): String {
    val targetClasses = config?.routeObjects ?: emptyList()
    return targetClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val structure = extractClassStructure(kClass)

        val directFields = structure.directProps.map { (valName, _) -> "  readonly $valName: string;" }
        val nestedFields = structure.nestedObjects.map { (nestedName, nestedProps) ->
            val propsCode = nestedProps.joinToString("\n    ") { (vName, _) -> "readonly $vName: string;" }
            "  readonly $nestedName: {\n    $propsCode\n  };"
        }

        val allFields = directFields + nestedFields

        if (allFields.isEmpty()) {
            "export declare const $name: {};"
        } else {
            """
export declare const $name: {
${allFields.joinToString("\n")}
};
            """.trimIndent()
        }
    }
}

/**
 * Generates JavaScript contract objects from registered contract Kotlin classes.
 */
public fun generateContractObjects(config: NpmGeneratorConfig? = null): String {
    val targetClasses = config?.contractClasses ?: emptyList()
    val standardContracts = targetClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val props = extractObjectStringProperties(kClass)
        val fields = props.map { (valName, valValue) -> "  $valName: \"$valValue\"" }

        """
export const $name = {
${fields.joinToString(",\n")}
};
        """.trimIndent()
    }

    val extra = config?.extraConstantsCode
    return if (!extra.isNullOrBlank()) "$standardContracts\n\n$extra" else standardContracts
}

/**
 * Generates TypeScript contract declarations from registered contract Kotlin classes.
 */
public fun generateContractDeclarationsDts(config: NpmGeneratorConfig? = null): String {
    val targetClasses = config?.contractClasses ?: emptyList()
    val standardContracts = targetClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val props = extractObjectStringProperties(kClass)
        val fields = props.map { (valName, valValue) -> "  readonly $valName: \"$valValue\";" }

        """
export declare const $name: {
${fields.joinToString("\n")}
};
        """.trimIndent()
    }

    val extra = config?.extraConstantsDts
    return if (!extra.isNullOrBlank()) "$standardContracts\n\n$extra" else standardContracts
}

/**
 * Generates JavaScript error code objects from registered error Kotlin classes.
 */
public fun generateErrorCodeObjects(config: NpmGeneratorConfig? = null): String {
    val targetClasses = config?.errorClasses ?: emptyList()
    return targetClasses.joinToString("\n\n") { kClass ->
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

/**
 * Generates TypeScript error code declarations from registered error Kotlin classes.
 */
public fun generateErrorCodeDeclarationsDts(config: NpmGeneratorConfig? = null): String {
    val targetClasses = config?.errorClasses ?: emptyList()
    return targetClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val props = extractObjectStringProperties(kClass)
        val fields = props.map { (valName, _) -> "  readonly $valName: string;" }

        """
export declare const $name: {
${fields.joinToString("\n")}
};
        """.trimIndent()
    }
}

/**
 * Extracts constant permission properties and wire values from permission Kotlin classes.
 */
public fun extractConstantPermissionProperties(
    kClass: KClass<*>,
    explicitTypeName: String? = null
): List<Triple<String, String, String>> {
    val instance = kClass.objectInstance ?: return emptyList()
    val fieldResults = extractPermissionFields(kClass, instance, explicitTypeName)
    if (fieldResults.isNotEmpty()) return fieldResults
    return extractPermissionGetters(kClass, instance, explicitTypeName)
}

private fun extractPermissionFields(
    kClass: KClass<*>,
    instance: Any,
    explicitTypeName: String?
): List<Triple<String, String, String>> {
    val results = mutableListOf<Triple<String, String, String>>()
    val fields = kClass.java.declaredFields.filter { field ->
        field.getName() != "INSTANCE" && !field.isSynthetic && (
            if (explicitTypeName != null) {
                field.type.simpleName == explicitTypeName || field.type.getName().endsWith(explicitTypeName)
            } else {
                field.type != String::class.java
            }
        )
    }

    for (field in fields) {
        field.isAccessible = true
        val permObj = field.get(instance) ?: field.get(null) ?: continue
        val typeName = explicitTypeName ?: field.type.simpleName
        val valueProp = try { permObj.javaClass.getMethod("getValue").invoke(permObj) as? String } catch (_: Exception) { null }
            ?: try { permObj.javaClass.getDeclaredField("value").apply { isAccessible = true }.get(permObj) as? String } catch (_: Exception) { null }
        if (valueProp != null) {
            results.add(Triple(field.getName(), valueProp, typeName))
        }
    }
    return results
}

private fun extractPermissionGetters(
    kClass: KClass<*>,
    instance: Any,
    explicitTypeName: String?
): List<Triple<String, String, String>> {
    val results = mutableListOf<Triple<String, String, String>>()
    val methods = kClass.java.declaredMethods.filter { method ->
        if (explicitTypeName != null) {
            (method.returnType.simpleName == explicitTypeName || method.returnType.getName().endsWith(explicitTypeName)) && method.parameterCount == 0
        } else {
            method.returnType != String::class.java && method.parameterCount == 0
        }
    }

    for (method in methods) {
        method.isAccessible = true
        val name = method.getName().removePrefix("get")
        val permObj = method.invoke(instance) ?: continue
        val typeName = explicitTypeName ?: method.returnType.simpleName
        val valueProp = try { permObj.javaClass.getMethod("getValue").invoke(permObj) as? String } catch (_: Exception) { null }
            ?: try { permObj.javaClass.getDeclaredField("value").apply { isAccessible = true }.get(permObj) as? String } catch (_: Exception) { null }
        if (valueProp != null) {
            results.add(Triple(name, valueProp, typeName))
        }
    }
    return results
}

/**
 * Generates JavaScript permission code objects from registered permission Kotlin classes.
 */
public fun generatePermissionCodeObjects(config: NpmGeneratorConfig): String {
    val targetClasses = config.permClasses
    return targetClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val props = extractConstantPermissionProperties(kClass, null)
        val fields = props.map { (valName, valValue, typeName) -> "  $valName: to${typeName}OrThrow(\"$valValue\")" }

        if (fields.isEmpty()) {
            "export const $name = {};"
        } else {
            """
export const $name = {
${fields.joinToString(",\n")}
};
            """.trimIndent()
        }
    }
}

/**
 * Generates TypeScript permission code declarations from registered permission Kotlin classes.
 */
public fun generatePermissionCodeDeclarationsDts(config: NpmGeneratorConfig): String {
    val targetClasses = config.permClasses
    return targetClasses.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val props = extractConstantPermissionProperties(kClass, null)
        val fields = props.map { (valName, _, typeName) -> "  readonly $valName: $typeName;" }

        if (fields.isEmpty()) {
            "export declare const $name: {};"
        } else {
            """
export declare const $name: {
${fields.joinToString("\n")}
};
            """.trimIndent()
        }
    }
}
