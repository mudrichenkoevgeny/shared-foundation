package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.user.detail

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import kotlin.test.Test
import kotlin.test.assertEquals

class UserDetailParamsTest {

    private companion object {
        const val TEST_USER_ID = "00000000-0000-4000-8000-000000000001"
    }

    @Test
    fun `parse extracts userId correctly`() {
        val params = mapOf(
            UserApiPaths.USER_ID to TEST_USER_ID
        )

        val parsed = UserDetailParams.parse(params)

        assertEquals(TEST_USER_ID, parsed.userId)
    }

    @Test
    fun `parse returns empty userId when map is empty`() {
        val parsed = UserDetailParams.parse(emptyMap())

        assertEquals("", parsed.userId)
    }

    @Test
    fun `serialize and toQueryMap return empty map for path parameters`() {
        val model = UserDetailParams(userId = TEST_USER_ID)
        val serialized = model.toQueryMap()

        assertEquals(emptyMap(), serialized)
        assertEquals(serialized, UserDetailParams.serialize(model))
    }
}
