package nz.co.ipredict.phydano.appipredict;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by phydano on 20/01/2016.
 * This allows multiple views to display in the same row (It has the Net Worth info that require
 * to display in the row)
 */
public class NetworthCustomAdapter extends ArrayAdapter<Traders>{

    private Context context; // hold reference to the activity which is using the custom adapter
    ArrayList<Traders> data = new ArrayList<>(); // to hold the array of data items

    /**
     * Constructor helps to initialise the data items and the context
     * */
    public NetworthCustomAdapter(Context context, ArrayList<Traders> data){
        super(context, R.layout.traders_networth, data);
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
            // Get layout Inflator
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.traders_networth, parent, false);
            holder = new AppInfoHolder();

            holder.number = (TextView) row.findViewById(R.id.number);
            holder.tradeName = (TextView) row.findViewById(R.id.tradername);
            holder.change = (TextView) row.findViewById(R.id.change);
            holder.amount = (TextView) row.findViewById(R.id.amount);
            row.setTag(holder);
        }
        else{
            holder = (AppInfoHolder) row.getTag();
        }

        Traders appinfo = data.get(position);
        holder.number.setText(appinfo.getRank());
        holder.tradeName.setText(appinfo.getTraderName());
        holder.change.setText(coloringText(appinfo.getNetworthChange()));
        holder.amount.setText(appinfo.getNetworth());
        return row;
    }

    /**
     * Color the text for the change in the Networth
     * Green for positive change
     * Red for negative change
     * @param text the networth change that needs to check and change the color
     * @return text in which is in color code
     * */
    public Spanned coloringText(String text){
        if(text.contains("-")) return Html.fromHtml("<font color='#D80000'>" + text + "</font>");
        else return Html.fromHtml("<font color='#24A400'>" + text + "</font>");
    }

    /**
     * Inner class that contains the categories name (title) and the checkbox.
     * */
    static class AppInfoHolder{
        TextView number;
        TextView tradeName;
        TextView change;
        TextView amount;
    }
}
