package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token

import kotlin.jvm.JvmInline

/**
 * Hash of a [RefreshToken] for secure storage and comparison.
 *
 * Used in [UserSession] and in revocation checks; the original token is never stored.
 *
 * @param value Hash value (e.g. from a one-way function); must match the algorithm used when issuing tokens.
 */
@JvmInline
value class RefreshTokenHash(val value: String)