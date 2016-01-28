package nz.co.ipredict.phydano.appipredict;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by phydano
 * This is the ranking activities which show the users the top 10 traders
 * The display traders can be shown ranging between 10 - 500. When either
 * increase or decrease the amount display, it increase/decrease by 10
 * per click with 10 being the minimum and 500 being the max.
 * */
public class Ranking extends AppCompatActivity {

    private ArrayList<Traders> roiValues = new ArrayList<>(); // our top traders with the highest roi
    private ArrayList<Traders> networthValues = new ArrayList<>(); // our top traders with the highest networth
    private ArrayList<Traders> roiGrowingList = new ArrayList<>(); // start with 10 and keep growing
    private ArrayList<Traders> netGrowingList = new ArrayList<>(); // start with 10 and keep growing
    private boolean toogleSwitch; // switch to track whether we on ROI (false) or Networth (true) tab

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        // If there is no Internet connection then pop a dialog
        if(!BrowsePrediction.isNetworkAvailable(this) && ReadingTopTraders.getTraders("roi").isEmpty()) optionalAlertBox("No Internet Connection");
        // If the network is available and the traders list is totally empty then we execute an AsynTask
        else if(BrowsePrediction.isNetworkAvailable(this) && ReadingTopTraders.getTraders("roi").isEmpty()) new GetRanking().execute();
        // Case where we jump back and forth between activity
        else{
            System.out.println("TAG: Roi value: " + roiValues.size());
            System.out.println("TAG: Networth value: " + networthValues.size());
            System.out.println("TAG: Roi Growing List: " + roiGrowingList.size());
            System.out.println("TAG: Networth Growing List: " + netGrowingList.size());
            System.out.println("TAG: Traders Roi size" + ReadingTopTraders.getTraders("roi").size());
            System.out.println("TAG: Traders Net size" + ReadingTopTraders.getTraders("networth").size());
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
        savedInstanceState.putParcelableArrayList("roiValue", roiValues);
        savedInstanceState.putParcelableArrayList("netValue", networthValues);
        savedInstanceState.putParcelableArrayList("roiList", roiGrowingList);
        savedInstanceState.putParcelableArrayList("netList", netGrowingList);
        if(toogleSwitch) savedInstanceState.putBoolean("toogle", true);
        else savedInstanceState.putBoolean("toogle",false);
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
        roiValues = savedInstanceState.getParcelableArrayList("roiValue");
        networthValues = savedInstanceState.getParcelableArrayList("netValue");
        roiGrowingList = savedInstanceState.getParcelableArrayList("roiList");
        netGrowingList = savedInstanceState.getParcelableArrayList("netList");
        toogleSwitch = savedInstanceState.getBoolean("toogle");
        // Restore back the list
        if(toogleSwitch) {
            loadView(netGrowingList, true);
            toogleSwitch = true;
        }
        else {
            loadView(roiGrowingList, false);
            toogleSwitch = false;
        }
        clicked(); // load all the buttons in this activity
        System.out.println("TAG: Load Roi value: " + roiValues.size());
        System.out.println("TAG: Load Networth value: " + networthValues.size());
        System.out.println("TAG: Load Roi Growing List: " + roiGrowingList.size());
        System.out.println("TAG: Load Networth Growing List: " + netGrowingList.size());
        System.out.println("TAG: Load Traders Roi size" + ReadingTopTraders.getTraders("roi").size());
        System.out.println("TAG: Load Traders Net size" + ReadingTopTraders.getTraders("networth").size());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ranking, menu);
        return true;
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
                for(Traders roiTraders : ReadingTopTraders.getTraders("roi")) roiValues.add(roiTraders); // now store all the categories name
                for(Traders netTraders : ReadingTopTraders.getTraders("networth")) networthValues.add(netTraders);

            }catch(Exception e) {
                optionalAlertBox("No Internet Connection");
            }
            return roiValues;
        }

        @Override
        protected void onPostExecute(List<Traders> info){
            mDialog.dismiss();
            // load only 10 values first upon first execute
            for(int i=0; i<10; i++){
                roiGrowingList.add(roiValues.get(i));
                netGrowingList.add(networthValues.get(i));
            }
            loadView(roiGrowingList, false);
            toogleSwitch = false; // switch is on roi side
            clicked(); // load all the buttons in this activity
        }
    }

    /**
     * Load the items to the list view
     * @param values list of items we want to add to our list view
     * @param toogle whether we want to show the ROI or Networth
     * */
    public void loadView(List<Traders> values, boolean toogle){
        ListView lv = (ListView) findViewById(R.id.rankingList);
        Traders[] modelItems = new Traders[values.size()];

        for(int i=0; i<values.size(); i++){
            modelItems[i] = new Traders(values.get(i).getRank(), values.get(i).getTraderName(),
                    values.get(i).getRoi(), values.get(i).getNetworth(), values.get(i).getNetworthChange());
        }

        if(toogle) {
            loadNetlayout();
            NetworthCustomAdapter networthAdapter = new NetworthCustomAdapter(this, modelItems);
            lv.setAdapter(networthAdapter);
        }
        else{
            loadROIlayout();
            ROICustomAdapter roiAdapter = new ROICustomAdapter(this, modelItems);
            lv.setAdapter(roiAdapter);
        }
    }

    /**
     * load the ROI layout to display in the linear layout under the activity_ranking
     * */
    public void loadROIlayout(){
        LinearLayout layout = (LinearLayout) findViewById(R.id.tradingheadings);
        layout.removeAllViews();
        TextView number = new TextView(this);
        TextView traderName = new TextView(this);
        TextView amount = new TextView(this);

        // set text, format and layout for the number text view
        number.setText("#");
        number.setTypeface(null, Typeface.BOLD);
        number.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        number.setPadding(15,0,0,0);

        // set text, format and layout for the trader name text view
        traderName.setText("Trader Name");
        traderName.setTypeface(null, Typeface.BOLD);
        traderName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2));

        // set text, format and layout for the amount text view
        amount.setText("Amount");
        amount.setTypeface(null, Typeface.BOLD);
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
        LinearLayout layout = (LinearLayout) findViewById(R.id.tradingheadings);
        layout.removeAllViews(); // should remove all the views before add in

        TextView number = new TextView(this);
        TextView traderName = new TextView(this);
        TextView change = new TextView(this);
        TextView amount = new TextView(this);

        // set text, format and layout for the number text view
        number.setText("#");
        number.setTypeface(null, Typeface.BOLD);
        number.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.6f));
        number.setPadding(10, 0, 0, 0);

        // set text, format and layout for the trader name text view
        traderName.setText("Trader Name");
        traderName.setTypeface(null, Typeface.BOLD);
        traderName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.4f));

        // set text, format and layout for the change text view
        change.setText("Change");
        change.setTypeface(null, Typeface.BOLD);
        change.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        // set text, format and layout for the amount text view
        amount.setText("Amount");
        amount.setTypeface(null, Typeface.BOLD);
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
        final Button roiButton = (Button) findViewById(R.id.roiTraders);
        final Button networthButton = (Button) findViewById(R.id.networthTraders);

        // The more and less buttons to show more or less traders
        final Button moreButton = (Button) findViewById(R.id.moreButton);
        final Button lessButton = (Button) findViewById(R.id.lessButton);

        // ROI button on click listener
        roiButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                loadView(roiGrowingList, false);
                toogleSwitch = false;
            }
        });

        // Networth on click listener
        networthButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
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
                    if(temp < 1000) {
                        for (int i = temp; i < temp + 10; i++) { // always 10 more than the size of the list
                            netGrowingList.add(networthValues.get(i));
                        }
                        // reload the view
                        loadView(netGrowingList, true);
                        toogleSwitch = true;
                    }
                }
                else {
                    temp = roiGrowingList.size(); // temp list to store current size of the top traders on ROI
                    if(temp < 1000) {
                        for (int i = temp; i < temp + 10; i++) { // always 10 more than the size of the list
                            roiGrowingList.add(roiValues.get(i));
                        }
                        // reload the view
                        loadView(roiGrowingList, false);
                        toogleSwitch = false;
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
                            netGrowingList.remove(networthValues.get(i));
                        // reload the view
                        loadView(netGrowingList, true);
                        toogleSwitch = true;
                    }
                }
                else {
                    temp = roiGrowingList.size(); // temp list to store current size of the top traders on ROI
                    if(temp > 10){ // 10 traders are the minimum
                        for(int i=temp; i >= temp-10;i--)
                            roiGrowingList.remove(roiValues.get(i));
                        // reload the view
                        loadView(roiGrowingList, false);
                        toogleSwitch = false;
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
                        if (BrowsePrediction.isNetworkAvailable(Ranking.this) && MyJSONReader.getCategories() == null) {
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
}