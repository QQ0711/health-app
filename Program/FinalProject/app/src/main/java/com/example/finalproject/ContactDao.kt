package com.example.finalproject

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao {
    @Query("Select * from Contacts where datemark like :x")
    fun getAllContacts(x:String) : LiveData<List<Contacts>>

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    fun insertContact(contact : Contacts)

    @Delete
    fun delete(contact: Contacts)
}