package com.bvrith.moukthika.feedbackapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class RetrieveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);
        Button rd = (Button) findViewById(R.id.rd);
        rd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RetrieveActivity.this, DisplayActivity.class));
            }
        });
    }

    public void back(View view) {
        Intent intent = new Intent(RetrieveActivity.this, MainActivity.class);
        startActivity(intent);
    }

//    public void retrieve(View view) {
  //      Intent intent = new Intent(RetrieveActivity.this, DisplayActivity.class);
    //    startActivity(intent);
    //}
}
