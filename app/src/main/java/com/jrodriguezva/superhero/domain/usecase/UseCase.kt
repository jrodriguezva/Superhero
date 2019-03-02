package com.jrodriguezva.superhero.domain.usecase

abstract class UseCase (private val executor: Executor){

    fun uiExecute(uiFun:suspend ()->Unit){
        executor.uiExecute{uiFun()}
    }
    fun asyncExecute(asyncFun:suspend()->Unit){
        executor.asyncExecute {asyncFun()}
    }

}