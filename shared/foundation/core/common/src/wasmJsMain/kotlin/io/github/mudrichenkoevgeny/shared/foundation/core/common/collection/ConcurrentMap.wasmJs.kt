package io.github.mudrichenkoevgeny.shared.foundation.core.common.collection

internal actual fun <K : Any, V : Any> createConcurrentMap(): MutableMap<K, V> =
    mutableMapOf()
