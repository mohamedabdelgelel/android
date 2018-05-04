package com.example.omarmohammedraafat.qasseda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ComplementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complement);
        final ArrayList<ItemData> itemData = new ArrayList<ItemData>();


        ItemAdapter adapter = new ItemAdapter(this, itemData);
        ListView listView =  (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}
