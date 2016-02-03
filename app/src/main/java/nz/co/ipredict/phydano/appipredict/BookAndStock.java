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
    private String quantity; // the amount of stocks
    private String price; // stock price

    /** Constructor to constructs the book and stock object */
    public BookAndStock (String orderType, String quantity, String price){
        this.orderType = orderType;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Get the quantity of the stocks either buy or sell
     * @return the quantity of the stock
     * */
    public String getQuantity() {return quantity;}

    /**
     * Get the price of the stock
     * @return the price of the stock
     * */
    public String getPrice() {return price;}

    /**
     * Parcelling part to store and pass info from one activity to another
     * */
    public BookAndStock(Parcel in){
        this.orderType = in.readString();
        this.quantity = in.readString();
        this.price = in.readString();
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
        dest.writeString(this.orderType);
        dest.writeString(this.quantity);
        dest.writeString(this.price);
    }

    /**
     * Interface that must be implemented and provided as a public CREATOR field that generates
     * instances of my Parcelable class from a Parcel
     * */
    public static final Parcelable.Creator<BookAndStock> CREATOR = new Parcelable.Creator<BookAndStock>() {

        /**
         * Create new instance of Parcelable class, instantiated from the given Parcel whose
         * date previously written by writeToParcel()
         * @param in the parcel to read the object data from
         * @return a new instance of the Parcelable class
         * */
        public BookAndStock createFromParcel(Parcel in) {return new BookAndStock(in);}

        /**
         * Create a new array of the Parcelable class.
         * @param size size of the array
         * @return the array of the Parcelable class, with every entry set to null
         * */
        public BookAndStock[] newArray(int size) {return new BookAndStock[size];}
    };
}
