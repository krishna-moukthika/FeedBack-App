package com.bvrith.moukthika.feedbackapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class giveFeedback extends AppCompatActivity {
    EditText ed, ed1, ed2;
    ListView listview;
    boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_feedback);
        ed = (EditText) findViewById(R.id.sn);
        ed1 = (EditText) findViewById(R.id.sub);
        ed2 = (EditText) findViewById(R.id.feed);

        if (Config.isNetworkStatusAvailable(getApplicationContext())) {
            new saveFeedback().execute();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(giveFeedback.this);
            builder.setMessage("No Internet Connection").setTitle("Information");
            builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });

            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
        Button fd = (Button) findViewById(R.id.submit);
        fd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Config.isNetworkStatusAvailable(getApplicationContext())) {
                    new saveFeedback().execute();
                    Toast.makeText(giveFeedback.this, "Feedback is saved!!", Toast.LENGTH_LONG).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(giveFeedback.this);
                    builder.setMessage("No Internet Connection").setTitle("Information");
                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }
                Intent intent = new Intent(giveFeedback.this, RetrieveActivity.class);
                startActivity(intent);
            }
        });

    }

    public class saveFeedback extends AsyncTask<Void, Void, Void> {

        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(giveFeedback.this);
            dialog.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loader);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            final String s1 = ed.getText().toString();
            final String s2 = ed1.getText().toString();
            final String s3 = ed2.getText().toString();
            String url = "http://123.176.47.87:3002/saveFeedback?student_name=" + s1 + "&subject=" + s2 + "&feedback=" + s3;
            String jsonStr = sh.makeServiceCall(url);

            //Log.e(TAG, "Response from url: " + jsonStr);
            //Log.e(TAG, "URL : " + url);
            Log.e(TAG, "Got Response from url!");

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dialog.dismiss();
            //if (flag == false) {
              //  detailview();
           // }
        }

        //public void detailview() {

            //listview = (ListView) findViewById(R.id.lv);
            //ArrayAdapter adapter = new ArrayAdapter<String>(giveFeedback.this, R.layout.activity_display);
            //listview.setAdapter(adapter);
        //}

    }

}