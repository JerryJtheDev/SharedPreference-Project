package com.jetbreed.sharedpreferencev2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etTask;
    private Button btnAdd, btnClear;
    private RecyclerView rvTasks;
    private ArrayList<String> taskList;
    private TaskAdapter adapter;

    private SharedPreferences prefs;
    private static final String PREFS_NAME = "TaskAppPrefs";
    private static final String TASKS_KEY = "tasks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTask = findViewById(R.id.etTask);
        rvTasks = findViewById(R.id.rvTasks);
        btnAdd = findViewById(R.id.btnAdd);
        btnClear = findViewById(R.id.btnClear);

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // ✅ Load tasks from SharedPreferences (using Gson)
        loadTasks();

        // ✅ Setup RecyclerView
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(taskList, position -> {
            taskList.remove(position);
            adapter.notifyItemRemoved(position);
            saveTasks();
        });
        rvTasks.setAdapter(adapter);

        // ✅ Add new task
        btnAdd.setOnClickListener(v -> {
            String task = etTask.getText().toString().trim();
            if (!task.isEmpty()) {
                taskList.add(task);
                adapter.notifyItemInserted(taskList.size() - 1);
                saveTasks();
                etTask.setText("");
            } else {
                Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show();
            }
        });

        // ✅ Clear all tasks
        btnClear.setOnClickListener(v -> {
            taskList.clear();
            adapter.notifyDataSetChanged();
            saveTasks();
        });
    }

    // 🔹 Save tasks to SharedPreferences as JSON
    private void saveTasks() {
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(taskList);
        editor.putString(TASKS_KEY, json);
        editor.apply();
    }

    // 🔹 Load tasks from SharedPreferences
    private void loadTasks() {
        String json = prefs.getString(TASKS_KEY, null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            taskList = new Gson().fromJson(json, type);
        } else {
            taskList = new ArrayList<>();
        }
    }
}



//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//public class MainActivity extends AppCompatActivity {
//
////    EditText jsf, hibernate, spring, android, junit;
////    Button btnSharedPref, btnViewSharePref;
////    String jsfStr, hibernateStr, springStr, androidStr, junitStr;
//
//    SharedPreferences sp;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
////        jsf = findViewById(R.id.jsfx);
////        hibernate = findViewById(R.id.hibernate);
////        spring = findViewById(R.id.spring);
////        android = findViewById(R.id.android);
////        junit = findViewById(R.id.junit);
////
////        btnSharedPref = findViewById(R.id.btnSharePref);
////
////        btnViewSharePref = findViewById(R.id.btnViewPref);
////
////        btnSharedPref.setOnClickListener(view -> {
////            jsfStr = jsf.getText().toString();
////            hibernateStr = hibernate.getText().toString();
////            springStr = spring.getText().toString();
////            androidStr = android.getText().toString();
////            junitStr = junit.getText().toString();
////
////            sp = getSharedPreferences("CoursePref", MODE_PRIVATE);
////
////            SharedPreferences.Editor editor = sp.edit();
////
////            editor.putString("jsfStr", jsfStr);
////            editor.putString("hibernateStr", hibernateStr);
////            editor.putString("springStr", springStr);
////            editor.putString("androidStr", androidStr);
////            editor.putString("junitStr", junitStr);
////
////            editor.commit();
//
//            Toast.makeText(MainActivity.this, "Preference Saved Successfully", Toast.LENGTH_SHORT).show();
//
//


//        btnViewSharePref.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent viewPrefIntent = new Intent(MainActivity.this, ViewPref.class);
//                startActivity(viewPrefIntent);
//            }
//        });
//    }
////On your on going project, with a button, submit and save all
//// the plaintext strings to your SharedPreference.
//
////All use a button to navigate to a new activity where you can view your
////    SharedPreference.
//
////    Furthermore, create extra five layouts (activities) with
////    accompanying buttons for navigations.
//
//
//}