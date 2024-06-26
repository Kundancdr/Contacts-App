package com.example.contactapp.Navigation

sealed class Routes ( var route : String) {

    object AllContact : Routes("AllContact")
    object AddContact : Routes("AddContact")
}