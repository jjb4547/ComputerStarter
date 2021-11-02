package com.example.computerstarter;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Results_Activity extends AppCompatActivity {
    private TextView questions;
    private ArrayList<QuizModule> quizModuleArrayList;
    private ArrayList<String> quizanswers;
    private int currentPos;
    RelativeLayout layout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);
        getSupportActionBar().setTitle("Results");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        questions = findViewById(R.id.idQuestions);
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

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            this.finish();
            return true;
        }else
            return super.onOptionsItemSelected(item);
    }
}
