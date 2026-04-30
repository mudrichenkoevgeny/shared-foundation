---
description: Unit and integration test standards, mocking policy, and manual execution rule
glob: "**/*Test.kt"
alwaysApply: true
---

# Testing Standards and Conventions

This document defines the testing methodology for the `shared-foundation` project. These rules ensure that tests remain modular, fast, and consistent across all Kotlin Multiplatform modules.

## 1. Operational Policy (Strict)
- **Manual Execution Only:** Test runs must be triggered explicitly by the developer (e.g., `./gradlew test`).
- **Execution Ban:** AI agents and IDE background processes are strictly prohibited from automatically initiating test suites.
- **Resource Focus:** Tests should not run on every file save to prevent resource exhaustion and maintain a focused development flow.

## 2. Test Organization & Naming
- **Mirror Structure:** Test files must reside in the same package as the subject under test (e.g., `src/commonTest/kotlin/io/github/.../serialization/`).
- **1:1 Relationship:** Each production class/top-level component should have a corresponding test class (e.g., `FoundationJson` -> `FoundationJsonTest`).
- **Naming:**
    - Unit Tests: `[Subject]Test.kt`.
    - Mappers: `[Subject]MapperTest.kt`.
    - Integration: `[BoundaryName]IntegrationTest.kt`.

## 3. Multiplatform Strategy
- **`commonTest` Priority:** Since this is a KMP library, the majority of logic (serialization, validation, mappers) must be tested in `commonTest`.
- **Assertions:** Use `kotlin.test` (e.g., `assertEquals`, `assertTrue`) for all common code to ensure compatibility across JVM, iOS, and Wasm targets.
- **Mocking:**
    - In `commonTest`: Prefer manual **Fakes** or **Stubs**.
    - In `jvmTest` / `androidMain`: **MockK** is permitted.
    - Avoid adding heavy mocking frameworks to common code unless they are fully KMP-compatible.

## 4. Coding Standards in Tests
- **No Comments:** Like production code, tests must be self-documenting. Use descriptive method names like `test_paged_result_serialization_with_empty_list()`.
- **No FQN:** Fully Qualified Names are forbidden. Use imports.
- **Isolation:** Tests must be stateless. Do not rely on execution order or shared mutable global state.
- **Clean Data:** Use `private const val` for repeated test values (IDs, sample JSON strings) to avoid "magic values" in the test body.

## 5. Scope of Testing
- **Serialization (Critical):** Every DTO in `core/` must have a serialization test ensuring `FoundationJson` produces the expected `snake_case` output.
- **Contract Integrity:** Feature-API modules should test that route constants and path segments are correctly concatenated.
- **Logic Validation:** Components like `PasswordPolicyValidator` must be covered by exhaustive unit tests including edge cases (empty strings, maximum length, special characters).

## 6. Dependency Rules
- **Explicit Only:** Declare all test dependencies in `build.gradle.kts` via `commonTest.dependencies`.
- **Version Catalog:** Use `libs.versions.toml` aliases for all test libraries (e.g., `implementation(libs.kotlin.test)`).

---
*Refer to `AGENTS.md` for the full list of project standards.*