package com.aicontent.status

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform