package fr.esaip.barduino.bluetooth;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Set;

import fr.esaip.barduino.MainActivity;

public class BluetoothManager {

    private BluetoothAdapter bluetoothAdapter;
    private Context context;

    public BluetoothManager(Context context) {
        this.context = context;
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    // Vérifie si Bluetooth est activé, sinon demande à l'utilisateur de l'activer
    public void checkAndEnableBluetooth(int requestCode) {
        if (bluetoothAdapter == null) {
            Toast.makeText(context, "Bluetooth non supporté", Toast.LENGTH_SHORT).show();
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                ((MainActivity) context).startActivityForResult(enableBtIntent, requestCode);
            }
        }
    }

    // Vérifie si les permissions Bluetooth sont accordées et les demande si nécessaire
    public boolean checkPermissions(int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Vérifier et demander des permissions pour les versions antérieures à Android 12 (API 31)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{
                                    Manifest.permission.BLUETOOTH,
                                    Manifest.permission.BLUETOOTH_ADMIN,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                            }, requestCode);
                }
            }

            // Vérification des permissions pour Android 12 (API 31 et plus)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{
                                    Manifest.permission.BLUETOOTH_CONNECT,
                                    Manifest.permission.BLUETOOTH_SCAN
                            }, requestCode);
                }
            }
        } else {
            return true; // Permissions sont déjà accordées sur les versions plus anciennes (avant Android 6.0)
        }
        return true;
    }

    // Retourne les appareils appairés
    public Set<BluetoothDevice> getPairedDevices() {
        return bluetoothAdapter.getBondedDevices();
    }

    // Lance la découverte des appareils Bluetooth disponibles
    public void startDiscovery(BluetoothDeviceDiscoveryListener listener) {
        bluetoothAdapter.startDiscovery();
        listener.onDiscoveryStarted();

        // Vous pouvez implémenter ici un BroadcastReceiver pour recevoir les résultats de la découverte
    }

    public interface BluetoothDeviceDiscoveryListener {
        void onDiscoveryStarted();
        void onDeviceFound(BluetoothDevice device);
    }
}