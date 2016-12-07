package forever.foreverandroiddemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SPreference {
	
//	     //sp的名字
//		public static final String SP_NAME = "config";
//		private static SharedPreferences sp;
    public static String getId(Context context){
        return getPreference(context, "id");
                
    }
    
    public static String getAuth(Context context){
        return getPreference(context, "auth");
                
    }

    public static int getIntPreference(Context context, String Key) {
        SharedPreferences mPerferences = null;
        mPerferences = PreferenceManager.getDefaultSharedPreferences(context);
        return mPerferences.getInt(Key, 0);
    }
    public static String getPreference(Context context, String Key) {
        SharedPreferences mPerferences = null;
        mPerferences = PreferenceManager.getDefaultSharedPreferences(context);
        return mPerferences.getString(Key, "");
    }
    
    public static Boolean getBoolean(Context context, String key) {
        SharedPreferences mPerferences = null;
        mPerferences = PreferenceManager.getDefaultSharedPreferences(context);
        return mPerferences.getBoolean(key, false);
    }
    public static void setBoolean(Context context, String Key, Boolean Values) {
        SharedPreferences mPerferences = null;
        mPerferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor Editor = mPerferences.edit();
        Editor.putBoolean(Key, Values);
        Editor.commit();
    }

    public static void setPreference(Context context, String Key, String Values) {
        SharedPreferences mPerferences = null;
        mPerferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor Editor = mPerferences.edit();
        Editor.putString(Key, Values);
        Editor.commit();
    }
    public static void setInt(Context context, String Key, int Values) {
        SharedPreferences mPerferences = null;
        mPerferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor Editor = mPerferences.edit();
        Editor.putInt(Key, Values);
        Editor.commit();
    }

    public static void removePreference(Context context, String Key) {
        SharedPreferences mPerferences = null;
        mPerferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor Editor = mPerferences.edit();
        Editor.remove(Key);
        Editor.commit();
    }

}
