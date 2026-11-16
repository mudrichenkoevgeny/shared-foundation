package io.github.mudrichenkoevgeny.shared.foundation.testing.architecture

import kotlin.test.Test

/**
 * Local architecture test execution for `shared-foundation` via [FoundationArchitectureAssertions].
 */
class ArchitectureTest {

    @Test
    fun `no class should have InternalPayload suffix`() {
        FoundationArchitectureAssertions.assertNoInternalPayloadSuffix()
    }

    @Test
    fun `FilterValues and SortBy classes should be top-level`() {
        FoundationArchitectureAssertions.assertTopLevelFilterAndSortClasses()
    }

    @Test
    fun `API network route constants must have required KDoc sections`() {
        FoundationArchitectureAssertions.assertApiRouteKDocSections()
    }

    @Test
    fun `localization JSON keys should be snake_case`() {
        FoundationArchitectureAssertions.assertLocalizationJsonKeysSnakeCase()
    }
}
