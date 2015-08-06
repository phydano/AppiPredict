package nz.co.ipredict.phydano.appipredict;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by phydano on 30/07/2015.
 * This is a class to read the XML
 */
public class XmlReader {

    ArrayList<CurrentPrice> allCurrentPrice = new ArrayList<CurrentPrice>();
    ArrayList<StockInformation> stockInfo = new ArrayList<StockInformation>();

    public XmlReader(){} /** No arguments in the constructor */

    /** Read the XML file from the website on the Current Price
     * Code is from:
     * http://stackoverflow.com/questions/8897459/parsing-xml-from-website-to-an-android-device
     * */
    public void readCurrentPrice(String info){

        NodeList doubleLast, doubleBid, doubleAsk;
        Element valueLast, valueBid, valueAsk;
        String stringClaim;

        try{
            /** Open the connection to the XML online */
            URL url = new URL("https://www.ipredict.co.nz/app.php?do=api&action=prices&stock=" + info);
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
                allCurrentPrice.add(p); // add into the array list
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /** Read the XML file from the website on the Current Price
     * Code is from:
     * http://stackoverflow.com/questions/8897459/parsing-xml-from-website-to-an-android-device
     * */
    public void readStockInfo(String info){

        NodeList stringTodayVol, stringTodayChange, stringLastTrade, stringStatus,
                stringDateCreated, stringDateDue, stringShortDes, stringLongDes, stringJudgeSta;
        Element todayVol, todayChange, lastTrade, status, dateCreated, dateDue, shortDes,
                longDes, judgeStatement;
        String stringClaim;

        try{
            /** Open the connection to the XML online */
            URL url = new URL("https://www.ipredict.co.nz/app.php?do=api&action=stock&results=extended&stock=" + info);
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
                stringTodayVol = element.getElementsByTagName("todaysVol");
                stringTodayChange = element.getElementsByTagName("todaysChange");
                stringLastTrade = element.getElementsByTagName("lastTrade");
                stringStatus = element.getElementsByTagName("status");
                stringDateCreated = element.getElementsByTagName("dateCreated");
                stringDateDue = element.getElementsByTagName("dateDue");
                stringShortDes = element.getElementsByTagName("shortDesc");
                stringLongDes = element.getElementsByTagName("longDesc");
                stringJudgeSta = element.getElementsByTagName("judgeStatement");

                todayVol = (Element) stringTodayVol.item(0);
                todayChange = (Element) stringTodayChange.item(0);
                lastTrade = (Element) stringLastTrade.item(0);
                status = (Element) stringStatus.item(0);
                dateCreated = (Element) stringDateCreated.item(0);
                dateDue = (Element) stringDateDue.item(0);
                shortDes = (Element) stringShortDes.item(0);
                longDes = (Element) stringLongDes.item(0);
                judgeStatement = (Element) stringJudgeSta.item(0);

                /** Turn the String of the date and time to a proper format before display
                 * The API given time is in 00:00:00 so I ignored the time for now
                 * But the date is correct */
                SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
                SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
                Date d = input.parse(dateCreated.getTextContent());
                String dateCreatedreformat = output.format(d);
                d = input.parse(dateDue.getTextContent());
                String dateDuereformat = output.format(d);
                d = input.parse(lastTrade.getTextContent());
                String lastTradereformat = output.format(d);

                StockInformation p = new StockInformation(stringClaim, todayVol.getTextContent(),
                        todayChange.getTextContent(), lastTradereformat, status.getTextContent(),
                        dateCreatedreformat, dateDuereformat, shortDes.getTextContent(),
                        longDes.getTextContent(), judgeStatement.getTextContent());
                stockInfo.add(p); // add into the array list
                //  System.out.println(dateDuereformat);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /** Read the XML by providing the stock information */
    public void print(){
        readCurrentPrice("OCR.10DEC15.D25");
        readCurrentPrice("OCR.10DEC15.D50");
        readStockInfo("OCR.10DEC15.D25");
        readStockInfo("OCR.10DEC15.D50");
        /** Below is just print statement to test the code */
        System.out.println("The Current Price size is " + allCurrentPrice.size());
        System.out.println("The Stock info size is " + stockInfo.size());

        /** Loop through all the current price items in the array list and print */
        for(CurrentPrice p: allCurrentPrice) {
            //CurrentPrice p = allCurrentPrice.get(0);
            System.out.println("The attribute is " + p.getAttr());
            System.out.println("The last is " + p.getLast());
            System.out.println("The bid is " + p.getBid());
            System.out.println("The ask is " + p.getAsk() + "\n");
        }

        /** Loop through all the stock information in the array list and print */
        for(StockInformation s: stockInfo){
            System.out.println("The attribute is " + s.getAttr());
            System.out.println("The volume is " + s.getTodaysVol());
            System.out.println("The Change is " + s.getTodaysChange());
            System.out.println("The last trade is " + s.getLastTrade());
            System.out.println("The status is " + s.getStatus());
            System.out.println("The date created is " + s.getDateCreated());
            System.out.println("The date closed is " + s.getDateDue());
            System.out.println("Short Description: " + s.getShortDes());
            System.out.println("Long Description: " + s.getLongDes());
            System.out.println("Judge Statment: " + s.getJudgeStatement() + "\n");
        }


    }

    /** To run the file by itself for testing purpose */
    public static void main(String[] args) {
        XmlReader reader = new XmlReader();
        reader.print();
    }

    //Todo: Note that this Xml reader has not yet deal with null values.


}