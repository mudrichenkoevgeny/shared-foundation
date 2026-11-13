---
description: Multiplatform strategy, scope of testing, and test fixtures for shared-foundation
glob: "**/*Test.kt"
alwaysApply: true
---

# Shared Foundation — Local Testing Strategy

This document defines the project-specific Multiplatform testing strategy and coverage boundaries for `shared-foundation`.

## 1. Multiplatform Strategy
- **`commonTest` Priority:** Since this library compiles to multiple target platforms (JVM, Android, iOS, Wasm), the majority of domain logic (serialization, validation, mappers) must be tested in `commonTest`.
- **Assertions:** Use `kotlin.test` (e.g., `assertEquals`, `assertTrue`) for all common code to ensure compatibility across JVM, iOS, and Wasm targets.
- **Mocking:**
  - In `commonTest`: Prefer manual **Mocks** (e.g., `PasswordPolicyValidatorMock`, `LocalizedStringResolverMock`) or **Stubs**.
  - In `jvmTest` / `androidMain`: **MockK** is permitted.
  - Avoid adding heavy mocking frameworks to common code unless they are fully compatible across all compilation targets.

## 2. Scope of Testing (Mandatory Coverage Rules)
- **Serialization (Critical):** Every DTO/Payload in `core/` and `feature/` must have a serialization test ensuring `FoundationJson` produces the expected `snake_case` output, handles optional/null fields gracefully, and deserializes without errors.
- **Domain Projection Mappers:** Every projection mapper (`*DomainMapper`, `*PrivateMapper`, `*SummaryMapper`) converting between projection tiers (`Internal -> Private -> Summary`) must have unit tests verifying 1-step conversion, field preservation, and prevention of secret/internal data leaks into public DTOs.
- **Validators & Normalizers:** Every validator (`*Validator`) and parameter normalizer (`PhoneParamNormalizer`, `QueryParamNormalizer`) must be covered by exhaustive unit tests including edge cases (empty strings, invalid formats, length limits, special characters).
- **Navigation Screen Params:** Every `*Params` data class and `NavigationScreenParamsParser` implementation must have bidirectional tests verifying `parse` (from `Map<String, String>` to object) and `serialize` (from object back to `Map<String, String>`).
- **NPM Code Generator (`tools/npm-generator`):** Code generation logic mapping Kotlin domain classes to TypeScript definitions (`index.d.ts`) and Zod schemas must have unit test coverage.

## 3. Testing Fixtures
- Use standardized test fixtures and constants from `testing` (e.g., `TEST_*` constants, factory functions) rather than constructing dummy instances directly inside test methods.

---
*Refer to `AGENTS.md` for the full list of project standards.*
