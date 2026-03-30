package io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings

/**
 * Global public settings used by clients and management flows.
 *
 * @property privacyPolicyUrl URL of the privacy policy page.
 * @property termsOfServiceUrl URL of the terms of service page.
 * @property contactSupportEmail Support contact email address.
 */
data class GlobalSettings(
    val privacyPolicyUrl: String?,
    val termsOfServiceUrl: String?,
    val contactSupportEmail: String?
)