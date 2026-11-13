# tools/npm-generator-runner

Executable JVM application module responsible for building, auditing, and publishing the official **`@mudrichenkoevgeny/shared-foundation`** TypeScript / JavaScript NPM package artifact (`build/npm-package/`).

It collects all wire DTOs, domain mappers, REST API routes, navigation screen parameters, error codes, permission objects, localization bundles, and test factories across all `core/*` and `feature/*` modules of `shared-foundation` and delegates package generation and verification to `NpmGeneratorEngine`.

---

## Architecture & Components

- **`NpmClassCollectorRunner.kt`**: Central collector aggregating Kotlin targets across `core/*` and `feature/*` modules:
  - `dtoClasses`: Wire DTOs and Screen Parameter models (`UserSummaryPayload`, `LoginByEmailParams`, etc.).
  - `enumClasses`: Domain enums (`UserRole`, `AuditStatus`, `TotpStep`, `ClientType`, `ExternalAuthProvider`, etc.).
  - `domainToDtoClasses`: Mappers between domain models and DTO payloads (`UserSummary to UserSummaryPayload`).
  - `routeClasses`: Route objects (`ManagementUsersRoutes`, `ManagementUsersNavigation`, `ClientAuthNavigation`, etc.).
  - `contractClasses`: Protocol contracts (`UserApiFields`, `CommonHttpHeaders`, `WebSocketContract`).
  - `errorClasses`: Error codes and args (`UserErrorCodes`, `CommonErrorCodes`, etc.).
  - `permClasses`: Permission codes (`UserPermissionCode`, `SecurityPermissionCode`, etc.).
- **`NpmConfigProviderRunner.kt`**: Config builder (`buildFoundationNpmConfig`) providing branded types, mock mappings, and type resolvers for `@mudrichenkoevgeny/shared-foundation`.
- **`NpmConstantsProviderRunner.kt`**: Extra JS/DTS constants, validators, composite parsers, testing helpers, and formatting utility generators.
- **`NpmGeneratorRunner.kt`**: Main application entry point (`NpmGeneratorRunnerKt.main()`).
  - Resolves package version from Gradle properties/environment.
  - Configures `NpmGeneratorConfig` for `@mudrichenkoevgeny/shared-foundation`.
  - Invokes `NpmGeneratorEngine(config).generate()`.
- **`scripts/audit-npm-exports.js`**: 100% Parity Audit Script that scans Kotlin source files on disk in `core/*` and `feature/*` and compares them against generated JS and DTS exports, ensuring no Kotlin object, enum, resolver, or fixture is missing in the generated NPM package.

---

## Gradle Tasks & Workflow

```bash
# Build the @mudrichenkoevgeny/shared-foundation NPM package in build/npm-package/
./gradlew generateNpmPackage

# Run application generator without verification script
./gradlew :tools:npm-generator-runner:run

# Run 100% Export Parity Audit against Kotlin source files
./gradlew :tools:npm-generator-runner:auditNpmExports

# Run unit tests
./gradlew :tools:npm-generator-runner:test
```

---

## Output Package Structure (`build/npm-package/`)

Running `./gradlew generateNpmPackage` produces:

- `package.json`: NPM package descriptor (`name: "@mudrichenkoevgeny/shared-foundation"`, `"type": "module"`, `"zod": "^3.24.2"`).
- `index.d.ts`: Full TypeScript type declarations, interfaces, branded types (`UserId`, `PermissionCode`, etc.), JSDoc hover comments, route types, and test fixture types.
- `index.js`: JavaScript runtime exports containing Zod schemas (`*Schema`), parameter parsers/serializers (`toQueryMap`), runtime mappers (`toUserSummary`), validators (`passwordPolicyValidator`, `storageUploadValidator`), test fixtures (`createTestUserSummary`), and localization facade (`SharedLocalization`).
- `README.md` & `.npmignore`.
