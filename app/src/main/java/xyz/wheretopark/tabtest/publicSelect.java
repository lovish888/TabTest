package xyz.wheretopark.tabtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class publicSelect extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_select);

        Spinner spinner = (Spinner) findViewById(R.id.cyberhub_spin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cyberhub_parking_select, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    final ParseQuery<ParseObject> query = ParseQuery.getQuery("Public");
                    final String parking_name = parent.getItemAtPosition(position).toString();
                    query.whereEqualTo("parking_name", parking_name.toUpperCase());
                    query.getFirstInBackground(new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (object == null) {
                               // Bundle bundle = new Bundle();
                               // bundle.putString("Name", object.getObjectId()); //This is for a String
                                Intent i = new Intent(publicSelect.this, CyberHubParking.class);
                               // i.putExtras(bundle);
                                startActivity(i);
                            }
                        }
                    });
                }
                else if (position == 0) {
                    Toast.makeText(publicSelect.this, "Select Company", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
