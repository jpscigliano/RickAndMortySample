package com.sample.feeddomain.model


import java.io.Serializable

@JvmInline
value class Id private constructor(val value: Int) : Serializable {
    init {
        require(value >= 0) { "Id should be bigger than 0" }
    }

    companion object {
        @Throws(IllegalArgumentException::class)
        operator fun invoke(value: Int?): Id {
            requireNotNull(value) { "Id cannot be null" }
            return Id(value)
        }
    }
}


