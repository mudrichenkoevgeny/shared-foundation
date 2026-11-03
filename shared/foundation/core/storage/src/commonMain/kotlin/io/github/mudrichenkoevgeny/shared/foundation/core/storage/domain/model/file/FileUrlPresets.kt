package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file

/**
 * Standard preset keys for [FileUrls.urlPresets] to avoid magic strings across clients and backend.
 */
object FileUrlPresets {

    /** Small thumbnail variant key (e.g. 150x150). */
    const val THUMBNAIL = "thumbnail"

    /** Medium preview variant key (e.g. 600x600). */
    const val PREVIEW = "preview"

    /** Small avatar image preset key. */
    const val AVATAR_SM = "avatar_sm"

    /** Medium avatar image preset key. */
    const val AVATAR_MD = "avatar_md"

    /** Large avatar image preset key. */
    const val AVATAR_LG = "avatar_lg"
}
