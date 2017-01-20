package ca.lifehypnosis.barbara.gamestimer;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by barbara on 2016-09-06.
 */

public class Util {

    Activity mActivity;

    public Util(Activity activity) {
        mActivity = activity;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = mActivity.getAssets().open("game1.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public ArrayList<GameDef> loadJson(String jsonString) {
        ArrayList<GameDef> gameList = new ArrayList<GameDef>();
        try {
            JSONObject obj = new JSONObject(jsonString);
            JSONArray jsonArray = obj.getJSONArray("Games");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject current = jsonArray.getJSONObject(i);
                Log.d("Details-->", current.getString("Title"));
                GameDef currentGame = new GameDef();
                currentGame.title = current.getString("Title");
                currentGame.Description = current.getString("Description");
                currentGame.durationInMinutes = current.getInt("DurationInMinutes");
                gameList.add(currentGame);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return gameList;

    }

    public int getShortestTime(ArrayList<GameDef> games) {
        if (games == null) {return 0;}

        int shortestTime = 0;
        int[] minutes = new int[games.size()];
        for (int minute: minutes) {
            shortestTime = (minute < shortestTime ? minute : shortestTime);
        }
        return shortestTime;
    }

    public int getTotalTime(ArrayList<GameDef> games) {
        if (games == null) {return 0;}
        int[] minutes = new int[games.size()];

        int totalTime = 0;
        for (int minute: minutes) {
            totalTime = (minute < totalTime ? minute : totalTime);
        }
        return totalTime;
    }

}
