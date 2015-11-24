package nz.co.ipredict.phydano.appipredict;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;


public class searchPrediction extends AppCompatActivity {

    private ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_prediction);

        list = getIntent().getStringArrayListExtra("selectedContract");
        TextView t = (TextView) findViewById(R.id.testText);
        System.out.println("TAG: " + list.size());
        t.setText(list.get(0));
        loadBundle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_prediction, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
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
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Show the result stock price depends on what the user clicked on the browse prediction page
     **/
    public void resultStockPrice(){
        XmlReader reader = new XmlReader();
        ArrayList<String> myList = reader.readCSVFile();

        // Loop through to filter each stock name and store them separately
        for(String e : myList){
            filter(e);
        }
    }

    /**
     * Filter by storing each stock name into their correspondence list
     * @param stockName the name of the stock
     * */
    public void filter(String stockName){

        // Condition 1 no need to filter anything [All Contracts]

        // Condition 2 filter for the Featured Contracts [What are the features contract?]

        // Condition 3 filter for the NZ Foreign Affairs

        // Condition 4 filter for the NZ Politics

        // Condition 5 filter for the NZ Economics

        // Condition 6 filter for NZ Election 2017

        // Condition 7 filter for the NZ Vote Share 2017

        // Condition 8 filter for the International Politics

        // Condition 9 filter fot NZ Pay gaps

        // Condition 10 filter for NZ Misc Issues

        // Condition 11 filter for Pay-the-Searcher

        // Condition 12 filter for the Commodities

        // Condition 13 filter for Financial Markets

        // Condition 14 filter for US Politics

        // Condition 15 filter for US President 2016

        // Condition 16 filter for US Economics

        // Condition 17 filter for AUS Economics

        // Condition 18 filter for AUS politics

        // Condition 19 filter for Argentina Election

        // Condition 20 filter for European Elections

        // Condition 21 filter for British Elections

        // Condition 22 filter for British Politics

        // Condition 23 filter for Eurozone Crisis

        // Condition 24 filter for Science & Tech

        // Condition 25 filter for North Korea

        // Condition 26 filter for Student Issues

        // Condition 27 filter for NZ Long-term Econ

        // Condition 28 filter for NZ Fonterra

        /** Conditions 29-32 required to get each of the stock information and compare them
         * to get the results we want
         * */

        // Condition 29 filter by Trades

        // Condition 30 filter by Movement

        // Condition 31 filter by New (How do you classify new as new?)

        // Condition 32 filter by Close Date

    }

    /**
     * Call the bundle method from the XMLReader to grab the info from the JSON file.
     * */
    public void loadBundle(){
        XmlReader reader = new XmlReader();
        for(int i =0; i<list.size(); i++) reader.bundle(list.get(i));
        System.out.println("TAG : Check the wanted bundle " + reader.getWantedBundle().size());
    }
}
