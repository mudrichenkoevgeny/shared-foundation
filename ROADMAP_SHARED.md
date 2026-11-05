# Shared Foundation SDK — Development Roadmap

This document outlines the strategic plan for improving code quality, testing infrastructure, and CI/CD pipelines across the `shared-foundation` ecosystem. Since this is a public foundational library, backward compatibility, strict API boundaries, and multiplatform consistency are the highest priorities.

## 1. Static Analysis & Compiler Configuration

*   **Strict Kotlin Compiler Configuration (Typecheck)**
    *   `-Werror`: Treat all compiler warnings as errors (unused variables, deprecated calls).
    *   **Explicit API Mode (`-Xexplicit-api=strict`)**: Enabled for all targets. A public library requires explicit visibility modifiers (`public`, `internal`) and explicit return types to prevent accidental leakage of internal domain models or implementation details.
*   **Detekt (Static Analysis)**
    *   Integration via Gradle (`io.gitlab.arturbosch.detekt`).
    *   Base rulesets: Cyclomatic complexity, naming conventions.
    *   **Custom RuleSets (PSI-based)**: 
        *   Strict ban on inline comments (`//`, `/* */`) to enforce self-documenting code and KDoc usage.
        *   Strict ban on Fully Qualified Names (FQN).
        *   Strict ban on trailing commas.
*   **Ktlint / Spotless (Formatting Control)**
    *   Integration via Gradle plugin (e.g., `org.jlleitschuh.gradle.ktlint`).
    *   Strict code style enforcement: mandatory braces, no trailing commas, import ordering, and unused import removal. Supports auto-formatting.
*   **Lefthook (Git Hooks)**
    *   Repository-level pre-commit hooks block commits on `ktlintCheck`, `detekt`, or `test` failures.

## 2. Architectural Control (Konsist)

For a shared contracts library, architectural consistency is critical. Konsist will enforce rules defined in `.agent/` across the entire codebase:

*   **Payload Boundary Protection:**
    *   **Prohibit `InternalPayload`**: Block PRs if any class is created with the `InternalPayload` suffix. Internal domain models (`*Internal`) must never be serialized or exposed via API payloads.
*   **Route Documentation Parity:**
    *   Test that every public API route constant contains KDoc with mandatory sections: **HTTP method:**, **Request body:**, **Response body:**, **UI Presentation:**, and **Audit logging:**.
*   **Filtering and Sorting Structures:**
    *   Ensure that `*FilterValues` and `*SortBy` objects are top-level constructs (avoiding deep nesting).
    *   Verify that sorting parameters strictly rely on the common `SortOrder` enum.
*   **Localization Consistency:**
    *   Verify that all keys in `*.json` localization resources are formatted in `snake_case`.
    *   Ensure that generated `*Strings.kt` interfaces correctly map to all required keys.

## 3. API Backward Compatibility Protection

*   **Binary Compatibility Validator**
    *   `org.jetbrains.kotlinx.binary-compatibility-validator` plugin integration.
    *   **API Dump Generation**: Maintain `.api` files containing a snapshot of all public signatures (DTOs, routes, interfaces).
    *   **CI Enforcement**: Any change that breaks backward compatibility (e.g., deleting a field in a DTO, changing a property type, removing a route constant) will fail the CI pipeline.

## 4. Contract and Logic Testing

*   **JSON Serialization Contract Tests**
    *   End-to-end tests for `kotlinx.serialization` using the configured `FoundationJson`.
    *   Ensure DTOs map to JSON correctly, including strict validation of `snake_case` naming and proper handling of missing/optional fields.
*   **Domain Mappers Tests**
    *   Verify 1-step conversions for entity projections (e.g., `Internal -> Private`, `Private -> Summary`).
    *   Ensure conversions occur without data loss and without the creation of unnecessary intermediate objects.
*   **Navigation Parsers Tests**
    *   Bidirectional testing of `NavigationScreenParamsParser` implementations.
    *   Test `parse` (from `Map<String, String>` to `*Params` objects) and `serialize` (back to `Map`).
    *   Verify correct behavior of standard normalizers (`QueryParamNormalizer` and `PhoneParamNormalizer`).
*   **NPM Generator Testing (`tools/npm-generator`)**
    *   Comprehensive test coverage for the NPM code generator module.
    *   Verify that Kotlin types correctly map to TypeScript (`index.d.ts`) definitions.
    *   Ensure generated Zod schemas exactly match the validation rules of the Kotlin DTOs.

## 5. CI/CD Pipeline & DevOps (GitHub Actions)

*   **Matrix Cross-Platform Build**
    *   Simultaneous compilation of **JVM, Android Library, iOS (Native), and Wasm JS** targets on every commit.
    *   Guarantees that the shared library compiles successfully across all supported platforms.
*   **NPM Package Verification**
    *   Execute `./gradlew generateNpmPackage` during CI.
    *   Validate the generated TypeScript code using `tsc --noEmit` to ensure there are no syntax or type errors in the generated Zod schemas and route constants.
*   **Automatic Publishing (Maven Central)**
    *   Triggered on creating a `release` tag.
    *   Use `com.vanniktech.maven.publish` plugin for GPG signing and uploading artifacts (`jar`, `aar`, `klib`) and the `bom` module to Maven Central.
*   **Automatic Publishing (NPM Registry)**
    *   Triggered on creating a `release` tag.
    *   Publish the generated `@mudrichenkoevgeny/shared-foundation` package to `npmjs.com` for consumption by the Web Platform SDK (`web-platform-sdk`) and Node.js clients.
