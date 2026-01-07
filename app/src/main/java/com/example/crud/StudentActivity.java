package com.example.crud;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentActivity extends AppCompatActivity {

    Button btnLoadStudents;
    ListView listView;
    ArrayList<String> studentList;
    ArrayAdapter<String> adapter;

    private final String URL_ADD = "http://192.168.1.70/CRUD/add_student.php";
    private final String URL_FETCH = "http://192.168.1.70/CRUD/fetch_students.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        btnLoadStudents = findViewById(R.id.btnLoadStudents);
        listView = findViewById(R.id.listViewStudents);
        studentList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        listView.setAdapter(adapter);

        // Add 5 students and then immediately fetch the list
        addStudents();
        fetchStudents(); // Automatically load the records

        btnLoadStudents.setOnClickListener(v -> fetchStudents());
    }

    private void addStudents() {
        String[][] students = {
                {"Alice","Female","alice@email.com","101","City A"},
                {"Bob","Male","bob@email.com","102","City B"},
                {"Charlie","Male","charlie@email.com","103","City C"},
                {"Diana","Female","diana@email.com","104","City D"},
                {"Eve","Female","eve@email.com","105","City E"}
        };

        RequestQueue queue = Volley.newRequestQueue(this);

        for (String[] s : students) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD,
                    response -> {
                        // You can log response
                    },
                    error -> {
                        // Errors here might be expected if students already exist
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", s[0]);
                    params.put("gender", s[1]);
                    params.put("email", s[2]);
                    params.put("rollno", s[3]);
                    params.put("address", s[4]);
                    return params;
                }
            };
            queue.add(stringRequest);
        }
    }

    private void fetchStudents() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_FETCH,
                response -> {
                    studentList.clear();
                    String[] records = response.split(";");
                    for (String rec : records) {
                        if (!rec.trim().isEmpty()) {
                            studentList.add(rec);
                        }
                    }
                    adapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(StudentActivity.this, "Error fetching data. Check IP address and server.", Toast.LENGTH_LONG).show());

        queue.add(stringRequest);
    }
}
