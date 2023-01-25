package com.example.ertah.Register

import androidx.lifecycle.ViewModel

class RegisterViewModel: ViewModel() {

    var phone = ""
    var name = ""
    var email = ""

    fun checkName( name:String): Boolean = name.isEmpty()
    fun checkPhone(phone:String): Boolean = phone.isEmpty()
    fun checkEmail(email:String): Boolean = email.length == 0
}