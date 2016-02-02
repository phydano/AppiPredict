package nz.co.ipredict.phydano.appipredict;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by phydano on 20/01/2016.
 * This allows multiple views to display in the same row (It has the ROI info that require
 * to display in the row)
 */
public class ROICustomAdapter extends ArrayAdapter<Traders>{

    private Context context; // hold reference to the activity which is using the custom adapter
    ArrayList<Traders> data = new ArrayList<>(); // to hold the array of data items

    /**
     * Constructor helps to initialise the data items and the context
     * */
    public ROICustomAdapter(Context context, ArrayList<Traders> data){
        super(context, R.layout.traders_roi, data);
        this.context = context;
        this.data = data;
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
            row = inflater.inflate(R.layout.traders_roi, parent, false);
            holder = new AppInfoHolder();

            holder.number = (TextView) row.findViewById(R.id.number);
            holder.tradeName = (TextView) row.findViewById(R.id.tradername);
            holder.amount = (TextView) row.findViewById(R.id.amount);
            row.setTag(holder);
        }
        else{
            holder = (AppInfoHolder) row.getTag();
        }

        Traders appinfo = data.get(position);
        holder.number.setText(appinfo.getRank());
        holder.tradeName.setText(appinfo.getTraderName());
        holder.amount.setText(appinfo.getRoi());
        return row;
    }

    /**
     * Inner class that contains the categories name (title) and the checkbox.
     * */
    static class AppInfoHolder {
        TextView number;
        TextView tradeName;
        TextView amount;
    }
}
