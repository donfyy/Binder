package com.donfyy.binderservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.donfyy.binderlibrary.Person
import com.donfyy.binderlibrary.PersonStub
import java.util.concurrent.ConcurrentSkipListSet

class PersonService : Service() {
    val list = ConcurrentSkipListSet<Person> { l, r-> l.grade - r.grade}
    override fun onCreate() {
        super.onCreate()
        Log.e("PersonService", "onCreate: success")
    }
    override fun onBind(intent: Intent?): IBinder? {
        Log.e("PersonService", "onBind: success")
        return object : PersonStub() {
            override fun addPerson(person: Person?) {
                if (person != null) {
                    list.add(person)
                }
            }

            override fun getPersonList(): List<Person>? {
                return list.toList()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("PersonService", "onDestroy: success")
    }

}