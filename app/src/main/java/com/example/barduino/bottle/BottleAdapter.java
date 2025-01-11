package com.example.barduino.bottle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.barduino.R;

public class BottleAdapter extends BaseAdapter{
    private BottleList listBottle;
    private Context context;
    private LayoutInflater inflater;

    public BottleAdapter (Context context, BottleList list) {
        this.context = context;
        listBottle = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listBottle.size();
    }

    @Override
    public Object getItem(int position) {
        return listBottle.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = (View) inflater.inflate(R.layout.row_bottle, parent, false);
        } else {
            view = (View) convertView;
        }

        ImageButton bottleditbtn = (ImageButton)view.findViewById(R.id.editbottle);
        final EditText bottlename = (EditText)view.findViewById(R.id.bottlename);
        final EditText bottlenumber = (EditText)view.findViewById(R.id.bottlenumber);
        ImageView image = view.findViewById(R.id.bottleimage);

        bottlename.setText(listBottle.get(position).getBottlename());
        bottlenumber.setText(String.valueOf(listBottle.get(position).getBottleposition()));
        image.setImageResource(listBottle.get(position).getImg());


        bottleditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottlename.isEnabled()){
                    bottlenumber.setEnabled(false);
                    bottlename.setEnabled(false);
                    listBottle.set(Integer.parseInt(bottlenumber.getText().toString())-1, new Bottle(bottlename.getText().toString(),Integer.parseInt(bottlenumber.getText().toString()), R.drawable.bottle_vodka));
                    System.out.println(Integer.parseInt(bottlenumber.getText().toString())-1);
                    System.out.println(new Bottle(bottlename.getText().toString(),Integer.parseInt(bottlenumber.getText().toString()), R.drawable.bottle_vodka).toString());
                }else{
                    bottlename.setEnabled(true);
                    bottlenumber.setEnabled(true);
                }
            }
        });


        return view;


    }

}
