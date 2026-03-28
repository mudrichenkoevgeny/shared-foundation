package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model

import kotlinx.serialization.Serializable

/**
 * Outcome of an audited action on the API wire.
 *
 * JSON values are the enum names (`SUCCESS`, `FAILED`, `DENIED`), matching default kotlinx.serialization
 * for the backend audit domain model.
 */
@Serializable
enum class AuditStatus {
    /** Action completed successfully. */
    SUCCESS,

    /** Action was attempted but failed (e.g. validation or business error). */
    FAILED,

    /** Action was denied (e.g. insufficient permissions). */
    DENIED
}
