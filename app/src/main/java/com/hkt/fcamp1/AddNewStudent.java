package com.hkt.fcamp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddNewStudent extends AppCompatActivity {

    EditText phoneEditText, nameEditText;
    TextView scoreTextView;
    int score;
    ArrayList<Student> studentArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_student);
        settingView();
        loadData();
        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
        scoreTextView.setText(Integer.toString(score));
    }

    public void settingView(){
        phoneEditText = findViewById(R.id.phoneInput);
        nameEditText = findViewById(R.id.editText);
        scoreTextView = findViewById(R.id.score);
    }

    @Override
    public void onBackPressed() {

    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(studentArrayList);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Student>>() {}.getType();
        studentArrayList = gson.fromJson(json, type);

        if (studentArrayList == null) {
            studentArrayList = new ArrayList<>();
        }
    }

    public void submit(View view) {
        if (phoneEditText.getText().toString().isEmpty() || nameEditText.getText().toString().isEmpty()){
            Toast.makeText(AddNewStudent.this, "Please enter all the information!!", Toast.LENGTH_SHORT).show();
        } else {
            Log.i("Final SCore: ", Integer.toString(score));
            Student temp = new Student(phoneEditText.getText().toString(), nameEditText.getText().toString(), score);
            temp.setScore(score);
            studentArrayList.add(temp);
            saveData();
            Intent intent = new Intent(AddNewStudent.this, Home.class);
            startActivity(intent);
            finish();
        }
    }
}
