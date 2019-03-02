package com.jrodriguezva.superhero.domain.usecase

interface Executor {
    fun uiExecute(uiFun:suspend ()->Unit)
    fun asyncExecute(asyncFun:suspend()->Unit)
}