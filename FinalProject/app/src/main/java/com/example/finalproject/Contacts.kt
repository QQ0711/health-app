package com.example.finalproject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contacts")
class Contacts (
    @PrimaryKey(autoGenerate = true)
    var id : Int?=null,
    var datemark : String,
    var food : String,
    var quantity: String
)