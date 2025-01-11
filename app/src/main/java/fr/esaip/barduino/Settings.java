package fr.esaip.barduino;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import fr.esaip.barduino.bottle.BottleView;


public class Settings extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(fr.esaip.barduino.R.layout.settings);

        Button backbtn = (Button)findViewById(R.id.back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ImageButton bottlesettingsbtn = (ImageButton)findViewById(R.id.bottle);
        bottlesettingsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, BottleView.class));
/**                Intent i = new Intent (Settings.this, BottleView.class);
                Intent intent = getIntent();
                String bottlelist = intent.getStringExtra("bottleList");

                i.putExtra("bottleList", bottlelist);
                startActivity(i);**/
            }
        });
    }
}
