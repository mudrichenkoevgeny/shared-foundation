package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings

/**
 * **Public** authentication settings: what sign-in methods the deployment exposes to clients.
 *
 * @property availableAuthProviders Enabled providers grouped for display and policy; see [AvailableAuthProviders].
 */
data class PublicAuthSettings(
    val availableAuthProviders: AvailableAuthProviders
)
