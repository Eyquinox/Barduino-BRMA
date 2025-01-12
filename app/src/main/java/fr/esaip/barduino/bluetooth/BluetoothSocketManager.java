package fr.esaip.barduino.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothSocketManager {

    private static final UUID MY_UUID = UUID.fromString("your-unique-uuid");  // Remplacez par un UUID unique
    private BluetoothSocket bluetoothSocket;
    private BluetoothDevice bluetoothDevice;
    private Context context;
    private ConnectedThread connectedThread;

    public BluetoothSocketManager(Context context) {
        this.context = context;
    }

    // Connexion à un appareil Bluetooth via son BluetoothDevice
    public void connectToDevice(BluetoothDevice device) {
        this.bluetoothDevice = device;
        try {
            bluetoothSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
            bluetoothSocket.connect();
            connectedThread = new ConnectedThread(bluetoothSocket);
            connectedThread.start();
            Toast.makeText(context, "Connecté à l'appareil", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, "Erreur de connexion", Toast.LENGTH_SHORT).show();
        }
    }

    // Classe interne pour gérer les échanges de données après la connexion
    private static class ConnectedThread extends Thread {
        private BluetoothSocket bluetoothSocket;
        private InputStream inputStream;
        private OutputStream outputStream;
        private Handler handler;

        public ConnectedThread(BluetoothSocket socket) {
            this.bluetoothSocket = socket;
            InputStream tempIn = null;
            OutputStream tempOut = null;
            try {
                tempIn = socket.getInputStream();
                tempOut = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.inputStream = tempIn;
            this.outputStream = tempOut;
        }

        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;
            while (true) {
                try {
                    bytes = inputStream.read(buffer);
                    handler.obtainMessage(1, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        public void write(byte[] bytes) {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Envoie des données à l'appareil connecté
    public void sendData(byte[] data) {
        if (connectedThread != null) {
            connectedThread.write(data);
        }
    }

    // Méthode pour fermer la connexion Bluetooth
    public void closeConnection() {
        try {
            if (bluetoothSocket != null) {
                bluetoothSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
