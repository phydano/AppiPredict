package nz.co.ipredict.phydano.appipredict;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by phydano on 30/11/2015.
 */
public class SearchViewCustomAdapter extends BaseAdapter{
    private static ArrayList<StockItem> searchArrayList;
    private LayoutInflater mInflater;

    public SearchViewCustomAdapter(Context context, ArrayList<StockItem> results){
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount(){
        return searchArrayList.size();
    }

    public Object getItem(int position){
        return searchArrayList.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.result_prediction_row, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.stockCategories);
            holder.txtPrice = (TextView) convertView.findViewById(R.id.priceCategories);
            holder.txtChange = (TextView) convertView.findViewById(R.id.changeCategories);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtName.setText(searchArrayList.get(position).getStockName());
        holder.txtPrice.setText(searchArrayList.get(position).getPrice());
        holder.txtChange.setText(searchArrayList.get(position).getChange());
        return convertView;
    }

    static class ViewHolder{
        TextView txtName;
        TextView txtPrice;
        TextView txtChange;
    }
}
