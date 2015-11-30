package nz.co.ipredict.phydano.appipredict;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;

public class searchPrediction extends AppCompatActivity {

    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<ContractInfo> selectedContract = new ArrayList<ContractInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_prediction);
        list.clear();
        list = getIntent().getStringArrayListExtra("selectedContract");
        System.out.println("TAG: " + list.size());
        loadBundle();
        displayListView();
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
                MyJSONReader.clearArrayList();
                this.finish();
                returnToHome();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void returnToHome(){
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
    }

    @Override
    public void onBackPressed(){
        MyJSONReader.clearArrayList();
        this.finish();
        returnToHome();
    }

    /**
     * Call the bundle method from the XMLReader to grab the info from the JSON file.
     * */
    public void loadBundle() {
        try {
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
        }catch(IOException e){
            e.printStackTrace();
        }
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
}
