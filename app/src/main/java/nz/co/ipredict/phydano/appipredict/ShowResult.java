package nz.co.ipredict.phydano.appipredict;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class ShowResult extends AppCompatActivity {

    private ContractInfo selectedContract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        if(getIntent().getExtras().getParcelable("myObject") != null) {
            selectedContract = getIntent().getExtras().getParcelable("myObject");
            loadContractInfo(selectedContract);
            sellOrdersTable(selectedContract.getSellOrders());
            buyOrdersTable(selectedContract.getBuyOrders());
            System.out.println("Test: Size of the buy orders " + selectedContract.getBuyOrders().size());
            System.out.println("Test: Size of the sell orders " + selectedContract.getSellOrders().size());
        }
        System.out.println("Test: Show result: " + selectedContract.getName());
/*        loadWebView();*/
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_result, menu);
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
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadContractInfo(ContractInfo contract){
        // name of the stock
        TextView title = (TextView) findViewById(R.id.resultTileDisplay);
        title.setText(contract.getStockName());
        // short description of the stock
        title = (TextView) findViewById(R.id.shortDescription);
        title.setText(contract.getShortDescription());
        // last trade price of the stock
        title = (TextView) findViewById(R.id.lastTradePrice);
        title.setText("Last trade price " + "\n" + "$" + contract.getLastTradePrice());
        // last trade time of the stock
        title = (TextView) findViewById(R.id.lastTradeTime);
        title.setText("Last trade time " + "\n" + contract.getLastTradeTime());
        // today's volume of the stock
        title = (TextView) findViewById(R.id.todaysVol);
        title.setText("Todays Vol " + "\n" + contract.getTodaysVolume());
        // average volume of the stock
        title = (TextView) findViewById(R.id.avgVolume);
        title.setText("Average Daily Vol " + "\n" + contract.getAverageDailyVol());
        // today's change of the stock
        title = (TextView) findViewById(R.id.todaysChange);
        title.setText("Todays Change " + "\n" + contract.getTodaysChange());
        // get stock status
        title = (TextView) findViewById(R.id.status);
        title.setText("Status " + "\n" + contract.getStatus());
        // stock start date
        title = (TextView) findViewById(R.id.startDate);
        title.setText("Start Date " + "\n" + contract.getStartDate());
        // stock end date
        title = (TextView) findViewById(R.id.endDate);
        title.setText("End Date " + "\n" + contract.getEndDate());
        // long description
        title = (TextView) findViewById(R.id.longDescriptionText);
        title.setText(contract.getLongDescription());
        // judinging criteria
        title = (TextView) findViewById(R.id.judgingCriteriaText);
        if(contract.getJudgeStatement().equals("") || contract.getJudgeStatement().equals("[]")) title.setText("Empty");
        else title.setText(contract.getJudgeStatement());
    }

    public void buyOrdersTable(List<BookAndStock> buyOrder){
        // get a reference to the table layout
        TableLayout orderTable = (TableLayout) findViewById(R.id.buyOrdersTable);
        // Making sure that the size of buy order and sell order are the same

        for(int i=0; i<buyOrder.size(); i++) {
            // create a new row to be added
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView buyOrderQuantity = new TextView(this);
            buyOrderQuantity.setText(buyOrder.get(i).getQuantiity());

            TextView buyOrderPrice = new TextView(this);
            buyOrderPrice.setText(buyOrder.get(i).getPrice());

            tr.addView(buyOrderQuantity);
            tr.addView(buyOrderPrice);

            orderTable.addView(tr, new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        }
    }

    public void sellOrdersTable(List<BookAndStock> sellOrder){
        // get a reference to the table layout
        TableLayout orderTable = (TableLayout) findViewById(R.id.sellOrdersTable);
        // Making sure that the size of buy order and sell order are the same

        for(int i=0; i<sellOrder.size(); i++) {
            // create a new row to be added
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView sellOrderQuantity = new TextView(this);
            sellOrderQuantity.setText(sellOrder.get(i).getQuantiity());

            TextView sellOrderPrice = new TextView(this);
            sellOrderPrice.setText(sellOrder.get(i).getPrice());

            tr.addView(sellOrderQuantity);
            tr.addView(sellOrderPrice);

            orderTable.addView(tr, new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        }
    }

/*    public void loadWebView(){
        WebView wv = (WebView) findViewById(R.id.stockflashgraph);
    }*/

}
