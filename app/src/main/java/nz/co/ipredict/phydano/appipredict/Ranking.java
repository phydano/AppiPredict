package nz.co.ipredict.phydano.appipredict;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phydano
 * This is the ranking activities which show the users the top 10 traders
 * The display traders can be shown ranging between 10 - 500. When either
 * increase or decrease the amount display, it increase/decrease by 10
 * per click with 10 being the minimum and 500 being the max.
 * */
public class Ranking extends AppCompatActivity {

    private ArrayList<Traders> roiGrowingList = new ArrayList<>(); // start with 10 and keep growing
    private ArrayList<Traders> netGrowingList = new ArrayList<>(); // start with 10 and keep growing
    private boolean toogleSwitch; // switch to track whether we on ROI (false) or Networth (true) tab
    private ListView lv; // our list view
    private NetworthCustomAdapter networthAdapter; // the networth adapter
    private ROICustomAdapter roiAdapter; // the roi adapter
    private Button roiButton;
    private Button networthButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        // If there is no Internet connection then pop a dialog
        if(!BrowsePrediction.isNetworkAvailable(this) && ReadingTopTraders.getTraders("roi").isEmpty()) optionalAlertBox("No Internet Connection");
        // If the network is available and the traders list is totally empty then we execute an AsynTask
        else if(BrowsePrediction.isNetworkAvailable(this) && ReadingTopTraders.getTraders("roi").isEmpty()) {
            new GetRanking().execute();
        }
    }

    /**
     * Saved the activity state to restore when screen orientation changes
     * */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("roiList", roiGrowingList);
        savedInstanceState.putParcelableArrayList("netList", netGrowingList);
        if(toogleSwitch) savedInstanceState.putBoolean("toogle", true);
        else savedInstanceState.putBoolean("toogle",false);
    }

    /**
     * Restore the activity state back when the screen orientation changes
     * */
    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        roiGrowingList = savedInstanceState.getParcelableArrayList("roiList");
        netGrowingList = savedInstanceState.getParcelableArrayList("netList");
        toogleSwitch = savedInstanceState.getBoolean("toogle");
        clicked(); // load all the buttons in this activity
        // Restore back the list depend on the state it was in
        if(toogleSwitch) {
            setNetworthButtonColor();
            loadView(netGrowingList, true);
            toogleSwitch = true;
        }
        else {
            setRoiButtonColor();
            loadView(roiGrowingList, false);
            toogleSwitch = false;
        }
    }

    /**
     * Item in the option menu. There would be varies case depend on the selection
     * The code here allow us to go back to the home page (parent activity)
     * @param item the items in the action menu
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
     * Clear the list upon exit, then when came back we can retrieve the list again
     * */
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        ReadingTopTraders.getTraders("roi").clear();
        ReadingTopTraders.getTraders("networth").clear();
    }

    /**
     * Internal class AysyncTask - UI thread allowing to perform the background operations
     * */
    class GetRanking extends AsyncTask<Traders,Traders,List<Traders>> {
        ProgressDialog mDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // create a dialog showing that the app is loading to retrieve the info
            mDialog = new ProgressDialog(Ranking.this);
            mDialog.setMessage("Please wait...");
            mDialog.setIndeterminate(false); // play the loop animation
            mDialog.setCancelable(true); // allow user to cancel
            mDialog.show(); // show the dialog
        }

        @Override
        protected List<Traders> doInBackground(Traders... args){
            try {
                for(int i=0; i<10; i++){
                    roiGrowingList.add(ReadingTopTraders.getTraders("roi").get(i));
                    netGrowingList.add(ReadingTopTraders.getTraders("networth").get(i));
                }

            }catch(Exception e) {
                optionalAlertBox("No Internet Connection");
            }
            return roiGrowingList;
        }

        @Override
        protected void onPostExecute(List<Traders> info){
            mDialog.dismiss();
            // load only 10 values first upon first execute
            clicked(); // load all the buttons in this activity
            loadView(roiGrowingList, false);
            toogleSwitch = false; // switch is on roi side
        }
    }

    /**
     * Load the items to the list view
     * @param values list of items we want to add to our list view
     * @param toogle whether we want to show the ROI or Networth
     * */
    public void loadView(ArrayList<Traders> values, boolean toogle){

        lv = (ListView) findViewById(R.id.rankingList);

        // depends on what button the users click, we handle the case differently
        if(toogle) {
            loadNetlayout();
            if (networthAdapter == null) {
                networthAdapter = new NetworthCustomAdapter(this, values);
                lv.setAdapter(networthAdapter);
            }
            else networthAdapter.notifyDataSetChanged();
        }
        else{
            loadROIlayout();
            if(roiAdapter == null) {
                roiAdapter = new ROICustomAdapter(this, values);
                lv.setAdapter(roiAdapter);
            }
            else roiAdapter.notifyDataSetChanged();
        }
    }

    /**
     * load the ROI layout to display in the linear layout under the activity_ranking
     * */
    public void loadROIlayout(){
        // Setup layout for ROI traders
        LinearLayout layout = (LinearLayout) findViewById(R.id.tradingheadings);
        layout.removeAllViews();
        TextView number = new TextView(this);
        TextView traderName = new TextView(this);
        TextView amount = new TextView(this);

        // set text, format and layout for the number text view
        number.setText("#");
        number.setTypeface(null, Typeface.BOLD);
        number.setTextColor(Color.BLACK);
        number.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        number.setPadding(15, 0, 0, 0);

        // set text, format and layout for the trader name text view
        traderName.setText("Trader Name");
        traderName.setTypeface(null, Typeface.BOLD);
        traderName.setTextColor(Color.BLACK);
        traderName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2));

        // set text, format and layout for the amount text view
        amount.setText("Amount");
        amount.setTypeface(null, Typeface.BOLD);
        amount.setTextColor(Color.BLACK);
        amount.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        // Add the three views to the layout
        layout.addView(number);
        layout.addView(traderName);
        layout.addView(amount);
    }

    /**
     * load the Networth layout to display in the linear layout under the activity_ranking
     * */
    public void loadNetlayout(){

        // Setup layout for the Net Worth traders
        LinearLayout layout = (LinearLayout) findViewById(R.id.tradingheadings);
        layout.removeAllViews(); // should remove all the views before add in
        TextView number = new TextView(this);
        TextView traderName = new TextView(this);
        TextView change = new TextView(this);
        TextView amount = new TextView(this);

        // set text, format and layout for the number text view
        number.setText("#");
        number.setTypeface(null, Typeface.BOLD);
        number.setTextColor(Color.BLACK);
        number.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.6f));
        number.setPadding(10, 0, 0, 0);

        // set text, format and layout for the trader name text view
        traderName.setText("Trader Name");
        traderName.setTypeface(null, Typeface.BOLD);
        traderName.setTextColor(Color.BLACK);
        traderName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.4f));

        // set text, format and layout for the change text view
        change.setText("Change");
        change.setTypeface(null, Typeface.BOLD);
        change.setTextColor(Color.BLACK);
        change.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        // set text, format and layout for the amount text view
        amount.setText("Amount");
        amount.setTypeface(null, Typeface.BOLD);
        amount.setTextColor(Color.BLACK);
        amount.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        // Add the four views to the layout
        layout.addView(number);
        layout.addView(traderName);
        layout.addView(change);
        layout.addView(amount);
    }

    /**
     * Depends on which button is clicked, the results show accordingly
     * */
    public void clicked (){
        // The ROI and the Networth buttons
        roiButton = (Button) findViewById(R.id.roiTraders);
        networthButton = (Button) findViewById(R.id.networthTraders);

        // The more and less buttons to show more or less traders
        final Button moreButton = (Button) findViewById(R.id.moreButton);
        final Button lessButton = (Button) findViewById(R.id.lessButton);

        roiButton.getBackground().setColorFilter(Color.parseColor("#084EE4"), PorterDuff.Mode.MULTIPLY);
        roiButton.setTextColor(Color.WHITE);
        // ROI button on click listener
        roiButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                setRoiButtonColor();
                loadView(roiGrowingList, false);
                toogleSwitch = false;
            }
        });

        // Networth on click listener
        networthButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                setNetworthButtonColor();
                loadView(netGrowingList, true);
                toogleSwitch = true;
            }
        });

        // More button on click listener
        moreButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int temp;
                if(toogleSwitch) {
                    temp = netGrowingList.size(); // temp list to store current size of the top traders on Networth
                    if(temp < 500) {
                        for (int i = temp; i < temp + 10; i++) { // always 10 more than the size of the list
                            netGrowingList.add(ReadingTopTraders.getTraders("networth").get(i));
                        }
                        // reload the view
                        loadView(netGrowingList, true);
                        toogleSwitch = true;
                        lv.setSelection(networthAdapter.getCount()-1);
                    }
                }
                else {
                    temp = roiGrowingList.size(); // temp list to store current size of the top traders on ROI
                    if(temp < 500) {
                        for (int i = temp; i < temp + 10; i++) { // always 10 more than the size of the list
                            roiGrowingList.add(ReadingTopTraders.getTraders("roi").get(i));
                        }
                        // reload the view
                        loadView(roiGrowingList, false);
                        toogleSwitch = false;
                        lv.setSelection(roiAdapter.getCount()-1);
                    }
                }
            }
        });

        // less button on click listener
        lessButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int temp;
                if(toogleSwitch){
                    temp = netGrowingList.size(); // temp list to store current size of the top traders on Networth
                    if(temp > 10){ // 10 traders are the minimum
                        for(int i=temp; i>=temp-10; i--)
                            netGrowingList.remove(ReadingTopTraders.getTraders("networth").get(i));
                        // reload the view
                        loadView(netGrowingList, true);
                        toogleSwitch = true;
                        lv.setSelection(networthAdapter.getCount() - 1);
                    }
                }
                else {
                    temp = roiGrowingList.size(); // temp list to store current size of the top traders on ROI
                    if(temp > 10){ // 10 traders are the minimum
                        for(int i=temp; i >= temp-10;i--)
                            roiGrowingList.remove(ReadingTopTraders.getTraders("roi").get(i));

                        // reload the view
                        loadView(roiGrowingList, false);
                        toogleSwitch = false;
                        lv.setSelection(roiAdapter.getCount() - 1);
                    }
                }
            }
        });
    }

    /**
     * This is slightly different from the normal alert box
     * have 2 buttons where one is the same but the other can allow users to retry
     * the Internet Connection
     * @param message the text to display on the alert box
     * */
    public void optionalAlertBox(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(Ranking.this);
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
                        if (BrowsePrediction.isNetworkAvailable(Ranking.this) && ReadingTopTraders.getTraders("roi").isEmpty()) {
                            new GetRanking().execute();
                            dialog.cancel();
                        } else {
                            optionalAlertBox("No Internet Connection");
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Set up a button color for the Roi button and reset the color on the Networth button
     * */
    public void setRoiButtonColor(){
        roiButton.getBackground().setColorFilter(Color.parseColor("#084EE4"), PorterDuff.Mode.MULTIPLY);
        roiButton.setTextColor(Color.WHITE);
        networthButton.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        networthButton.setTextColor(Color.BLACK);
        networthAdapter = null;
    }

    /**
     * Set up a button color for the Networth button and reset the color on the ROI button
     * */
    public void setNetworthButtonColor(){
        roiButton.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        roiButton.setTextColor(Color.BLACK);
        networthButton.setTextColor(Color.WHITE);
        networthButton.getBackground().setColorFilter(Color.parseColor("#084EE4"), PorterDuff.Mode.MULTIPLY);
        roiAdapter = null;
    }
}