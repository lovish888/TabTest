package xyz.wheretopark.tabtest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;

public class first extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        int DELAY = 1000;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isNetworkConnected();
            }
        }, DELAY);
    }
        private boolean isNetworkConnected() {

            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo == null) {
                // There are no active networks.
                onPreExecute();
                return false;
            } else {
                Intent intent = new Intent(first.this, second.class);
                startActivity(intent);
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
}
