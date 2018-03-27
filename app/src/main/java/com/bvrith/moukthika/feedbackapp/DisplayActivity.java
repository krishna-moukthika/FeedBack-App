package com.bvrith.moukthika.feedbackapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class DisplayActivity extends AppCompatActivity {

    //ScrollView scrollview;
    boolean flag = false;
    TextView textview = (TextView)findViewById(R.id.lv);
    String data = "";
    String data1 = "";
    String data2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        if (Config.isNetworkStatusAvailable(getApplicationContext())) {
            new getFeedback().execute();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(DisplayActivity.this);
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
                    new getFeedback().execute();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DisplayActivity.this);
                    builder.setMessage("No Internet Connection").setTitle("Information");
                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }
            }
        });

    }

    public class getFeedback extends AsyncTask<Void, Void, Void> {

        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(DisplayActivity.this);
            dialog.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loader);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            //final String s1 = ed.getText().toString();
            //final String s2 = ed1.getText().toString();
            //final String s3 = ed2.getText().toString();
            String url = "http://123.176.47.87:3002/retrieveFeedbacks";
            //"?student_name=" + s1 + "&subject=" + s2 + "&feedback=" + s3;
            String jsonStr = sh.makeServiceCall(url);

            //Log.e(TAG, "Response from url: " + jsonStr);
            //Log.e(TAG, "URL : " + url);
            Log.e(TAG, "Got Response from url!");

            if (!jsonStr.equals("Nothing")) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray fbs = jsonObj.getJSONArray("feedbacks");
                    // looping through feedbacks
                    for (int i =0; i < fbs.length(); i++) {
                        JSONObject c = fbs.getJSONObject(i);
                        data = c.getString("name") + "\n";
                        data1 = c.getString("subject") + "\n";
                        data2 = c.getString("feedback`") + "\n";
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    flag = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                Log.v(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Couldn't get json from server.", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dialog.dismiss();
            textview.setText(data, TextView.BufferType.EDITABLE);
            //if (flag == false) {
              //  detailview();
            //}
        }

        //public void detailview() {

          //  scrollview = (ScrollView) findViewById(R.id.lv);
            //ScrollView extendedView = new ScrollView(this.getApplicationContext());
            //setContentView(extendedView);
        //}


    }
}

