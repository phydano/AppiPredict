package nz.co.ipredict.phydano.appipredict;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by phydano on 1/12/2015.
 * This class is for the expandable list view.
 * Sample Code from: http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter{

    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<String> listOfHeaders; // this is our header titles
    private HashMap<String, ArrayList<StockItem>> listOfElements; // all the items under header

    public ExpandableListAdapter(Context context, ArrayList<String> listOfHeaders, HashMap<String, ArrayList<StockItem>>listOfElements){
        this.context = context;
        this.listOfHeaders = listOfHeaders;
        this.listOfElements = listOfElements;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public StockItem getChild(int groupPosition, int childPosition){
        String stockname = listOfElements.get(this.listOfHeaders.get(groupPosition)).get(childPosition).getStockName();
        String price = listOfElements.get(this.listOfHeaders.get(groupPosition)).get(childPosition).getPrice();
        String change = listOfElements.get(this.listOfHeaders.get(groupPosition)).get(childPosition).getChange();
        return new StockItem(stockname, price, change);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition){
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild,
                              View convertView, ViewGroup parent){
        ViewHolder holder;
        StockItem childText = getChild(groupPosition, childPosition);

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
        holder.txtName.setText(childText.getStockName());
        holder.txtPrice.setText(childText.getPrice());
        holder.txtChange.setText(childText.getChange());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition){
        return this.listOfElements.get(this.listOfHeaders.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition){
        return this.listOfHeaders.get(groupPosition);
    }

    @Override
    public int getGroupCount(){
        return this.listOfHeaders.size();
    }

    @Override
    public long getGroupId(int groupPosition){
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent){
        String headerTitle = (String) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.grouplist, null);
        }

        TextView listHeader = (TextView) convertView.findViewById(R.id.groupItemLists);
        listHeader.setTypeface(null, Typeface.BOLD);
        listHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds(){
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition){
        return true;
    }

    static class ViewHolder{
        TextView txtName;
        TextView txtPrice;
        TextView txtChange;
    }

}
