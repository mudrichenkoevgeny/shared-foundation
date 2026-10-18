package io.github.mudrichenkoevgeny.shared.foundation.core.common.localization

/**
 * Well-known BCP 47 language codes supported by the shared library.
 *
 * Every listed locale has corresponding localizations provided by the library.
 */
object CommonLocales {

    /**
     * English language code ("en").
     */
    const val ENGLISH: String = "en"

    /**
     * Russian language code ("ru").
     */
    const val RUSSIAN: String = "ru"

    /**
     * Language codes supported by the shared library.
     */
    val SUPPORTED: Set<String> = setOf(
        ENGLISH,
        RUSSIAN
    )

    /**
     * Default fallback locale for the library ("en").
     */
    const val DEFAULT: String = ENGLISH
}