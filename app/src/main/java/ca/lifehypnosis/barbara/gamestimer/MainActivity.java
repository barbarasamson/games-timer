package ca.lifehypnosis.barbara.gamestimer;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        startTimer();
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

    private void startTimer() { // 3 hours, increment every 6 seconds
        final View timerBar = findViewById(R.id.timerBar);
        timerBar.setVisibility(View.VISIBLE);
        new CountDownTimer(10800000, 1000) {
            int yPad = timerBar.getPaddingTop();
            public void onTick(long millisUntilFinished) {
                timerBar.setPadding(0, yPad + 10, 0, 0);
                yPad = timerBar.getPaddingTop();
            }

            @Override
            public void onFinish() {
                timerBar.setVisibility(View.GONE);
            }
        }.start();
    }

}
