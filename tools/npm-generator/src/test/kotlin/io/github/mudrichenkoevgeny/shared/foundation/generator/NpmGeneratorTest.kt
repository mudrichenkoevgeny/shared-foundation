package io.github.mudrichenkoevgeny.shared.foundation.generator

import io.github.mudrichenkoevgeny.shared.foundation.generator.mapper.generateDomainModelsAndMappersJs
import io.github.mudrichenkoevgeny.shared.foundation.generator.route.generateEnumsJs
import io.github.mudrichenkoevgeny.shared.foundation.generator.route.generateErrorCodeObjects
import io.github.mudrichenkoevgeny.shared.foundation.generator.route.generatePermissionCodeObjects
import io.github.mudrichenkoevgeny.shared.foundation.generator.route.generateRouteObjects
import io.github.mudrichenkoevgeny.shared.foundation.generator.schema.generateBrandedTypes
import io.github.mudrichenkoevgeny.shared.foundation.generator.schema.generateDtoSchemasAndTypes
import io.github.mudrichenkoevgeny.shared.foundation.generator.testing.generateTestFixturesJs
import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * Unit tests verifying TypeScript definitions, Zod schemas, route objects, and domain mapper generation logic in NPM Generator.
 */
class NpmGeneratorTest {
    @Test
    fun testGenerateBrandedTypes() {
        val result = generateBrandedTypes()

        assertTrue(result.contains("export function toUserIdOrNull"))
        assertTrue(result.contains("export function toUserIdOrThrow"))
        assertTrue(result.contains("export const userIdSchema = z.string()"))
        assertTrue(result.contains("export function toFileIdOrNull"))
        assertTrue(result.contains("export function toAuditEventIdOrNull"))
    }

    @Test
    fun testGenerateDtoSchemasAndTypes() {
        val result = generateDtoSchemasAndTypes()

        assertTrue(result.contains("export function pagedResultSchema"))
        assertTrue(result.contains("export const clientInfoPayloadSchema = z.object({"))
        assertTrue(result.contains("export const userSummaryPayloadSchema = z.object({"))
        assertTrue(result.contains("export const storageFileSummaryPayloadSchema = z.object({"))
        assertTrue(result.contains("export const auditEventSummaryPayloadSchema = z.object({"))
    }

    @Test
    fun testGenerateRouteObjects() {
        val result = generateRouteObjects()

        assertTrue(result.isNotEmpty())
        assertTrue(result.contains("export const "))
    }

    @Test
    fun testGenerateErrorCodeObjects() {
        val result = generateErrorCodeObjects()

        assertTrue(result.contains("export const CommonErrorCodes"))
        assertTrue(result.contains("export const CommonErrorArgs"))
    }

    @Test
    fun testGeneratePermissionCodeObjects() {
        val result = generatePermissionCodeObjects()

        assertTrue(result.contains("export const AuditPermissionCode") || result.contains("export const SecurityPermissionCode"))
    }

    @Test
    fun testGenerateEnumsJs() {
        val result = generateEnumsJs()

        assertTrue(result.contains("ClientType") || result.contains("UserRole") || result.contains("StorageCategory"))
    }

    @Test
    fun testGenerateDomainModelsAndMappersJs() {
        val result = generateDomainModelsAndMappersJs()

        assertTrue(result.isNotEmpty())
        assertTrue(result.contains("function ") || result.contains("export "))
    }

    @Test
    fun testGenerateTestFixturesJs() {
        val result = generateTestFixturesJs()

        assertTrue(result.isNotEmpty())
    }
}
