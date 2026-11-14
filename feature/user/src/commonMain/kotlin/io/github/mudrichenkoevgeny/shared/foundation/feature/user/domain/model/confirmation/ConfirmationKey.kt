package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.confirmation

import kotlinx.serialization.Serializable

/**
 * Key for tracking an outstanding confirmation (flow type + target identifier such as email/phone).
 *
 * @property type Which confirmation flow this key refers to (e.g. registration, password reset).
 * @property identifier Target identifier string (e.g. email address or phone number).
 */
@Serializable
public data class ConfirmationKey(
    public val type: ConfirmationType,
    public val identifier: String
)
