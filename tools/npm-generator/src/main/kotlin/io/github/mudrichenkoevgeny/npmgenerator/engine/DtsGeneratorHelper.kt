package io.github.mudrichenkoevgeny.npmgenerator.engine

import io.github.mudrichenkoevgeny.npmgenerator.doc.KDocExtractor
import io.github.mudrichenkoevgeny.npmgenerator.doc.formatAsJsDoc
import io.github.mudrichenkoevgeny.npmgenerator.mapper.generateCompositeParsersDts
import io.github.mudrichenkoevgeny.npmgenerator.mapper.generateDomainModelsAndMappersDts
import io.github.mudrichenkoevgeny.npmgenerator.mapper.generateParamParsersAndSerializersDts
import io.github.mudrichenkoevgeny.npmgenerator.route.generateContractDeclarationsDts
import io.github.mudrichenkoevgeny.npmgenerator.route.generateErrorCodeDeclarationsDts
import io.github.mudrichenkoevgeny.npmgenerator.route.generatePermissionCodeDeclarationsDts
import io.github.mudrichenkoevgeny.npmgenerator.route.generateRouteDeclarationsDts
import io.github.mudrichenkoevgeny.npmgenerator.testing.generateTestFixturesDts
import io.github.mudrichenkoevgeny.npmgenerator.util.decapitalizeFirstLetter
import io.github.mudrichenkoevgeny.npmgenerator.util.getEnumInfo
import kotlinx.serialization.SerialName
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.primaryConstructor

internal fun generateDtoDeclarationsDts(
    config: NpmGeneratorConfig,
    engine: NpmGeneratorEngine,
    kDocExtractor: KDocExtractor
): String = config.dtoClasses.joinToString("\n\n") { kClass ->
    val name = kClass.simpleName ?: ""
    val classDoc = kDocExtractor.extractKDoc(kClass)?.let { "$it\n" } ?: ""
    val schemaName = name.decapitalizeFirstLetter() + "Schema"
    val primaryConstructor = kClass.primaryConstructor

    val fields = if (primaryConstructor != null) {
        primaryConstructor.parameters.map { param ->
            val paramName = param.name ?: ""
            val serialNameAnnotation = param.annotations.filterIsInstance<SerialName>().firstOrNull()
                ?: kClass.declaredMemberProperties
                    .firstOrNull { it.name == paramName }?.findAnnotation<SerialName>()

            val wireKey = serialNameAnnotation?.value ?: paramName
            val tsType = engine.resolveTsType(paramName, param.type, kClass)
            val optionalSign = if (param.isOptional) "?" else ""
            val fieldDoc = kDocExtractor.extractKDoc(kClass, paramName)?.formatAsJsDoc("    ")?.let { "$it\n" } ?: ""
            "$fieldDoc    readonly $wireKey$optionalSign: $tsType;"
        }
    } else {
        emptyList()
    }

    """
${classDoc}export interface $name {
${fields.joinToString("\n")}
}
export const $schemaName: z.ZodType<$name>;
    """.trimIndent()
}

internal fun generateDtsContent(
    config: NpmGeneratorConfig,
    engine: NpmGeneratorEngine,
    kDocExtractor: KDocExtractor,
    localizationDts: String,
    isFoundation: Boolean,
    importedFoundationDts: Set<String>
): String {
    val brandedDts = config.brandedTypes.joinToString("\n\n") { kClass ->
        val name = kClass.simpleName ?: ""
        val schemaName = name.decapitalizeFirstLetter() + "Schema"
        """
export type $name = string & { readonly __brand: unique symbol };
export function to${name}OrNull(value: string | null | undefined): $name | null;
export function to${name}OrThrow(value: string | null | undefined): $name;
export const $schemaName: z.ZodType<$name>;
        """.trimIndent()
    }

    val enumsDts = config.enumClasses.joinToString("\n\n") { kClass ->
        val info = getEnumInfo(kClass, config.enumEnhancers, config.customEnumExtractor)
        val name = info.name
        val classDoc = kDocExtractor.extractKDoc(kClass)?.let { "$it\n" } ?: ""
        val schemaName = info.schemaName
        val typeValues = info.entries.map { it.second }.distinct().joinToString(" | ") { "\"$it\"" }.ifEmpty { "string" }
        val objectFields = info.entries.map { (key, _) -> "  readonly $key: $name;" }

        """
${classDoc}export type $name = $typeValues;
export const $schemaName: z.ZodType<$name>;
export declare const $name: {
${objectFields.joinToString("\n")}
};
        """.trimIndent()
    }

    val extraEnumsDts = config.extraEnums.joinToString("\n\n") { info ->
        val name = info.name
        val schemaName = info.schemaName
        val typeValues = info.entries.map { it.second }.distinct().joinToString(" | ") { "\"$it\"" }.ifEmpty { "string" }
        val objectFields = info.entries.map { (key, _) -> "  readonly $key: $name;" }

        """
export type $name = $typeValues;
export const $schemaName: z.ZodType<$name>;
export declare const $name: {
${objectFields.joinToString("\n")}
};
        """.trimIndent()
    }

    val dtosDts = generateDtoDeclarationsDts(config, engine, kDocExtractor)
    val errorsDts = generateErrorCodeDeclarationsDts(config)
    val permsDts = generatePermissionCodeDeclarationsDts(config)
    val contractsDts = generateContractDeclarationsDts(config)
    val routesDts = generateRouteDeclarationsDts(config)

    val mappersDts = generateDomainModelsAndMappersDts(config, engine)
    val paramParsersDts = generateParamParsersAndSerializersDts(config)
    val compositeParsersDts = generateCompositeParsersDts(config)

    val testingDts = generateTestFixturesDts(config)

    val foundationBaseTypes = if (isFoundation) {
        """
export type Instant = number;

export interface PagedResult<T> {
  readonly items: T[];
  readonly totalCount: number;
  readonly pageNumber: number;
  readonly pageSize: number;
  readonly totalPages: number;
}

export declare function pagedResultSchema<T>(itemSchema: z.ZodType<T>): z.ZodType<PagedResult<T>>;
        """.trimIndent()
    } else {
        ""
    }

    return buildString {
        append("import { z } from 'zod';\n\n")
        if (!isFoundation && importedFoundationDts.isNotEmpty()) {
            val imports = importedFoundationDts.sorted().joinToString(", ")
            append("import type { $imports } from '${config.foundationImportPackage}';\n\n")
        }
        if (foundationBaseTypes.isNotBlank()) {
            append(foundationBaseTypes).append("\n\n")
        }
        append(brandedDts).append("\n\n")
        append(enumsDts).append("\n\n")
        if (extraEnumsDts.isNotBlank()) {
            append(extraEnumsDts).append("\n\n")
        }
        append(dtosDts).append("\n\n")
        append(errorsDts).append("\n\n")
        append(permsDts).append("\n\n")
        append(contractsDts).append("\n\n")
        append(routesDts).append("\n\n")
        append(mappersDts).append("\n\n")
        append(paramParsersDts).append("\n\n")
        append(compositeParsersDts).append("\n\n")
        append(testingDts).append("\n\n")
        append(localizationDts)
        if (!config.extraDtsCode.isNullOrBlank()) {
            append("\n\n").append(config.extraDtsCode)
        }
    }
}

internal fun generateNpmReadme(config: NpmGeneratorConfig): String {
    return """
# ${config.packageName}

${config.packageDescription}

## Version
`${config.packageVersion}`

## Contents
- **TypeScript Declarations (`index.d.ts`)**: Typed DTO interfaces, domain models,branded IDs, route objects, localization strings, and test factories.
- **JavaScript Module (`index.js`)**: Executable Zod validation schemas (`*Schema`), parameter parsers, domain mappers, and localization facades.

## License
Apache-2.0
    """.trimIndent()
}
