package ro.platon.xmler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by platon on 13.04.2017.
 */
public class XmlerRead {
    protected int index;
    protected DocumentBuilderFactory documentBuilderFactory;
    protected DocumentBuilder documentBuilder;
    protected Document document;

    Map<String,Class> builtInMap = new HashMap<String,Class>();{
        builtInMap.put("int", Integer.TYPE );
        builtInMap.put("long", Long.TYPE );
        builtInMap.put("double", Double.TYPE );
        builtInMap.put("float", Float.TYPE );
        builtInMap.put("bool", Boolean.TYPE );
        builtInMap.put("char", Character.TYPE );
        builtInMap.put("byte", Byte.TYPE );
        builtInMap.put("short", Short.TYPE );
    }

    public XmlerRead() {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        index = 0;
    }

    public Object readObject(String filename) throws Exception {
        document = documentBuilder.parse(new File(filename));
        Element rootElement = document.getDocumentElement();
        rootElement.getTagName();
        NodeList nodeList = rootElement.getElementsByTagName("object");
        Element node = (Element) nodeList.item(index);
        Object result = readObjectNode(node);
        index++;
        return result;
    }

    public Object readObjectNode(Element node) throws Exception {
        String className = node.getAttribute("type");
        Class objClass = Class.forName(className);
        if (isWrapperType(objClass)) {
            return toPrimitiveObject(objClass, node.getTextContent());
        }
        Object result = objClass.newInstance();

        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) childNode;
                String name = element.getNodeName();
                Field field = getField(objClass, name);
                field.setAccessible(true);
                if (field.getType().isPrimitive() || field.getType().equals(String.class)) {
                    String value = element.getChildNodes().item(0).getTextContent();
                    field.set(result, toPrimitiveObject(field.getType(), value));
                } else {
                    if (field.get(result) instanceof Collection) {
                        Collection collection = (Collection) field.getType().newInstance();
                        NodeList arrayNodes = element.getChildNodes();
                        for (int j = 0; j < arrayNodes.getLength(); j++) {
                            Node arrayNode = arrayNodes.item(j);
                            if (arrayNode.getNodeType() == Node.ELEMENT_NODE) {
                                collection.add(readObjectNode((Element) arrayNode));
                            }
                        }
                        field.set(result, collection);
                    } else {
                        field.set(result, readObjectNode(element));
                    }
                }
            }
        }
        return result;
    }


    private Field getField(Class clazz, String fieldName)
            throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class superClass = clazz.getSuperclass();
            if (superClass == null) {
                throw e;
            } else {
                return getField(superClass, fieldName);
            }
        }
    }

    private Object toPrimitiveObject(Class clazz, String value) {
        if (boolean.class == clazz) return Boolean.parseBoolean(value);
        if (byte.class == clazz) return Byte.parseByte(value);
        if (short.class == clazz) return Short.parseShort(value);
        if (int.class == clazz) return Integer.parseInt(value);
        if (long.class == clazz) return Long.parseLong(value);
        if (float.class == clazz) return Float.parseFloat(value);
        if (double.class == clazz) return Double.parseDouble(value);
        if (String.class == clazz) return value;
        return value;
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
