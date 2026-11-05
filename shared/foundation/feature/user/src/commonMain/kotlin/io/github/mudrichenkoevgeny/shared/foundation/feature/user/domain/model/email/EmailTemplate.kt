package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.email

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Localized email template containing subject and HTML body templates.
 *
 * @property subject Email subject line template.
 * @property body Email HTML body template.
 */
@Serializable
public data class EmailTemplate(
    @SerialName("subject") public val subject: String,
    @SerialName("body") public val body: String
)
