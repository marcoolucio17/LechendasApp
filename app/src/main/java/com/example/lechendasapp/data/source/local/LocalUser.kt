package com.example.lechendasapp.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

//TODO: HASH PASSWORDS
@Entity(
    tableName = "users",
    indices = [Index(value = ["email"], unique = true)]
)
data class LocalUser (
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,

    //personal data
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "birth_date") val birthDate : String,
    val country: String,
    val occupation: String? = null,
    val height: String,

    //credentials
    @ColumnInfo(name = "email") val email: String,
    val password: String,

    //other
    val team: Int,
    val role: String,
)