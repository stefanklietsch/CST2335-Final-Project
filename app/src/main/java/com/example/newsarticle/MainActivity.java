package com.example.newsarticle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText etext;
    ProgressBar sprogress;
    Button search;
    ListView aList;
    String s = null;
    MyListAdapter myAdapter;
    ArrayList<Message> objects = new ArrayList<>();
    String strurl = "https://content.guardianapis.com/search?api-key=1fb36b70-1588-4259-b703-2570ea1fac6a&q=Tesla";
    String title = null;
    String Url = null;
    String Section = null;
    int sectionid= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etext = findViewById(R.id.answer);
        sprogress = findViewById(R.id.progress);
        search = findViewById(R.id.search);
        aList = findViewById(R.id.list);
        searchsite req = new searchsite();

        DatabaseHelper dbOpener = new DatabaseHelper(this);
        objects = dbOpener.getAllMessage();

        myAdapter = new MyListAdapter();
        aList.setAdapter(myAdapter);

        search.setOnClickListener(clk -> {
            s = etext.getText().toString();
            req.execute();
            Message message = new Message(title,sectionid,Section,"",0);
            message.setId(dbOpener.insert(message));
            objects.add(message);
            myAdapter.notifyDataSetChanged();

        });
    }
    private class searchsite extends AsyncTask< String, Integer, String>
    {
        @Override
        protected String doInBackground(String... args) {
            try {

                //create a URL object of what server to contact:
                URL url = new URL(strurl);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //wait for data:
                InputStream response = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                if(response.equals(s)) {
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    String result = sb.toString();
                }



            }
            catch (Exception e)
            {
                Log.e("Error", e.getMessage());
            }
            return null;
        }
    }
    String streamToString(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String line;
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        // Close stream
        if (stream != null) {
            stream.close();
        }
        return result;
    }

    private class MyListAdapter extends BaseAdapter {

        public int getCount() {  return objects.size();  } //This function tells how many objects to show

        public Message getItem(int position) { return objects.get(position);  }  //This returns the string at position p

        public long getItemId(int p) { return p; } //This returns the database id of the item at position p

        public View getView(int p, View recycled, ViewGroup parent)
        {
            View thisRow = null;
            if (objects.get(p).getSectionID() == Message.SEARCH) {
                thisRow = getLayoutInflater().inflate(R.layout.activity_main_search, null);
            }

            TextView title = thisRow.findViewById(R.id.text1);
            title.setText(objects.get(p).getTitle());

            TextView section = thisRow.findViewById(R.id.text2);
            section.setText(objects.get(p).getSectionName());

            TextView urls = thisRow.findViewById(R.id.text3);
            urls.setText(objects.get(p).geturl());

            return thisRow;
        }
    }
}
