package ru.androidschool.intensiv.util

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

class HttpLogging : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        val tag = "OkHttp"
        var logger = Timber.tag(tag)
        if (!message.startsWith("{")) {
            logger.d(message)
            return
        }
        try {
            val json = GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(JsonParser().parse(message))
            logger.d(json)
        } catch (e: JsonSyntaxException) {
            logger.w(e, message)
        }
    }
}
