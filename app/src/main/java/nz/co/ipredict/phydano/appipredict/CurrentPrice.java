package nz.co.ipredict.phydano.appipredict;

/**
 * Created by phydano on 30/07/2015.
 * This is a class that creates Current Price Object to store information from the Current Price
 * in the XML file
 */
public class CurrentPrice {

    private String attr; // get the stock name and date
    private String last; // is the last trade price
    private String bid; // is the offer price
    private String ask; //

    /** A constructor taking in the stock name, last, bid and ask */
    public CurrentPrice(String attr, String last, String bid, String ask){
        this.attr = attr;
        this.last = last;
        this.bid = bid;
        this.ask = ask;
    }

    /** Get the attribute value */
    public String getAttr(){
        return this.attr;
    }

    /** Get the last value from the Current Price */
    public String getLast(){
        return this.last;
    }

    /** Get the bid value from the Current Price */
    public String getBid(){
        return this.bid;
    }

    /** Get the ask value from the Current Price */
    public String getAsk(){
        return this.ask;
    }

}
