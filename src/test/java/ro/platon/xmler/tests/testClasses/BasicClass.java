package ro.platon.xmler.tests.testClasses;

/**
 * Created by platon on 22.03.2017.
 */
public class BasicClass {
    protected int number;
    protected String string;

    public BasicClass() { }

    public BasicClass(int number, String string) {
        this.number = number;
        this.string = string;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return "BasicClass{" +
                "number=" + number +
                ", string='" + string + '\'' +
                '}';
    }
}
