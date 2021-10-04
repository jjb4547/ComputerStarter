package com.example.computerstarter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {
    private TextView questionTV, questionNumberTV;
    private Button option1Btn;
    private Button option2Btn;
    private Button option3Btn;
    private Button option4Btn;
    ArrayList<QuizModule> quizModuleArrayList;
    private ArrayList<String> quizAnswers;
    int currentScore =0, questionAttempted = 1, currentPos = 0, num_of_questions =0;
    RelativeLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_layout);
        layout = findViewById(R.id.relativelayout);
        layout.setOnTouchListener(new OnSwipeTouchListener(QuizActivity.this){
            @Override
            public void onSwipeRight(){
                super.onSwipeRight();
                finish();
            }
        });
        Button backbutton = findViewById(R.id.buttonback);
        questionTV = findViewById(R.id.idTVQuestion);
        questionNumberTV = findViewById(R.id.idTVQuestionAttempted);
        option1Btn = findViewById(R.id.idBtnOption1);
        option2Btn = findViewById(R.id.idBtnOption2);
        option3Btn = findViewById(R.id.idBtnOption3);
        option4Btn = findViewById(R.id.idBtnOption4);
        quizModuleArrayList = new ArrayList<>();
        quizAnswers = new ArrayList<>();
        num_of_questions = getActivityName(quizModuleArrayList);
        setDataToViews(currentPos,num_of_questions);
        backbutton.setOnClickListener(view -> {
            finish();
        });
        option1Btn.setOnClickListener(view -> {
            quizAnswers.add(quizModuleArrayList.get(currentPos).getQuestion());
            quizAnswers.add(quizModuleArrayList.get(currentPos).getOption1());
            questionAttempted++;
            currentPos++;
            setDataToViews(currentPos,num_of_questions);
        });
        option2Btn.setOnClickListener(view -> {
            quizAnswers.add(quizModuleArrayList.get(currentPos).getQuestion());
            quizAnswers.add(quizModuleArrayList.get(currentPos).getOption2());
            questionAttempted++;
            currentPos++;
            setDataToViews(currentPos,num_of_questions);
        });
        option3Btn.setOnClickListener(view -> {
            quizAnswers.add(quizModuleArrayList.get(currentPos).getQuestion());
            quizAnswers.add(quizModuleArrayList.get(currentPos).getOption3());
            questionAttempted++;
            currentPos++;
            setDataToViews(currentPos,num_of_questions);
        });
        option4Btn.setOnClickListener(view -> {
            quizAnswers.add(quizModuleArrayList.get(currentPos).getQuestion());
            quizAnswers.add(quizModuleArrayList.get(currentPos).getOption4());
            questionAttempted++;
            currentPos++;
            setDataToViews(currentPos,num_of_questions);
        });

    }
    private void showBottomSheet(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.score_bottom_sheet, (LinearLayout)findViewById(R.id.idLLScore));
        Button restartQuizBtn = bottomSheetView.findViewById(R.id.idBtnRestart);
        Button backButton = bottomSheetView.findViewById(R.id.idBtnBack);
        Button resultBtn = bottomSheetView.findViewById(R.id.buttonResult);
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
        resultBtn.setOnClickListener(view -> {
            Intent intent = new Intent(QuizActivity.this,Results_Activity.class);
            intent.putStringArrayListExtra("List",quizAnswers);
            startActivity(intent);
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
    protected void getQuizQuestion_Education(ArrayList<QuizModule> quizModuleArrayList) {
        quizModuleArrayList.add(new QuizModule("What is your knowledge of computers ?", "Beginner","Intermediate", "Advanced", "I am a computer","I am a computer"));
        quizModuleArrayList.add(new QuizModule("Do you plan on upgrading a computer ? (Answer has no affect)", "Yes", "No", "What is a computer", "computer who","Yes"));
        quizModuleArrayList.add(new QuizModule("What type of computers interest you ?", "School", "Gaming", "Art/Design", "Other","Yes"));
        quizModuleArrayList.add(new QuizModule("What is your budget for your build ?", "1-499", "500-999", "1000-1999", "2000+++++","Yes"));
        quizModuleArrayList.add(new QuizModule("If applicable, what area of study or career are you in ?", "STEM", "History", "Athlete", "I'm Useless","Yes"));
    }
    protected void getQuizQuestion_Social(ArrayList<QuizModule> quizModuleArrayList) {
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