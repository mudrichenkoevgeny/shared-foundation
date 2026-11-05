package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file

/**
 * Standard preset keys for [FileUrls.urlPresets] to avoid magic strings across clients and backend.
 */
public object FileUrlPresets {
    /** Small thumbnail variant key (e.g. 150x150). */
    public const val THUMBNAIL: String = "thumbnail"

    /** Medium preview variant key (e.g. 600x600). */
    public const val PREVIEW: String = "preview"

    /** Small avatar image preset key. */
    public const val AVATAR_SM: String = "avatar_sm"

    /** Medium avatar image preset key. */
    public const val AVATAR_MD: String = "avatar_md"

    /** Large avatar image preset key. */
    public const val AVATAR_LG: String = "avatar_lg"
}
