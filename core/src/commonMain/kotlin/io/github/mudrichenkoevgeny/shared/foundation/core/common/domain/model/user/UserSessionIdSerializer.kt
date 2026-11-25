package io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.user

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Custom [KSerializer] for [UserSessionId] to serialize it as a plain hex-dash UUID string.
 */
public object UserSessionIdSerializer : KSerializer<UserSessionId> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.user.UserSessionId",
        PrimitiveKind.STRING
    )

    override fun serialize(
        encoder: Encoder,
        value: UserSessionId
    ) {
        encoder.encodeString(value.asHexDashString())
    }

    override fun deserialize(decoder: Decoder): UserSessionId = decoder.decodeString().toUserSessionIdOrThrow()
}
