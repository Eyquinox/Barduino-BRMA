package com.example.barduino.bottle;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.barduino.R;
import com.google.gson.Gson;


import java.util.ArrayList;


public class BottleView extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottle_setting);

/**        Intent intent = getIntent();
 String s = intent.getStringExtra("bottleList");
 BottleList bottleList = new Gson().fromJson(s, BottleList.class);**/
        SharedPreferences mPrefs = getSharedPreferences("your_sp_key", MODE_PRIVATE); //add key
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        String data = mPrefs.getString("someName", null);
        final BottleList bottleList = new Gson().fromJson(data, BottleList.class);


        BottleAdapter adapter = new BottleAdapter(this, bottleList);
        ListView list = (ListView) findViewById(R.id.listbottle);
        list.setAdapter(adapter);
/**
 ImageButton goback = (ImageButton)findViewById(R.id.goback);
 goback.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
SharedPreferences mPrefs = getSharedPreferences("your_sp_key", MODE_PRIVATE); //add key
SharedPreferences.Editor prefsEditor = mPrefs.edit();
String json = new Gson().toJson(bottleList);
prefsEditor.putString("someName", json).apply();
finish();
System.out.println("wowowo");
}
});**/
        ImageButton backbtn = (ImageButton)findViewById(R.id.goback);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
