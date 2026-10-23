package io.github.mudrichenkoevgeny.shared.foundation.testing.core.storage.domain.model.file

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileId
import kotlin.uuid.Uuid

/**
 * Creates a test [FileId] instance.
 *
 * @param value Raw UUID string.
 */
fun createTestFileId(
    value: String = "00000000-0000-4000-8000-000000000001"
) = FileId(Uuid.parse(value))
