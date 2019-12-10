package com.hkt.fcamp1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {

    private static final String FORMAT = "%02d:%02d";
    TextView timeCounting, scoreTextView, back;
    ImageButton pause;
    Dialog myDialog;
    Fragment simpleCalculate = new GameSimpleCalculateFragment();
    Fragment advanceCalculate = new GameAdvanceCalculateFragment();
    Fragment yesNoGame = new GameYesNoFragment();
    Fragment memoryGameFragment = new MemoryGameFragment();
    FragmentManager fm = null;
    Fragment active = null;
    Random random;
    int randomGame, score;
    long timeCurrent;
    ProgressBar progressBar;
    CountDownTimer countDownTimer;
    Bundle bundleSendData;
    Dialog dialog;
    ArrayList<Student> studentArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        settingView();

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
        myDialog = new Dialog(this);

        random = new Random();
        randomGame = (int) getRandomIntegerBetweenRange(0, 1);
        fm = getSupportFragmentManager();
        score = 0;
        changeFragment(randomGame);
        createTimeCounting(120000); //120000
        scoreTextView.setText("Score: 0");
    }

    @Override
    public void onBackPressed() {

    }

    public void pause(View view){
        dialog = new Dialog(GameActivity.this);
        dialog.setContentView(R.layout.dialog_pause);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog));
        dialog.show();
        countDownTimer.cancel();
        dialog.setCanceledOnTouchOutside(false);

        ImageView resume = dialog.findViewById(R.id.resume);
        ImageView restart = dialog.findViewById(R.id.restart);
        ImageView exit = dialog.findViewById(R.id.home);

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                createTimeCounting(timeCurrent);
                int temp = randomGame;
                do {
                    randomGame = (int) getRandomIntegerBetweenRange(0, 2);
                } while (temp == randomGame);
                changeFragment(randomGame);
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(GameActivity.this, Home.class);
                startActivity(intent);
            }
        });
    }

    public void receivedData(int scoreReceive, int randomGame){
        score = scoreReceive;
        scoreTextView.setText("Score: " + Integer.toString(score));
        if (timeCurrent <= 30000){
            randomGame = 3;
            Log.i("Test ", Integer.toString(randomGame));
        }
        changeFragment(randomGame);
    }

    public void receivedPoint (int scoreReceive){
        score = scoreReceive;
        scoreTextView.setText("Score: " + Integer.toString(score));
    }



    private void changeFragment(int randomGame){
        bundleSendData = new Bundle();
        switch(randomGame){
            case 0 :{
                bundleSendData.putString("Score", Integer.toString(score));
                simpleCalculate.setArguments(bundleSendData);

                if (active == null){
                    fm.beginTransaction().add(R.id.fragment_container, simpleCalculate).disallowAddToBackStack().commit();
                } else {
                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fm.beginTransaction().replace(R.id.fragment_container, simpleCalculate).commit();
                }
                active = simpleCalculate;
                break;
            }

            case 1:{
                bundleSendData.putString("Score", Integer.toString(score));
                advanceCalculate.setArguments(bundleSendData);
                if (active == null){
                    fm.beginTransaction().add(R.id.fragment_container, advanceCalculate).disallowAddToBackStack().commit();
                } else {
                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fm.beginTransaction().replace(R.id.fragment_container, advanceCalculate).commit();
                }
                active = advanceCalculate;
                break;
            }

            case 2:{
                bundleSendData.putString("Score", Integer.toString(score));
                yesNoGame.setArguments(bundleSendData);
                if (active == null){
                    fm.beginTransaction().add(R.id.fragment_container, yesNoGame).disallowAddToBackStack().commit();
                } else {
                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fm.beginTransaction().replace(R.id.fragment_container, yesNoGame).commit();
                }
                active = yesNoGame;
                break;
            }

            case 3:{
                bundleSendData.putString("Score", Integer.toString(score));
                memoryGameFragment.setArguments(bundleSendData);
                if (active == null){
                    fm.beginTransaction().add(R.id.fragment_container, memoryGameFragment).disallowAddToBackStack().commit();
                } else {
                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fm.beginTransaction().replace(R.id.fragment_container, memoryGameFragment).commit();
                }
                active = memoryGameFragment;
            }
        }
    }

    private void settingView() {
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);
        timeCounting = findViewById(R.id.timeCounting);
        scoreTextView = findViewById(R.id.score);
        pause = findViewById(R.id.pauseButton);
        back = findViewById(R.id.textView2);
        progressBar.setScaleY(3f);
    }

    public static double getRandomIntegerBetweenRange(double min, double max){

        double x = (int)(Math.random()*((max-min)+1))+min;

        return x;

    }

    public void createTimeCounting(final long timeCurrent1){
        countDownTimer = new CountDownTimer(timeCurrent1, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress((int) Math.round(millisUntilFinished / 1000.0));
                timeCounting.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                timeCurrent = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(GameActivity.this, AddNewStudent.class);
                intent.putExtra("score", score);
                startActivity(intent);
                finish();
            }

        }.start();
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



}
