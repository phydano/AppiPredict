package nz.co.ipredict.phydano.appipredict;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by phydano on 19/01/2016.
 * This class constructs a trader
 */
public class Traders implements Parcelable {

    private String rank; // rank of the trader
    private String traderName; // name of the trader
    private String roi; // the rate in return of the investment
    private String networth; // the trader networth
    private String networthChange; // the change in the networth

    public Traders (String rank, String traderName, String roi, String networth, String networthChange){
        this.rank = rank;
        this.traderName = traderName;
        this.roi = roi;
        this.networth = networth;
        this.networthChange = networthChange;
    }

    public String getRank(){
        return rank;
    }
    public String getTraderName(){
        return traderName;
    }
    public String getRoi(){
        return roi;
    }
    public String getNetworth(){
        return networth;
    }
    public String getNetworthChange(){
        return networthChange;
    }

    /**
     * Parcelling part to store and pass info from one activity to another
     * */
    public Traders(Parcel in){
        this.rank = in.readString();
        this.traderName = in.readString();
        this.roi = in.readString();
        this.networth = in.readString();
        this.networthChange = in.readString();
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
        dest.writeString(this.rank);
        dest.writeString(this.traderName);
        dest.writeString(this.roi);
        dest.writeString(this.networth);
        dest.writeString(this.networthChange);
    }

    /**
     * Interface that must be implemented and provided as a public CREATOR field that generates
     * instances of my Parcelable class from a Parcel
     * */
    public static final Parcelable.Creator<Traders> CREATOR = new Parcelable.Creator<Traders>() {

        /**
         * Create new instance of Parcelable class, instantiated from the given Parcel whose
         * date previously written by writeToParcel()
         * @param in the parcel to read the object data from
         * @return a new instance of the Parcelable class
         * */
        public Traders createFromParcel(Parcel in) {return new Traders(in);}

        /**
         * Create a new array of the Parcelable class.
         * @param size size of the array
         * @return the array of the Parcelable class, with every entry set to null
         * */
        public Traders[] newArray(int size) {return new Traders [size];}
    };
}
