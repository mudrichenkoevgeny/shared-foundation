package io.github.mudrichenkoevgeny.shared.foundation.core.storage.mapper.presigned

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.toFileIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.presigned.PresignedReadUrlBatch
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.presigned.PresignedReadUrlBatchPayload

/**
 * Builds domain [PresignedReadUrlBatch] from [PresignedReadUrlBatchPayload].
 */
fun PresignedReadUrlBatchPayload.toPresignedReadUrlBatch(): PresignedReadUrlBatch = PresignedReadUrlBatch(
    urls = urls.mapKeys { it.key.toFileIdOrThrow() }
)

/**
 * Builds network [PresignedReadUrlBatchPayload] from domain [PresignedReadUrlBatch].
 */
fun PresignedReadUrlBatch.toPresignedReadUrlBatchPayload(): PresignedReadUrlBatchPayload = PresignedReadUrlBatchPayload(
    urls = urls.mapKeys { it.key.asHexDashString() }
)
