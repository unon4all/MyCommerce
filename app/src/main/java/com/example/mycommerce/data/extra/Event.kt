package com.example.mycommerce.data.extra

open class Event<out T>(private val content: T) {

    private var hasBeenHandled = false
        private set // Allow external read but not write

    fun getContentOrNull(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}