package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.AvailableAuthProvidersPayload

fun createTestAvailableAuthProvidersPayload(
    primary: List<String> = listOf(UserAuthProvider.EMAIL.value, UserAuthProvider.PHONE.value),
    secondary: List<String> = emptyList()
) = AvailableAuthProvidersPayload(
    primary = primary,
    secondary = secondary
)
