package nz.co.ipredict.phydano.appipredict;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by phydano on 17/11/2015.
 * This is a model class that is use for the listview where there is text and checkbox
 */
public class Model implements Parcelable{
    String name; // category name
    int value; // 0 means checkbox is disabled and 1 mean enabled

    Model(String name, int value){
        this.name = name;
        this.value = value;
    }

    /**
     * Get the category name
     * */
    public String getName(){
        return this.name;
    }

    /**
     * Parcelling part to store and pass info from one activity to another
     * */
    public Model(Parcel in){
        this.name = in.readString();
        this.value = in.readInt();
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
        dest.writeString(this.name);
        dest.writeInt(this.value);
    }

    /**
     * Interface that must be implemented and provided as a public CREATOR field that generates
     * instances of my Parcelable class from a Parcel
     * */
    public static final Parcelable.Creator<Model> CREATOR = new Parcelable.Creator<Model>() {

        /**
         * Create new instance of Parcelable class, instantiated from the given Parcel whose
         * date previously written by writeToParcel()
         * @param in the parcel to read the object data from
         * @return a new instance of the Parcelable class
         * */
        public Model createFromParcel(Parcel in) {return new Model(in);}

        /**
         * Create a new array of the Parcelable class.
         * @param size size of the array
         * @return the array of the Parcelable class, with every entry set to null
         * */
        public Model[] newArray(int size) {return new Model [size];}
    };
}
