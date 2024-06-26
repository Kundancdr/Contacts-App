package com.example.contactapp.Presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.contacts.data.Contact

data class ContactState (

    val contact : List<Contact> = emptyList(),
    val id : MutableState<Int> = mutableStateOf(1),
    val name : MutableState<String> = mutableStateOf(""),
    val number : MutableState<String> = mutableStateOf(""),
    val email : MutableState<String> = mutableStateOf(""),
    val dateOfCreation : MutableState<Long> = mutableStateOf(0),
    val isActive :MutableState<Boolean> = mutableStateOf(true),
    val image : MutableState<ByteArray> = mutableStateOf(ByteArray(0))

)