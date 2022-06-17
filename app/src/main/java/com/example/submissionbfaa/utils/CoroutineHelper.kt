package com.example.submissionbfaa.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object CoroutineHelper {
    fun main(work: suspend (() -> Unit)) = CoroutineScope(Dispatchers.Main).launch { work() }

    fun oi(work: suspend (() -> Unit)) = CoroutineScope(Dispatchers.IO).launch { work() }
}