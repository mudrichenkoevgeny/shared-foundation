package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.AvailableAuthProviders
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.AvailableAuthProvidersPayload

/**
 * Builds domain [AvailableAuthProviders] from [AvailableAuthProvidersPayload].
 */
public fun AvailableAuthProvidersPayload.toAvailableAuthProviders(): AvailableAuthProviders =
    AvailableAuthProviders(
        primary = primary.map { it.toUserAuthProviderOnWire() },
        secondary = secondary.map { it.toUserAuthProviderOnWire() }
    )

/**
 * Builds network [AvailableAuthProvidersPayload] from domain [AvailableAuthProviders].
 */
public fun AvailableAuthProviders.toAvailableAuthProvidersPayload(): AvailableAuthProvidersPayload =
    AvailableAuthProvidersPayload(
        primary = primary.map(UserAuthProvider::value),
        secondary = secondary.map(UserAuthProvider::value)
    )

private fun String.toUserAuthProviderOnWire(): UserAuthProvider = UserAuthProvider.fromValueOrThrow(this)
