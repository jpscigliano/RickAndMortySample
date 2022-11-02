package com.sample.feeddomain.model

@JvmInline
value class Name (
    val value: String,
) {
    init {
        require(value.isNotEmpty()) { "Name cannot be empty" }
    }

    companion object {
        @Throws(IllegalArgumentException::class)
        operator fun invoke(value: String?): Name {
            requireNotNull(value) { "Name cannot be null" }
            return Name(value)
        }
    }

}



