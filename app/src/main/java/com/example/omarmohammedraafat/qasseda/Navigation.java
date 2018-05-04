package com.example.omarmohammedraafat.qasseda;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference messagerefrence;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private static  int RC_SIGN_IN=1;
    private FirebaseStorage firebaseStorag;
    private StorageReference chatRefrence;
    private ChildEventListener mchildevent;
    private int counter1=0;
    private int counter2=0;
    private int counter3=0;
    private int counter4=0;
    private int counter5=0;
    private int counter6=0;
    private int counter7=0;

    private DepAdapter depAdapter;
    private ArrayList<Dep_Items> dep_itemses;
    private ListView dep_list;
    private FirebaseUser user;
    Menu u;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
setTitle("قصائد واشعار");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        firebaseDatabase =FirebaseDatabase.getInstance();
        firebaseAuth =FirebaseAuth.getInstance();
        messagerefrence=firebaseDatabase.getReference("posts");
         u=navigationView.getMenu();


        dep_itemses = new ArrayList<>();
        dep_list = (ListView) findViewById(R.id.nav_list);

        user = firebaseAuth.getCurrentUser();
        if(user!=null)
        {
            MenuItem item=u.findItem(R.id.nav_share);
            item.setVisible(false);
            if (user.getEmail().equals("majed6966@gmail.com"))
            {
                MenuItem itema=u.findItem(R.id.nav_ADMIN);
                itema.setVisible(true);
            }
            else {
                MenuItem itema=u.findItem(R.id.nav_ADMIN);
                itema.setVisible(false);
            }
        }
        else {
            MenuItem item=u.findItem(R.id.nav_send);
            item.setVisible(false);
            MenuItem itema=u.findItem(R.id.nav_ADMIN);
            itema.setVisible(false);
        }
        depAdapter = new DepAdapter(Navigation.this , dep_itemses);

    //

    }
    private void login()
    {
        // String email=n.getEmail();

        MenuItem item=u.findItem(R.id.nav_share);
        item.setVisible(false);
        MenuItem items=u.findItem(R.id.nav_send);
        items.setVisible(true);
        if (user.getEmail().equals("majed6966@gmail.com"))
        {
            MenuItem itema=u.findItem(R.id.nav_ADMIN);
            itema.setVisible(true);
        }
        else {
            MenuItem itema=u.findItem(R.id.nav_ADMIN);
            itema.setVisible(false);
        }
return;


    }
    private void signIn()
    {
        authStateListener =new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();

                if(user!=null)
                {
                    login();
                    return;

                }
                else {
                    logout();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setProviders(
                                            AuthUI.EMAIL_PROVIDER,

                                            AuthUI.GOOGLE_PROVIDER)
                                    .build(),
                            RC_SIGN_IN);
                    return;

                }
            }

        };
        firebaseAuth.addAuthStateListener(authStateListener);
        return;
    }
    private void logout()
    {
        if( mchildevent!=null) {
            messagerefrence.removeEventListener(mchildevent);
            mchildevent=null;
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        if(user!=null) {
            String email = user.getEmail();
            if (email.equals("m.abdelgelel95@yahoo.com")) {
                MenuItem item = menu.findItem(R.id.action_cart);
                item.setVisible(true);

            } else {
                MenuItem item = menu.findItem(R.id.action_cart);
                item.setVisible(false);

            }
        }
        else
        {
            MenuItem item = menu.findItem(R.id.action_cart);
            item.setVisible(false);

        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id)
        {




            case R.id.action_cart:

                Intent intent = new Intent(Navigation.this, DashBoard.class);
                startActivity(intent);
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (item.getItemId()) {

            case R.id.nav_share: {
                signIn();
                break;
            }
            case R.id.nav_send: {

                AuthUI.getInstance().signOut(this);
                MenuItem itemaa=u.findItem(R.id.nav_send);
                itemaa.setVisible(false);
                MenuItem itemaaa=u.findItem(R.id.nav_share);
                itemaaa.setVisible(true);
                MenuItem itemad=u.findItem(R.id.nav_ADMIN);
                itemad.setVisible(false);
                break;
            }
            case R.id.nav_ADMIN: {

                Intent intent = new Intent(Navigation.this, DashBoard.class);
                startActivity(intent);
                return true;
            }


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
       // firebaseAuth.addAuthStateListener(authStateListener);
        counter1=0;
        counter2=0;
        counter3=0;
        counter4=0;
        counter5=0;
        counter6=0;
        counter7=0;

        depAdapter.clear();
        dep_list.setAdapter(depAdapter);
        Query query = messagerefrence.orderByChild("department").equalTo(getString(R.string.item_1));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot post : dataSnapshot.getChildren()) {
                        counter1++;

                        //   Toast.makeText(MainActivity.this,counter1+"a",Toast.LENGTH_SHORT).show();

                    }
                    depAdapter.add(new Dep_Items(getString(R.string.item_1) ,counter1));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Query query1 = messagerefrence.orderByChild("department").equalTo(getString(R.string.item_2));
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot post : dataSnapshot.getChildren()) {
                        counter2++;

                    }
                    depAdapter.add(new Dep_Items(getString(R.string.item_2) ,counter2));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Query query2 = messagerefrence.orderByChild("department").equalTo(getString(R.string.item_3));
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot post : dataSnapshot.getChildren()) {
                        counter3++;

                    }
                    depAdapter.add(new Dep_Items(getString(R.string.item_3) ,counter3));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Query query3 = messagerefrence.orderByChild("department").equalTo(getString(R.string.item_4));
        query3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot post : dataSnapshot.getChildren()) {
                        counter4++;

                    }
                    depAdapter.add(new Dep_Items(getString(R.string.item_4) ,counter4));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Query query4 = messagerefrence.orderByChild("department").equalTo(getString(R.string.item_5));
        query4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot post : dataSnapshot.getChildren()) {
                        counter5++;

                    }
                    depAdapter.add(new Dep_Items(getString(R.string.item_5) ,counter5));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Query query5 = messagerefrence.orderByChild("department").equalTo(getString(R.string.item_6));
        query5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot post : dataSnapshot.getChildren()) {
                        counter6++;
                    }
                    depAdapter.add(new Dep_Items(getString(R.string.item_6) ,counter6));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Query query6 = messagerefrence.orderByChild("department").equalTo(getString(R.string.item_7));
        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot post : dataSnapshot.getChildren()) {
                        counter7++;
                    }
                    depAdapter.add(new Dep_Items(getString(R.string.item_7) ,counter7));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dep_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // position = dep_list.getPositionForView(view);
                //Object obj = parent.getItemAtPosition(position);

                Dep_Items obj = (Dep_Items) adapterView.getItemAtPosition(position);
                if (obj.getText1().equals(getString(R.string.item_1)))
                {
                    Intent intent = new Intent(Navigation.this, FriendshipActivity.class);
                    intent.putExtra("department",getString(R.string.item_1));
                    startActivity(intent);
                }
                else if(obj.getText1().equals(getString(R.string.item_2)))
                {
                    Intent intent = new Intent(Navigation.this, FriendshipActivity.class);
                    intent.putExtra("department",getString(R.string.item_2));
                    startActivity(intent);
                }
                else if(obj.getText1().equals(getString(R.string.item_3)))
                {
                    Intent intent = new Intent(Navigation.this, FriendshipActivity.class);
                    intent.putExtra("department",getString(R.string.item_3));

                    startActivity(intent);
                }
                else if(obj.getText1().equals(getString(R.string.item_4)))
                {
                    Intent intent = new Intent(Navigation.this, FriendshipActivity.class);
                    intent.putExtra("department",getString(R.string.item_4));

                    startActivity(intent);
                }
                else if(obj.getText1().equals(getString(R.string.item_5)))
                {
                    Intent intent = new Intent(Navigation.this, FriendshipActivity.class);
                    intent.putExtra("department",getString(R.string.item_5));

                    startActivity(intent);
                }
                else if(obj.getText1().equals(getString(R.string.item_6)))
                {
                    Intent intent = new Intent(Navigation.this, FriendshipActivity.class);
                    intent.putExtra("department",getString(R.string.item_6));

                    startActivity(intent);
                }
                else if(obj.getText1().equals(getString(R.string.item_7)))
                {
                    Intent intent = new Intent(Navigation.this, FriendshipActivity.class);
                    intent.putExtra("department",getString(R.string.item_7));

                    startActivity(intent);
                }







            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "logged out", Toast.LENGTH_SHORT).show();
               // finish();

            } else if (requestCode == RESULT_OK) {
                Toast.makeText(this, "logged in", Toast.LENGTH_SHORT).show();

finish();
            }
        }
    }
}
