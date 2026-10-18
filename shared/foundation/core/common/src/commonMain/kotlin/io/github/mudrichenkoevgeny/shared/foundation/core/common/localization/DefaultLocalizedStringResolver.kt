package io.github.mudrichenkoevgeny.shared.foundation.core.common.localization

import io.github.mudrichenkoevgeny.shared.foundation.core.common.collection.createConcurrentMap
import io.github.mudrichenkoevgeny.shared.foundation.core.common.serialization.FoundationJson
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * Standard thread-safe implementation of [LocalizedStringResolver] with in-memory bundle caching.
 *
 * Resolves bundles using the following fallback sequence:
 * 1. Exact normalized locale (e.g., "en-us").
 * 2. Language-only code (e.g., "en").
 * 3. Default fallback locale (by default "en").
 *
 * @param resourceLoader Function returning file content by relative classpath/resource path.
 * @param defaultLocale Fallback locale code when requested locale is unavailable.
 */
class DefaultLocalizedStringResolver(
    private val resourceLoader: (path: String) -> String?,
    private val defaultLocale: String = CommonLocales.DEFAULT
) : LocalizedStringResolver {

    private val cache: MutableMap<String, Map<String, String>> = createConcurrentMap()

    override fun resolve(module: String, file: String, key: String, locale: String): String? {
        val normalizedLocale = locale.lowercase()
        val languageOnly = normalizedLocale.substringBefore('-').substringBefore('_')

        return getString(module, file, normalizedLocale, key)
            ?: getString(module, file, languageOnly, key)
            ?: getString(module, file, defaultLocale, key)
    }

    private fun getString(module: String, file: String, locale: String, key: String): String? {
        val cacheKey = "$module/$file/$locale"
        val bundle = cache.getOrPut(cacheKey) {
            loadBundle(module, file, locale)
        }
        return bundle[key]
    }

    private fun loadBundle(module: String, file: String, locale: String): Map<String, String> {
        val path = "localization/$module/$locale/$file.json"
        val content = resourceLoader(path) ?: return emptyMap()

        return try {
            val jsonObject = FoundationJson.decodeFromString<JsonObject>(content)
            jsonObject.mapValues { it.value.jsonPrimitive.content }
        } catch (e: Exception) {
            throw IllegalStateException("Failed to parse localization JSON bundle at path '$path'", e)
        }
    }
}