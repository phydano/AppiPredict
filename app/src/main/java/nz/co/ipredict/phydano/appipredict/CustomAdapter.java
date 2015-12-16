package nz.co.ipredict.phydano.appipredict;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * Created by phydano on 17/11/2015.
 * Create subclass for array adapter
 * Code tutorial: http://stackoverflow.com/questions/18162931/get-selected-item-using-checkbox-in-listview
 */
public class CustomAdapter extends ArrayAdapter<Model> implements CompoundButton.OnCheckedChangeListener{
    Model[] data = null; // to hold the array of data items
    Context context; // hold reference to the activity which is using the custom adapter
    SparseBooleanArray mCheckStates; // states of the checkbox

    /**
     * Constructor helps to initialise the data items and the context
     * */
    public CustomAdapter(Context context, Model[] data){
        super(context,R.layout.row, data);
        this.context = context;
        this.data = data;
        mCheckStates = new SparseBooleanArray(data.length);
    }

    /**
     * Get a view that displays the data at the specified position in the data set.
     * @param position the position of the item
     * @param convertView Create a new view if no view can reuse. Otherwise reuse.
     * @param parent The parent of which this view will attached to.
     * @return This method returns a view item which is placed as a row inside the list view.
     * */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        AppInfoHolder holder;

        if(row==null){
            // Get the layout inflator
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.row, parent, false);
            holder = new AppInfoHolder();

            holder.txtTitle = (TextView) row.findViewById(R.id.textView1);
            holder.chkSelect = (CheckBox) row.findViewById(R.id.checkBox1);
            row.setTag(holder);
        }
        else{
            holder = (AppInfoHolder)row.getTag();
        }

        Model appinfo = data[position];
        holder.txtTitle.setText(appinfo.getName());
        holder.chkSelect.setTag(position);
        holder.chkSelect.setChecked(mCheckStates.get(position, false));
        holder.chkSelect.setOnCheckedChangeListener(this);
        return row;
    }

    /**
     * Find out whether the box is checked or not
     * @param position where we want to find out
     * @return the state of the box
     * */
    public boolean isChecked(int position){
        return mCheckStates.get(position, false);
    }

    /**
     * Set the state of the position we want to checked state
     * @param position the position we want to change
     * @param isChecked the toggle state
     * */
    public void setChecked(int position, boolean isChecked){
        mCheckStates.put(position, isChecked);
    }

    /**
     * Toggle the button checked state
     * @param position where we want to toggle
     * */
    public void toggle(int position){
        setChecked(position, !isChecked(position));
    }

    /**
     * Change the state of the button
     * @param buttonView the button of two states - checked or unchecked
     * @param isChecked the state of the buttons
     * */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
        mCheckStates.put((Integer) buttonView.getTag(), isChecked);
    }

    /**
     * Inner class that contains the categories name (title) and the checkbox.
     * */
    static class AppInfoHolder {
        TextView txtTitle;
        CheckBox chkSelect;
    }
}
