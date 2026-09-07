package io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.contract

/**
 * Global settings specific naming conventions for API request parameters and response fields.
 */
object GlobalSettingsApiFields {
    /** JSON field name for the privacy policy document URL. */
    const val PRIVACY_POLICY_URL = "privacy_policy_url"

    /** JSON field name for the terms of service document URL. */
    const val TERMS_OF_SERVICE_URL = "terms_of_service_url"

    /** JSON field name for the official support contact email. */
    const val CONTACT_SUPPORT_EMAIL = "contact_support_email"

    /** JSON field name for the global tracing enablement flag. */
    const val IS_TRACING_ENABLED = "is_tracing_enabled"

    /** JSON field name for the global metrics collection enablement flag. */
    const val IS_METRICS_ENABLED = "is_metrics_enabled"

    /** JSON field name for the verbose logging enablement flag. */
    const val IS_VERBOSE_LOGGING_ENABLED = "is_verbose_logging_enabled"

    /** JSON field name for the maintenance mode expiration epoch timestamp in milliseconds. */
    const val MAINTENANCE_UNTIL_EPOCH_MILLIS = "maintenance_until_epoch_millis"

    /** JSON field name for the minimum supported application versions dictionary. */
    const val MIN_SUPPORTED_APP_VERSIONS = "min_supported_app_versions"
}
