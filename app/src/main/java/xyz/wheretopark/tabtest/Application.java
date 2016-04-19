//Prevents crashing of app if the app is already running and u click the app icon to open it again.

package xyz.wheretopark.tabtest;

import com.parse.Parse;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
    }
}