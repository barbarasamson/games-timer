package ca.lifehypnosis.barbara.gamestimer;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private final static int BASIC_SCALE = 10;
    private final static float MIN_WIDTH_SCALE = 0.3f;

    private static String TAG = "MainActivity";
    private boolean isTimerOn = false;
    private Util mUtil;

    // values restored from savedInstanceState
    private long mStartTime = 0; // in milliseconds
    private long mDuration = 0; // in milliseconds
    private int mShortestTime = 0; // in minutes
    private String mJsonString = null; // file definition of the games

    //sequence of durations
    /*int[] minutes = new int[] {
            10,
            3,
            2,
            5,
            2,
            10,
            8,
            30,
            15,
            10,
            20,
            20,
            15,
            20
    };*/

    Game games[];

            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
                ViewGroup gameContainer = (ViewGroup)findViewById(R.id.timeFrame);
                mUtil = new Util(this);
                setUpGames(savedInstanceState, gameContainer);
    }

    private void setUpGames(Bundle savedInstanceState, ViewGroup container) {

        //begin by reading json file

        ArrayList<GameDef> gameDefs;

        if (savedInstanceState == null) {

            //Date startDate = parseFormat.parse(startTime);
            Date startDate = new Date(); //TEMP for testing
            //parseFormat.parse(startTime); //TEMP for testing
            mStartTime = startDate.getTime();

            mJsonString = mUtil.loadJSONFromAsset();
            gameDefs = mUtil.loadJson(mJsonString);
            int totalTime = mUtil.getTotalTime(gameDefs);
            int shortestTime = mUtil.getShortestTime(gameDefs);

            mDuration = totalTime * 60 * 1000; // mDuration is in milliseconds
            mShortestTime = shortestTime; // in minutes
        } else {
            mStartTime = savedInstanceState.getLong("startTime");
            mDuration = savedInstanceState.getLong("duration");
            mShortestTime = savedInstanceState.getInt("shortestTime");
            mJsonString = savedInstanceState.getString("jsonString");
        }
        gameDefs = mUtil.loadJson(mJsonString);

        //games = new Game[gameDefs.size()];
        //ArrayList<Game> games = new ArrayList<Game>();
        //Date gameTime = setupDate();
        Date gameStartTime = new Date(mStartTime);
        for(GameDef gameDef : gameDefs) {
            int gameTimeMinutes = gameDef.durationInMinutes;
            int gameDuration = gameTimeMinutes * 60 * 1000; // minutes to milliseconds
            int height = calculateHeight(gameTimeMinutes);
            int width = calculateWidth(gameTimeMinutes, mShortestTime);
            String title = gameDef.title;
            container.addView(new Game(this, title, gameDuration, gameStartTime, height, width).getGameView());
            gameStartTime = new Date(gameStartTime.getTime() + gameDuration); //calculate start time for next game
        }
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
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong("startTime", mStartTime);
        outState.putLong("duration", mDuration);
        outState.putInt("shortestTime", mShortestTime);
        outState.putString("jsonString", mJsonString);

        super.onSaveInstanceState(outState);
        GameColors.getInstance(this).reset();
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


            Date currentDate = new Date();
            long currentTime = currentDate.getTime();
            long millisElapsed = currentTime - mStartTime;


            if ((millisElapsed < 0 && millisElapsed < -5000)) { // more than 5 seconds before time to start
                Timer timer = new Timer(true);
                Date startDate = new Date(mStartTime);
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
            if (currentTime <= mStartTime) {
                startTimer(0f);
                return;
            }
            // currentTime > startTime
            //float secondsElapsed = millisElapsed / 1000;
            float percentExpired = (float)millisElapsed / (float)mDuration; // percentage of time elapsed
            startTimer(percentExpired);
     }

    private int calculateHeight(int duration) {

        // calculate the unscaled height of this button
        float height = duration * BASIC_SCALE;

// Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
// Convert the dps to pixels, based on density scale
        int pxHeight = (int) (height * scale + 0.5f);
        return pxHeight;
    }

    private int calculateWidth(int minutes, int shortestTime) {
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int minWidth = (int)(widthPixels * MIN_WIDTH_SCALE);
        int widthRange = widthPixels - minWidth;
        float ratio = Float.intBitsToFloat(shortestTime) / Float.intBitsToFloat(minutes);
        int pxWidth = (int)(ratio * widthRange) + minWidth;

        return pxWidth;
    }


    private void startTimer(float percentExpired) {
        View measureView = findViewById(R.id.scrollFrame);
        int height = measureView.getMeasuredHeight();

        final View timerBar = findViewById(R.id.timerBar);

        //run the calcs
        final float pixelsPerSecond = (float) height * 1000 / (float) mDuration;

        //determine how much time has elapsed already
        if (percentExpired > 0f) {
            timerBar.setTranslationY(height * percentExpired);
        }

        timerBar.setVisibility(View.VISIBLE);
        new CountDownTimer(mDuration, 1000) {
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
