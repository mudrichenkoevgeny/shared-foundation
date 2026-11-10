package io.github.mudrichenkoevgeny.shared.foundation.testing.architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.verify.assertFalse
import com.lemonappdev.konsist.api.verify.assertTrue
import java.io.File
import kotlin.test.Test
import kotlin.test.assertTrue

public class ArchitectureTest {
    @Test
    public fun noClassShouldHaveInternalPayloadSuffix() {
        Konsist
            .scopeFromProject()
            .classes()
            .assertFalse { it.name.endsWith("InternalPayload") }
    }

    @Test
    public fun filterValuesAndSortByClassesShouldBeTopLevel() {
        Konsist
            .scopeFromProject()
            .classes()
            .assertTrue {
                if (it.name.endsWith("FilterValues") || it.name.endsWith("SortBy")) {
                    it.isTopLevel
                } else {
                    true
                }
            }
    }

    @Test
    public fun apiNetworkRouteConstantsMustHaveRequiredKDocSections() {
        Konsist
            .scopeFromProject()
            .properties()
            .assertTrue { property ->
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

    @Test
    public fun localizationJsonKeysShouldBeSnakeCase() {
        val rootDir = File(".")
        val jsonFiles = rootDir
            .walkTopDown()
            .filter { it.isFile && it.extension == "json" && it.path.contains("localization") }
            .toList()

        val snakeCaseRegex = Regex("^[a-z0-9_]+$")
        jsonFiles.forEach { file ->
            val content = file.readText()
            val keyRegex = Regex(""""([^"]+)":""")
            keyRegex.findAll(content).forEach { match ->
                val key = match.groupValues[1]
                if (file.name == "email_templates.json" && (key == "subject" || key == "body")) {
                    return@forEach
                }
                assertTrue(
                    snakeCaseRegex.matches(key),
                    "Key '$key' in ${file.name} is not snake_case"
                )
            }
        }
    }
}
