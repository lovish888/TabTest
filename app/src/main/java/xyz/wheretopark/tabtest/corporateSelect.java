package xyz.wheretopark.tabtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class corporateSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corporate_select);

        //Parse.enableLocalDatastore(this);
        //Parse.initialize(this);

        Spinner spinner = (Spinner) findViewById(R.id.company_spin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.company_list, android.R.layout.simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {
                    final ParseQuery<ParseObject> query = ParseQuery.getQuery("Corporate");
                    final String company_name = parent.getItemAtPosition(position).toString();
                    query.whereEqualTo("company_name", company_name.toUpperCase());
                    query.getFirstInBackground(new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (object != null) {
                               Bundle bundle = new Bundle();
                                bundle.putString("Name", object.getObjectId()); //This is for a String
                                Intent i = new Intent(corporateSelect.this, companyShow.class);
                                i.putExtras(bundle);
                                startActivity(i);
                            }
                        }
                    });
                }
                else if (position == 0){
                    Toast.makeText(corporateSelect.this, "Select Company", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
