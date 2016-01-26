package xyz.wheretopark.tabtest;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.lang.*;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;


public class companyShow extends AppCompatActivity {

    static TextView tv;
    static TextView tv1;
    static ListView lv1;
    static ListView lv2;
    String[] park_name;
    String[] park_status;
    String delete;
    String d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_show);
        Bundle bundle = null;
        bundle = this.getIntent().getExtras();
        String myString = bundle.getString("Name");
        tv=(TextView)findViewById(R.id.company_name);
        tv1=(TextView)findViewById(R.id.textView4);
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Corporate");
        query.whereEqualTo("objectId", myString);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object != null) {
                    tv.setText(object.getString("company_name"));
                    d=object.getUpdatedAt().toString().substring(20);
                    delete=object.getUpdatedAt().toString().replace(d,"");
                    tv1.setText("Last Updated: "+delete);
                    ArrayList<String> parking_name = (ArrayList<String>) object.get("parking_name");
                    ArrayList<String> park_show_status = (ArrayList<String>) object.get("park_show_status");
                    park_name = parking_name.toArray(new String[parking_name.size()]);
                    park_status = park_show_status.toArray(new String[park_show_status.size()]);
                    create(park_name, park_status);
                }
            }
        });
    }

    void create(String[] park_name, String[] park_status) {

        lv1 = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> ad = new ArrayAdapter<String>(this, R.layout.item_layout, park_name);
        lv1.setAdapter(ad);
        lv2 = (ListView) findViewById(R.id.listView2);
        ArrayAdapter<String> ad2 = new ArrayAdapter<String>(this, R.layout.item_layout,park_status);
        lv2.setAdapter(ad2);
        Toast.makeText(companyShow.this, "You may need to wait as some of the tokens for these slots might be at the exit points.", Toast.LENGTH_LONG).show();
    }
}
