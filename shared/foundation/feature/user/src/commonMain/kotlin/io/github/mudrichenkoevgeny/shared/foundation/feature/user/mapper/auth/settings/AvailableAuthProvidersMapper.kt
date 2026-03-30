package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.AvailableAuthProviders
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.AvailableAuthProvidersPayload

/**
 * Maps between [AvailableAuthProvidersPayload] and domain [AvailableAuthProviders].
 *
 * Wire list entries use [UserAuthProvider.serialName]; [UserAuthProvider.fromValueOrNull] parses them.
 * Unknown wire values fail mapping with [IllegalArgumentException].
 */

/**
 * Builds domain [AvailableAuthProviders] from [AvailableAuthProvidersPayload].
 */
fun AvailableAuthProvidersPayload.toAvailableAuthProviders(): AvailableAuthProviders =
    AvailableAuthProviders(
        primary = primary.map { it.toUserAuthProviderOnWire() },
        secondary = secondary.map { it.toUserAuthProviderOnWire() }
    )

/**
 * Builds network [AvailableAuthProvidersPayload] from domain [AvailableAuthProviders].
 */
fun AvailableAuthProviders.toAvailableAuthProvidersPayload(): AvailableAuthProvidersPayload =
    AvailableAuthProvidersPayload(
        primary = primary.map(UserAuthProvider::serialName),
        secondary = secondary.map(UserAuthProvider::serialName)
    )

private fun String.toUserAuthProviderOnWire(): UserAuthProvider {
    val parsed = UserAuthProvider.fromValueOrNull(this)
    if (parsed == null) {
        throw IllegalArgumentException("Unknown UserAuthProvider wire value: $this")
    }
    return parsed
}
