package nz.co.ipredict.phydano.appipredict;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by phydano on 20/01/2016.
 */
public class ROICustomAdapter extends ArrayAdapter<Traders>{

    private Context context; // hold reference to the activity which is using the custom adapter
    Traders[] data = null;

    public ROICustomAdapter(Context context, Traders[] data){
        super(context, R.layout.traders_roi, data);
        this.context = context;
        this.data = data;
    }

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

        Traders appinfo = data[position];
        holder.number.setText(appinfo.getRank());
        holder.tradeName.setText(appinfo.getTraderName());
        holder.amount.setText(appinfo.getRoi());
        return row;
    }

    static class AppInfoHolder {
        TextView number;
        TextView tradeName;
        TextView amount;
    }
}
