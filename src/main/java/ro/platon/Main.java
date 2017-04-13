package ro.platon;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by platon on 22.03.2017.
 */
public class Main {

    public static class EncoderClass {
        public String string;
        public ArrayList<Integer> array;

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }

        public ArrayList<Integer> getArray() {
            return array;
        }

        public void setArray(ArrayList<Integer> array) {
            this.array = array;
        }

        public EncoderClass() {
            this.string = "ADOIASJ DPOAISJD PO";
            this.array = new ArrayList<>();
            array.add(42);
            array.add(43);
            array.add(44);
        }

        @Override
        public String toString() {
            return "EncoderClass{" +
                    "string='" + string + '\'' +
                    ", array=" + array +
                    '}';
        }
    }

    public static void main(String[] args) {
        try {
            EncoderClass obj = new EncoderClass();
            XMLEncoder xmlEncoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("encoderOutput.xml", false)));
            xmlEncoder.writeObject(obj);
            xmlEncoder.close();

            XMLDecoder xmlDecoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("encoderOutput.xml")));
            EncoderClass readObj = (EncoderClass) xmlDecoder.readObject();
            xmlDecoder.close();
            System.out.println("Result:");
            System.out.println(readObj.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
