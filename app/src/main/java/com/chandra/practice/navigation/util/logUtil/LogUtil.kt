package com.chandra.practice.navigation.util.logUtil

import android.util.Log
import com.chandra.practice.navigation.BuildConfig

enum class LogType {
    DEBUG , WARN , INFO , ERROR
}

object LogUtil {

    fun log(keyName : String , value : Any? , type : LogType = LogType.DEBUG) {
        if (! BuildConfig.DEBUG) return //Only in DEBUG MODE
        val stackTrace = Throwable().stackTrace[1] //Caller Info
        val tag = stackTrace.className.substringAfterLast(".") //Auto Class name
        val fileName = stackTrace.fileName //File Name For Clickable Link
        val methodName = stackTrace.methodName //Auto Method Name
        val lineNumber = stackTrace.lineNumber //Auto Line Number

        val dataType = value?.let { it::class.simpleName } ?: "null"
        val logMessage = "($fileName:$lineNumber)-> ($keyName:$methodName) ($dataType) = $value"
        when (type) {
            LogType.DEBUG -> Log.d(tag , logMessage)
            LogType.WARN -> Log.w(tag , logMessage)
            LogType.INFO -> Log.i(tag , logMessage)
            LogType.ERROR -> Log.e(tag , logMessage)

        }

    }
}