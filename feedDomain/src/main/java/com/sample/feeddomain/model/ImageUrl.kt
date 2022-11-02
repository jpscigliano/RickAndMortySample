package com.sample.feeddomain.model

import java.net.URL

@JvmInline
value class ImageUrl private constructor(val value: String) {
    init {
        require(runCatching { URL(value) }.isSuccess)
    }

    companion object {
        @Throws(IllegalArgumentException::class)
        operator fun invoke(value: String?): ImageUrl {
            requireNotNull(value) { "Url cannot be null" }
            return ImageUrl(value)
        }
    }
}


