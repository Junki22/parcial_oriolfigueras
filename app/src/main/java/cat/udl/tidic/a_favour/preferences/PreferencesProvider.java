package cat.udl.tidic.a_favour.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PreferencesProvider
{

        private static String SHARED_PREFERENCES = "mPreferences";

        private static SharedPreferences sPreferences;

        public static SharedPreferences providePreferences() {
            return sPreferences;
        }

        public static void init(Context context)
        {
            sPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        }

    public static boolean existToken(String name)
    {
        Log.d("PreferencesProvider", "'" + name + "'" + "existeix a les sharedpreferences? --> " + sPreferences.contains(name));
        return sPreferences.contains(name);
    }
}
