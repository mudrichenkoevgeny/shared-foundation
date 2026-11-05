package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.patch

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.nullable
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Custom [KSerializer] for [PatchField].
 *
 * Designed to work together with `@EncodeDefault(EncodeDefault.Mode.NEVER)` on DTO properties
 * so [PatchField.Unchanged] default values are completely omitted from JSON during serialization.
 */
public class PatchFieldSerializer<T>(
    private val elementSerializer: KSerializer<T>
) : KSerializer<PatchField<T>> {
    override val descriptor: SerialDescriptor = elementSerializer.descriptor.nullable

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(
        encoder: Encoder,
        value: PatchField<T>
    ) {
        when (value) {
            is PatchField.Unchanged -> {}
            is PatchField.Value -> {
                if (value.value == null) {
                    encoder.encodeNull()
                } else {
                    encoder.encodeSerializableValue(elementSerializer, value.value)
                }
            }
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): PatchField<T> =
        if (decoder.decodeNotNullMark()) {
            PatchField.Value(decoder.decodeSerializableValue(elementSerializer))
        } else {
            decoder.decodeNull()
            PatchField.Value(null)
        }
}
