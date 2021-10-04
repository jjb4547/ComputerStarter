package com.example.computerstarter;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Results_Activity extends AppCompatActivity {
    private TextView questions;
    private Button backbutton;
    private ArrayList<QuizModule> quizModuleArrayList;
    private ArrayList<String> quizanswers;
    private int currentPos;
    RelativeLayout layout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);
        questions = findViewById(R.id.idQuestions);
        backbutton = findViewById(R.id.backbutton);
        currentPos = 0;
        layout = findViewById(R.id.relativelayout);
        layout.setOnTouchListener(new OnSwipeTouchListener(Results_Activity.this){
            @Override
            public void onSwipeRight(){
                super.onSwipeRight();
                finish();
            }
        });
        quizanswers = getIntent().getStringArrayListExtra("List");
        StringBuilder content = new StringBuilder();
        String bracket = ") ";
        int j = 1;
        for(int i =0; i<quizanswers.size();i++){
            if(i%2==0){
                content.append(j++);
                content.append(bracket);
            }
            content.append(quizanswers.get(i));
            content.append('\n');
        }
            questions.setText(content.toString());
        backbutton.setOnClickListener(view -> {
            finish();
        });

    }

}
