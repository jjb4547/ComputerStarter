package com.example.computerstarter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private TextView questionTV, questionNumberTV;
    private Button option1Btn,option2Btn,option3Btn,option4Btn, backbutton;
    private ArrayList<QuizModule> quizModuleArrayList;
    int currentScore =0, questionAttempted = 1, currentPos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.quiz_layout);
        backbutton = findViewById(R.id.buttonback);
        questionTV = findViewById(R.id.idTVQuestion);
        questionNumberTV = findViewById(R.id.idTVQuestionAttempted);
        option1Btn = findViewById(R.id.idBtnOption1);
        option2Btn = findViewById(R.id.idBtnOption2);
        option3Btn = findViewById(R.id.idBtnOption3);
        option4Btn = findViewById(R.id.idBtnOption4);
        quizModuleArrayList = new ArrayList<>();
        getQuizQuestion(quizModuleArrayList);
        setDataToViews(currentPos);
        backbutton.setOnClickListener(view -> {
            finish();
        });
        option1Btn.setOnClickListener(view -> {
            if(quizModuleArrayList.get(currentPos).getAnswer().trim().equalsIgnoreCase(option1Btn.getText().toString().trim())){
                currentScore++;
            }
            questionAttempted++;
            currentPos++;
            setDataToViews(currentPos);
        });
        option2Btn.setOnClickListener(view -> {
            if(quizModuleArrayList.get(currentPos).getAnswer().trim().equalsIgnoreCase(option2Btn.getText().toString().trim())){
                currentScore++;
            }
            questionAttempted++;
            currentPos++;
            setDataToViews(currentPos);
        });
        option3Btn.setOnClickListener(view -> {
            if(quizModuleArrayList.get(currentPos).getAnswer().trim().equalsIgnoreCase(option3Btn.getText().toString().trim())){
                currentScore++;
            }
            questionAttempted++;
            currentPos++;
            setDataToViews(currentPos);
        });
        option4Btn.setOnClickListener(view -> {
            if(quizModuleArrayList.get(currentPos).getAnswer().trim().equalsIgnoreCase(option4Btn.getText().toString().trim())){
                currentScore++;
            }
            questionAttempted++;
            currentPos++;
            setDataToViews(currentPos);
        });

    }
    private void showBottomSheet(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.score_bottom_sheet, (LinearLayout)findViewById(R.id.idLLScore));
        TextView scoreTV = bottomSheetView.findViewById(R.id.idTVScore);
        Button restartQuizBtn = bottomSheetView.findViewById((R.id.idBtnRestart));
        Button backButton = bottomSheetView.findViewById(R.id.idBtnBack);
        scoreTV.setText("Your Score is \n"+currentScore+ "/7");
        restartQuizBtn.setOnClickListener(view -> {
            currentPos =0;
            setDataToViews(currentPos);
            questionAttempted =1;
            currentScore = 0;
            bottomSheetDialog.dismiss();
        });
        backButton.setOnClickListener(view -> {
           finish();
        });
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    private void setDataToViews(int currentPos){
        if(questionAttempted<=7)
            questionNumberTV.setText("Questions : "+questionAttempted + "/7");
        if(questionAttempted >7)
        {
            showBottomSheet();
        }else {
            questionTV.setText(quizModuleArrayList.get(currentPos).getQuestion());
            option1Btn.setText(quizModuleArrayList.get(currentPos).getOption1());
            option2Btn.setText(quizModuleArrayList.get(currentPos).getOption2());
            option3Btn.setText(quizModuleArrayList.get(currentPos).getOption3());
            option4Btn.setText(quizModuleArrayList.get(currentPos).getOption4());
        }
    }
    private void getQuizQuestion(ArrayList<QuizModule> quizModuleArrayList) {
        quizModuleArrayList.add(new QuizModule("What is your knowledge of computers ?", "Beginner","Intermediate", "Advanced", "I am a computer","I am a computer"));
        quizModuleArrayList.add(new QuizModule("Do you plan on building a computer ? (Answer has no affect)", "Yes", "No", "What is a computer", "computer who","Yes"));
        quizModuleArrayList.add(new QuizModule("What type of computers interest you ?", "School", "Gaming", "Art/Design", "Other","Yes"));
        quizModuleArrayList.add(new QuizModule("What is your budget for your build ?", "1-499", "500-999", "1000-1999", "2000+++++","Yes"));
        quizModuleArrayList.add(new QuizModule("If applicable, what area of study or career are you in ?", "STEM", "History", "Athlete", "I'm Useless","Yes"));
        quizModuleArrayList.add(new QuizModule("What operating system do you want to use ?", "Windows", "Mac", "Linux/OpenSource", "What is an operating system","Yes"));
        quizModuleArrayList.add(new QuizModule("What are you interests for computers ?", "Gaming", "Building", "Setups", "other","Yes"));

    }
}