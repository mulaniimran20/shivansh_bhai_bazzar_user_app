package com.aarfaatechnovision.kjl.shivanshbhajibazar;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;


/**
 * *************************************************************************
 * GrocerApplication
 *
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This application class to set application level variable and method which
 * used through-out application
 * <p/>
 * *************************************************************************
 */

public class GrocerApplication extends Application {


    private static GrocerApplication mInstance;
    private SharedPreferences sharedPreferences;
    private Activity activity;
    public Activity getActivity() {
        return activity;
    }
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    public static GrocerApplication getmInstance() {
        return mInstance;
    }



    @Override
    public void onCreate() {
        super.onCreate();


        mInstance = this;
        sharedPreferences = getSharedPreferences(getString(R.string.app_name_new), Context.MODE_PRIVATE);


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }


    public void savePreferenceDataString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void savePreferenceDataBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


    /**
     * Call when application is close
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mInstance != null) {
            mInstance = null;
        }
    }





    public void clearePreferenceData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

}
