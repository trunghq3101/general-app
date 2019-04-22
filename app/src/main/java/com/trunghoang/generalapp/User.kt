package com.trunghoang.generalapp

data class User(
    var name: String? = "",
    var age: Int? = 0,
    var gender: String? = "",
    var friendStatus: String? = "Chưa kết bạn"
) {
    companion object {
        const val MALE = "Male"
        const val FEMALE = "Female"
    }
}