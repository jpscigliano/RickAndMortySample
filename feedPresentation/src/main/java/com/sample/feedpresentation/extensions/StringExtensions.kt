package com.sample.feedpresentation.extensions


inline fun String.ifNotEmpty(defaultValue: (String) -> Unit) {
    if (isEmpty().not()){ defaultValue(this)}
}
