package ca.lifehypnosis.barbara.gamestimer;

import android.content.Context;
import android.support.v4.content.ContextCompat;

/**
 * Created by barbara on 2016-06-29.
 */
public class GameColors {

    private int blue;
    private int brightGreen;
    private int violet;
    private int gold;
    private int orange;
    private int brightBlue;
    private int yellow;
    private int magenta;
    private int red;
    private int marigold;

    private int[] colors;

    int nextColorPick = 0;
    int highestPick;


    private static GameColors ourInstance = null;

    public static GameColors getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new GameColors(context);
        }
        return ourInstance;
    }

    private GameColors(Context context) {
        colors = new int[]{
                ContextCompat.getColor(context, R.color.brightBlue),
                ContextCompat.getColor(context, R.color.red),
                ContextCompat.getColor(context, R.color.violet),
                ContextCompat.getColor(context, R.color.magenta),
                ContextCompat.getColor(context, R.color.brightGreen),
                ContextCompat.getColor(context, R.color.blue),
                ContextCompat.getColor(context, R.color.red),
                ContextCompat.getColor(context, R.color.marigold),
                ContextCompat.getColor(context, R.color.brightGreen),
                ContextCompat.getColor(context, R.color.blue),
                ContextCompat.getColor(context, R.color.brightBlue),
                ContextCompat.getColor(context, R.color.marigold),
                ContextCompat.getColor(context, R.color.violet),
                ContextCompat.getColor(context, R.color.red)

        };
        highestPick = colors.length - 1;
    }

    public int getNextColor() {
        int color = colors[nextColorPick];
        if (nextColorPick >= (highestPick)) {
            nextColorPick = 0;
        } else {
            nextColorPick++;
        }
        return color;
    }

    public void reset() {
        nextColorPick = 0;
    }
}
