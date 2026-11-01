package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.patch

import io.github.mudrichenkoevgeny.shared.foundation.core.common.serialization.FoundationJson
import kotlinx.serialization.builtins.serializer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PatchFieldTest {

    private val testStringValue = "test_value"
    private val testListValue = listOf("item_1", "item_2")

    @Test
    fun testValueOf_NonNullValue_ReturnsValueInstance() {
        val field = PatchField.valueOf(testStringValue)
        assertEquals(expected = PatchField.Value(testStringValue), actual = field)
    }

    @Test
    fun testValueOf_NullValue_ReturnsValueWithNull() {
        val field = PatchField.valueOf<String>(null)
        assertEquals(expected = PatchField.Value(null), actual = field)
    }

    @Test
    fun testSerializer_Descriptor_IsNullable() {
        val serializer = PatchFieldSerializer(String.serializer())
        assertNotNull(serializer.descriptor)
        assertTrue(serializer.descriptor.isNullable)
    }

    @Test
    fun testSerialize_Unchanged_OmitsFields() {
        val dto = TestPatchDto()
        val json = FoundationJson.encodeToString(TestPatchDto.serializer(), dto)
        assertEquals("{}", json)
    }

    @Test
    fun testSerialize_ValueNonNull_EncodesField() {
        val dto = TestPatchDto(stringField = PatchField.Value(testStringValue))
        val json = FoundationJson.encodeToString(TestPatchDto.serializer(), dto)
        assertEquals("""{"${TestPatchDto.TEST_STRING_FIELD_KEY}":"$testStringValue"}""", json)
    }

    @Test
    fun testSerialize_ValueNull_EncodesExplicitNull() {
        val dto = TestPatchDto(stringField = PatchField.Value(null))
        val json = FoundationJson.encodeToString(TestPatchDto.serializer(), dto)
        assertEquals("""{"${TestPatchDto.TEST_STRING_FIELD_KEY}":null}""", json)
    }

    @Test
    fun testSerialize_ComplexCollectionField_EncodesList() {
        val dto = TestPatchDto(listField = PatchField.Value(testListValue))
        val json = FoundationJson.encodeToString(TestPatchDto.serializer(), dto)
        assertEquals("""{"${TestPatchDto.TEST_LIST_FIELD_KEY}":["item_1","item_2"]}""", json)
    }

    @Test
    fun testDeserialize_FieldPresent_ReturnsValue() {
        val json = """{"${TestPatchDto.TEST_STRING_FIELD_KEY}":"$testStringValue"}"""
        val dto = FoundationJson.decodeFromString(TestPatchDto.serializer(), json)
        assertEquals(PatchField.Value(testStringValue), dto.stringField)
        assertEquals(PatchField.Unchanged, dto.intField)
        assertEquals(PatchField.Unchanged, dto.listField)
    }

    @Test
    fun testDeserialize_FieldNull_ReturnsValueNull() {
        val json = """{"${TestPatchDto.TEST_STRING_FIELD_KEY}":null}"""
        val dto = FoundationJson.decodeFromString(TestPatchDto.serializer(), json)
        assertEquals(PatchField.Value(null), dto.stringField)
        assertEquals(PatchField.Unchanged, dto.intField)
    }

    @Test
    fun testDeserialize_FieldOmitted_ReturnsUnchanged() {
        val json = """{}"""
        val dto = FoundationJson.decodeFromString(TestPatchDto.serializer(), json)
        assertEquals(PatchField.Unchanged, dto.stringField)
        assertEquals(PatchField.Unchanged, dto.intField)
        assertEquals(PatchField.Unchanged, dto.listField)
    }
}
