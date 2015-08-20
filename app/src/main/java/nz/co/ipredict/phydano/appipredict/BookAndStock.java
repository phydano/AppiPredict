package nz.co.ipredict.phydano.appipredict;

/**
 * Created by phydano on 20/08/2015.
 * This class defines the BookAndStock object which construct stock that is buy or sell.
 *
 */
public class BookAndStock {

    private String attr; // the name of the stock
    private String orderType; // sell or buy
    private String quantiity; // the amount of stocks
    private String price; // stock price

    /** Constructor to constructs the book and stock object */
    public BookAndStock (String attr, String orderType, String quantity, String price){
        this.attr = attr;
        this.orderType = orderType;
        this.quantiity = quantity;
        this.price = price;
    }

    /** Get the name of the stock */
    public String getAttr() {return attr;}

    /** Get the type of the order whether it is sell or buy */
    public String getOrderType() {return orderType;}

    /** Get the quantity of the stocks either buy or sell */
    public String getQuantiity() {return quantiity;}

    /** Get the price of the stock */
    public String getPrice() {return price;}

}
