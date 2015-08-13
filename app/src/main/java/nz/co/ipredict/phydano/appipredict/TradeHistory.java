package nz.co.ipredict.phydano.appipredict;

/**
 * Created by phydano on 13/08/2015.
 */
public class TradeHistory {

    private String attr; // get the stock name and date
    private String currentTime; // stock current time
    private String startTime; // stock start time
    private String endTime; // stock end time
    private String price; // price of the stock
    private String time; // time on that particular stock trade
    private String date; // date on that particular stock trade
    private String quantity; // amount of stock

    /** First constructor is the stock history on its start and end time */
    public TradeHistory(String currentTime, String startTime, String endTime){
        this.currentTime = currentTime;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /** Second constructor is for each trade in that stock */
    public TradeHistory(String attr, String price, String time, String date, String quantity){
        this.attr = attr;
        this.price = price;
        this.time = time;
        this.date = date;
        this.quantity = quantity;
    }

    /** Get the attribute value */
    public String getAttr() {return attr;}

    /** Get the current time */
    public String getCurrentTime() {return currentTime;}

    /** Get the start time */
    public String getStartTime() {return startTime;}

    /**Get the end time */
    public String getEndTime() {return endTime;}

    /** Get the price */
    public String getPrice() {return price;}

    /** Get the time */
    public String getTime() {return time;}

    /** Get the date */
    public String getDate() {return date;}

    /** Get the quantity */
    public String getQuantity() {return quantity;}
}
