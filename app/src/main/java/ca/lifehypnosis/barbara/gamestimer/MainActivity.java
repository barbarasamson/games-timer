package ca.lifehypnosis.barbara.gamestimer;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private static String TAG = "MainActivity";
    private boolean isTimerOn = false;
        private static final String startTime = "2016-05-04 19:00:00:00";
    //private static final String startTime = "2016-05-04 15:42:00:00";
    private static final int DURATION = 10800; // 3 hours in seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isTimerOn) return;
        setupDate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDate() {
        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
            Date startDate = parseFormat.parse(startTime);
            //Date startDate = new Date(); //TEMP for testing
            //parseFormat.parse(startTime); //TEMP for testing

            long startTime = startDate.getTime();
        /*startTime = startTime - 600000; // TEMP 10 minutes
        startTime = startTime - 180000; // TEMP 3 minutes
        startTime = startTime - 120000; // TEMP 2 minutes
        startTime = startTime - 300000; // TEMP 5 minutes
        startTime = startTime - 120000; // TEMP 2 minutes
        startTime = startTime - 600000; // TEMP 10 minutes
        startTime = startTime - 480000; // TEMP 8 minutes
        startTime = startTime - 1800000; // TEMP 30 minutes
        startTime = startTime - 900000; // TEMP 15 minutes
        startTime = startTime - 600000; // TEMP 10 minutes
        startTime = startTime - 1200000; // TEMP 20 minutes
        startTime = startTime - 1200000; // TEMP 20 minutes
        startTime = startTime - 900000; // TEMP 15 minutes
        startTime = startTime - 1200000; // TEMP 20 minutes*/

            Date currentDate = new Date();
            long currentTime = currentDate.getTime();
            long millisElapsed = currentTime - startTime;


            if ((millisElapsed < 0 && millisElapsed < -5000)) { // more than 5 seconds before time to start
                Timer timer = new Timer(true);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startTimer(0f);
                            }
                        });}


                }, startDate);
                return;
            }
            if (currentTime <= startTime) {
                startTimer(0f);
                return;
            }
            // currentTime > startTime
            float secondsElapsed = millisElapsed / 1000;
            float percentExpired = secondsElapsed / DURATION; // percentage of time elapsed
            startTimer(percentExpired);
        } catch (java.text.ParseException e) {
            Log.e(TAG, "unable to parse: " + startTime);
            return;
        }
    }


    private void startTimer(float percentExpired) {
        View measureView = findViewById(R.id.scrollFrame);
        int height = measureView.getMeasuredHeight();

        final View timerBar = findViewById(R.id.timerBar);

        //run the calcs
        final float pixelsPerSecond = (float) height / (float) DURATION;

        //determine how much time has elapsed already
        if (percentExpired > 0f) {
            timerBar.setTranslationY(height * percentExpired);
        }

        timerBar.setVisibility(View.VISIBLE);
        new CountDownTimer(DURATION * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerBar.setTranslationY(timerBar.getTranslationY() + (pixelsPerSecond));
            }

            @Override
            public void onFinish() {
                timerBar.setVisibility(View.GONE);
            }
        }.start();
    }


}
