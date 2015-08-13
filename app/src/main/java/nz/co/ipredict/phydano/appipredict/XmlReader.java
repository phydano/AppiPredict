package nz.co.ipredict.phydano.appipredict;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by phydano on 30/07/2015.
 * This is a class to read the XML
 */
public class XmlReader {

    ArrayList<CurrentPrice> allCurrentPrice = new ArrayList<CurrentPrice>();
    ArrayList<StockInformation> stockInfo = new ArrayList<StockInformation>();
    ArrayList<TradeHistory> tradeHistory = new ArrayList<TradeHistory>();
    ArrayList<TradeHistory> stockHistory = new ArrayList<TradeHistory>();

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

        NodeList  doubleLast, doubleBid, doubleAsk, stringTodayVol, stringTodayChange, stringLastTrade,
                stringStatus, stringDateCreated, stringDateDue, stringShortDes, stringLongDes, stringJudgeSta;
        Element valueLast, valueBid, valueAsk, todayVol, todayChange, lastTrade, status, dateCreated,
                dateDue, shortDes, longDes, judgeStatement;
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
                doubleLast = element.getElementsByTagName("last");
                doubleBid = element.getElementsByTagName("bid");
                doubleAsk = element.getElementsByTagName("ask");
                stringTodayVol = element.getElementsByTagName("todaysVol");
                stringTodayChange = element.getElementsByTagName("todaysChange");
                stringLastTrade = element.getElementsByTagName("lastTrade");
                stringStatus = element.getElementsByTagName("status");
                stringDateCreated = element.getElementsByTagName("dateCreated");
                stringDateDue = element.getElementsByTagName("dateDue");
                stringShortDes = element.getElementsByTagName("shortDesc");
                stringLongDes = element.getElementsByTagName("longDesc");
                stringJudgeSta = element.getElementsByTagName("judgeStatement");

                valueLast = (Element) doubleLast.item(0);
                valueBid = (Element) doubleBid.item(0);
                valueAsk = (Element) doubleAsk.item(0);
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

                StockInformation p = new StockInformation(stringClaim, valueLast.getTextContent(),
                        valueBid.getTextContent(), valueAsk.getTextContent(), todayVol.getTextContent(),
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

    /** Read the XML file from the website on the Current Price
     * Code is from:
     * http://stackoverflow.com/questions/8897459/parsing-xml-from-website-to-an-android-device
     * */
    public void readTradeHistory(int day, String info){

        NodeList stringCurrentTime, stringStartTime, stringEndTime, stringPrice,
                stringTime, stringQuantity;
        Element currentTime, startTime, endTime, price, time, quantity;
        String stringClaim;

        try{
            /** Open the connection to the XML online */
            URL url = new URL("https://www.ipredict.co.nz/app.php?do=api&action=history&entries=500&from="
                    + day + "+days+ago&skip=0&stock=" + info);
            URLConnection conn = url.openConnection();

            /** Build the document */
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(conn.getInputStream());

            /** Fromat the string into a proper date and time format */
            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat inputOne = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssss");
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm a");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss a");

            /** Read the XML by the tag name and read each of the items and create an object */
            /** Read the trade history */
            NodeList nodes = doc.getElementsByTagName("trade");
            for(int i=0; i<nodes.getLength(); i++){
                Element element = (Element) nodes.item(i);

                stringClaim = element.getAttribute("stock");
                stringPrice = element.getElementsByTagName("price");
                stringTime = element.getElementsByTagName("time");
                stringQuantity = element.getElementsByTagName("quantity");

                price = (Element) stringPrice.item(0);
                time = (Element) stringTime.item(0);
                quantity = (Element) stringQuantity.item(0);

                /** Format date and time for the stock */
                Date dateTime = input.parse(time.getTextContent());
                String dateMod = dateFormat.format(dateTime);
                String timeMod = timeFormat.format(dateTime);

                TradeHistory t = new TradeHistory(stringClaim, price.getTextContent(),
                        timeMod, dateMod, quantity.getTextContent() );
                tradeHistory.add(t); // add into the array list
            }
            /** Read the history of the stock */
            nodes = doc.getElementsByTagName("history");
            for(int i=0; i<nodes.getLength(); i++){
                Element element = (Element) nodes.item(i);

                stringCurrentTime = element.getElementsByTagName("currentTime");
                stringStartTime = element.getElementsByTagName("startTime");
                stringEndTime = element.getElementsByTagName("endTime");

                currentTime = (Element) stringCurrentTime.item(0);
                startTime = (Element) stringStartTime.item(0);
                endTime = (Element) stringEndTime.item(0);

                //Todo: These 3 give wrong time but correct date.
                /** Format for the current time */
                Date dateTime = input.parse(currentTime.getTextContent());
                String currentTimeMod = dateTimeFormat.format(dateTime);

                /** Format for the start time */
                dateTime = inputOne.parse(startTime.getTextContent());
                String startTimeMod = dateTimeFormat.format(dateTime);

                /** Format for end time */
                dateTime = inputOne.parse(endTime.getTextContent());
                String endTimeMod = dateTimeFormat.format(dateTime);

                System.out.println("Unmodified time is " + startTime.getTextContent());
                System.out.println("Modified time is " + startTimeMod);

                TradeHistory t = new TradeHistory(currentTimeMod,
                        startTimeMod, endTimeMod);
                stockHistory.add(t); // add into the array list
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
        readTradeHistory(2, "91.21AUG15.HIGH");
        /** Below is just print statement to test the code */
        System.out.println("The Current Price size is " + allCurrentPrice.size());
        System.out.println("The Stock info size is " + stockInfo.size());
        System.out.println("The Trade History size is " + tradeHistory.size());
        System.out.println("The Stock History size is " + stockHistory.size());

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
            System.out.println("The last is " + s.getLast());
            System.out.println("The bid is " + s.getBid());
            System.out.println("The ask is " + s.getAsk());
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

        /** Loop through all the trade history for a particular stock and print it */
        for(TradeHistory t: tradeHistory){
            System.out.println("The attribute is " + t.getAttr());
            System.out.println("The price is " + t.getPrice());
            System.out.println("The date is " + t.getDate());
            System.out.println("The time is " + t.getTime());
            System.out.println("The quantity is " + t.getQuantity() + "\n");
        }
        for(TradeHistory s: stockHistory){
            System.out.println("The current time is " + s.getCurrentTime());
            System.out.println("The start time is " + s.getStartTime());
            System.out.println("The end time is " + s.getEndTime() + "\n");
        }

        /** Find the maximum stock trade price */
        double max = Double.parseDouble(tradeHistory.get(0).getPrice());
        for (int i = 1; i < tradeHistory.size(); i++) {
            if (Double.parseDouble(tradeHistory.get(i).getPrice()) > max) {
                max = Double.parseDouble(tradeHistory.get(i).getPrice());
            }
        }

        /** Find the minimum stock trade price */
        double min = Double.parseDouble(tradeHistory.get(0).getPrice());
        for (int i = 1; i < tradeHistory.size(); i++) {
            if (Double.parseDouble(tradeHistory.get(i).getPrice()) < min) {
                min = Double.parseDouble(tradeHistory.get(i).getPrice());
            }
        }

        /** Add all the stocks price together */
        double sum = 0;
        DecimalFormat newFormat = new DecimalFormat("#.####");
        for(TradeHistory t : tradeHistory){
            sum += Double.parseDouble(t.getPrice());
        }
        double sumPrice =  Double.valueOf(newFormat.format(sum));

        /** Print statement for testing */
        System.out.println("Number of stocks: " + tradeHistory.size());
        System.out.println("The max price of the stock is: " + max);
        System.out.println("The min price of the stock is: " + min);
        System.out.println("The total price of stock is: " + sumPrice);
    }

    /** To run the file by itself for testing purpose */
    public static void main(String[] args) {
        XmlReader reader = new XmlReader();
        reader.print();
    }

    //Todo: Note that this Xml reader has not yet deal with null values.
    //Todo: Note the short and long description import from XML has the Apostrophe error shown as &#039;
    //Todo: Under the trade history there is no API for the price changes. 


}