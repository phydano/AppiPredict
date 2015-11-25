package nz.co.ipredict.phydano.appipredict;

/**
 * Created by phydano on 25/11/2015.
 * This class is to store stock item for displaying in the result page of the selected categories
 */
public class StockItem {

    private String stockName;
    private String price;
    private String change;

    public StockItem(String stockName, String price, String change){
        this.stockName = stockName;
        this.price = price;
        this.change = change;
    }

    public String getStockName(){ return stockName;}
    public String getPrice(){ return price;}
    public String getChange(){ return change;}
}
