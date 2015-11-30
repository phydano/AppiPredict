package nz.co.ipredict.phydano.appipredict;

import com.google.gson.JsonArray;

/**
 * Created by phydano on 24/11/2015.
 */
public class ContractInfo {

    private String stockName;
    private String title;
    private String name;
    private String price;
    private String lastTradePrice;
    private String todaysChange;
    private String todaysVolume;
    private String averageDailyVol;
    private String status;
    private String startDate;
    private String endDate;
    private String lastTradeTime;
    private String shortDescription;
    private String longDescription;
    private String judgeStatement;
    private JsonArray buyOrders;
    private JsonArray sellOrders;

    public ContractInfo(String stockName, String title, String name, String price, String lastTradePrice, String todaysChange,
                        String todaysVolume, String averageDailyVol, String status,
                        String startDate, String endDate, String lastTradeTime,
                        String shortDescription, String longDescription,
                        String judgeStatement, JsonArray buyOrders, JsonArray sellOrders){
        this.stockName = stockName;
        this.title = title;
        this.name = name;
        this.price = price;
        this.lastTradePrice = lastTradePrice;
        this.todaysChange = todaysChange;
        this.todaysVolume = todaysVolume;
        this.averageDailyVol = averageDailyVol;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lastTradeTime = lastTradeTime;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.judgeStatement = judgeStatement;
        this.buyOrders = buyOrders;
        this.sellOrders = sellOrders;
    }

    public String getStockName(){ return this.stockName;}
    public String getTitle(){ return this.title;}
    public String getName(){ return this.name;}
    public String getPrice() { return this.price;}
    public String getLastTradePrice(){ return this.lastTradePrice;}
    public String getTodaysChange(){ return this.todaysChange;}
    public String getTodaysVolume(){ return this.todaysVolume;}
    public String getAverageDailyVol(){ return this.averageDailyVol;}
    public String getStatus(){ return this.status;}
    public String getStartDate(){ return this.startDate;}
    public String getEndDate(){ return this.endDate;}
    public String getLastTradeTime(){ return this.lastTradeTime;}
    public String getShortDescription(){ return this.shortDescription;}
    public String getLongDescription(){ return this.longDescription;}
    public String getJudgeStatement(){ return this.judgeStatement;}
    public JsonArray getBuyOrders(){ return this.buyOrders;}
    public JsonArray getSellOrders(){ return this.sellOrders;}
}
