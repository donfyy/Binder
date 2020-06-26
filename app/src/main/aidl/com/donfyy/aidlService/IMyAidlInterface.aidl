// IMyAidlInterface.aidl
package com.donfyy.aidlService;

// Declare any non-default types here with import statements
parcelable Person;

interface IMyAidlInterface {
    void addPerson(in Person person);
    List<Person> getPersonList();
}
