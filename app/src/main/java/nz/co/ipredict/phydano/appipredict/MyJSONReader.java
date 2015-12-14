package nz.co.ipredict.phydano.appipredict;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by phydano on 25/11/2015.
 * This is the class to read the JSON file from web provided by Don
 */
public class MyJSONReader {

    private static ArrayList<ContractInfo> browsePrediction = new ArrayList<ContractInfo>(); // browse predictions
    private static ArrayList<ContractInfo> listOfwantedBundle = new ArrayList<ContractInfo>(); // the bundle we want
    private static JsonObject categories = null;
    private static ArrayList<String> allCategoriesName = new ArrayList<String>();

    /**
     * Establish a connection to the web server
     * Read JSON File from the web given by Don (which update every 5 minutes)
     */
    public static void EstablishedWebConnection() throws SocketTimeoutException, SocketException{
        JsonElement root;
        try{
            String sURL = "http://ipredict-test.elasticbeanstalk.com/beta/ajax/Browse/Categories.php?includeContracts=true";
            // Connect to the URL using java's native library
            URL url = new URL(sURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setConnectTimeout(60000); //If can't connect, time out in 60 seconds
            request.connect(); // establish connection

            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            root = jp.parse(new InputStreamReader(request.getInputStream())); // all stuff in JSON
            // get object twice because of object inside object
            categories = root.getAsJsonObject().getAsJsonObject("categories"); // get inside categories

            request.disconnect(); // close the connection
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Get the JSON info and go through all subcategories under the category that the user checked
     * @param wantedBundle is the category that the user checked
     */
    public static void JSONReader(String wantedBundle) {
        if(categories != null) {
            // Export root.toString to text file will shows everything - print in console here only some are shown
            // Should loop below 1500 times as the ID range from 0-1500
            int count = 0;
            while (count < 1500) {
                readJsonObject(categories, Integer.toString(count), wantedBundle);
                count++;
            }
            bundle(wantedBundle);
        }
    }

    /**
     * Read the Categories and store it in the browse prediction array list
     * @param categories is the all the json info we have
     * @param num is the ID that ranges from 0-1500
     * @param wantedBundle is the category that user checked
     * */
    public static void readJsonObject (JsonObject categories, String num, String wantedBundle){
        Pattern br = Pattern.compile("\\[br]"); // the newline pattern in HTML
        Pattern apostrophe = Pattern.compile("&#039;"); // the apostrophe in HTML
        Pattern quote = Pattern.compile("&quot;");
        if(categories != null){
            JsonObject ObjNumber = categories.getAsJsonObject(num);
            if(ObjNumber != null){
                JsonArray contracts = ObjNumber.getAsJsonArray("contracts");
                String title = ObjNumber.get("desc").getAsString();
                String name = ObjNumber.get("name").getAsString();
                allCategoriesName.add(name);
                if(contracts != null && name.equals(wantedBundle)){
                    for(int i=0; i<contracts.size(); i++){
                        JsonObject e = contracts.get(i).getAsJsonObject();
                        if(e != null){
                            /** Contract Info */
                            String stockName = e.get("symbol").toString().replace("\"", ""); // stock Name
                            String lastTradePrice = e.getAsJsonObject("tradingData").get("lastTradePrice").toString().replace("\"", ""); // last Trade price
                            String todayChange = e.getAsJsonObject("tradingData").get("todaysChange").toString().replace("\"", "");
                            String todayVolume = e.getAsJsonObject("tradingData").get("todaysVolume").toString().replace("\"", "");
                            String averageDailyVol = e.getAsJsonObject("tradingData").get("averageDailyVolume").toString().replace("\"", "");
                            String status = e.get("status").toString().replace("\"", "");
                            String startDate = e.get("dateCreated").toString().replace("\"", "");
                            String endDate = e.get("dateDue").toString().replace("\"", "");
                            String lastTradeTime = e.getAsJsonObject("tradingData").get("lastTradeTime").toString().replace("\"", "");
                            String shortDescription = e.get("shortDescription").toString().replace("\"", "");
                            String longDescription = e.get("longDescription").toString().replace("\"", "");
                            String judgeStatement = e.get("judgeStatement").toString().replace("\"", "");
                            String price = e.get("price").toString().replace("\"", "");

                            /** Buy order and Sell order */
                            JsonArray buyOrders = e.getAsJsonObject("book").getAsJsonArray("buyOrders");
                            JsonArray sellOrders = e.getAsJsonObject("book").getAsJsonArray("sellOrders");

                            List<BookAndStock> listOfBuyOrders = turnOrdersToList(buyOrders);
                            List<BookAndStock> listOfSellOrders = turnOrdersToList(sellOrders);

                            /** Replace the number string to the corresponding states */
                            String temp = status;
                            status = checkStatus(temp);

                            /** Changes the breaking [br] and apostrophe in a correct format */
                            // match the break [br] and replace with new line
                            Matcher matcher = br.matcher(judgeStatement);
                            String judgeS = matcher.replaceAll("\n");
                            // then replace the apostrophe
                            matcher = apostrophe.matcher(judgeS);
                            String alteredJudegeStatement = matcher.replaceAll("'");
                            // then replace the quote mark
                            matcher = quote.matcher(alteredJudegeStatement);
                            String furtherAlteredJudgeStatement = matcher.replaceAll("\"");
                            // match the break [br] and replace with new line
                            matcher = br.matcher(longDescription);
                            String longDes = matcher.replaceAll("\n");
                            // then replace the apostrophe
                            matcher = apostrophe.matcher(longDes);
                            String alteredLongDes = matcher.replaceAll("'");
                            // match and replace the apostrophe
                            matcher = apostrophe.matcher(shortDescription);
                            String alteredShortDes = matcher.replaceAll("'");

                            /** Now create the object and it*/
                            ContractInfo contractInfo = new ContractInfo(stockName, title, name, price,
                                    lastTradePrice, todayChange, todayVolume, averageDailyVol, status,
                                    startDate, endDate, lastTradeTime, alteredShortDes, alteredLongDes,
                                    furtherAlteredJudgeStatement, listOfBuyOrders, listOfSellOrders);
                            browsePrediction.add(contractInfo);
                        }
                    }
                }
                 // System.out.println("TAG: " + browsePrediction.size() + "---" + num); // test
            }
        }
    }

    public static List<BookAndStock> turnOrdersToList(JsonArray book){
        List<BookAndStock> myTempListOfBuyandSell = new ArrayList<BookAndStock>();
        for(int i=0; i<book.size(); i++){
            JsonObject temp = book.get(i).getAsJsonObject();
            String price = temp.get("price").toString().replace("\"", "");
            String quantity = temp.get("quantity").toString().replace("\"", "");
            String type = temp.get("type").toString().replace("\"", "");
            myTempListOfBuyandSell.add(new BookAndStock(type, quantity, price));
        }
        return myTempListOfBuyandSell;
    }

    /**
     * Return the corresponding status depend on the codes
     * @param status string in which is the code for the status name
     * */
    public static String checkStatus(String status){
        switch(status) {
            case "1":
                status = "pending";
                return status;
            case "2":
                status = "active";
                return status;
            case "3":
                status = "suspended";
                return status;
            case "4":
                status = "closed";
                return status;
            default:
                status = "unknown";
                return status;
        }
    }

    /**
     * Store the bundle contracts in the array list so that we can use it in search prediction page
     * @param wantedBundle give the string of the stock name and we can grab its info from the list
     * */
    public static void bundle(String wantedBundle){
        System.out.println("TAG: Browse Prediction size : " + browsePrediction.size());
        for(int i=0; i<browsePrediction.size();i++){
            System.out.println("TAG: Prediction Name " + browsePrediction.get(i).getName());
            System.out.println("TAG: The bundle we wanted" + wantedBundle);
            if(browsePrediction.get(i).getName().equals(wantedBundle)){ // grab list of specific contract
                listOfwantedBundle.add(browsePrediction.get(i));
            }
        }
    }

    /** Get the list of bundle that wa want */
    public static ArrayList<ContractInfo> getWantedBundle (){
        return listOfwantedBundle;
    }

    public static ArrayList<String> getName(){ return allCategoriesName; }

    public static JsonObject getCategories(){ return categories;}

    /** Must clear the list after return back to the browse prediction page */
    public static void clearJsonFile(){
        categories = null;
    }

    public static void clearList(){
        // Need to clear it to avoid duplicate when search
        listOfwantedBundle.clear();
        browsePrediction.clear();
    }
}
