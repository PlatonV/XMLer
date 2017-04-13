package ro.platon.xmler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by platon on 13.04.2017.
 */
public class XmlerWrite {
    protected DocumentBuilderFactory documentBuilderFactory;
    protected DocumentBuilder documentBuilder;
    protected Document document;
    protected Element rootElement;

    public XmlerWrite() {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        rootElement = document.createElement("root");
        document.appendChild(rootElement);
    }

    public XmlerWrite writeObject(Object obj) throws IllegalAccessException {
        writeObject(obj, rootElement);
        return this;
    }

    protected void writeObject(Object object, Element baseElement) throws IllegalAccessException {
        Class objectClass = object.getClass();
        Element objElement = document.createElement("object");
        objElement.setAttribute("type", objectClass.getTypeName());
        if (isWrapperType(objectClass)) {
            objElement.appendChild(document.createTextNode(object.toString()));
        } else {
            for (Field field : getAllFields(objectClass)) {
                field.setAccessible(true);
                Element fieldElement = document.createElement(field.getName());
                fieldElement.setAttribute("type", field.getType().toString());
                if (field.getType().isPrimitive()
                        || field.getType().equals(String.class)) {
                    fieldElement.appendChild(document.createTextNode(field.get(object).toString()));
                } else {
                    Object[] values;
                    if (field.get(object) instanceof Collection) {
                        values = ((Collection) field.get(object)).toArray();
                        for (Object o : values) {
                            writeObject(o, fieldElement);
                        }
                    } else if (field.get(object) instanceof Object[]) {
                        values = ((Collection) field.get(object)).toArray();
                        for (Object o : values) {
                            writeObject(o, fieldElement);
                        }
                    } else {
                        writeObject(field.get(object), fieldElement);
                    }
                }
                objElement.appendChild(fieldElement);
            }
        }
        baseElement.appendChild(objElement);
    }

    public void saveToFile(String fileName) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(fileName));
            try {
                transformer.transform(source, result);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
    }

    public Field[] getAllFields(Class aClass) {
        List<Field> fields = new ArrayList<Field>();
        fields.addAll(Arrays.asList(aClass.getDeclaredFields()));
        if (aClass.getSuperclass() != null) {
            fields.addAll(Arrays.asList(getAllFields(aClass.getSuperclass())));
        }
        return fields.toArray(new Field[] {});
    }

    protected boolean isWrapperType(Class c) {
        return c.equals(Boolean.class) ||
                c.equals(Integer.class) ||
                c.equals(Character.class) ||
                c.equals(Byte.class) ||
                c.equals(Short.class) ||
                c.equals(Double.class) ||
                c.equals(Long.class) ||
                c.equals(Float.class);
    }

}
