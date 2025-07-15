package com.mahdi.rostamipour.rpstore.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahdi.rostamipour.rpstore.intent.ProfileIntent
import com.mahdi.rostamipour.rpstore.intent.state.ProfileState
import com.mahdi.rostamipour.rpstore.model.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(val profileRepository: ProfileRepository) : ViewModel() {

    private val _getProfileState = MutableStateFlow<ProfileState>(ProfileState.Idle)
    val getProfileState : StateFlow<ProfileState> = _getProfileState

    fun handleProfileIntent(profileIntent: ProfileIntent){
        when(profileIntent){
            is ProfileIntent.GetProfile -> {
                getProfile()
            }
        }
    }

    private fun getProfile(){
        viewModelScope.launch {
            _getProfileState.value = ProfileState.Loading
            try {
                val profile = profileRepository.getProfile()
                _getProfileState.value = ProfileState.Success(profile)
            }catch (e : Exception){
                _getProfileState.value = ProfileState.Error(e.message ?: "Error get profile")
            }
        }
    }

}