package nz.co.ipredict.phydano.appipredict;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phydano on 24/11/2015.
 * This class is the contract information where contains most, if not all the information
 * on the stock that I got from the Json file online.
 */
public class ContractInfo implements Parcelable {

    private String stockName; // name of the stock
    private String title; // title of the stock
    private String name; // description name of the stock
    private String price; // current price of the stock
    private String lastTradePrice; // the last trade price of the stock
    private String todaysChange; // any changes in the stock today
    private String todaysVolume; // the volume of today stock
    private String averageDailyVol; // average volume of the stock daily
    private String status; // the status of the stock
    private String startDate; // the start date of when the stock starts
    private String endDate; // the end date where the prediction came true or not
    private String lastTradeTime; // the last trade time of the stock
    private String shortDescription; // a short description describing what the stock is
    private String longDescription; // a slight longer that describes the stock
    private String judgeStatement; // a complete version of the stock information
    private List<BookAndStock> buyOrders; // the top 10 buy orders of the stock
    private List<BookAndStock> sellOrders;  // the top 10 sell orders of the stock

    /**
     * Constructor building the object containg all the info on the stock
     * */
    public ContractInfo(String stockName, String title, String name, String price, String lastTradePrice, String todaysChange,
                        String todaysVolume, String averageDailyVol, String status,
                        String startDate, String endDate, String lastTradeTime,
                        String shortDescription, String longDescription,
                        String judgeStatement, List<BookAndStock> buyOrders, List<BookAndStock> sellOrders){
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

    /**
     * Get stock name
     * @return the name of the stock
     * */
    public String getStockName(){ return this.stockName;}

    /**
     * Get the title of the stock
     * @return the title of the stock
     * */
    public String getTitle(){ return this.title;}

    /**
     * Get the description name of the stock
     * @return the description name of the stock
     * */
    public String getName(){ return this.name;}

    /**
     * Get the price of the stock
     * @return the price of the stock
     * */
    public String getPrice() { return this.price;}

    /**
     * Get the last trade price of the stock
     * @return the last trade price of the stock
     * */
    public String getLastTradePrice(){ return this.lastTradePrice;}

    /**
     * Get the changes in the stock today
     * @return the changes of the stock today
     * */
    public String getTodaysChange(){ return this.todaysChange;}

    /**
     * Get the stock volume for today
     * @return the stock volume today
     * */
    public String getTodaysVolume(){ return this.todaysVolume;}

    /**
     * Average daily volume of the stock
     * @return the average daily volume of the stock
     * */
    public String getAverageDailyVol(){ return this.averageDailyVol;}

    /**
     * The status of the stock
     * @return the status of the current stock
     * */
    public String getStatus(){ return this.status;}

    /**
     * Get the start date of the stock
     * @return the date where the stock first started
     * */
    public String getStartDate(){ return this.startDate;}

    /**
     * The end date of the stock
     * @return the date in which the stock is closed
     * */
    public String getEndDate(){ return this.endDate;}

    /**
     * The last trade time of the stock
     * @return get the last trade time of the stock
     * */
    public String getLastTradeTime(){ return this.lastTradeTime;}

    /**
     * The short description of the stock
     * @return the short version describing the stock
     * */
    public String getShortDescription(){ return this.shortDescription;}

    /**
     * The long description of the stock
     * @return get the long description of the stock
     * */
    public String getLongDescription(){ return this.longDescription;}

    /**
     * The judge statement of the stock
     * @return the full info of the stock
     * */
    public String getJudgeStatement(){ return this.judgeStatement;}

    /**
     * The list of buy orders
     * @return the list of top 10 buy orders of that stock
     * */
    public List<BookAndStock> getBuyOrders(){ return this.buyOrders;}

    /**
     * The list of sell orders
     * @return the list of top 10 sell orders of the stock
     * */
    public List<BookAndStock> getSellOrders(){ return this.sellOrders;}

    /**
     * Parcelling part to store and pass info from one activity to another
     * */
    public ContractInfo(Parcel in){
        this.stockName = in.readString();
        this.title = in.readString();
        this.name = in.readString();
        this.price = in.readString();
        this.lastTradePrice = in.readString();
        this.todaysChange = in.readString();
        this.todaysVolume = in.readString();
        this.averageDailyVol = in.readString();
        this.status = in.readString();
        this.startDate = in.readString();
        this.endDate = in.readString();
        this.lastTradeTime = in.readString();
        this.shortDescription = in.readString();
        this.longDescription = in.readString();
        this.judgeStatement = in.readString();
        this.buyOrders = new ArrayList<BookAndStock>();
        this.sellOrders = new ArrayList<BookAndStock>();
        in.readTypedList(this.buyOrders, BookAndStock.CREATOR);
        in.readTypedList(this.sellOrders, BookAndStock.CREATOR);
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * @return bitmask indicating the set of special object types
     * */
    @Override
    public int describeContents(){
        return 0;
    }

    /**
     * Flatten the object into a parcel
     * @param dest the parcel in which the object should be written to
     * @param flags additional flags about how the object should be written
     * */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.stockName);
        dest.writeString(this.title);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.lastTradePrice);
        dest.writeString(this.todaysChange);
        dest.writeString(this.todaysVolume);
        dest.writeString(this.averageDailyVol);
        dest.writeString(this.status);
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
        dest.writeString(this.lastTradeTime);
        dest.writeString(this.shortDescription);
        dest.writeString(this.longDescription);
        dest.writeString(this.judgeStatement);
        dest.writeTypedList(this.buyOrders);
        dest.writeTypedList(this.sellOrders);
    }

    /**
     * Interface that must be implemented and provided as a public CREATOR field that generates
     * instances of my Parcelable class from a Parcel
     * */
    public static final Parcelable.Creator<ContractInfo> CREATOR = new Parcelable.Creator<ContractInfo>() {

        /**
         * Create new instance of Parcelable class, instantiated from the given Parcel whose
         * date previously written by writeToParcel()
         * @param in the parcel to read the object data from
         * @return a new instance of the Parcelable class
         * */
        public ContractInfo createFromParcel(Parcel in) {return new ContractInfo(in);}

        /**
         * Create a new array of the Parcelable class.
         * @param size size of the array
         * @return the array of the Parcelable class, with every entry set to null
         * */
        public ContractInfo[] newArray(int size) {return new ContractInfo [size];}
    };
}
