package com.sample.feedpresentation

sealed class UiMessage {
    object ShowGenericError : UiMessage()
    object ShowNoInternetMessage : UiMessage()
    object ShowCharacterInvalidMessage : UiMessage()
    object UnableToFetchData : UiMessage()
}