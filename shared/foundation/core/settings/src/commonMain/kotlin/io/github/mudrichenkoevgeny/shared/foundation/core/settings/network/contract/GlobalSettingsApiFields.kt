package io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.contract

/**
 * Global settings specific naming conventions for API request parameters and response fields.
 */
public object GlobalSettingsApiFields {
    /** JSON field name for the privacy policy document URL. */
    public const val PRIVACY_POLICY_URL: String = "privacy_policy_url"

    /** JSON field name for the terms of service document URL. */
    public const val TERMS_OF_SERVICE_URL: String = "terms_of_service_url"

    /** JSON field name for the official support contact email. */
    public const val CONTACT_SUPPORT_EMAIL: String = "contact_support_email"

    /** JSON field name for the global tracing enablement flag. */
    public const val IS_TRACING_ENABLED: String = "is_tracing_enabled"

    /** JSON field name for the global metrics collection enablement flag. */
    public const val IS_METRICS_ENABLED: String = "is_metrics_enabled"

    /** JSON field name for the verbose logging enablement flag. */
    public const val IS_VERBOSE_LOGGING_ENABLED: String = "is_verbose_logging_enabled"

    /** JSON field name for the minimum supported application versions dictionary. */
    public const val MIN_SUPPORTED_APP_VERSIONS: String = "min_supported_app_versions"
}
