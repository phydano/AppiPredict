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
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by phydano
 * This is the browse prediction page that loads the information from the json file via URL
 * */
public class BrowsePrediction extends AppCompatActivity {

    private ListView lv; // the list of items to display under the search field with checkbox
    private Model[] modelItems; // our model in each of the row showing the text and checkbox
    private ArrayList<String> browseValues = new ArrayList<>(); // Item to be listed under the first button (Browse)
    private ArrayList<String> sortByValues = new ArrayList<>(); // Item to be listed under the second button (Sort)
    private CustomAdapter adapter; // created custom adapter
    private ArrayList<String> selectedCategoriesContract = new ArrayList<>(); // listed of selected categories
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_prediction);
        // Check whether the network is available and list of browse items
        if(!isNetworkAvailable(this) && browseValues.isEmpty()) optionalAlertBox("No Internet Connection");
        else if (isNetworkAvailable(this) && MyJSONReader.getCategories()==null) new GetTask().execute();
        // This is when the Categories are not empty, meaning we already have the info
        else if (MyJSONReader.getCategories() != null){
            browseValues = MyJSONReader.getName();
            ArrayList<String> newList = new ArrayList<>(new HashSet<>(browseValues));
            browseValues.clear();
            for(String e : newList){
                browseValues.add(e);
            }
        }
        searchView(); // load the search view
        buttonIsClicked(); // load all the buttons in this activity
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        selectedCategoriesContract.clear(); // clear the list upon restart
    }

    /**
     * Internal class AysyncTask - UI thread allowing to perform the background operations
     *
     * */
    class GetTask extends AsyncTask<String,String,ArrayList<String>> {
        ProgressDialog mDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // create a dialog showing that the app is loading to retrieve the info
            mDialog = new ProgressDialog(BrowsePrediction.this);
            mDialog.setMessage("Please wait...");
            mDialog.setIndeterminate(false); // play the loop animation
            mDialog.setCancelable(true); // allow user to cancel
            mDialog.show(); // show the dialog
        }

        @Override
        protected ArrayList<String> doInBackground(String... args){
            try {
                MyJSONReader.EstablishedWebConnection(); // establish connection to the web server and store the result
                MyJSONReader.JSONReader("All"); // load the result above and grab all the categories name
                browseValues = MyJSONReader.getName(); // now store all the categories name
            }catch(SocketTimeoutException | SocketException e) {
                optionalAlertBox("No Internet Connection");
            }
            return browseValues;
        }

        @Override
        protected void onPostExecute(ArrayList<String> info){
            mDialog.dismiss();
            searchView(); // load the search view in this acitvity
            buttonIsClicked(); // load all the buttons in this activity
        }
    }

    /**
     * Check to see if there is any Internet Connection
     * @param ctx the context
     * @return true if there is any Internet connection
     * */
    public static boolean isNetworkAvailable(Context ctx){
        ConnectivityManager cm = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnectedOrConnecting()
                && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected());
    }

    /**
     * Below is the code for the search view
     * */
    public void searchView(){
        SearchView search = (SearchView) findViewById(R.id.searchView);
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

        loadView(browseValues); // by default load this list
        // Change the list view depends on what button the user clicked
        browseButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                loadView(browseValues);
            }
        });
        sortByButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(sortByValues.isEmpty()){
                    sortByValues.add("Trades");
                    sortByValues.add("Movement");
                    sortByValues.add("New");
                    sortByValues.add("Close Date");
                }
                loadView(sortByValues);
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

                // Called the selected categories to store the items selected
                selectedCategories();
                // Then check if there is any item selected or not
                if (selectedCategoriesContract.size() > 0) {
                    gotoSearchPage();
                }
                // No item is selected
                else alertBox("Please select at least one category or search");
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
     * This is slightly different from the normal alert box
     * have 2 buttons where one is the same but the other can allow users to retry
     * the Internet Connection
     * @param message the text to display on the alert box
     * */
    public void optionalAlertBox(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(BrowsePrediction.this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        onBackPressed();
                    }
                })
                .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isNetworkAvailable(BrowsePrediction.this) && MyJSONReader.getCategories()==null) {
                            new GetTask().execute();
                            dialog.cancel();
                        }
                        else {
                            optionalAlertBox("No Internet Connection");
                        }
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
     * Load the items to the list view
     * @param values list of items we want to add to our list view
     * */
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
        for (int i = 0; i < modelItems.length; i++) {
            // Check all the items in the list view to see if it is clicked
            if (adapter.mCheckStates.get(i) && !selectedCategoriesContract.contains(modelItems[i].getName())) {
                selectedCategoriesContract.add(modelItems[i].getName());
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
