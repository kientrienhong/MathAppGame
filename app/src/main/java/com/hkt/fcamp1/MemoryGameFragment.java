package com.hkt.fcamp1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;


public class MemoryGameFragment extends Fragment {
    public MemoryGameFragment() {
        // Required empty public constructor
    }
    int score;
    String receiveData;
    int colummn, temp1;
    GridView gridView;
    TextView timeCounting;
    int[] randomPosition;
    ImageAdapter imageAdapter2;
    Random random;
    int count, countChoice, temp, currentElement;
    final int greenImage = R.drawable.white_rec;
    final int redImage = R.drawable.red_rec;
    CountDownTimer countDownTimer;
    ImageAdapter imageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_memory_game, container, false);
        random = new Random();
        timeCounting = view.findViewById(R.id.timeCounting);
        gridView = view.findViewById(R.id.gridView);
        createTimeCounting();
        colummn = 5;
        temp = colummn;
        count = 0;
        receiveData = this.getArguments().getString("Score");
        score = Integer.parseInt(receiveData);
        randomPosition = new int[temp];
        currentElement = 0;


        for (int i = 0; i < colummn; i++) {
            int temp = (int) getRandomIntegerBetweenRange(0, colummn * colummn - 1);
            if (checkExisted(randomPosition, temp)){
                i--;
            } else {
                randomPosition[i] = temp;
                countChoice++;
                currentElement++;
            }
        }
        gridView.setNumColumns(colummn);

        imageAdapter = new ImageAdapter(getActivity(), colummn * colummn, randomPosition, 0);
        imageAdapter2 = new ImageAdapter(getActivity(), colummn * colummn, randomPosition, 1);
        gridView.setAdapter(imageAdapter);
        return view;
    }

    public boolean checkExisted (int randomPosition[], int numberWantedToCheck){
        for(int i = 0; i < currentElement; i++){
            if (randomPosition[i] == numberWantedToCheck){
                return true;
            }
        }

        return false;
    }

    public static double getRandomIntegerBetweenRange(double min, double max){

        double x = (int)(Math.random()*((max-min)+1))+min;

        return x;

    }

    public void createTimeCounting(){
        countDownTimer= new CountDownTimer(3000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {
                gridView.setClickable(true);
                gridView.setFocusable(true);
                gridView.setEnabled(false);

                timeCounting.setVisibility(View.VISIBLE);
                timeCounting.setText(Integer.toString((int) (millisUntilFinished / 1000.0)));
            }

            @Override
            public void onFinish() {
                gridView.setClickable(false);
                gridView.setFocusable(false);
                gridView.setEnabled(true);
                temp1 = 0;
                timeCounting.setVisibility(View.INVISIBLE);
                gridView.setAdapter(imageAdapter2);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View imageview, int pos,
                                            long arg3) {
                        Log.i("Count", Integer.toString(count));
                        Log.i("Count choice", Integer.toString(countChoice));
                        count = countChoice;
                        for (int i = 0; i < randomPosition.length; i++){
                            if (pos == randomPosition[i]){
                                GameActivity gameActivity = (GameActivity) getActivity();
                                gameActivity.receivedPoint(score);
                                ImageView imageView = (ImageView) imageview;
                                imageView.setImageResource(R.drawable.orange_rec);
                                imageView.setClickable(true);
                                imageView.setFocusable(true);
                                temp1++;
                                count = temp1;
                                randomPosition[i] = -1;
                                break;
                            } else{
                                ImageView imageView = (ImageView) imageview;
                                imageView.setImageResource(R.drawable.red_rec);
                                imageView.setClickable(true);
                                imageView.setFocusable(true);
                            }
                        }

                        if (temp1 == countChoice){
                            score += 2;
                        }

                        if (count == countChoice){
                            GameActivity gameActivity = (GameActivity) getActivity();
                            gameActivity.receivedPoint(score);
                            count = 0;
                            countChoice = 0;
                            randomPosition = new int[colummn];
                            currentElement = 0;
                            for (int i = 0; i < colummn; i++) {
                                int temp = (int) getRandomIntegerBetweenRange(0, colummn * colummn - 1);
                                if (checkExisted(randomPosition, temp)){
                                    i--;
                                } else {
                                    randomPosition[i] = temp;
                                    countChoice++;
                                    currentElement++;
                                }
                            }

                            imageAdapter.setRandomPosition(randomPosition);
                            imageAdapter.setAmount(colummn * colummn);
                            imageAdapter2.setRandomPosition(randomPosition);
                            imageAdapter2.setAmount(colummn * colummn);
                            gridView.setNumColumns(colummn);
                            gridView.setAdapter(null);
                            gridView.setAdapter(imageAdapter);
                            gridView.setFocusable(true);
                            gridView.setClickable(true);
                            countDownTimer.cancel();
                            countDownTimer.start();

                        }
                    }
                });

            }

        }.start();
    }

}
