package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common

/**
 * Creates a list of [count] elements using the provided [generator] function.
 * Used by domain-specific `createTest*List` fixture helpers.
 *
 * @param count Number of elements to generate (defaults to 2).
 * @param generator Item builder function receiving 0-based element index.
 */
inline fun <T> createTestList(count: Int = 2, generator: (index: Int) -> T): List<T> =
    List(count) { index -> generator(index) }
