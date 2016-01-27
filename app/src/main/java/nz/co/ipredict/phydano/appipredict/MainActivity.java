package nz.co.ipredict.phydano.appipredict;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by phydano
 * This is the main activity in which is the Home Page of iPredict.
 * */
public class MainActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resizeImagesUsingBitMap(); // resize the image with Bitmap
        usersComment(); // load the users comments
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

    /**
     * Use bitmap to resize images
     * @param image the name of the image
     **/
    public void reduceImageSize(String image){
        Bitmap bmp;
        Bitmap bMapScaled;
        ImageView v;
        switch(image) {
            case "brain.png":
                v = (ImageView) findViewById(R.id.brain);
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.brain);
                bMapScaled = Bitmap.createScaledBitmap(bmp, 90, 100, true);
                v.setImageBitmap(bMapScaled);
                return;
            case "coin.png":
                v = (ImageView) findViewById(R.id.coin);
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.coin);
                bMapScaled = Bitmap.createScaledBitmap(bmp, 90, 100, true);
                v.setImageBitmap(bMapScaled);
                return;
            case "phone2.png":
                v = (ImageView) findViewById(R.id.phone2);
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.phone2);
                bMapScaled = Bitmap.createScaledBitmap(bmp, 90, 100, true);
                v.setImageBitmap(bMapScaled);
                return;
            case "whiteboard.png":
                v = (ImageView) findViewById(R.id.prediction);
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.whiteboard);
                bMapScaled = Bitmap.createScaledBitmap(bmp, 90, 100, true);
                v.setImageBitmap(bMapScaled);
                return;
            case "user_one.png":
                v = (ImageView) findViewById(R.id.userone);
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.user_one);
                bMapScaled = Bitmap.createScaledBitmap(bmp, 150, 140, true);
                v.setImageBitmap(bMapScaled);
                return;
            case "user_two.png":
                v = (ImageView) findViewById(R.id.usertwo);
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.user_two);
                bMapScaled = Bitmap.createScaledBitmap(bmp, 150, 140, true);
                v.setImageBitmap(bMapScaled);
                return;
            case "vic_logo.png":
                v = (ImageView) findViewById(R.id.viclogo);
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.vic_logo);
                bMapScaled = Bitmap.createScaledBitmap(bmp, 400, 150, true);
                v.setImageBitmap(bMapScaled);
                return;
            case "phone.png":
                v = (ImageView) findViewById(R.id.phone);
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.phone);
                bMapScaled = Bitmap.createScaledBitmap(bmp, 250, 400, true);
                v.setImageBitmap(bMapScaled);
                return;
            case "twitter_logo.png":
                v = (ImageView) findViewById(R.id.twitterlogo);
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.twitter_logo);
                bMapScaled = Bitmap.createScaledBitmap(bmp, 50, 50, true);
                v.setImageBitmap(bMapScaled);
        }
    }

    /**
     * Resize the image using the Bitmap and load the images programmatically rather
     * than in the XML Layout
     * */
    public void resizeImagesUsingBitMap(){
        String images[] = {"brain.png", "coin.png", "phone2.png", "whiteboard.png", "user_one.png",
                "user_two.png", "vic_logo.png", "phone.png", "twitter_logo.png"};
        for(String image : images) reduceImageSize(image);
        setBackgroundImage();
    }

    /**
     * Set the blue background image
     * */
    public void setBackgroundImage(){
        ImageView v = (ImageView) findViewById(R.id.firstBackground);
        v.setImageResource(R.drawable.blueportrait);
    }

    /**
     * Go to the second activity which is the 'Trading' at the front screen
     * */
    public void gotoBrowsePrediction(View view) {
        Intent intent = new Intent(this, BrowsePrediction.class);
        startActivity(intent);
    }

    /**
     * Go to iPredict Twitter page by loading a browser
     * */
    public void twitterPage(View view){
        Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/ipredictnz"));
        startActivity(openBrowser);
    }

    /**
     * Create the action menu
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Determine which of the action button is clicked and handle that event
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Action depends on what item you selected in the menu
        switch (item.getItemId()) {
            // Sign in page
            case R.id.action_signIn:
                return openSignIn();
            // Browse prediction page
            case R.id.action_browse:
                return openPrediction();
            // About us page
            case R.id.action_about:
                return openAboutUs();
            // Registration page for user who wants to sign up
            case R.id.action_register:
                return signup();
            case R.id.action_rankings:
                return ranking();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Load the log in page
     * */
    public boolean openSignIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        return true;
    }

    /**
     * Load the Browse Prediction Page
     * */
    public boolean openPrediction() {
        Intent intent = new Intent(this, BrowsePrediction.class);
        startActivity(intent);
        return true;
    }

    /**
     * Load the about us page
     * */
    public boolean openAboutUs() {
        Intent intent = new Intent(this, AboutUs.class);
        startActivity(intent);
        return true;
    }

    /**
     * Load the registration page
     * */
    public boolean signup(){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
        return true;
    }

    public boolean ranking(){
        Intent intent = new Intent(this, Ranking.class);
        startActivity(intent);
        return true;
    }

    /**
     * The app is completely close when pressed back button on the home page
     * Two back clicked to exit the app
     * Code from: http://stackoverflow.com/questions/8430805/android-clicking-twice-the-back-button-to-exit-activity
     * */
    @Override
    public void onBackPressed(){
        // close the app completely if clicked twice
        if (doubleBackToExitPressedOnce) {
            System.exit(0);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        // Given 2 seconds for users to pressed back to exit the app
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}