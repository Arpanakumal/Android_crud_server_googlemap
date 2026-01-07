// MainActivity.java
package com.example.crud;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    Button btnOpenCRUD;
    Button btnStudentRecords;
    Button btnMaps;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOpenCRUD = findViewById(R.id.btnOpenCRUD);
        btnStudentRecords = findViewById(R.id.btnStudentRecords);
        btnMaps = findViewById(R.id.btnMaps);

        btnOpenCRUD.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CRUDActivity.class);
            startActivity(intent);
        });

        btnStudentRecords.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StudentActivity.class);
            startActivity(intent);
        });

        btnMaps.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            } else {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(this, MapsActivity.class));
            } else {
                Toast.makeText(this, "Location permission is required to use maps", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
