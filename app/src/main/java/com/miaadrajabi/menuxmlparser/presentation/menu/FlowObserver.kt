package com.miaadrajabi.menuxmlparser.presentation.menu

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FlowObserver<T>(
    private val lifecycle: Lifecycle,
    private val flow: StateFlow<T>,
    private val block: (T) -> Unit
) : DefaultLifecycleObserver {
    private var job: Job? = null

    override fun onStart(owner: LifecycleOwner) {
        job = owner.lifecycleScope.launch {
            flow.collect { block(it) }
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        job?.cancel()
    }
}


