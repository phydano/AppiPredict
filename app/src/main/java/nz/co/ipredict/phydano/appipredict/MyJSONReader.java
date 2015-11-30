package nz.co.ipredict.phydano.appipredict;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by phydano on 25/11/2015.
 * This is the class to read the JSON file from web provided by Don
 */
public class MyJSONReader {

    private static ArrayList<ContractInfo> browsePrediction = new ArrayList<ContractInfo>(); // browse predictions
    private static ArrayList<ContractInfo> listOfwantedBundle = new ArrayList<ContractInfo>(); // the bundle we want

    /**
     * Read JSON File from the web given by Don (which update every 5 minutes)
     * At the moment this code open a connection but it should also give a connection timeout if
     * users are not connected to the Internet
     */
    //Todo: to avoid the network connection error to the main activity, it is suggested to use the AsynTask

    public static void JSONReader(String wantedBundle) throws IOException {
        //String sURL = "http://ipredict-test.elasticbeanstalk.com/beta/api/IPredict/cache/ContractResults.ipcache"; //just a string
        String sURL = "http://ipredict-test.elasticbeanstalk.com/beta/ajax/Browse/Categories.php?includeContracts=true";
        // Connect to the URL using java's native library
        URL url = new URL(sURL);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setConnectTimeout(60000); // Allow 1 minute to establish connection
        request.connect();

        // Convert to a JSON object to print data
        JsonParser jp = new JsonParser(); //from gson
        JsonElement root = jp.parse(new InputStreamReader(request.getInputStream())); // all stuff in JSON
        // Export root.toString to text file will shows everything - print in console here only some are shown
        JsonObject allStuffinJson = root.getAsJsonObject(); // all stuff in JSON
        JsonObject categories = allStuffinJson.getAsJsonObject("categories"); // get inside categories

        int count = 0;
        while(count < 1500){
            readJsonObject(categories, Integer.toString(count), wantedBundle);
            count++;
        }
        request.disconnect();
        bundle(wantedBundle);
    }

    /**
     * Read the Categories and store it in the browse prediction array list
     * */
    public static void readJsonObject (JsonObject categories, String num, String wantedBundle){
        if(categories != null){
            JsonObject ObjNumber = categories.getAsJsonObject(num);
            if(ObjNumber != null){
                JsonArray contracts = ObjNumber.getAsJsonArray("contracts");
                String title = ObjNumber.get("desc").getAsString();
                String name = ObjNumber.get("name").getAsString();
                if(contracts != null && name.equals(wantedBundle)){
                    for(int i=0; i<contracts.size(); i++){
                        JsonObject e = contracts.get(i).getAsJsonObject();
                        if(e != null){
                            /** Contract Info */
                            String stockName = e.get("symbol").toString(); // stock Name
                            String lastTradePrice = e.getAsJsonObject("tradingData").get("lastTradePrice").toString(); // last Trade price
                            String todayChange = e.getAsJsonObject("tradingData").get("todaysChange").toString();
                            String todayVolume = e.getAsJsonObject("tradingData").get("todaysVolume").toString();
                            String averageDailyVol = e.getAsJsonObject("tradingData").get("averageDailyVolume").toString();
                            String status = e.get("status").toString();
                            String startDate = e.get("dateCreated").toString();
                            String endDate = e.get("dateDue").toString();
                            String lastTradeTime = e.getAsJsonObject("tradingData").get("lastTradeTime").toString();
                            String shortDescription = e.get("shortDescription").toString();
                            String longDescription = e.get("longDescription").toString();
                            String judgeStatement = e.get("judgeStatement").toString();
                            String price = e.get("price").toString();

                            /** Buy order and Sell order */
                            JsonArray buyOrders = e.getAsJsonObject("book").getAsJsonArray("buyOrders");
                            JsonArray sellOrders = e.getAsJsonObject("book").getAsJsonArray("sellOrders");

                            if(status.equals("2")){ status = "active";}

                            ContractInfo contractInfo = new ContractInfo(stockName, title, name, price,
                                    lastTradePrice, todayChange, todayVolume, averageDailyVol, status,
                                    startDate, endDate, lastTradeTime, shortDescription, longDescription,
                                    judgeStatement, buyOrders, sellOrders);
                            browsePrediction.add(contractInfo);
                        }
                    }
                }
                //  System.out.println("TAG: " + browsePrediction.size() + "---" + num); // test
            }
        }
    }

    /**
     * Store the bundle contracts in the array list so that we can use it in search prediction page
     * @param wantedBundle give the string of the stock name and we can grab its info from the list
     * */
    public static void bundle(String wantedBundle){
        for(int i=0; i<browsePrediction.size();i++){
            if(browsePrediction.get(i).getName().equals(wantedBundle)){ // grab list of specific contract
                listOfwantedBundle.add(browsePrediction.get(i));
            }
        }
    }

    /** Get the list of bundle that wa want */
    public static ArrayList<ContractInfo> getWantedBundle (){
        return listOfwantedBundle;
    }

    /** Must clear the list after return back to the browse prediction page */
    public static void clearArrayList(){
        browsePrediction.clear();
        listOfwantedBundle.clear();
    }
}