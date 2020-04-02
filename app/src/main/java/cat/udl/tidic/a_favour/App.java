package cat.udl.tidic.a_favour;

import android.app.Application;

import cat.udl.tidic.a_favour.preferences.PreferencesProvider;



public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesProvider.init(this);
    }
}
