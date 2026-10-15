package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.user.detail

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import kotlin.test.Test
import kotlin.test.assertEquals

class UserDetailParamsTest {

    @Test
    fun `parse extracts userId correctly`() {
        val params = mapOf(
            UserApiPaths.USER_ID to "123e4567-e89b-12d3-a456-426614174000"
        )

        val parsed = UserDetailParams.parse(params)

        assertEquals("123e4567-e89b-12d3-a456-426614174000", parsed.userId)
    }

    @Test
    fun `parse returns empty userId when map is empty`() {
        val parsed = UserDetailParams.parse(emptyMap())

        assertEquals("", parsed.userId)
    }

    @Test
    fun `serialize and toQueryMap return empty map for path parameters`() {
        val model = UserDetailParams(userId = "123e4567-e89b-12d3-a456-426614174000")
        val serialized = model.toQueryMap()

        assertEquals(emptyMap(), serialized)
        assertEquals(serialized, UserDetailParams.serialize(model))
    }
}
