package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission

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
value class UserPermissionCode(val value: String) {
    init {
        require(value.isNotBlank()) {
            "UserPermissionCode value must not be blank."
        }
    }

    override fun toString(): String = value
}