package com.donfyy.binderlibrary

import android.os.IBinder
import android.os.Parcel

class PersonProxy(private val mRemote: IBinder) : IPersonInterface {
    override fun addPerson(person: Person?) {
        val data = Parcel.obtain()
        val reply = Parcel.obtain()
        try {
            data.writeInterfaceToken(DESCRIPTOR)
            if (person != null) {
                data.writeInt(1)
                person.writeToParcel(data, 0)
            } else {
                data.writeInt(0)
            }

            val status = mRemote.transact(PersonStub.TRANSACTION_addPerson, data, reply, 0);

            if (status) {
                reply.readException()
            }
        } finally {
            data.recycle()
            reply.recycle()
        }
    }

    override fun getPersonList(): List<Person>? {
        val data = Parcel.obtain()
        val reply = Parcel.obtain()
        var result: List<Person>? = null
        try {
            data.writeInterfaceToken(DESCRIPTOR)
            val status = mRemote.transact(PersonStub.TRANSACTION_getPersonList, data, reply, 0)

            if (status) {
                reply.readException()
                result = reply.createTypedArrayList(Person.CREATOR)
            }
        } finally {
            data.recycle()
            reply.recycle()
        }
        return result
    }

    override fun asBinder(): IBinder {
        return mRemote
    }
}