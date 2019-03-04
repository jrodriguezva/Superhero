package com.jrodriguezva.superhero.utils

import android.content.Context
import com.jrodriguezva.superhero.utils.extension.networkInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHandler
@Inject constructor(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnected ?: false
}