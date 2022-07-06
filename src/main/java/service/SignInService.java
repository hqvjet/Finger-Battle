package service;

import constants.Cipher;
import constants.Role;
import main.Main;
import model.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import serverHost.reponsitories.SignInRepo;
import security.Encryption;
import view.Enter;
import view.SignIn;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SignInService implements ActionListener {

    SignIn signin;
    SignInRepo SIRepo;
    User user;
    Encryption encryption;
    BufferedWriter writer;
    BufferedReader reader;
    public SignInService(SignIn signin){
        this.signin = signin;

        try {
            reader = new BufferedReader(new InputStreamReader(signin.player.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(signin.player.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        SIRepo = new SignInRepo();
        encryption = new Encryption();
    }

    public void reset(){
        signin.userNameTxt.setText("");
        signin.passWordTxt.setText("");
    }

    private void alert(String s){
        JOptionPane.showMessageDialog(null, s);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signin.submit) {
            try {
                user = new User(signin.userNameTxt.getText(), encryption.encrypt( String.valueOf( signin.passWordTxt.getPassword())), Role.PLAYER);
                writer.write(convertToCipher(user.getUsername(), user.getPassword()));
                writer.newLine();
                writer.flush();
                boolean stop = false;

                while(!stop){
                    String getCipher = reader.readLine();
                    if(getCipher.equals(Cipher.cipher_True)){
                        alert("Sign In Successful");
                        if(!checkUser(user.getUsername())){
                            createElementToXMLFile(user.getUsername());
                        }
                        signin.setVisible(false);
                        stop = true;
                        Main.accept(user);
                    }
                    else if(getCipher.equals(Cipher.cipher_False)){
                        reset();
                        alert("Wrong input, try again");
                        stop = true;
                    }
                }

            } catch (IOException | TransformerException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == signin.signUp) {
            signin.setVisible(false);
            reset();
            Enter.signup.setVisible(true);
        }
    }

    File url = new File("src/main/java/loginData.xml");
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;
    Document doc;
    {
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(url);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    };
    private void createElementToXMLFile(String username) throws TransformerException {
        Element root = doc.getDocumentElement();
        Element user = doc.createElement("User");
        root.appendChild(user);

        Element un = doc.createElement("username");

        un.appendChild(doc.createTextNode(username));

        user.appendChild(un);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(new File("src/main/java/loginData.xml"));
        transformer.transform(domSource, streamResult);

        DOMSource source = new DOMSource(doc);
    }

    private boolean checkUser(String username) {
        NodeList nodeList = doc.getElementsByTagName("User");
        Node node;
        for(int i=0; i<nodeList.getLength(); i++){
            node = nodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element nElement = (Element) node;
                if(nElement.getElementsByTagName("username").item(0).getTextContent().equals(username)){
                    return true;
                }
            }
        }
        return false;
    }

    private String convertToCipher(String un, String pw) {
        return Cipher.cipher_SIGNIN_OFFER + un + Cipher.cipherKey + pw;
    }
}

