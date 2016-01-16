package de.dev_bros.workoutcenter;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Marc on 10.01.2016.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);

        Parse.initialize(this);
    }
}
