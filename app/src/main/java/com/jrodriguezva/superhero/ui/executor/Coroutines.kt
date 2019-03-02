package com.jrodriguezva.superhero.ui.executor

import com.jrodriguezva.superhero.domain.usecase.Executor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Singleton
class Coroutines : Executor {
    override fun uiExecute(uiFun: suspend () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            uiFun()
        }
    }

    override fun asyncExecute(asyncFun: suspend () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            asyncFun()
        }
    }
}