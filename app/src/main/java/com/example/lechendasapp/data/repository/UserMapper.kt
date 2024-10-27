package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.User
import com.example.lechendasapp.data.source.local.LocalUser


//App (External) to local
//TODO: debe llevar el password??
fun User.toLocal() = LocalUser (
    firstName = firstName,
    lastName = lastName,
    email = email,
    password = password,
    birthDate = birthDate,
    country = country,
    occupation = occupation
)

fun List<User>.toLocal() = map { it.toLocal() }

//Local to External(App)
//TODO: chequen lo de nullability del occupation y el .toString() como solución rápida
fun LocalUser.toExternal() = User (
    id = id,
    firstName = firstName,
    lastName = lastName,
    email = email,
    password = password,
    birthDate = birthDate,
    country = country,
    occupation = occupation.toString()
)

//PUEDE TENER ERROR DE COMPILACIÓN, EN TAL CASO AGREGAR @JvmName("localToExternal")
// P.D. pueden cambiar lo que esta entre ""
@JvmName("localToExternal")
fun List<LocalUser>.toExternal() = map { it.toExternal() }

