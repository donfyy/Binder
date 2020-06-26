package com.donfyy.binderlibrary

import android.os.IInterface

const val DESCRIPTOR = "com.donfyy.binderlibrary.IPersonInterface"
interface IPersonInterface : IInterface {
    fun addPerson(person: Person?)
    fun getPersonList(): List<Person>?
}