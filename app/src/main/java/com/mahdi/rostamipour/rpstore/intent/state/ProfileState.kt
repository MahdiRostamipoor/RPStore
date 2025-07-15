package com.mahdi.rostamipour.rpstore.intent.state

import com.mahdi.rostamipour.rpstore.model.ProfileModel

sealed class ProfileState {
    object Idle : ProfileState()
    object Loading : ProfileState()
    data class Success(val profile : ProfileModel) : ProfileState()
    data class Error(val message : String) : ProfileState()
}