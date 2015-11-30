package nz.co.ipredict.phydano.appipredict;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * This is the main activity in which is the Home Page of iPredict.
 **/
public class MainActivity extends AppCompatActivity {

    // Load upon opening the app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // load the main activity layout
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        usersComment();
    }

    /**
     * The user comment in the home page
     **/
    public void usersComment(){
        String userCommentOne = "\"One of the only three prediction markets in the world\"";
        String userCommentTwo = "\"A fantastic information asset in the NZ market\"";
        String username = "<font color='#0026FF'>iPredict Trader</font>";
        TextView t1 = (TextView) findViewById(R.id.user1);
        TextView t2 = (TextView) findViewById(R.id.user2);
        t1.setText(Html.fromHtml(userCommentOne + "<br/>" + username));
        t2.setText(Html.fromHtml(userCommentTwo + "<br/>" + username));
    }

    //Todo: delete this afterwards as this is just the front page button to direct to about us page
    /** Go to the second activity which is the 'Trading' at the front screen */
    public void gotoBrowsePrediction(View view) {
        Intent intent = new Intent(this, BrowsePrediction.class);
        startActivity(intent);
    }

    /** Create the action menu */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /** Determine which of the action button is clicked and handle that event */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Action depends on what item you selected in the menu
        if (id == R.id.action_signIn) { // gets you to the sign in page
            this.finish(); // close the home page
            openSignIn();
            return true;
        }
        else if(id == R.id.action_browse) { // gets you to the browse prediction page
            this.finish(); // close the home page
            openPrediction();
            return true;
        }
        else if(id == R.id.action_about) { // gets you to the about us page
            this.finish(); // close the home page
            openAboutUs();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Load the log in page */
    public void openSignIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /** Load the Browse Prediction Page */
    public void openPrediction() {
        Intent intent = new Intent(this, BrowsePrediction.class);
        startActivity(intent);
    }

    /** Load the about us page */
    public void openAboutUs() {
        Intent intent = new Intent(this, AboutUs.class);
        startActivity(intent);
    }

    /**
     * This is the action overflow menu if needed to use
     **/
/*    private void makeActionOverflowMenuShown() {
        //devices with hardware menu button (e.g. Samsung Note) don't show action overflow menu
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }*/
}