package com.example.lechendasapp.viewmodels
import com.example.lechendasapp.data.model.User

data class EditProfileUiState (
    var profileInformation: UserProfile = UserProfile()
)

data class UserProfile(
    val firstName: String = "",
    val secondName: String = "",
    val email: String = "",
    val password: String = "",
    val phone: String = ""
)

fun User.toEditProfileUiState(): EditProfileUiState = EditProfileUiState(

)

class EditProfileViewModel {
}