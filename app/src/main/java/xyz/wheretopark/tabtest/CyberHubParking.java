package xyz.wheretopark.tabtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CyberHubParking extends AppCompatActivity {

    LinearLayout mImage;
    TextView mParkingName, mAddress, mAddressDetails, mCurrentStatus, mCurrentStatusDetails, mFair, mFairDetails;
    String myString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cyberhub_parking);

        Bundle bundle = null;
        bundle = this.getIntent().getExtras();
        myString = bundle.getString("Name");

        mImage = (LinearLayout) findViewById(R.id.parkingImage);
        mParkingName = (TextView) findViewById(R.id.parkingName);
        //mAddressDetails = (TextView) findViewById(R.id.addressDetails);
        mCurrentStatusDetails = (TextView) findViewById(R.id.currentStatusDetails);
        mFairDetails = (TextView) findViewById(R.id.fairDetails);
        parseDataFromParse();
    }

    public void parseDataFromParse() {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("CyberHub");
        query.whereEqualTo("objectId", myString);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object != null) {
                    mParkingName.setText(object.getString("name"));
                    //mAddressDetails.setText(object.getString("address"));
                    mCurrentStatusDetails.setText(object.getString("parking_count"));
                    //mFairDetails.setText(object.getString("fair"));
                }
            }
        });
    }
}