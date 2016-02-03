package nz.co.ipredict.phydano.appipredict;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

/**
 * Created by phydano
 * This is a login activity where users can login
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

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
     * If the login details are correct, user should redirect to their portfolio page
     * Note: this is just using the dummy data
     * */
    public void logIn(View view) {
        AutoCompleteTextView emailAddr = (AutoCompleteTextView) findViewById(R.id.email);
        String email = emailAddr.getText().toString(); // this is the email addr
        EditText myPassword = (EditText) findViewById(R.id.password);
        String password = myPassword.getText().toString(); // this is the password

        /** Do the checking here before jump to the portfolio page */
        Intent intent = new Intent(this, myPortfolio.class);
        startActivity(intent);
    }
}
