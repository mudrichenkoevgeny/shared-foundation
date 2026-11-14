package io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.testing

import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.engine.NpmGeneratorConfig
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.mapper.getWireKey
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.util.decapitalizeFirstLetter
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.util.getEnumInfo
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.primaryConstructor

/**
 * Extracts property names and types from a class primary constructor.
 */
public fun extractClassProperties(kClass: KClass<*>): List<Pair<String, KType>> {
    val constructor = kClass.primaryConstructor ?: return emptyList()
    return constructor.parameters.mapNotNull { param ->
        val name = param.name ?: return@mapNotNull null
        name to param.type
    }
}

/**
 * Generates default mock value expression for JavaScript code.
 */
public fun getDefaultMockValueJs(
    paramName: String,
    type: KType,
    ownerClass: KClass<*>? = null,
    config: NpmGeneratorConfig? = null,
    importedFoundationJs: MutableSet<String>? = null
): String {
    val actualType = resolvePatchFieldInnerType(type) ?: type
    val classifier = actualType.classifier as? KClass<*>

    if (classifier != null && (classifier.java.isEnum || classifier in (config?.enumClasses ?: emptyList()))) {
        return getEnumDefaultMockValue(classifier, config)
    }

    if (config != null) {
        val overrideMock = resolveOverrideMockValue(paramName, actualType, ownerClass, config, importedFoundationJs)
        if (overrideMock != null) {
            return overrideMock
        }
    }

    val typeName = classifier?.simpleName ?: ""
    val ownerClassName = ownerClass?.simpleName ?: ""

    return when {
        typeName == "String" -> getDefaultStringMockValue(paramName, ownerClassName, config, importedFoundationJs)
        typeName == "Int" || typeName == "Long" || typeName == "Number" -> getDefaultNumberMockValue(paramName, config)
        else -> getDefaultTypeOrBrandedMockValue(actualType, typeName, config, importedFoundationJs)
    }
}

private fun resolvePatchFieldInnerType(type: KType): KType? {
    val classifier = type.classifier as? KClass<*>
    if (classifier?.simpleName == "PatchField") {
        return type.arguments.firstOrNull()?.type
    }
    return null
}

private fun resolveOverrideMockValue(
    paramName: String,
    type: KType,
    ownerClass: KClass<*>?,
    config: NpmGeneratorConfig,
    importedFoundationJs: MutableSet<String>?
): String? {
    val overrideClass = (if (ownerClass != null) config.stringFieldTypeOverrides["${ownerClass.simpleName}.$paramName"] else null)
        ?: config.stringFieldTypeOverrides[paramName]

    if (overrideClass != null) {
        val classifier = type.classifier as? KClass<*>
        val isCollection = classifier == List::class || classifier == Set::class ||
            classifier?.simpleName == "List" || classifier?.simpleName == "Set" || classifier?.simpleName == "Collection"

        val enumMock = resolveEnumOverrideMockValue(overrideClass, isCollection, config)
        if (enumMock != null) return enumMock

        val brandedMock = resolveBrandedOverrideMockValue(overrideClass, isCollection, config, importedFoundationJs)
        if (brandedMock != null) return brandedMock
    }

    return resolveConfigEnumMockValue(paramName, type, ownerClass, config)
}

private fun resolveEnumOverrideMockValue(
    overrideClass: KClass<*>,
    isCollection: Boolean,
    config: NpmGeneratorConfig
): String? {
    if (overrideClass.java.isEnum || overrideClass in config.enumClasses) {
        val enumMock = getEnumDefaultMockValue(overrideClass, config)
        return if (isCollection) "[$enumMock]" else enumMock
    }
    return null
}

private fun resolveBrandedOverrideMockValue(
    overrideClass: KClass<*>,
    isCollection: Boolean,
    config: NpmGeneratorConfig,
    importedFoundationJs: MutableSet<String>?
): String? {
    val isBrandedOverride = overrideClass in config.brandedTypes ||
        overrideClass.simpleName in config.brandedTypesNeedingHelpers

    if (!isBrandedOverride) return null

    val isFoundation = config.basePackagePrefix.isEmpty() || (config.foundationImportPackage.isNotEmpty() && config.packageName == config.foundationImportPackage)
    val overridePkg = overrideClass.java.name
    val isLocalClass = overrideClass in config.brandedTypes || overrideClass in config.dtoClasses || overrideClass in config.enumClasses
    val isFromFoundation = !isLocalClass && config.basePackagePrefix.isNotEmpty() && overridePkg.startsWith(config.basePackagePrefix)
    val brandedMock = resolveBrandedMockValue(overrideClass.simpleName ?: "", isFoundation, isFromFoundation, config, importedFoundationJs)

    return if (isCollection) "[$brandedMock]" else brandedMock
}

private fun resolveConfigEnumMockValue(
    paramName: String,
    type: KType,
    ownerClass: KClass<*>?,
    config: NpmGeneratorConfig
): String? {
    val overrideClass = (if (ownerClass != null) config.stringFieldTypeOverrides["${ownerClass.simpleName}.$paramName"] else null)
        ?: config.stringFieldTypeOverrides[paramName]
    if (overrideClass != null && (overrideClass.java.isEnum || overrideClass in config.enumClasses)) {
        return getEnumDefaultMockValue(overrideClass, config)
    }

    val classifier = type.classifier as? KClass<*>
    if (classifier == String::class || type.classifier == String::class) {
        val matchedEnum = config.enumClasses.firstOrNull { enumClass ->
            val enumName = enumClass.simpleName ?: ""
            enumName.equals(paramName, ignoreCase = true) ||
                enumName.equals("User$paramName", ignoreCase = true)
        }
        if (matchedEnum != null) {
            return getEnumDefaultMockValue(matchedEnum, config)
        }
    }

    return null
}

private fun getEnumDefaultMockValue(classifier: KClass<*>, config: NpmGeneratorConfig? = null): String {
    val enumInfo = getEnumInfo(classifier, config?.enumEnhancers ?: emptyMap(), config?.customEnumExtractor)
    val preferredNames = config?.preferredEnumDefaultNames ?: listOf("DEFAULT", "NONE")
    val defaultEntry = enumInfo.entries.firstOrNull { entry ->
        preferredNames.any { preferred -> entry.first.equals(preferred, ignoreCase = true) }
    } ?: enumInfo.entries.firstOrNull()
    return if (defaultEntry != null) "\"${defaultEntry.second}\"" else "\"\""
}

private fun getDefaultStringMockValue(
    paramName: String,
    ownerClassName: String,
    config: NpmGeneratorConfig? = null,
    importedFoundationJs: MutableSet<String>? = null
): String {
    val stringMappings = config?.stringMockMappings ?: emptyMap()
    val matchedEntry = stringMappings.entries.firstOrNull { (key, _) ->
        paramName.equals(key, ignoreCase = true) || paramName.contains(key, ignoreCase = true)
    }

    if (matchedEntry != null) {
        val mapped = matchedEntry.value
        val autoSymbols = config?.autoImportSymbols ?: emptySet()
        val isFoundation = config == null || config.basePackagePrefix.isEmpty() || (config.foundationImportPackage.isNotEmpty() && config.packageName == config.foundationImportPackage)
        if (!isFoundation && mapped in autoSymbols) {
            importedFoundationJs?.add(mapped)
        }
        return if (mapped.startsWith("\"") || mapped in autoSymbols || mapped.contains("(")) mapped else "\"$mapped\""
    }

    if (paramName == "id" || paramName.endsWith("Id") || paramName.endsWith("_id")) {
        return getDefaultStringIdMockValue(paramName, ownerClassName, config, importedFoundationJs)
    }

    return "\"test_${paramName}\""
}

private fun getDefaultStringIdMockValue(
    paramName: String,
    ownerClassName: String,
    config: NpmGeneratorConfig? = null,
    importedFoundationJs: MutableSet<String>? = null
): String {
    val autoSymbols = config?.autoImportSymbols ?: emptySet()
    val idMappings = config?.idMockMappings ?: emptyMap()
    val ownerIdMappings = config?.ownerIdMockMappings ?: emptyMap()
    val isFoundation = config == null || config.basePackagePrefix.isEmpty() || (config.foundationImportPackage.isNotEmpty() && config.packageName == config.foundationImportPackage)

    if (paramName in idMappings) {
        val symbol = idMappings[paramName]
        if (symbol != null && symbol in autoSymbols) {
            if (!isFoundation) {
                importedFoundationJs?.add(symbol)
            }
            return symbol
        }
    }

    if (paramName == "id") {
        for ((ownerPrefix, symbol) in ownerIdMappings) {
            if (ownerClassName.contains(ownerPrefix)) {
                if (symbol in autoSymbols) {
                    if (!isFoundation) {
                        importedFoundationJs?.add(symbol)
                    }
                    return symbol
                }
            }
        }
    }

    val defaultSymbol = config?.defaultIdMockMapping
    if (defaultSymbol != null && defaultSymbol in autoSymbols) {
        if (!isFoundation) {
            importedFoundationJs?.add(defaultSymbol)
        }
        return defaultSymbol
    }

    return "\"test_${paramName}_12345\""
}

private fun getDefaultNumberMockValue(
    paramName: String,
    config: NpmGeneratorConfig? = null
): String {
    val numberMappings = config?.numberMockMappings ?: emptyMap()
    val matchedEntry = numberMappings.entries.firstOrNull { (key, _) ->
        paramName.equals(key, ignoreCase = true) || paramName.contains(key, ignoreCase = true)
    }
    if (matchedEntry != null) {
        return matchedEntry.value
    }

    return "1"
}

private fun getDefaultTypeOrBrandedMockValue(
    type: KType,
    typeName: String,
    config: NpmGeneratorConfig? = null,
    importedFoundationJs: MutableSet<String>? = null
): String {
    val classifier = type.classifier as? KClass<*>
    if (classifier != null && (classifier.java.isEnum || classifier in (config?.enumClasses ?: emptyList()))) {
        return getEnumDefaultMockValue(classifier, config)
    }

    val isBranded = config?.brandedTypes?.contains(classifier) == true ||
        typeName in (config?.brandedTypesNeedingHelpers ?: emptySet()) ||
        typeName.endsWith("Id")

    val isFoundation = config == null || config.basePackagePrefix.isEmpty() || (config.foundationImportPackage.isNotEmpty() && config.packageName == config.foundationImportPackage)
    val classifierPkg = classifier?.java?.name ?: ""
    val isLocalClass = config != null && (classifier in config.brandedTypes || classifier in config.dtoClasses || classifier in config.enumClasses)
    val isFromFoundation = !isLocalClass && config?.basePackagePrefix?.isNotEmpty() == true && classifierPkg.startsWith(config.basePackagePrefix)

    if (isBranded) {
        return resolveBrandedMockValue(typeName, isFoundation, isFromFoundation, config, importedFoundationJs)
    }

    val dtoOrDomainMock = resolveDtoOrDomainMockValue(classifier, isFoundation, isFromFoundation, config, importedFoundationJs)
    if (dtoOrDomainMock != null) {
        return dtoOrDomainMock
    }

    return resolvePrimitiveOrCollectionMockValue(typeName)
}

private fun resolveBrandedMockValue(
    typeName: String,
    isFoundation: Boolean,
    isFromFoundation: Boolean,
    config: NpmGeneratorConfig?,
    importedFoundationJs: MutableSet<String>?
): String {
    val helperName = "to${typeName}OrThrow"
    if (!isFoundation && (isFromFoundation || typeName in (config?.brandedTypesNeedingHelpers ?: emptySet()))) {
        importedFoundationJs?.add(helperName)
    }

    val autoSymbols = config?.autoImportSymbols ?: emptySet()
    val idMappings = config?.idMockMappings ?: emptyMap()
    val brandedMockValues = config?.brandedTypeMockValues ?: emptyMap()

    val mockArg = when {
        typeName in idMappings -> {
            val symbol = idMappings[typeName]
            if (symbol != null && symbol in autoSymbols) {
                if (!isFoundation) {
                    importedFoundationJs?.add(symbol)
                }
                symbol
            } else {
                "\"00000000-0000-0000-0000-000000000001\""
            }
        }
        typeName in brandedMockValues -> {
            val valMock = brandedMockValues[typeName] ?: "test_value"
            if (valMock in autoSymbols) {
                if (!isFoundation) {
                    importedFoundationJs?.add(valMock)
                }
                valMock
            } else {
                "\"$valMock\""
            }
        }
        else -> "\"test_${typeName.decapitalizeFirstLetter()}_12345\""
    }

    return "$helperName($mockArg)"
}

private fun resolveDtoOrDomainMockValue(
    classifier: KClass<*>?,
    isFoundation: Boolean,
    isFromFoundation: Boolean,
    config: NpmGeneratorConfig?,
    importedFoundationJs: MutableSet<String>?
): String? {
    if (classifier == null) return null
    val isDtoOrDomain = (config?.dtoClasses?.contains(classifier) == true) ||
        (config?.domainToDtoMappers?.any { it.first == classifier || it.second == classifier } == true)

    if (!isDtoOrDomain) return null

    val helperName = "createTest${classifier.simpleName}"
    if (!isFoundation && isFromFoundation) {
        importedFoundationJs?.add(helperName)
    }
    return "$helperName()"
}

private fun resolvePrimitiveOrCollectionMockValue(typeName: String): String = when (typeName) {
    "Boolean" -> "true"
    "Instant" -> "1700000000000"
    "List", "Set", "Collection" -> "[]"
    "Map" -> "{}"
    else -> "{}"
}

private fun collectAllFixtureNames(config: NpmGeneratorConfig?): List<String> {
    val names = mutableListOf<String>()

    if (config?.testingSourceDirectory != null && config.testingSourceDirectory.exists()) {
        config.testingSourceDirectory.walkTopDown()
            .filter { it.isFile && it.name.endsWith("Fixture.kt") }
            .forEach { file ->
                val targetName = file.name.removeSuffix("Fixture.kt")
                if (targetName.isNotBlank()) {
                    names.add(targetName)
                }
            }
    }

    if (config != null && config.testFixtures.isNotEmpty()) {
        config.testFixtures.forEach { kClass ->
            kClass.simpleName?.let { names.add(it) }
        }
    } else {
        val defaultClasses = (config?.dtoClasses ?: emptyList()) +
            (config?.domainToDtoMappers?.map { it.first } ?: emptyList())
        defaultClasses.forEach { kClass -> kClass.simpleName?.let { names.add(it) } }
    }

    return names.distinct()
}

private fun isBrandedType(kClass: KClass<*>?, config: NpmGeneratorConfig?): Boolean {
    if (kClass == null) return false
    return config?.brandedTypes?.contains(kClass) == true ||
        kClass.simpleName in (config?.brandedTypesNeedingHelpers ?: emptySet()) ||
        (kClass.isValue && kClass.primaryConstructor?.parameters?.size == 1 && kClass.primaryConstructor?.parameters?.first()?.name == "value")
}

private fun buildDefaultPropsJs(
    name: String,
    kClass: KClass<*>?,
    config: NpmGeneratorConfig?,
    importedFoundationJs: MutableSet<String>?
): String {
    val props = if (kClass != null) extractClassProperties(kClass) else emptyList()
    if (props.isEmpty() || kClass == null) {
        return "    id: \"00000000-0000-0000-0000-000000000001\""
    }

    val isDtoClass = kClass in (config?.dtoClasses ?: emptyList()) ||
        name.endsWith("Payload") ||
        name.endsWith("Request") ||
        name.endsWith("Params")

    return props.joinToString(",\n    ") { (propName, propType) ->
        val wireKey = if (isDtoClass) getWireKey(kClass, propName) else propName
        val mockVal = getDefaultMockValueJs(propName, propType, kClass, config, importedFoundationJs)
        "    $wireKey: $mockVal"
    }
}

private fun generateSingleFixtureJs(
    name: String,
    kClass: KClass<*>?,
    config: NpmGeneratorConfig?,
    isFoundation: Boolean,
    importedFoundationJs: MutableSet<String>?
): String {
    val createSingleName = "createTest$name"
    val createListName = "createTest${name}List"

    if (isBrandedType(kClass, config) && kClass != null) {
        val simpleName = kClass.simpleName ?: ""
        val defaultVal = resolveBrandedMockValue(simpleName, isFoundation, false, config, importedFoundationJs)
        return """
export const $createSingleName = (overrides = {}) =>
  (typeof overrides === 'string' ? overrides : (typeof overrides === 'function' ? overrides(0) : $defaultVal));

export const $createListName = (count = 2, overrides = {}) =>
  createTestList(count, (index) => $createSingleName(typeof overrides === 'function' ? overrides(index) : overrides));
        """.trimIndent()
    }

    val defaultProps = buildDefaultPropsJs(name, kClass, config, importedFoundationJs)
    return """
export const $createSingleName = (overrides = {}) => ({
$defaultProps,
  ...(typeof overrides === 'function' ? overrides(0) : overrides)
});

export const $createListName = (count = 2, overrides = {}) =>
  createTestList(count, (index) => $createSingleName(typeof overrides === 'function' ? overrides(index) : (index === 0 ? overrides : { ...overrides })));
    """.trimIndent()
}

/**
 * Generates JavaScript test fixture factory functions.
 */
public fun generateTestFixturesJs(
    config: NpmGeneratorConfig? = null,
    importedFoundationJs: MutableSet<String>? = null
): String {
    val fixtureNames = collectAllFixtureNames(config)
    val excluded = config?.excludedTestFixtures ?: emptySet()
    val namesToGenerate = fixtureNames.filter { it !in excluded }

    val isFoundation = config == null || config.basePackagePrefix.isEmpty() || (config.foundationImportPackage.isNotEmpty() && config.packageName == config.foundationImportPackage)
    if (!isFoundation && namesToGenerate.isNotEmpty()) {
        importedFoundationJs?.add("createTestList")
    }

    val createTestListHelperJs = if (isFoundation && namesToGenerate.isNotEmpty()) {
        """
export const createTestList = (count = 2, factory) =>
  Array.from({ length: count }, (_, index) => (typeof factory === 'function' ? factory(index) : factory));
        """.trimIndent() + "\n\n"
    } else {
        ""
    }

    val allTargetClasses = (config?.dtoClasses ?: emptyList()) +
        (config?.domainToDtoMappers?.map { it.first } ?: emptyList()) +
        (config?.domainToDtoMappers?.mapNotNull { it.second } ?: emptyList()) +
        (config?.brandedTypes ?: emptyList()) +
        (config?.testFixtures ?: emptyList())
    val testClassesMap = allTargetClasses.associateBy { it.simpleName ?: "" }

    val generateFunctionCode = namesToGenerate.joinToString("\n\n") { name ->
        generateSingleFixtureJs(name, testClassesMap[name], config, isFoundation, importedFoundationJs)
    }

    return createTestListHelperJs + generateFunctionCode
}

/**
 * Generates TypeScript declaration code for test fixture factory functions.
 */
public fun generateTestFixturesDts(config: NpmGeneratorConfig? = null): String {
    val fixtureNames = collectAllFixtureNames(config)
    val excluded = config?.excludedTestFixtures ?: emptySet()
    val namesToGenerate = fixtureNames.filter { it !in excluded }

    val isFoundation = config == null || config.basePackagePrefix.isEmpty() || (config.foundationImportPackage.isNotEmpty() && config.packageName == config.foundationImportPackage)

    val createTestListHelperDts = if (isFoundation && namesToGenerate.isNotEmpty()) {
        "export declare function createTestList<T>(count?: number, factory?: ((index: number) => T) | T): T[];\n\n"
    } else {
        ""
    }

    val typeDef = "export type Overrides<T> = Partial<T> | ((index: number) => Partial<T>);\n\n"

    val allTargetClasses = (config?.dtoClasses ?: emptyList()) +
        (config?.domainToDtoMappers?.map { it.first } ?: emptyList()) +
        (config?.domainToDtoMappers?.mapNotNull { it.second } ?: emptyList()) +
        (config?.brandedTypes ?: emptyList()) +
        (config?.testFixtures ?: emptyList())
    val testClassesMap = allTargetClasses.associateBy { it.simpleName ?: "" }

    val declarationsCode = namesToGenerate.joinToString("\n\n") { name ->
        val createSingleName = "createTest$name"
        val createListName = "createTest${name}List"
        val kClass = testClassesMap[name]
        val isBranded = kClass != null && (
            config?.brandedTypes?.contains(kClass) == true ||
            kClass.simpleName in (config?.brandedTypesNeedingHelpers ?: emptySet()) ||
            (kClass.isValue && kClass.primaryConstructor?.parameters?.size == 1 && kClass.primaryConstructor?.parameters?.first()?.name == "value")
        )

        if (isBranded) {
            """
export declare function $createSingleName(overrides?: $name | string | ((index: number) => $name | string)): $name;
export declare function $createListName(count?: number, overrides?: $name | string | ((index: number) => $name | string)): $name[];
            """.trimIndent()
        } else {
            """
export declare function $createSingleName(overrides?: Overrides<$name>): $name;
export declare function $createListName(count?: number, overrides?: Overrides<$name>): $name[];
            """.trimIndent()
        }
    }

    return "$typeDef$createTestListHelperDts$declarationsCode"
}
