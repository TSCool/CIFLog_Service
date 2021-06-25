package com.cif.utils.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.h2.value.CaseInsensitiveMap;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class XmlReadWrite {
    private Document doc;
    private Node rootNode;
    private String strXMLFile = "";

    public XmlReadWrite(String fileName) {
        strXMLFile = fileName;
    }

    public XmlReadWrite() {
    }

    public Document getDoc() {
        return doc;
    }

    public void parseDoc() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(new File(strXMLFile));
        } catch (Throwable t) {
            t.printStackTrace();
        }
        if (doc != null) {
            rootNode = doc.getDocumentElement();
        }
    }

    public void parseDoc(InputStream in) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(in);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        if (doc != null) {
            rootNode = doc.getDocumentElement();
        }
    }

    public Document createDoc() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.newDocument();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return doc;
    }

    public Element writeMainNode(String applicationName, String version) {
        Element ele = doc.createElement("Main");
        doc.appendChild(ele);
        ele.setAttribute("Application", applicationName);
        ele.setAttribute("Version", version);
        return ele;
    }

    public String getApplicationName() {
        if (rootNode == null) {
            return null;
        }
        if (rootNode.getNodeType() == Node.ELEMENT_NODE) {
            if ("Main".equals(rootNode.getNodeName())) {
                return parseAttribute(rootNode, "Application");
            }
        }
        return null;
    }

    public String getVersion() {
        if (rootNode == null) {
            return null;
        }
        if (rootNode.getNodeType() == Node.ELEMENT_NODE) {
            if ("Main".equals(rootNode.getNodeName())) {
                return parseAttribute(rootNode, "Version");
            }
        }
        return null;
    }

    public boolean isRightFormat(String applicationName) {
        String name = getApplicationName();
        if (name == null) {
            return false;
        }
        if (name.compareToIgnoreCase(applicationName) == 0) {
            return true;
        }
        return false;
    }

    public org.w3c.dom.NodeList readTagName(String tagName) {
        NodeList nodeList = doc.getElementsByTagName(tagName);

        return nodeList;
    }

    public org.w3c.dom.Node getRootNode() {
        return rootNode;
    }

    public org.w3c.dom.Node findNode(NodeList nodeList, String strNodeName) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeName().equals(strNodeName)) {
                return node;
            }
        }

        return null;
    }

    public Node findNode(Node parentNode, String strNodeName) {
        NodeList nodeList = (parentNode == null) ? null : parentNode.getChildNodes();
        if (nodeList != null) {
            return findNode(nodeList, strNodeName);
        } else {
            return null;
        }
    }

    public ArrayList<Node> findNodeList(Node parentNode, String strNodeName) {
        NodeList nodeList = (parentNode == null) ? null : parentNode.getChildNodes();
        ArrayList<Node> nodes = new ArrayList<Node>();
        if (nodeList != null) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i).getNodeName().equals(strNodeName)) {
                    nodes.add(nodeList.item(i));
                }
            }
        }
        return nodes;
    }

    public Element writeElement(String strTagName) {
        Element ee = doc.createElement(strTagName);
        return ee;
    }

    public void writeAttribute(Element element, String strName, String strValue) {
        element.setAttribute(strName, strValue);
    }

    public void writeAttribute(Element element, String strName, int strValue, int defaultValue) {
        if (strValue != defaultValue) {
            element.setAttribute(strName, Integer.toString(strValue));
        }
    }

    public void writeAttribute(Element element, String strName, float strValue, float defaultValue) {
        if (Math.abs(strValue - defaultValue) < 0.000001) {
            return;
        }
        element.setAttribute(strName, Float.toString(strValue));
    }

    public void writeAttribute(Element element, String strName, double strValue, double defaultValue) {
        if (Math.abs(strValue - defaultValue) < 0.000001) {
            return;
        }
        element.setAttribute(strName, Double.toString(strValue));
    }

    public void writeAttribute(Element element, String strName, boolean strValue, boolean defaultValue) {
        if (strValue != defaultValue) {
            element.setAttribute(strName, Boolean.toString(strValue));
        }
    }

    public void writeAttribute(Element element, String strName, String strValue, String defaultValue) {
        if (strValue != null && !strValue.equals(defaultValue)) {
            element.setAttribute(strName, strValue);
        }
    }

    public void writeNode(Element element) {
        doc.appendChild(element);
    }

    public void writeNode(Element parent, Element element) {
        parent.appendChild(element);
    }

    public void writeNode(Element parent, Text sub) {
        parent.appendChild(sub);
    }

    /**
     * 写入XML到字节流（不关闭流）
     *
     * @param outputStream 输出流
     * @return 是否成功写入
     */
    public boolean writeXMLToStream(OutputStream outputStream) {
        if (outputStream == null) {
            return false;
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException ex) {
            ex.printStackTrace();
            return false;
        }
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        try {
            transformer.transform(new DOMSource(doc), new StreamResult(outputStream));
            return true;
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 写入XML到字节流（不关闭流）
     *
     * @param out 输出流
     * @deprecated 由更通用的 writeXMLToStream(OutputStream) 替代
     */
    @Deprecated
    public void writeXMLToFile(ByteArrayOutputStream out) {
        if (out != null) {
            writeXMLToStream(out);
        }
    }

    public void writeXMLToFile() {
        if (strXMLFile != null && !strXMLFile.isEmpty()) {
            FileOutputStream fileOutputStream;
            try {
                fileOutputStream = FileUtils.openOutputStream(new File(strXMLFile));
            } catch (IOException ex) {
                return;
            }
            writeXMLToStream(fileOutputStream);
            try {
                fileOutputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 读取xml属性的值，如果没有该属性，返回空
     *
     * @param node
     * @param attributeName
     * @return
     */
    public String parseAttribute(Node node, String attributeName) {
        String buf = null;
        NamedNodeMap attributes = null;
        if (node == null) {
            return null;
        } else {
            attributes = node.getAttributes();
        }

        for (int i = 0; i < attributes.getLength(); i++) {
            String name = attributes.item(i).getNodeName();
            if (attributeName.compareToIgnoreCase(name) == 0) {
                buf = attributes.item(i).getNodeValue();
                break;
            }
        }
        return buf;
    }

    public Text WriteText(String strTextNode) {
        return doc.createTextNode(strTextNode);
    }

    public String ParseText(Node textNode, String strTextName) {
        NodeList nodes = textNode.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.TEXT_NODE && node.getNodeName().equals(strTextName)) {
                return node.getNodeValue();
            }
        }
        return "";
    }

    public int parseInt(org.w3c.dom.Node node, String name, int org) {
        String str = parseAttribute(node, name);
        int ret = org;

        if (str == null || str.trim().equals("")) {
            return ret;
        } else if (str.toUpperCase().startsWith("0X")) {
            str = str.substring(2);
            try {
                ret = (int) Long.parseLong(str, 16);
            } catch (Exception e) {
                System.out.println(e + " Color");
            }
        } else if (str.startsWith("#")) {
            str = str.substring(1);
            try {
                ret = (int) Long.parseLong(str, 16);
            } catch (Exception e) {
                System.out.println(e + " Color");
            }
        } else {
            try {
                ret = Integer.parseInt(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public long parseLong(org.w3c.dom.Node node, String name, long org) {
        String str = parseAttribute(node, name);
        long ret = org;
        try {
            if (str == null || str.trim().equals("")) {
                return ret;
            } else if (str.toUpperCase().startsWith("0X")) {
                str = str.substring(2);
                ret = Long.parseLong(str, 16);
            } else {
                ret = Long.parseLong(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public byte parseByte(org.w3c.dom.Node node, String name, byte org) {
        String str = parseAttribute(node, name);
        byte ret = org;
        try {
            if (str == null || str.trim().equals("")) {
                return ret;
            } else if (str.toUpperCase().startsWith("0X")) {
                str = str.substring(2);
                ret = Byte.parseByte(str, 16);
            } else {
                ret = Byte.parseByte(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public boolean parseBoolean(org.w3c.dom.Node node, String name, boolean org) {
        boolean ret = org;
        try {
            String temp = parseAttribute(node, name);
            if (temp != null && !temp.trim().equals("")) {
                ret = Boolean.parseBoolean(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public float parseFloat(org.w3c.dom.Node node, String name, float org) {
        float ret = org;
        try {
            String temp = parseAttribute(node, name);
            if (temp != null && !temp.trim().equals("")) {
                ret = Float.parseFloat(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public double parseDouble(org.w3c.dom.Node node, String name, double org) {
        double ret = org;
        try {
            String temp = parseAttribute(node, name);
            if (temp != null && !temp.trim().equals("")) {
                ret = Double.parseDouble(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public String parse(org.w3c.dom.Node node, String name) {
        return parse(node, name, "");
    }

    public String parse(org.w3c.dom.Node node, String name, String org) {
        String ret = "";
        try {
            ret = parseAttribute(node, name);
            if (ret == null) {
                ret = org;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 向xml中写颜色信息
     *
     * @param element
     * @param name
     * @param clr
     */
    public void saveColor(Element element, String name, Color clr) {
        writeAttribute(element, name, formatColor(clr));
    }

    /**
     * 格式化颜色为十六进制字符串
     *
     * @param color 要格式化的颜色
     * @return 返回颜色的十六进制格式化字符串 为 null 时返回 "nullValue"
     */
    public String formatColor(Color color) {
        if (color == null) {
            return "nullValue";
        }
        //"%#010X"表示大写以0X开头的长度为10的补0字符串(8位十六进制)。
        //"#%08X"表示大写以#开头的8位十六进制的补0字符串。
        return String.format("%#010X", color.getRGB());
    }

    public Color loadColor(Node root, String nodeName, Color color) {
        return parseColor(root, nodeName, color);
    }

    public Color parseColor(Node node, String name, Color org) {
        String NULL = "null";
        String noColor = "nullValue";
        String str = parseAttribute(node, name);
        if (true) {
            return getColor(str, org);
        }
        int ret = 0;
        try {
            if (str == null || str.trim().equals("")) {
                return org;
            } else if (NULL.equalsIgnoreCase(str) || noColor.equalsIgnoreCase(str)) {
                return null;
            } else if (str.toUpperCase().startsWith("0X")) {
                str = str.substring(2);
                ret = (int) Long.parseLong(str, 16);
            } else {
                ret = Integer.parseInt(str);
            }
        } catch (Exception e) {
            return org;
        }

        if (str.length() > 6) {
            return new Color(ret, true);
        } else {
            return new Color(ret);
        }
    }

    private Map<String, Color> colorMap = new CaseInsensitiveMap<Color>() {
        {
            put("nullValue", null);
            put("null", null);
            put("BLACK", Color.BLACK);
            put("BLUE", Color.BLUE);
            put("CYAN", Color.CYAN);
            put("GRAY", Color.GRAY);
            put("GREEN", Color.GREEN);
            put("MAGENTA", Color.MAGENTA);
            put("ORANGE", Color.ORANGE);
            put("PINK", Color.PINK);
            put("RED", Color.RED);
            put("WHITE", Color.WHITE);
            put("YELLOW", Color.YELLOW);
        }
    };

    public boolean contains(String colorString) {
        return colorMap.containsKey(colorString);
    }

    public Color getSystemColor(String colorString) {
        return colorMap.get(colorString);
    }

    public Color getColor(String nm, Color v) {
        if (StringUtils.isBlank(nm)) {
            return v;
        }

        if (contains(nm)) {
            // 加载系统颜色 包括无颜色 null
            return getSystemColor(nm);
        }

        // 支持六位或以内的十六进制数字串
        boolean hasAlpha;
        if (nm.startsWith("#")) {
            hasAlpha = (nm.length() > 7);
        } else if (nm.startsWith("0X") || nm.startsWith("0x")) {
            hasAlpha = (nm.length() > 8);
        } else {
            hasAlpha = true;
        }
        int i;
        try {
            i = Long.decode(nm).intValue();
        } catch (NumberFormatException numberFormatException) {
            return v;
        }
        return new Color(i, hasAlpha);
    }
}
