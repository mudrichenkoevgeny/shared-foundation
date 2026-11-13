package io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.iprestriction

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.iprestriction.IpRestrictionPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.iprestriction.IpRestrictionPolicyPayload
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Unit tests verifying mapping between [IpRestrictionPolicyPayload] DTO and domain [IpRestrictionPolicy].
 */
class IpRestrictionPolicyMapperTest {
    @Test
    fun testIpRestrictionPolicyPayloadToDomainMapping() {
        val payload = IpRestrictionPolicyPayload(
            isBlacklistEnabled = true,
            blacklist = listOf("192.168.1.1", "10.0.0.0/8"),
            isWhitelistEnabled = false,
            whitelist = emptyList()
        )

        val domain = payload.toIpRestrictionPolicy()

        assertEquals(true, domain.isBlacklistEnabled)
        assertEquals(listOf("192.168.1.1", "10.0.0.0/8"), domain.blacklist)
        assertEquals(false, domain.isWhitelistEnabled)
        assertEquals(emptyList(), domain.whitelist)
    }

    @Test
    fun testIpRestrictionPolicyDomainToPayloadMapping() {
        val domain = IpRestrictionPolicy(
            isBlacklistEnabled = false,
            blacklist = emptyList(),
            isWhitelistEnabled = true,
            whitelist = listOf("127.0.0.1", "172.16.0.1")
        )

        val payload = domain.toIpRestrictionPolicyPayload()

        assertEquals(false, payload.isBlacklistEnabled)
        assertEquals(emptyList(), payload.blacklist)
        assertEquals(true, payload.isWhitelistEnabled)
        assertEquals(listOf("127.0.0.1", "172.16.0.1"), payload.whitelist)
    }
}
