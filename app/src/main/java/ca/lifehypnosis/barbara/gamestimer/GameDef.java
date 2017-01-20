package ca.lifehypnosis.barbara.gamestimer;

import android.widget.Button;

/**
 * Created by barbara on 2016-09-06.
 */

public class GameDef {
    public String title;
    public int durationInMinutes;
    public int moreInSeconds; //amount of time the game can be lengthened by
    public int lessInSeconds; //amount of time the game can be shortened by
    public String Description;
    public Button[] buttons;

}
