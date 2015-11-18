package nz.co.ipredict.phydano.appipredict;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

public class BrowsePrediction extends AppCompatActivity {

    SearchView search;
    ListView lv;
    Model[] modelItems;
    String[] browseValues = new String[] {"All Contracts", "Featured",
            "NZ Foreign Affairs", "NZ Politics", "NZ Economics", "NZ Election 2017",
            "NZ Vote Share 2017", "International Politics", "NZ Pay Gaps", "NZ Misc Issues",
            "Pay-the-Searcher", "Commodities", "Financial Markets", "US Politics",
            "US President", "AUS Economics", "AUS Politics", "Argentina Election",
            "European Elections", "British Election", "British Politics",
            "Eurozone Crises", "Science & Tech", "North Korea", "Student Issues",
            "NZ Long-Term Econ", "NZ Fonterra"};
    String[] sortByValues = new String[] {"Trades", "Movement", "New", "Close Date"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_prediction);

        searchView();
        buttonIsClicked();
    }

    /**
     * Below is the code for the search view
     * */
    public void searchView(){

        search = (SearchView) findViewById(R.id.searchView);
        search.setQueryHint("Search by keyword");
        search.setIconifiedByDefault(false);
        search.setFocusable(false); // hide the keyboard upon load

        // Text Focus Change Listener
        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            }
        });

        // Search on Query Text Listener
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            // Text user input in the search field when performing the search
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("The input text is: " + query);
                return false;
            }

            // Call when the query text is changed by the user
            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("The new input text is: " + newText);
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_browse_prediction, menu);
        return super.onCreateOptionsMenu(menu);
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
     * Checks to see if button is clicked
     */
    public void buttonIsClicked(){
        final Button browseButton = (Button) findViewById(R.id.browseButton);
        final Button sortByButton = (Button) findViewById(R.id.sortByButton);
        final Button cancelButton = (Button) findViewById(R.id.cancelButton);
        final Button searchButton = (Button) findViewById(R.id.searchButton);

        loadView(browseValues);
        browseButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                loadView(browseValues);
            }
        });
        sortByButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadView(sortByValues);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish(); // return back to the main activity
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            }
        });
    }

    /**
     * ListView of items for the Browse section and Sort By section
     * @param values takes in the array of values
     * */
    public void loadView(String[] values){

        lv = (ListView) findViewById(R.id.listView1);
        modelItems = new Model[values.length];
        for(int i=0; i<values.length; i++){
            modelItems[i] = new Model(values[i],0);
        }
        CustomAdapter adapter = new CustomAdapter(this, modelItems);
        lv.setAdapter(adapter);
    }
}
