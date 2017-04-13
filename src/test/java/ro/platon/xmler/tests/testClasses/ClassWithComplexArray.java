package ro.platon.xmler.tests.testClasses;

import java.util.ArrayList;

/**
 * Created by platon on 05.04.2017.
 */
public class ClassWithComplexArray extends BasicClass {
    public ArrayList<BasicClass> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<BasicClass> arrayList) {
        this.arrayList = arrayList;
    }

    public void add(BasicClass element) {
        arrayList.add(element);
    }

    ArrayList<BasicClass> arrayList;

    public ClassWithComplexArray() {
        arrayList = new ArrayList<BasicClass>();
    }

    @Override
    public String toString() {
        return "ClassWithComplexArray{" +
                super.toString() +
                "arrayList=" + arrayList +
                '}';
    }
}
