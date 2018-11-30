package f22labs.thiyagu.com.f22labs;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
