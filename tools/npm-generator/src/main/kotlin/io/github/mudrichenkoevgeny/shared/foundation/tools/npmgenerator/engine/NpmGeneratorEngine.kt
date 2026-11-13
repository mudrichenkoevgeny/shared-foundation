package io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.engine

import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.doc.KDocExtractor
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.doc.formatAsJsDoc
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.localization.generateLocalizationCode
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.mapper.generateCompositeParsersJs
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.mapper.generateDomainModelsAndMappersJs
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.mapper.generateParamParsersAndSerializersJs
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.route.extractConstantPermissionProperties
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.route.generateContractObjects
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.route.generateErrorCodeObjects
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.route.generatePermissionCodeObjects
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.route.generateRouteObjects
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.testing.generateTestFixturesJs
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.util.decapitalizeFirstLetter
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.util.getEnumInfo
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.util.sortDtosTopologically
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.verification.NpmPackageVerifier
import kotlinx.serialization.SerialName
import java.io.File
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.primaryConstructor

/**
 * Core generation engine for building NPM packages with TypeScript declarations, Zod schemas, and runtime contracts.
 */
public class NpmGeneratorEngine(private val config: NpmGeneratorConfig) {

    public val kDocExtractor: KDocExtractor = KDocExtractor(config.sourceCodeDirectory)
    private val importedFoundationJs = mutableSetOf<String>()
    private val importedFoundationDts = mutableSetOf<String>()

    /**
     * Executes full generation based on the provided configuration.
     */
    public fun generate() {
        val outputDir = config.outputDirectory
        outputDir.mkdirs()

        val isFoundation = config.basePackagePrefix.isEmpty() ||
            (config.foundationImportPackage.isNotEmpty() && config.packageName == config.foundationImportPackage)

        if (!isFoundation) {
            collectFoundationImports()
        }

        File(outputDir, "package.json").writeText(generatePackageJson())
        File(outputDir, ".npmignore").writeText("*.tgz\n")
        File(outputDir, "README.md").writeText(generateNpmReadme(config))

        val (localizationJs, localizationDts) = generateLocalizationCode(config, config.outputDirectory, importedFoundationJs, importedFoundationDts)

        val fullJsContent = buildFullJsContent(isFoundation, localizationJs)
        File(outputDir, "index.js").writeText(fullJsContent)

        val dtsContent = generateDtsContent(config, this, kDocExtractor, localizationDts, isFoundation, importedFoundationDts)
        File(outputDir, "index.d.ts").writeText(dtsContent)

        auditGeneratedOutput(config, fullJsContent, dtsContent, importedFoundationJs)
        NpmPackageVerifier(config, importedFoundationJs, importedFoundationDts).verifyAll()
    }

    /**
     * Cleans JavaScript content by stripping comments and string literal interiors.
     */
    public fun cleanJsContent(content: String): String =
        io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.engine.cleanJsContent(content)

    private fun collectFoundationImports() {
        if (config.permClasses.isNotEmpty()) {
            config.permClasses.forEach { kClass ->
                val props = extractConstantPermissionProperties(kClass, null)
                props.forEach { (_, _, typeName) ->
                    if (typeName in config.brandedTypesNeedingHelpers) {
                        importedFoundationJs.add("to${typeName}OrThrow")
                        importedFoundationDts.add(typeName)
                    }
                }
            }
        }

        if (config.autoImportSymbols.isNotEmpty()) {
            val extraJs = (config.extraJsCode ?: "") + "\n" + (config.customMatchersAndParsersJs ?: "")
            if (extraJs.isNotBlank()) {
                val cleanExtra = cleanJsContent(extraJs)
                config.autoImportSymbols.forEach { symbol ->
                    if (Regex("""\b${Regex.escape(symbol)}\b""").containsMatchIn(cleanExtra)) {
                        importedFoundationJs.add(symbol)
                    }
                }
            }
        }

        if (config.dtoClasses.any { it.simpleName?.endsWith("Params") == true }) {
            importedFoundationJs.add("toQueryMap")
            importedFoundationJs.add("toQuerySearchString")
        }

        if (config.testFixtures.isNotEmpty() || config.testingSourceDirectory != null) {
            importedFoundationJs.add("createTestList")
        }

        collectDomainMapperImports()
    }

    private fun collectDomainMapperImports() {
        if (config.domainToDtoMappers.isEmpty()) return

        config.domainToDtoMappers.forEach { (domainClass, payloadClass) ->
            val domainPkg = domainClass.java.name
            if (!isLocalClass(domainClass) && config.basePackagePrefix.isNotEmpty() && domainPkg.startsWith(config.basePackagePrefix)) {
                domainClass.simpleName?.let { domainName ->
                    importedFoundationJs.add("to$domainName")
                    importedFoundationDts.add(domainName)
                }
            }
            if (payloadClass != null) {
                val payloadPkg = payloadClass.java.name
                if (!isLocalClass(payloadClass) && config.basePackagePrefix.isNotEmpty() && payloadPkg.startsWith(config.basePackagePrefix)) {
                    payloadClass.simpleName?.let { payloadName ->
                        importedFoundationJs.add("to$payloadName")
                        importedFoundationDts.add(payloadName)
                        importedFoundationJs.add(payloadName.decapitalizeFirstLetter() + "Schema")
                    }
                }
            }

            trackFoundationImport(domainClass)
            if (payloadClass != null) {
                trackFoundationImport(payloadClass)
            }

            trackTypeConstructorImports(domainClass)
            if (payloadClass != null) {
                trackTypeConstructorImports(payloadClass)
            }
        }
    }

    private fun trackTypeConstructorImports(kClass: KClass<*>) {
        kClass.primaryConstructor?.parameters?.forEach { param ->
            (param.type.classifier as? KClass<*>)?.let { trackFoundationImport(it) }
            param.type.arguments.forEach { arg ->
                (arg.type?.classifier as? KClass<*>)?.let { trackFoundationImport(it) }
            }
        }
    }

    private fun buildFullJsContent(isFoundation: Boolean, localizationJs: String): String {
        val brandedTypesJs = generateBrandedTypesCode()
        val dtoSchemasJs = generateDtoSchemasCode(isFoundation)
        val enumsJs = generateEnumsCode()
        val errorsJs = generateErrorCodeObjects(config)
        val permsJs = generatePermissionCodeObjects(config)
        val contractsJs = generateContractObjects(config)
        val routesJs = generateRouteObjects(config)

        val mappersJs = generateDomainModelsAndMappersJs(config)
        val paramParsersJs = generateParamParsersAndSerializersJs(config)
        val compositeParsersJs = generateCompositeParsersJs(config)

        val testingJs = generateTestFixturesJs(config, importedFoundationJs)

        return buildString {
            append("import { z } from 'zod';\n\n")
            if (!isFoundation && importedFoundationJs.isNotEmpty()) {
                val imports = importedFoundationJs.sorted().joinToString(", ")
                append("import { $imports } from '${config.foundationImportPackage}';\n\n")
            }
            append(brandedTypesJs).append("\n\n")
            append(enumsJs).append("\n\n")
            append(dtoSchemasJs).append("\n\n")
            append(errorsJs).append("\n\n")
            append(permsJs).append("\n\n")
            append(contractsJs).append("\n\n")
            append(routesJs).append("\n\n")
            append(mappersJs).append("\n\n")
            append(paramParsersJs).append("\n\n")
            append(testingJs).append("\n\n")
            append(localizationJs)
            if (compositeParsersJs.isNotBlank()) {
                append("\n\n").append(compositeParsersJs)
            }
        }
    }

    private fun generatePackageJson(): String {
        val deps = buildMap {
            put("zod", config.zodVersion)
            putAll(config.peerDependencies)
        }
        val depsJson = deps.entries.joinToString(",\n    ") { (k, v) -> "\"$k\": \"$v\"" }

        return """
{
  "name": "${config.packageName}",
  "version": "${config.packageVersion}",
  "description": "${config.packageDescription}",
  "license": "Apache-2.0",
  "type": "module",
  "main": "index.js",
  "types": "index.d.ts",
  "exports": {
    ".": {
      "types": "./index.d.ts",
      "import": "./index.js"
    }
  },
  "dependencies": {
    $depsJson
  }
}
        """.trimIndent()
    }

    private fun generateBrandedTypesCode(): String {
        val codeBlocks = config.brandedTypes.map { kClass ->
            val name = kClass.simpleName ?: ""
            val schemaName = name.decapitalizeFirstLetter() + "Schema"
            val jsDoc = kDocExtractor.extractKDoc(kClass)?.formatAsJsDoc() ?: ""

            """
${if (jsDoc.isNotBlank()) "$jsDoc\n" else ""}export const $schemaName = z.string().brand();

export function to${name}OrNull(value) {
  if (value === null || value === undefined || String(value).trim() === '') return null;
  return $schemaName.parse(String(value));
}

export function to${name}OrThrow(value) {
  if (value === null || value === undefined || String(value).trim() === '') {
    throw new Error("$name cannot be empty");
  }
  return $schemaName.parse(String(value));
}
            """.trimIndent()
        }

        return codeBlocks.joinToString("\n\n")
    }

    private fun generateDtoSchemasCode(isFoundation: Boolean): String {
        val pagedResultCode = if (isFoundation) {
            """
export function pagedResultSchema(itemSchema) {
  return z.object({
    items: z.array(itemSchema),
    total_count: z.number(),
    page_number: z.number(),
    page_size: z.number(),
    total_pages: z.number()
  }).transform(data => ({
    items: data.items,
    totalCount: data.total_count,
    pageNumber: data.page_number,
    pageSize: data.page_size,
    totalPages: data.total_pages
  }));
}
            """.trimIndent()
        } else {
            importedFoundationJs.add("pagedResultSchema")
            ""
        }

        val sortedDtos = sortDtosTopologically(config.dtoClasses)
        val generatedDtos = sortedDtos.joinToString("\n\n") { kClass ->
            generateSingleDtoSchema(kClass)
        }

        return if (pagedResultCode.isNotBlank()) "$pagedResultCode\n\n$generatedDtos" else generatedDtos
    }

    /**
     * Generates Zod schema definition for a single DTO class.
     */
    public fun generateSingleDtoSchema(kClass: KClass<*>): String {
        val name = kClass.simpleName ?: ""
        val schemaName = name.decapitalizeFirstLetter() + "Schema"
        val primaryConstructor = kClass.primaryConstructor ?: return ""

        val fields = primaryConstructor.parameters.map { param ->
            val paramName = param.name ?: ""
            val serialNameAnnotation = param.annotations.filterIsInstance<SerialName>().firstOrNull()
                ?: kClass.declaredMemberProperties
                    .firstOrNull { it.name == paramName }?.findAnnotation<SerialName>()

            val wireKey = serialNameAnnotation?.value ?: paramName
            val type = param.type
            var zodType = resolveZodType(paramName, type, kClass)

            if (param.isOptional) {
                zodType = resolveOptionalZodType(zodType)
            }

            "    $wireKey: $zodType"
        }

        return """
export const $schemaName = z.object({
${fields.joinToString(",\n")}
});
        """.trimIndent()
    }

    private fun resolveOptionalZodType(zodType: String): String = when {
        zodType.startsWith("z.array(") -> "$zodType.default([])"
        zodType.contains(".nullable()") -> "$zodType.default(null)"
        else -> "$zodType.optional()"
    }

    /**
     * Resolves Zod type string representation for a given Kotlin type.
     */
    public fun resolveZodType(paramName: String, type: KType, parentClass: KClass<*>): String {
        config.customTypeResolver?.resolveZodType(paramName, type, parentClass) { p, t, parent ->
            resolveZodType(p, t, parent)
        }?.let { return it }

        val classifier = type.classifier as? KClass<*> ?: return "z.unknown()"
        val isMarkedNullable = type.isMarkedNullable

        trackFoundationImport(classifier)

        val baseZod = resolveEnumZod(classifier, config)
            ?: resolveBrandedZod(classifier, paramName, parentClass, config) { trackFoundationImport(it) }
            ?: resolveContainerZod(type, paramName, parentClass) { p, t, parent -> resolveZodType(p, t, parent) }
            ?: (classifier.simpleName?.decapitalizeFirstLetter() + "Schema")

        return if (isMarkedNullable) "$baseZod.nullable()" else baseZod
    }

    /**
     * Resolves TypeScript type string representation for a given Kotlin type.
     */
    public fun resolveTsType(paramName: String, type: KType, parentClass: KClass<*>): String {
        config.customTypeResolver?.resolveTsType(paramName, type, parentClass) { p, t, parent ->
            resolveTsType(p, t, parent)
        }?.let { return it }

        val classifier = type.classifier as? KClass<*> ?: return "unknown"
        val isMarkedNullable = type.isMarkedNullable

        trackFoundationImport(classifier)

        val baseTs = resolveEnumTs(classifier, config)
            ?: resolveBrandedTs(classifier, paramName, parentClass, config, importedFoundationDts) { trackFoundationImport(it) }
            ?: resolveContainerTs(type, paramName, parentClass) { p, t, parent -> resolveTsType(p, t, parent) }
            ?: (classifier.simpleName ?: "unknown")

        return if (isMarkedNullable) "$baseTs | null" else baseTs
    }

    private fun isLocalClass(classifier: KClass<*>): Boolean {
        return classifier in config.dtoClasses ||
            classifier in config.enumClasses ||
            classifier in config.brandedTypes ||
            config.domainToDtoMappers.any { it.first == classifier || it.second == classifier }
    }

    private fun trackFoundationImport(classifier: KClass<*>) {
        val simpleName = classifier.simpleName ?: return
        if (config.customTypeResolver?.isSyntheticWrapper(classifier) == true ||
            classifier == List::class || classifier == Set::class || classifier == Map::class || classifier == Collection::class
        ) {
            return
        }
        if (config.basePackagePrefix.isNotEmpty() && config.packageName != config.foundationImportPackage) {
            if (!isLocalClass(classifier)) {
                val pkg = classifier.java.name
                if (pkg.startsWith(config.basePackagePrefix)) {
                    importedFoundationDts.add(simpleName)
                    if (classifier.java.isEnum || classifier in config.enumClasses) {
                        importedFoundationJs.add(simpleName)
                        importedFoundationJs.add(simpleName.decapitalizeFirstLetter() + "Schema")
                    } else {
                        importedFoundationJs.add(simpleName.decapitalizeFirstLetter() + "Schema")
                    }

                    val isBranded = classifier in config.brandedTypes ||
                        simpleName in config.brandedTypesNeedingHelpers ||
                        simpleName.endsWith("Id")
                    if (isBranded) {
                        importedFoundationJs.add("to${simpleName}OrThrow")
                    }
                }
            }
        }
    }

    private fun generateEnumsCode(): String {
        val enumBlocks = config.enumClasses.map { kClass ->
            val info = getEnumInfo(kClass, config.enumEnhancers, config.customEnumExtractor)
            val jsDoc = kDocExtractor.extractKDoc(kClass)?.formatAsJsDoc() ?: ""

            val valuesList = info.entries.map { it.second }.distinct().joinToString(", ") { "\"$it\"" }
            val objectFields = info.entries.joinToString(",\n") { (key, wire) -> "  $key: \"$wire\"" }

            if (info.entries.isEmpty()) {
                """
${if (jsDoc.isNotBlank()) "$jsDoc\n" else ""}export const ${info.name} = {};
export const ${info.schemaName} = z.string();
                """.trimIndent()
            } else {
                """
${if (jsDoc.isNotBlank()) "$jsDoc\n" else ""}export const ${info.name} = {
$objectFields
};

export const ${info.schemaName} = z.enum([$valuesList]);
                """.trimIndent()
            }
        }

        val extraEnumBlocks = config.extraEnums.map { info ->
            val schemaValues = info.entries.map { it.second }.distinct().joinToString(", ") { "\"$it\"" }
            val objectFields = info.entries.joinToString(",\n") { (key, wire) -> "  $key: \"$wire\"" }
            """
export const ${info.schemaName} = z.enum([$schemaValues]);
export const ${info.name} = {
$objectFields
};
            """.trimIndent()
        }

        val extraCode = config.extraJsCode ?: ""

        return (enumBlocks + extraEnumBlocks + listOf(extraCode)).filter { it.isNotBlank() }.joinToString("\n\n")
    }
}
