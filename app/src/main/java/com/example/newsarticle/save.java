package com.example.newsarticle;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class save extends AppCompatActivity {
    TextView save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_save);
        save = findViewById(R.id.save);

        String temp = getIntent().getStringExtra("citem");
        save.setText(temp);

    }
}
