package nz.co.ipredict.phydano.appipredict;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;


public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        scrollTextEdit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_us, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                                    // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Code got from: http://stackoverflow.com/questions/10896839/scroll-inside-an-edittext-which-is-in-a-scrollview
     * This allows the edittext to be scrollable
     * */
    public void scrollTextEdit(){
        EditText dwEdit = (EditText) findViewById(R.id.message);
        dwEdit.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
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
    public void submitMessage(View view) {
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
