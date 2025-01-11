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

        // TODO: remove manual object creation used for testing purposes
        ArrayList<Bottle> bottles = new ArrayList<Bottle>();
        bottles.add(new Bottle("Vodka", 1,R.drawable.bottle_vodka));
        bottles.add(new Bottle("Jager", 2,R.drawable.bottle_jager));
        bottles.add(new Bottle("Rum", 3,R.drawable.bottle_rum));
        bottles.add(new Bottle("Tequila", 4,R.drawable.bottle_champagne));
        bottles.add(new Bottle("Whiskey", 5,R.drawable.bottle_whiskey));
        bottles.add(new Bottle("Gin", 6,R.drawable.bottle_gin));
        final BottleList bottleList= new BottleList(bottles);

        final ArrayList<Drink> drinks = new ArrayList<Drink>();
        // TODO : Limiter à 4 le nombre de boutielles
        drinks.add(new Drink("Bloody mary",new String[]{"vodka", "tomato juice", "lemon juice"},new Double[]{4.5,9.0,1.5},R.drawable.cocktail));
        drinks.add(new Drink("Cocktail",new String[]{"orange", "pomme", "raisin"}, new Double[]{5.0,3.0,4.0},R.drawable.cocktail1));
        drinks.add(new Drink("Jus d'orange",new String[]{"orange"}, new Double[]{100.},R.drawable.cocktail2));
        drinks.add(new Drink("Gin Tonic",new String[]{"gin", "tonic"}, new Double[]{7.0,14.0}, R.drawable.gintonic));
        drinks.add(new Drink("Mai Thai",new String[]{"rhum blanc", "rhum ambré","cointreau","sucre de canne","sirop d'orgeat"}, new Double[]{3.,3.,2.,1.,1.},R.drawable.maithai));
        drinks.add(new Drink("Mojito", new String[]{"rhum blanc","sucre de canne","eau gazeuse"},new Double[]{4.,2.,1.},R.drawable.mojito));


        DrinkAdapter adapter = new DrinkAdapter(this, drinks);
        ListView list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: drink: " + drinks.get(position).getName());
                Toast.makeText(MainActivity.this, drinks.get(position).getName(), Toast.LENGTH_SHORT).show();
                // TODO : Send the drink to the arduino
                // 1. Get item_id and nb_sec from drink.


                // 2. Build array.
                // {[1,2,3], [5.0,3.0,4.0]}
                // 2. Send array to arduino.



                // Côté adruino :
                // Boucle For compteur = nb item dans l'arrey
                // Pour chaque items, se déplacer à la position X de l'item
                // Soulever le verre pendant x secondes
                // Passer à l'item suivant | Rejoindre la position home
            }
        });

        ImageButton settingsbtn = (ImageButton)findViewById(R.id.settings);
        settingsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                Intent i = new Intent (MainActivity.this, Settings.class);
                String s = new Gson().toJson(bottleList);
                i.putExtra("bottleList", s);
                startActivity(i);
                **/
                // TODO : Finish the proper implementation of shared prefs
                // change shared pref key
                SharedPreferences mPrefs = getSharedPreferences("your_sp_key", MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                String json = new Gson().toJson(bottleList);
                prefsEditor.putString("someName", json).apply();
                startActivity(new Intent(MainActivity.this, Settings.class));

            }
        });
    }
}
