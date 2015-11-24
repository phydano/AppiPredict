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
    SparseBooleanArray mCheckStates;

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
     * This method is to build the view item. This method returns a view item which is
     * placed as a row inside the listview
     * */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        AppInfoHolder holder = null;

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

    public boolean isChecked(int position){
        return mCheckStates.get(position, false);
    }

    public void setChecked(int position, boolean isChecked){
        mCheckStates.put(position, isChecked);
    }

    public void toggle(int position){
        setChecked(position, !isChecked(position));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
        mCheckStates.put((Integer) buttonView.getTag(), isChecked);
    }

    static class AppInfoHolder {
        TextView txtTitle;
        CheckBox chkSelect;
    }
}
