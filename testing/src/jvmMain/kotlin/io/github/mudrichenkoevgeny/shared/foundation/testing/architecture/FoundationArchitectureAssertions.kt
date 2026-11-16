package io.github.mudrichenkoevgeny.shared.foundation.testing.architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.container.KoScope
import com.lemonappdev.konsist.api.verify.assertFalse
import com.lemonappdev.konsist.api.verify.assertTrue
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import java.io.File

/**
 * Composition-based architecture assertions for ensuring correctness across
 * DTOs, network routes, and localization files in `shared-foundation` and downstream SDKs.
 */
public object FoundationArchitectureAssertions {

    /**
     * Resolves the root directory of the repository reliably across multi-module Gradle builds.
     * Iterates parent directories until a `settings.gradle.kts` or `settings.gradle` file is found.
     *
     * @return The absolute canonical path to the repository root directory.
     */
    public fun resolveProjectRoot(): File {
        var current = File(".").canonicalFile
        while (current.parentFile != null) {
            if (File(current, "settings.gradle.kts").exists() || File(current, "settings.gradle").exists()) {
                return current
            }
            current = current.parentFile
        }
        return File(".").canonicalFile
    }

    /**
     * Verifies that no class names end with `InternalPayload`.
     * Internal models must never be serialized into wire DTO payloads.
     *
     * @param scope The Konsist scope to verify.
     */
    public fun assertNoInternalPayloadSuffix(scope: KoScope = Konsist.scopeFromProduction()) {
        scope
            .classes()
            .assertFalse(testName = "assertNoInternalPayloadSuffix") { it.name.endsWith("InternalPayload") }
    }

    /**
     * Verifies that `FilterValues` and `SortBy` domain classes are declared as top-level classes.
     *
     * @param scope The Konsist scope to verify.
     */
    public fun assertTopLevelFilterAndSortClasses(scope: KoScope = Konsist.scopeFromProduction()) {
        scope
            .classes()
            .assertTrue(testName = "assertTopLevelFilterAndSortClasses") {
                if (it.name.endsWith("FilterValues") || it.name.endsWith("SortBy")) {
                    it.isTopLevel
                } else {
                    true
                }
            }
    }

    /**
     * Verifies that network API route constants documented in `*Routes.kt` files include required KDoc sections.
     *
     * @param scope The Konsist scope to verify.
     */
    public fun assertApiRouteKDocSections(scope: KoScope = Konsist.scopeFromProduction()) {
        scope
            .properties()
            .assertTrue(testName = "assertApiRouteKDocSections") { property ->
                val containingFile = property.containingFile.name
                if (containingFile.endsWith("Routes.kt") && property.name != "PATH_PREFIX") {
                    val kDoc = property.hasKDoc
                    if (!kDoc) return@assertTrue false
                    val content = property.kDoc?.text ?: ""
                    content.contains("HTTP method:") &&
                        content.contains("Request body:") &&
                        content.contains("Response body:") &&
                        content.contains("UI Presentation:") &&
                        content.contains("Audit logging:")
                } else {
                    true
                }
            }
    }

    /**
     * Verifies that all JSON keys within `localization/**/*.json` files are written in strict `snake_case`.
     *
     * @param rootDirectory Root directory of the repository to scan.
     * @param excludedFiles Optional set of filenames to exclude from the check.
     * @param excludedKeysByFile Optional map of filename to set of keys to exclude from the check.
     */
    public fun assertLocalizationJsonKeysSnakeCase(
        rootDirectory: File = resolveProjectRoot(),
        excludedFiles: Set<String> = emptySet(),
        excludedKeysByFile: Map<String, Set<String>> = mapOf("email_templates.json" to setOf("subject", "body"))
    ) {
        val jsonFiles = rootDirectory
            .walkTopDown()
            .onEnter { dir ->
                val dirName = dir.name
                dirName != "build" && dirName != ".gradle"
            }
            .filter { it.isFile && it.extension == "json" && it.path.contains("localization") }
            .filter { it.name !in excludedFiles }
            .toList()

        val snakeCaseRegex = Regex("^[a-z0-9_]+$")

        jsonFiles.forEach { file ->
            val content = file.readText()
            val jsonElement = Json.parseToJsonElement(content)
            val excludedKeys = excludedKeysByFile[file.name] ?: emptySet()
            verifySnakeCaseKeys(jsonElement, file, snakeCaseRegex, excludedKeys)
        }
    }

    private fun verifySnakeCaseKeys(
        jsonElement: JsonElement,
        file: File,
        snakeCaseRegex: Regex,
        excludedKeys: Set<String>
    ) {
        when (jsonElement) {
            is JsonObject -> {
                jsonElement.entries.forEach { (key, value) ->
                    if (key !in excludedKeys) {
                        check(snakeCaseRegex.matches(key)) {
                            "Key '$key' in ${file.name} is not snake_case"
                        }
                    }
                    verifySnakeCaseKeys(value, file, snakeCaseRegex, excludedKeys)
                }
            }

            is JsonArray -> {
                jsonElement.forEach { item ->
                    verifySnakeCaseKeys(item, file, snakeCaseRegex, excludedKeys)
                }
            }

            else -> Unit
        }
    }

    /**
     * Executes all consumer-facing architecture assertions simultaneously.
     *
     * @param scope The Konsist scope to verify.
     * @param rootDirectory Root directory of the repository to scan for JSONs.
     * @param localizationExcludedFiles Set of filenames to exclude from localization checks.
     * @param localizationExcludedKeysByFile Map of filename to set of keys to exclude from localization checks.
     */
    public fun assertAllConsumerRules(
        scope: KoScope = Konsist.scopeFromProduction(),
        rootDirectory: File = resolveProjectRoot(),
        localizationExcludedFiles: Set<String> = emptySet(),
        localizationExcludedKeysByFile: Map<String, Set<String>> = mapOf(
            "email_templates.json" to setOf("subject", "body")
        )
    ) {
        assertNoInternalPayloadSuffix(scope)
        assertTopLevelFilterAndSortClasses(scope)
        assertApiRouteKDocSections(scope)
        assertLocalizationJsonKeysSnakeCase(rootDirectory, localizationExcludedFiles, localizationExcludedKeysByFile)
    }
}
