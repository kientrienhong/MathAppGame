package com.hkt.fcamp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class InformationSpecificStudent extends AppCompatActivity {

    int rank;
    Student temp;
    EditText name, phone;
    TextView score, rankTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_specific_student);
        settingView();
        Intent i = getIntent();
        temp = (Student) i.getSerializableExtra("student");
        rank = i.getIntExtra("rank", 0);
        rank += 2;
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        name.setText(temp.getName());
        phone.setText(temp.getPhoneNumber());
        score.setText(Integer.toString(temp.getScore()));
        rankTextView.setText(Integer.toString(rank));
    }

    public void settingView(){
        name = findViewById(R.id.editText);
        phone = findViewById(R.id.phoneInput);
        score = findViewById(R.id.score);
        rankTextView = findViewById(R.id.textView8);
    }

    public void submit(View view) {
        temp.setPhoneNumber(phone.getText().toString());
        temp.setName(name.getText().toString());
        Intent intent=new Intent();
        intent.putExtra("back", temp);
        if (rank == 1){
            setResult(3, intent);
            finish();
        } else {
            rank -= 2;
            intent.putExtra("rank", rank);
            setResult(2,intent);
            finish();
        }

    }
}
