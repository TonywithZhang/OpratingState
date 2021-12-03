package com.minghua.opratingstate.utils

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

object AutoCookieJar : CookieJar {
    private val cache: ArrayList<Cookie> = ArrayList()
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val invalidCookies :ArrayList<Cookie> = ArrayList()
        val validCookies : ArrayList<Cookie> = ArrayList()
        for (cookie in cache)
        {
            if (cookie.expiresAt < System.currentTimeMillis())
            {
                invalidCookies.add(cookie)
            }else if (cookie.matches(url))
            {
                validCookies.add(cookie)
            }

        }
        cache.removeAll(invalidCookies.toSet())
        return validCookies
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cache.addAll(cookies)
    }
}