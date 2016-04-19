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

import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.ParseException;

public class SignupActivity extends AppCompatActivity {

    EditText mNameText;
    EditText mEmailText;
    EditText mPasswordText;
    EditText mRePasswordText;
    Button mSignUpButton;
    TextView mLoginLink;
    //UserLocalStore userLocalStore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mNameText = (EditText) findViewById(R.id.input_name);
        mEmailText = (EditText) findViewById(R.id.input_email);
        mPasswordText = (EditText) findViewById(R.id.input_password);
        mRePasswordText = (EditText) findViewById(R.id.input_repassword);
        mSignUpButton = (Button) findViewById(R.id.btn_signup);
        mLoginLink = (TextView) findViewById(R.id.link_login);

        //userLocalStore = new UserLocalStore(this);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        mLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signup() {
            if (!validate()) {
                onSignupFailed();
                return;
            }

            mSignUpButton.setEnabled(false);

            final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating Account...");
            progressDialog.show();

        // TODO: Implement signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onSignupSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onSignupSuccess() {

        String name = mNameText.getText().toString().trim();
        String email = mEmailText.getText().toString().trim();
        String password = mPasswordText.getText().toString().trim();

        //CREATING NEW USER

        //Storing User data locally on the device. Maybe needed in future.

       /* User registeredUser = new User(name,email,password);
        userLocalStore.storeUserData(registeredUser);
        mSignUpButton.setEnabled(true);*/

        //Storing User data on Parse.

        ParseUser user = new ParseUser();
        user.setUsername(email);
        user.setEmail(email);
        user.setPassword(password);
        user.put("Name",name);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Intent intent = new Intent(SignupActivity.this, second.class);
                    //Deleting navigation history to prevent user from going back.
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    //userLocalStore.setUserLoggedIn(true);

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
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
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_LONG).show();
        mSignUpButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = mNameText.getText().toString();
        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();
        String repassword = mRePasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            mNameText.setError("at least 3 characters");
            valid = false;
        } else {
            mNameText.setError(null);
        }

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
        if(repassword == password){
            mPasswordText.setError(null);
        }else{
            mRePasswordText.setText("Password didn't match");
        }

        return valid;
    }
}