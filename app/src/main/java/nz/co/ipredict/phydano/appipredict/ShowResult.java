package nz.co.ipredict.phydano.appipredict;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by phydano
 * This is the activity that shows the result of the selected subcategories, that is the
 * contract that we wanted to see its entire information. These info is passed from the
 * previous activity which is the 'searchPrediction' in which the info is gathered from
 * the JSON file provided by Don.
 * */
public class ShowResult extends AppCompatActivity {

    private ContractInfo selectedContract; // this is the object that contains the contract info we want
    private ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        // first of all check whether the object is null just in case
        if(getIntent().getExtras().getParcelable("myObject") != null) {
            selectedContract = getIntent().getExtras().getParcelable("myObject"); // load it all in
            loadContractInfo(selectedContract);
            sellOrdersTable(selectedContract.getSellOrders()); // dealing with sell order table
            buyOrdersTable(selectedContract.getBuyOrders()); // dealing with buy order table
        }
        mScrollView = (ScrollView) findViewById(R.id.resultPageScrollView);
    }

    /**
     * Save the activity state
     * Code from: http://stackoverflow.com/questions/29208086/save-the-position-of-scrollview-when-the-orientation-changes
     * */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("scroll",
                new int[]{mScrollView.getScrollX(), mScrollView.getScrollY()});
    }

    /**
     * Restore the activity state assuming there is no null
     * Code from: http://stackoverflow.com/questions/29208086/save-the-position-of-scrollview-when-the-orientation-changes
     * */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int[] position = savedInstanceState.getIntArray("scroll");
        if(position != null)
            mScrollView.post(new Runnable() {
                public void run() {
                    mScrollView.scrollTo(position[0], position[1]);
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
     * Go to iPredict Twitter page by loading a browser
     * */
    public void twitterPage(View view){
        Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/ipredictnz"));
        startActivity(openBrowser);
    }

    /**
     * Go to iPredict Twitter page by loading a browser
     * */
    public void facebookPage(View view){
        Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/iPredictNZ/"));
        startActivity(openBrowser);
    }


    /**
     * Loading all the info from the contract and display it according to the layout
     * @param contract the selected contract
     * */
    public void loadContractInfo(ContractInfo contract){
        // name of the stock
        TextView title = (TextView) findViewById(R.id.resultTileDisplay);
        title.setText(contract.getStockName());
        // short description of the stock
        title = (TextView) findViewById(R.id.shortDescription);
        title.setText(contract.getShortDescription());
        // last trade price of the stock
        title = (TextView) findViewById(R.id.lastTradePrice);
        title.setText(Html.fromHtml("<b>Last trade price</b> " + "<br>" + "<font color='#0066cc'><b> $" + contract.getLastTradePrice() + "</b></font>"));
        // last trade time of the stock
        title = (TextView) findViewById(R.id.lastTradeTime);
        title.setText(Html.fromHtml("<b>Last trade time</b> " + "<br>" + "<font color='#0066cc'><b>" + convertTime(contract.getLastTradeTime()) + "</b></font>"));
        // today's volume of the stock
        title = (TextView) findViewById(R.id.todaysVol);
        title.setText(Html.fromHtml("<b>Todays Vol</b> " + "<br>" + "<font color='#0066cc'><b>" + contract.getTodaysVolume() + "</b></font>"));
        // average volume of the stock
        title = (TextView) findViewById(R.id.avgVolume);
        title.setText(Html.fromHtml("<b>Average Daily Vol</b> " + "<br>" + "<font color='#0066cc'><b>" + changeDecimal(contract.getAverageDailyVol()) + "</b></font>"));
        // today's change of the stock
        title = (TextView) findViewById(R.id.todaysChange);
        title.setText(Html.fromHtml("<b>Todays Change</b> " + "<br>" + "<font color='#29a329'><b>" + contract.getTodaysChange() + "</b></font>"));
        // get stock status
        title = (TextView) findViewById(R.id.status);
        title.setText(Html.fromHtml("<b>Status</b> " + "<br>" + "<font color='#29a329'><b>" + contract.getStatus() + "</b></font>"));
        // stock start date
        title = (TextView) findViewById(R.id.startDate);
        title.setText(Html.fromHtml("<b>Start Date</b> " + "<br>" + "<font color='#0066cc'><b>" + convertDate(contract.getStartDate()) + "</b></font>"));
        // stock end date
        title = (TextView) findViewById(R.id.endDate);
        title.setText(Html.fromHtml("<b>End Date</b> " + "<br>" + "<font color='#0066cc'><b>" + convertDate(contract.getEndDate()) + "</b></font>"));
        // long description
        title = (TextView) findViewById(R.id.longDescriptionText);
        title.setText(contract.getLongDescription());
        // judinging criteria
        title = (TextView) findViewById(R.id.judgingCriteriaText);
        // Some of the Judge statments in the contract is empty - so it is good to say 'Empty' rather than '[]'
        if(contract.getJudgeStatement().equals("") || contract.getJudgeStatement().equals("[]")) title.setText("Empty");
        else title.setText(contract.getJudgeStatement());
    }

    /**
     * Deal with buy order table and display it according to the layout
     * @param buyOrder the list of buy order items gathered from the list
     * */
    public void buyOrdersTable(List<BookAndStock> buyOrder){
        // get a reference to the table layout
        TableLayout orderTable = (TableLayout) findViewById(R.id.buyOrdersTable);

        // loop through all the items in the list of buy order
        for(int i=0; i<buyOrder.size(); i++) {
            // create a new row to be added
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            // the buy order quantity text field
            TextView buyOrderQuantity = new TextView(this);
            buyOrderQuantity.setText(buyOrder.get(i).getQuantity());
            buyOrderQuantity.setGravity(Gravity.CENTER);

            // the buy order price text field
            TextView buyOrderPrice = new TextView(this);
            buyOrderPrice.setText(changeDecimal(buyOrder.get(i).getPrice()));
            buyOrderPrice.setGravity(Gravity.CENTER);

            // add both to the row
            tr.addView(buyOrderQuantity);
            tr.addView(buyOrderPrice);

            // add to the layout
            orderTable.addView(tr, new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        }
    }

    /**
     * Deal with sell order table and display it according to the layout
     * @param sellOrder the list of sell order items gathered from the list
     * */
    public void sellOrdersTable(List<BookAndStock> sellOrder){
        // get a reference to the table layout
        TableLayout orderTable = (TableLayout) findViewById(R.id.sellOrdersTable);

        // loop through all the items in the list of sell order
        for(int i=0; i<sellOrder.size(); i++) {
            // create a new row to be added
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            // the sell order quantity text field
            TextView sellOrderQuantity = new TextView(this);
            sellOrderQuantity.setText(sellOrder.get(i).getQuantity());
            sellOrderQuantity.setGravity(Gravity.CENTER);

            // the sell order price text field
            TextView sellOrderPrice = new TextView(this);
            sellOrderPrice.setText(changeDecimal(sellOrder.get(i).getPrice()));
            sellOrderPrice.setGravity(Gravity.CENTER);

            // add both to the row
            tr.addView(sellOrderQuantity);
            tr.addView(sellOrderPrice);

            // add to the layout
            orderTable.addView(tr, new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        }
    }

    /**
     * The date time from the JSON is not suitable to display.
     * This method basically converts it to a date/month/year format.
     * @param dateTime the date time format we want to convert
     * */
    public String convertDate(String dateTime){
        String dateTimeReformat = "";
        Date d;

        try {
            // if empty date/time then just return back empty string
            if(dateTime.equals("[]")) return dateTimeReformat;
            // do the conversion
            d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(dateTime);
            dateTimeReformat = new SimpleDateFormat("dd-MMM-yyyy").format(d);
        }catch(ParseException e){
            e.printStackTrace();
        }
        return dateTimeReformat;
    }

    /**
     * The date time from JSON is not suitable to display.
     * This method basically converts it to a time format
     * @param dateTime the date time format we want to convert
     * */
    public String convertTime (String dateTime){
        String dateTimeReformat = "";
        Date d;

        try {
            // if empty date/time then just return back empty string
            if(dateTime.equals("[]")) return dateTimeReformat;
            // do the conversion
            d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(dateTime);
            dateTimeReformat = new SimpleDateFormat("h:mm a").format(d);
        }catch(ParseException e){
            e.printStackTrace();
        }
        return dateTimeReformat;
    }

    /**
     * Limits the decimal to 4 decimal place.
     * @param convert the number we want to convert
     * */
    public String changeDecimal(String convert){
        return Double.toString(Double.valueOf(
                new DecimalFormat("#.####").format(Double.parseDouble(convert))));

    }
}
