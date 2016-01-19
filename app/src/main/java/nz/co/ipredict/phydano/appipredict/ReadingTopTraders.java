package nz.co.ipredict.phydano.appipredict;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by phydano on 19/01/2016.
 * This class read the XML from the web on the top traders.
 */
public class ReadingTopTraders {

    static List<Traders> topTraders = new ArrayList<>(); // list of traders

    /**
     * Read the order book which contains sell and buy of the stock
     * */
    public static void readRankingInfo(String date, String numRow){

        NodeList rankN, traderNameN, roiN, networthN, networthChangeN;
        Element rankE, traderNameE, roiE, networthE, networthChangeE;

        try{
            /** Open the connection to the XML online */
            URL url = new URL("https://www.ipredict.co.nz/ipapi/?action=rankings&numRows="+numRow+"&date="+date+"&type=ROI");
            URLConnection conn = url.openConnection();

            /** Build the document */
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(conn.getInputStream());

            /** Read the XML by the tag name and read each of the items and create an object */
            NodeList rankingNode = doc.getElementsByTagName("rankedTrader");

            /** loop through all the elements */
            for(int i=0; i<rankingNode.getLength(); i++){
                Element element = (Element) rankingNode.item(i);

                rankN = element.getElementsByTagName("rank");
                traderNameN = element.getElementsByTagName("traderName");
                roiN = element.getElementsByTagName("roi");
                networthN = element.getElementsByTagName("networth");
                networthChangeN = element.getElementsByTagName("networthChange");

                rankE = (Element) rankN.item(0);
                traderNameE = (Element) traderNameN.item(0);
                roiE = (Element) roiN.item(0);
                networthE = (Element) networthN.item(0);
                networthChangeE = (Element) networthChangeN.item(0);

                Traders trd = new Traders(rankE.getTextContent(), traderNameE.getTextContent(),
                        roiE.getTextContent(), networthE.getTextContent(), networthChangeE.getTextContent());
                topTraders.add(trd);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Get the list of all traders that is added when reading from the XML on the web.
     * @return the list of traders
     * */
    public List<Traders> getTraders(){
        readRankingInfo(dateTime(), "5");
        return topTraders;
    }

    /**
     * Get the current date and time in order to help retrieve the latest info on the ranking
     * @return the date time formatted as use in the XML web page
     * */
    public static String dateTime(){
        // Grab the year
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR); // the current year
        int month = now.get(Calendar.MONTH) + 1; // the current month + 1 because January = 0
        int day = now.get(Calendar.DAY_OF_MONTH); // the day of the month
        return (String.valueOf(year) + "-" +
                String.valueOf(month) + "-" + String.valueOf(day));
    }

    /** To run the file by itself for testing purpose */
    public static void main(String[] args) throws Exception {
        readRankingInfo(dateTime(), "5");
    }
}