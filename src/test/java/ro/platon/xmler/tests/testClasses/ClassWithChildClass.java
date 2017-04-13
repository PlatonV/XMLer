package ro.platon.xmler.tests.testClasses;

/**
 * Created by platon on 13.04.2017.
 */
public class ClassWithChildClass extends BasicClass {
    BasicClass childObject;

    public BasicClass getChildObject() {
        return childObject;
    }

    public void setChildObject(BasicClass childObject) {
        this.childObject = childObject;
    }

    @Override
    public String toString() {
        return "ClassWithChildClass{" +
                super.toString() +
                "childObject=" + childObject +
                '}';
    }
}
