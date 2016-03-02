package xyz.wheretopark.tabtest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseUser;

public class first extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    //UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this);

        //userLocalStore = new UserLocalStore(this);

        int DELAY = 1000;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onAppStart();
               // Boolean status = userLocalStore.getIfUserLoggedIn();
               // Log.d("check",status.toString());
                 }
        }, DELAY);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    protected void onPreExecute() {
        // Create a progressdialog
        mProgressDialog = new ProgressDialog(first.this);
        // Set progressdialog title
        mProgressDialog.setTitle("Loading");
        // Set progressdialog message
        mProgressDialog.setMessage("No Internet...");
        mProgressDialog.setIndeterminate(false);
        // Show progressdialog
        mProgressDialog.show();
    }

    protected void onAppStart(){

        if(isNetworkConnected()){
                ParseUser currentUser = ParseUser.getCurrentUser();
                if(currentUser != null){
                    Intent i = new Intent(first.this, second.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }else{
                    Intent i = new Intent(first.this, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }
        else{
            // There are no active networks.
            onPreExecute();
        }
    }

    //Authentication via shared Preference. Maybe needed in future.
   /* private boolean authenticate(){
        return userLocalStore.getIfUserLoggedIn();

    }*/
}
