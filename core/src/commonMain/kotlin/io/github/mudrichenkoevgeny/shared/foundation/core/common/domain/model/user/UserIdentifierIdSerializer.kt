package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.user

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Custom [KSerializer] for [UserIdentifierId] to serialize it as a plain hex-dash UUID string.
 */
public object UserIdentifierIdSerializer : KSerializer<UserIdentifierId> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.user.UserIdentifierId",
        PrimitiveKind.STRING
    )

    override fun serialize(
        encoder: Encoder,
        value: UserIdentifierId
    ) {
        encoder.encodeString(value.asHexDashString())
    }

    override fun deserialize(decoder: Decoder): UserIdentifierId = decoder.decodeString().toUserIdentifierIdOrThrow()
}
