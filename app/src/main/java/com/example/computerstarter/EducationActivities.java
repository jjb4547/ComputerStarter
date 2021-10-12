package com.example.computerstarter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EducationActivities extends AppCompatActivity {
    //gonna try to make this like quiz activity
    // for the title, paragraph and image
    //private TextView eduTitle,eduPara;
    //private ImageView eduPic;
    //arraylist of display options
    //private ArrayList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_activities);

        //changing the title
        String[] diffTitles ={"CPU","Motherboard","Memory(RAM)","Storage","Video Card(GPU)","Power Supply","Raspberry Pi and Possibilities", "Arduino and Possibilities"};
        TextView title = (TextView) findViewById(R.id.partTitle);
        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            int position = bundle.getInt("position");
            switch (position){
                case 0:
                    //CPU
                    title.setText(diffTitles[0]);
                    break;
                case 1:
                    //Motherboard
                    title.setText(diffTitles[1]);
                    break;
                case 2:
                    //Memory
                    title.setText(diffTitles[2]);
                    break;
                case 3:
                    //Storage
                    title.setText(diffTitles[3]);
                    break;
                case 4:
                    //Video Card
                    title.setText(diffTitles[4]);
                    break;
                case 5:
                    //Power Supply
                    title.setText(diffTitles[5]);
                    break;
                case 6:
                    //Raspberry Pi
                    title.setText(diffTitles[6]);
                    break;
                case 7:
                    //Arduino
                    title.setText(diffTitles[7]);
                    break;

            }
        }
        //changeTextforPart();
    }
    /*
    private void changeTextforPart(){
        String[] diffTitles ={"CPU","Motherboard","Memory(RAM)","Storage","Video Card(GPU)","Power Supply","Raspberry Pi and Possibilities",
                "Arduino and Possibilities"};
        String[] diffPara= {"The CPU makes calculations","The Motherboard houses all the components"};

        TextView changeTitle = (TextView) findViewById(R.id.partTitle);
    }

     */
}