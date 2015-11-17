package nz.co.ipredict.phydano.appipredict;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by phydano on 17/11/2015.
 * Create subclass for array adapter
 */
public class CustomAdapter extends ArrayAdapter<Model>{
    Model[] modelItems = null; // to hold the array of data items
    Context context; // hold reference to the activity which is using the custom adapter

    /**
     * Constructor helps to initialise the data items and the context
     * */
    public CustomAdapter(Context context, Model[] resource){
        super(context,R.layout.row, resource);
        this.context = context;
        this.modelItems = resource;
    }

    /**
     * This method is to build the view item. This method returns a view item which is
     * placed as a row inside the listview
     * */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Get the layout inflator
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        // Inflate the layout with the row (checkbox and textview)
        convertView = inflater.inflate(R.layout.row, parent, false);

        // Get reference of both text view and checkbox
        TextView name = (TextView) convertView.findViewById(R.id.textView1);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);

        name.setText(modelItems[position].getName());
        if(modelItems[position].getValue() == 1){
            cb.setChecked(true);
        }
        else{
            cb.setChecked(false);
        }
        return convertView; // return the view item
    }


}
