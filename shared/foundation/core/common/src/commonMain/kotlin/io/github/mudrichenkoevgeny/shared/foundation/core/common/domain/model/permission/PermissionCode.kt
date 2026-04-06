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