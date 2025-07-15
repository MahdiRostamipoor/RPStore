package com.mahdi.rostamipour.rpstore.intent

sealed class ProfileIntent {
    object GetProfile : ProfileIntent()
}