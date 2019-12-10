package com.hkt.fcamp1;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HighScoreActivity extends AppCompatActivity {

    ArrayList<Student> studentArrayList;
    ListView listView;
    TextView nameWinner, bestScore;
    Button submit;
    Student studentWinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_high_score);
        loadData();
        settingView();

        if (studentArrayList.size() != 0){
            Collections.sort(studentArrayList, new SortByScore());
            studentWinner = studentArrayList.get(0);
            studentArrayList.remove(0);
            StudentAdapter studentAdapter = new StudentAdapter(this, studentArrayList);
            listView.setAdapter(studentAdapter);
            nameWinner.setText(studentWinner.getName());
            bestScore.setText(Integer.toString(studentWinner.getScore()));
            selectItemInListView();
        }
    }

    public void settingView(){
        listView = findViewById(R.id.listView);
        nameWinner = findViewById(R.id.nameWinner);
        bestScore = findViewById(R.id.winnerScore);
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(studentArrayList);
        editor.putString("task list", json);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        studentArrayList.add(studentWinner);
        saveData();
    }

    private void loadData() {
        Gson gson;
        String json;
        Type type;
        try{
            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            gson = new Gson();
            json = sharedPreferences.getString("task list", null);
            type = new TypeToken<ArrayList<Student>>() {}.getType();
            studentArrayList = gson.fromJson(json, type);
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT);
        }

        if (studentArrayList == null) {
            studentArrayList = new ArrayList<>();
        }
    }

    public void selectItemInListView(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Student temp = studentArrayList.get(i);
                Intent intent = new Intent(HighScoreActivity.this, InformationSpecificStudent.class);
                intent.putExtra("student", temp);
                intent.putExtra("rank", i);
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode == 2)
        {
            Student temp = (Student) data.getSerializableExtra("back");
            int position = data.getIntExtra("rank", 0);
            studentArrayList.get(position).setName(temp.getName());
            studentArrayList.get(position).setPhoneNumber(temp.getName());
            Log.i("test", studentArrayList.get(position).toString());
            StudentAdapter studentAdapter = new StudentAdapter(this, studentArrayList);
            listView.setAdapter(studentAdapter);
        } else {
            studentWinner = (Student) data.getSerializableExtra("back");
            nameWinner.setText(studentWinner.getName());
            bestScore.setText(Integer.toString(studentWinner.getScore()));
        }
    }

    public void changeWinner(View view) {
        Intent intent = new Intent(HighScoreActivity.this, InformationSpecificStudent.class);
        intent.putExtra("student", studentWinner);
        intent.putExtra("rank", 1);
        startActivityForResult(intent, 3);
    }
}
