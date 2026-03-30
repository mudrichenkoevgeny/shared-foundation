package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.ExternalAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AvailableAuthProvidersTest {

    @Test
    fun `supportedExternalProviders is empty when no external providers are enabled`() {
        val providers = AvailableAuthProviders(
            primary = listOf(UserAuthProvider.EMAIL),
            secondary = listOf(UserAuthProvider.PHONE),
        )

        assertTrue(providers.supportedExternalProviders.isEmpty())
    }

    @Test
    fun `supportedExternalProviders maps enabled auth providers to external providers`() {
        val providers = AvailableAuthProviders(
            primary = listOf(UserAuthProvider.GOOGLE),
            secondary = listOf(UserAuthProvider.APPLE),
        )

        assertEquals(
            setOf(ExternalAuthProvider.Google, ExternalAuthProvider.Apple),
            providers.supportedExternalProviders,
        )
    }
}
