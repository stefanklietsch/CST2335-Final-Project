package com.example.bbcreaderproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bbcreaderproject.dummy.WebHTTP;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public TextView titleText;
    public TextView descriptionText;
    public TextView dateText;
    public TextView weblinkText;
    public WebHTTP articles;

    public MainActivity(Context context, int num, ArrayList<WebHTTP> art) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.favourites_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.favourites, menu);
        return true;
    }

    private void setBBCList(){

        ListView BBC_list = (ListView) findViewById(R.id.articles_list);
        final ArrayAdapter<WebHTTP> adapter = new ArrayAdapter(this,articles);
        articles_list.setAdapter(adapter);

        articles_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actions_add:
                Toast.makeText(this, "Added to favourites", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.actions_open:
                Toast.makeText(this, "Favourites opened", Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }

        return true;
    }

    WebHTTP web = new WebHTTP();
    web.execute("http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml");

}
