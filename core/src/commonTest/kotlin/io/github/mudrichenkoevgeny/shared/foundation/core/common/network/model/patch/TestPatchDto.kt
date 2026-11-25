package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.patch

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TestPatchDto(
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    @SerialName(TEST_STRING_FIELD_KEY)
    val stringField: PatchField<String> = PatchField.Unchanged,
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    @SerialName(TEST_INT_FIELD_KEY)
    val intField: PatchField<Int> = PatchField.Unchanged,
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    @SerialName(TEST_LIST_FIELD_KEY)
    val listField: PatchField<List<String>> = PatchField.Unchanged
) {
    companion object {
        internal const val TEST_STRING_FIELD_KEY = "string_field"
        internal const val TEST_INT_FIELD_KEY = "int_field"
        internal const val TEST_LIST_FIELD_KEY = "list_field"
    }
}
