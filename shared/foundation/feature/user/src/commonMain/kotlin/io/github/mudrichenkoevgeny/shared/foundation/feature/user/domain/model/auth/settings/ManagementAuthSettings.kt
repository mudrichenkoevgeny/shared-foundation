package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings

/**
 * Full authentication configuration edited through the **management** API.
 *
 * Superset of [PublicAuthSettings]: includes the same provider lists plus token lifetime policy
 * for issued credentials. Server implementations apply updates without restart; **already issued**
 * tokens may retain semantics from the previous policy until they expire or are revoked, unless
 * the server documents otherwise.
 *
 * @property availableAuthProviders Enabled providers grouped for display and policy; see [AvailableAuthProviders].
 * @property accessTokenValidityHours Validity window for **new** access tokens, in whole hours.
 * @property refreshTokenValidityDays Validity window for **new** refresh tokens, in whole days.
 */
data class ManagementAuthSettings(
    val availableAuthProviders: AvailableAuthProviders,
    val accessTokenValidityHours: Long,
    val refreshTokenValidityDays: Long
)
