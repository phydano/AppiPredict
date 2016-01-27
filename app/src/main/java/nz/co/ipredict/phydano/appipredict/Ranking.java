package nz.co.ipredict.phydano.appipredict;

import android.app.ProgressDialog;
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
import java.util.List;

/**
 * Created by phydano
 * This is the ranking activities which show the users the top 10 traders
 * */
public class Ranking extends AppCompatActivity {

    private List<Traders> roiValues = new ArrayList<>(); // our top traders with the highest roi
    private List<Traders> networthValues = new ArrayList<>(); // our top traders with the highest networth
    private ArrayList<Traders> roiGrowingList = new ArrayList<>(); // start with 10 and keep growing
    private ArrayList<Traders> netGrowingList = new ArrayList<>(); // start with 10 and keep growing
    private boolean toogleSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        new GetRanking().execute();
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
        if(toogleSwitch) savedInstanceState.putBoolean("toogle",true);
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
        roiGrowingList = savedInstanceState.getParcelableArrayList("roiList");
        netGrowingList = savedInstanceState.getParcelableArrayList("netList");
        toogleSwitch = savedInstanceState.getBoolean("toogle");

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
            ReadingTopTraders.clearList(); // clear the list every time we reload the page
        }

        @Override
        protected List<Traders> doInBackground(Traders... args){
            try {
                roiValues = ReadingTopTraders.getTraders("roi"); // now store all the categories name
                networthValues = ReadingTopTraders.getTraders("networth");
            }catch(Exception e) {
                //optionalAlertBox("No Internet Connection");
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
     *
     * */
    public void loadROIlayout(){
        LinearLayout layout = (LinearLayout) findViewById(R.id.tradingheadings);
        layout.removeAllViews();
        TextView number = new TextView(this);
        TextView traderName = new TextView(this);
        TextView amount = new TextView(this);

        number.setText("#");
        number.setTypeface(null, Typeface.BOLD);
        number.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        number.setPadding(15,0,0,0);

        traderName.setText("Trader Name");
        traderName.setTypeface(null, Typeface.BOLD);
        traderName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2));

        amount.setText("Amount");
        amount.setTypeface(null, Typeface.BOLD);
        amount.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        layout.addView(number);
        layout.addView(traderName);
        layout.addView(amount);
    }

    public void loadNetlayout(){
        LinearLayout layout = (LinearLayout) findViewById(R.id.tradingheadings);
        layout.removeAllViews();
        TextView number = new TextView(this);
        TextView traderName = new TextView(this);
        TextView change = new TextView(this);
        TextView amount = new TextView(this);

        number.setText("#");
        number.setTypeface(null, Typeface.BOLD);
        number.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.6f));
        number.setPadding(10,0,0,0);

        traderName.setText("Trader Name");
        traderName.setTypeface(null, Typeface.BOLD);
        traderName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.4f));

        change.setText("Change");
        change.setTypeface(null, Typeface.BOLD);
        change.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        amount.setText("Amount");
        amount.setTypeface(null, Typeface.BOLD);
        amount.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        layout.addView(number);
        layout.addView(traderName);
        layout.addView(change);
        layout.addView(amount);
    }

    /**
     * Depends on which button is clicked, the results show accordingly
     * */
    public void clicked (){
        final Button roiButton = (Button) findViewById(R.id.roiTraders);
        final Button networthButton = (Button) findViewById(R.id.networthTraders);

        final Button moreButton = (Button) findViewById(R.id.moreButton);
        final Button lessButton = (Button) findViewById(R.id.lessButton);

        roiButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                loadView(roiGrowingList, false);
                toogleSwitch = false;
            }
        });

        networthButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                loadView(netGrowingList, true);
                toogleSwitch = true;
            }
        });

        moreButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int temp;
                if(toogleSwitch) {
                    temp = netGrowingList.size();
                    if(temp < 1000) {
                        for (int i = temp; i < temp + 10; i++) { // always 10 more than the size of the list
                            netGrowingList.add(networthValues.get(i));
                        }
                        loadView(netGrowingList, true);
                        toogleSwitch = true;
                    }
                }
                else {
                    temp = roiGrowingList.size(); // get the size of the list
                    if(temp < 1000) {
                        for (int i = temp; i < temp + 10; i++) { // always 10 more than the size of the list
                            roiGrowingList.add(roiValues.get(i));
                        }
                        loadView(roiGrowingList, false);
                        toogleSwitch = false;
                    }
                }
            }
        });

        lessButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int temp;

                if(toogleSwitch){
                    temp = netGrowingList.size();
                    if(temp > 10){
                        for(int i=temp; i>=temp-10; i--)
                            netGrowingList.remove(networthValues.get(i));
                        loadView(netGrowingList, true);
                        toogleSwitch = true;
                    }
                }
                else {
                    temp = roiGrowingList.size();
                    if(temp > 10){
                        for(int i=temp; i >= temp-10;i--)
                            roiGrowingList.remove(roiValues.get(i));
                        loadView(roiGrowingList, false);
                        toogleSwitch = false;
                    }
                }
            }
        });
    }
}