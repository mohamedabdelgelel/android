package com.example.omarmohammedraafat.qasseda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class RomanceActivity extends AppCompatActivity {

    private boolean IS_CLICKED = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_romance);

        final ArrayList<ItemData> itemData = new ArrayList<>();


        ItemAdapter adapter = new ItemAdapter(this, itemData);
        ListView listView =  (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);



    }
}
