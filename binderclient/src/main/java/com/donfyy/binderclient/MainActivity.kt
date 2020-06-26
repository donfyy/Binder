package com.donfyy.binderclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import com.donfyy.binderlibrary.IPersonInterface
import com.donfyy.binderlibrary.Person
import com.donfyy.binderlibrary.PersonStub

class MainActivity : AppCompatActivity() {
    var personInterface : IPersonInterface? = null
    val conn = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e("PersonClient", "onServiceDisconnected: success")
            personInterface = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.e("PersonClient", "onServiceConnected: success")
            personInterface = PersonStub.asInterface(service)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent()
        intent.component = ComponentName("com.donfyy.binderservice", "com.donfyy.binderservice.PersonService")
        bindService(intent, conn, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        unbindService(conn)
        super.onDestroy()
    }

    fun onCall(view: View) {
        personInterface?.let {
            it.addPerson(Person("三世书", count++))
            Log.e("PersonClient", it.getPersonList().toString())
        }
    }
}
var count = 0
