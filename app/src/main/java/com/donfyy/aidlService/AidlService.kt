package com.donfyy.aidlService

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log


class AidlService : Service() {
    private val personList = ArrayList<Person>()
    override fun onCreate() {
        super.onCreate()
        Log.e("AidlService", "onCreate: success")
    }
    override fun onBind(intent: Intent?): IBinder? {
        Log.e("AidlService", "onBind: success")
        return object : IMyAidlInterface.Stub() {
            override fun addPerson(person: Person?) {
                if (person != null) {
                    personList.add(person)
                }
            }

            override fun getPersonList(): MutableList<Person> {
                return this@AidlService.personList
            }

        }
    }
}