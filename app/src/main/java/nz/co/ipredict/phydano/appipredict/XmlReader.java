package nz.co.ipredict.phydano.appipredict;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by phydano on 30/07/2015.
 * This is a class to read the XML
 */
public class XmlReader {

    ArrayList<CurrentPrice> allCurrentPrice = new ArrayList<CurrentPrice>();

    public XmlReader(){} /** No arguments in the constructor */

    /** Read the XML file from the website on the Current Price
     * Code is from:
     * http://stackoverflow.com/questions/8897459/parsing-xml-from-website-to-an-android-device
     * */
    public void readCurrentPrice(){

        NodeList doubleLast, doubleBid, doubleAsk;
        Element valueLast, valueBid, valueAsk;
        String stringClaim;

        try{
            /** Open the connection to the XML online */
            URL url = new URL("https://www.ipredict.co.nz/app.php?do=api&action=prices&stock=91.JUL15.HI");
            URLConnection conn = url.openConnection();

            /** Build the document */
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(conn.getInputStream());

            /** Read the XML by the tag name and read each of the items and create an object */
            NodeList nodes = doc.getElementsByTagName("claim");
            for(int i=0; i<nodes.getLength(); i++){
                Element element = (Element) nodes.item(i);

                stringClaim = element.getAttribute("stock");
                doubleLast = element.getElementsByTagName("last");
                doubleBid = element.getElementsByTagName("bid");
                doubleAsk = element.getElementsByTagName("ask");

                valueLast = (Element) doubleLast.item(0);
                valueBid = (Element) doubleBid.item(0);
                valueAsk = (Element) doubleAsk.item(0);

                CurrentPrice p = new CurrentPrice(stringClaim, valueLast.getTextContent(),
                        valueBid.getTextContent(), valueAsk.getTextContent());
                allCurrentPrice.add(p);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /** For testing to see if the values obtain correctly from the XML */
    public void print(){
        readCurrentPrice();
        CurrentPrice p = allCurrentPrice.get(0);
        System.out.println("The attribute is " + p.getAttr());
        System.out.println("The last is " + p.getLast());
        System.out.println("The bid is " + p.getBid());
        System.out.println("The ask is " + p.getAsk());
    }

    /** To run the file by itself for testing purpose */
    public static void main(String[] args) {
        XmlReader reader = new XmlReader();
        reader.print();
    }

    //Todo: Note that this Xml reader has not yet deal with null values.


}