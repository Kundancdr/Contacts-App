package com.example.contactapp.Presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contacts.data.Contact
import com.example.contacts.data.ContactDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContacsViewModel @Inject constructor (var database: ContactDatabase):ViewModel() {

    private val isSortedByName = MutableStateFlow(true)
    private val contact = isSortedByName.flatMapLatest {
        if (it) {
            database.dao.getContactSortByName()
        } else {
            database.dao.getContactSortByDate()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    val _state = MutableStateFlow(ContactState())

    val state = combine(_state, contact , isSortedByName){
        _state,contact,isSoretByName ->

        _state.copy(contact = contact)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ContactState())


    fun changeSorting(){
        isSortedByName.value = !isSortedByName.value
    }

    fun saveContact(){
        val contact = Contact(name = state.value.name.value,
            id = state.value.id.value,
            number = state.value.number.value,
            email = state.value.email.value,
            dateOfCreation = System.currentTimeMillis().toLong(),
            isActive = true,
            image = state.value.image.value

            )
        viewModelScope.launch {
            database.dao.upsertContact(contact)
        }

        state.value.id.value = 0
        state.value.name.value = ""
        state.value.number.value = ""
        state.value.email.value = ""
        state.value.dateOfCreation.value = 0
        state.value.image.value= ByteArray(0)

    }

    fun deleteContacts(){

        val contact = Contact(
            id = state.value.id.value,
            name = state.value.name.value,
            number = state.value.number.value,
            email = state.value.email.value,
            dateOfCreation = state.value.dateOfCreation.value,
            isActive = true
        )
        viewModelScope.launch {
            database.dao.deleteContact(contact = contact)
        }

        state.value.id.value = 0
        state.value.name.value = ""
        state.value.number.value = ""
        state.value.email.value = ""
        state.value.dateOfCreation.value = 0




    }

}



