package xyz.wheretopark.tabtest;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CyberHubParking extends AppCompatActivity {

    LinearLayout mImage;
    TextView mParkingName, mCurrentStatusDetails;
    String myString;
    SwipeRefreshLayout cyberSwipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cyberhub_parking);

        Bundle bundle = null;
        bundle = this.getIntent().getExtras();
        myString = bundle.getString("Name");

        mImage = (LinearLayout) findViewById(R.id.parkingImage);
        mParkingName = (TextView) findViewById(R.id.parkingName);
        mCurrentStatusDetails = (TextView) findViewById(R.id.currentStatusDetails);
        cyberSwipe =  (SwipeRefreshLayout) findViewById(R.id.parking_swipe);
        cyberSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        parseDataFromParse();
    }

    private void refresh() {
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                parseDataFromParse();
                cyberSwipe.setRefreshing(false);
            }
        },3000);
    }

    public void parseDataFromParse() {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("CyberHub");
        query.whereEqualTo("objectId", myString);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object != null) {
                    mParkingName.setText(object.getString("name"));
                    String slots = " slots available";
                    mCurrentStatusDetails.setText(object.getString("parking_count") + slots);
                }
            }
        });
    }
}