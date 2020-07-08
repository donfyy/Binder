// IPersonAidlInterface.aidl
package com.donfyy.binderlibrary;

// Declare any non-default types here with import statements
parcelable Person;
interface IPersonAidlInterface {
    void addPerson(in Person person);
    List<Person> getPersonList();
}
