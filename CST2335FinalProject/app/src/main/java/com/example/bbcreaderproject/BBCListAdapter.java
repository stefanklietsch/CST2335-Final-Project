package com.example.bbcreaderproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bbcreaderproject.dummy.WebHTTP;

import java.util.ArrayList;

public class BBCListAdapter extends MainActivity {
    public MainActivity act = new MainActivity();
    public ArrayList<String> list = new ArrayList<String>();


    public BBCListAdapter(Context context, int num, ArrayList<WebHTTP> messages){
        super(context, num, messages);
    }

    public int getCount(){
        return list.size();
    }

    public Object getItem(int position){
        return list.get(position);
    }

    public long getItemId(int position){
        return (long) position;
    }

    public View getView(int position, View view, ViewGroup parent){

        view = act.getLayoutInflater().inflate(R.layout.simple_list_item_1, parent, false);

        return view;
    }
}
