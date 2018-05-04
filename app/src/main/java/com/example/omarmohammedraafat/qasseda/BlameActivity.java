package com.example.omarmohammedraafat.qasseda;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BlameActivity extends AppCompatActivity {
    private TextView description;
    private ImageView imageView;
    private Button delete;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private DatabaseReference messagerefrence;
    private FirebaseDatabase firebaseDatabase;
    private ItemData item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blame);
        Intent intent = getIntent();
        item = (ItemData) intent.getSerializableExtra("data");
        // Toast.makeText(BlameActivity.this,item.getDescription(),Toast.LENGTH_SHORT).show();
        description = (TextView) findViewById(R.id.description);
        imageView = (ImageView) findViewById(R.id.image1);
        delete = (Button) findViewById(R.id.button);
        description.setText(item.getDescription());
        firebaseDatabase = FirebaseDatabase.getInstance();
        messagerefrence = firebaseDatabase.getReference("posts");

        firebaseAuth = FirebaseAuth.getInstance();
        boolean isPhoto = item.getImageResource() != null;
        if (isPhoto) {

            imageView.setVisibility(View.VISIBLE);
            Glide.with(imageView.getContext())
                    .load(item.getImageResource())
                    .into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
        }

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    String email = user.getEmail();
                    if (email.equals("majed6966@gmail.com")) {
                        delete.setVisibility(View.VISIBLE);
                    } else {
                        delete.setVisibility(View.GONE);

                    }

                } else {
                    delete.setVisibility(View.GONE);

                }

            }


        };
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messagerefrence.orderByChild("imageResource").equalTo(item.getImageResource())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    String clubkey = childSnapshot.getKey();
                                    DatabaseReference databaseReference=messagerefrence.child(clubkey);
                                    databaseReference.removeValue();
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }


                        });
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();

        firebaseAuth.addAuthStateListener(authStateListener);
    }
}