package com.hkt.fcamp1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {
    private Context context;
    private List<Student> studentList;

    public StudentAdapter(Context context, List<Student> studentList) {
        super(context, 0 ,studentList);
        this.context = context;
        this.studentList = studentList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.item_student,parent,false);

        ViewGroup.LayoutParams params = listItem.getLayoutParams();
        params.height = 200;
        Student currentStudent = studentList.get(position);

        TextView rank = listItem.findViewById(R.id.rank);
        ImageView imageView = listItem.findViewById(R.id.imageView2);
        TextView name = listItem.findViewById(R.id.name);
        TextView score = listItem.findViewById(R.id.score);

        rank.setText(Integer.toString(position + 2));
        imageView.setBackgroundResource(R.drawable.rank);
        name.setText(currentStudent.getName());
        score.setText(Integer.toString(currentStudent.getScore()));
        listItem.setLayoutParams(params);
        return listItem;
    }
}
