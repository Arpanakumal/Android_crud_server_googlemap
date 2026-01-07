// BookCRUDActivity.java
package com.example.crud;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BookCRUDActivity extends AppCompatActivity {

    EditText etSBN, etTitle, etAuthor, etPrice;
    Button btnAdd, btnUpdate, btnDelete, btnView;
    ListView listView;
    BookDBHelper dbHelper;
    ArrayAdapter<String> adapter;
    ArrayList<String> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_crudactivity);

        etSBN = findViewById(R.id.etSBN);
        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etPrice = findViewById(R.id.etPrice);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnView = findViewById(R.id.btnView);
        listView = findViewById(R.id.listView);

        dbHelper = new BookDBHelper(this);
        bookList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookList);
        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            String sbn = etSBN.getText().toString();
            String title = etTitle.getText().toString();
            String author = etAuthor.getText().toString();
            double price = Double.parseDouble(etPrice.getText().toString());
            if (dbHelper.addBook(sbn, title, author, price)) {
                Toast.makeText(this, "Book Added", Toast.LENGTH_SHORT).show();
                clearFields();
                loadBooks();
            } else {
                Toast.makeText(this, "Error Adding Book", Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(v -> {
            String sbn = etSBN.getText().toString().trim();
            String title = etTitle.getText().toString().trim();
            String author = etAuthor.getText().toString().trim();
            String priceStr = etPrice.getText().toString().trim();

            if(sbn.isEmpty()) {
                Toast.makeText(this, "Enter SBN to update", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = 0;
            if(!priceStr.isEmpty()) {
                try {
                    price = Double.parseDouble(priceStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid price", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if(dbHelper.updateBook(sbn, title, author, price)) {
                Toast.makeText(this, "Book Updated", Toast.LENGTH_SHORT).show();
                clearFields();
                loadBooks();
            } else {
                Toast.makeText(this, "No book with this SBN found", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(v -> {
            String sbn = etSBN.getText().toString().trim();
            if(sbn.isEmpty()) {
                Toast.makeText(this, "Enter SBN to delete", Toast.LENGTH_SHORT).show();
                return;
            }

            if(dbHelper.deleteBook(sbn)) {
                Toast.makeText(this, "Book Deleted", Toast.LENGTH_SHORT).show();
                clearFields();
                loadBooks();
            } else {
                Toast.makeText(this, "No book with this SBN found", Toast.LENGTH_SHORT).show();
            }
        });


        btnView.setOnClickListener(v -> loadBooks());
    }

    private void loadBooks() {
        bookList.clear();
        Cursor cursor = dbHelper.getAllBooks();
        if (cursor.moveToFirst()) {
            do {
                String book = "SBN: " + cursor.getString(0) +
                        "\nTitle: " + cursor.getString(1) +
                        "\nAuthor: " + cursor.getString(2) +
                        "\nPrice: " + cursor.getDouble(3);
                bookList.add(book);
            } while (cursor.moveToNext());
        }
        adapter.notifyDataSetChanged();
        cursor.close();
    }

    private void clearFields() {
        etSBN.setText("");
        etTitle.setText("");
        etAuthor.setText("");
        etPrice.setText("");
    }
}
