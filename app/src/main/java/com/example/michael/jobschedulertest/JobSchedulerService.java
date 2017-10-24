//package com.example.michael.jobschedulertest;
//
//import android.app.job.JobParameters;
//import android.app.job.JobService;
//import android.os.Message;
//import android.widget.Toast;
//
//import java.util.PriorityQueue;
//import java.util.logging.Handler;
//
///**
// * Created by Michael on 4/16/2017.
// */
//
//
//public class JobSchedulerService extends JobService{
//
//    PriorityQueue<String> queue;
//
//    private Handler mJobHandler = new Handler( new Handler.Callback() {
//
//        public boolean handleMessage( Message msg ) {
//            Toast.makeText( getApplicationContext(),
//                    "JobService task running", Toast.LENGTH_SHORT )
//                    .show();
//            jobFinished( (JobParameters) msg.obj, false );
//            return true;
//        }
//
//    } );
//
//    public JobSchedulerService(){
//     queue = new PriorityQueue<String>();
//    }
//    @Override
//    public boolean onStartJob(JobParameters params){
//        return false;
//    }
//
//    @Override
//    public boolean onStopJob(JobParameters params){
//        return false;
//    }
//
//}

package com.example.michael.jobschedulertest;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * Created by Michael on 4/16/2017.
 */
public class JobSchedulerService extends JobService {

    public String getFirstAns(){
        if(MainActivity.answers.peek() != null) {
            return MainActivity.answers.remove();
        }else{
            return null;
        }
    }

    private Handler mJobHandler = new Handler( new Handler.Callback() {
        @Override
        public boolean handleMessage( Message msg ) {
            String ans = getFirstAns();
            if(ans != null){
                Toast.makeText( getApplicationContext(), "Element is " + ans, Toast.LENGTH_SHORT ).show();
            }else{
                Toast.makeText( getApplicationContext(), "No Element", Toast.LENGTH_SHORT ).show();
                
            }

            jobFinished( (JobParameters) msg.obj, false );
            return true;
        }
    } );

    @Override
    public boolean onStartJob(JobParameters params ) {
        mJobHandler.sendMessage( Message.obtain( mJobHandler, 1, params ) );
        return true;
    }

    @Override
    public boolean onStopJob( JobParameters params ) {
        mJobHandler.removeMessages( 1 );
        return false;
    }

}