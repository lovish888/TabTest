package xyz.wheretopark.tabtest;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class companyShow extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    static TextView tv;
    static TextView tv1;
    static ListView listView;
    String[] park_name;
    String[] park_status;
    String myString;
    private SwipeRefreshLayout swipeRefreshLayout;
    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    SimpleDateFormat format,format1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_show);
        Bundle bundle = null;
        bundle = this.getIntent().getExtras();
        myString = bundle.getString("Name");
        tv = (TextView) findViewById(R.id.company_name);
        tv1 = (TextView) findViewById(R.id.textView4);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        format = new SimpleDateFormat("E MMM dd hh:mm:ss a");
        format1 = new SimpleDateFormat("E MMM dd ");
        parseDataFromParse();

    }

    public void parseDataFromParse() {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Corporate");
        query.whereEqualTo("objectId", myString);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object != null) {
                    tv.setText(object.getString("company_name"));
                if(hour>=7 && hour<16) {
                    String time = String.format("Last Updated: " + format.format(calendar.getTime()));
                    tv1.setText(time);
                }else{
                    String timeOver = String.format("Last Updated: " + format1.format(calendar.getTime()) + "04:00:00 PM");
                    tv1.setText(timeOver);
                }
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

        listView = (ListView) findViewById(R.id.listView);
        myAdapter adapter = new myAdapter(this,park_name,park_status);
        listView.setAdapter(adapter);

        Toast.makeText(companyShow.this, "You may need to wait as some of the tokens for these slots might be at the exit points.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        parseDataFromParse();
    }
}

class myAdapter extends ArrayAdapter<String>{
    Context context;
    String[] park_name;
    String[] park_status;

    myAdapter(Context context, String[] park_name, String[] park_status){
        super(context,R.layout.item_layout,R.id.textView5,park_name);
        this.context = context;
        this.park_name = park_name;
        this.park_status = park_status;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.item_layout,parent,false);

        TextView tv1 = (TextView) row.findViewById(R.id.textView5);
        TextView tv2 = (TextView) row.findViewById(R.id.textView6);
        tv1.setText(park_name[position]);
        tv2.setText(park_status[position]);
        return row;
    }
}
