package com.example.omarmohammedraafat.qasseda;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class DashBoard extends AppCompatActivity {
    private static final int RC_PHOTO_PICKER =  2;
    private FirebaseStorage firebaseStorag;
    private Button mPhotoPickerButton;
    private DatabaseReference messagerefrence;
    private FirebaseDatabase firebaseDatabase;
    private  Uri selected;
    private EditText description;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private RadioButton radioButton5;
    private RadioButton radioButton6;
    private RadioButton radioButton7;
    private StorageReference chatRefrence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        mPhotoPickerButton = (Button) findViewById(R.id.photoPickerButton);
        firebaseDatabase =FirebaseDatabase.getInstance();
        description=(EditText)findViewById(R.id.editText) ;
        radioButton1=(RadioButton)findViewById(R.id.radioButton);
        radioButton2=(RadioButton)findViewById(R.id.radioButton2);
        radioButton3=(RadioButton)findViewById(R.id.radioButton3);
        radioButton4=(RadioButton)findViewById(R.id.radioButton4);
        radioButton5=(RadioButton)findViewById(R.id.radioButton5);
        radioButton6=(RadioButton)findViewById(R.id.radioButton6);
        radioButton7=(RadioButton)findViewById(R.id.radioButton7);

        messagerefrence=firebaseDatabase.getReference().child("posts");

        firebaseStorag=FirebaseStorage.getInstance();
        chatRefrence=firebaseStorag.getReference().child("post_photos");
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

         if(requestCode==RC_PHOTO_PICKER && resultCode==RESULT_OK)
        {
             selected= data.getData();

        }
    }
    public void post(View view)
    {
        String department = null;

        try {
            final String desrcipe = description.getText().toString();
            if (radioButton1.isChecked()) {
                department = radioButton1.getText().toString();
            } else if (radioButton2.isChecked()) {
                department = radioButton2.getText().toString();

            } else if (radioButton3.isChecked()) {
                department = radioButton3.getText().toString();

            } else if (radioButton4.isChecked()) {
                department = radioButton4.getText().toString();

            } else if (radioButton5.isChecked()) {
                department = radioButton5.getText().toString();

            } else if (radioButton6.isChecked()) {
                department = radioButton6.getText().toString();

            } else if (radioButton7.isChecked()) {
                department = radioButton7.getText().toString();

            }


            final String sort = department;
            StorageReference photoEef = chatRefrence.child(selected.getLastPathSegment());
            photoEef.putFile(selected).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri download = taskSnapshot.getDownloadUrl();
                            ItemData item = new ItemData(desrcipe, download.toString(), sort);
                            messagerefrence.push().setValue(item);
                            Toast.makeText(DashBoard.this, "تم النشر بنجاح ", Toast.LENGTH_LONG).show();
                            finish();
                        }

                    }
            );
        }
        catch(Exception ea)
        {
            if(description.getText().toString().equals(""))
            {
                Toast.makeText(DashBoard.this, "قم بكتابة القصيدة ", Toast.LENGTH_LONG).show();
return;
            }
            if(department==null)
            {
                Toast.makeText(DashBoard.this, "قم باختيار القسم ", Toast.LENGTH_LONG).show();
                return;
            }
            ItemData item = new ItemData(description.getText().toString(),null, department);
            messagerefrence.push().setValue(item);
            Toast.makeText(DashBoard.this, "تم النشر بنجاح ", Toast.LENGTH_LONG).show();
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id)
        {




            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
