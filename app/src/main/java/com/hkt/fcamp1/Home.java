package com.hkt.fcamp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Home extends AppCompatActivity {

    StudentAdapter studentAdapter;
    ArrayList<Student> studentArrayList;
    Button highscore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        studentAdapter = new StudentAdapter(this, studentArrayList);
        highscore = findViewById(R.id.highscore);
        highscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this ,HighScoreActivity.class);
                startActivity(intent);
            }
        });
    }

    public void play (View view){
        Intent intent = new Intent(Home.this, GameActivity.class);
        startActivity(intent);
    }

//    private void loadData() {
//        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("task list", null);
//        Type type = new TypeToken<ArrayList<Student>>() {}.getType();
//        studentArrayList = gson.fromJson(json, type);
//
//        if (studentArrayList == null) {
//            studentArrayList = new ArrayList<>();
//        }
//    }

}
