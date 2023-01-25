package com.example.ertah.login

import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    var phone = ""
    fun checkPhone(phone: String): Boolean = phone.isEmpty()
}
