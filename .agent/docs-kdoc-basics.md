---
description: Language standards, link resolution rules, and KDoc Definition of Done
glob: "**/*.kt"
alwaysApply: true
---

# KDoc Style Guide — Basics

This document defines mandatory language and syntax standards for all public interfaces and documentation within the project.

## 1. Language Standard
- **English Only:** All KDoc, README files, and API documentation must be written strictly in English. No exceptions.

## 2. KDoc Links & References
- **Simple Names Only:** Use only simple names that resolve via imports: `[Foo]` or `[SomeType.member]`.
- **No FQN in Links:** Fully qualified names in links (e.g., `[full.package.Foo]`) are strictly forbidden.
- **Import Requirement:** Every type referenced in a link must be imported in the current file.
- **Unreachable Targets:** If a type cannot be imported (e.g., from a consumer module), use **plain text** without brackets.
- **No Deferred Prose:** Do not redirect the reader to other KDocs. Phrasing like "Same as [Other.CONST]" is forbidden. Repeat the contract details fully to ensure each constant is self-contained.

## 3. Scope Rules
- **No Parameter Links in Class-level:** Do not link to function parameters by name in the class-level KDoc. Move such links to the function-level `@param`.
- **Package Documentation:** When documenting a package, do not create "KDoc-only" Kotlin files. Apply documentation to the primary interfaces/classes within that package instead.

## 4. Definition of Done (DoD)
A documentation task is considered complete ONLY if:
1. **Public Interfaces:** Every public `interface` has a class-level KDoc AND every public member (`fun`/`val`) has its own KDoc.
2. **Members:** Member KDoc must include a summary, `@param` for all parameters, and `@return` for non-`Unit` types.
3. **Unit Returns:** For `Unit` functions, the summary must clearly state the outcome (success/failure behavior).

## 5. Inline Comments vs. KDoc
- **KDoc Priority:** Focus on public API documentation.
- **No Narrative Comments:** Strictly forbidden to write comments that describe obvious code behavior.
- **Intent over Implementation:** Use comments only for non-obvious constraints.

---
*Refer to `AGENTS.md` for the full list of project standards.*