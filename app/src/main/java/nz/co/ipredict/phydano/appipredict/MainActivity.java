package nz.co.ipredict.phydano.appipredict;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // load the main activity layout
        backgroundImage(findViewById(R.id.draw)); // set the background image
        XmlReader reader = new XmlReader();
        reader.print();

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

    /** Go to the second activity which is the 'Trading' at the front screen */
    public void gotoSecond(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
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
        return super.onOptionsItemSelected(item);
    }

    /** Load the log in page */
    public void openSignIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
