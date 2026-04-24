package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * Strongly typed permission code used in access control checks and API contracts.
 *
 * Typical format is `resource.action[.scope]`.
 *
 * @property value Raw machine-readable permission string.
 */
@Serializable
@JvmInline
value class PermissionCode(val value: String) {
    init {
        require(value.isNotBlank()) { "PermissionCode value must not be blank." }
    }

    override fun toString(): String = value
}

/**
 * Attempts to create a [PermissionCode] from this string.
 *
 * Returns `null` if the string is blank.
 */
fun String.toPermissionCodeOrNull(): PermissionCode? {
    return if (this.isNotBlank()) {
        PermissionCode(this)
    } else {
        null
    }
}

/**
 * Creates a [PermissionCode] from this string or throws an exception if the string is blank.
 */
fun String.toPermissionCodeOrThrow(): PermissionCode {
    return PermissionCode(this)
}