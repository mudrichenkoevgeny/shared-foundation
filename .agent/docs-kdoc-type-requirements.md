---
description: Specific KDoc patterns for DTOs, Error Codes, Sealed types, and Routes
glob: "**/*.kt"
alwaysApply: true
---

# Documentation Requirements by Type

## 1. Public API (Interfaces & Contracts)
- **Role-based:** Class-level KDoc must define the role and "Source of Truth" responsibility.
- **Member-level:** Every public property and function must describe its purpose, parameters, and return values.

## 2. Sealed Hierarchies & Results
- **Root Type:** Describe the overall domain model and how variants are discriminated.
- **Variants:** Each variant must document when it applies. For payloads (e.g., `ValidationResult.Failure`), document the meaning of fields.

## 3. API Error Handling (`ApiErrorResponse` & Codes)
- **`ApiErrorResponse`:** Explicitly document that `code` matches constants from `*ErrorCodes`.
- **`*ErrorCodes` Objects:** Class-level KDoc must state alignment with the wire format. Each `const val` must have a short KDoc if its name is not self-explanatory.

## 4. HTTP Route Constants (Critical)
Every `const val` representing an endpoint must follow the format defined in `api-documentation-standard.md`:
- **HTTP Method:** Explicitly stated in backticks (e.g., `GET`).
- **Request/Response:** Linked types only if they exist in the current or dependent modules.
- **Redundancy Policy:** Intentionally duplicate authorization, pagination, and filter logic. Do not use "See [OtherRoute]".

## 5. Value Classes & Wrappers
- **Invariants:** Document what the wrapper represents (e.g., "A hex-encoded, dash-separated UUID") and any validation logic.

## 6. Logic Components (Validators)
- **Contracts:** Document the `validate` function's behavior relative to policy models.
- **Outcome Mapping:** Explain how result variants relate to server-side settings.

---
*Refer to `AGENTS.md` for the full list of project standards.*