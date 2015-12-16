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

    private Context context; // hold reference to the activity which is using the custom adapter
    private LayoutInflater mInflater; // layout
    private ArrayList<String> listOfHeaders; // this is our header titles (group)
    private HashMap<String, ArrayList<StockItem>> listOfElements; // all the items under header (children)

    public ExpandableListAdapter(Context context, ArrayList<String> listOfHeaders, HashMap<String, ArrayList<StockItem>>listOfElements){
        this.context = context;
        this.listOfHeaders = listOfHeaders;
        this.listOfElements = listOfElements;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * Get the element of the stock
     * @param groupPosition what group it belongs
     * @param childPosition the position where the child is
     * @return the stock item object containing name, price and change
     * */
    @Override
    public StockItem getChild(int groupPosition, int childPosition){
        String stockName = listOfElements.get(this.listOfHeaders.get(groupPosition)).get(childPosition).getStockName();
        String price = listOfElements.get(this.listOfHeaders.get(groupPosition)).get(childPosition).getPrice();
        String change = listOfElements.get(this.listOfHeaders.get(groupPosition)).get(childPosition).getChange();
        return new StockItem(stockName, price, change);
    }

    /**
     * Identifying the child
     * @param groupPosition what group it belongs
     * @param childPosition the position where the child is
     * */
    @Override
    public long getChildId(int groupPosition, int childPosition){
        return childPosition;
    }

    /**
     * Return the view of that child
     * @param groupPosition what group it belongs
     * @param childPosition the position where the child is
     * @param convertView reuse old view if possible. Otherwise create new view
     * @param isLastChild whether it is the last child of the group
     * @param parent parent of the view
     * */
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild,
                              View convertView, ViewGroup parent){
        ViewHolder holder;
        StockItem childText = getChild(groupPosition, childPosition);

        // Create new view
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.result_prediction_row, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.stockCategories);
            holder.txtPrice = (TextView) convertView.findViewById(R.id.priceCategories);
            holder.txtChange = (TextView) convertView.findViewById(R.id.changeCategories);
            convertView.setTag(holder);
        }
        // There is existing view
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtName.setText(childText.getStockName());
        holder.txtPrice.setText(childText.getPrice());
        holder.txtChange.setText(childText.getChange());

        return convertView;
    }

    /**
     * Get the number of children based on the specifiy group
     * @param groupPosition the position of the group for which children should be returned
     * @return the number of children that belongs to that group
     * */
    @Override
    public int getChildrenCount(int groupPosition){
        return this.listOfElements.get(this.listOfHeaders.get(groupPosition)).size();
    }

    /**
     * Get the data that is associated with that group
     * @param groupPosition the position of the group
     * @return the data child for the specified group
     * */
    @Override
    public Object getGroup(int groupPosition){
        return this.listOfHeaders.get(groupPosition);
    }

    /**
     * Gets the total number of groups
     * @return the number of groups
     * */
    @Override
    public int getGroupCount(){
        return this.listOfHeaders.size();
    }

    /**
     * Gets the ID for thr group at the given position.
     * @param groupPosition the position of the group for which the ID is wanted
     * @return the ID that associated with the group
     * */
    @Override
    public long getGroupId(int groupPosition){
        return groupPosition;
    }

    /**
     * Gets a view that display the given group.
     * @param groupPosition the position of the group of which the View is returned
     * @param isExpanded whether the group is expanded or collapsed
     * @param convertView reuse old view if possible. Otherwise create new view
     * @param parent parent of the view
     * @return the view corresponding to the group of the specified position.
     * */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent){
        String headerTitle = (String) getGroup(groupPosition); // category name
        // Create a new view
        if(convertView == null){
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.grouplist, null);
        }

        // Setup the text view that shows the title (category name)
        TextView listHeader = (TextView) convertView.findViewById(R.id.groupItemLists);
        listHeader.setTypeface(null, Typeface.BOLD);
        listHeader.setText(headerTitle);

        return convertView;
    }

    /**
     * Indicates whether the child and group IDs are stable across changes to the underlying data
     * @return whether or not the same ID, always refer to the same object
     * */
    @Override
    public boolean hasStableIds(){
        return false;
    }

    /**
     * Whether the child at the specified position is selectable
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group
     * */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition){
        return true;
    }

    /**
     * An internal class which holds the view of name, price, and change
     * */
    static class ViewHolder{
        TextView txtName;
        TextView txtPrice;
        TextView txtChange;
    }
}
