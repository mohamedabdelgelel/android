package com.example.omarmohammedraafat.qasseda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class DepAdapter extends ArrayAdapter<Dep_Items> {

    public DepAdapter(Context context, ArrayList<Dep_Items> dep_itemses) {
        super(context,0, dep_itemses);
    }
    ItemData itemData = new ItemData();
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.dep_item_list, parent, false);
        }

        Dep_Items currentDep = getItem(position);

        TextView depText = listItemView.findViewById(R.id.dep_name);
        depText.setText(currentDep.getText1());



        TextView counterText = listItemView.findViewById(R.id.counter);
       // counterText.setText(count);
        counterText.setText(currentDep.getText2()+"");





        return listItemView;
    }

}
