// IMyAidlInterface.aidl
package com.feng.demo.aidlthecustomdatatypes;

// Declare any non-default types here with import statements
import com.feng.demo.aidlthecustomdatatypes.Person;
interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<Person> add(in Person person);
//    int add(int a,int b);
}
