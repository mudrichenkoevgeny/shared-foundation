package io.github.mudrichenkoevgeny.npmgenerator.engine

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.File
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@JvmInline
value class SampleId(val value: String)

enum class SampleRole {
    ADMIN,
    USER
}

@Serializable
data class SampleDto(
    val id: String,
    val role: SampleRole,
    @SerialName("optional_field")
    val optionalField: String? = null
)

@Serializable
data class SampleParams(
    val query: String,
    val pageNumber: Int = 1
)

data class SampleDomain(
    val id: SampleId,
    val role: SampleRole,
    val optionalField: String?
)

object SampleRoutes {
    const val HOME = "/home"
    object Auth {
        const val LOGIN = "/auth/login"
    }
}

object SampleErrorCodes {
    const val UNAUTHORIZED = "UNAUTHORIZED"
    const val NOT_FOUND = "NOT_FOUND"
}

class SamplePermissionCode(val value: String)

object SamplePermissions {
    val READ = SamplePermissionCode("sample:read")
    val WRITE = SamplePermissionCode("sample:write")
}

@JvmInline
value class BaseUserId(val value: String)

enum class BaseRole {
    ADMIN,
    USER
}

@Serializable
data class DownstreamDto(
    val id: String,
    val userId: BaseUserId,
    val role: BaseRole
)

@Serializable
data class DownstreamParams(
    val query: String,
    val pageNumber: Int = 1
)

class NpmGeneratorEngineTest {

    @Test
    fun testFoundationBasePackageGeneration() {
        val tempDir = Files.createTempDirectory("npm_gen_base_test").toFile()
        try {
            val srcDir = File(tempDir, "src").also { it.mkdirs() }
            val outDir = File(tempDir, "out").also { it.mkdirs() }

            val config = NpmGeneratorConfig(
                packageName = "@sample/base-package",
                packageVersion = "1.0.0",
                packageDescription = "Sample Base Package Test",
                outputDirectory = outDir,
                sourceCodeDirectory = srcDir,
                dtoClasses = listOf(SampleDto::class, SampleParams::class),
                enumClasses = listOf(SampleRole::class),
                brandedTypes = listOf(SampleId::class, SamplePermissionCode::class),
                errorClasses = listOf(SampleErrorCodes::class),
                permClasses = listOf(SamplePermissions::class),
                routeObjects = listOf(SampleRoutes::class),
                contractClasses = emptyList(),
                domainToDtoMappers = listOf(SampleDomain::class to SampleDto::class),
                zodVersion = "^3.24.2",
                typescriptVersion = "^5.5.0"
            )

            val engine = NpmGeneratorEngine(config)
            engine.generate()

            val pkgJson = File(outDir, "package.json").readText()
            assertTrue(pkgJson.contains("\"type\": \"module\""))
            assertTrue(pkgJson.contains("\"name\": \"@sample/base-package\""))

            val indexJs = File(outDir, "index.js").readText()
            assertTrue(indexJs.contains("export const sampleRoleSchema"))
            assertTrue(indexJs.contains("export const sampleDtoSchema"))
            assertTrue(indexJs.contains("export const SampleRoutes"))
            assertTrue(indexJs.contains("export const SampleErrorCodes"))
            assertTrue(indexJs.contains("export const SamplePermissions"))
            assertTrue(indexJs.contains("toSampleDomain"))
            assertFalse(indexJs.contains("INSTANCE:"))

            val configWithSmoke = config.copy(
                customRuntimeSmokeTestCode = """
                    if (typeof Pkg.createTestSampleDtoList === 'function') {
                      const list = Pkg.createTestSampleDtoList(2);
                      if (!Array.isArray(list) || list.length !== 2) errors++;
                    }
                    if (typeof Pkg.SampleParamsParser === 'object') {
                      const str = Pkg.SampleParamsParser.toSearchString({ query: "abc", pageNumber: 1 });
                      if (typeof str !== 'string') errors++;
                    }
                """.trimIndent()
            )
            val engineWithSmoke = NpmGeneratorEngine(configWithSmoke)
            engineWithSmoke.generate()

            val indexDts = File(outDir, "index.d.ts").readText()
            assertTrue(indexDts.contains("export type Instant = number;"))
            assertTrue(indexDts.contains("export interface PagedResult<T>"))
            assertTrue(indexDts.contains("export interface SampleDomain"))
            assertTrue(indexDts.contains("export declare const SamplePermissions"))

            assertFalse(indexDts.contains("Partial<any>"))
            assertFalse(indexDts.contains(": any;"))
        } finally {
            tempDir.deleteRecursively()
        }
    }

    @Test
    fun testDownstreamPackageGeneration() {
        val tempDir = Files.createTempDirectory("npm_gen_downstream_test").toFile()
        try {
            val srcDir = File(tempDir, "src").also { it.mkdirs() }
            val outDir = File(tempDir, "out").also { it.mkdirs() }

            val config = NpmGeneratorConfig(
                packageName = "@sample/downstream-package",
                packageVersion = "1.0.0",
                packageDescription = "Sample Downstream Package Test",
                outputDirectory = outDir,
                sourceCodeDirectory = srcDir,
                dtoClasses = listOf(DownstreamDto::class, DownstreamParams::class),
                enumClasses = emptyList(),
                brandedTypes = emptyList(),
                basePackagePrefix = "io.github.mudrichenkoevgeny.npmgenerator.engine",
                foundationImportPackage = "@sample/base-package",
                autoImportSymbols = setOf(
                    "BaseRole", "baseRoleSchema", "BaseUserId", "toBaseUserIdOrThrow",
                    "toQueryMap", "toQuerySearchString", "createTestList"
                ),
                brandedTypesNeedingHelpers = setOf("BaseUserId"),
                zodVersion = "^3.24.2",
                typescriptVersion = "^5.5.0"
            )

            val engine = NpmGeneratorEngine(config)
            engine.generate()

            val indexJs = File(outDir, "index.js").readText()
            assertTrue(indexJs.contains("export const downstreamDtoSchema"))
            assertTrue(indexJs.contains("from '@sample/base-package'"))
            assertTrue(indexJs.contains("toBaseUserIdOrThrow"))
            assertTrue(indexJs.contains("baseRoleSchema"))
            assertTrue(indexJs.contains("toQueryMap"))
            assertTrue(indexJs.contains("createTestList"))

            val indexDts = File(outDir, "index.d.ts").readText()
            assertTrue(indexDts.contains("import type { BaseRole, BaseUserId } from '@sample/base-package';"))
            assertFalse(indexDts.contains("export type Instant = number;"))
            assertFalse(indexDts.contains("Partial<any>"))
            assertFalse(indexDts.contains(": any;"))
        } finally {
            tempDir.deleteRecursively()
        }
    }

    @Test
    fun testCleanJsContent() {
        val config = NpmGeneratorConfig(
            packageName = "test",
            packageVersion = "1.0.0",
            packageDescription = "test",
            outputDirectory = File("."),
            sourceCodeDirectory = File("."),
            zodVersion = "^3.24.2",
            typescriptVersion = "^5.5.0"
        )
        val engine = NpmGeneratorEngine(config)

        val jsInput = """
            // Line comment with { braces }
            /* Multi-line comment
               with { braces } */
            const text = "String with { braces } and // comments";
            function test() {
              return 42;
            }
        """.trimIndent()

        val clean = engine.cleanJsContent(jsInput)
        assertFalse(clean.contains("Line comment"))
        assertFalse(clean.contains("Multi-line comment"))
        assertFalse(clean.contains("String with"))
        assertTrue(clean.contains("function test()"))
    }
}
