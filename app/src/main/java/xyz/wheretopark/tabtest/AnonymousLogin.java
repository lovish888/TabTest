package xyz.wheretopark.tabtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AnonymousLogin extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous_login);

        Button letsGo = (Button) findViewById(R.id.letsGo);
        letsGo.setOnClickListener(this);
    }

    public void onClick(View v){
        startActivity(new Intent(AnonymousLogin.this,second.class));
    }
}
