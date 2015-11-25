package nz.co.ipredict.phydano.appipredict;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;
import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // load the main activity layout
        usersComment();
    }

    /**
     * The user comment in the home page
     **/
    public void usersComment(){
        String userCommentOne = "\"Buying low and selling high\"";
        String userCommentTwo = "\"The prediction is real close to its perfection\"";
        String username = "<font color='#0026FF'>iPredict Trader</font>";
        TextView t1 = (TextView) findViewById(R.id.user1);
        TextView t2 = (TextView) findViewById(R.id.user2);
        t1.setText(Html.fromHtml(userCommentOne + "<br/>" + username));
        t2.setText(Html.fromHtml(userCommentTwo + "<br/>" + username));
    }

    /** Set the background image depends on portrait or landscape */
    public void backgroundImage(View view){
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            view.setBackgroundResource (R.drawable.itemsblue);
        } else {
            view.setBackgroundResource (R.drawable.blueportrait);
        }
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signIn) {
            openSignIn();
            return true;
        }
        else if(id == R.id.action_browse) {
            openPrediction();
            return true;
        }
        else if(id == R.id.action_about) {
            finish();
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

    /** This is the action overflow menu */
    private void makeActionOverflowMenuShown() {
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
    }
}
