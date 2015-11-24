package nz.co.ipredict.phydano.appipredict;

/**
 * Created by phydano on 17/11/2015.
 */
public class Model {
    String name;
    int value; // 0 means checkbox is disabled and 1 mean enabled

    Model(String name, int value){
        this.name = name;
        this.value = value;
    }

    public String getName(){
        return this.name;
    }

    public int getValue(){
        return this.value;
    }

    public void setValue(int value){ this.value = value;}
}
