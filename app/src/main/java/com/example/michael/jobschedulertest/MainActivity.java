package com.example.michael.jobschedulertest;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private JobScheduler mJobScheduler;
    private Button mScheduleJobButton;
    private Button mCancelAllJobsButton;
    private EditText editText;
    private Button addButton;
    public static LinkedList<String> answers = new LinkedList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJobScheduler = (JobScheduler)getSystemService (Context.JOB_SCHEDULER_SERVICE);
        mScheduleJobButton = (Button) findViewById(R.id.schedule_job);
        mCancelAllJobsButton = (Button) findViewById(R.id.cancel_all);
        addButton = (Button) findViewById(R.id.add_more_button);
        editText = (EditText) findViewById(R.id.textboxx);
//        final String ans = editText.getText().toString();

        Button more = (Button) findViewById(R.id.add_more_button);
        more.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                if(editText.getText().length() != 0){
                    String ans = editText.getText().toString();
                    answers.add(ans);
                    editText.setText("");
                }

            }});
        mScheduleJobButton.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
               JobInfo.Builder builder =
                       new JobInfo.Builder(1, new ComponentName(getPackageName(),JobSchedulerService.class.getName()));
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){

                   builder.setMinimumLatency(3000);
               }else{
                   builder.setPeriodic(3000);
           }
               if(mJobScheduler.schedule(builder.build())<=0){
                   //somethings not right
               }
           }
        });

        mCancelAllJobsButton.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
               Toast.makeText( getApplicationContext(), "you cancelled!", Toast.LENGTH_SHORT ).show();
               mJobScheduler.cancelAll();
           }
        });


    }
}
