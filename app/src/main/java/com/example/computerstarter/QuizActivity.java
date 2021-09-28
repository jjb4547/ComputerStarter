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
    private Button option1Btn;
    private Button option2Btn;
    private Button option3Btn;
    private Button option4Btn;
    private ArrayList<QuizModule> quizModuleArrayList;
    int currentScore =0, questionAttempted = 1, currentPos = 0, num_of_questions =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.quiz_layout);
        Button backbutton = findViewById(R.id.buttonback);
        questionTV = findViewById(R.id.idTVQuestion);
        questionNumberTV = findViewById(R.id.idTVQuestionAttempted);
        option1Btn = findViewById(R.id.idBtnOption1);
        option2Btn = findViewById(R.id.idBtnOption2);
        option3Btn = findViewById(R.id.idBtnOption3);
        option4Btn = findViewById(R.id.idBtnOption4);
        quizModuleArrayList = new ArrayList<>();
        num_of_questions = getActivityName(quizModuleArrayList);
        setDataToViews(currentPos,num_of_questions);
        backbutton.setOnClickListener(view -> {
            finish();
        });
        option1Btn.setOnClickListener(view -> {
            if(quizModuleArrayList.get(currentPos).getAnswer().trim().equalsIgnoreCase(option1Btn.getText().toString().trim())){
                currentScore++;
            }
            questionAttempted++;
            currentPos++;
            setDataToViews(currentPos,num_of_questions);
        });
        option2Btn.setOnClickListener(view -> {
            if(quizModuleArrayList.get(currentPos).getAnswer().trim().equalsIgnoreCase(option2Btn.getText().toString().trim())){
                currentScore++;
            }
            questionAttempted++;
            currentPos++;
            setDataToViews(currentPos,num_of_questions);
        });
        option3Btn.setOnClickListener(view -> {
            if(quizModuleArrayList.get(currentPos).getAnswer().trim().equalsIgnoreCase(option3Btn.getText().toString().trim())){
                currentScore++;
            }
            questionAttempted++;
            currentPos++;
            setDataToViews(currentPos,num_of_questions);
        });
        option4Btn.setOnClickListener(view -> {
            if(quizModuleArrayList.get(currentPos).getAnswer().trim().equalsIgnoreCase(option4Btn.getText().toString().trim())){
                currentScore++;
            }
            questionAttempted++;
            currentPos++;
            setDataToViews(currentPos,num_of_questions);
        });

    }
    private void showBottomSheet(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.score_bottom_sheet, (LinearLayout)findViewById(R.id.idLLScore));
        TextView scoreTV = bottomSheetView.findViewById(R.id.idTVScore);
        Button restartQuizBtn = bottomSheetView.findViewById((R.id.idBtnRestart));
        Button backButton = bottomSheetView.findViewById(R.id.idBtnBack);
        scoreTV.setText("Your Score is \n"+currentScore+ "/"+num_of_questions);
        restartQuizBtn.setOnClickListener(view -> {
            currentPos =0;
            setDataToViews(currentPos,num_of_questions);
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

    private void setDataToViews(int currentPos, int num_of_questions){
        if(questionAttempted<=num_of_questions)
            questionNumberTV.setText("Questions : "+questionAttempted + "/"+num_of_questions);
        if(questionAttempted >num_of_questions)
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
    //Different Quizzes
    private void getQuizQuestion_Education(ArrayList<QuizModule> quizModuleArrayList) {
        quizModuleArrayList.add(new QuizModule("What is your knowledge of computers ?", "Beginner","Intermediate", "Advanced", "I am a computer","I am a computer"));
        quizModuleArrayList.add(new QuizModule("Do you plan on upgrading a computer ? (Answer has no affect)", "Yes", "No", "What is a computer", "computer who","Yes"));
        quizModuleArrayList.add(new QuizModule("What type of computers interest you ?", "School", "Gaming", "Art/Design", "Other","Yes"));
        quizModuleArrayList.add(new QuizModule("What is your budget for your build ?", "1-499", "500-999", "1000-1999", "2000+++++","Yes"));
        quizModuleArrayList.add(new QuizModule("If applicable, what area of study or career are you in ?", "STEM", "History", "Athlete", "I'm Useless","Yes"));
    }
    private void getQuizQuestion_Social(ArrayList<QuizModule> quizModuleArrayList) {
        quizModuleArrayList.add(new QuizModule("Choose up to  10 subjects that interest you: ", "#Beginner","#Intermediate", "#Advanced", "#I am a computer","#I am a computer"));
    }
    private void getQuizQuestion_Building(ArrayList<QuizModule> quizModuleArrayList) {
        quizModuleArrayList.add(new QuizModule("What is your estimated budget?", "0-500","500-1000","1000-2000", "2000+","2000+"));
        quizModuleArrayList.add(new QuizModule("What kind of computer are you looking to build?", "Gaming","Design","Work", "Engineering","Engineering"));
        quizModuleArrayList.add(new QuizModule("Intel or Ryzen?", "Intel","Ryzen","What are you talking about?", "Arm","Arm"));
    }
    private void getQuizQuestion_Login(ArrayList<QuizModule> quizModuleArrayList) {
        quizModuleArrayList.add(new QuizModule("What is your estimated budget?", "0-500","500-1000","1000-2000", "2000+","2000+"));
        quizModuleArrayList.add(new QuizModule("What kind of computer are you looking to build?", "Gaming","Design","Work", "Engineering","Engineering"));
        quizModuleArrayList.add(new QuizModule("Intel or Ryzen?", "Intel","Ryzen","What are you talking about?", "Arm","Arm"));
    }
    private int getActivityName(ArrayList<QuizModule> quizModuleArrayList){
        Intent intent = this.getIntent();
        quizModuleArrayList.clear();
        if(intent!=null){
            String name = intent.getExtras().getString("ID");
            switch(name){
                case "Education":
                    getQuizQuestion_Education(quizModuleArrayList);
                    break;
                case "Building":
                    getQuizQuestion_Building(quizModuleArrayList);
                    break;
                case "Social":
                    getQuizQuestion_Social(quizModuleArrayList);
                    break;
                case "Login":
                    getQuizQuestion_Login(quizModuleArrayList);
                    break;
                default:
                    break;
            }
        }
        return quizModuleArrayList.size();
    }

}