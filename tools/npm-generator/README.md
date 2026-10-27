# tools/npm-generator

Executable JVM tool module responsible for generating the official TypeScript / JavaScript NPM package artifact **`@mudrichenkoevgeny/shared-foundation`** inside `build/npm-package/`.

It bridges the Kotlin Multiplatform ecosystem and TypeScript/React Web applications (`web-platform-sdk`) by acting as a **Single Source of Truth (SSOT)** for all wire payloads, REST API routes, navigation screen routes, screen parameters, Zod schemas, error codes, permission models, and domain mappers.

---

## What It Generates (`build/npm-package/`)

Running `./gradlew generateNpmPackage` executes this tool and produces the following files in `build/npm-package/`:

1. **`package.json`**: NPM package descriptor with dynamically inherited version from root `build.gradle.kts` (e.g. `0.0.60`).
2. **`index.d.ts`**: TypeScript declaration file exporting:
   - **TypeScript Interfaces & Types**: Typed models for DTOs (`UserSummaryPayload`), Domain Models (`UserSummary`), Navigation Screen Parameters (`ManagementUserListParams`), and UI Component Configurations (`StorageFileUploadUiConfig`).
   - **JSDoc Documentation**: Rich JSDoc hover comments (`/** ... */`) extracted from Kotlin source KDocs for all classes, properties, routes, and mappers.
   - **Branded Identifier Types**: Type-safe IDs (`UserId`, `UserSessionId`, `UserIdentifierId`, `AuditEventId`, `ClientDeviceId`, `PermissionCode`).
   - **REST & Navigation Route Objects**: Strongly typed route constants (`ManagementUsersRoutes`, `ManagementUsersNavigation`, `ClientAuthNavigation`, etc.).
   - **Localization Classes & Types**: Type-safe bundle classes (`CommonErrorsStrings`, `CommonUiStrings`, `SecurityErrorsStrings`, `UserErrorsStrings`, `UserUiStrings`), module facades (`CommonModuleStrings`, `SecurityModuleStrings`, `UserModuleStrings`), root facade (`SharedFoundationLocalization`), and key types (`*RawKey`, `*Property`).
3. **`index.mjs` (ESM) & `index.js` (CommonJS)**: JavaScript runtime modules exporting:
   - **Zod Validation Schemas (`*Schema`)**: Auto-generated `z.ZodType` schemas for all payloads, DTOs, and Screen Parameter models (`managementAuditEventListParamsSchema`, `apiErrorResponseSchema`). Handled with safe defaults for optional parameters (`.default(...)`, `.optional()`).
   - **Parameter Serializers & Parsers**: Runtime parser objects (`*ParamsParser`), serializers (`serialize*Params`, `toQueryMap*Params`), and global helpers (`toQueryMap`, `toQuerySearchString`) for converting parameter objects to query parameter maps while omitting default values (`pageNumber=1`, `pageSize=20`).
   - **Runtime Enums**: TS types & runtime JS objects for all domain enums and string unions (`UserRole`, `AuditStatus`, `TotpStep`, `UnlockMethod`, etc.).
   - **Runtime Mappers**: Mapping functions (`toAuditEventSummary`, `toUserPrivatePayload`).
   - **Polymorphic Composite Parsers**: Runtime parsers (`CompositeAuditActionTypeParser`, `CompositeAuditMetadataKeyParser`, `CompositeAuditResourceTypeParser`).
   - **Testing Fixtures & Collection Generators**: Ready-to-use constants (`TEST_*`), mock factories (`createTestUserSummary`, `createTestUserPrivatePayload`), auto-calculating `createTestPagedResult<T>`, generic helper `createTestList<T>`, and deterministic collection generators (`createTest<Entity>List`, `createTest<Entity>PayloadList`) with 0-based index IDs, alternating providers/devices, and time shifting (`TEST_TIME_MILLIS + index * 60_000`).
   - **Localization Bundles & Formatters**: Raw JSON dictionary constant (`localizationResources`), string template formatter (`formatLocalizedString`), default resolver (`defaultLocalizedStringResolver`), bundle classes, and module facades.
4. **`README.md`**: Comprehensive documentation for NPM package consumers with installation instructions, module-by-module navigation route & Zod parameter schema (`*ParamsSchema`) index, and code examples for Zod validation, REST API calls, and router search parameter parsing.

---

## Generator Architecture & Internal Pipeline

1. **Class Collectors**:
   - `routeClasses`: Collects all REST API route objects, base network route objects (`CommonNetworkRoutes`, `BaseSettingsNetworkRoutes`), navigation path constants (`UserNavigationPaths`, `CommonNavigationRoutes`), and Navigation screen objects (`ClientMainNavigation`, `ManagementMainNavigation`, `ManagementUsersNavigation`, etc.).
   - `contractClasses`: Collects field name constants (`UserApiFields`), query params (`UserApiQueryParams`), HTTP headers, and WebSocket close/event constants.
   - `errorClasses`: Collects error code strings (`CommonErrorCodes`, `UserErrorCodes`) and error arguments.
   - `permClasses`: Collects permission code constants (`UserPermissionCode`, `AuditPermissionCode`, etc.).
   - `enumClasses`: Collects all 28 domain enums across `core/*` and `feature/*`.
   - `rawDtoClasses`: Collects all DTO payloads, request models, and screen parameter data classes (`*Params`).
   - `rawDomainToDtoClasses`: Maps Domain entity models to DTO payloads.

2. **KDoc to JSDoc Parser (`extractKDoc`)**:
   - Scans Kotlin source `.kt` files in `shared/foundation/`.
   - Extracts KDoc comment blocks (`/** ... */`) for classes, objects, interfaces, nested screen objects, properties, and enum entries.
   - Converts KDoc links (`[Type.member]`) into clean TypeScript JSDoc comments.

3. **Evaluation Order Safeguard**:
   - Emits Zod enum schemas (`$enumCode`) at the very top of `index.mjs` / `index.js` right after branded types, ensuring all enum schemas are defined BEFORE any DTO schema or mapper that references them (preventing `ReferenceError` during ESM evaluation).

4. **Optional Field & Default Value Converter**:
   - Inspects Kotlin primary constructor parameter metadata (`param.isOptional`).
   - Generates `.default(...)` or `.optional()` in Zod schemas and `?` in `index.d.ts` interfaces so `*ParamsSchema.parse({})` accepts partial/empty input maps.

---

## Execution & Workflow

```bash
# Generate the NPM package artifact in build/npm-package/
./gradlew generateNpmPackage

# Create local tarball for testing in Web monorepo (web-platform-sdk)
npm pack ./build/npm-package --pack-destination ./build/npm-package
```
