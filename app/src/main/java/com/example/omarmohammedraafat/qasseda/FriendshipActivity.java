package com.example.omarmohammedraafat.qasseda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FriendshipActivity extends AppCompatActivity {
    private ItemAdapter adapter;
    private ListView listView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference messagerefrence;
    ArrayList<ItemData> itemData;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendship);

        itemData = new ArrayList<ItemData>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        messagerefrence = firebaseDatabase.getReference().child("posts");
         intent = getIntent();

        adapter = new ItemAdapter(this, itemData);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

    }
    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
        adapter.clear();
        Query query = messagerefrence.orderByChild("department");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot post : dataSnapshot.getChildren()) {

                        ItemData message = post.getValue(ItemData.class);
                        adapter.add(message);

                        //   Toast.makeText(MainActivity.this,counter1+"a",Toast.LENGTH_SHORT).show();

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // position = dep_list.getPositionForView(view);
                //Object obj = parent.getItemAtPosition(position);

                ItemData obj = (ItemData) adapterView.getItemAtPosition(position);

                Intent intent = new Intent(FriendshipActivity.this, BlameActivity.class);
                intent.putExtra("data", obj);
                startActivity(intent);


            }
        });
    }
}
