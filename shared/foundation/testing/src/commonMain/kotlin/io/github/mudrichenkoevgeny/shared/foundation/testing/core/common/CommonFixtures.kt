package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common

/**
 * Default IP address for unit testing.
 */
const val TEST_IP_ADDRESS = "127.0.0.1"

/**
 * Default User-Agent string for unit testing.
 */
const val TEST_USER_AGENT = "TestAgent/1.0 (Mobile; Android)"

/**
 * Default email address for unit testing.
 */
const val TEST_EMAIL = "test.user@example.com"

/**
 * Default phone number for unit testing.
 */
const val TEST_PHONE = "+15550000000"

/**
 * Default timestamp in milliseconds for unit testing.
 */
const val TEST_TIME_MILLIS = 1700000000000L

/**
 * Generates a deterministic, valid 36-character UUID string (hex with dashes).
 *
 * @param index Used for the last 12 characters (increment).
 * @param group Used for the first 8 characters (to separate entity types).
 *
 * For example:
 * - index = 0, group = 1 -> "00000001-0000-4000-8000-000000000001"
 * - index = 9, group = 2 -> "00000002-0000-4000-8000-00000000000a"
 */
fun generateTestUuidString(index: Int = 0, group: Int = 0): String {
    require(index >= 0) { "Index must be non-negative: $index" }
    require(group >= 0) { "Group must be non-negative: $group" }

    val groupHex = group.toString(16).padStart(8, '0')
    val indexHex = (index + 1).toString(16).padStart(12, '0')

    return "$groupHex-0000-4000-8000-$indexHex"
}
