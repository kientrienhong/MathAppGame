package com.hkt.fcamp1;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;


public class GameAdvanceCalculateFragment extends Fragment {

    GridLayout containButoon;
    TextView operater, operand1TextView, operand2TextView, resultTextView, scoreTextView;
    Button button1, button2, button3, button4, button5, button6, button7, button8;
    Random random;
    double[] rightAnswer, leftAnswer;
    int leftChoiceExpression, rightChoiceExpression, chosenLeftAnswer, chosenRightAnswer;
    StringBuffer answer;
    StringBuffer question;
    String receiveData;
    int randomChoice, expression1, expression2, result, operand, difficulty, score, randomGame, leftChoiceTest, rightChoiceTest, resultTest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_advance_calculate, container, false);
        settingView(view);

        return view;
    }


    // cái onStart coi nó như là hàm main đi nha
    @Override
    public void onStart() {
        super.onStart();
        random = new Random();
        rightAnswer = new double[4];
        leftAnswer = new double[4];
        receiveData = this.getArguments().getString("Score");
        score = Integer.parseInt(receiveData);
        Log.i("Score = ", Integer.toString(score));
        difficulty = score * 2 + 10;
        answer = new StringBuffer();
        question = new StringBuffer();
        generateRandomNumber(difficulty);
        generateRandomAnswerForButtons(expression1, expression2, result);
        setOnClick();
    }


    // hàm lấy số random trong khoảng
    public static double getRandomIntegerBetweenRange(double min, double max){

        double x = (int)(Math.random()*((max-min)+1))+min;

        return x;

    }

    // ở đây t tạo 2 mảng, mỗi mảng chứa 4 đáp á cho 1 hàng á
    // positionLeftAnswer + positionRightAnswer là t random vị trí đúng của đáp án nếu chạy đúng tới
    // vị trí đó thì nó quăng vô còn không thì random

    public void generateRandomAnswer(double[] leftAnswer, double[] rightAnswer, int expression1, int expression2){
        int positionLeftAnswer = random.nextInt(4);
        int positionRightAnswer = random.nextInt(4);
        for (int i = 0; i < 4; i++){
            if (i == positionLeftAnswer){
                leftAnswer[i] = expression1;
            } else {

                int randomRange = (int) getRandomIntegerBetweenRange(0, 1);

                if (randomRange == 0){
                    leftAnswer[i] = getRandomIntegerBetweenRange(expression1 - 20, expression1 - 2);
                } else {
                    leftAnswer[i] = getRandomIntegerBetweenRange(expression1 + 2, expression1 + 20);
                }
            }

            if (i == positionRightAnswer){
                rightAnswer[i] = expression2;
            } else{
                int randomRange = (int) getRandomIntegerBetweenRange(0, 1);

                if (randomRange == 0){
                    rightAnswer[i] = getRandomIntegerBetweenRange(expression2 - 20, expression2 - 2);
                } else {
                    rightAnswer[i] = getRandomIntegerBetweenRange(expression2 + 2, expression2 + 20);
                }
            }
        }
    }

    // này là đưa 2 mảng lên view cho nút á nên khỏi coi cái này
    public void setTextForAnswerButtons(double[] leftAnswer, double[] rightAnswer){
        button1.setText(Double.toString(leftAnswer[0]));
        button2.setText(Double.toString(leftAnswer[1]));
        button3.setText(Double.toString(leftAnswer[2]));
        button4.setText(Double.toString(leftAnswer[3]));

        button5.setText(Double.toString(rightAnswer[0]));
        button6.setText(Double.toString(rightAnswer[1]));
        button7.setText(Double.toString(rightAnswer[2]));
        button8.setText(Double.toString(rightAnswer[3]));
    }

    // này cũng đưa đáp án lên view cho nút
    public void setButtonFromRandomChoice(int chosenLeftAnswer, int chosenRightAnswer, int randomChoice){
        if (randomChoice == 0){
            operand1TextView.setText(Integer.toString(chosenLeftAnswer));
            operand2TextView.setText(Integer.toString(chosenRightAnswer));
            if (leftChoiceExpression == 0){
                operand1TextView.setText("?");
            }

            if (rightChoiceExpression == 0){
                operand2TextView.setText("?");
            }

        } else if (randomChoice == 1){
            operand1TextView.setText(Integer.toString(chosenLeftAnswer));
            resultTextView.setText(Integer.toString(chosenRightAnswer));

            if (leftChoiceExpression == 0){
                operand1TextView.setText("?");
            }

            if (rightChoiceExpression == 0){
                resultTextView.setText("?");
            }

        } else {
            operand2TextView.setText(Integer.toString(chosenLeftAnswer));
            resultTextView.setText(Integer.toString(chosenRightAnswer));

            if (leftChoiceExpression == 0){
                operand2TextView.setText("?");
            }

            if (rightChoiceExpression == 0){
                resultTextView.setText("?");
            }
        }
    }

    // hàm này là xài 2 hàm trên (setTextForAnswerButtons, setButtonFromRandomChoice)
    public void generateRandomAnswerForButtons(int expression1, int expression2, int result){
        int resultOfChoice = generateRandomChoice();
        switch (resultOfChoice){
            case 0: {
                generateRandomAnswer(leftAnswer, rightAnswer, expression1, expression2);
                setTextForAnswerButtons(leftAnswer, rightAnswer);
                break;
            }

            case 2: {
                generateRandomAnswer(leftAnswer, rightAnswer, expression2, result);
                setTextForAnswerButtons(leftAnswer, rightAnswer);
                break;
            }

            case 1: {
                generateRandomAnswer(leftAnswer, rightAnswer, expression1, result);
                setTextForAnswerButtons(leftAnswer, rightAnswer);
                break;
            }
        }
    }

    public void checkAnswer (){
            leftChoiceTest = (int) Double.parseDouble((String) operand1TextView.getText());
            rightChoiceTest = (int) Double.parseDouble((String) operand2TextView.getText());
            resultTest = (int) Double.parseDouble((String) resultTextView.getText());
            if (operand == 0){
                if((leftChoiceTest + rightChoiceTest) == resultTest){
                    score += 3;
                    GameActivity gameActivity = (GameActivity) getActivity();
                    gameActivity.receivedData(score, randomGame);
                } else {
                    GameActivity gameActivity = (GameActivity) getActivity();
                    gameActivity.receivedData(score, randomGame);
                }
            } else if (operand == 1){
                if((leftChoiceTest - rightChoiceTest) == resultTest){
                    score += 3;
                    GameActivity gameActivity = (GameActivity) getActivity();
                    gameActivity.receivedData(score, randomGame);
                } else {
                    GameActivity gameActivity = (GameActivity) getActivity();
                    gameActivity.receivedData(score, randomGame);
                }
            } else if (operand == 2){
                if((leftChoiceTest * rightChoiceTest) == resultTest){
                    score += 3;
                    GameActivity gameActivity = (GameActivity) getActivity();
                    gameActivity.receivedData(score, randomGame);
                } else {
                    GameActivity gameActivity = (GameActivity) getActivity();
                    gameActivity.receivedData(score, randomGame);
                }
            } else {
                if ((leftChoiceTest / rightChoiceTest) == resultTest) {
                    score += 3;
                    GameActivity gameActivity = (GameActivity) getActivity();
                    gameActivity.receivedData(score, randomGame);
                } else {
                    GameActivity gameActivity = (GameActivity) getActivity();
                    gameActivity.receivedData(score, randomGame);
                }
            }
    }

    // hàm tạo event onClick cho nút nên tụi m coi button1 thôi khỏi coi mấy cái khác
    public void setOnClick(){
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenLeftAnswer = (int) Double.parseDouble((String) button1.getText());
                leftChoiceExpression = 1;
                setButtonFromRandomChoice(chosenLeftAnswer, chosenRightAnswer, randomChoice);

                // đây là khi cả 2 hàng đều được ấn là nó tự động check coi nó có giống với cái đề hay không
                // t tạo đề bằng String á là khi t ấn 2 hàng thì t tự động ghép 2 số đó với nhau thành string rồi t đem đi so với String đề
                if (leftChoiceExpression == 1 && rightChoiceExpression == 1){
                    leftChoiceExpression = 0;
                    rightChoiceExpression = 0;
                    chosenLeftAnswer = 0;
                    chosenRightAnswer = 0;
                    checkAnswer();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenLeftAnswer = (int) Double.parseDouble((String) button2.getText());
                leftChoiceExpression = 1;
                setButtonFromRandomChoice(chosenLeftAnswer, chosenRightAnswer, randomChoice);

                if (leftChoiceExpression == 1 && rightChoiceExpression == 1){
                    leftChoiceExpression = 0;
                    rightChoiceExpression = 0;
                    chosenLeftAnswer = 0;
                    chosenRightAnswer = 0;
                    checkAnswer();
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenLeftAnswer = (int) Double.parseDouble((String) button3.getText());
                leftChoiceExpression = 1;

                setButtonFromRandomChoice(chosenLeftAnswer, chosenRightAnswer, randomChoice);

                if (leftChoiceExpression == 1 && rightChoiceExpression == 1){
                    leftChoiceExpression = 0;
                    rightChoiceExpression = 0;
                    chosenLeftAnswer = 0;
                    chosenRightAnswer = 0;
                    checkAnswer();
                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenLeftAnswer = (int) Double.parseDouble((String) button4.getText());
                leftChoiceExpression = 1;

                setButtonFromRandomChoice(chosenLeftAnswer, chosenRightAnswer, randomChoice);
                if (leftChoiceExpression == 1 && rightChoiceExpression == 1){
                    leftChoiceExpression = 0;
                    rightChoiceExpression = 0;
                    chosenLeftAnswer = 0;
                    chosenRightAnswer = 0;
                    checkAnswer();
                }
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenRightAnswer = (int) Double.parseDouble((String) button5.getText());
                rightChoiceExpression = 1;

                setButtonFromRandomChoice(chosenLeftAnswer, chosenRightAnswer, randomChoice);
                if (leftChoiceExpression == 1 && rightChoiceExpression == 1){
                    leftChoiceExpression = 0;
                    rightChoiceExpression = 0;
                    chosenLeftAnswer = 0;
                    chosenRightAnswer = 0;
                    checkAnswer();
                }
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenRightAnswer = (int) Double.parseDouble((String) button6.getText());
                rightChoiceExpression = 1;

                setButtonFromRandomChoice(chosenLeftAnswer, chosenRightAnswer, randomChoice);
                if (leftChoiceExpression == 1 && rightChoiceExpression == 1){
                    leftChoiceExpression = 0;
                    rightChoiceExpression = 0;
                    chosenLeftAnswer = 0;
                    chosenRightAnswer = 0;
                    checkAnswer();
                }
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenRightAnswer = (int) Double.parseDouble((String) button7.getText());
                rightChoiceExpression = 1;

                setButtonFromRandomChoice(chosenLeftAnswer, chosenRightAnswer, randomChoice);
                if (leftChoiceExpression == 1 && rightChoiceExpression == 1){
                    leftChoiceExpression = 0;
                    rightChoiceExpression = 0;
                    chosenLeftAnswer = 0;
                    chosenRightAnswer = 0;
                    checkAnswer();
                }
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenRightAnswer = (int) Double.parseDouble((String) button8.getText());
                rightChoiceExpression = 1;

                setButtonFromRandomChoice(chosenLeftAnswer, chosenRightAnswer, randomChoice);
                if (leftChoiceExpression == 1 && rightChoiceExpression == 1){
                    leftChoiceExpression = 0;
                    rightChoiceExpression = 0;
                    chosenLeftAnswer = 0;
                    chosenRightAnswer = 0;
                    checkAnswer();
                }
            }
        });
    }

    // này hàm lấy id nên không ảnh hưởng
    public void settingView(View view){
        operater = (TextView) view.findViewById(R.id.operater);
        operand1TextView = (TextView) view.findViewById(R.id.expresion);
        operand2TextView = (TextView) view.findViewById(R.id.expresion1);
        resultTextView = (TextView) view.findViewById(R.id.expresion2);
        button1 = (Button) view.findViewById(R.id.button1);
        button2 = (Button) view.findViewById(R.id.button2);
        button3 = (Button) view.findViewById(R.id.button3);
        button4 = (Button) view.findViewById(R.id.button4);
        button5 = (Button) view.findViewById(R.id.button5);
        button6 = (Button) view.findViewById(R.id.button6);
        button7 = (Button) view.findViewById(R.id.button7);
        button8 = (Button) view.findViewById(R.id.button8);
        containButoon = (GridLayout) view.findViewById(R.id.gridLayout2);
    }

    // này là hàm đưa lên view nên không ảnh hưởng
    public int generateRandomChoice (){
        randomChoice = random.nextInt(3);

        switch (randomChoice){
            case 0: {
                operand1TextView.setText("?");
                operand1TextView.setTextColor(Color.parseColor("#B85100"));
                operand2TextView.setText("?");
                operand2TextView.setTextColor(Color.parseColor("#008D7E"));
                return 0;
            }

            case 1: {
                operand1TextView.setText("?");
                operand1TextView.setTextColor(Color.parseColor("#B85100"));
                resultTextView.setText("?");
                resultTextView.setTextColor(Color.parseColor("#008D7E"));
                return 1;
            }

            case 2: {
                operand2TextView.setText("?");
                operand2TextView.setTextColor(Color.parseColor("#B85100"));
                resultTextView.setText("?");
                resultTextView.setTextColor(Color.parseColor("#008D7E"));
                return 2;
            }
        }

        return 0;
    }


    // này là hàm tạo đề
    public void generateRandomNumber(int difficulty){
        operand = random.nextInt(4);
        expression1 = (int) getRandomIntegerBetweenRange(difficulty - 7, difficulty + 10);
        expression2 = (int) getRandomIntegerBetweenRange(difficulty - 7, difficulty + 10);

        switch(operand){

            case 0 : {
                operater.setText("+");
                result = expression1 + expression2;
                break;
            }

            case 1 : {
                operater.setText("-");
                result = expression1 - expression2;
                break;
            }

            case 2 : {
                operater.setText("*");
                if (expression2 > 10){
                    expression2 = (int) getRandomIntegerBetweenRange(2, 10);
                }
                result = expression1 * expression2;
                break;
            }

            case 3 : {
                operater.setText("/");

                if (expression1 < expression2){
                    int temp = expression1;
                    expression1 = expression2;
                    expression2 = temp;
                }

                while (expression2 == 0){
                    expression2 = (int) getRandomIntegerBetweenRange(difficulty - 7, difficulty + 10);
                }

                if (expression1 % expression2 != 0){
                    int remain = expression2 - (expression1 % expression2);
                    expression1 += remain;
                }
                result = expression1 / expression2;
                break;
            }
        }

        do {
            randomGame = (int) getRandomIntegerBetweenRange(0, 2);
        } while (randomGame == 1);

        operand1TextView.setText(Integer.toString(expression1));
        operand2TextView.setText(Integer.toString(expression2));
        resultTextView.setText(Integer.toString(result));
        Log.i("Testing", Integer.toString(expression1) + " " + operater.getText() + " " + Integer.toString(expression2) + " = " + Integer.toString(result));
    }


    private void scaleContents(View rootView, View container)
    {
        // Compute the scaling ratio
        float xScale = (float)container.getWidth() / rootView.getWidth();
        float yScale = (float)container.getHeight() / rootView.getHeight();
        float scale = Math.min(xScale, yScale);

        // Scale our contents
        scaleViewAndChildren(rootView, scale);
    }
    // Scale the given view, its contents, and all of its children by the given factor.
    public static void scaleViewAndChildren(View root, float scale)
    {
        // Retrieve the view's layout information
        ViewGroup.LayoutParams layoutParams = root.getLayoutParams();
        // Scale the view itself
        if (layoutParams.width != ViewGroup.LayoutParams.FILL_PARENT &&
                layoutParams.width != ViewGroup.LayoutParams.WRAP_CONTENT)
        {
            layoutParams.width *= scale;
        }
        if (layoutParams.height != ViewGroup.LayoutParams.FILL_PARENT &&
                layoutParams.height != ViewGroup.LayoutParams.WRAP_CONTENT)
        {
            layoutParams.height *= scale;
        }

        // If this view has margins, scale those too
        if (layoutParams instanceof ViewGroup.MarginLayoutParams)
        {
            ViewGroup.MarginLayoutParams marginParams =
                    (ViewGroup.MarginLayoutParams)layoutParams;
            marginParams.leftMargin *= scale;
            marginParams.rightMargin *= scale;
            marginParams.topMargin *= scale;
            marginParams.bottomMargin *= scale;
        }

        // Set the layout information back into the view
        root.setLayoutParams(layoutParams);
        // Scale the view's padding
        root.setPadding(
                (int)(root.getPaddingLeft() * scale),
                (int)(root.getPaddingTop() * scale),
                (int)(root.getPaddingRight() * scale),
                (int)(root.getPaddingBottom() * scale));

        // If the root view is a TextView, scale the size of its text
        if (root instanceof TextView)
        {
            TextView textView = (TextView)root;
            textView.setTextSize(textView.getTextSize() * scale);
        }

        // If the root view is a ViewGroup, scale all of its children recursively
        if (root instanceof ViewGroup)
        {
            ViewGroup groupView = (ViewGroup)root;
            for (int cnt = 0; cnt < groupView.getChildCount(); ++cnt)
                scaleViewAndChildren(groupView.getChildAt(cnt), scale);
        }
    }

}
