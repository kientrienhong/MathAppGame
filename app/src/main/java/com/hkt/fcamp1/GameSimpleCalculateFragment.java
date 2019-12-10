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
import android.widget.TextView;

import java.util.Random;


public class GameSimpleCalculateFragment extends Fragment {

    TextView question;
    Button answerButton1, answerButton2, answerButton3, answerButton4;
    GridLayout gridLayout;
    int randomQuestion1, randomQuestion2, result, operand, score;
    Random random;
    String questionString, receiveData;
    int[] answer;

    public static GameSimpleCalculateFragment newInstance(){
        return new  GameSimpleCalculateFragment();
    }

    public void generateRandomNumber(int difficulty){
        operand = (int) getRandomIntegerBetweenRange(0, 3);
        randomQuestion1 = (int) getRandomIntegerBetweenRange(difficulty - 7, difficulty + 10);
        randomQuestion2 = (int) getRandomIntegerBetweenRange(difficulty - 7, difficulty + 10);
        switch(operand){

            case 0 : {
                result = randomQuestion1 + randomQuestion2;
                questionString = Integer.toString(randomQuestion1) + " + " + Integer.toString(randomQuestion2);
                break;
            }

            case 1 : {
                result = randomQuestion1 - randomQuestion2;
                questionString = Integer.toString(randomQuestion1) + " - " + Integer.toString(randomQuestion2);
                break;
            }

            case 2 : {
                if (randomQuestion2 > 11){
                    randomQuestion2 = (int) getRandomIntegerBetweenRange(2, 11);
                }
                result = randomQuestion1 * randomQuestion2;

                questionString = Integer.toString(randomQuestion1) + " * " + Integer.toString(randomQuestion2);
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
                break;
            }
        }

        question.setText(questionString);
    }

    public static double getRandomIntegerBetweenRange(double min, double max){

        double x = (int)(Math.random()*((max-min)+1))+min;

        return x;

    }

    public void setAnswerForButton(int[] answer){
        answerButton1.setText(Integer.toString(answer[0]));
        answerButton2.setText(Integer.toString(answer[1]));
        answerButton3.setText(Integer.toString(answer[2]));
        answerButton4.setText(Integer.toString(answer[3]));
    }

    public void generateRandomAnswer(int[] answer, int result){
        int positionLeftAnswer = random.nextInt(4);
        for (int i = 0; i < 4; i++){
            if (i == positionLeftAnswer){
                answer[i] = result;
            } else {
                answer[i] = (int) getRandomIntegerBetweenRange(result - 10, result + 10);

                while (answer[i] == result){
                    answer[i] = (int) getRandomIntegerBetweenRange(result - 20, result + 20);
                }

                for (int j = 0; j <= i; j++){
                    if (answer[i] == answer[j]){
                        answer[i] = (int) getRandomIntegerBetweenRange(result - 20, result + 20);
                        break;
                    }
                }
            }
        }
    }


    public void setOnClickForButton(){
        answerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer[0] == result) {
                    score++;
                    GameActivity gameActivity = (GameActivity) getActivity();
                    int choiceGame = (int) getRandomIntegerBetweenRange(1, 2);
                    gameActivity.receivedData(score, choiceGame);
                } else {
                    score--;
                    GameActivity gameActivity = (GameActivity) getActivity();
                    int choiceGame = (int) getRandomIntegerBetweenRange(1, 2);
                    gameActivity.receivedData(score, choiceGame);
                }

            }
        });

        answerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer[1] == result) {
                    score++;
                    GameActivity gameActivity = (GameActivity) getActivity();
                    int choiceGame = (int) getRandomIntegerBetweenRange(1, 2);
                    gameActivity.receivedData(score, choiceGame);
                } else {
                    score--;
                    GameActivity gameActivity = (GameActivity) getActivity();
                    int choiceGame = (int) getRandomIntegerBetweenRange(1, 2);
                    gameActivity.receivedData(score, choiceGame);
                }
            }
        });

        answerButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer[2] == result) {
                    score++;
                    GameActivity gameActivity = (GameActivity) getActivity();
                    int choiceGame = (int) getRandomIntegerBetweenRange(1, 2);
                    gameActivity.receivedData(score, choiceGame);
                } else {
                    GameActivity gameActivity = (GameActivity) getActivity();
                    int choiceGame = (int) getRandomIntegerBetweenRange(1, 2);
                    gameActivity.receivedData(score, choiceGame);
                }
            }
        });

        answerButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer[3] == result) {
                    score++;
                    GameActivity gameActivity = (GameActivity) getActivity();
                    int choiceGame = (int) getRandomIntegerBetweenRange(1, 2);
                    gameActivity.receivedData(score, choiceGame);
                } else {
                    score--;
                    GameActivity gameActivity = (GameActivity) getActivity();
                    int choiceGame = (int) getRandomIntegerBetweenRange(1, 2);
                    gameActivity.receivedData(score, choiceGame);
                }
            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_simple_calculate, container, false);
        question = (TextView) view.findViewById(R.id.questions);
        answerButton1 = (Button) view.findViewById(R.id.answer1) ;
        answerButton2 = (Button) view.findViewById(R.id.answer2) ;
        answerButton3 = (Button) view.findViewById(R.id.answer3) ;
        answerButton4 = (Button) view.findViewById(R.id.answer4) ;
        gridLayout = view.findViewById(R.id.gridLayout);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        receiveData = this.getArguments().getString("Score");
        score = Integer.parseInt(receiveData);
        random = new Random();
        answer = new int[4] ;
        questionString = new String();
        generateRandomNumber(10);
        generateRandomAnswer(answer, result);
        setAnswerForButton(answer);
        setOnClickForButton();
    }
}
