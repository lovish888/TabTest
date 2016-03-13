package xyz.wheretopark.tabtest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import com.parse.ParseUser;

import java.util.Calendar;

public class first extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    //UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //userLocalStore = new UserLocalStore(this);

        int DELAY = 2500;

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
                AlertDialog.Builder builder = new AlertDialog.Builder(first.this);
                builder.setMessage("Sorry no Internet connectivity detected. Please reconnect and try again.");
                builder.setTitle("No Internet Connection");
                builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isNetworkConnected();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            return false;
        } else {
            return true;
        }
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
    }
    //Authentication via shared Preference. Maybe needed in future.
   /* private boolean authenticate(){
        return userLocalStore.getIfUserLoggedIn();

    }*/
    @Override
    public void onBackPressed() {
        System.exit(0);
    }
}
