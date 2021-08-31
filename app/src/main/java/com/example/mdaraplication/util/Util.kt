package com.example.mdaraplication.util

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import org.jsoup.Jsoup
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object Util {
    var token = ""
    private var session = ""
    private lateinit var okHttpClient : OkHttpClient
    private lateinit var retrofitConnect : Retrofit
    private var sharedKey : SharedKey = SharedKey()

    private class SharedKey{
        public var pw = "password"
        public var idKey = "ID"
        public var nameKey = "Name"
        public var sharedPreferName = "pref"
        public var defaultUserName = "환영합니다."
        public var baseURL = "http://192.168.0.3:8080/ADRM/"
        public var autoLogin = "autoLogin"
    }

    fun getRetrofit() : Retrofit {
        return retrofitConnect;
    }

    fun setCSRF(toString: String): String {
        val html = Jsoup.parse(toString)
        val token = html.select("#csrf")
        this.token = token.attr("value")
        return this.token
    }
    fun getCSRFToken() : String{
        return this.token
    }

    fun setRetrofit(cookieJar: PersistentCookieJar) {
        this.okHttpClient = OkHttpClient.Builder().cookieJar(cookieJar).build()
        this.retrofitConnect = Retrofit.Builder().baseUrl(sharedKey.baseURL).client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create()).build()
    }

    fun getJsonArray(strJson : String): JsonArray {
        var jsonParser : JsonParser = JsonParser()
        var jsonArray : JsonArray = jsonParser.parse(strJson) as JsonArray
        return jsonArray
    }

    fun getJsonObject(toString: String): JsonObject {
        var jsonParser : JsonParser = JsonParser()
        var jsonItemSeq : JsonObject = jsonParser.parse(toString).asJsonObject
        return jsonItemSeq
    }

    fun getCookieJar(): CookieJar {
        return this.okHttpClient.cookieJar()
    }

    fun getIDKey(): String? {
        return sharedKey.idKey
    }

    fun getUserName(): String? {
        return sharedKey.nameKey
    }

    fun getSharedPrefName(): String? {
        return sharedKey.sharedPreferName
    }

    fun getDefaultUserName(): String? {
        return sharedKey.defaultUserName
    }

    fun getBaseURL() : String?{
        return sharedKey.baseURL
    }

    fun getAutoLogin(): String? {
        return sharedKey.autoLogin
    }

    fun getPassword(): String? {
        return sharedKey.pw
    }

    fun isJson(message: String): Any {
        if(message.contains("관리자에게 문의 바랍니다")){
            return false
        }
        return true
    }
}