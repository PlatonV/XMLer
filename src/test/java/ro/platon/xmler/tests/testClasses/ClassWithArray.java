package ro.platon.xmler.tests.testClasses;

import java.util.ArrayList;

/**
 * Created by platon on 22.03.2017.
 */
public class ClassWithArray extends BasicClass {
    public ArrayList<Long> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Long> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<Long> arrayList;

    public ClassWithArray() {
        arrayList = new ArrayList<Long>();
    }

    @Override
    public String toString() {
        return "ClassWithArray{" +
                "string=" + this.getString() +
                "number=" + this.getNumber() +
                "arrayList=" + arrayList +
                '}';
    }
}
