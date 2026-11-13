package io.github.mudrichenkoevgeny.npmgenerator.engine

import io.github.mudrichenkoevgeny.npmgenerator.util.EnumInfo
import java.io.File
import kotlin.reflect.KClass

/**
 * Configuration data class defining package metadata, paths, and target Kotlin classes to generate NPM exports for.
 */
public data class NpmGeneratorConfig(
    public val packageName: String,
    public val packageVersion: String,
    public val packageDescription: String,
    public val peerDependencies: Map<String, String> = emptyMap(),
    public val outputDirectory: File,
    public val sourceCodeDirectory: File,
    public val localizationJsonDirectory: File? = null,
    public val testingSourceDirectory: File? = null,
    public val dtoClasses: List<KClass<*>> = emptyList(),
    public val enumClasses: List<KClass<*>> = emptyList(),
    public val brandedTypes: List<KClass<*>> = emptyList(),
    public val errorClasses: List<KClass<*>> = emptyList(),
    public val permClasses: List<KClass<*>> = emptyList(),
    public val routeObjects: List<KClass<*>> = emptyList(),
    public val contractClasses: List<KClass<*>> = emptyList(),
    public val domainToDtoMappers: List<Pair<KClass<*>, KClass<*>?>> = emptyList(),
    public val testFixtures: List<KClass<*>> = emptyList(),
    public val stringFieldTypeOverrides: Map<String, KClass<*>> = emptyMap(),
    public val extraConstantsCode: String? = null,
    public val extraConstantsDts: String? = null,
    public val customTypeResolver: NpmTypeResolver? = null,
    public val basePackagePrefix: String = "",
    public val foundationImportPackage: String = "",
    public val autoImportSymbols: Set<String> = emptySet(),
    public val brandedTypesNeedingHelpers: Set<String> = emptySet(),
    public val extraJsCode: String? = null,
    public val extraDtsCode: String? = null,
    public val enumEnhancers: Map<String, List<Pair<String, String>>> = emptyMap(),
    public val extraEnums: List<EnumInfo> = emptyList(),
    public val fieldAliases: Map<String, String> = emptyMap(),
    public val idMockMappings: Map<String, String> = emptyMap(),
    public val ownerIdMockMappings: Map<String, String> = emptyMap(),
    public val defaultIdMockMapping: String? = null,
    public val stringMockMappings: Map<String, String> = emptyMap(),
    public val numberMockMappings: Map<String, String> = emptyMap(),
    public val brandedTypeMockValues: Map<String, String> = emptyMap(),
    public val localizationRootClassName: String? = null,
    public val localizationManagerName: String? = null,
    public val excludedLocalizationBundles: Set<String> = emptySet(),
    public val customEnumExtractor: ((KClass<*>) -> List<Pair<String, String>>)? = null,
    public val customMatchersAndParsersJs: String? = null,
    public val customMatchersAndParsersDts: String? = null,
    public val excludedTestFixtures: Set<String> = emptySet(),
    public val preferredEnumDefaultNames: List<String> = listOf("DEFAULT", "NONE"),
    public val customDtsSmokeTestCode: String? = null,
    public val customRuntimeSmokeTestCode: String? = null,
    public val zodVersion: String,
    public val typescriptVersion: String
)
