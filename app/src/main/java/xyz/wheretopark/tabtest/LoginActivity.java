package xyz.wheretopark.tabtest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    EditText mEmailText;
    EditText mPasswordText;
    Button mLoginButton;
    TextView mSignUpLink;
    //TextView mForgotPassword;
    //UserLocalStore userLocalSore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         mEmailText = (EditText) findViewById(R.id.input_email);
         mPasswordText = (EditText) findViewById(R.id.input_password);
         mLoginButton = (Button) findViewById(R.id.btn_login);
         mSignUpLink = (TextView) findViewById(R.id.link_signup);
         //mForgotPassword = (TextView) findViewById(R.id.forget_password);

        //userLocalSore = new UserLocalStore(this);

        mLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        mSignUpLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        /*mForgotPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO : Reset Password.
            }
        });*/
    }

    public void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }

        mLoginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);                       //For unknown loading time.
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        // TODO: Login Authentication.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onLoginSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    @Override
    public void onBackPressed() {
        // Disables going back to the MainActivity.
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {

        String email = mEmailText.getText().toString().trim();
        String password = mPasswordText.getText().toString().trim();

        //Checking whether the User exists or not through parse
        ParseUser.logInInBackground(email, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user != null){
                    Intent intent = new Intent(LoginActivity.this, second.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                   //userLocalSore.setUserLoggedIn(true);

                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage(e.getMessage());
                    builder.setTitle("OOPS!!!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        //Storing User data locally on the device. Maybe needed in future.
        /*User user = new User(name,password);
        userLocalSore.storeUserData(user);

        mLoginButton.setEnabled(true);*/
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        mLoginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailText.setError("enter a valid email address");
            valid = false;
        } else {
            mEmailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 30) {
            mPasswordText.setError("between 4 and 30 alphanumeric characters");
            valid = false;
        } else {
            mPasswordText.setError(null);
        }

        return valid;
    }
}
