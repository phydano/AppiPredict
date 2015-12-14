package nz.co.ipredict.phydano.appipredict;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by phydano on 20/08/2015.
 * This class defines the BookAndStock object which construct stock that is buy or sell.
 *
 */
public class BookAndStock implements Parcelable {

    private String orderType; // sell or buy
    private String quantiity; // the amount of stocks
    private String price; // stock price

    /** Constructor to constructs the book and stock object */
    public BookAndStock (String orderType, String quantity, String price){
        this.orderType = orderType;
        this.quantiity = quantity;
        this.price = price;
    }

    /** Get the type of the order whether it is sell or buy */
    public String getOrderType() {return orderType;}

    /** Get the quantity of the stocks either buy or sell */
    public String getQuantiity() {return quantiity;}

    /** Get the price of the stock */
    public String getPrice() {return price;}

    // Parcelling part
    public BookAndStock(Parcel in){
        this.orderType = in.readString();
        this.quantiity = in.readString();
        this.price = in.readString();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderType);
        dest.writeString(this.quantiity);
        dest.writeString(this.price);
    }

    public static final Parcelable.Creator<BookAndStock> CREATOR = new Parcelable.Creator<BookAndStock>() {
        public BookAndStock createFromParcel(Parcel in) {return new BookAndStock(in);}
        public BookAndStock[] newArray(int size) {return new BookAndStock[size];}
    };

}
