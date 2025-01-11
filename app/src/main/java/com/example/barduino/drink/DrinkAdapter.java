package com.example.barduino.drink;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.barduino.R;

import java.util.ArrayList;

public class DrinkAdapter extends BaseAdapter {

    private ArrayList<Drink> listDrink;
    private Context context;
    private LayoutInflater inflater;

    public DrinkAdapter (Context context, ArrayList<Drink> list) {
        this.context = context;
        listDrink = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listDrink.size();
    }

    @Override
    public Object getItem(int position) {
        return listDrink.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = (View) inflater.inflate(R.layout.row_drink, parent, false);
        } else {
            view = (View) convertView;
        }

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView ingredient = (TextView) view.findViewById(R.id.ingredient);
        ImageView image = (ImageView) view.findViewById(R.id.image);

        name.setText(listDrink.get(position).getName());
        ingredient.setText(listDrink.get(position).getIngredient());
        image.setImageResource(listDrink.get(position).getImg());

        return view;


    }
}
