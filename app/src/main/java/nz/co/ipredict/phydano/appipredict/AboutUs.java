package nz.co.ipredict.phydano.appipredict;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.gmail.GmailScopes;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.codec.binary.Base64;

public class AboutUs extends AppCompatActivity {
    GoogleAccountCredential mCredential;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = { GmailScopes.GMAIL_LABELS };
    private com.google.api.services.gmail.Gmail mService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        scrollTextEdit();

        // Initialize credentials and service object.
        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff())
                .setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_us, menu);
        return super.onCreateOptionsMenu(menu);

    }
*/
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

/*    public void submitMessage(View view){
        GoogleAccountCredential mCredential;
        String userId = "464470648798-9to9fcfks8ndh8fu64njsehs31q2agav.apps.googleusercontent.com";
        try {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
            mCredential = GoogleAccountCredential.usingOAuth2(
                    getApplicationContext(), Arrays.asList(SCOPES))
                    .setBackOff(new ExponentialBackOff())
                    .setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));

            mService = new com.google.api.services.gmail.Gmail.Builder(
                    transport, jsonFactory, mCredential)
                    .setApplicationName("Gmail API")
                    .build();

            sendMessage(mService, userId, createEmail("dano@ipredict.co.nz",
                    "dano@ipredict.co.nz", "Hello", "Testing"));

        }catch(MessagingException | IOException e){
            System.out.println("Error: " + e.toString());
        }
    }*/

    /**
     * Create an Email using the Java API imported
     * Code is from: https://developers.google.com/gmail/api/guides/sending
     * */
/*    public static MimeMessage createEmail (String to, String from, String subject,
                                           String bodyText) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);

        // Setting the sender, receiver addr,add subject and text
        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        System.out.println("These are the recipients: " + email.getAllRecipients());
        return email;
    }*/

    /**
     * Sending Email
     * */
/*    public static void sendMessage(Gmail service, String userId, MimeMessage email)
            throws MessagingException, IOException{
        Message message = createMessageWithEmail(email);
        message = service.users().messages().send(userId, message).execute();

        System.out.println("Message id: " + message.getId());
        System.out.println(message.toPrettyString());
    }*/

    /**
     * Encode the MimeMessage
     * */
/*    public static Message createMessageWithEmail(MimeMessage email)
            throws MessagingException, IOException{
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        email.writeTo(bytes);
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes.toByteArray());
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }*/
}