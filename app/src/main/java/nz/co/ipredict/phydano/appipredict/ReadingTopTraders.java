package nz.co.ipredict.phydano.appipredict;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by phydano on 19/01/2016.
 * This class read the XML from the web on the top traders - ROI and Networth.
 */
public class ReadingTopTraders {

    private static List<Traders> topTradersRoi = new ArrayList<>(); // list the top roi traders
    private static List<Traders> topTradersNetworth = new ArrayList<>(); // list the top networth traders

    /**
     * Read the ranking information from the web URL which is in XML form
     * @param date is the current day (E.g. 2016-01-18)
     * @param numRow is the number of row we want to show the users
     * @param type is either the Roi or networth
     * */
    public static void readRankingInfo(String date, String numRow, String type) {

        NodeList rankN, traderNameN, roiN, networthN, networthChangeN;
        Element rankE, traderNameE, roiE, networthE, networthChangeE;
        HttpURLConnection conn = null;

        try{
            /** Open the connection to the XML online */
            URL url = new URL("https://www.ipredict.co.nz/ipapi/?action=rankings&numRows="+numRow+"&date="+date+"&type=" + type);
            conn = (HttpURLConnection) url.openConnection();

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
                        amount(changeDecimal(roiE.getTextContent())),
                        formatString(changeDecimal(networthE.getTextContent())),
                        formatString(changeDecimal(networthChangeE.getTextContent())));
                if(type.equals("roi")) topTradersRoi.add(trd);
                else if(type.equals("networth")) topTradersNetworth.add(trd);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null) conn.disconnect();
        }
    }

    /**
     * Format to add the '$' sign to the Networth Change and Networth
     * @param temp the string we need to replace
     * @return the formatted string
     * */
    public static String formatString(String temp){
        // If there is a minus in there replace it with -$, otherwise just return back $
        if(temp.contains("-")){
            return temp.replace("-", "-$");
        }
        return ("$"+temp);
    }

    /**
     * Get the list of all traders that is added when reading from the XML on the web.
     * The current max traders to display is 500, can increase / decrease the max here
     * @param type the type of whether it is ROI or NETWORTH
     * @return the list of traders
     * */
    public static List<Traders> getTraders(String type){
        // first of all check whether the Roi list is empty or not
        if(topTradersRoi.isEmpty() && type.equals("roi"))
            readRankingInfo(dateTime(), "600", type); // read 500 traders ranking
        // Second check if the networth list is empty or not
        else if (topTradersNetworth.isEmpty() && type.equals("networth"))
            readRankingInfo(dateTime(), "600", type); // read 500 traders ranking
        // If none of the lists are empty then grab info and then return the list
        if(type.equals("roi")) return topTradersRoi;
        else return topTradersNetworth;
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

    /**
     * Limits the decimal to 4 decimal place.
     * @param convert the number we want to convert
     * @return the new string with shorter decimal place
     * */
    public static String changeDecimal(String convert){
        return Double.toString(Double.valueOf(
                new DecimalFormat("#.##").format(Double.parseDouble(convert))));
    }

    /**
     * A small method adding percentage sign
     * @param text we want to add percentage sign to
     * @return the new text with percentage sign
     * */
    public static String amount(String text){
        return (text + "%");
    }
}