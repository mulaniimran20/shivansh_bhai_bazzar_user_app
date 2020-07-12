package com.aarfaatechnovision.kjl.shivanshbhajibazar.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;

public class SplashActivity extends AppCompatActivity {
    private int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new SplashTask().execute();
    }


    /**
     * AsycTask for setting splash screen by sleep thread for some time
     */
    private class SplashTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(SPLASH_TIME_OUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            Intent mLoginIntent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(mLoginIntent);
            overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
            finish();


        }
    }


}
