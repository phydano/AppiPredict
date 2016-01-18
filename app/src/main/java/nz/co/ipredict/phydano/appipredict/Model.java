package nz.co.ipredict.phydano.appipredict;

/**
 * Created by phydano on 17/11/2015.
 * This is a model class that is use for the listview where there is text and checkbox
 */
public class Model {
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
     * Get the value of the checkbox, whether it is disable or enable
     * */
    public int getValue(){ return this.value; }

}