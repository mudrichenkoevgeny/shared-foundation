package io.github.mudrichenkoevgeny.shared.foundation.generator

import io.github.mudrichenkoevgeny.npmgenerator.engine.NpmGeneratorConfig
import io.github.mudrichenkoevgeny.npmgenerator.engine.NpmGeneratorEngine
import io.github.mudrichenkoevgeny.npmgenerator.localization.generateLocalizationCode
import io.github.mudrichenkoevgeny.npmgenerator.util.sortDtosTopologically
import io.github.mudrichenkoevgeny.shared.foundation.generator.foundation.buildFoundationNpmConfig
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserSummaryPayload
import java.io.File
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Unit tests verifying TypeScript definitions, Zod schemas, route objects, and domain mapper generation logic in NPM Generator.
 */
class NpmGeneratorTest {

    private fun resolveRootDir(): File {
        val current = File(".").canonicalFile
        return if (current.name == "foundation-npm-generator" || current.name == "npm-generator") current.parentFile.parentFile else current
    }

    private fun createTempTestDir(prefix: String): File {
        val tempFile = File.createTempFile(prefix, "dir")
        tempFile.delete()
        tempFile.mkdirs()
        return tempFile
    }

    @Test
    fun testFullFoundationGeneration() {
        val tempDir = createTempTestDir("npm-test-full-foundation")
        try {
            val rootDir = resolveRootDir()
            val config = buildFoundationNpmConfig(rootDir, tempDir, "0.0.1", "^3.24.2", "^5.5.0")

            val engine = NpmGeneratorEngine(config)
            engine.generate()

            val indexJs = File(tempDir, "index.js").readText()
            val indexDts = File(tempDir, "index.d.ts").readText()

            assertTrue(indexJs.contains("export function toUserIdOrNull"))
            assertTrue(indexJs.contains("export function toUserIdOrThrow"))
            assertTrue(indexJs.contains("export const userIdSchema = z.string()"))
            assertTrue(indexJs.contains("export const userSummaryPayloadSchema = z.object({"))

            assertTrue(indexJs.contains("ENGLISH: \"en\""), "CommonLocales JS must contain ENGLISH property")
            assertTrue(indexJs.contains("RUSSIAN: \"ru\""), "CommonLocales JS must contain RUSSIAN property")
            assertTrue(indexDts.contains("readonly ENGLISH: string"), "CommonLocales DTS must contain ENGLISH property declaration")

            assertTrue(indexJs.contains("export const userStrings ="), "JS must export userStrings localization singleton")
            assertTrue(indexJs.contains("export const commonStrings ="), "JS must export commonStrings localization singleton")
            assertTrue(indexJs.contains("export const securityStrings ="), "JS must export securityStrings localization singleton")
            assertTrue(indexJs.contains("export const storageStrings ="), "JS must export storageStrings localization singleton")
            assertTrue(indexDts.contains("export declare const userStrings:"), "DTS must declare userStrings localization singleton")

            assertTrue(indexJs.contains("export const sharedLocalization ="), "JS must export sharedLocalization facade")
            assertTrue(indexDts.contains("export declare const sharedLocalization:"), "DTS must declare sharedLocalization facade")

            assertTrue(indexJs.contains("resolvePasswordTooWeakReasons"), "JS must contain resolvePasswordTooWeakReasons")
            assertTrue(indexDts.contains("resolvePasswordTooWeakReasons"), "DTS must contain resolvePasswordTooWeakReasons declaration")

            assertTrue(indexDts.contains("export interface UserSummaryPayload"))
            assertTrue(indexDts.contains("export const userSummaryPayloadSchema: z.ZodType<UserSummaryPayload>;"))
            assertFalse(indexDts.contains("UserFilterValues: Record<string, string>"))
            assertTrue(indexDts.contains("readonly USER_ID: \"user_id\";"))
            assertTrue(indexDts.contains("reasons: PasswordPolicyFailReason[]"))
            assertFalse(indexDts.contains("Partial<any>"), "Generated index.d.ts must not contain Partial<any>")
            assertFalse(indexDts.contains(": any;"), "Generated index.d.ts must not contain : any;")
        } finally {
            tempDir.deleteRecursively()
        }
    }

    @Test
    fun testEnumWireValueResolution() {
        val tempDir = createTempTestDir("npm-test-enum")
        try {
            val rootDir = resolveRootDir()
            val config = NpmGeneratorConfig(
                packageName = "@mudrichenkoevgeny/shared-foundation",
                packageVersion = "0.0.1",
                packageDescription = "Test package",
                outputDirectory = tempDir,
                sourceCodeDirectory = File(rootDir, "shared/foundation"),
                zodVersion = "^3.24.2",
                typescriptVersion = "^5.5.0",
                enumClasses = listOf(UserRole::class),
                enumEnhancers = mapOf("UserRole" to listOf("USER" to "user", "CLIENT_USER" to "user", "MANAGEMENT_USER" to "admin"))
            )
            val engine = NpmGeneratorEngine(config)
            engine.generate()

            val indexJs = File(tempDir, "index.js").readText()
            assertTrue(indexJs.contains("USER: \"user\""), "Enum should output lowercase/wire value 'user', got: $indexJs")
            assertTrue(indexJs.contains("MANAGEMENT_USER: \"admin\""), "Enum should map MANAGEMENT_USER to 'admin', got: $indexJs")
        } finally {
            tempDir.deleteRecursively()
        }
    }

    @Test
    fun testDownstreamPackageFoundationIsolation() {
        val tempDir = createTempTestDir("npm-test-hivepick")
        try {
            val rootDir = resolveRootDir()
            val config = NpmGeneratorConfig(
                packageName = "@hivepick/shared",
                packageVersion = "1.0.0",
                packageDescription = "Hivepick Shared SDK",
                outputDirectory = tempDir,
                sourceCodeDirectory = File(rootDir, "shared/foundation"),
                zodVersion = "^3.24.2",
                typescriptVersion = "^5.5.0",
                dtoClasses = listOf(UserSummaryPayload::class),
                basePackagePrefix = "io.github.mudrichenkoevgeny.shared.foundation",
                foundationImportPackage = "@mudrichenkoevgeny/shared-foundation"
            )

            val engine = NpmGeneratorEngine(config)
            engine.generate()

            val indexJs = File(tempDir, "index.js").readText()
            val indexDts = File(tempDir, "index.d.ts").readText()

            assertFalse(indexJs.contains("ListingParamNames"), "Downstream package should not contain foundation ListingParamNames")
            assertFalse(indexDts.contains("AuditEventFilterValues"), "Downstream package should not contain foundation AuditEventFilterValues")
            assertFalse(indexJs.contains("CompositeAuditActionTypeParser"), "Downstream package should not contain foundation CompositeAuditActionTypeParser")
        } finally {
            tempDir.deleteRecursively()
        }
    }

    @Test
    fun testDownstreamCrossPackageImports() {
        val tempDir = createTempTestDir("npm-test-cross-package")
        try {
            val rootDir = resolveRootDir()
            val config = NpmGeneratorConfig(
                packageName = "@hivepick/shared",
                packageVersion = "1.0.0",
                packageDescription = "Hivepick Shared SDK",
                outputDirectory = tempDir,
                sourceCodeDirectory = File(rootDir, "shared/foundation"),
                zodVersion = "^3.24.2",
                typescriptVersion = "^5.5.0",
                dtoClasses = listOf(UserSummaryPayload::class),
                domainToDtoMappers = listOf(UserSummary::class to UserSummaryPayload::class),
                stringFieldTypeOverrides = mapOf("id" to UserId::class),
                basePackagePrefix = "io.github.mudrichenkoevgeny.shared.foundation",
                foundationImportPackage = "@mudrichenkoevgeny/shared-foundation"
            )

            val engine = NpmGeneratorEngine(config)
            engine.generate()

            val indexJs = File(tempDir, "index.js").readText()
            val indexDts = File(tempDir, "index.d.ts").readText()

            assertTrue(
                indexJs.contains("import {") && indexJs.contains("@mudrichenkoevgeny/shared-foundation"),
                "Downstream package JS should import foundation types/schemas, got: $indexJs"
            )
            assertTrue(
                indexDts.contains("import type {") && indexDts.contains("@mudrichenkoevgeny/shared-foundation"),
                "Downstream package DTS should import type declarations from foundation, got: $indexDts"
            )
        } finally {
            tempDir.deleteRecursively()
        }
    }

    @Test
    fun testCustomLocalizationNaming() {
        val rootDir = resolveRootDir()
        val locDir = File(rootDir, "shared/foundation/core/common/src/commonMain/resources/localization")
        val config = NpmGeneratorConfig(
            packageName = "@hivepick/shared",
            packageVersion = "1.0.0",
            packageDescription = "Hivepick Shared SDK",
            outputDirectory = File("build/temp-test"),
            sourceCodeDirectory = File(rootDir, "shared/foundation"),
            zodVersion = "^3.24.2",
            typescriptVersion = "^5.5.0",
            localizationJsonDirectory = locDir,
            basePackagePrefix = "io.github.mudrichenkoevgeny.shared.foundation",
            foundationImportPackage = "@mudrichenkoevgeny/shared-foundation"
        )

        val (jsCode, dtsCode) = generateLocalizationCode(config)
        assertTrue(jsCode.contains("HivepickSharedLocalization"), "JS should contain custom localization class HivepickSharedLocalization, got: $jsCode")
        assertTrue(dtsCode.contains("HivepickSharedLocalization"), "DTS should contain custom localization class HivepickSharedLocalization")
        assertTrue(jsCode.contains("hivepickSharedLocalization"), "JS should contain custom singleton hivepickSharedLocalization")
    }

    @Test
    fun testTopologicalSortingOfDtos() {
        val sorted = sortDtosTopologically(listOf(UserSummaryPayload::class))
        assertTrue(sorted.contains(UserSummaryPayload::class))
    }
}
