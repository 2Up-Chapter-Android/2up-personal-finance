package com.aicontent.category

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform