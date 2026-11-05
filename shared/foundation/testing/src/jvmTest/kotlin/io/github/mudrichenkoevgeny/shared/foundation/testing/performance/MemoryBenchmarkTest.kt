package io.github.mudrichenkoevgeny.shared.foundation.testing.performance

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.mapper.audit.toAuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.domain.model.event.createTestAuditEventInternal
import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * Performance and memory benchmark test suite verifying allocation efficiency and absence of heap leaks.
 */
public class MemoryBenchmarkTest {
    /**
     * Verifies that mapping audit events repeatedly does not cause memory leaks or excessive retained heap allocation.
     */
    @Test
    public fun auditEventMapperMemoryLeakTest() {
        val runtime = Runtime.getRuntime()
        System.gc()
        val initialMemory = runtime.totalMemory() - runtime.freeMemory()

        val event = createTestAuditEventInternal()
        val iterations = 100_000

        for (i in 0 until iterations) {
            val privateModel = event.toAuditEventPrivate()
            if (i % 25_000 == 0) {
                privateModel.id.toString()
            }
        }

        System.gc()
        Thread.sleep(50)
        System.gc()

        val finalMemory = runtime.totalMemory() - runtime.freeMemory()
        val memoryDelta = finalMemory - initialMemory

        val maxAllowedDeltaBytes = 15 * 1024 * 1024L
        assertTrue(
            memoryDelta < maxAllowedDeltaBytes,
            "Memory leak detected: Retained heap delta was ${memoryDelta / 1024 / 1024} MB after $iterations iterations"
        )
    }
}
