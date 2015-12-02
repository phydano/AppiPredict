package nz.co.ipredict.phydano.appipredict;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * This is the main activity in which is the Home Page of iPredict.
 **/
public class MainActivity extends AppCompatActivity {

    // Load upon opening the app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // load the main activity layout
        resizeImagesUsingBitMap();
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

    /** Use bitmap to resize images **/
    public void reduceImageSize(String image){
        Bitmap bmp;
        Bitmap bMapScaled;
        ImageView v;
        if(image.equals("brain.png") || image.equals("brain")) {
            v = (ImageView) findViewById(R.id.brain);
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.brain);
            bMapScaled = Bitmap.createScaledBitmap(bmp, 100, 100, true);
            v.setImageBitmap(bMapScaled);
        }
        else if(image.equals("coin.png") || image.equals("coin")) {
            v = (ImageView) findViewById(R.id.coin);
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.coin);
            bMapScaled = Bitmap.createScaledBitmap(bmp, 100, 100, true);
            v.setImageBitmap(bMapScaled);
        }
        else if(image.equals("phone2.png") || image.equals("phone2")) {
            v = (ImageView) findViewById(R.id.phone2);
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.phone2);
            bMapScaled = Bitmap.createScaledBitmap(bmp, 100, 100, true);
            v.setImageBitmap(bMapScaled);
        }
        else if(image.equals("whiteboard.png") || image.equals("whiteboard")) {
            v = (ImageView) findViewById(R.id.prediction);
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.whiteboard);
            bMapScaled = Bitmap.createScaledBitmap(bmp, 100, 100, true);
            v.setImageBitmap(bMapScaled);
        }
        else if(image.equals("user_one.png") || image.equals("user_one")) {
            v = (ImageView) findViewById(R.id.userone);
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.user_one);
            bMapScaled = Bitmap.createScaledBitmap(bmp, 150, 150, true);
            v.setImageBitmap(bMapScaled);
        }
        else if(image.equals("user_two.png") || image.equals("user_two")) {
            v = (ImageView) findViewById(R.id.usertwo);
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.user_two);
            bMapScaled = Bitmap.createScaledBitmap(bmp, 150, 150, true);
            v.setImageBitmap(bMapScaled);
        }
    }

    /**
     * Resize the image using the Bitmap and load the images programmatically rather than in the Layout
     * */
    public void resizeImagesUsingBitMap(){
        String test [] = {"brain.png", "coin.png", "phone2.png", "whiteboard.png", "user_one.png", "user_two.png"};
        for(int i=0; i<test.length; i++) reduceImageSize(test[i]);
        setImages();
    }

    /**
     * Other large images such as the background and the phone image
     * */
    public void setImages(){
        ImageView v = (ImageView) findViewById(R.id.phone);
        v.setImageResource(R.drawable.phone);
        v = (ImageView) findViewById(R.id.firstBackground);
        v.setImageResource(R.drawable.blueportrait);
    }

    /** Go to the second activity which is the 'Trading' at the front screen */
    public boolean gotoBrowsePrediction(View view) {
        this.finish();
        Intent intent = new Intent(this, BrowsePrediction.class);
        startActivity(intent);
        return true;
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
            return openSignIn();
        }
        else if(id == R.id.action_browse) { // gets you to the browse prediction page
            return openPrediction();
        }
        else if(id == R.id.action_about) { // gets you to the about us page
            return openAboutUs();
        }
        return super.onOptionsItemSelected(item);
    }

    /** Load the log in page */
    public boolean openSignIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
        return true;
    }

    /** Load the Browse Prediction Page */
    public boolean openPrediction() {
        Intent intent = new Intent(this, BrowsePrediction.class);
        startActivity(intent);
        this.finish();
        return true;
    }

    /** Load the about us page */
    public boolean openAboutUs() {
        Intent intent = new Intent(this, AboutUs.class);
        startActivity(intent);
        this.finish();
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart(); // Always call the superclass method first
    }

    @Override
    protected void onStart() {
        super.onStart();
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