package com.donfyy.binderservice

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
import com.donfyy.binderlibrary.PersonStub

class MainActivity : AppCompatActivity() {
    var personInterface: IPersonInterface? = null
    val connection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            personInterface = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
//            在同一个进程内service就是Service.onBind()返回的实例
            Log.e("PersonService", "IBinder Instance: $service")
            personInterface = PersonStub.asInterface(service)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService(Intent(this, PersonService::class.java))
        bindService(Intent(this, PersonService::class.java), connection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        unbindService(connection)
        super.onDestroy()
    }

    //来自本地进程的调用在发起调用的同一进程内执行。
    fun onCall(view: View) {
        Log.e("PersonService", "Current thread:" + Thread.currentThread())
        Log.e("PersonService", "getPersonList:${personInterface?.getPersonList()}")
    }

    fun onOtherCall(view: View) {
        Thread { onCall(view) }.start()
    }
}
