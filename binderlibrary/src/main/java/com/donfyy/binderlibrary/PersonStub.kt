package com.donfyy.binderlibrary

import android.os.Binder
import android.os.IBinder
import android.os.Parcel

abstract class PersonStub : Binder(), IPersonInterface {
    init {
       attachInterface(this, DESCRIPTOR)
    }
    override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
        when (code) {
            IBinder.INTERFACE_TRANSACTION -> {
                reply?.writeString(DESCRIPTOR)
                return true
            }
            TRANSACTION_addPerson -> {
                data.enforceInterface(DESCRIPTOR)
                var person: Person? = null
                if (1 == data.readInt()) {
                    person = Person.createFromParcel(data)
                }
                addPerson(person)
                reply?.writeNoException()
                return true
            }
            TRANSACTION_getPersonList -> {
                data.enforceInterface(DESCRIPTOR)
                val personList = getPersonList()
                reply?.writeNoException()
                reply?.writeTypedList(personList)
                return true
            }
            else -> {
                return super.onTransact(code, data, reply, flags)
            }
        }
    }

    override fun asBinder(): IBinder {
        return this
    }

    companion object {
        const val TRANSACTION_addPerson = Binder.FIRST_CALL_TRANSACTION + 0
        const val TRANSACTION_getPersonList = Binder.FIRST_CALL_TRANSACTION + 1

        @JvmStatic
        fun asInterface(iBinder: IBinder?) : IPersonInterface? {
            if (iBinder == null) {
                return null
            }

            val localInterface = iBinder.queryLocalInterface(DESCRIPTOR)
            if (localInterface is IPersonInterface) {
                return localInterface
            }

            return PersonProxy(iBinder)
        }
    }
}