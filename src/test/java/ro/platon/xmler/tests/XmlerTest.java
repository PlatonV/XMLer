package ro.platon.xmler.tests;

import org.junit.Test;
import ro.platon.xmler.tests.testClasses.BasicClass;
import ro.platon.xmler.tests.testClasses.ClassWithArray;
import ro.platon.xmler.tests.testClasses.ClassWithChildClass;
import ro.platon.xmler.tests.testClasses.ClassWithComplexArray;
import ro.platon.xmler.XmlerRead;
import ro.platon.xmler.XmlerWrite;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by platon on 13.04.2017.
 */
public class XmlerTest {

    @Test
    public void basicClassTest() {
        BasicClass basicObj = new BasicClass(42, "ASas  f");
        XmlerWrite writer = new XmlerWrite();
        XmlerRead reader = new XmlerRead();
        try {
            writer.writeObject(basicObj).saveToFile("test.xml");
            BasicClass testObj = (BasicClass) reader.readObject("test.xml");
            assertEquals(testObj.toString(), basicObj.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void classWithChildTest() {
        ClassWithChildClass basicObj = new ClassWithChildClass();
        basicObj.setNumber(42);
        basicObj.setString("ASDPOIAJ P");
        basicObj.setChildObject(new BasicClass(30, " aopsidjaspod "));
        XmlerWrite writer = new XmlerWrite();
        XmlerRead reader = new XmlerRead();
        try {
            writer.writeObject(basicObj).saveToFile("test.xml");
            ClassWithChildClass testObj = (ClassWithChildClass) reader.readObject("test.xml");
            assertEquals(testObj.toString(), basicObj.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void classWithArrayTest() {
        ClassWithArray obj = new ClassWithArray();
        obj.setNumber(42);
        obj.setString("aspdoifjas dfpoji ");
        obj.getArrayList().add((long) 42);
        obj.getArrayList().add((long) 43);
        obj.getArrayList().add((long) 44);
        XmlerWrite writer = new XmlerWrite();
        XmlerRead reader = new XmlerRead();
        try {
            writer.writeObject(obj).saveToFile("test.xml");
            ClassWithArray testObj = (ClassWithArray) reader.readObject("test.xml");
            assertEquals(testObj.toString(), obj.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void classWithComplexArrayTest() {
        ClassWithComplexArray obj = new ClassWithComplexArray();
        obj.setNumber(42);
        obj.setString("aspdoifjas dfpoji ");
        obj.getArrayList().add(new BasicClass(42, "asdfasdf"));
        obj.getArrayList().add(new BasicClass(43, "different number"));
        obj.getArrayList().add(new BasicClass(42, "words many words"));
        XmlerWrite writer = new XmlerWrite();
        XmlerRead reader = new XmlerRead();
        try {
            writer.writeObject(obj).saveToFile("test.xml");
            ClassWithComplexArray testObj = (ClassWithComplexArray) reader.readObject("test.xml");
            assertEquals(testObj.toString(), obj.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
