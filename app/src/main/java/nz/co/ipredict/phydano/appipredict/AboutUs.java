package nz.co.ipredict.phydano.appipredict;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

/**
 * Created by phydano
 * This is the about us page which is the iPredict info page on the company
 **/
public class AboutUs extends AppCompatActivity {
    ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        resizeImagesUsingBitMap(); // Scaled the images
        scrollTextEdit(); // make text scrollable in text field when the text is overflow
        mScrollView = (ScrollView) findViewById(R.id.aboutUsScrollView);
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
        // restore the scroll view position
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
     * Scaled the images using Bitmap to reduce the image size
     * @param image the image we want to scale
     * */
    public void reduceImageSize(String image){
        Bitmap bmp; // create bitmap
        Bitmap bMapScaled; // scaled
        ImageView v; // image we want to scale;

        switch(image){
            case "donald_w300px_h350px.png":
                v = (ImageView) findViewById(R.id.don);
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.donald_w300px_h350px);
                bMapScaled = Bitmap.createScaledBitmap(bmp, 350, 370, true);
                v.setImageBitmap(bMapScaled);
                return;
            case "emily_w300px_h350px.png":
                v = (ImageView) findViewById(R.id.emily);
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.emily_w300px_h350px);
                bMapScaled = Bitmap.createScaledBitmap(bmp, 350, 370, true);
                v.setImageBitmap(bMapScaled);
                return;
            case "ian_w300px_h350px.png":
                v = (ImageView) findViewById(R.id.ian);
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ian_w300px_h350px);
                bMapScaled = Bitmap.createScaledBitmap(bmp, 200, 220, true);
                v.setImageBitmap(bMapScaled);
                return;
            case "kate_w300px_h350px.png":
                v = (ImageView) findViewById(R.id.kate);
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.kate_w300px_h350px);
                bMapScaled = Bitmap.createScaledBitmap(bmp, 200, 220, true);
                v.setImageBitmap(bMapScaled);
                return;
            case "lewis_w300px_h350px.png":
                v = (ImageView) findViewById(R.id.lewis);
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.lewis_w300px_h350px);
                bMapScaled = Bitmap.createScaledBitmap(bmp, 200, 220, true);
                v.setImageBitmap(bMapScaled);
        }
    }

    /**
     * Given set of images and use that to reduce the image size
     * */
    public void resizeImagesUsingBitMap(){
        String images [] = {"donald_w300px_h350px.png", "emily_w300px_h350px.png",
                "ian_w300px_h350px.png", "kate_w300px_h350px.png", "lewis_w300px_h350px.png"};
        for(String image : images) reduceImageSize(image);
        setBackgroundImage();
    }

    /**
     * Set the background images programmatically to reduce the memory usage
     * */
    public void setBackgroundImage(){
        ImageView v = (ImageView) findViewById(R.id.firstBackgroundAboutUs);
        v.setImageResource(R.drawable.blueportrait);
        v = (ImageView) findViewById(R.id.thirdLinear);
        v.setImageResource(R.drawable.blueportrait);
    }

    /**
     * Code got from: http://stackoverflow.com/questions/10896839/scroll-inside-an-edittext-which-is-in-a-scrollview
     * This allows the edittext to be scrollable once you clicked on it and typed something in
     * that leads to overflow in text.
     * */
    public void scrollTextEdit(){

        final EditText dwEdit = (EditText) findViewById(R.id.message);

        dwEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dwEdit.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View view, MotionEvent event) {
                        if (view.getId() == R.id.message) {
                            // When you keep scrolling
                            view.getParent().requestDisallowInterceptTouchEvent(true);
                            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                                // Once you stopped scrolling
                                case MotionEvent.ACTION_UP:
                                    view.getParent().requestDisallowInterceptTouchEvent(false);
                                    break;
                            }
                        }
                        return false;
                    }
                });
            }
        });
    }

    /**
     * This method read the text from the editText and send over to the help@ipredict.co.nz
     * Here are some useful sites to help with this:
     * Code from: http://stackoverflow.com/questions/2020088/sending-email-in-android-using-javamail-api-without-using-the-default-built-in-a
     * Code from: http://stackoverflow.com/questions/6817616/open-gmail-message-intent
     * @param view for handling drawing
     * */
    public void submitMessage (View view) {
        // Edit text fields taking in the message and subject field (like email address)
        EditText emailAddr = (EditText) findViewById(R.id.email_address);
        EditText messageContent = (EditText) findViewById(R.id.message);

        // Grab the values entered by the users
        String emailAddrValue = emailAddr.getText().toString();
        String messageContentValue = messageContent.getText().toString();

        // Launch the Gmail App
        Intent sendIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
        // If the user have a Gmail app installed
        if (sendIntent != null) {
            // We found the activity now start the activity
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            sendIntent.setType("plain/text");
            sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
            // We need to pass these info to the Gmail app.
            sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"help@ipredict.co.nz"});
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "RE: " + emailAddrValue);
            sendIntent.putExtra(Intent.EXTRA_TEXT, messageContentValue);
            startActivity(sendIntent);
        }
        // If the app is not installed, take users to the market
        else {
            sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            sendIntent.setData(Uri.parse("market://details?id=" + "com.google.android.gm"));
            startActivity(sendIntent);
        }
    }
}