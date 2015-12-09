package nz.co.ipredict.phydano.appipredict;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class BrowsePrediction extends AppCompatActivity {

    private SearchView search; // the search field
    private ListView lv; // the list of items to display under the search field with checkbox
    private Model[] modelItems; // our model in each of the row showing the text and checkbox
    // Item to be listed under the first button
    private ArrayList<String> browseValuesTest = new ArrayList<String>();
    private String[] browseValues = {"All Contracts", "NZ Politics", "Misc", "Infl 1215",
            "NZ Misc Issues", "GDP 0615", "GDP 0915", "Ministerial changes", "GDP 1215", "Govop 1617",
            "MPs to Depart", "ExchAU", "New Element", "Len Brown", "RMA", "NextLabLead", "Syria",
            "Unem 0915", "Unem 1215", "Infl0915", "Newspapers", "Infl0316", "TP Cable", "CAD 0615",
            "CAD 0915", "CAD 1215", "Lab Dep Leader", "Natlead Key", "NatLead", "OCR0616", "OCR0416",
            "NASA", "Payout 2016 17", "Payout 2017 18", "Payout 2014 15", "Payout 2015 16", "Wildlife",
            "Ele WIn 2017", "Burundi", "EC All 2017", "Brazil President", "Infl0616", "Infl0916",
            "Infl1216", "Next Election", "Aus Leadership", "Rep Nominee", "Election 2016",
            "Unem 0316", "Greater Wgtn Council", "Minister Depart", "Speaker 2017", "Which Justice",
            "Marijuana Leg", "Spain", "Election YEAR", "Rep VP Nominee", "Dem Nominee", "Dem VP",
            "Judith Collins", "GovOp 2015/16", "Greenhouse Gases", "NZ Obesity Rates", "Annl Intl Visitors",
            "PM HCASE", "By-Elections NZ", "NZ Super Fund", "UBER", "Insurance", "Stats", "NZ1 BoP",
            "Nth Kor - leadership", "Argentina Election", "Aus.Republic", "Eurozone Departures",
            "Other Departures", "2016 Election", "GovOp 2014/15", "Shinzo Abe", "NZ Growth 15/16",
            "NZ Growth 14/15", "NZ u/e 2014/15", "NZ u/e 2015/16", "NZ Infl 2015/16", "Boris",
            "Fair Fares", "GovOp 2018/19", "Clark UN", "HC to UK", "Can PM", "NZ Rep", "Future NZHOS",
            "Can Early Elect", "Nominal GDP", "Nominal GDP 2", "OCR0116", "TPPA", "OCR0316", "Lead LAB",
            "Iran Deal", "EU Membership", "Turnout Next", "Nomination Day", "US Am", "GovOp 2017/18",
            "VSNZF", "VSGRN", "VSLAB", "VSNAT", "Maurice Williamson", "Auckland Mayor 2016", "Flag Change",
            "FIREWORKS", "OCR0915", "OCR1015", "OCR1215", "LAB Local Govt", "Google", "TPP Agreement",
            "BTC/USD", "Trilateral Summit", "Andrew Little Depart", "Millennium Prizes", "Russia President",
            "GST Bill", "PAC PG", "MAORI PG", "ASIAN PG", "GENDER PG", "Second GE", "NB", "Ontario",
            "1 Million", "AKL 0815", "Swing States", "Iowa Caucus GOP", "NH PD", "NH PG", "CONGRESS",
            "AKL 0915", "Iowa Caucus Dem", "CAN MIN GOV", "MAJ votes", "SEATS RANGE", "Seats Liberal",
            "Seats NDP", "GITMO", "Fed rise", "SC Primary Dem"};

    // Item to be listed under the second button
    private String[] sortByValues = new String[] {"Trades", "Movement", "New", "Close Date"};
    private CustomAdapter adapter; // created custom adapter
    private ArrayList<String> selectedCategoriesContract = new ArrayList<String>(); // listed of selected categories
    private long mLastClickTime = 0;

    /**
     * Runs upon loading the activity
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_prediction);
        if (isNetworkAvailable(this) && MyJSONReader.getCategories()==null) new GetTask().execute();
        else browseValuesTest = MyJSONReader.getName();
        searchView(); // load the search view in this acitvity
        buttonIsClicked(); // load all the buttons in this activity
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        selectedCategoriesContract.clear();
    }

    class GetTask extends AsyncTask<String,String,ArrayList<String>> {
        ProgressDialog mDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(BrowsePrediction.this);
            mDialog.setMessage("Please wait...");
            mDialog.setIndeterminate(false);
            mDialog.setCancelable(true);
            mDialog.show();
        }

        @Override
        protected ArrayList<String> doInBackground(String... args){
            MyJSONReader.EstablishedWebConnection();
            MyJSONReader.JSONReader("All");
            browseValuesTest = MyJSONReader.getName();
            return browseValuesTest;
        }

        @Override
        protected void onPostExecute(ArrayList<String> info){
            mDialog.dismiss();
            searchView(); // load the search view in this acitvity
            buttonIsClicked(); // load all the buttons in this activity
        }
    }

    public boolean isNetworkAvailable(Context ctx){
        ConnectivityManager cm = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnectedOrConnecting()
                && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) return true;
        else return false;
    }

    /**
     * Below is the code for the search view
     * */
    public void searchView(){
        search = (SearchView) findViewById(R.id.searchView);
        search.setQueryHint("Search by keyword"); // show the hint in the search area
        search.setIconifiedByDefault(false); // turn off iconified
        search.setFocusable(false); // hide the keyboard upon load

        // Text Focus Change Listener
        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {}
        });

        // Search on Query Text Listener
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // Text user input in the search field when performing the search
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Check if no view has focus and hide the soft keyboard
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                // Add the search text query to the categories we want to search
                selectedCategoriesContract.add(query);
                gotoSearchPage();
                return false;
            }

            // Call when the query text is changed by the user
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    /**
     * It depends on whether we need the option menu or not. According to the design of the app
     * apparently it is not needed
     * */
/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_browse_prediction, menu);
        return super.onCreateOptionsMenu(menu);
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
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Checks to see if button is clicked
     */
    public void buttonIsClicked(){
        // All the buttons that should be in this activity
        final Button browseButton = (Button) findViewById(R.id.browseButton);
        final Button sortByButton = (Button) findViewById(R.id.sortByButton);
        final Button clearButton = (Button) findViewById(R.id.clearButton);
        final Button searchButton = (Button) findViewById(R.id.searchButton);

        loadView(browseValuesTest); // by default load this list
        // Change the list view depends on what button the user clicked
        browseButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                loadView(browseValuesTest);
            }
        });
        sortByButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               /* loadView(sortByValues);*/
            }
        });
        // These are the two buttons located at the bottom of the activity
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearView();
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // mis-clicking prevention, using threshold of 1s
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                // Do all other actions here

                // First of all it is important to check the Internet connection
/*                if (isNetworkAvailable()){*/
                // Called the selected categories to store the items selected
                selectedCategories();
                // Then check if there is any item selected or not
                if (selectedCategoriesContract.size() > 0) {
                    for (int i = 0; i < selectedCategoriesContract.size(); i++) {
                        System.out.println("TAG Categories Selected Item: " + selectedCategoriesContract.get(i));
                    }
                    gotoSearchPage();
                }
                // No item is selected
                else alertBox("Please select at least one category or search");
/*                }
                // There is no Internet Connection
                else alertBox("You have no Internet Connection");*/
            }
        });
    }

    /**
     * This is the alert box tp notify the users
     * @param message the message which we want to display as a pop up
     **/
    public void alertBox(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(BrowsePrediction.this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Go to search page and store the arraylist of string to use it in the next activity
     * This is the list of the contracts that is being selected in the browse page
     * */
    public void gotoSearchPage() {
        Intent intent = new Intent(this, searchPrediction.class);
        intent.putStringArrayListExtra("selectedContract", selectedCategoriesContract);
        startActivity(intent);
    }

    /**
     * ListView of items for the Browse section and Sort By section
     * @param values takes in the array of values
     * */
/*    public void loadView(String[] values){

        lv = (ListView) findViewById(R.id.listView1);
        modelItems = new Model[values.length];
        for(int i=0; i<values.length; i++){
            modelItems[i] = new Model(values[i],0);
        }
        adapter = new CustomAdapter(this, modelItems);
        lv.setAdapter(adapter);
    }*/

    public void loadView(ArrayList<String> values){
        lv = (ListView) findViewById(R.id.listView1);
        modelItems = new Model[values.size()];
        for(int i=0; i<values.size(); i++){
            modelItems[i] = new Model(values.get(i),0);
        }
        adapter = new CustomAdapter(this, modelItems);
        lv.setAdapter(adapter);
    }

    /**
     * Use this to reload the view after the user clicks search. We are going to use this
     * and pass it to the searchPrediction allowing it to gathers all information related
     * to the stocks
     * */
    public void selectedCategories(){
        // Check to see if the first state is selected
        // This is a special state where it should add all categories
        if (adapter.mCheckStates.get(0)){
            for(Model m : modelItems){
                selectedCategoriesContract.add(m.getName());
            }
        }
        else {
            for (int i = 0; i < modelItems.length; i++) {
                // Check all the items in the list view to see if it is clicked
                if (adapter.mCheckStates.get(i) && !selectedCategoriesContract.contains(modelItems[i].getName())) {
                    selectedCategoriesContract.add(modelItems[i].getName());
                }
            }
        }
    }

    /**
     * Clear the view - the selected items will be cleared
     * First of all checked the states, if any item is checked then clear that
     * */
    public void clearView(){
        for(int i=0; i<modelItems.length;i++){
            if(adapter.isChecked(i)){
                adapter.setChecked(i, false); // turn checked item(s) off
                lv.setAdapter(adapter); // set the adapter to show the changes
            }
        }
    }
}
