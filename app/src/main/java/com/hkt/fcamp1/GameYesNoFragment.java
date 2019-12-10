package com.hkt.fcamp1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;


public class GameYesNoFragment extends Fragment {
    ImageButton yes, no;
    GridLayout gridLayout;
    int randomQuestion1, randomQuestion2, result, operand, yesNoRandom, score, wrongAnswer;
    Random random;
    String questionString, receiveData;
    TextView expression1, expression2, resultTextView, operandTextView;
    int[] answer;
    boolean memmoryGame;
    public static GameYesNoFragment newInstance(){
        return new GameYesNoFragment();
    }

    public void generateRandomNumber(int difficulty, int yesNoRandom){
        operand = (int) getRandomIntegerBetweenRange(0, 3);
        randomQuestion1 = (int) getRandomIntegerBetweenRange(difficulty - 7, difficulty + 10);
        randomQuestion2 = (int) getRandomIntegerBetweenRange(difficulty - 7, difficulty + 10);
        switch(operand){

            case 0 : {
                result = randomQuestion1 + randomQuestion2;
                questionString = Integer.toString(randomQuestion1) + " + " + Integer.toString(randomQuestion2);
                operandTextView.setText("+");
                break;
            }

            case 1 : {
                result = randomQuestion1 - randomQuestion2;
                questionString = Integer.toString(randomQuestion1) + " - " + Integer.toString(randomQuestion2);
                operandTextView.setText("-");
                break;
            }

            case 2 : {
                if (randomQuestion2 > 11){
                    randomQuestion2 = (int) getRandomIntegerBetweenRange(2, 11);
                }
                result = randomQuestion1 * randomQuestion2;

                questionString = Integer.toString(randomQuestion1) + " * " + Integer.toString(randomQuestion2);
                operandTextView.setText("*");
                break;
            }

            case 3 : {
                if (randomQuestion1 < randomQuestion2){
                    int temp = randomQuestion1;
                    randomQuestion1 = randomQuestion2;
                    randomQuestion2 = temp;
                }

                if (randomQuestion2 == 0){
                    randomQuestion2 = randomQuestion2 + 1;
                }

                if (randomQuestion1 % randomQuestion2 != 0){
                    int remain = randomQuestion2 - (randomQuestion1 % randomQuestion2);
                    randomQuestion1 += remain;
                }
                result = randomQuestion1 / randomQuestion2;

                questionString = Integer.toString(randomQuestion1) + " / " + Integer.toString(randomQuestion2);
                operandTextView.setText("/");
                break;
            }
        }

        expression1.setText(Integer.toString(randomQuestion1));
        expression2.setText(Integer.toString(randomQuestion2));
        if (yesNoRandom == 0){
            do {
                wrongAnswer = (int) getRandomIntegerBetweenRange(result - 20, result + 10);
            }while (wrongAnswer == result);

            resultTextView.setText(Integer.toString(wrongAnswer));
        } else {
            resultTextView.setText(Integer.toString(result));
        }
    }

    public static double getRandomIntegerBetweenRange(double min, double max){

        double x = (int)(Math.random()*((max-min)+1))+min;

        return x;
    }

    public void setOnlick(){
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yesNoRandom == 0){
                    score--;
                    GameActivity gameActivity = (GameActivity) getActivity();
                    if (memmoryGame == true){
                        gameActivity.receivedData(score, 3);
                    } else {
                        int choiceGame = (int) getRandomIntegerBetweenRange(0, 1);
                        gameActivity.receivedData(score, choiceGame);
                    }
                } else {
                    score++;
                    GameActivity gameActivity = (GameActivity) getActivity();
                    if (memmoryGame == true){
                        gameActivity.receivedData(score, 3);
                    } else {
                        int choiceGame = (int) getRandomIntegerBetweenRange(0, 1);
                        gameActivity.receivedData(score, choiceGame);
                    }
                }
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yesNoRandom == 0){
                    score++;
                    GameActivity gameActivity = (GameActivity) getActivity();
                    if (memmoryGame == true){
                        gameActivity.receivedData(score, 3);
                    } else {
                        int choiceGame = (int) getRandomIntegerBetweenRange(0, 1);
                        gameActivity.receivedData(score, choiceGame);
                    }
                } else {
                    score--;
                    GameActivity gameActivity = (GameActivity) getActivity();
                    if (memmoryGame == true){
                        gameActivity.receivedData(score, 3);
                    } else {
                        int choiceGame = (int) getRandomIntegerBetweenRange(0, 1);
                        gameActivity.receivedData(score, choiceGame);
                    }
                }
            }
        });
    }

    public void setView (View view){
        yes = view.findViewById(R.id.button1);
        no = view.findViewById(R.id.button2);
        expression1 = view.findViewById(R.id.expresion1);
        expression2 = view.findViewById(R.id.expresion2);
        resultTextView = view.findViewById(R.id.result);
        operandTextView = view.findViewById(R.id.operand);
        gridLayout = view.findViewById(R.id.gridLayout);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_yes_no, container, false);
        setView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        receiveData = this.getArguments().getString("Score");
        memmoryGame = this.getArguments().getBoolean("Memory");
        score = Integer.parseInt(receiveData);
        yesNoRandom = (int) getRandomIntegerBetweenRange(0, 1);
        generateRandomNumber(10, yesNoRandom);
        setOnlick();
    }
}
