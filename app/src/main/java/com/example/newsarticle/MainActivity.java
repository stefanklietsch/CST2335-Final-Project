package com.example.newsarticle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText etext;
    ProgressBar sprogress;
    Button search;
    ListView aList;
    String s = null;
    ArrayList<Message> objects;
    String title = null;
    String wurl = null;
    String Section = null;
    MyListAdapter adpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etext = findViewById(R.id.answer);
        sprogress = findViewById(R.id.progress);
        search = findViewById(R.id.search);
        aList = findViewById(R.id.list);
        objects = new ArrayList<Message>();
        searchsite req = new searchsite();
        adpt = new MyListAdapter(objects,this);
        aList.setAdapter(adpt);
        s = etext.getText().toString();

        search.setOnClickListener(clk -> {
            req.execute("https://content.guardianapis.com/search?api-key=1fb36b70-1588-4259-b703-2570ea1fac6a&q=Tesla");
            Toast.makeText(this,"search in progress",Toast.LENGTH_LONG);
        });
        aList.setOnItemLongClickListener( (p, b, pos, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("A title")

                    //What is the message:
                    .setMessage("Do you want to save this?")

                    //what the Yes button does:
                    .setPositiveButton("Yes", (click, arg) -> {
                        String temp = objects.get(pos).toString();

                        Intent intent = new Intent(MainActivity.this,save.class);

                        intent.putExtra("citem",temp);
                        startActivity(intent);
                    })
                    //What the No button does:
                    .setNegativeButton("No", (click, arg) -> { })

                    //Show the dialog
                    .create().show();
            return true;
        });
    }
    private class searchsite extends AsyncTask<String,Void, List<Message>> {
        @Override
        protected void onPostExecute(List<Message> result) {
            adpt.setItemList(result);
            adpt.notifyDataSetChanged();
        }
        @Override
        protected List<Message> doInBackground(String... args) {
            List<Message> lMessage = new ArrayList<>();
            try {
                //connects to server
                URL url = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data imput
                InputStream response = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString(); //result is the whole string
                JSONObject jObject = new JSONObject(result);

                JSONObject jstart = jObject.getJSONObject("response");
                JSONArray jArray = jstart.getJSONArray("results");
                for(int i = 0;i<jArray.length();i++){
                    lMessage.add(convertNews(jArray.getJSONObject(i)));
                }
                return lMessage;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        private Message convertNews (JSONObject jo) throws JSONException{
            title = jo.getString("webTitle");
            Section = jo.getString("sectionName");
            wurl = jo.getString("webUrl");

            return new Message(title,Section,wurl);
        }
    }
}
