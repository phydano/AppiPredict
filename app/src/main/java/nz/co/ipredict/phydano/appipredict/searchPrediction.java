package nz.co.ipredict.phydano.appipredict;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by phydano
 * This is the search prediction page which displays the result of the search based on what
 * the users selected in the browse prediction page
 */
public class searchPrediction extends AppCompatActivity {

    private ArrayList<String> list = new ArrayList<String>(); // list of contracts
    private ArrayList<ContractInfo> selectedContract = new ArrayList<ContractInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_prediction);
        MyJSONReader.clearList(); // the Json File is too large, get rid of it after completed the work
        // Activity on the first time
        if(savedInstanceState == null) {
            if (getIntent().getStringArrayListExtra("selectedContract") != null)
                list = getIntent().getStringArrayListExtra("selectedContract");
            new GetTask().execute();
        }
        // Activity reloaded back
        else{
            selectedContract = savedInstanceState.getParcelableArrayList("Test");
            ExpandableListView();
        }
    }

    /**
     * Saved the activity state to restore when screen orientation changes
     * */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("Test", selectedContract);
    }

    /**
     * Restore the activity state back when the screen orientation changes
     * but not when we moved to the other activity and came back....
     * */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        selectedContract = savedInstanceState.getParcelableArrayList("Test");
        ExpandableListView();
    }

    class GetTask extends AsyncTask<String,String,ArrayList<ContractInfo>> {
        ProgressDialog mDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(searchPrediction.this);
            mDialog.setMessage("Please wait...");
            mDialog.setIndeterminate(false);
            mDialog.setCancelable(true);
            mDialog.show();
        }

        @Override
        protected ArrayList<ContractInfo> doInBackground(String... args){
            loadBundle();
            return MyJSONReader.getWantedBundle();
        }

        @Override
        protected void onPostExecute(ArrayList<ContractInfo> info){
            mDialog.dismiss();
            ExpandableListView();
            super.cancel(true); // cancel the Asyntask
        }

    }

    /**
     * This is the alert box tp notify the users
     * @param message the message which we want to display as a pop up
     **/
    public void alertBox(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(searchPrediction.this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        onBackPressed();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_prediction, menu);
        return true;
    }*/

    /**
     * Item in the option menu. There would be varies case depend on the selection
     * The code here allow us to go back to the home page (parent activity)
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //  MyJSONReader.clearArrayList();
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Call the bundle method from the XMLReader to grab the info from the JSON file.
     * */
    public void loadBundle() {
        for (int i = 0; i < list.size(); i++) {
            MyJSONReader.JSONReader(list.get(i)); // search for the selected checked box
        }
        if(MyJSONReader.getWantedBundle().size() > 0) {
            for (ContractInfo e : MyJSONReader.getWantedBundle()) {
                selectedContract.add(e);
            }
        }
    }

    public void ExpandableListView() {
        final ExpandableListView expListView = (ExpandableListView) findViewById(R.id.lvExp);
        final ArrayList<String> listDataHeader = new ArrayList<String>();
        final HashMap<String, ArrayList<StockItem>> listDataChild = new HashMap<String, ArrayList<StockItem>>();
        String title = "";

        if(selectedContract.size() == 0) alertBox("Sorry no results found");
        for(int i=0 ; i<selectedContract.size(); i++){
            // first grab any info on that stock
            String stock = selectedContract.get(i).getStockName().replace("\"", "");
            String price = "Price: $" + selectedContract.get(i).getPrice().replace("\"", "");
            String change = "Change: " + selectedContract.get(i).getTodaysChange().replace("\"", "");
            StockItem temp = new StockItem(stock, price, change);
            ArrayList<StockItem> myTempList = new ArrayList<StockItem>();

            // check if the title is the same or not
            // If the title is not the same then add the stock to the list
            if(!title.equals(selectedContract.get(i).getTitle().replace("\"", ""))) {
                title = selectedContract.get(i).getTitle().replace("\"", "");
                myTempList.add(temp);
                // add it to the map
                listDataHeader.add(title);
                listDataChild.put(title, myTempList);
            }
            // Otherwise if title is already in the map then add the stock to the arraylist of stocks
            else{
                listDataChild.get(title).add(temp);
            }
        }

        final ExpandableListAdapter listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        for(int i=0; i<listAdapter.getGroupCount(); i++){
            expListView.expandGroup(i);
        }
        // Add a click listener so we can grab the info on that selected subcategory
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                StockItem temp = listAdapter.getChild(groupPosition, childPosition);
                goToResultPage(temp.getStockName());
                return false;
            }
        });
    }

    /**
     * This method is for taking the user who click on the subcategories of items that
     * is displayed under the category. Upon selection the info should be pass to the
     * next activity and the user is directed there
     * @param subcategory the name of the child
     * */
    public void goToResultPage(String subcategory){
        ContractInfo subcategoryItem = null;
        for(int i=0; i< selectedContract.size(); i++) {
            String temp = selectedContract.get(i).getStockName().replace("\"", "");;
            if( temp.equals(subcategory)){
                subcategoryItem = selectedContract.get(i);
            }
        }
        if(subcategoryItem != null){
            Intent intent = new Intent(this, ShowResult.class);
            // pass the object to the next activity
            intent.putExtra("myObject", subcategoryItem);
            intent.putParcelableArrayListExtra("savedInstance",selectedContract);
            // call the next activity to run
            startActivity(intent);
            // finish the current activity
            // this.finish(); // not a good idea here, better leave it in the stack
        }
    }
}