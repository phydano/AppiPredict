package nz.co.ipredict.phydano.appipredict;

/**
 * Created by phydano on 6/08/2015.
 * This is a class that creates the stock information object to store information about the stock.
 * Note that the last, bid, and ask are already in the 'CurrentPrice' class.
 */
public class StockInformation {

    //Todo: Will need to turn 'lastTrade', 'dateCreated', and 'dateDue' into date and time format
    private String attr; // The name of the stock
    private String todaysVol; // Today's Volume
    private String todaysChange; //
    private String lastTrade; // Last trade price
    private String status; // Status whether Active or Inactive
    private String dateCreated; // Date that the stock is created
    private String dateDue; // Date that the stock is closed
    private String shortDes; // A short description of the stock
    private String longDes; // A long description of the stock

    /** A constructor creating stock information */
    public StockInformation(String attr, String todaysVol, String todaysChange, String lastTrade,
                            String status, String dateCreated, String dateDue, String shortDes,
                            String longDes){
        this.attr = attr;
        this.todaysVol = todaysVol;
        this.todaysChange = todaysChange;
        this.lastTrade = lastTrade;
        this.status = status;
        this.dateCreated = dateCreated;
        this.dateDue = dateDue;
        this.shortDes = shortDes;
        this.longDes = longDes;
    }

    /** Get the attribute value */
    public String getAttr(){return attr;}

    /** Get Today's volume */
    public String getTodaysVol(){return todaysVol;}

    /** Get Today's change */
    public String getTodaysChange(){return todaysChange;}

    /** Get the last trade */
    public String getLastTrade(){return lastTrade;}

    /** Get the status whether stock is Active or Not */
    public String getStatus(){return status;}

    /** Get the date that the stock is created */
    public String getDateCreated(){return dateCreated;}

    /** Get the date that the stock is closed */
    public String getDateDue(){return dateDue;}

    /** Get a short description of the stock */
    public String getShortDes(){return shortDes;}

    /** Get a long description of the stock */
    public String getLongDes(){return longDes;}

}
