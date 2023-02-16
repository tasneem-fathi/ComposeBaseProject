package com.selsela.composebaseproject.ui.core

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selsela.composebaseproject.ui.theme.Red
import com.selsela.composebaseproject.util.InputWrapper
import com.selsela.composebaseproject.util.log
import com.selsela.composebaseproject.util.validatePassword
import com.selsela.composebaseproject.util.validatePhone
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


const val MOBILE = "mobile"
const val PASSWORD = "password"

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val application: Application
) : ViewModel() {

    val mobile = savedStateHandle.getStateFlow(MOBILE, InputWrapper())
    val password = savedStateHandle.getStateFlow(PASSWORD, InputWrapper())
    val areInputsValid = combine(mobile, password) { name, cardNumber ->
        name.value.isNotEmpty() && name.validationMessage.isNullOrEmpty() && cardNumber.value.isNotEmpty() && cardNumber.validationMessage.isNullOrEmpty()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)

    fun onMobileEntered(input: String) {
        if (input.isEmpty().not()) {
            val errorId = input.validatePhone(application.applicationContext)
            savedStateHandle[MOBILE] = mobile.value.copy(
                value = input, validationMessage = errorId,
                borderColor = if (errorId.isNotEmpty()) Red else Color.Black
            )
        } else {
            savedStateHandle[MOBILE] = mobile.value.copy(
                value = input, validationMessage = null,
                borderColor = Color.Black
            )
        }
    }

    fun onPasswordEntered(input: String) {
        if (input.isEmpty().not()) {
            val errorId = input.validatePassword(application.applicationContext)
            savedStateHandle[PASSWORD] = password.value.copy(
                value = input, validationMessage = errorId,
                borderColor = if (errorId.isNotEmpty()) Red else Color.Black
            )
        } else {
            savedStateHandle[PASSWORD] = password.value.copy(
                value = input, validationMessage = null,
                borderColor = Color.Black
            )
        }
    }

    fun onContinueClick() {
//        val resId = if (areInputsValid.value) R.string.success else R.string.validation_error
//        _events.trySend(ScreenEvent.ShowToast(resId))
    }
}