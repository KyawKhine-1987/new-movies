package com.android.freelance.wonders.ui.util

import kotlinx.coroutines.*

//Deferred is a job without result.
fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}