package io.github.mudrichenkoevgeny.shared.foundation.core.common.localization

/**
 * Well-known BCP 47 language codes supported by the shared library.
 *
 * Every listed locale has corresponding localizations provided by the library.
 */
public object CommonLocales {
    /**
     * English language code ("en").
     */
    public const val ENGLISH: String = "en"

    /**
     * Russian language code ("ru").
     */
    public const val RUSSIAN: String = "ru"

    /**
     * Language codes supported by the shared library.
     */
    public val SUPPORTED: Set<String> =
        setOf(
            ENGLISH,
            RUSSIAN
        )

    /**
     * Default fallback locale for the library ("en").
     */
    public const val DEFAULT: String = ENGLISH
}
