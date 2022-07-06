package service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import view.SignIn;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

public class LoginHint extends JComboBox<String>{
    SignIn si;

    public LoginHint(SignIn si) throws ParserConfigurationException, IOException, SAXException {
        //createDocumentBuilder
        this.si = si;
        File url = new File("src/main/java/loginData.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        //parse xml to Document
        Document doc = builder.parse(url);
        //doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("User");
        Node node;
        addItem("");
        for(int i=0; i<nodeList.getLength(); i++){
            node = nodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element nElement = (Element) node;
                addItem(nElement.getElementsByTagName("username").item(0).getTextContent() );
            }
        }
        //DocumentFactory -> Document -> (Normalize doc) -> NodeList -> Node -> Element
    }

}
