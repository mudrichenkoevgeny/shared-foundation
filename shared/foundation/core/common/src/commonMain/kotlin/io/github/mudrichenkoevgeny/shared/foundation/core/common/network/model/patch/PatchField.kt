package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.patch

import kotlinx.serialization.Serializable

/**
 * Represents a 3-state field update for PATCH requests:
 * 1. [Unchanged]: Field was omitted in request; leave unchanged in persistence.
 * 2. [Value] with non-null `value`: Set field to the new value in persistence.
 * 3. [Value] with `null` `value`: Explicitly clear/nullify field in persistence.
 *
 * **Usage Requirement:**
 * Properties of type [PatchField] in `@Serializable` DTO classes **MUST** be annotated with
 * `@EncodeDefault(EncodeDefault.Mode.NEVER)` and default to [PatchField.Unchanged]
 * (for example: `@EncodeDefault(EncodeDefault.Mode.NEVER) val nickname: PatchField<String> = PatchField.Unchanged`).
 * This ensures that [Unchanged] fields are completely omitted from JSON during serialization.
 */
@Serializable(with = PatchFieldSerializer::class)
public sealed interface PatchField<out T> {
    /** Indicates the field was omitted in the update request and must remain unchanged in persistence. */
    public data object Unchanged : PatchField<Nothing>

    /** Indicates the field was explicitly provided in the update request, wrapping a value or `null` to clear. */
    public data class Value<out T>(
        public val value: T?
    ) : PatchField<T>

    public companion object {
        /** Helper to wrap a value into [Value]. */
        public fun <T> valueOf(value: T?): PatchField<T> = Value(value)
    }
}
