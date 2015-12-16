package nz.co.ipredict.phydano.appipredict;

/**
 * Created by phydano on 25/11/2015.
 * This class is to store stock item of the selected categories
 */
public class StockItem {

    private String stockName; // stock name
    private String price; // price of the stock
    private String change; // its changes

    /** Constructor for the stock item */
    public StockItem(String stockName, String price, String change){
        this.stockName = stockName;
        this.price = price;
        this.change = change;
    }

    /** Get the name of the stock */
    public String getStockName(){ return stockName;}
    /** Get the price of the stock */
    public String getPrice(){ return price;}
    /** Get the change of the stock */
    public String getChange(){ return change;}
}
