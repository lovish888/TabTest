package xyz.wheretopark.tabtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AnonymousLogin extends AppCompatActivity implements View.OnClickListener {
    EditText mNameText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous_login);

        mNameText = (EditText) findViewById(R.id.input_name);
        Button letsGo = (Button) findViewById(R.id.letsGo);
        letsGo.setOnClickListener(this);
    }

    public void onClick(View v){
        String name = mNameText.getText().toString();
        if (name.isEmpty() || name.length() < 3) {
            mNameText.setError("at least 3 characters");
        } else {
            mNameText.setError(null);
            startActivity(new Intent(AnonymousLogin.this, second.class));
        }
    }
}
