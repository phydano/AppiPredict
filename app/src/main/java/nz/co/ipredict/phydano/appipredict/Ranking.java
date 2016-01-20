package nz.co.ipredict.phydano.appipredict;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phydano
 *
 * */
public class Ranking extends AppCompatActivity {

    private List<Traders> roiValues = new ArrayList<>();
    private List<Traders> networthValues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        new GetTask().execute();
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
     *
     * */
    class GetTask extends AsyncTask<Traders,Traders,List<Traders>> {
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
            //searchView(); // load the search view in this acitvity
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
            NetworthCustomAdapter Networthadapter = new NetworthCustomAdapter(this, modelItems);
            lv.setAdapter(Networthadapter);
        }
        else{
            ROICustomAdapter ROIadapter = new ROICustomAdapter(this, modelItems);
            lv.setAdapter(ROIadapter);
        }
    }

    public void clicked (){
        final Button test = (Button) findViewById(R.id.testbutton);
        final Button test2 = (Button) findViewById(R.id.testbutton2);

        test.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                loadView(roiValues, false);
            }
        });

        test2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                loadView(networthValues, true);
            }
        });
    }
}