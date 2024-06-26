package com.example.contacts.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "contact_table")
data class Contact (
    @PrimaryKey(autoGenerate = true) var id : Int = 0,
    @ColumnInfo(name = "user_name") var name : String?=null,
    var number : String?=null,
    var email : String?=null,
    var dateOfCreation : Long?=null,
    var isActive : Boolean,
    val image : ByteArray? = null
)