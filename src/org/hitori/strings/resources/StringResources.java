package org.hitori.strings.resources;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

/**
 * Class for managing string resources
 */
public class StringResources {
    private final Map<String, StringResource> stringStringResourceMap = new LinkedHashMap<>();

    public StringResources(final File resourcesFile) {
        final Document dom;

        // Make an  instance of the DocumentBuilderFactory
        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            final DocumentBuilder db = dbf.newDocumentBuilder();

            // parse using the builder to get the DOM mapping of the
            // XML file
            dom = db.parse(resourcesFile);

            final Element doc = dom.getDocumentElement();
            System.out.println(doc.getTagName());

            final NodeList childNodeList = doc.getChildNodes();
            for (int i = 0; i < childNodeList.getLength(); ++i) {
                final Node node = childNodeList.item(i);
                final String nodeType = node.getNodeName();

                final StringResource resource;
                if (nodeType.equals("string")) {
                    // A simple string
                    // ie: <string name="blah">Blah blah</string>
                    resource = new SimpleString(getName(node), getValue(node));
                } else if (node.getNodeName().equals("string-array")) {
                    resource = new StringsArray(getName(node), getArrayValues(node));
                } else if (node.getNodeName().equals("plurals")) {
                    resource = new PluralString(getName(node), getPlurals(node));
                } else if (node.getNodeName().equals("#comment")) {
                    resource = new Comment("comment#" + i, node.getNodeValue());
                } else {
                    continue;
                }

                stringStringResourceMap.put(resource.name, resource);
            }
        } catch (final Exception cause) {
            final IllegalArgumentException exception = new IllegalArgumentException("Failed to parse strings file");
            exception.initCause(cause).fillInStackTrace();
            throw exception;
        }

        for (final Map.Entry<String, StringResource> kv : stringStringResourceMap.entrySet()) {
            if (kv.getValue() instanceof Comment) {
                System.out.println("<!-- " + ((Comment) kv.getValue()).comment + " -->");
            } else {
                System.out.println(kv.getValue().type + " : " + kv.getKey());
            }
        }
    }

    /*
     * Common processing routines
     */

    private String getName(final Node node) {
        return node.getAttributes().getNamedItem("name").getNodeValue();
    }

    private String getValue(final Node node) {
        return node.getChildNodes().item(0).getNodeValue();
    }

    private boolean isCData(final Node node) {
        return node.getChildNodes().item(0).getNodeType() == Node.CDATA_SECTION_NODE;
    }

    /*
     * Arrays
     */

    private List<String> getArrayValues(final Node arrayNode) {
        final List<String> strings = new ArrayList<>();

        final NodeList childNodeList = arrayNode.getChildNodes();
        for (int i = 0; i < childNodeList.getLength(); ++i) {
            final Node node = childNodeList.item(i);
            final String nodeType = node.getNodeName();

            if (nodeType.equals("item")) {
                strings.add(node.getChildNodes().item(0).getNodeValue());
            } else if (node.getNodeName().equals("#comment")) {
//                System.out.println("<!-- " + node.getNodeValue() + " -->");
            }
        }
        return strings;
    }

    /*
     * Plurals
     */

    private Map<String, String> getPlurals(final Node pluralNode) {
        final Map<String, String> plurals = new HashMap<>();

        final NodeList childNodeList = pluralNode.getChildNodes();
        for (int i = 0; i < childNodeList.getLength(); ++i) {
            final Node node = childNodeList.item(i);
            final String nodeType = node.getNodeName();

            if (nodeType.equals("item")) {
                final String qtyType = node.getAttributes().getNamedItem("quantity").getNodeValue();
                final String qtyString = node.getChildNodes().item(0).getNodeValue();
                plurals.put(qtyType, qtyString);
            } else if (node.getNodeName().equals("#comment")) {
//                System.out.println("<!-- " + node.getNodeValue() + " -->");
            }
        }
        return plurals;
    }
}
