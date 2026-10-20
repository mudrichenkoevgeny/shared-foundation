package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.AvailableAuthProvidersPayload

/**
 * Creates a test [AvailableAuthProvidersPayload] wire object instance.
 *
 * @param primary List of primary auth provider wire values ([UserAuthProvider]).
 * @param secondary List of secondary auth provider wire values ([UserAuthProvider]).
 */
fun createTestAvailableAuthProvidersPayload(
    primary: List<String> = listOf(UserAuthProvider.EMAIL.value, UserAuthProvider.PHONE.value),
    secondary: List<String> = emptyList()
) = AvailableAuthProvidersPayload(
    primary = primary,
    secondary = secondary
)
