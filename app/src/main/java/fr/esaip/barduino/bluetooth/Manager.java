package fr.esaip.barduino.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;

import java.io.OutputStream;

public class Manager {
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private OutputStream outputStream;

    private void connectToBluetoothDevice(String DEVICE_ADDRESS, String MY_UUID) {
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(DEVICE_ADDRESS);
//        try {
//            BluetoothSocket socket = device.createRfcommSocketToServiceRecord(MY_UUID);
//            socket.connect();
//            outputStream = socket.getOutputStream();
//            Toast.makeText(this, "Connected to " + device.getName(), Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            Log.d(TAG,  "Cannot connect", e);
//            Toast.makeText(Manager.this, "Échec de la connexion Bluetooth", Toast.LENGTH_SHORT).show();
//        }
    }

}
