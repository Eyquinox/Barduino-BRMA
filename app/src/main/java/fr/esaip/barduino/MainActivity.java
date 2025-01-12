package com.example.barduino;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.barduino.bottle.Bottle;
import com.example.barduino.bottle.BottleList;
import com.example.barduino.drink.Drink;
import com.example.barduino.drink.DrinkAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Réduction à 4 bouteilles
        ArrayList<Bottle> bottles = new ArrayList<>();
        bottles.add(new Bottle("Vodka", 1, R.drawable.bottle_vodka));
        bottles.add(new Bottle("Gin", 2, R.drawable.bottle_gin));
        bottles.add(new Bottle("Rum", 3, R.drawable.bottle_rum));  // Regroupement rhum blanc et ambré
        bottles.add(new Bottle("Tonic", 4, R.drawable.bottle_champagne)); // Remplace tequila et whiskey

        final BottleList bottleList = new BottleList(bottles);

        // Mise à jour des boissons
        final ArrayList<Drink> drinks = new ArrayList<>();
        drinks.add(new Drink("Bloody Mary", new String[]{"vodka", "tomato juice", "lemon juice"}, new Double[]{4.5, 9.0, 1.5}, R.drawable.cocktail));
        drinks.add(new Drink("Gin Tonic", new String[]{"gin", "tonic"}, new Double[]{7.0, 14.0}, R.drawable.gintonic));
        drinks.add(new Drink("Mai Tai", new String[]{"rum", "cointreau", "sucre de canne", "sirop d'orgeat"}, new Double[]{6.0, 2.0, 1.0, 1.0}, R.drawable.maithai));
        drinks.add(new Drink("Mojito", new String[]{"rum", "sucre de canne", "eau gazeuse"}, new Double[]{4.0, 2.0, 1.0}, R.drawable.mojito));

        DrinkAdapter adapter = new DrinkAdapter(this, drinks);
        ListView list = findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener((parent, view, position, id) -> {
            Log.d(TAG, "onItemClick: drink: " + drinks.get(position).getName());
            Toast.makeText(MainActivity.this, drinks.get(position).getName(), Toast.LENGTH_SHORT).show();
        });

        ImageButton settingsbtn = findViewById(R.id.settings);
        settingsbtn.setOnClickListener(v -> {
            // Sauvegarde de la liste des bouteilles dans les SharedPreferences
            SharedPreferences mPrefs = getSharedPreferences("your_sp_key", MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            String json = new Gson().toJson(bottleList);
            prefsEditor.putString("someName", json).apply();
            startActivity(new Intent(MainActivity.this, Settings.class));
        });
    }
}
