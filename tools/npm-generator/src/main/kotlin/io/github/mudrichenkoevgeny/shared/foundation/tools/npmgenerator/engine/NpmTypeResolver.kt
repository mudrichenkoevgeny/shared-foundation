package io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.engine

import kotlin.reflect.KClass
import kotlin.reflect.KType

/**
 * Strategy interface for custom resolution of Kotlin types to Zod schemas or TypeScript type strings.
 */
public interface NpmTypeResolver {
    /**
     * Resolves a Kotlin type to a Zod schema expression string.
     */
    public fun resolveZodType(
        paramName: String,
        type: KType,
        parentClass: KClass<*>,
        resolver: (String, KType, KClass<*>) -> String
    ): String? = null

    /**
     * Resolves a Kotlin type to a TypeScript type annotation string.
     */
    public fun resolveTsType(
        paramName: String,
        type: KType,
        parentClass: KClass<*>,
        resolver: (String, KType, KClass<*>) -> String
    ): String? = null

    /**
     * Determines whether a classifier is a synthetic wrapper type that should not be tracked as an imported foundation class.
     */
    public fun isSyntheticWrapper(classifier: KClass<*>): Boolean = false
}
