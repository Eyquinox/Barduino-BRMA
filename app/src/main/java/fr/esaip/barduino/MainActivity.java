package fr.esaip.barduino;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;

import fr.esaip.barduino.bottle.Bottle;
import fr.esaip.barduino.bottle.BottleList;
import fr.esaip.barduino.drink.Drink;
import fr.esaip.barduino.drink.DrinkAdapter;
import fr.esaip.barduino.bluetooth.BluetoothManager;
import fr.esaip.barduino.bluetooth.BluetoothSocketManager;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends Activity {

    private BluetoothManager bluetoothManager;
    private BluetoothSocketManager bluetoothSocketManager;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private OutputStream outputStream;

    private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // Standard UUID used for serial Bluetooth communications
    private final String DEVICE_ADDRESS = "00:11:22:33:44:55"; // Replace with the MAC address of your Bluetooth device
    private static final int REQUEST_ENABLE_BT_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothManager = new BluetoothManager(this);
        bluetoothSocketManager = new BluetoothSocketManager(this);

        // Init Bluetooth
        //bluetoothAdapter =  BluetoothAdapter.getDefaultAdapter();
        bluetoothManager.checkAndEnableBluetooth(REQUEST_ENABLE_BT_PERMISSION);

        // Connect to Bluetooth device
        Log.d(TAG, "Connexion à l'appareil Bluetooth");
        bluetoothManager.checkPermissions(REQUEST_ENABLE_BT_PERMISSION);

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

        drinks.add(new Drink("Bloody mary",new String[]{"vodka", "tomato juice", "lemon juice"},new Double[]{4.5,9.0,1.5},R.drawable.cocktail));
        drinks.add(new Drink("Gin Tonic",new String[]{"gin", "tonic"}, new Double[]{7.0,14.0}, R.drawable.gintonic));
        drinks.add(new Drink("Mai Thai",new String[]{"rhum","cointreau","sucre de canne","sirop d'orgeat"}, new Double[]{3.,2.,1.,1.},R.drawable.maithai));
        drinks.add(new Drink("Mojito", new String[]{"rhum","sucre de canne","eau gazeuse"},new Double[]{4.,2.,1.},R.drawable.mojito));


        DrinkAdapter adapter = new DrinkAdapter(this, drinks);
        ListView list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: drink: " + drinks.get(position).getName());
                Toast.makeText(MainActivity.this, drinks.get(position).getName(), Toast.LENGTH_SHORT).show();
                // FIXME : Send the drink to the arduino
                // 1. Send array to arduino.
                bluetoothSocketManager.sendArrayList(drinks);

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ENABLE_BT_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bluetoothSocketManager.connectToDevice(bluetoothAdapter.getRemoteDevice(DEVICE_ADDRESS));
            } else {
                Toast.makeText(this, "Permission refusée", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (outputStream != null) outputStream.close();
            bluetoothSocketManager.closeConnection();
        } catch (IOException e) {
            Log.e(TAG, "Erreur lors de la fermeture des connexion Bluetooth", e);
        }
    }
}
