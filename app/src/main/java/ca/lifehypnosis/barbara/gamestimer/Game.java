package ca.lifehypnosis.barbara.gamestimer;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Date;

/**
 * Created by barbara on 2016-05-03.
 */
public class Game {

    private Context mContext;
    private String mTitle;
    private int mDuration;
    private Date mStartTime;
    private Button mButton;
    private Fragment mFragment;
    private Bundle mFragmentArgs;

    public Game(Context c, String title, int duration, Date startTime, int height, int width) {
        this.mContext = c;
        this.mTitle = title;
        this.mDuration = duration;
        this.mStartTime = startTime;

        mFragment = new Fragment();


        mButton = new Button(c);
        mButton.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        mButton.setHeight(height);
        mButton.setWidth(width);
        mButton.setText(title);
        mButton.setBackgroundColor(GameColors.getInstance(c).getNextColor());
    }

    public View getGameView() {

        return mButton;
    }
}

