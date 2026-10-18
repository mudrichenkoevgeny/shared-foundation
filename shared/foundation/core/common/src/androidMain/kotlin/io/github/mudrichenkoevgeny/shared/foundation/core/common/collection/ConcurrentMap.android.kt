package io.github.mudrichenkoevgeny.shared.foundation.core.common.collection

import java.util.concurrent.ConcurrentHashMap

internal actual fun <K : Any, V : Any> createConcurrentMap(): MutableMap<K, V> =
    ConcurrentHashMap()
