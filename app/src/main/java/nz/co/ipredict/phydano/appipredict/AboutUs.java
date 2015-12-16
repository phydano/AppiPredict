package nz.co.ipredict.phydano.appipredict;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by phydano
 * This is the about us page which is the iPredict info page on the company
 **/
public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        resizeImagesUsingBitMap(); // Scaled the images
        scrollTextEdit(); // make text scrollable in text field when its overflow
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

    /***/
    public void reduceImageSize(String image){
        Bitmap bmp;
        Bitmap bMapScaled;
        ImageView v;

        switch(image){
            case "don_w300px_h350px.png":
                v = (ImageView) findViewById(R.id.don);
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.don_w300px_h350px);
                bMapScaled = Bitmap.createScaledBitmap(bmp, 330, 350, true);
                v.setImageBitmap(bMapScaled);
                return;
            case "emily_w300px_h350px.png":
                v = (ImageView) findViewById(R.id.emily);
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.emily_w300px_h350px);
                bMapScaled = Bitmap.createScaledBitmap(bmp, 330, 350, true);
                v.setImageBitmap(bMapScaled);
                return;
            case "ian_w300px_h350px.png":
                v = (ImageView) findViewById(R.id.ian);
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ian_w300px_h350px);
                bMapScaled = Bitmap.createScaledBitmap(bmp, 180, 200, true);
                v.setImageBitmap(bMapScaled);
                return;
            case "kate_w300px_h350px.png":
                v = (ImageView) findViewById(R.id.kate);
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.kate_w300px_h350px);
                bMapScaled = Bitmap.createScaledBitmap(bmp, 180, 200, true);
                v.setImageBitmap(bMapScaled);
                return;
            case "lewis_w300px_h350px.png":
                v = (ImageView) findViewById(R.id.lewis);
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.lewis_w300px_h350px);
                bMapScaled = Bitmap.createScaledBitmap(bmp, 180, 200, true);
                v.setImageBitmap(bMapScaled);
        }
    }

    public void resizeImagesUsingBitMap(){
        String images [] = {"don_w300px_h350px.png", "emily_w300px_h350px.png",
                "ian_w300px_h350px.png", "kate_w300px_h350px.png", "lewis_w300px_h350px.png"};
        for(String image : images) reduceImageSize(image);
        setBackgroundImage();
    }

    public void setBackgroundImage(){
        ImageView v = (ImageView) findViewById(R.id.firstBackgroundAboutUs);
        v.setImageResource(R.drawable.blueportrait);
        v = (ImageView) findViewById(R.id.thirdLinear);
        v.setImageResource(R.drawable.blueportrait);
    }

    /**
     * Code got from: http://stackoverflow.com/questions/10896839/scroll-inside-an-edittext-which-is-in-a-scrollview
     * This allows the edittext to be scrollable
     * */
    public void scrollTextEdit(){
        EditText dwEdit = (EditText) findViewById(R.id.message);
        dwEdit.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                if (view.getId() ==R.id.message) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction()&MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });
    }

    /**
     * This method read the text from the editText and send over to the help@ipredict.co.nz
     * Here are some useful sites to help with this:
     * http://stackoverflow.com/questions/2020088/sending-email-in-android-using-javamail-api-without-using-the-default-built-in-a
     *
     * */
    public void submitMessage (View view) {
        //Todo: Here should send the feedback to help@ipredict.co.nz.
        EditText emailAddr = (EditText) findViewById(R.id.email_address);
        EditText messageContent = (EditText) findViewById(R.id.message);

        String emailAddrValue = emailAddr.getText().toString();
        String messageContentValue = messageContent.getText().toString();

        // Launch the Gmail App
        //Todo: The problem here it launches Gmail App not under the same iPredict App.
        //Todo: Don't forget to change the Email Addr to help@ipredict.co.nz
        /**
         * Code from: http://stackoverflow.com/questions/6817616/open-gmail-message-intent
         * */
        Intent sendIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
        if (sendIntent != null) {
            // We found the activity now start the activity
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            sendIntent.setType("plain/text");
            sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
            sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"dano@ipredict.co.nz"});
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "RE: " + emailAddrValue);
            sendIntent.putExtra(Intent.EXTRA_TEXT, messageContentValue);
            startActivity(sendIntent);
        } else {
            // Bring user to the market or let them choose an app?
            sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            sendIntent.setData(Uri.parse("market://details?id=" + "com.google.android.gm"));
            startActivity(sendIntent);
        }
    }
}