package com.example.uok.cgpa_calculator;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    private EditText subj1, subj2, subj3, subj4, subj5, subj6, subj7;
    private Button cal;
    private TextView totalGPA;
    int counter = 0;
    int semesterCounter = 1;
    int failCounter = 0;
    double allSemesterGPA = 0;

    Boolean isEmpty(EditText text){
        CharSequence textBox =  text.getText().toString();
        return TextUtils.isEmpty(textBox);
    }

    private void clearAllField()
    {
        EditText[] allFieldList={subj1, subj2, subj3, subj4, subj5, subj6, subj7};

        for(int i=0; i<7; i++)
        {
            if(isEmpty(allFieldList[i]) == false)
                allFieldList[i].getText().clear();
        }
    }

    private double checkGPA(double Subject)
    {
        if(Subject < 50)
            return 0;
        else if(Subject >= 50 && Subject <= 52)
            return 1.0;
        else if(Subject >= 53 && Subject <= 56)
            return 1.4;
        else if(Subject >= 57 && Subject <= 60)
            return 1.8;
        else if(Subject >= 61 && Subject <= 63)
            return 2.0;
        else if(Subject >= 64 && Subject <= 67)
            return 2.4;
        else if(Subject >= 68 && Subject <= 70)
            return 2.8;
        else if(Subject >= 71 && Subject <= 74)
            return 3.0;
        else if(Subject >= 75 && Subject <= 79)
            return 3.4;
        else if(Subject >= 80 && Subject <= 84)
            return 3.8;
        else if(Subject >= 85 && Subject <= 100)
            return 4.0;
        else
            return 0;
    }

    private void gpaCalculate(){
        counter = 0;
        failCounter = 0;
        double GPA = 0, totalMarks = 0;
        double Subject1 = 0, Subject2 = 0, Subject3 = 0, Subject4 = 0, Subject5 = 0, Subject6 = 0, Subject7 = 0 ;
        double[] subjectsList= {Subject1, Subject2, Subject3, Subject4, Subject5, Subject6, Subject7};

        EditText[] subjLists={subj1, subj2, subj3, subj4, subj5, subj6, subj7};

        for (int i=0; i<7; i++) {
            if(isEmpty(subjLists[i]) == false) {
                subjectsList[i] = Double.parseDouble(subjLists[i].getText().toString());
                counter++;
            }
            if(isEmpty(subjLists[i]) == false && checkGPA(subjectsList[i]) == 0)
                failCounter++;
        }

        if(counter == 0)
            totalGPA.setText("Please filled all fields!");
        else {
            for (int j = 0; j < counter; j++) {
                if (isEmpty(subjLists[j]) == false)
                    totalMarks += checkGPA(subjectsList[j]);
            }
            if(failCounter == 0) {
                GPA = totalMarks / counter;
                allSemesterGPA += GPA;
                if(semesterCounter > 1){
                    double finalCGPA = allSemesterGPA/semesterCounter;
                    totalGPA.setText("CGPA With Included "+ Integer.toString(semesterCounter) +" Semesters: "+ String.format("%.2f", finalCGPA));
                }
                else
                    totalGPA.setText("Your CGPA: " + Double.toString(GPA));
            }
            else
                totalGPA.setText("Fail in some subject(s)");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subj1 = (EditText)findViewById(R.id.etSubject1);
        subj2 = (EditText)findViewById(R.id.etSubject2);
        subj3 = (EditText)findViewById(R.id.etSubject3);
        subj4 = (EditText)findViewById(R.id.etSubject4);
        subj5 = (EditText)findViewById(R.id.etSubject5);
        subj6 = (EditText)findViewById(R.id.etSubject6);
        subj7 = (EditText)findViewById(R.id.etSubject7);

        cal = (Button)findViewById(R.id.btnCalculate);
        totalGPA = (TextView)findViewById(R.id.tvGPA);

        //Calculate Button Event
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gpaCalculate();

                if(failCounter == 0 && semesterCounter < 8 && counter != 0) {
                    //Dialog Click Listner
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    clearAllField();
                                    semesterCounter++;
                                    subj1.requestFocus();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    semesterCounter = 1;
                                    allSemesterGPA = 0;
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("Do you want to calculate and include next semester GPA ?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
                else
                {
                    semesterCounter = 1;
                    allSemesterGPA = 0;
                }
            }
        });

    }
}