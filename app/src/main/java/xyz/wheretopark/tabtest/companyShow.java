package xyz.wheretopark.tabtest;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;

public class companyShow extends AppCompatActivity {

    static TextView tv;
    static ListView lv1;
    static ListView lv2;
    String[] park_name;
    String[] park_status;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_show);



        Bundle bundle = null;
        bundle = this.getIntent().getExtras();
        String myString = bundle.getString("Name");
        tv=(TextView)findViewById(R.id.company_name);
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Corporate");
        query.whereEqualTo("objectId", myString);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object != null) {
                    tv.setText(object.getString("company_name"));
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

    }



}
