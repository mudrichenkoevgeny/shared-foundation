package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.identifier.list

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val ACTION_ADD = "add"

/**
 * Contextual modal actions supported on the self-service user identifiers list screen.
 *
 * @property value String wire representation of the [SelfIdentifierListAction].
 */
@Serializable
enum class SelfIdentifierListAction(val value: String) {
    /** Opens the modal wizard for adding a new authentication identifier ([ACTION_ADD]). */
    @SerialName(ACTION_ADD)
    ADD(ACTION_ADD);

    companion object {

        /**
         * Resolves [SelfIdentifierListAction] matching the given [value], or `null` if no match is found.
         *
         * Compares against both wire [value] and enum [name] case-insensitively.
         */
        fun fromValueOrNull(value: String): SelfIdentifierListAction? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Resolves [SelfIdentifierListAction] for the given [value].
         *
         * @throws IllegalArgumentException if [value] does not match any action.
         */
        fun fromValueOrThrow(value: String): SelfIdentifierListAction = fromValueOrNull(value)
            ?: throw IllegalArgumentException("Unknown value for SelfIdentifierListAction: '$value'")
    }
}