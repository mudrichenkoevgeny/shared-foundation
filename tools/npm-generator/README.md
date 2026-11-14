# tools/npm-generator (`shared-foundation-npm-generator`)

Pure, domain-agnostic JVM code generation framework and reusable engine for building TypeScript / JavaScript NPM package artifacts (**`@mudrichenkoevgeny/shared-foundation`** and downstream SDK packages such as **`@hivepick/shared`**).

Published to Maven Central as **`io.github.mudrichenkoevgeny:shared-foundation-npm-generator:<version>`**.

It bridges the Kotlin Multiplatform (KMP) ecosystem and TypeScript/React Web applications by acting as a **Single Source of Truth (SSOT)** for all wire payloads, REST API routes, navigation screen routes, screen parameters, Zod schemas, error codes, permission models, domain mappers, localization bundles, and test factories.

---

## Generator Architecture & Core Engine (`NpmGeneratorEngine`)

The generator core is completely generic, pure, and decoupled from domain-specific contracts:

- **`NpmGeneratorEngine`**: Drives package creation, topological schema sorting, cross-package foundation import resolution, TypeScript `.d.ts` declaration generation, and native output verification via `NpmPackageVerifier`.
- **`NpmGeneratorConfig`**: Configuration data class defining package metadata, target output directory, Kotlin source directories, localization/testing directories, class targets (`dtoClasses`, `enumClasses`, `brandedTypes`, `errorClasses`, `permClasses`, `routeObjects`, `contractClasses`, `domainToDtoMappers`, `testFixtures`), and custom configuration hooks:
  - `zodVersion`: Configurable Zod version string (defaults to `"^3.24.2"`).
  - `customDtsSmokeTestCode`: Custom TypeScript smoke test code evaluated during `tsc` verification.
  - `customRuntimeSmokeTestCode`: Custom JavaScript smoke test code evaluated during Node.js V8 ESM runtime verification.
  - `customEnumExtractor`: Lambda hook for custom enum wire value extraction.
  - `customMatchersAndParsersJs` / `customMatchersAndParsersDts`: Extra composite parser code.
  - `fieldAliases`: Alias mapping for domain-to-DTO property conversion.
  - `enumEnhancers`: Extra wire value pairs for existing enums.
  - `idMockMappings`, `ownerIdMockMappings`, `stringMockMappings`, `numberMockMappings`, `brandedTypeMockValues`: Configurable mock generators for test fixtures.
  - `localizationRootClassName`, `localizationManagerName`, `byteSizeUnitModulePath`, `byteSizeUnitBundleName`: Custom localization facade naming and byte unit bundle configuration.
- **`NpmPackageVerifier`**: Cross-platform verifier executed automatically at the end of `engine.generate()`.
- **`KDocExtractor`**: Extracts KDoc comment blocks (`/** ... */`) from Kotlin source code and converts them into rich TypeScript JSDoc hover comments.

---

## What It Generates (`build/npm-package/`)

Running `NpmGeneratorEngine(config).generate()` produces the following files in `outputDirectory`:

1. **`package.json`**: NPM package descriptor with `"type": "module"`, package metadata (`packageName`, `packageVersion`), and Zod dependency (`"zod": "^3.24.2"`).
2. **`index.d.ts`**: TypeScript declaration file exporting:
   - **TypeScript Interfaces & Types**: Typed models for DTOs (`UserSummaryPayload`), Domain Models (`UserSummary`), Navigation Screen Parameters (`ManagementUserListParams`), UI Component Configurations (`StorageFileUploadUiConfig`), and base types (`Instant`, `PagedResult<T>`).
   - **JSDoc Documentation**: Rich JSDoc hover comments (`/** ... */`) extracted from KDocs for all classes, properties, routes, and mappers.
   - **Branded Identifier Types**: Type-safe IDs (`UserId`, `UserSessionId`, `UserIdentifierId`, `AuditEventId`, `FileId`, `ClientDeviceId`, `PermissionCode`, `DecryptedString`, `EncryptedString`, `PasswordHash`, `AccessToken`, `RefreshToken`, `RefreshTokenHash`).
   - **REST & Navigation Route Objects**: Strongly typed route constants (`ManagementUsersRoutes`, `ManagementUsersNavigation`, `ClientAuthNavigation`, etc.).
   - **Localization Classes & Types**: Type-safe bundle classes, module facades (`CommonModuleStrings`, `SecurityModuleStrings`, `UserModuleStrings`), root facade (`SharedFoundationLocalization`), and key types (`*RawKey`, `*Property`).
   - **Test Fixture Types & Overrides**: Strict `Overrides<T>` types (`export type Overrides<T> = Partial<T> | ((index: number) => Partial<T>);`) for test factories.
3. **`index.js` (ESM)**: JavaScript runtime module exporting:
   - **Zod Validation Schemas (`*Schema`)**: Auto-generated `z.ZodType` schemas for all payloads, DTOs, and Screen Parameter models with safe defaults for optional parameters (`.default(...)`, `.optional()`).
   - **Parameter Serializers & Parsers**: Runtime parser objects (`*ParamsParser`), serializers (`serialize*Params`, `toQueryMap*Params`), and `toQueryMap` / `toQuerySearchString` for converting parameter objects to query parameter maps while skipping default values (`pageNumber=1`, `pageSize=20`, `sortBy='default'`), mapping plural keys (`userRoles` -> `role`, `phone` -> `phone_number`), and stripping path parameters (`userId`, `identifierId`, `sessionId`, `eventId`).
   - **Runtime Enums**: TS types & runtime JS objects with wire values from `@SerialName` / `getSerialName()` / `lowercase` (`UserRole`, `AuditStatus`, `TotpStep`, `UnlockMethod`, `ExternalAuthProvider`, etc.).
   - **Runtime Mappers**: Mapping functions (`toAuditEventSummary`, `toUserPrivatePayload`, `toAuditEventPrivate`).
   - **Polymorphic Composite Parsers**: Runtime parsers (`CompositeAuditActionTypeParser`, `CompositeAuditMetadataKeyParser`, `CompositeAuditResourceTypeParser`).
   - **Validators & Helper Utilities**: `PasswordPolicyValidator`, `passwordPolicyValidator`, `storageUploadValidator`, `nicknameValidator`, `displayNameValidator`, `resolveUserDisplayName`.
   - **Testing Fixtures & Collection Generators**: Constants (`TEST_USER_ID`, `TEST_FILE_ID`, `TEST_TIME_MILLIS`, `SharedTestConstants`), mock factories (`createTestUserSummary`, `createTestUserPrivatePayload`), auto-calculating `createTestPagedResult`, UUID generator `generateTestUuidString(index, group)`, and 0-based index list generator `createTestList(count, factory)`.
   - **Localization Bundles & Formatters**: Raw JSON dictionary constant (`localizationResources`), string template formatter (`formatLocalizedString`), default resolver (`defaultLocalizedStringResolver`), bundle classes, and module facades.
4. **`README.md`**: Package user guide.

---

## Built-in Verification Pipeline (`NpmPackageVerifier`)

Every invocation of `NpmGeneratorEngine(config).generate()` automatically executes `NpmPackageVerifier`:

1. **Artifacts Presence Check**: Verifies existence of `package.json`, `index.js`, `index.d.ts`, `README.md`, `.npmignore`.
2. **JS Syntax Verification**: Runs `node --check index.js` using Node.js V8.
3. **`npm pack --dry-run` Check**: Verifies archive layout in `outputDirectory`.
4. **TypeScript Strict Declaration Compilation**:
   - Generates temporary `test-types.ts` validating all DTO schemas and `config.customDtsSmokeTestCode`.
   - Uses an isolated verifier cache in `build/npm-verifier-env` containing exact Zod v3 (`^3.24.2`) and TypeScript (`^5.5.0`) dependencies linked via directory junction (`mklink /J` on Windows, symlink on Unix).
   - Executes `tsc --noEmit --strict --skipLibCheck test-types.ts`.
5. **Runtime Behavior Verification**:
   - Generates temporary `test-runtime.mjs` executing `index.js` in Node.js ESM mode.
   - Tests branded type validation (`to<Name>OrThrow`), mock factories, and `config.customRuntimeSmokeTestCode`.
   - Safely cleans up junction links without modifying root `node_modules`.

---

## How Downstream Libraries (e.g. `hivepick-shared`) Use This Generator

Downstream KMP projects (such as `hivepick-shared` or `@hivepick/shared`) consume `shared-foundation-npm-generator` as a Gradle dependency.

### Integration Guide for `hivepick-shared`:

#### Step 1: Add Dependency in `build.gradle.kts`
In the downstream tool/generator subproject `tools/npm-generator/build.gradle.kts`:

```kotlin
dependencies {
    // Depend on the published NPM generator engine
    implementation("io.github.mudrichenkoevgeny:shared-foundation-npm-generator:<version>")

    // Depend on downstream domain/network modules
    implementation(project(":shared:hivepick:feature:quiz"))
}
```

#### Step 2: Create Downstream Generator Main Entry Point
In downstream project `tools/npm-generator/src/main/kotlin/com/hivepick/shared/generator/HivepickNpmGenerator.kt`:

```kotlin
package com.hivepick.shared.generator

import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.engine.NpmGeneratorConfig
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.engine.NpmGeneratorEngine
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import java.io.File

fun main() {
    val rootDir = File(System.getProperty("user.dir"))
    val outputDir = File(rootDir, "build/npm-package")

    val config = NpmGeneratorConfig(
        packageName = "@hivepick/shared",
        packageVersion = "1.0.0",
        packageDescription = "Hivepick Shared TypeScript SDK - Zod schemas and API contracts",
        peerDependencies = mapOf(
            "@mudrichenkoevgeny/shared-foundation" to "^0.0.71"
        ),
        outputDirectory = outputDir,
        sourceCodeDirectory = File(rootDir, "shared/hivepick"),
        localizationJsonDirectory = File(rootDir, "shared/hivepick/resources/localization"),
        testingSourceDirectory = File(rootDir, "shared/hivepick/testing/src/commonMain/kotlin"),

        // Specify Hivepick-specific DTOs, Enums, Mappers, Routes, and Contracts
        dtoClasses = listOf(
            QuizSummaryPayload::class,
            QuizDetailPayload::class,
            CreateQuizRequest::class
        ),
        enumClasses = listOf(
            QuizStatus::class,
            QuestionType::class
        ),
        domainToDtoMappers = listOf(
            QuizSummary::class to QuizSummaryPayload::class,
            QuizDetail::class to QuizDetailPayload::class
        ),
        routeObjects = listOf(
            QuizRoutes::class
        ),

        // Map DTO fields to Foundation branded types
        stringFieldTypeOverrides = mapOf(
            "authorId" to UserId::class
        ),

        // Downstream cross-package import settings
        basePackagePrefix = "com.hivepick.shared",
        foundationImportPackage = "@mudrichenkoevgeny/shared-foundation",
        autoImportSymbols = setOf("UserId", "toUserIdOrThrow", "toQueryMap", "createTestList"),

        // Optional custom smoke test code
        customDtsSmokeTestCode = """
            const assertQuiz: Pkg.QuizSummaryPayload = Pkg.quizSummaryPayloadSchema.parse({});
        """.trimIndent(),
        customRuntimeSmokeTestCode = """
            if (Pkg.createTestQuizSummaryPayload) {
              const quiz = Pkg.createTestQuizSummaryPayload();
              if (!quiz) errors++;
            }
        """.trimIndent()
    )

    // Run the engine (automatically invokes NpmPackageVerifier)
    val engine = NpmGeneratorEngine(config)
    engine.generate()

    println("Successfully generated @hivepick/shared in ${outputDir.absolutePath}")
}
```

#### Step 3: What Happens During Downstream Generation?
1. **Zero Duplication**: `NpmGeneratorEngine` generates `@hivepick/shared` containing only Hivepick DTOs, Enums, Routes, and Mappers.
2. **Auto-Import Foundation Types**: Any reference to `UserId`, `FileId`, `PermissionCode`, `pagedResultSchema`, `toQueryMap`, `formatLocalizedString`, or `createTestList` automatically generates cross-package import statements:
   - In `index.js`: `import { userIdSchema, toUserIdOrThrow, pagedResultSchema } from '@mudrichenkoevgeny/shared-foundation';`
   - In `index.d.ts`: `import type { UserId, PagedResult } from '@mudrichenkoevgeny/shared-foundation';`
3. **Custom Localization**: Automatically generates `HivepickSharedLocalization` and `hivepickSharedLocalization` singleton, importing `formatLocalizedString` from foundation.
4. **Automatic Verification**: `NpmPackageVerifier` verifies artifacts, syntax, `npm pack --dry-run`, `tsc --noEmit --strict`, and Node.js runtime behavior before finishing.
