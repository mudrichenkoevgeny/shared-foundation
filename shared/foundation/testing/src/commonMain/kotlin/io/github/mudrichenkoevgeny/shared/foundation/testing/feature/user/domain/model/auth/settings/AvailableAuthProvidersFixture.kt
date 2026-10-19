package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.AvailableAuthProviders
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider

fun createTestAvailableAuthProviders(
    primary: List<UserAuthProvider> = listOf(UserAuthProvider.EMAIL, UserAuthProvider.PHONE),
    secondary: List<UserAuthProvider> = emptyList()
) = AvailableAuthProviders(
    primary = primary,
    secondary = secondary
)
