package com.example.omarmohammedraafat.qasseda;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ItemAdapter extends ArrayAdapter<ItemData> {
    private ImageView showHide;
    private ImageView copy;
    private ImageView favourit;
    private ImageView share;
    private boolean IS_CLICKED = true;

    public ItemAdapter(Context context, ArrayList<ItemData> itemsdata) {
        super(context,0, itemsdata);
    }

    public ItemAdapter(Context context){
        super(context,0);
    }




    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_list, parent, false);
        }

        final ItemData currentArticle = getItem(position);
        final TextView poemDescription = listItemView.findViewById(R.id.description);
        poemDescription.setText(currentArticle.getDescription());

        final ImageView imageView =  listItemView.findViewById(R.id.image1);
        boolean isPhoto = currentArticle.getImageResource() != null;
        if (isPhoto) {

            imageView.setVisibility(View.VISIBLE);
            Glide.with(imageView.getContext())
                    .load(currentArticle.getImageResource())
                    .into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
        }


        showHide =  listItemView.findViewById(R.id.showHide);

        showHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(IS_CLICKED){
                    imageView.setVisibility(View.GONE);
                    IS_CLICKED = false;
                }
                else {
                    imageView.setVisibility(View.VISIBLE);
                    IS_CLICKED = true;
                }
            }
        });



        copy =  listItemView.findViewById(R.id.copy);

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", poemDescription.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "تم نسخ النص إلى الحافظة ☺", Toast.LENGTH_SHORT).show();
            }
        });
        favourit  =  listItemView.findViewById(R.id.favourit);
        favourit.setTag(getItem(position));
        favourit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    if(IS_CLICKED){
                        Toast.makeText(getContext(), "تم أضافته بالمفضله ♥", Toast.LENGTH_SHORT).show();
                        favourit.setImageResource(R.drawable.heart2);
                        IS_CLICKED = false;
                    }
                    else {
                        Toast.makeText(getContext(), "تم حذفه من المفضله ☺", Toast.LENGTH_SHORT).show();
                        favourit.setImageResource(R.drawable.heart);
                        IS_CLICKED = true;
                    }


            }
        });


        // action on specific list_item or move it to main activity

        share =  listItemView.findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + currentArticle.getImageResource()));
                getContext().startActivity(Intent.createChooser(share, "Share Image"));
            }
        });

        return listItemView;

    }



}
