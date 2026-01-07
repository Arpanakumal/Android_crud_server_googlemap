// CRUDActivity.java
package com.example.crud;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CRUDActivity extends AppCompatActivity {

    Button btnBookCRUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudactivity);

        btnBookCRUD = findViewById(R.id.btnBookCRUD);

        btnBookCRUD.setOnClickListener(v -> {
            Intent intent = new Intent(CRUDActivity.this, BookCRUDActivity.class);
            startActivity(intent);
        });
    }
}
