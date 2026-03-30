package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token

import kotlin.jvm.JvmInline

/**
 * Opaque token string used to obtain a new [SessionToken] without re-authentication.
 *
 * Stored or compared via [RefreshTokenHash]; the raw value is only exposed when
 * issuing new tokens to the client.
 *
 * @param value Raw refresh token string; must not be logged or persisted in plain form.
 */
@JvmInline
value class RefreshToken(val value: String)