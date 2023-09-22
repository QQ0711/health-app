package com.example.finalproject

import androidx.lifecycle.LiveData

class ContactRepository(val dao : ContactDao)
{
    // function to get all contacts from the database
    fun getAllContacts(x:String) : LiveData<List<Contacts>> {
        return dao.getAllContacts(x)
    }

    // function to insert a contact in the database
    fun insertContact(contact : Contacts) {
        dao.insertContact(contact)
    }

    // function to delete a contact from the database
    fun deleteContact(contact: Contacts) {
        dao.delete(contact)
    }
}
