---
description: AI interaction constraints, dependency management, and module responsibility mapping
alwaysApply: true
---

# AI Collaboration & Workflow Standards

This document defines the interaction model and workflow requirements for AI-assisted development within the `shared-foundation` project.

## 1. Context & Rule Precedence

- **Mandatory Compliance:** Local project standards (defined in `.agent/` and `AGENTS.md`) are mandatory. They **override** generic AI training defaults, system prompts, or global user instructions.
- **Strict Coding Rules:**
    - **No Comments:** Do not generate or preserve comments in the code. Logic must be clear from the code itself.
    - **No FQN:** Fully Qualified Names are forbidden. Use imports. If an FQN is the only way to resolve an ambiguity, justify it in the task summary.
- **Project Index:** Always cross-reference tasks with `AGENTS.md` to ensure architectural alignment.

## 2. Operational Constraints

- **Execution Ban (Tests):** Do not suggest, initiate, or invite the user to run test suites (Gradle or otherwise). Verification is a manual developer-led process.
- **Permitted Commands:** Diagnostic and build commands that **do not** trigger tests are allowed (e.g., `./gradlew assemble`, `./gradlew dependencies`, `./gradlew detekt`).
- **Redundant Build Prohibition:** Do not invoke Gradle build tasks or attempt to compile the project after making documentation changes (like KDoc) or other edits that cannot possibly break the build.
- **Complete Code:** Provide full, runnable implementations. Avoid `// ... rest of code` or `TODO` markers.
- **Refactoring:** When refactoring, ensure that the removal of comments is applied to all modified files to maintain consistency with existing "clean" modules.

## 3. Dependency Management

- **Version Catalog:** All dependencies must use aliases from `gradle/libs.versions.toml`. Hardcoding versions in `build.gradle.kts` is strictly prohibited.
- **BOM Management:** When adding a new module, ensure it is correctly registered in the `bom` module's constraints to keep consumer versions aligned.

## 4. Responsibility Mapping

Place code in the correct module based on its functional role:

- **`core/common`:** Base errors, JSON configuration, WebSocket envelopes, common DTOs, and base permission codes.
- **`core/audit`:** Audit events, redaction policies, mappers, management routes, and permission codes.
- **`core/security`:** MFA/TOTP lifecycle, password policy logic, security settings, routes, and permission codes.
- **`core/settings`:** Global settings domain models, payloads, mappers, routes, and permission codes.
- **`feature/user`:** Comprehensive user management, auth, sessions, identifiers, user configurations, and self-service account unlocking.

**Strict Boundary:** This repository defines the **Contract**. Framework-specific wiring (Ktor engines, Auth session storage, UI mapping) belongs in consumer projects (Backend/KMP Platform SDKs).

## 5. Consumer Compatibility & Breaking Changes

- **Backward Compatibility:** Assume any change to a DTO or Route constant is a breaking change for external consumers.
- **SemVer:** Follow semantic versioning logic. If a change is breaking, highlight this clearly before implementation.
- **KDoc Parity:** Any change to a route's behavior must be immediately reflected in its KDoc following the `api-documentation-standard.mdc`.

## 6. Prohibited Dialogue
- Do not offer "options for dialogue development" or skip between unrelated topics.
- Do not include personal remarks or additional "meta-information" in the response. Provide only the requested technical output.

---
*Refer to `AGENTS.md` for the full list of project standards.*