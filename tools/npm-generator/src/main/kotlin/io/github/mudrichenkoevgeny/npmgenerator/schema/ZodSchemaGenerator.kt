package io.github.mudrichenkoevgeny.npmgenerator.schema

import io.github.mudrichenkoevgeny.npmgenerator.engine.NpmGeneratorEngine
import kotlin.reflect.KClass
import kotlin.reflect.KType

/**
 * Generates Zod schema definition for a single DTO class.
 */
public fun generateSingleDtoSchema(engine: NpmGeneratorEngine, kClass: KClass<*>): String =
    engine.generateSingleDtoSchema(kClass)

/**
 * Resolves Zod type string representation for a given Kotlin type.
 */
public fun resolveZodType(engine: NpmGeneratorEngine, paramName: String, type: KType, parentClass: KClass<*>): String =
    engine.resolveZodType(paramName, type, parentClass)

/**
 * Resolves TypeScript type string representation for a given Kotlin type.
 */
public fun resolveTsType(engine: NpmGeneratorEngine, paramName: String, type: KType, parentClass: KClass<*>): String =
    engine.resolveTsType(paramName, type, parentClass)
