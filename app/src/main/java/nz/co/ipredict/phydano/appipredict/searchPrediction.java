package nz.co.ipredict.phydano.appipredict;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;


public class searchPrediction extends AppCompatActivity {

    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<ContractInfo> browsePrediction = new ArrayList<ContractInfo>(); // browse predictions
    private ArrayList<ContractInfo> listOfwantedBundle = new ArrayList<ContractInfo>(); // the bundle we want
    private ListView lv;
    private StockItem[] modelItems;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_prediction);

        list = getIntent().getStringArrayListExtra("selectedContract");
        TextView t = (TextView) findViewById(R.id.stockName);
        System.out.println("TAG: " + list.size());
        t.setText(list.get(0));
        loadBundle();
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
        returnToHome();
    }

    /**
     * Call the bundle method from the XMLReader to grab the info from the JSON file.
     * */
    public void loadBundle() {
        System.out.println("TAG: Start here");
        try {
            for (int i = 0; i < list.size(); i++) {
                System.out.println("TAG: What in the list is: " + list.get(i));
                MyJSONReader.JSONReader(list.get(i));
                System.out.println("TAG : Check the wanted bundle " + MyJSONReader.getWantedBundle().size());
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
