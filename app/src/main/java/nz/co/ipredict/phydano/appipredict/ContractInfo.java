package nz.co.ipredict.phydano.appipredict;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phydano on 24/11/2015.
 */
public class ContractInfo implements Parcelable {

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
    private List<BookAndStock> buyOrders;
    private List<BookAndStock> sellOrders;

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
    public List<BookAndStock> getBuyOrders(){ return this.buyOrders;}
    public List<BookAndStock> getSellOrders(){ return this.sellOrders;}

    // Parcelling part
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

    @Override
    public int describeContents(){
        return 0;
    }

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

    public static final Parcelable.Creator<ContractInfo> CREATOR = new Parcelable.Creator<ContractInfo>() {
        public ContractInfo createFromParcel(Parcel in) {return new ContractInfo(in);}
        public ContractInfo[] newArray(int size) {return new ContractInfo [size];}
    };
}
