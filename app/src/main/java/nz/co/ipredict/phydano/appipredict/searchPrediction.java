package nz.co.ipredict.phydano.appipredict;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class searchPrediction extends AppCompatActivity {

    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<ContractInfo> selectedContract = new ArrayList<ContractInfo>();

    /** Test for the Expandable list */
    static ExpandableListAdapter listAdapter;
    static ExpandableListView expListView;
    static ArrayList<String> listDataHeader;
    static HashMap<String, ArrayList<StockItem>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_prediction);
        list = getIntent().getStringArrayListExtra("selectedContract");
        new GetTask().execute();
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
        }
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
                returnToHome();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void returnToHome(){
        this.finish();
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            // This activity is NOT part of this app's task, so create a new task
            // when navigating up, with a synthesized back stack.
            TaskStackBuilder.create(this)
                    // Add all of this activity's parents to the back stack
                    .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                    .startActivities();
        } else {
            // This activity is part of this app's task, so simply
            // navigate up to the logical parent activity.
            NavUtils.navigateUpTo(this, upIntent);
        }
        return;
    }

    @Override
    public void onBackPressed(){
        // MyJSONReader.clearArrayList();
        returnToHome();
    }

/*    @Override
    protected void onStop() {
        super.onStop();
        MyJSONReader.clearArrayList();
    }*/

    /**
     * Call the bundle method from the XMLReader to grab the info from the JSON file.
     * */
    public void loadBundle() {
        MyJSONReader.EstablishedWebConnection();
        for (int i = 0; i < list.size(); i++) {
            MyJSONReader.JSONReader(list.get(i)); // search for the selected checked box
            System.out.println("TAG: Bundled Size " + MyJSONReader.getWantedBundle().size());
        }
        if(MyJSONReader.getWantedBundle().size() > 0) {
            for (ContractInfo e : MyJSONReader.getWantedBundle()) {
                selectedContract.add(e);
            }
        }
        System.out.println("TAG: " + selectedContract.size());
    }

    /**
     * Add items to the listview and display them
     **/
    public void displayListView(){
        ArrayList<StockItem> myList = new ArrayList<StockItem>();
        // Loop through the contracts and just grab any infomation necessary to display
        for(ContractInfo e : selectedContract){
            String stock = e.getStockName().replace("\"", "");
            String price = "$" + e.getPrice().replace("\"", "");
            String change = e.getTodaysChange().replace("\"", "");
            StockItem temp = new StockItem(stock, price, change);
            myList.add(temp);
        }

        final ListView myBundlesList = (ListView) findViewById(R.id.listOfStocks);
        myBundlesList.setAdapter(new SearchViewCustomAdapter(this, myList));
        myBundlesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public void ExpandableListView() {
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, ArrayList<StockItem>>();
        String title = "";

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

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        for(int i=0; i<listAdapter.getGroupCount(); i++){
            expListView.expandGroup(i);
        }
        MyJSONReader.clearJsonFile(); // the Json File is too large, get rid of it after completed the work
    }
}