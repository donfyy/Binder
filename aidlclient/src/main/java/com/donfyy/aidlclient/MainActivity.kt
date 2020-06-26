package com.donfyy.aidlclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import com.donfyy.aidlService.IMyAidlInterface
import com.donfyy.aidlService.Person

class MainActivity : AppCompatActivity() {
    var iMyAidlInterface: IMyAidlInterface? = null
    var service: IBinder? = null

    val deathRecipient = IBinder.DeathRecipient { Log.e("AidlClient", "binderDied") }
    val conn = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e("AidlClient", "onServiceDisconnected: success")
            iMyAidlInterface = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.e("AidlClient", "onServiceConnected: success")
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service)

            service?.linkToDeath(deathRecipient, 0)
            this@MainActivity.service = service
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent()
        intent.component =
            ComponentName("com.donfyy.aidlService", "com.donfyy.aidlService.AidlService")

        bindService(intent, conn, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        service?.let {
            val success = it.unlinkToDeath(deathRecipient, 0)
            Log.e("AidlClient", "unlinkToDeath$success")
        }
        unbindService(conn)
    }

    fun onCall(view: View) {
        iMyAidlInterface?.addPerson(Person("三世书", count++))
        Log.e("AidlClient", iMyAidlInterface?.personList.toString())
    }
}

var count = 0
