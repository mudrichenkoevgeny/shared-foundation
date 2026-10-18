package io.github.mudrichenkoevgeny.shared.foundation.core.common.localization

/**
 * Utility providing interpolation of template variables in localized strings.
 */
object LocalizationFormatter {

    /**
     * Replaces `{key}` placeholders in [template] with matching values from [args].
     *
     * @param template Localized string template containing placeholders.
     * @param args Map of argument names and their replacement values.
     * @return Formatted string with substituted values.
     */
    fun format(template: String, args: Map<String, Any?>): String {
        return args.entries.fold(template) { current, (key, value) ->
            current.replace("{$key}", value?.toString().orEmpty())
        }
    }
}